
package com.genshindaily.genshindaily

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_farming.*
import java.util.*


class FarmingFragment : Fragment() {

    var Textflag_iron_chunk = 0
    var Textflag_white_iron_chunk = 0
    var Textflag_cor_lapis = 0
    var Textflag_yabak = 0
    var Textflag_starsilver = 0
    var Textflag_electro_crystal = 0
    var Textflag_crystal_chunk = 0
    var Textflag_amethyst_lump = 0
    var Textflag_gori_gori = 0
    var Textflag_nakrak = 0
    var Textflag_small_lamp_grass = 0
    var Textflag_dandelion_seed = 0
    var Textflag_mushroom = 0
    var Textflag_cecilia = 0
    var Textflag_yesang_flower = 0
    var Textflag_yuri_lily = 0
    var Textflag_yuri_zumeoni = 0
    var Textflag_jueyun_chili = 0
    var Textflag_qingxin = 0
    var Textflag_tongtong = 0
    var Textflag_windwheel = 0
    var Textflag_ghost_pungdeng = 0
    var Textflag_crystal_golsu = 0
    var Textflag_sanho_pearl = 0
    var Textflag_cherry_blossom_sugu = 0
    var Textflag_yulimple = 0
    var Textflag_hyul_gok = 0
    var Textflag_amakumocho = 0
    var Textflag_sea_bulocho = 0
    var Textflag_parametic_transformer = 0
    var Textflag_fish_mond = 0
    var Textflag_fish_liyue = 0
    var Textflag_fish_inazuma = 0
    var Textflag_fluorescent_fungus = 0

    //수메르
    var Textflag_hena_berry = 0
    var Textflag_kalpalata_lotus = 0
    var Textflag_mourning_flower = 0
    var Textflag_nilotpala_lotus = 0
    var Textflag_padisarah = 0
    var Textflag_rukkhashava_mushrooms = 0
    var Textflag_sand_grease_pupa = 0
    var Textflag_scarab = 0
    var Textflag_trishiraite = 0

    var isPurchasedRemoveAds = false

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var alarmIntent2: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()


