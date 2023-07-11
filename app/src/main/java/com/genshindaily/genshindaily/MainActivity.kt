//SharedPreferences : https://riapapa-collection.tistory.com/41

package com.genshindaily.genshindaily

import android.content.Context
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

    private var currentFragmentTag: String = "home"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current fragment
        outState.putString("current_fragment", currentFragmentTag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //다크모드 설정 불러오기
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean(getString(R.string.saved_theme), false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        replaceFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab1 -> {
                    replaceFragment(homeFragment)
                    currentFragmentTag = "home"
                }
                R.id.tab2 -> {
                    replaceFragment(farmingFragment)
                    currentFragmentTag = "farming"
                }
                R.id.tab3 -> {
                    replaceFragment(infoFragment)
                    currentFragmentTag = "info"
                }
                R.id.tab4 -> {
                    replaceFragment(optionFragment)
                    currentFragmentTag = "option"
                }
            }
            true
        }

        //다크모드 - onSaveInstanceState
        if (savedInstanceState != null) {
            // Restore the current fragment
            val currentFragmentTag = savedInstanceState.getString("current_fragment")
            if (currentFragmentTag != null) {
                val currentFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
                if (currentFragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, currentFragment, currentFragmentTag)
                        .commit()
                }
            }
        } else {
            // Show the default fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, HomeFragment(), "home")
                .commit()
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