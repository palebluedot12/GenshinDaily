package com.genshindaily.genshindaily

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.android.billingclient.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.security.auth.callback.Callback

class BillingModule(
    private val activity: Activity,
    private val lifeCycleScope: LifecycleCoroutineScope,
    private val callback: Callback
) {
    interface Callback {
        fun onBillingModulesIsReady()
        fun onSuccess(purchase: Purchase)
        fun onFailure(errorCode: Int)
    }

    // '소비'되어야 하는 sku 들을 적어줍니다.
    private val consumableSkus = setOf(OneTimeActivity.Sku.WELKIN_MOON, OneTimeActivity.Sku.BATTLE_PASS)

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.let { purchaseList ->
                    for (purchase in purchaseList) {
                        confirmPurchase(purchase)
                    }
                }
            }
        }
    }

    private var billingClient: BillingClient = BillingClient.newBuilder(activity)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()


    private fun confirmPurchase(purchase: Purchase) {
        when {
            consumableSkus.contains(purchase.skus[0]) -> {
                // 소비성 구매는 consume을 해주어야합니다.
                val consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                lifeCycleScope.launch(Dispatchers.IO) {
                    val result = billingClient.consumePurchase(consumeParams)
                    withContext(Dispatchers.Main) {
                        if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            callback.onSuccess(purchase)
                        }
                    }
                }
            }
            purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged -> {
                // 구매는 완료되었으나 확인이 되어있지 않다면 구매 확인 처리를 합니다.
                val ackPurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                lifeCycleScope.launch(Dispatchers.IO) {
                    val result = billingClient.acknowledgePurchase(ackPurchaseParams.build())
                    withContext(Dispatchers.Main) {
                        if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                            callback.onSuccess(purchase)
                        } else {
                            callback.onFailure(result.responseCode)
                        }
                    }
                }
            }
        }

    }

    init {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 여기서부터 billingClient 활성화 됨
                    callback.onBillingModulesIsReady()
                } else {
                    callback.onFailure(billingResult.responseCode)
                }
            }

            override fun onBillingServiceDisconnected() {
                // GooglePlay와 연결이 끊어졌을때 재시도하는 로직이 들어갈 수 있음.
                Log.e("BillingModule", "Disconnected.")
            }
        })
    }

    /**
     * 원하는 sku id를 가지고있는 상품 정보를 가져옵니다.
     * @param sku sku 목록
     * @param resultBlock sku 상품정보 콜백
     */
    fun querySkuDetail(
        type: String = BillingClient.SkuType.INAPP,
        vararg sku: String,
        resultBlock: (List<SkuDetails>) -> Unit = {}
    ) {
        SkuDetailsParams.newBuilder().apply {
            // 인앱, 정기결제 유형중에서 고름. (SkuType.INAPP, SkuType.SUBS)
            setSkusList(sku.asList()).setType(type)
            // 비동기적으로 상품정보를 가져옵니다.
            lifeCycleScope.launch(Dispatchers.IO) {
                val skuDetailResult = billingClient.querySkuDetails(build())
                withContext(Dispatchers.Main) {
                    resultBlock(skuDetailResult.skuDetailsList ?: emptyList())
                }
            }
        }
    }

    /**
     * 구매 시작하기
     * @param skuDetail 구매하고자하는 항목. querySkuDetail()을 통해 획득한 SkuDetail
     */
    fun purchase(
        skuDetail: SkuDetails
    ) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetail)
            .build()

        // 구매 절차를 시작, OK라면 제대로 된것입니다.
        val launchResult = billingClient.launchBillingFlow(activity, flowParams)
        if (launchResult.responseCode != BillingClient.BillingResponseCode.OK) {
            callback.onFailure(launchResult.responseCode)
        }
        // 이후 부터는 purchasesUpdatedListener를 거치게 됩니다.
    }

    fun onResume(type: String) {
        if (billingClient.isReady) {
            billingClient.queryPurchasesAsync(type) { result, purchasesList ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    purchasesList?.let { purchaseList ->
                        for (purchase in purchaseList) {
                            if (!purchase.isAcknowledged && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                confirmPurchase(purchase)
                            }
                        }
                    }
                }
            }
        }
    }


    fun checkPurchased(
        sku: String,
        resultBlock: (purchased: Boolean) -> Unit
    ) {
        billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP) { result, purchasesList ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val purchased = purchasesList?.any { purchase ->
                    purchase.skus?.contains(sku) == true && purchase.isAcknowledged && purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                } ?: false
                resultBlock(purchased)
            } else {
                resultBlock(false)
            }
        }
    }



    // 구매 확인 검사 Extension
    private fun Purchase.isPurchaseConfirmed(): Boolean {
        return this.isAcknowledged && this.purchaseState == Purchase.PurchaseState.PURCHASED
    }

}