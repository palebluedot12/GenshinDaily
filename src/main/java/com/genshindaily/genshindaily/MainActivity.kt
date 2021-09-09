//SharedPreferences : https://riapapa-collection.tistory.com/41

package com.genshindaily.genshindaily

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.kakao.adfit.ads.AdListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.end_dialog.*
import kotlinx.android.synthetic.main.fragment_farming.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.NumberFormatException
import java.util.*
import javax.xml.datatype.DatatypeConstants.DURATION
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView


class MainActivity : AppCompatActivity() {

    //Fragment
    private val homeFragment = HomeFragment()
    private val farmingFragment = FarmingFragment()
    private val optionFragment = OptionFragment()
    private final var TAG = "MainActivity"
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //야간모드
        replaceFragment(homeFragment)

        // createAd()

       // createAd()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.tab1 -> replaceFragment(homeFragment)
                R.id.tab2 -> replaceFragment(farmingFragment)
                R.id.tab3 -> replaceFragment(optionFragment)
            }
            true
        }
      } //onCreate

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
        }
    }

    //종료 다이얼로그 네이티브 광고
//    override fun onBackPressed() {
//        //super.onBackPressed()
//
//        val builder = AlertDialog.Builder(this)
//                .create()
//        val inflater = layoutInflater.inflate(R.layout.end_dialog,null)
//
//        builder.setView(inflater)
//        val backbtn = inflater.findViewById<View>(R.id.backbtn)
//        val exitbtn = inflater.findViewById<View>(R.id.exitbtn)
//        backbtn.setOnClickListener {
//            builder.dismiss()
//        }
//        exitbtn.setOnClickListener {
//            finish()
//        }
//
//        builder.show()
//
//
//
//    }
//
//    fun createAd() {
//        MobileAds.initialize(this)
//        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
//            .forNativeAd { ad : NativeAd ->
//                // Show the ad.
//            }
//            .withNativeAdOptions(
//                NativeAdOptions.Builder()
//                    // Methods in the NativeAdOptions.Builder class can be
//                    // used here to specify individual options settings.
//                    .build())
//            .build()
//    }


}