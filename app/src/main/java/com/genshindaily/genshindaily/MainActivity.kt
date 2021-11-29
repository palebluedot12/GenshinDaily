//SharedPreferences : https://riapapa-collection.tistory.com/41

package com.genshindaily.genshindaily

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_farming.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class MainActivity : AppCompatActivity() {

    //Fragment
    private val homeFragment = HomeFragment()
    private val farmingFragment = FarmingFragment()
    private val infoFragment = InfoFragment()
    private val optionFragment = OptionFragment()
    private final var TAG = "MainActivity"
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //야간모드
        replaceFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.tab1 -> replaceFragment(homeFragment)
                R.id.tab2 -> replaceFragment(farmingFragment)
                R.id.tab3 -> replaceFragment(infoFragment)
                R.id.tab4 -> replaceFragment(optionFragment)
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
}