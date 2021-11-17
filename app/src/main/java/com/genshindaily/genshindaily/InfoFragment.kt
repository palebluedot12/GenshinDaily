package com.genshindaily.genshindaily

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.genshindaily.genshindaily.databinding.ActivityMainBinding
import com.genshindaily.genshindaily.databinding.FragmentInfoBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kakao.adfit.ads.AdListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_option.*
import kotlinx.android.synthetic.main.webview_daily_check_in.*
import java.util.*
import java.util.zip.Inflater

class InfoFragment : Fragment() {

    var isPurchasedRemoveAds = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()

        if(!isPurchasedRemoveAds) {
            MobileAds.initialize(requireActivity())
            adView3.loadAd(AdRequest.Builder().build()) //구글 배너
        }

        val builder = AlertDialog.Builder(requireActivity())
        val webView = layoutInflater.inflate(R.layout.webview_daily_check_in, null)
        val webview_daily_check_in = webView.findViewById<WebView>(R.id.webview_daily_check_in)

        text_daily_check_in.setPaintFlags(text_daily_check_in.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

        //출석체크 WebView 연결
        text_daily_check_in.setOnClickListener {
            val intent = Intent(activity, WebwebView::class.java)
            startActivity(intent)
        }

        //5성
        aloy_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_aloy, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        raiden_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_raiden, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        yoimiya_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_yoimiya, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        ayaka_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_ayaka, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        kazuha_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_kazuha, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        eula_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_eula, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        jean_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_jean, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        diluc_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_diluc, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        venti_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_venti, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        klee_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_klee, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        mona_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_mona, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        xiao_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_xiao, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        qiqi_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_qiqi, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        keqing_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_keqing, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        tartaglia_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_tartaglia, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        zhongli_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_zhongli, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        albedo_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_albedo, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        ganyu_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_ganyu, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        hutao_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_hutao, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }

        //4성
        sara_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_sara, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        sayu_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_sayu, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        rosaria_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_rosaria, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        yanfei_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_yanfei, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        lisa_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_lisa, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        amber_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_amber, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        kaeya_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_keiya, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        barbara_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_barbara, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        razor_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_razor, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        bennett_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_bennett, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        noelle_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_noelle, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        fischl_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_fischl, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        sucrose_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_surcrose, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        beidou_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_beidou, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        ningguang_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_ningguang, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        xiangling_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_xiangling, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        xingqiu_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_xingqiu, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        chongyun_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_chongyun, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        diona_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_diona, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }
        xinyan_materials.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.materials_xinyan, null)
            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }

    }//onViewCreated

    private fun loadData() {
        val pref = requireActivity().getSharedPreferences("pref", 0)
        isPurchasedRemoveAds = pref.getBoolean("isPurchasedRemoveAds", isPurchasedRemoveAds)
    }

}