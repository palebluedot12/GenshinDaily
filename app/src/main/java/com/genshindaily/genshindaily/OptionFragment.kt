package com.genshindaily.genshindaily

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.genshindaily.genshindaily.databinding.FragmentInfoBinding
import com.genshindaily.genshindaily.databinding.FragmentOptionBinding
import kotlinx.android.synthetic.main.fragment_option.*
import java.util.*

class OptionFragment : Fragment() {

    private lateinit var binding: FragmentOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_option, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLocate()

        val items = arrayOf<String>("한국어(ASIA)", "English(ASIA)", "English(AMERICA)", "English(EUROPE)", "English(TW, HK, MO)")
        spinner.adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        )

        loadSpinnerPosition()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                        setLocate("ko")
                        Log.d("language", "Korean")
                    }
                    1 -> {
                        setLocate("ja")
                        Log.d("language", "AS_English")
                    }
                    2 -> {
                        setLocate("en")
                        Log.d("language", "US_English")
                    }
                    3 -> {
                        setLocate("fr")
                        Log.d("language", "Europe_English")
                    }
                    4 -> {
                        setLocate("zh")
                        Log.d("language", "HK_English")
                    }
                    //...
                    else -> {
                    }
                }
                saveSpinnerPosition(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        } //spinner

        //라이트모드
        btn_light.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.saved_theme), false)
                apply()
            }
        }

        //다크모드
        btn_dark.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.saved_theme), true)
                apply()
            }
        }

        //후원버튼
        btn_purchase.setOnClickListener {
            val intent = Intent(activity, OneTimeActivity::class.java)
            startActivity(intent)
        }

    }//onViewCreated

    private fun setLocate(Lang: String)
    {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        //save
        val editor = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate(){
        val sharedPreferences = requireActivity().getSharedPreferences(
            "Settings",
            Activity.MODE_PRIVATE
        )
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }

    fun saveSpinnerPosition(position: Int){
        val editor = requireActivity().getSharedPreferences("Spinner", Context.MODE_PRIVATE).edit()
        editor.putInt("position", position)
        editor.apply()
    }

    fun loadSpinnerPosition(){
        val sharedPreferences = requireActivity().getSharedPreferences(
            "Spinner",
            Activity.MODE_PRIVATE
        )
        val pos = sharedPreferences.getInt("position", 0)
            spinner.setSelection(pos)
    }
}