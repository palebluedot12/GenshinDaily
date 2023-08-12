package com.genshindaily.genshindaily

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.genshindaily.genshindaily.databinding.ActivityOnetimePurchaseBinding
import com.genshindaily.genshindaily.databinding.FragmentInfoBinding
import com.genshindaily.genshindaily.databinding.FragmentOptionBinding
import kotlinx.android.synthetic.main.activity_onetime_purchase.*

class OneTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnetimePurchaseBinding
    private lateinit var bm: BillingModule

    private val storage: AppStorage by lazy {
        AppStorage(this)
    }

    private var mSkuDetails = listOf<SkuDetails>()
        set(value) {
            field = value
            setSkuDetailsView()
        }

    //이전에 광고 제거 구매 여부
    private var isPurchasedRemoveAds = false
        set(value) {
            field = value
            updateRemoveAdsView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnetimePurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bm = BillingModule(this, lifecycleScope, object: BillingModule.Callback {
            override fun onBillingModulesIsReady() {
                bm.querySkuDetail(BillingClient.SkuType.INAPP, Sku.PURCHASE_NO_ADS, Sku.WELKIN_MOON, Sku.BATTLE_PASS) { skuDetailsList ->
                    mSkuDetails = skuDetailsList
                }

                bm.checkPurchased(Sku.PURCHASE_NO_ADS) { result ->
                    isPurchasedRemoveAds = result
                }
            }

            override fun onSuccess(purchase: Purchase) {
                when (purchase.skus.firstOrNull()) {
                    Sku.PURCHASE_NO_ADS-> {
                        isPurchasedRemoveAds = true
                    }
                    Sku.WELKIN_MOON -> {
                        // 공월 축복
                    }

                    Sku.BATTLE_PASS -> {
                        // 기행
                    }
                }
            }

            override fun onFailure(errorCode: Int) {
                when (errorCode) {
                    BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                        Toast.makeText(this@OneTimeActivity, "이미 구입한 상품입니다.", Toast.LENGTH_LONG).show()
                    }
                    BillingClient.BillingResponseCode.USER_CANCELED -> {
                        Toast.makeText(this@OneTimeActivity, "구매를 취소하셨습니다.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this@OneTimeActivity, "error: $errorCode", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        setClickListeners()
    } //OnCreate

    private fun setClickListeners() {
        with (binding) {
            // 광고 제거 구매 버튼 클릭
            btn_purchase_ad.setOnClickListener {
                mSkuDetails.find { it.sku == Sku.PURCHASE_NO_ADS}?.let { skuDetail ->
                    bm.purchase(skuDetail)
                } ?: also {
                    Toast.makeText(this@OneTimeActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                }
            }

            btn_purchase_welkin_moon.setOnClickListener {
                mSkuDetails.find { it.sku == Sku.WELKIN_MOON }?.let { skuDetail ->
                    bm.purchase(skuDetail)
                } ?: also {
                    Toast.makeText(this@OneTimeActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                }
            }

            btn_purchase_battle_pass.setOnClickListener {
                mSkuDetails.find { it.sku == Sku.BATTLE_PASS}?.let { skuDetail ->
                    bm.purchase(skuDetail)
                } ?: also {
                    Toast.makeText(this@OneTimeActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateRemoveAdsView() {
        binding.purchaseAdWhether.text = "광고 제거 여부: ${if (isPurchasedRemoveAds) "O" else "X"}"
    }

    private fun setSkuDetailsView() {
        val builder = StringBuilder()
        for (skuDetail in mSkuDetails) {
            for (skuDetail in mSkuDetails) {
                builder.append("<${skuDetail.title}>\n")
                builder.append(skuDetail.price)
                builder.append("\n======================\n\n")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bm.onResume(BillingClient.SkuType.INAPP)
    }

    private fun saveData() {
        val pref : SharedPreferences = getSharedPreferences("pref", 0)
        val edit = pref.edit() //수정모드
        edit.putBoolean("isPurchasedRemoveAds", isPurchasedRemoveAds)
        edit.apply()
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    object Sku {
        const val PURCHASE_NO_ADS = "purchase_no_ads"
        const val WELKIN_MOON = "welkin_moon"
        const val BATTLE_PASS = "battle_pass"
    }

}
