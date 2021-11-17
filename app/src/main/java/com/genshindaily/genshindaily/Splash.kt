package com.genshindaily.genshindaily

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class Splash : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "Splashh"
    var isPurchasedRemoveAds = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadData()
        var adRequest = AdRequest.Builder().build()

        //구글 전면광고
        MobileAds.initialize(this) {}
        InterstitialAd.load(this, "ca-app-pub-2985851452464131/2047135418", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                val errorCode = adError.cause
                mInterstitialAd = null
                Log.d("mInterfail", errorCode.toString())
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("mInter1", mInterstitialAd.toString())

                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        Log.d(TAG, "Ad failed to show.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                        mInterstitialAd = null;
                    }
                }
            }

        })

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            val c = OneTimeActivity()
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
            if(!isPurchasedRemoveAds) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                    Log.d("mInter3", mInterstitialAd.toString())
                }
            }
        },DURATION)

    }
    companion object {
        private const val DURATION : Long = 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun loadData() {
        val pref : SharedPreferences = getSharedPreferences("pref", 0)
        isPurchasedRemoveAds = pref.getBoolean("isPurchasedRemoveAds", false)
    }
}