        if(!isPurchasedRemoveAds) {
            adView2.loadAd(AdRequest.Builder().build()) //구글광고

//            //카카오 광고
//            kakaoAdview2.run {
//                setClientId("DAN-jx2L2xstDOGjpBcZ")
//                setAdListener(object : AdListener {
//                    override fun onAdLoaded() {
//                        //JoLog.d("ad loaded")
//                    }
//
//                    override fun onAdFailed(p0: Int) {
//                        //JoLog.d("failed to upload")
//                    }
//
//                    override fun onAdClicked() {}
//                })
//                loadAd()
//            }

        }
        //알람 스위치
        farming_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)Toast.makeText(activity, activity?.getString(R.string.farming_switch).toString(), Toast.LENGTH_LONG).show()
            if(!isChecked) {
                Toast.makeText(activity,activity?.getString(R.string.resin_alarm_off).toString() , Toast.LENGTH_LONG).show()
                alarm_off(1, activity?.getString(R.string.iron_chunk).toString())
                alarm_off(2, activity?.getString(R.string.white_iron_chunk).toString())
                alarm_off(3, activity?.getString(R.string.cor_lapis).toString())
                alarm_off(4, activity?.getString(R.string.noctilucous).toString())
                alarm_off(5, activity?.getString(R.string.starsilver).toString())
                alarm_off(6, activity?.getString(R.string.electro_crystal).toString())
                alarm_off(7, activity?.getString(R.string.crystal_chunk).toString())
                alarm_off(8, activity?.getString(R.string.wolfhook).toString())
                alarm_off(9, activity?.getString(R.string.valberry).toString())
                alarm_off(10, activity?.getString(R.string.small_lamp_grass).toString())
                alarm_off(11, activity?.getString(R.string.dandelion_seed).toString())
                alarm_off(12, activity?.getString(R.string.philanemo_mushroom).toString())
                alarm_off(13, activity?.getString(R.string.cecilia).toString())
                alarm_off(14, activity?.getString(R.string.silk_flower).toString())
                alarm_off(15, activity?.getString(R.string.glaze_lily).toString())
                alarm_off(16, activity?.getString(R.string.violetgrass).toString())
                alarm_off(17, activity?.getString(R.string.jueyun).toString())
                alarm_off(18, activity?.getString(R.string.qingxin).toString())
                alarm_off(19, activity?.getString(R.string.calla_lily).toString())
                alarm_off(20, activity?.getString(R.string.windwheel_aster).toString())
                alarm_off(21, activity?.getString(R.string.crystal_golsu).toString())
                alarm_off(22, activity?.getString(R.string.ghost_pungdeng).toString())
                alarm_off(23, activity?.getString(R.string.cherry_blossom).toString())
                alarm_off(24, activity?.getString(R.string.hyulgok).toString())
                alarm_off(25, activity?.getString(R.string.yulimple).toString())
                alarm_off(26, activity?.getString(R.string.sea_bulocho).toString())
                alarm_off(27, activity?.getString(R.string.sanho_pearl).toString())
                alarm_off(28, activity?.getString(R.string.amakumocho).toString())
                alarm_off(29, activity?.getString(R.string.parametric_transformer).toString())
                alarm_off(30, activity?.getString(R.string.fish_mond).toString())
                alarm_off(31, activity?.getString(R.string.fish_liyue).toString())
                alarm_off(32, activity?.getString(R.string.fish_inazuma).toString())
                alarm_off(33, activity?.getString(R.string.fluorescent_fungus).toString())
                alarm_off(34, activity?.getString(R.string.hena_berry).toString())
                alarm_off(35, activity?.getString(R.string.kalpalata_lotus).toString())
                alarm_off(36, activity?.getString(R.string.mourning_flower).toString())
                alarm_off(37, activity?.getString(R.string.nilotpala_lotus).toString())
                alarm_off(38, activity?.getString(R.string.padisarah).toString())
                alarm_off(39, activity?.getString(R.string.rukkhashava_mushrooms).toString())
                alarm_off(40, activity?.getString(R.string.sand_grease_pupa).toString())
                alarm_off(41, activity?.getString(R.string.scarab).toString())
                alarm_off(42, activity?.getString(R.string.trishiraite).toString())
                alarm_off(43, activity?.getString(R.string.amethyst_lump).toString())
            }
        }

        //86400 172800 259200
        iron_chunk.setOnClickListener(View.OnClickListener {
            onTextClicked_iron_chunk()
            if(Textflag_iron_chunk == 1 && farming_switch.isChecked) alarm_on(86400,activity?.getString(R.string.iron_chunk).toString(),1) else alarm_off(1,activity?.getString(R.string.iron_chunk).toString())
        })
        white_iron_chunk.setOnClickListener(View.OnClickListener {
            onTextClicked_white_iron_chunk()
            if(Textflag_white_iron_chunk == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.white_iron_chunk).toString(),2) else alarm_off(2,activity?.getString(R.string.white_iron_chunk).toString())
        })
        cor_lapis.setOnClickListener(View.OnClickListener {
            onTextClicked_cor_lapis()
            if(Textflag_cor_lapis == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.cor_lapis).toString(),3) else alarm_off(3,activity?.getString(R.string.cor_lapis).toString())
        })
        yabak.setOnClickListener(View.OnClickListener {
            onTextClicked_yabak()
            if(Textflag_yabak == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.noctilucous).toString(),4) else alarm_off(4,activity?.getString(R.string.noctilucous).toString())
        })
        starsilver.setOnClickListener(View.OnClickListener {
            onTextClicked_starsilver()
            if(Textflag_starsilver == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.starsilver).toString(),5) else alarm_off(5,activity?.getString(R.string.starsilver).toString())
        })
        electro_crystal.setOnClickListener(View.OnClickListener {
            onTextClicked_electro_crystal()
            if(Textflag_electro_crystal == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.electro_crystal).toString(),6) else alarm_off(6,activity?.getString(R.string.electro_crystal).toString())
        })
        crystal_chunk.setOnClickListener(View.OnClickListener {
            onTextClicked_crystal_chunk()
            if(Textflag_crystal_chunk == 1 && farming_switch.isChecked) alarm_on(259200,activity?.getString(R.string.crystal_chunk).toString(),7) else alarm_off(7,activity?.getString(R.string.crystal_chunk).toString())
        })
        gori_gori.setOnClickListener(View.OnClickListener {
            onTextClicked_gori_gori()
            if(Textflag_gori_gori == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.wolfhook).toString(),8) else alarm_off(8,activity?.getString(R.string.wolfhook).toString())
        })
        nakrak.setOnClickListener(View.OnClickListener {
            onTextClicked_nakrak()
            if(Textflag_nakrak == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.valberry).toString(),9) else alarm_off(9,activity?.getString(R.string.valberry).toString())
        })
        small_lamp_grass.setOnClickListener(View.OnClickListener {
            onTextClicked_small_lamp_grass()
            if(Textflag_small_lamp_grass == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.small_lamp_grass).toString(),10) else alarm_off(10,activity?.getString(R.string.small_lamp_grass).toString())
        })
        dandelion_seed.setOnClickListener(View.OnClickListener {
            onTextClicked_dandelion_seed()
            if(Textflag_dandelion_seed == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.dandelion_seed).toString(),11) else alarm_off(11,activity?.getString(R.string.dandelion_seed).toString())
        })
        mushroom.setOnClickListener(View.OnClickListener {
            onTextClicked_mushroom()
            if(Textflag_mushroom == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.philanemo_mushroom).toString(),12) else alarm_off(12,activity?.getString(R.string.philanemo_mushroom).toString())
        })
        cecilia.setOnClickListener(View.OnClickListener {
            onTextClicked_cecilia()
            if(Textflag_cecilia == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.cecilia).toString(),13) else alarm_off(13,activity?.getString(R.string.cecilia).toString())
        })
        yesang_flower.setOnClickListener(View.OnClickListener {
            onTextClicked_yesang_flower()
            if(Textflag_yesang_flower == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.silk_flower).toString(),14) else alarm_off(14,activity?.getString(R.string.silk_flower).toString())
        })
        yuri_lily.setOnClickListener(View.OnClickListener {
            onTextClicked_yuri_iliy()
            if(Textflag_yuri_lily == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.glaze_lily).toString(),15) else alarm_off(15,activity?.getString(R.string.glaze_lily).toString())
        })
        yuri_zumeoni.setOnClickListener(View.OnClickListener {
            onTextClicked_yuri_zumeoni()
            if(Textflag_yuri_zumeoni == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.violetgrass).toString(),16) else alarm_off(16,activity?.getString(R.string.violetgrass).toString())
        })
        jueyun_chili.setOnClickListener(View.OnClickListener {
            onTextClicked_jueyun_chili()
            if(Textflag_jueyun_chili == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.jueyun).toString(),17) else alarm_off(17,activity?.getString(R.string.jueyun).toString())
        })
        qingxin.setOnClickListener(View.OnClickListener {
            onTextClicked_qingxin()
            if(Textflag_qingxin == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.qingxin).toString(),18) else alarm_off(18,activity?.getString(R.string.qingxin).toString())
        })
        tongtong.setOnClickListener(View.OnClickListener {
            onTextClicked_tongtong()
            if(Textflag_tongtong == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.calla_lily).toString(),19) else alarm_off(19,activity?.getString(R.string.calla_lily).toString())
        })
        windwheel_aster.setOnClickListener(View.OnClickListener {
            onTextClicked_windwheel()
            if(Textflag_windwheel == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.windwheel_aster).toString(),20) else alarm_off(20,activity?.getString(R.string.windwheel_aster).toString())
        })
        crystal_golsu.setOnClickListener(View.OnClickListener {
            onTextClicked_crystal_golsu()
            if(Textflag_crystal_golsu == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.crystal_golsu).toString(),21) else alarm_off(21,activity?.getString(R.string.crystal_golsu).toString())
        })
        ghost_pungdeng.setOnClickListener(View.OnClickListener {
            onTextClicked_ghost_pungdeng()
            if(Textflag_ghost_pungdeng == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.ghost_pungdeng).toString(),22) else alarm_off(22,activity?.getString(R.string.ghost_pungdeng).toString())
        })
        cherry_blossom_sugu.setOnClickListener(View.OnClickListener {
            onTextClicked_cherry_blossom_sugu()
            if(Textflag_cherry_blossom_sugu == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.cherry_blossom).toString(),23) else alarm_off(23,activity?.getString(R.string.cherry_blossom).toString())
        })
        hyul_gok.setOnClickListener(View.OnClickListener {
            onTextClicked_hyul_gok()
            if(Textflag_hyul_gok == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.hyulgok).toString(),24) else alarm_off(24,activity?.getString(R.string.hyulgok).toString())
        })
        yulimple.setOnClickListener(View.OnClickListener {
            onTextClicked_yulimple()
            if(Textflag_yulimple == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.yulimple).toString(),25) else alarm_off(25,activity?.getString(R.string.yulimple).toString())
        })
        sea_bulocho.setOnClickListener(View.OnClickListener {
            onTextClicked_sea_bulocho()
            if(Textflag_sea_bulocho == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.sea_bulocho).toString(),26) else alarm_off(26,activity?.getString(R.string.sea_bulocho).toString())
        })
        sanho_pearl.setOnClickListener(View.OnClickListener {
            onTextClicked_sanho_pearl()
            if(Textflag_sanho_pearl == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.sanho_pearl).toString(),27) else alarm_off(27,activity?.getString(R.string.sanho_pearl).toString())
        })
        amakumocho.setOnClickListener(View.OnClickListener {
            onTextClicked_amakumocho()
            if(Textflag_amakumocho == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.amakumocho).toString(),28) else alarm_off(28,activity?.getString(R.string.amakumocho).toString())
        })
        parametric_transformer.setOnClickListener(View.OnClickListener {
            onTextClicked_parametric_transformer()
            if(Textflag_parametic_transformer == 1 && farming_switch.isChecked) alarm_on(604800,activity?.getString(R.string.parametric_transformer).toString(),29) else alarm_off(29,activity?.getString(R.string.parametric_transformer).toString())
        })
        fish_mond.setOnClickListener(View.OnClickListener {
            onTextClicked_fish_mond()
            if(Textflag_fish_mond == 1 && farming_switch.isChecked) alarm_on(259200,activity?.getString(R.string.fish_mond).toString(),30)
            else alarm_off(30,activity?.getString(R.string.fish_mond).toString())
        })
        fish_liyue.setOnClickListener(View.OnClickListener {
            onTextClicked_fish_liyue()
            if(Textflag_fish_liyue == 1 && farming_switch.isChecked) alarm_on(259200,activity?.getString(R.string.fish_liyue).toString(),31)
            else alarm_off(31,activity?.getString(R.string.fish_liyue).toString())
        })
        fish_inazuma.setOnClickListener(View.OnClickListener {
            onTextClicked_fish_inazuma()
            if(Textflag_fish_inazuma == 1 && farming_switch.isChecked) alarm_on(259200,activity?.getString(R.string.fish_inazuma).toString(),32)
            else alarm_off(32,activity?.getString(R.string.fish_inazuma).toString())
        })
        fluorescent_fungus.setOnClickListener(View.OnClickListener {
            onTextClicked_fluorescent_fungus()
            if(Textflag_fluorescent_fungus == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.fluorescent_fungus).toString(),33)
            else alarm_off(33,activity?.getString(R.string.fluorescent_fungus).toString())
        })
        hena_berry.setOnClickListener(View.OnClickListener {
            onTextClicked_hena_berry()
            if(Textflag_hena_berry == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.hena_berry).toString(),34)
            else alarm_off(34,activity?.getString(R.string.hena_berry).toString())
        })
        kalpalata_lotus.setOnClickListener(View.OnClickListener {
            onTextClicked_kalpalata_lotus()
            if(Textflag_kalpalata_lotus == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.kalpalata_lotus).toString(),35)
            else alarm_off(35,activity?.getString(R.string.kalpalata_lotus).toString())
        })
        mourning_flower.setOnClickListener(View.OnClickListener {
            onTextClicked_mourning_flower()
            if(Textflag_mourning_flower == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.mourning_flower).toString(),36)
            else alarm_off(36,activity?.getString(R.string.mourning_flower).toString())
        })
        nilotpala_lotus.setOnClickListener(View.OnClickListener {
            onTextClicked_nilotpala_lotus()
            if(Textflag_nilotpala_lotus == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.nilotpala_lotus).toString(),37)
            else alarm_off(37,activity?.getString(R.string.nilotpala_lotus).toString())
        })
        padisarah.setOnClickListener(View.OnClickListener {
            onTextClicked_padisarah()
            if(Textflag_padisarah == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.padisarah).toString(),38)
            else alarm_off(38,activity?.getString(R.string.padisarah).toString())
        })
        rukkhashava_mushrooms.setOnClickListener(View.OnClickListener {
            onTextClicked_rukkhashava_mushrooms()
            if(Textflag_rukkhashava_mushrooms == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.rukkhashava_mushrooms).toString(),39)
            else alarm_off(39,activity?.getString(R.string.rukkhashava_mushrooms).toString())
        })
        sand_grease_pupa.setOnClickListener(View.OnClickListener {
            onTextClicked_sand_grease_pupa()
            if(Textflag_sand_grease_pupa == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.sand_grease_pupa).toString(),40)
            else alarm_off(40,activity?.getString(R.string.sand_grease_pupa).toString())
        })
        scarab.setOnClickListener(View.OnClickListener {
            onTextClicked_scarab()
            if(Textflag_scarab == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.scarab).toString(),41)
            else alarm_off(34,activity?.getString(R.string.scarab).toString())
        })
        trishiraite.setOnClickListener(View.OnClickListener {
            onTextClicked_trishiraite()
            if(Textflag_trishiraite == 1 && farming_switch.isChecked) alarm_on(172800,activity?.getString(R.string.trishiraite).toString(),42)
            else alarm_off(34,activity?.getString(R.string.trishiraite).toString())
        })
        amethyst_lump.setOnClickListener(View.OnClickListener {
            onTextClicked_amethyst_lump()
            if(Textflag_amethyst_lump == 1 && farming_switch.isChecked) alarm_on(259200,activity?.getString(R.string.amethyst_lump).toString(),43) else alarm_off(43,activity?.getString(R.string.amethyst_lump).toString())
        })

    }//onViewCreated

    override fun onStop() {
        super.onStop()
//        kakaoAdview?.destroy()
        saveData()
    }

    fun getIntent(context: Context, requestCode: Int, name: String? = null, id : Int): PendingIntent? {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("이름", name)
        intent.putExtra("id", id)
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    private fun alarm_on(time : Int, param : String,id:Int) {

//        alarmMgr = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmIntent = Intent(activity, AlarmReceiver::class.java).let { intent ->
//            PendingIntent.getBroadcast(activity, 0, intent, 0) //Broadcast Receiver를 실행.
//        }
        val pendingIntent = getIntent(requireActivity(), id, param,id)
        val alarm = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // 여기서 알림 시간 설정
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis() //UTC 1970년 1월 1일 00:00:00.000 을 기준으로한 현제 시간의 차이를 long형으로 반환한다.
        }

        alarm.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + (time * 1000),
                pendingIntent
        )

        //부팅후에도 리시버를 받을 수 있게 만들기
        /*val receiver = ComponentName(requireActivity(), SampleBootReceiver::class.java)

        requireActivity().packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)*/
    }

    fun alarm_off (id:Int, param: String){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        getIntent(requireContext(), id, param, id).apply {
            alarmManager.cancel(this)
        }
    }
    private fun onTextClicked_windwheel() {
        if(Textflag_windwheel == 0) {
            windwheel_aster.setPaintFlags(windwheel_aster.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            windwheel_aster.setTextColor(Color.parseColor("#939597"))
            Textflag_windwheel = 1
            farming_afterhour_windwheel_aster.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            windwheel_aster.setPaintFlags(0);
            windwheel_aster.setTextColor(Color.parseColor("#000000"))
            Textflag_windwheel = 0
            farming_afterhour_windwheel_aster.text = ""
        }
    }
    private fun onTextClicked_tongtong() {
        if(Textflag_tongtong == 0) {
            tongtong.setPaintFlags(tongtong.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            tongtong.setTextColor(Color.parseColor("#939597"))
            Textflag_tongtong = 1
            farming_afterhour_tongtong.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            tongtong.setPaintFlags(0);
            tongtong.setTextColor(Color.parseColor("#000000"))
            Textflag_tongtong = 0
            farming_afterhour_tongtong.text = ""
        }
    }
    private fun onTextClicked_qingxin() {
        if(Textflag_qingxin == 0) {
            qingxin.setPaintFlags(qingxin.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            qingxin.setTextColor(Color.parseColor("#939597"))
            Textflag_qingxin = 1
            farming_afterhour_qingxin.text = current_time(48)

        }
        else{
            qingxin.setPaintFlags(0);
            qingxin.setTextColor(Color.parseColor("#000000"))
            Textflag_qingxin = 0
            farming_afterhour_qingxin.text = ""
        }
    }
    private fun onTextClicked_jueyun_chili() {
        if(Textflag_jueyun_chili == 0) {
            jueyun_chili.setPaintFlags(jueyun_chili.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            jueyun_chili.setTextColor(Color.parseColor("#939597"))
            Textflag_jueyun_chili = 1
            farming_afterhour_jueyun_chili.text = current_time(48)

        }
        else{
            jueyun_chili.setPaintFlags(0);
            jueyun_chili.setTextColor(Color.parseColor("#000000"))
            Textflag_jueyun_chili = 0
            farming_afterhour_jueyun_chili.text = ""
        }
    }
    private fun onTextClicked_yuri_zumeoni() {
        if(Textflag_yuri_zumeoni == 0) {
            yuri_zumeoni.setPaintFlags(yuri_zumeoni.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            yuri_zumeoni.setTextColor(Color.parseColor("#939597"))
            Textflag_yuri_zumeoni = 1
            farming_afterhour_yuri_zumeoni.text = current_time(48)

        }
        else{
            yuri_zumeoni.setPaintFlags(0);
            yuri_zumeoni.setTextColor(Color.parseColor("#000000"))
            Textflag_yuri_zumeoni = 0
            farming_afterhour_yuri_zumeoni.text = ""
        }
    }
    private fun onTextClicked_yuri_iliy() {
        if(Textflag_yuri_lily == 0) {
            yuri_lily.setPaintFlags(yuri_lily.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            yuri_lily.setTextColor(Color.parseColor("#939597"))
            Textflag_yuri_lily = 1
            farming_afterhour_yuri_iliy.text = current_time(48)
        }
        else{
            yuri_lily.setPaintFlags(0);
            yuri_lily.setTextColor(Color.parseColor("#000000"))
            Textflag_yuri_lily = 0
            farming_afterhour_yuri_iliy.text = ""
        }
    }
    private fun onTextClicked_yesang_flower() {
        if(Textflag_yesang_flower == 0) {
            yesang_flower.setPaintFlags(yesang_flower.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            yesang_flower.setTextColor(Color.parseColor("#939597"))
            Textflag_yesang_flower = 1
            farming_afterhour_yesang_flower.text = current_time(48)

        }
        else{
            yesang_flower.setPaintFlags(0);
            yesang_flower.setTextColor(Color.parseColor("#000000"))
            Textflag_yesang_flower = 0
            farming_afterhour_yesang_flower.text = ""
        }
    }
    private fun onTextClicked_cecilia() {
        if(Textflag_cecilia == 0) {
            cecilia.setPaintFlags(cecilia.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            cecilia.setTextColor(Color.parseColor("#939597"))
            Textflag_cecilia = 1
            farming_afterhour_cecilia.text = current_time(48)

        }
        else{
            cecilia.setPaintFlags(0);
            cecilia.setTextColor(Color.parseColor("#000000"))
            Textflag_cecilia = 0
            farming_afterhour_cecilia.text = ""
        }
    }
    private fun onTextClicked_mushroom() {
        if(Textflag_mushroom == 0) {
            mushroom.setPaintFlags(mushroom.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            mushroom.setTextColor(Color.parseColor("#939597"))
            Textflag_mushroom = 1
            farming_afterhour_mushroom.text = current_time(48)

        }
        else{
            mushroom.setPaintFlags(0);
            mushroom.setTextColor(Color.parseColor("#000000"))
            Textflag_mushroom = 0
            farming_afterhour_mushroom.text = ""
        }
    }
    private fun onTextClicked_dandelion_seed() {
        if(Textflag_dandelion_seed == 0) {
            dandelion_seed.setPaintFlags(dandelion_seed.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            dandelion_seed.setTextColor(Color.parseColor("#939597"))
            Textflag_dandelion_seed = 1
            farming_afterhour_dandelion_seed.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            dandelion_seed.setPaintFlags(0);
            dandelion_seed.setTextColor(Color.parseColor("#000000"))
            Textflag_dandelion_seed = 0
            farming_afterhour_dandelion_seed.text = ""
        }
    }
    private fun onTextClicked_small_lamp_grass() {
        if(Textflag_small_lamp_grass == 0) {
            small_lamp_grass.setPaintFlags(small_lamp_grass.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            small_lamp_grass.setTextColor(Color.parseColor("#939597"))
            Textflag_small_lamp_grass = 1
            farming_afterhour_small_lamp_grass.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            small_lamp_grass.setPaintFlags(0);
            small_lamp_grass.setTextColor(Color.parseColor("#000000"))
            Textflag_small_lamp_grass = 0
            farming_afterhour_small_lamp_grass.text = ""
        }
    }
    private fun onTextClicked_nakrak() {
        if(Textflag_nakrak == 0) {
            nakrak.setPaintFlags(nakrak.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            nakrak.setTextColor(Color.parseColor("#939597"))
            Textflag_nakrak = 1
            farming_afterhour_nakrak.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            nakrak.setPaintFlags(0);
            nakrak.setTextColor(Color.parseColor("#000000"))
            Textflag_nakrak = 0
            farming_afterhour_nakrak.text = ""
        }
    }
    private fun onTextClicked_gori_gori() {
        if(Textflag_gori_gori == 0) {
            gori_gori.setPaintFlags(gori_gori.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            gori_gori.setTextColor(Color.parseColor("#939597"))
            Textflag_gori_gori = 1
            farming_afterhour_gori_gori.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            gori_gori.setPaintFlags(0);
            gori_gori.setTextColor(Color.parseColor("#000000"))
            Textflag_gori_gori = 0
            farming_afterhour_gori_gori.text = ""
        }
    }
    private fun onTextClicked_crystal_chunk() {
        if(Textflag_crystal_chunk == 0) {
            crystal_chunk.setPaintFlags(crystal_chunk.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            crystal_chunk.setTextColor(Color.parseColor("#939597"))
            Textflag_crystal_chunk = 1
            farming_afterhour_crystal_chunk.text = current_time(72)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            crystal_chunk.setPaintFlags(0);
            crystal_chunk.setTextColor(Color.parseColor("#000000"))
            Textflag_crystal_chunk = 0
            farming_afterhour_crystal_chunk.text = ""
        }
    }
    private fun onTextClicked_electro_crystal() {
        if(Textflag_electro_crystal == 0) {
            electro_crystal.setPaintFlags(electro_crystal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            electro_crystal.setTextColor(Color.parseColor("#939597"))
            Textflag_electro_crystal = 1
            farming_afterhour_electro_crystal.text = current_time(48)

        }
        else{
            electro_crystal.setPaintFlags(0);
            electro_crystal.setTextColor(Color.parseColor("#000000"))
            Textflag_electro_crystal = 0
            farming_afterhour_electro_crystal.text = ""
        }
    }
    private fun onTextClicked_starsilver() {
        if(Textflag_starsilver == 0) {
            starsilver.setPaintFlags(starsilver.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            starsilver.setTextColor(Color.parseColor("#939597"))
            Textflag_starsilver = 1
            farming_afterhour_starsilver.text = current_time(48)
        }
        else{
            starsilver.setPaintFlags(0);
            starsilver.setTextColor(Color.parseColor("#000000"))
            Textflag_starsilver = 0
            farming_afterhour_starsilver.text = ""
        }
    }
    private fun onTextClicked_yabak() {
        if(Textflag_yabak == 0) {
            yabak.setPaintFlags(yabak.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            yabak.setTextColor(Color.parseColor("#939597"))
            Textflag_yabak = 1
            farming_afterhour_yabak.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            yabak.setPaintFlags(0);
            yabak.setTextColor(Color.parseColor("#000000"))
            Textflag_yabak = 0
            farming_afterhour_yabak.text = ""
        }
    }
    private fun onTextClicked_cor_lapis() {
        if(Textflag_cor_lapis == 0) {
            cor_lapis.setPaintFlags(cor_lapis.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            cor_lapis.setTextColor(Color.parseColor("#939597"))
            Textflag_cor_lapis = 1
            farming_afterhour_cor_lapis.text = current_time(48)

        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            cor_lapis.setPaintFlags(0);
            cor_lapis.setTextColor(Color.parseColor("#000000"))
            Textflag_cor_lapis = 0
            farming_afterhour_cor_lapis.text = ""
        }
    }
    private fun onTextClicked_white_iron_chunk() {
        if(Textflag_white_iron_chunk == 0) {
            white_iron_chunk.setPaintFlags(white_iron_chunk.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            white_iron_chunk.setTextColor(Color.parseColor("#939597"))
            Textflag_white_iron_chunk = 1
            farming_afterhour_white_iron_chunk.text = current_time(48)

        }
        else{
            white_iron_chunk.setPaintFlags(0);
            white_iron_chunk.setTextColor(Color.parseColor("#000000"))
            Textflag_white_iron_chunk = 0
            farming_afterhour_white_iron_chunk.text = ""
        }
    }
    private fun onTextClicked_ghost_pungdeng() {
        if(Textflag_ghost_pungdeng == 0) {
            ghost_pungdeng.setPaintFlags(ghost_pungdeng.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            ghost_pungdeng.setTextColor(Color.parseColor("#939597"))
            Textflag_ghost_pungdeng = 1
            farming_afterhour_ghost_pungdeng.text = current_time(48)

        }
        else{
            ghost_pungdeng.setPaintFlags(0);
            ghost_pungdeng.setTextColor(Color.parseColor("#000000"))
            Textflag_ghost_pungdeng = 0
            farming_afterhour_ghost_pungdeng.text = ""
        }
    }
    fun onTextClicked_iron_chunk(){
        if(Textflag_iron_chunk == 0) {
            iron_chunk.setPaintFlags(iron_chunk.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            iron_chunk.setTextColor(Color.parseColor("#939597"))
            Textflag_iron_chunk = 1
            farming_afterhour_iron_chunk.text = current_time(24)


        }
        //다시 클릭하면 취소선 지움. 이거 요일 바뀔 때 취소선 돼있으면 자동으로 지우는 기능 추가.
        else{
            iron_chunk.setPaintFlags(0);
            iron_chunk.setTextColor(Color.parseColor("#000000"))
            Textflag_iron_chunk = 0
            farming_afterhour_iron_chunk.text = ""
        }
    }
    private fun onTextClicked_crystal_golsu() {
        if(Textflag_crystal_golsu == 0) {
            crystal_golsu.setPaintFlags(crystal_golsu.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            crystal_golsu.setTextColor(Color.parseColor("#939597"))
            Textflag_crystal_golsu = 1
            farming_afterhour_crystal_golsu.text = current_time(48)

        }
        else{
            crystal_golsu.setPaintFlags(0);
            crystal_golsu.setTextColor(Color.parseColor("#000000"))
            Textflag_crystal_golsu = 0
            farming_afterhour_crystal_golsu.text = ""
        }
    }
    private fun onTextClicked_sanho_pearl() {
        if(Textflag_sanho_pearl == 0) {
            sanho_pearl.setPaintFlags(sanho_pearl.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            sanho_pearl.setTextColor(Color.parseColor("#939597"))
            Textflag_sanho_pearl = 1
            farming_afterhour_sanho_pearl.text = current_time(48)

        }
        else{
            sanho_pearl.setPaintFlags(0);
            sanho_pearl.setTextColor(Color.parseColor("#000000"))
            Textflag_sanho_pearl = 0
            farming_afterhour_sanho_pearl.text = ""
        }
    }
    private fun onTextClicked_cherry_blossom_sugu() {
        if(Textflag_cherry_blossom_sugu == 0) {
            cherry_blossom_sugu.setPaintFlags(cherry_blossom_sugu.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            cherry_blossom_sugu.setTextColor(Color.parseColor("#939597"))
            Textflag_cherry_blossom_sugu = 1
            farming_afterhour_cherry_blossom_sugu.text = current_time(48)

        }
        else{
            cherry_blossom_sugu.setPaintFlags(0);
            cherry_blossom_sugu.setTextColor(Color.parseColor("#000000"))
            Textflag_cherry_blossom_sugu = 0
            farming_afterhour_cherry_blossom_sugu.text = ""
        }
    }
    private fun onTextClicked_yulimple() {
        if(Textflag_yulimple == 0) {
            yulimple.setPaintFlags(yulimple.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            yulimple.setTextColor(Color.parseColor("#939597"))
            Textflag_yulimple = 1
            farming_afterhour_yulimple.text = current_time(48)

        }
        else{
            yulimple.setPaintFlags(0);
            yulimple.setTextColor(Color.parseColor("#000000"))
            Textflag_yulimple = 0
            farming_afterhour_yulimple.text = ""
        }
    }
    private fun onTextClicked_hyul_gok() {
        if(Textflag_hyul_gok == 0) {
            hyul_gok.setPaintFlags(hyul_gok.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            hyul_gok.setTextColor(Color.parseColor("#939597"))
            Textflag_hyul_gok = 1
            farming_afterhour_hyul_gok.text = current_time(48)

        }
        else{
            hyul_gok.setPaintFlags(0);
            hyul_gok.setTextColor(Color.parseColor("#000000"))
            Textflag_hyul_gok = 0
            farming_afterhour_hyul_gok.text = ""
        }
    }
    private fun onTextClicked_amakumocho() {
        if(Textflag_amakumocho == 0) {
            amakumocho.setPaintFlags(amakumocho.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            amakumocho.setTextColor(Color.parseColor("#939597"))
            Textflag_amakumocho = 1
            farming_afterhour_amakumocho.text = current_time(48)

        }
        else{
            amakumocho.setPaintFlags(0);
            amakumocho.setTextColor(Color.parseColor("#000000"))
            Textflag_amakumocho = 0
            farming_afterhour_amakumocho.text = ""
        }
    }
    private fun onTextClicked_sea_bulocho() {
        if(Textflag_sea_bulocho == 0) {
            sea_bulocho.setPaintFlags(sea_bulocho.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            sea_bulocho.setTextColor(Color.parseColor("#939597"))
            Textflag_sea_bulocho = 1
            farming_afterhour_sea_bulocho.text = current_time(48)

        }
        else{
            sea_bulocho.setPaintFlags(0);
            sea_bulocho.setTextColor(Color.parseColor("#000000"))
            Textflag_sea_bulocho = 0
            farming_afterhour_sea_bulocho.text = ""
        }
    }
    private fun onTextClicked_parametric_transformer() {
        if(Textflag_parametic_transformer == 0) {
            parametric_transformer.setPaintFlags(parametric_transformer.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            parametric_transformer.setTextColor(Color.parseColor("#939597"))
            Textflag_parametic_transformer = 1
            farming_afterhour_parametric_transformer.text = current_time(168)

        }
        else{
            parametric_transformer.setPaintFlags(0);
            parametric_transformer.setTextColor(Color.parseColor("#000000"))
            Textflag_parametic_transformer = 0
            farming_afterhour_parametric_transformer.text = ""
        }
    }
    private fun onTextClicked_fish_mond() {
        if(Textflag_fish_mond == 0) {
            fish_mond.setPaintFlags(fish_mond.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            fish_mond.setTextColor(Color.parseColor("#939597"))
            Textflag_fish_mond = 1
            farming_afterhour_fish_mond.text = current_time(72)

        }
        else{
            fish_mond.setPaintFlags(0);
            fish_mond.setTextColor(Color.parseColor("#000000"))
            Textflag_fish_mond = 0
            farming_afterhour_fish_mond.text = ""
        }
    }
    private fun onTextClicked_fish_liyue() {
        if(Textflag_fish_liyue == 0) {
            fish_liyue.setPaintFlags(fish_liyue.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            fish_liyue.setTextColor(Color.parseColor("#939597"))
            Textflag_fish_liyue = 1
            farming_afterhour_fish_liyue.text = current_time(72)

        }
        else{
            fish_liyue.setPaintFlags(0);
            fish_liyue.setTextColor(Color.parseColor("#000000"))
            Textflag_fish_liyue = 0
            farming_afterhour_fish_liyue.text = ""
        }
    }
    private fun onTextClicked_fish_inazuma() {
        if(Textflag_fish_inazuma == 0) {
            fish_inazuma.setPaintFlags(fish_inazuma.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            fish_inazuma.setTextColor(Color.parseColor("#939597"))
            Textflag_fish_inazuma = 1
            farming_afterhour_fish_inazuma.text = current_time(72)

        }
        else{
            fish_inazuma.setPaintFlags(0);
            fish_inazuma.setTextColor(Color.parseColor("#000000"))
            Textflag_fish_inazuma = 0
            farming_afterhour_fish_inazuma.text = ""
        }
    }
    private fun onTextClicked_amethyst_lump() {
        if(Textflag_amethyst_lump == 0) {
            amethyst_lump.setPaintFlags(amethyst_lump.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            amethyst_lump.setTextColor(Color.parseColor("#939597"))
            Textflag_amethyst_lump = 1
            farming_afterhour_amethyst_lump.text = current_time(72)
        }
        else{
            amethyst_lump.setPaintFlags(0);
            amethyst_lump.setTextColor(Color.parseColor("#000000"))
            Textflag_amethyst_lump = 0
            farming_afterhour_amethyst_lump.text = ""
        }
    }
    private fun onTextClicked_fluorescent_fungus() {
        if(Textflag_fluorescent_fungus == 0) {
            fluorescent_fungus.setPaintFlags(fluorescent_fungus.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            fluorescent_fungus.setTextColor(Color.parseColor("#939597"))
            Textflag_fluorescent_fungus = 1
            farming_afterhour_fluorescent_fungus.text = current_time(48)
        }
        else{
            fluorescent_fungus.setPaintFlags(0);
            fluorescent_fungus.setTextColor(Color.parseColor("#000000"))
            Textflag_fluorescent_fungus = 0
            farming_afterhour_fluorescent_fungus.text = ""
        }
    }
    private fun onTextClicked_hena_berry() {
        if(Textflag_hena_berry == 0) {
            hena_berry.setPaintFlags(hena_berry.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            hena_berry.setTextColor(Color.parseColor("#939597"))
            Textflag_hena_berry = 1
            farming_afterhour_hena_berry.text = current_time(48)

        }
        else{
            hena_berry.setPaintFlags(0);
            hena_berry.setTextColor(Color.parseColor("#000000"))
            Textflag_hena_berry = 0
            farming_afterhour_hena_berry.text = ""
        }
    }
    private fun onTextClicked_kalpalata_lotus() {
        if(Textflag_kalpalata_lotus == 0) {
            kalpalata_lotus.setPaintFlags(kalpalata_lotus.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            kalpalata_lotus.setTextColor(Color.parseColor("#939597"))
            Textflag_kalpalata_lotus = 1
            farming_afterhour_kalpalata_lotus.text = current_time(48)

        }
        else{
            kalpalata_lotus.setPaintFlags(0);
            kalpalata_lotus.setTextColor(Color.parseColor("#000000"))
            Textflag_kalpalata_lotus = 0
            farming_afterhour_kalpalata_lotus.text = ""
        }
    }
    private fun onTextClicked_mourning_flower() {
        if(Textflag_mourning_flower == 0) {
            mourning_flower.setPaintFlags(mourning_flower.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            mourning_flower.setTextColor(Color.parseColor("#939597"))
            Textflag_mourning_flower = 1
            farming_afterhour_mourning_flower.text = current_time(48)

        }
        else{
            mourning_flower.setPaintFlags(0);
            mourning_flower.setTextColor(Color.parseColor("#000000"))
            Textflag_mourning_flower = 0
            farming_afterhour_mourning_flower.text = ""
        }
    }
    private fun onTextClicked_nilotpala_lotus() {
        if(Textflag_nilotpala_lotus == 0) {
            nilotpala_lotus.setPaintFlags(nilotpala_lotus.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            nilotpala_lotus.setTextColor(Color.parseColor("#939597"))
            Textflag_nilotpala_lotus = 1
            farming_afterhour_nilotpala_lotus.text = current_time(48)

        }
        else{
            nilotpala_lotus.setPaintFlags(0);
            nilotpala_lotus.setTextColor(Color.parseColor("#000000"))
            Textflag_nilotpala_lotus = 0
            farming_afterhour_nilotpala_lotus.text = ""
        }
    }
    private fun onTextClicked_padisarah() {
        if(Textflag_padisarah == 0) {
            padisarah.setPaintFlags(padisarah.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            padisarah.setTextColor(Color.parseColor("#939597"))
            Textflag_padisarah = 1
            farming_afterhour_padisarah.text = current_time(48)

        }
        else{
            padisarah.setPaintFlags(0);
            padisarah.setTextColor(Color.parseColor("#000000"))
            Textflag_padisarah = 0
            farming_afterhour_padisarah.text = ""
        }
    }
    private fun onTextClicked_rukkhashava_mushrooms() {
        if(Textflag_rukkhashava_mushrooms == 0) {
            rukkhashava_mushrooms.setPaintFlags(rukkhashava_mushrooms.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            rukkhashava_mushrooms.setTextColor(Color.parseColor("#939597"))
            Textflag_rukkhashava_mushrooms = 1
            farming_afterhour_rukkhashava_mushrooms.text = current_time(48)

        }
        else{
            rukkhashava_mushrooms.setPaintFlags(0);
            rukkhashava_mushrooms.setTextColor(Color.parseColor("#000000"))
            Textflag_rukkhashava_mushrooms = 0
            farming_afterhour_rukkhashava_mushrooms.text = ""
        }
    }
    private fun onTextClicked_sand_grease_pupa() {
        if(Textflag_sand_grease_pupa == 0) {
            sand_grease_pupa.setPaintFlags(sand_grease_pupa.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            sand_grease_pupa.setTextColor(Color.parseColor("#939597"))
            Textflag_rukkhashava_mushrooms = 1
            farming_afterhour_sand_grease_pupa.text = current_time(48)

        }
        else{
            sand_grease_pupa.setPaintFlags(0);
            sand_grease_pupa.setTextColor(Color.parseColor("#000000"))
            Textflag_sand_grease_pupa = 0
            farming_afterhour_sand_grease_pupa.text = ""
        }
    }
    private fun onTextClicked_scarab() {
        if(Textflag_scarab == 0) {
            scarab.setPaintFlags(scarab.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            scarab.setTextColor(Color.parseColor("#939597"))
            Textflag_scarab = 1
            farming_afterhour_scarab.text = current_time(48)

        }
        else{
            scarab.setPaintFlags(0);
            scarab.setTextColor(Color.parseColor("#000000"))
            Textflag_scarab = 0
            farming_afterhour_scarab.text = ""
        }
    }
    private fun onTextClicked_trishiraite() {
        if(Textflag_trishiraite == 0) {
            trishiraite.setPaintFlags(trishiraite.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            trishiraite.setTextColor(Color.parseColor("#939597"))
            Textflag_trishiraite = 1
            farming_afterhour_trishiraite.text = current_time(48)

        }
        else{
            trishiraite.setPaintFlags(0);
            trishiraite.setTextColor(Color.parseColor("#000000"))
            Textflag_trishiraite = 0
            farming_afterhour_trishiraite.text = ""
        }
    }


    fun current_time(material : Int): String {
        val instance = Calendar.getInstance()
        val year = instance.get(Calendar.YEAR)
        val month = (instance.get(Calendar.MONTH) + 1)
        val date = instance.get(Calendar.DATE)
        val hour = instance.get(Calendar.HOUR_OF_DAY)
        val minute = instance.get(Calendar.MINUTE)

        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) //31일까지
        {
            if(date == 31)
            {
                monthStr = "${month+1} / "
                if(material == 24) dateStr = "1 "
                if(material == 48) dateStr = "2 "
                if(material == 72) dateStr = "3 "
                if(material == 168) dateStr = "7 "
            }
            else
            {
                monthStr = "${month} / "
                if(material == 24) dateStr = "${date+1} "

                if(material == 48) {
                    if(date == 30) {
                        monthStr = "${month + 1} / "
                        dateStr = "1 "
                    }
                    else
                        dateStr = "${date+2} "
                }

                if (material == 72){
                    if(date == 29){
                        monthStr = "${month + 1} / "
                        dateStr = "1 "
                    }
                    else if (date == 30){
                        monthStr = "${month + 1} / "
                        dateStr = "2 "
                    }

                    else dateStr = "${date+3} "
                }

                if(material == 168){
                    if(date == 25){
                        monthStr = "${month + 1} / "
                        dateStr = "1 "
                    }

                    else if (date == 26){
                        monthStr = "${month + 1} / "
                        dateStr = "2 "
                    }

                    else if (date == 27){
                        monthStr = "${month + 1} / "
                        dateStr = "3 "
                    }

                    else if (date == 28){
                        monthStr = "${month + 1} / "
                        dateStr = "4 "
                    }

                    else if (date == 29){
                        monthStr = "${month + 1} / "
                        dateStr = "5 "
                    }

                    else if (date == 30){
                        monthStr = "${month + 1} / "
                        dateStr = "6 "
                    }
                    else dateStr = "${date+7} "
                }

            }
        }

        if(month == 4 || month == 6 || month == 9 || month == 11) //30일까지
            {
                if(date == 30)
                {
                    monthStr = "${month+1} / "
                    if(material == 24) dateStr = "1 "
                    if(material == 48) dateStr = "2 "
                    if(material == 72) dateStr = "3 "
                    if(material == 168) dateStr = "7 "
                }
                else
                {
                    monthStr = "${month} / "
                    if(material == 24) dateStr = "${date+1} "

                    if(material == 48) {
                        if(date == 29) {
                            monthStr = "${month + 1} / "
                            dateStr = "1 "
                        }
                        else
                            dateStr = "${date+2} "
                    }

                    if (material == 72){
                        if(date == 28){
                            monthStr = "${month + 1} / "
                            dateStr = "1 "
                        }
                        else if (date == 29){
                            monthStr = "${month + 1} / "
                            dateStr = "2 "
                        }

                        else dateStr = "${date+3} "
                    }

                    if(material == 168){
                        Log.d("month", dateStr)
                        Log.d("date", date.toString())
                        if(date == 24){
                            monthStr = "${month + 1} / "
                            dateStr = "1 "
                        }

                        else if (date == 25){
                            monthStr = "${month + 1} / "
                            dateStr = "2 "
                        }

                        else if (date == 26){
                            Log.d("26","executed?")
                            monthStr = "${month + 1} / "
                            dateStr = "3 "
                        }

                        else if (date == 27){
                            Log.d("month-after", "not here?")
                            monthStr = "${month + 1} / "
                            dateStr = "4 "
                        }

                        else if (date == 28){
                            monthStr = "${month + 1} / "
                            dateStr = "5 "
                        }

                        else if (date == 29){
                            monthStr = "${month + 1} / "
                            dateStr = "6 "
                        }

                        else{
                            Log.d("month-after", "are you here?")
                            dateStr = "${date+7} "
                        }

                        Log.d("month-after", dateStr)
                    }
                }
            }

        if(month == 2)
        {
            if(date == 28)
            {
                monthStr = "${month+1} / "
                if(material == 24) dateStr = "1 "
                if(material == 48) dateStr = "2 "
                if(material == 72) dateStr = "3 "
                if(material == 168) dateStr = "7 "
            }
            else
            {
                monthStr = "${month} / "
                if(material == 24) dateStr = "${date+1} "

                if(material == 48) {
                    if(date == 27) {
                        monthStr = "${month + 1} / "
                        dateStr = "1 "
                    }
                    else
                        dateStr = "${date+2} "
                }

                if (material == 72){
                    if(date == 26){
                        monthStr = "${month + 1} / "
                        dateStr = "1 "
                    }
                    else if (date == 27){
                        monthStr = "${month + 1} / "
                        dateStr = "2 "
                    }

                    else dateStr = "${date+3} "
                }

                if(material == 168){
                    if(date == 22){
                        monthStr = "${month + 1} / "
                        dateStr = "1 "
                    }

                    else if (date == 23){
                        monthStr = "${month + 1} / "
                        dateStr = "2 "
                    }

                    else if (date == 24){
                        monthStr = "${month + 1} / "
                        dateStr = "3 "
                    }

                    else if (date == 25){
                        monthStr = "${month + 1} / "
                        dateStr = "4 "
                    }

                    else if (date == 26){
                        monthStr = "${month + 1} / "
                        dateStr = "5 "
                    }

                    else if (date == 27){
                        monthStr = "${month + 1} / "
                        dateStr = "6 "
                    }

                    else dateStr = "${date+7} "
                }
            }
        }

//        when(material)
//        {
//            24 -> dateStr = "${date+1}일 "
//            48 -> dateStr = "${date+2}일 "
//            72 -> dateStr = "${date+3}일 "
//        }

        when(hour)
        {
            in 1..11 -> hourStr = " AM${hour} : "
            in 13..23 -> hourStr = " PM${hour-12} : "
            12 -> hourStr =" PM${hour} : "
            0 -> hourStr =" AM${hour} : "
            24 -> hourStr =" AM${hour} : "
        }


        minStr = "${minute}"

        return  monthStr + dateStr + hourStr + minStr
    }

    private fun saveData(){ //값하고 지우는 행동까지 저장해보자.
        val pref = requireActivity().getSharedPreferences("pref", 0)
        val edit = pref.edit() //수정모드
        edit.putInt("iron_chunk_flag", Textflag_iron_chunk)
        edit.putInt("iron_chunk_status", iron_chunk.paintFlags)
        edit.putString("iron_chunk_aftertime", farming_afterhour_iron_chunk.text as String?)
        edit.putInt("white_iron_chunk_flag", Textflag_white_iron_chunk)
        edit.putInt("white_iron_chunk_status", white_iron_chunk.paintFlags)
        edit.putString("white_iron_chunk_aftertime", farming_afterhour_white_iron_chunk.text as String?)
        edit.putInt("cor_lapis_flag", Textflag_cor_lapis)
        edit.putInt("cor_lapis_status", cor_lapis.paintFlags)
        edit.putString("cor_lapis_aftertime", farming_afterhour_cor_lapis.text as String?)
        edit.putInt("yabak_flag", Textflag_yabak)
        edit.putInt("yabak_status", yabak.paintFlags)
        edit.putString("yabak_aftertime", farming_afterhour_yabak.text as String?)
        edit.putInt("starsilver_flag", Textflag_starsilver)
        edit.putInt("starsilver_status", starsilver.paintFlags)
        edit.putString("starsilver_aftertime", farming_afterhour_starsilver.text as String?)
        edit.putInt("electro_crystal_flag", Textflag_electro_crystal)
        edit.putInt("electro_crystal_status", electro_crystal.paintFlags)
        edit.putString("electro_crystal_aftertime", farming_afterhour_electro_crystal.text as String?)
        edit.putInt("crystal_chunk_flag", Textflag_crystal_chunk)
        edit.putInt("crystal_chunk_status", crystal_chunk.paintFlags)
        edit.putString("crystal_chunk_aftertime", farming_afterhour_crystal_chunk.text as String?)
        edit.putInt("amethyst_lump", Textflag_amethyst_lump)
        edit.putInt("amethyst_lump_status", amethyst_lump.paintFlags)
        edit.putString("amethyst_lump_aftertime", farming_afterhour_amethyst_lump.text as String?)
        edit.putInt("gori_gori_flag", Textflag_gori_gori)
        edit.putInt("gori_gori_status", gori_gori.paintFlags)
        edit.putString("gori_gori_aftertime", farming_afterhour_gori_gori.text as String?)
        edit.putInt("nakrak_flag", Textflag_nakrak)
        edit.putInt("nakrak_status", nakrak.paintFlags)
        edit.putString("nakrak_aftertime", farming_afterhour_nakrak.text as String?)
        edit.putInt("small_lamp_grass_flag", Textflag_small_lamp_grass)
        edit.putInt("small_lamp_grass_status", small_lamp_grass.paintFlags)
        edit.putString("small_lamp_grass_aftertime", farming_afterhour_small_lamp_grass.text as String?)
        edit.putInt("dandelion_seed_flag", Textflag_dandelion_seed)
        edit.putInt("dandelion_seed_status", dandelion_seed.paintFlags)
        edit.putString("dandelion_seed_aftertime", farming_afterhour_dandelion_seed.text as String?)
        edit.putInt("mushroom_flag", Textflag_mushroom)
        edit.putInt("mushroom_status", mushroom.paintFlags)
        edit.putString("mushroom_aftertime", farming_afterhour_mushroom.text as String?)
        edit.putInt("cecilia_flag", Textflag_cecilia)
        edit.putInt("cecilia_status", cecilia.paintFlags)
        edit.putString("cecilia_aftertime", farming_afterhour_cecilia.text as String?)
        edit.putInt("yesang_flower_flag", Textflag_yesang_flower)
        edit.putInt("yesang_flower_status", yesang_flower.paintFlags)
        edit.putString("yesang_flower_aftertime", farming_afterhour_yesang_flower.text as String?)
        edit.putInt("yuri_lily_flag", Textflag_yuri_lily)
        edit.putInt("yuri_lily_status", yuri_lily.paintFlags)
        edit.putString("yuri_lily_aftertime", farming_afterhour_yuri_iliy.text as String?)
        edit.putInt("yuri_zumeoni_flag", Textflag_yuri_zumeoni)
        edit.putInt("yuri_zumeoni_status", yuri_zumeoni.paintFlags)
        edit.putString("yuri_zumeoni_aftertime", farming_afterhour_yuri_zumeoni.text as String?)
        edit.putInt("jueyun_chili_flag", Textflag_jueyun_chili)
        edit.putInt("jueyun_chili_status", jueyun_chili.paintFlags)
        edit.putString("jueyun_chili_aftertime", farming_afterhour_jueyun_chili.text as String?)
        edit.putInt("qingxin_flag", Textflag_qingxin)
        edit.putInt("qingxin_status", qingxin.paintFlags)
        edit.putString("qingxin_aftertime", farming_afterhour_qingxin.text as String?)
        edit.putInt("tongtong_flag", Textflag_tongtong)
        edit.putInt("tongtong_status", tongtong.paintFlags)
        edit.putString("tongtong_aftertime", farming_afterhour_tongtong.text as String?)
        edit.putInt("windwheel_flag", Textflag_windwheel)
        edit.putInt("windwheel_status", windwheel_aster.paintFlags)
        edit.putString("windwheel_aftertime", farming_afterhour_windwheel_aster.text as String?)
        edit.putInt("ghost_pungdeng_flag", Textflag_ghost_pungdeng)
        edit.putInt("ghost_pungdeng_status", ghost_pungdeng.paintFlags)
        edit.putString("ghost_pungdeng_aftertime", farming_afterhour_ghost_pungdeng.text as String?)
        edit.putInt("crystal_golsu_flag", Textflag_crystal_golsu)
        edit.putInt("crystal_golsu_status", crystal_golsu.paintFlags)
        edit.putString("crystal_golsu_aftertime", farming_afterhour_crystal_golsu.text as String?)
        edit.putInt("cherry_blossom_sugu_flag", Textflag_cherry_blossom_sugu)
        edit.putInt("cherry_blossom_sugu_status", cherry_blossom_sugu.paintFlags)
        edit.putString("cherry_blossom_sugu_aftertime", farming_afterhour_cherry_blossom_sugu.text as String?)
        edit.putInt("hyul_gok_flag", Textflag_hyul_gok)
        edit.putInt("hyul_gok_status", hyul_gok.paintFlags)
        edit.putString("hyul_gok_aftertime", farming_afterhour_hyul_gok.text as String?)
        edit.putInt("yulimple_flag", Textflag_yulimple)
        edit.putInt("yulimple_status", yulimple.paintFlags)
        edit.putString("yulimple_aftertime", farming_afterhour_yulimple.text as String?)
        edit.putInt("sea_bulocho_flag", Textflag_sea_bulocho)
        edit.putInt("sea_bulocho_status", sea_bulocho.paintFlags)
        edit.putString("sea_bulocho_aftertime", farming_afterhour_sea_bulocho.text as String?)
        edit.putInt("sanho_pearl_flag", Textflag_sanho_pearl)
        edit.putInt("sanho_pearl_status", sanho_pearl.paintFlags)
        edit.putString("sanho_pearl_aftertime", farming_afterhour_sanho_pearl.text as String?)
        edit.putInt("amakumocho_flag", Textflag_amakumocho)
        edit.putInt("amakumocho_status", amakumocho.paintFlags)
        edit.putString("amakumocho_aftertime", farming_afterhour_amakumocho.text as String?)
        edit.putInt("parametric_transformer_flag", Textflag_parametic_transformer)
        edit.putInt("parametric_transformer_status", parametric_transformer.paintFlags)
        edit.putString("parametric_transformer_aftertime", farming_afterhour_parametric_transformer.text as String?)
        edit.putInt("fish_mond_flag", Textflag_fish_mond)
        edit.putInt("fish_mond_status", fish_mond.paintFlags)
        edit.putString("fish_mond_aftertime", farming_afterhour_fish_mond.text as String?)
        edit.putInt("fish_liyue_flag", Textflag_fish_liyue)
        edit.putInt("fish_liyue_status", fish_liyue.paintFlags)
        edit.putString("fish_liyue_aftertime", farming_afterhour_fish_liyue.text as String?)
        edit.putInt("fish_inazuma_flag", Textflag_fish_inazuma)
        edit.putInt("fish_inazuma_status", fish_inazuma.paintFlags)
        edit.putString("fish_inazuma_aftertime", farming_afterhour_fish_inazuma.text as String?)
        edit.putInt("fish_inazuma_flag", Textflag_fish_inazuma)
        edit.putInt("fish_inazuma_status", fish_inazuma.paintFlags)
        edit.putString("fish_inazuma_aftertime", farming_afterhour_fish_inazuma.text as String?)
        edit.putInt("fluorescent_fungus_flag", Textflag_fluorescent_fungus)
        edit.putInt("fluorescent_fungus_status", fluorescent_fungus.paintFlags)
        edit.putString("fluorescent_fungus_aftertime", farming_afterhour_fluorescent_fungus.text as String?)
        edit.putInt("hena_berry_flag", Textflag_hena_berry)
        edit.putInt("hena_berry_status", hena_berry.paintFlags)
        edit.putString("hena_berry_aftertime", farming_afterhour_hena_berry.text as String?)
        edit.putInt("kalpalata_lotus_flag", Textflag_kalpalata_lotus)
        edit.putInt("kalpalata_lotus_status", kalpalata_lotus.paintFlags)
        edit.putString("kalpalata_lotus_aftertime", farming_afterhour_kalpalata_lotus.text as String?)
        edit.putInt("mourning_flower_flag", Textflag_mourning_flower)
        edit.putInt("mourning_flower_status", mourning_flower.paintFlags)
        edit.putString("mourning_flower_aftertime", farming_afterhour_mourning_flower.text as String?)
        edit.putInt("nilotpala_lotus_flag", Textflag_nilotpala_lotus)
        edit.putInt("nilotpala_lotus_status", nilotpala_lotus.paintFlags)
        edit.putString("nilotpala_lotus_aftertime", farming_afterhour_nilotpala_lotus.text as String?)
        edit.putInt("padisarah_flag", Textflag_padisarah)
        edit.putInt("padisarah_status", padisarah.paintFlags)
        edit.putString("padisarah_aftertime", farming_afterhour_padisarah.text as String?)
        edit.putInt("rukkhashava_mushrooms_flag", Textflag_rukkhashava_mushrooms)
        edit.putInt("rukkhashava_mushrooms_status", rukkhashava_mushrooms.paintFlags)
        edit.putString("rukkhashava_mushrooms_aftertime", farming_afterhour_rukkhashava_mushrooms.text as String?)
        edit.putInt("sand_grease_pupa_flag", Textflag_sand_grease_pupa)
        edit.putInt("sand_grease_pupa_status", sand_grease_pupa.paintFlags)
        edit.putString("sand_grease_pupa_aftertime", farming_afterhour_sand_grease_pupa.text as String?)
        edit.putInt("scarab_flag", Textflag_scarab)
        edit.putInt("scarab_status", scarab.paintFlags)
        edit.putString("scarab_aftertime", farming_afterhour_scarab.text as String?)
        edit.putInt("trishiraite_flag", Textflag_trishiraite)
        edit.putInt("trishiraite_status", trishiraite.paintFlags)
        edit.putString("trishiraite_aftertime", farming_afterhour_trishiraite.text as String?)
        edit.putBoolean("farming_switch_isChecked", farming_switch.isChecked)
//        edit.putString("weekly_flag", weeklysavedday)
//        edit.putString("weekly_date_flag", weeklysaveddate)
        edit.apply()
    }

    data class Item(val key: String, var flag: Int, val view: TextView, var afterhour: TextView)

    private fun loadData() {
        val pref = requireActivity().getSharedPreferences("pref", 0)

        isPurchasedRemoveAds = pref.getBoolean("isPurchasedRemoveAds", isPurchasedRemoveAds) //광고제거구매여부

        val items = arrayOf(
            Item("iron_chunk", Textflag_iron_chunk, iron_chunk, farming_afterhour_iron_chunk),
            Item("white_iron_chunk", Textflag_white_iron_chunk, white_iron_chunk, farming_afterhour_white_iron_chunk),
            Item("cor_lapis", Textflag_cor_lapis, cor_lapis, farming_afterhour_cor_lapis),
            Item("yabak", Textflag_yabak, yabak, farming_afterhour_yabak),
            Item("starsilver", Textflag_starsilver, starsilver, farming_afterhour_starsilver),
            Item("electro_crystal", Textflag_electro_crystal, electro_crystal, farming_afterhour_electro_crystal),
            Item("crystal_chunk", Textflag_crystal_chunk, crystal_chunk, farming_afterhour_crystal_chunk),
            Item("amethyst_lump", Textflag_amethyst_lump, amethyst_lump, farming_afterhour_amethyst_lump),
            Item("gori_gori", Textflag_gori_gori, gori_gori, farming_afterhour_gori_gori),
            Item("nakrak", Textflag_nakrak, nakrak, farming_afterhour_nakrak),
            Item("small_lamp_grass", Textflag_small_lamp_grass, small_lamp_grass, farming_afterhour_small_lamp_grass),
            Item("dandelion_seed", Textflag_dandelion_seed, dandelion_seed, farming_afterhour_dandelion_seed),
            Item("mushroom", Textflag_mushroom, mushroom, farming_afterhour_mushroom),
            Item("cecilia", Textflag_cecilia, cecilia, farming_afterhour_cecilia),
            Item("yesang_flower", Textflag_yesang_flower, yesang_flower, farming_afterhour_yesang_flower),
            Item("yuri_lily", Textflag_yuri_lily, yuri_lily, farming_afterhour_yuri_iliy),
            Item("yuri_zumeoni", Textflag_yuri_zumeoni, yuri_zumeoni, farming_afterhour_yuri_zumeoni),
            Item("jueyun_chili", Textflag_jueyun_chili, jueyun_chili, farming_afterhour_jueyun_chili),
            Item("qingxin", Textflag_qingxin, qingxin, farming_afterhour_qingxin),
            Item("tongtong", Textflag_tongtong, tongtong, farming_afterhour_tongtong),
            Item("windwheel", Textflag_windwheel, windwheel_aster, farming_afterhour_windwheel_aster),
            Item("crystal_chunk", Textflag_crystal_chunk, crystal_chunk, farming_afterhour_crystal_chunk),
            Item("ghost_pungdeng", Textflag_ghost_pungdeng, ghost_pungdeng, farming_afterhour_ghost_pungdeng),
            Item("cherry_blossom_sugu", Textflag_cherry_blossom_sugu, cherry_blossom_sugu, farming_afterhour_cherry_blossom_sugu),
            Item("crystal_golsu", Textflag_crystal_golsu, crystal_golsu, farming_afterhour_crystal_golsu),
            Item("hyul_gok", Textflag_hyul_gok, hyul_gok, farming_afterhour_hyul_gok),
            Item("yulimple", Textflag_yulimple, yulimple, farming_afterhour_yulimple),
            Item("sea_bulocho", Textflag_sea_bulocho, sea_bulocho, farming_afterhour_sea_bulocho),
            Item("sanho_pearl", Textflag_sanho_pearl, sanho_pearl, farming_afterhour_sanho_pearl),
            Item("amakumocho", Textflag_amakumocho, amakumocho, farming_afterhour_amakumocho),
            Item("parametric_transformer", Textflag_parametic_transformer, parametric_transformer, farming_afterhour_parametric_transformer),
            Item("fish_mond", Textflag_fish_mond, fish_mond, farming_afterhour_fish_mond),
            Item("fish_liyue", Textflag_fish_liyue, fish_liyue, farming_afterhour_fish_liyue),
            Item("fish_inazuma", Textflag_fish_inazuma, fish_inazuma, farming_afterhour_fish_inazuma),
            Item("fluorescent_fungus", Textflag_fluorescent_fungus, fluorescent_fungus, farming_afterhour_fluorescent_fungus),
            Item("hena_berry", Textflag_hena_berry, hena_berry, farming_afterhour_hena_berry),
            Item("kalpalata_lotus", Textflag_kalpalata_lotus, kalpalata_lotus, farming_afterhour_kalpalata_lotus),
            Item("mourning_flower", Textflag_mourning_flower, mourning_flower, farming_afterhour_mourning_flower),
            Item("nilotpala_lotus", Textflag_nilotpala_lotus, nilotpala_lotus, farming_afterhour_nilotpala_lotus),
            Item("padisarah", Textflag_padisarah, padisarah, farming_afterhour_padisarah),
            Item("rukkhashava_mushrooms", Textflag_rukkhashava_mushrooms, rukkhashava_mushrooms, farming_afterhour_rukkhashava_mushrooms),
            Item("sand_grease_pupa", Textflag_sand_grease_pupa, sand_grease_pupa, farming_afterhour_sand_grease_pupa),
            Item("scarab", Textflag_scarab, scarab, farming_afterhour_scarab),
            Item("trishiraite", Textflag_trishiraite, trishiraite, farming_afterhour_trishiraite),
            Item("parametric_transformer", Textflag_parametic_transformer, parametric_transformer, farming_afterhour_parametric_transformer)
            )

        for (item in items) {
            item.flag = pref.getInt("${item.key}_flag", 0)
            item.view.paintFlags = pref.getInt("${item.key}_status", 0)
            item.afterhour.text = pref.getString("${item.key}_aftertime", "")
            if (item.flag == 1) {
                item.view.setTextColor(Color.parseColor("#939597"))
            }
        }


//        Textflag_iron_chunk = pref.getInt("iron_chunk_flag", 0)
//        iron_chunk.paintFlags = pref.getInt("iron_chunk_status", 0)
//        farming_afterhour_iron_chunk.text = pref.getString("iron_chunk_aftertime", "")
//        if(Textflag_iron_chunk == 1) {
//            iron_chunk.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_white_iron_chunk = pref.getInt("white_iron_chunk_flag", 0)
//        white_iron_chunk.paintFlags = pref.getInt("white_iron_chunk_status", 0)
//        farming_afterhour_white_iron_chunk.text = pref.getString("white_iron_chunk_aftertime", "")
//        if(Textflag_white_iron_chunk == 1){
//            white_iron_chunk.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_cor_lapis = pref.getInt("cor_lapis_flag", 0)
//        cor_lapis.paintFlags = pref.getInt("cor_lapis_status", 0)
//        farming_afterhour_cor_lapis.text = pref.getString("cor_lapis_aftertime", "")
//        if(Textflag_cor_lapis == 1){
//            cor_lapis.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_yabak = pref.getInt("yabak_flag", 0)
//        yabak.paintFlags = pref.getInt("yabak_status", 0)
//        farming_afterhour_yabak.text = pref.getString("yabak_aftertime", "")
//        if(Textflag_yabak == 1){
//            yabak.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_starsilver = pref.getInt("starsilver_flag", 0)
//        starsilver.paintFlags = pref.getInt("starsilver_status", 0)
//        farming_afterhour_starsilver.text = pref.getString("starsilver_aftertime", "")
//        if(Textflag_starsilver == 1){
//            starsilver.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_electro_crystal = pref.getInt("electro_crystal_flag", 0)
//        electro_crystal.paintFlags = pref.getInt("electro_crystal_status", 0)
//        farming_afterhour_electro_crystal.text = pref.getString("electro_crystal_aftertime", "")
//        if(Textflag_electro_crystal == 1){
//            electro_crystal.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_crystal_chunk = pref.getInt("crystal_chunk_flag", 0)
//        crystal_chunk.paintFlags = pref.getInt("crystal_chunk_status", 0)
//        farming_afterhour_crystal_chunk.text = pref.getString("crystal_chunk_aftertime", "")
//        if(Textflag_crystal_chunk == 1){
//            crystal_chunk.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_gori_gori = pref.getInt("gori_gori_flag", 0)
//        gori_gori.paintFlags = pref.getInt("gori_gori_status", 0)
//        farming_afterhour_gori_gori.text = pref.getString("gori_gori_aftertime", "")
//        if(Textflag_gori_gori == 1){
//            gori_gori.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_nakrak = pref.getInt("nakrak_flag", 0)
//        nakrak.paintFlags = pref.getInt("nakrak_status", 0)
//        farming_afterhour_nakrak.text = pref.getString("nakrak_aftertime", "")
//        if(Textflag_nakrak == 1){
//            nakrak.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_small_lamp_grass = pref.getInt("small_lamp_grass_flag", 0)
//        small_lamp_grass.paintFlags = pref.getInt("small_lamp_grass_status", 0)
//        farming_afterhour_small_lamp_grass.text = pref.getString("small_lamp_grass_aftertime", "")
//        if(Textflag_small_lamp_grass == 1){
//            small_lamp_grass.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_dandelion_seed = pref.getInt("dandelion_seed_flag", 0)
//        dandelion_seed.paintFlags = pref.getInt("dandelion_seed_status", 0)
//        farming_afterhour_dandelion_seed.text = pref.getString("dandelion_seed_aftertime", "")
//        if(Textflag_dandelion_seed == 1){
//            dandelion_seed.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_mushroom = pref.getInt("mushroom_flag", 0)
//        mushroom.paintFlags = pref.getInt("mushroom_status", 0)
//        farming_afterhour_mushroom.text = pref.getString("mushroom_aftertime", "")
//        if(Textflag_mushroom == 1){
//            mushroom.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_cecilia = pref.getInt("cecilia_flag", 0)
//        cecilia.paintFlags = pref.getInt("cecilia_status", 0)
//        farming_afterhour_cecilia.text = pref.getString("cecilia_aftertime", "")
//        if(Textflag_cecilia == 1){
//            cecilia.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_yesang_flower = pref.getInt("yesang_flower_flag", 0)
//        yesang_flower.paintFlags = pref.getInt("yesang_flower_status", 0)
//        farming_afterhour_yesang_flower.text = pref.getString("yesang_flower_aftertime", "")
//        if(Textflag_yesang_flower == 1){
//            yesang_flower.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_yuri_lily = pref.getInt("yuri_lily_flag", 0)
//        yuri_lily.paintFlags = pref.getInt("yuri_lily_status", 0)
//        farming_afterhour_yuri_iliy.text = pref.getString("yuri_lily_aftertime", "")
//        if(Textflag_yuri_lily == 1){
//            yuri_lily.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_yuri_zumeoni = pref.getInt("yuri_zumeoni_flag", 0)
//        yuri_zumeoni.paintFlags = pref.getInt("yuri_zumeoni_status", 0)
//        farming_afterhour_yuri_zumeoni.text = pref.getString("yuri_zumeoni_aftertime", "")
//        if(Textflag_yuri_zumeoni == 1){
//            yuri_zumeoni.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_jueyun_chili = pref.getInt("jueyun_chili_flag", 0)
//        jueyun_chili.paintFlags = pref.getInt("jueyun_chili_status", 0)
//        farming_afterhour_jueyun_chili.text = pref.getString("jueyun_chili_aftertime", "")
//        if(Textflag_jueyun_chili == 1){
//            jueyun_chili.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_qingxin = pref.getInt("qingxin_flag", 0)
//        qingxin.paintFlags = pref.getInt("qingxin_status", 0)
//        farming_afterhour_qingxin.text = pref.getString("qingxin_aftertime", "")
//        if(Textflag_qingxin == 1){
//            qingxin.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_tongtong = pref.getInt("tongtong_flag", 0)
//        tongtong.paintFlags = pref.getInt("tongtong_status", 0)
//        farming_afterhour_tongtong.text = pref.getString("tongtong_aftertime", "")
//        if(Textflag_tongtong == 1){
//            tongtong.setTextColor(Color.parseColor("#939597"))
//        }
//
//
//        Textflag_windwheel = pref.getInt("windwheel_flag", 0)
//        windwheel_aster.paintFlags = pref.getInt("windwheel_status", 0)
//        farming_afterhour_windwheel_aster.text = pref.getString("windwheel_aftertime", "")
//        if(Textflag_windwheel == 1){
//            windwheel_aster.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_crystal_chunk = pref.getInt("crystal_chunk_flag", 0)
//        crystal_chunk.paintFlags = pref.getInt("crystal_chunk_status", 0)
//        farming_afterhour_crystal_chunk.text = pref.getString("crystal_chunk_aftertime", "")
//        if(Textflag_crystal_chunk == 1){
//            crystal_chunk.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_ghost_pungdeng = pref.getInt("ghost_pungdeng_flag", 0)
//        ghost_pungdeng.paintFlags = pref.getInt("ghost_pungdeng_status", 0)
//        farming_afterhour_ghost_pungdeng.text = pref.getString("ghost_pungdeng_aftertime", "")
//        if(Textflag_ghost_pungdeng == 1){
//            ghost_pungdeng.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_cherry_blossom_sugu = pref.getInt("cherry_blossom_sugu_flag", 0)
//        cherry_blossom_sugu.paintFlags = pref.getInt("cherry_blossom_sugu_status", 0)
//        farming_afterhour_cherry_blossom_sugu.text = pref.getString("cherry_blossom_sugu_aftertime", "")
//        if(Textflag_cherry_blossom_sugu == 1){
//            cherry_blossom_sugu.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_crystal_golsu = pref.getInt("crystal_golsu_flag", 0)
//        crystal_golsu.paintFlags = pref.getInt("crystal_golsu_status", 0)
//        farming_afterhour_crystal_golsu.text = pref.getString("crystal_golsu_aftertime", "")
//        if(Textflag_crystal_golsu == 1){
//            crystal_golsu.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_hyul_gok = pref.getInt("hyul_gok_flag", 0)
//        hyul_gok.paintFlags = pref.getInt("hyul_gok_status", 0)
//        farming_afterhour_hyul_gok.text = pref.getString("hyul_gok_aftertime", "")
//        if(Textflag_hyul_gok == 1){
//            hyul_gok.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_yulimple = pref.getInt("yulimple_flag", 0)
//        yulimple.paintFlags = pref.getInt("yulimple_status", 0)
//        farming_afterhour_yulimple.text = pref.getString("yulimple_aftertime", "")
//        if(Textflag_yulimple == 1){
//            yulimple.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_sea_bulocho = pref.getInt("sea_bulocho_flag", 0)
//        sea_bulocho.paintFlags = pref.getInt("sea_bulocho_status", 0)
//        farming_afterhour_sea_bulocho.text = pref.getString("sea_bulocho_aftertime", "")
//        if(Textflag_sea_bulocho == 1){
//            sea_bulocho.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_sanho_pearl = pref.getInt("sanho_pearl_flag", 0)
//        sanho_pearl.paintFlags = pref.getInt("sanho_pearl_status", 0)
//        farming_afterhour_sanho_pearl.text = pref.getString("sanho_pearl_aftertime", "")
//        if(Textflag_sanho_pearl == 1){
//            sanho_pearl.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_amakumocho = pref.getInt("amakumocho_flag", 0)
//        amakumocho.paintFlags = pref.getInt("amakumocho_status", 0)
//        farming_afterhour_amakumocho.text = pref.getString("amakumocho_aftertime", "")
//        if(Textflag_amakumocho == 1){
//            amakumocho.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_parametic_transformer = pref.getInt("parametic_transformer_flag", 0)
//        parametric_transformer.paintFlags = pref.getInt("parametic_transformer_status", 0)
//        farming_afterhour_parametric_transformer.text = pref.getString("parametic_transformer_aftertime", "")
//        if(Textflag_parametic_transformer == 1){
//            parametric_transformer.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_fish_mond = pref.getInt("fish_mond_flag", 0)
//        fish_mond.paintFlags = pref.getInt("fish_mond_status", 0)
//        farming_afterhour_fish_mond.text = pref.getString("fish_mond_aftertime", "")
//        if(Textflag_fish_mond == 1){
//            fish_mond.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_fish_liyue = pref.getInt("fish_liyue_flag", 0)
//        fish_liyue.paintFlags = pref.getInt("fish_liyue_status", 0)
//        farming_afterhour_fish_liyue.text = pref.getString("fish_liyue_aftertime", "")
//        if(Textflag_fish_liyue == 1){
//            fish_liyue.setTextColor(Color.parseColor("#939597"))
//        }
//
//        Textflag_fish_inazuma = pref.getInt("fish_inazuma_flag", 0)
//        fish_inazuma.paintFlags = pref.getInt("fish_inazuma_status", 0)
//        farming_afterhour_fish_inazuma.text = pref.getString("fish_inazuma_aftertime", "")
//        if(Textflag_fish_inazuma == 1){
//            fish_inazuma.setTextColor(Color.parseColor("#939597"))
//        }
//        Textflag_fluorescent_fungus = pref.getInt("fluorescent_fungus_flag", 0)
//        fluorescent_fungus.paintFlags = pref.getInt("fluorescent_fungus_status", 0)
//        farming_afterhour_fluorescent_fungus.text = pref.getString("fluorescent_fungus_aftertime", "")
//        if(Textflag_fluorescent_fungus == 1){
//            fluorescent_fungus.setTextColor(Color.parseColor("#939597"))
//        }
        farming_switch.isChecked = pref.getBoolean("farming_switch_isChecked", true)
//        weeklysavedday = pref.getString("weekly_flag", "").toString()
//        weeklysaveddate = pref.getString("weekly_date_flag","").toString()
    }
}




