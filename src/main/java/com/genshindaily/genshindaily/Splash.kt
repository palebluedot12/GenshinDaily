package com.genshindaily.genshindaily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class Splash : AppCompatActivity() {

    public var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        MobileAds.initialize(this) {}

        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-2985851452464131/2047135418", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                Log.d("mInterfail", mInterstitialAd.toString())
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("mInter1", mInterstitialAd.toString())
            }

        })

//        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
//            override fun onAdDismissedFullScreenContent() {
//                Log.d(TAG, "Ad was dismissed.")
//            }
//
//            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                Log.d(TAG, "Ad failed to show.")
//            }
//
//            override fun onAdShowedFullScreenContent() {
//                Log.d(TAG, "Ad showed fullscreen content.")
//                mInterstitialAd = null
//            }
//        }

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
            if(mInterstitialAd != null)
            {
                mInterstitialAd?.show(this)
                Log.d("mInter3", mInterstitialAd.toString())
            }
        },DURATION)

    }
    companion object {
        private const val DURATION : Long = 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}