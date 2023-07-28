package com.genshindaily.genshindaily

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.*
import com.kakao.adfit.ads.AdListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.illgan
import java.lang.NumberFormatException
import java.sql.Ref
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.reflect.KMutableProperty0

class HomeFragment  : Fragment(){

    //일간 (체크안하면 0)
    var Textflag_illgan = 0
    var Textflag_resin = 0
    var Textflag_item = 0
    var Textflag_talent = 0
    var Textflag_ascend = 0
    var Textflag_battle_pass = 0
    var Textflag_monster = 0
    //일간 셋 visibility flag. 1=보임
    var illganvflag = 1
    var resinvflag = 1
    var artifactvflag = 1
    var talentvflag = 1
    var ascendvflag = 1
    var battlepassvflag = 1
    var enemyvflag = 1
    //주간 셋 visi
    var andriusvflag = 1
    var dvalinvflag = 1
    var tartagliavflag = 1
    var azhdahavflag = 1
    var signoravflag = 1
    var weeklybattlepassvflag = 1
    var reputationvflag = 1
    //주간
    var Textflag_andrius = 0
    var Textflag_dvalin = 0
    var Textflag_tartaglia = 0
    var Textflag_battle_pass_weekly = 0
    var Textflag_reputation_weekly = 0
    var Textflag_yata = 0
    var Textflag_signora = 0
    //요일,날짜 저장
    var currentday = "" //이게 왜 null??????
    var currentdate = dateGenerator()
    var savedday : String = ""
    var saveddate : String? = null
    var savedtime : String? = null
    var weeklysavedday : String = ""
    var weeklysaveddate : String = ""
    var language : String? = null

    //
    var isPurchasedRemoveAds = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData() //체크리스트 저장값 로드. 여기서 saveddate 가져와야됨.
        language = loadSetting() //location 불러오기

        Log.d("ispurchasedHome", isPurchasedRemoveAds.toString())

        if(!isPurchasedRemoveAds) {
            MobileAds.initialize(requireActivity())
            adView.loadAd(AdRequest.Builder().build()) //구글 배너

            //카카오 광고
            kakaoAdview.run {
                setClientId("DAN-ikDQEklRA3qRWh1g")
                setAdListener(object : AdListener {
                    override fun onAdLoaded() {
                        //JoLog.d("ad loaded")
                    }

                    override fun onAdFailed(p0: Int) {
                        //JoLog.d("failed to upload")
                    }

                    override fun onAdClicked() {}
                })
                loadAd()
            }
        }

        Log.d("loadillgan", illganvflag.toString()) //여기서 왜 1?
        savedday = App.prefs.myEditText.toString()
        Log.d("savedday : ", savedday)
        Log.d("currentday: ", currentday)
        Log.d("weekly: ", weeklysavedday)
        Log.d("weeklysaveddate", weeklysaveddate)
        Log.d("currentdate: ", currentdate)

        currentday = dayGenerator()//일단 해결은 됐는데 이거 왜 위에있으면 null뜸?? 이해가 안되네..

        val timeZone = when (language) {
            "ko" -> TimeZone.getTimeZone("GMT+8") //아시아
            "ja" -> TimeZone.getTimeZone("GMT+8") //아시아 (eng)
            "en" -> TimeZone.getTimeZone("GMT-5") //US
            "fr" -> TimeZone.getTimeZone("GMT+1") //유럽
            "zh" -> TimeZone.getTimeZone("GMT+8") //TW, HK, MO
            else -> TimeZone.getTimeZone("GMT+8")
        }

        val date = Calendar.getInstance(timeZone).time
        var calendar = Calendar.getInstance(timeZone)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd").apply {
            this.timeZone = timeZone
        }
        currentdate = dateFormat.format(date)
        Log.d("date1 : currentdate", currentdate)
        saveddate?.let { Log.d("date2 : saveddate", it) }

        calendar = Calendar.getInstance(timeZone).apply {
            this.timeZone = timeZone
            set(Calendar.HOUR_OF_DAY, 4)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            val dayOfWeek = get(Calendar.DAY_OF_WEEK)
            Log.d("date : dayofweek", dayOfWeek.toString())
            if (dayOfWeek == Calendar.MONDAY) {
                add(Calendar.DATE, 7)
            } else {
                add(Calendar.DATE, (Calendar.MONDAY - dayOfWeek + 7) % 7)
            }

        }
        val nextMondayDate = calendar.time
        val nextMondayDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").apply {
            this.timeZone = timeZone
        }
        Log.d("date3 : nextMonday", nextMondayDateFormat.format(nextMondayDate)) //이게 23일 8시로 나오고 있음. 왜?
        Log.d("date4 : currentHour", currentHour.toString())


        //일간퀘 초기화
        //사용자가 체크한 날짜보다 < 앱에 들어온 현재 시간이 더큰 날짜고 && 시간이 새벽 4시 이후이면.
        if(saveddate != null){
            val savedDate = dateFormat.parse(saveddate)
            val currentDate = dateFormat.parse(currentdate)
            if (currentDate.after(savedDate)) {
                Log.d("date3", currentHour.toString())
                if (currentHour >= 4) {
                    dailyreset()
                }
            }
        }

        // 주간퀘 초기화
        // currentDate가 다음주 월요일 새벽 4시(nextmondaydate) 이후라면
        val currentDate = dateFormat.parse(currentdate)
        if(currentDate.after(dateFormat.parse(nextMondayDateFormat.format(nextMondayDate))))
        {
            weeklyreset()
        }

        day.text = dayGenerator() // dayGenerator -> dayString 반환

        // (세팅에서)일간 체크리스트 visibility
        if(illganvflag == 0) illganline.visibility = View.GONE else illganline.visibility = View.VISIBLE
        if(resinvflag == 0) resinline.visibility = View.GONE else resinline.visibility = View.VISIBLE
        if(artifactvflag == 0) artifactline.visibility = View.GONE else artifactline.visibility = View.VISIBLE
        if(talentvflag == 0) talentline.visibility = View.GONE else talentline.visibility = View.VISIBLE
        if(battlepassvflag == 0) battlepassline.visibility = View.GONE else battlepassline.visibility = View.VISIBLE
        if(ascendvflag == 0) ascendline.visibility = View.GONE else ascendline.visibility = View.VISIBLE
        if(enemyvflag == 0) enemyline.visibility = View.GONE else enemyline.visibility = View.VISIBLE

        //주간 체크리스트 visibility
        if(andriusvflag == 0) andriusline.visibility = View.GONE else andriusline.visibility = View.VISIBLE
        if(dvalinvflag == 0) dvalinline.visibility = View.GONE else dvalinline.visibility = View.VISIBLE
        if(tartagliavflag == 0) tartaglialine.visibility = View.GONE else tartaglialine.visibility = View.VISIBLE
        if(azhdahavflag == 0) azhdahaline.visibility = View.GONE else azhdahaline.visibility = View.VISIBLE
        if(signoravflag == 0) signoraline.visibility = View.GONE else signoraline.visibility = View.VISIBLE
        if(weeklybattlepassvflag == 0) weeklybattlepassline.visibility = View.GONE else weeklybattlepassline.visibility = View.VISIBLE
        if(reputationvflag == 0) reputationline.visibility = View.GONE else reputationline.visibility = View.VISIBLE

        setOnClickListenerForView(illgan) { Textflag_illgan = it }
        setOnClickListenerForView(resin) { Textflag_resin = it }
        setOnClickListenerForView(item) { Textflag_item = it }
        setOnClickListenerForView(talent) { Textflag_talent = it }
        setOnClickListenerForView(ascend) { Textflag_ascend = it }
        setOnClickListenerForView(battle_pass) { Textflag_battle_pass = it }
        setOnClickListenerForView(monster) { Textflag_monster = it }

        //주간퀘
        // TODO : 주간퀘 - 여기도 함수화 필요
        andrius.setOnClickListener(View.OnClickListener {
            onTextClicked_andrius()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })

        dvalin.setOnClickListener(View.OnClickListener {
            onTextClicked_dvalin()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })

        tartaglia.setOnClickListener(View.OnClickListener {
            onTextClicked_tartaglia()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })

        battle_pass_weekly.setOnClickListener(View.OnClickListener {
            onTextClicked_battle_pass_weekly()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })

        reputation_weekly.setOnClickListener(View.OnClickListener {
            onTextClicked_reputation_weekly()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })

        yata.setOnClickListener(View.OnClickListener {
            onTextClicked_yata()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })

        signora.setOnClickListener(View.OnClickListener {
            onTextClicked_signora()
            weeklysavedday = dayGenerator()
            weeklysaveddate = dateGenerator()
        })


        //일간 세팅 다이얼로그
        //TODO : 체크리스트 추가 제거 부분 함수화
        illganmenu.setOnClickListener {

            val builder = AlertDialog.Builder(requireActivity())
            val dialogView = layoutInflater.inflate(R.layout.dialog_daily_checklist, null)

            //다이얼로그와 홈프래그먼트 연결
            val illgandelete = dialogView.findViewById<ImageView>(R.id.illgandelete)
            val illganplus = dialogView.findViewById<ImageView>(R.id.illganplus)
            val illgansettingtext = dialogView.findViewById<TextView>(R.id.illgansettingtext)
            val resindelete = dialogView.findViewById<ImageView>(R.id.resindelete)
            val resinplus = dialogView.findViewById<ImageView>(R.id.resinplus)
            val resinsettingtext = dialogView.findViewById<TextView>(R.id.resinsettingtext)
            val artifactdelete = dialogView.findViewById<ImageView>(R.id.artifactdelete)
            val artifactplus = dialogView.findViewById<ImageView>(R.id.artifactplus)
            val artifactsettingtext = dialogView.findViewById<TextView>(R.id.artifactsettingtext)
            val talentdelete= dialogView.findViewById<ImageView>(R.id.talentdelete)
            val talentplus = dialogView.findViewById<ImageView>(R.id.talentplus)
            val talentsettingtext = dialogView.findViewById<TextView>(R.id.talentsettingtext)
            val ascenddelete = dialogView.findViewById<ImageView>(R.id.ascenddelete)
            val ascendplus = dialogView.findViewById<ImageView>(R.id.ascendplus)
            val ascendsettingtext = dialogView.findViewById<TextView>(R.id.ascendsettingtext)
            val battlepassdelete = dialogView.findViewById<ImageView>(R.id.battlepassdelete)
            val battlepassplus = dialogView.findViewById<ImageView>(R.id.battlepassplus)
            val battlepasssettingtext = dialogView.findViewById<TextView>(R.id.battlepasssettingtext)
            val enemydelete = dialogView.findViewById<ImageView>(R.id.enemydelete)
            val enemyplus = dialogView.findViewById<ImageView>(R.id.enemyplus)
            val enemysettingtext = dialogView.findViewById<TextView>(R.id.enemysettingtext)

            //줄그인거 저장
            if(illganvflag == 0) {
                illgansettingtext.setPaintFlags(illgansettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                illgansettingtext.setTextColor(Color.parseColor("#939597")) //회색
            }
            if(resinvflag == 0) {
                resinsettingtext.setPaintFlags(resinsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                resinsettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(artifactvflag == 0) {
                artifactsettingtext.setPaintFlags(artifactsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                artifactsettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(talentvflag == 0) {
                talentsettingtext.setPaintFlags(talentsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                talentsettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(ascendvflag == 0) {
                ascendsettingtext.setPaintFlags(ascendsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                ascendsettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(battlepassvflag == 0) {
                battlepasssettingtext.setPaintFlags(battlepasssettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                battlepasssettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(enemyvflag == 0) {
                enemysettingtext.setPaintFlags(enemysettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                enemysettingtext.setTextColor(Color.parseColor("#939597"))
            }

            //함수로 만들면 작동 안하는 이유?
            illgandelete.setOnClickListener {
                illgansettingtext.setPaintFlags(illgansettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                illgansettingtext.setTextColor(Color.parseColor("#939597"))
                illganline.visibility = View.GONE
                illganvflag = 0
            }
            illganplus.setOnClickListener {
                illgansettingtext.setPaintFlags(0)
                illgansettingtext.setTextColor(Color.parseColor("#000000"))
                illganline.visibility = View.VISIBLE
                illganvflag = 1
            }

            resindelete.setOnClickListener {
                resinsettingtext.setPaintFlags(resinsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                resinsettingtext.setTextColor(Color.parseColor("#939597"))
                resinline.visibility = View.GONE
                resinvflag = 0
            }
            resinplus.setOnClickListener {
                resinsettingtext.setPaintFlags(0);
                resinsettingtext.setTextColor(Color.parseColor("#000000"))
                resinline.visibility = View.VISIBLE
                resinvflag = 1
            }

            artifactdelete.setOnClickListener {
                artifactsettingtext.setPaintFlags(artifactsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                artifactsettingtext.setTextColor(Color.parseColor("#939597"))
                artifactline.visibility = View.GONE
                artifactvflag = 0
            }
            artifactplus.setOnClickListener {
                artifactsettingtext.setPaintFlags(0);
                artifactsettingtext.setTextColor(Color.parseColor("#000000"))
                artifactline.visibility = View.VISIBLE
                artifactvflag = 1
            }

            talentdelete.setOnClickListener {
                talentsettingtext.setPaintFlags(talentsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                talentsettingtext.setTextColor(Color.parseColor("#939597"))
                talentline.visibility = View.GONE
                talentvflag = 0
            }
            talentplus.setOnClickListener {
                talentsettingtext.setPaintFlags(0);
                talentsettingtext.setTextColor(Color.parseColor("#000000"))
                talentline.visibility = View.VISIBLE
                talentvflag = 1
            }

            ascenddelete.setOnClickListener {
                ascendsettingtext.setPaintFlags(ascendsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                ascendsettingtext.setTextColor(Color.parseColor("#939597"))
                ascendline.visibility = View.GONE
                ascendvflag = 0
            }
            ascendplus.setOnClickListener {
                ascendsettingtext.setPaintFlags(0);
                ascendsettingtext.setTextColor(Color.parseColor("#000000"))
                ascendline.visibility = View.VISIBLE
                ascendvflag = 1
            }

            battlepassdelete.setOnClickListener {
                battlepasssettingtext.setPaintFlags(battlepasssettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                battlepasssettingtext.setTextColor(Color.parseColor("#939597"))
                battlepassline.visibility = View.GONE
                battlepassvflag = 0
            }
            battlepassplus.setOnClickListener {
                battlepasssettingtext.setPaintFlags(0);
                battlepasssettingtext.setTextColor(Color.parseColor("#000000"))
                battlepassline.visibility = View.VISIBLE
                battlepassvflag = 1
            }

            enemydelete.setOnClickListener {
                enemysettingtext.setPaintFlags(enemysettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                enemysettingtext.setTextColor(Color.parseColor("#939597"))
                enemyline.visibility = View.GONE
                enemyvflag = 0
            }
            enemyplus.setOnClickListener {
                enemysettingtext.setPaintFlags(0);
                enemysettingtext.setTextColor(Color.parseColor("#000000"))
                enemyline.visibility = View.VISIBLE
                enemyvflag = 1
            }

            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }

        //주간 세팅 다이얼로그
        weeklymenu.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            val dialogView = layoutInflater.inflate(R.layout.dialog_weekly_checklist, null)

            val andriusdelete = dialogView.findViewById<ImageView>(R.id.andriusdelete)
            val andriusplus = dialogView.findViewById<ImageView>(R.id.andriusplus)
            val andriussettingtext = dialogView.findViewById<TextView>(R.id.andriussettingtext)
            val dvalindelete = dialogView.findViewById<ImageView>(R.id.dvalindelete)
            val dvalinplus = dialogView.findViewById<ImageView>(R.id.dvalinplus)
            val dvalinsettingtext = dialogView.findViewById<TextView>(R.id.dvalinsettingtext)
            val tartagliadelete = dialogView.findViewById<ImageView>(R.id.tartagliadelete)
            val tartagliaplus = dialogView.findViewById<ImageView>(R.id.tartagliaplus)
            val tartagliasettingtext = dialogView.findViewById<TextView>(R.id.tartagliasettingtext)
            val azhdahadelete= dialogView.findViewById<ImageView>(R.id.azhdahadelete)
            val azhdahaplus = dialogView.findViewById<ImageView>(R.id.azhdahaplus)
            val azhdahasettingtext = dialogView.findViewById<TextView>(R.id.azhdahasettingtext)
            val signoradelete = dialogView.findViewById<ImageView>(R.id.signoradelete)
            val signoraplus = dialogView.findViewById<ImageView>(R.id.signoraplus)
            val signorasettingtext = dialogView.findViewById<TextView>(R.id.signorasettingtext)
            val weeklybattlepassdelete = dialogView.findViewById<ImageView>(R.id.weeklybattlepassdelete)
            val weeklybattlepassplus = dialogView.findViewById<ImageView>(R.id.weeklybattlepassplus)
            val weeklybattlepasssettingtext = dialogView.findViewById<TextView>(R.id.weeklybattlepasssettingtext)
            val reputationdelete = dialogView.findViewById<ImageView>(R.id.reputationdelete)
            val reputationplus = dialogView.findViewById<ImageView>(R.id.reputationplus)
            val reputationsettingtext = dialogView.findViewById<TextView>(R.id.reputationsettingtext)

            //줄그인거 저장
            if(andriusvflag == 0) {
                andriussettingtext.setPaintFlags(andriussettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                andriussettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(dvalinvflag == 0) {
                dvalinsettingtext.setPaintFlags(dvalinsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                dvalinsettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(tartagliavflag == 0) {
                tartagliasettingtext.setPaintFlags(tartagliasettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                tartagliasettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(azhdahavflag == 0) {
                azhdahasettingtext.setPaintFlags(azhdahasettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                azhdahasettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(signoravflag == 0) {
                signorasettingtext.setPaintFlags(signorasettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                signorasettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(weeklybattlepassvflag == 0) {
                weeklybattlepasssettingtext.setPaintFlags(weeklybattlepasssettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                weeklybattlepasssettingtext.setTextColor(Color.parseColor("#939597"))
            }
            if(reputationvflag == 0) {
                reputationsettingtext.setPaintFlags(reputationsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                reputationsettingtext.setTextColor(Color.parseColor("#939597"))
            }

            //함수로 만들면 작동 안하는 이유?
            andriusdelete.setOnClickListener {
                andriussettingtext.setPaintFlags(andriussettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                andriussettingtext.setTextColor(Color.parseColor("#939597"))
                andriusline.visibility = View.GONE
                andriusvflag = 0
            }
            andriusplus.setOnClickListener {
                andriussettingtext.setPaintFlags(0);
                andriussettingtext.setTextColor(Color.parseColor("#000000"))
                andriusline.visibility = View.VISIBLE
                andriusvflag = 1
            }

            dvalindelete.setOnClickListener {
                dvalinsettingtext.setPaintFlags(dvalinsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                dvalinsettingtext.setTextColor(Color.parseColor("#939597"))
                dvalinline.visibility = View.GONE
                dvalinvflag = 0
            }
            dvalinplus.setOnClickListener {
                dvalinsettingtext.setPaintFlags(0);
                dvalinsettingtext.setTextColor(Color.parseColor("#000000"))
                dvalinline.visibility = View.VISIBLE
                dvalinvflag = 1
            }

            tartagliadelete.setOnClickListener {
                tartagliasettingtext.setPaintFlags(tartagliasettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                tartagliasettingtext.setTextColor(Color.parseColor("#939597"))
                tartaglialine.visibility = View.GONE
                tartagliavflag = 0
            }
            tartagliaplus.setOnClickListener {
                tartagliasettingtext.setPaintFlags(0);
                tartagliasettingtext.setTextColor(Color.parseColor("#000000"))
                tartaglialine.visibility = View.VISIBLE
                tartagliavflag = 1
            }

            azhdahadelete.setOnClickListener {
                azhdahasettingtext.setPaintFlags(azhdahasettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                azhdahasettingtext.setTextColor(Color.parseColor("#939597"))
                azhdahaline.visibility = View.GONE
                azhdahavflag = 0
            }
            azhdahaplus.setOnClickListener {
                azhdahasettingtext.setPaintFlags(0);
                azhdahasettingtext.setTextColor(Color.parseColor("#000000"))
                azhdahaline.visibility = View.VISIBLE
                azhdahavflag = 1
            }

            signoradelete.setOnClickListener {
                signorasettingtext.setPaintFlags(signorasettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                signorasettingtext.setTextColor(Color.parseColor("#939597"))
                signoraline.visibility = View.GONE
                signoravflag = 0
            }
            signoraplus.setOnClickListener {
                signorasettingtext.setPaintFlags(0);
                signorasettingtext.setTextColor(Color.parseColor("#000000"))
                signoraline.visibility = View.VISIBLE
                signoravflag = 1
            }

            weeklybattlepassdelete.setOnClickListener {
                weeklybattlepasssettingtext.setPaintFlags(weeklybattlepasssettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                weeklybattlepasssettingtext.setTextColor(Color.parseColor("#939597"))
                weeklybattlepassline.visibility = View.GONE
                weeklybattlepassvflag = 0
            }
            weeklybattlepassplus.setOnClickListener {
                weeklybattlepasssettingtext.setPaintFlags(0);
                weeklybattlepasssettingtext.setTextColor(Color.parseColor("#000000"))
                weeklybattlepassline.visibility = View.VISIBLE
                weeklybattlepassvflag = 1
            }

            reputationdelete.setOnClickListener {
                reputationsettingtext.setPaintFlags(reputationsettingtext.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                reputationsettingtext.setTextColor(Color.parseColor("#939597"))
                reputationline.visibility = View.GONE
                reputationvflag = 0
            }
            reputationplus.setOnClickListener {
                reputationsettingtext.setPaintFlags(0);
                reputationsettingtext.setTextColor(Color.parseColor("#000000"))
                reputationline.visibility = View.VISIBLE
                reputationvflag = 1
            }

            builder.setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }

        resintimer_button.setOnClickListener { view ->
            var resinvalue : Int
            try {
                resinvalue = Integer.parseInt(currentresin.text.toString())

                if(resinvalue in 0..160) {
                    var pair = resintimer(resinvalue)
                    if (resin_switch.isChecked) {
                        alarm_on(resinvalue, activity?.getString(R.string.daily_resin).toString(), 100)
                        Toast.makeText(activity, activity?.getString(R.string.resin_alarm_activated).toString(), Toast.LENGTH_SHORT).show()
                    }
                    afterhourxml.text = pair.first.toString()
                    afterminutexml.text = pair.second.toString()
                }
                else Toast.makeText(activity, "0에서 160까지의 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            catch(e: NumberFormatException) {
                resinvalue = 160
                var pair = resintimer(resinvalue)
                afterhourxml.text = pair.first.toString()
                afterminutexml.text = pair.second.toString()
            }
        }

        resin_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)Toast.makeText(activity, activity?.getString(R.string.alarm_toast).toString(), Toast.LENGTH_LONG).show()
            if(!isChecked) {
                Toast.makeText(activity, activity?.getString(R.string.resin_alarm_off).toString(), Toast.LENGTH_LONG).show()
                alarm_off(100, "레진")
            }
        }

        // 특성 visi
        val talents = arrayOf(jean_talent, amber_talent, lisa_talent, kaeya_talent, barbara_talent,
            diluc_talent, razor_talent, venti_talent, klee_talent, bennett_talent, noelle_talent,
            fischl_talent, sucrose_talent, mona_talent, xiao_talent, beidou_talent, ningguang_talent,
            xiangling_talent, xingqiu_talent, chongyun_talent, qiqi_talent, keqing_talent,
            hangza_talent, diona_talent, tartaglia_talent, zhongli_talent, xinyan_talent,
            albedo_talent, ganyu_talent, hutao_talent, rosaria_talent, eula_talent,
            yanfei_talent, kazuha_talent, ayaka_talent, yoimiya_talent, sayu_talent,
            raiden_talent,sara_talent,aloy_talent)

        for (talent in talents) {
            talent.visibility = View.GONE
        }

        if (day.text == "월" || day.text == "MONDAY" || day.text == "목" || day.text == "THURSDAY" || day.text == "일" || day.text == "SUNDAY") {
            val visibleTalents = arrayOf(amber_talent, barbara_talent, klee_talent, sucrose_talent,
                xiao_talent, ningguang_talent, qiqi_talent, tartaglia_talent, keqing_talent, diona_talent, yoimiya_talent, aloy_talent)
            for (talent in visibleTalents) {
                talent.visibility = View.VISIBLE
            }
        }

        if (day.text == "화" || day.text == "TUESDAY" || day.text == "금" || day.text == "FRIDAY" || day.text == "일" || day.text == "SUNDAY") {
            val visibleTalents = arrayOf(jean_talent, diluc_talent, razor_talent, bennett_talent,
                noelle_talent, mona_talent, xiangling_talent, chongyun_talent, ganyu_talent, hutao_talent, eula_talent, kazuha_talent, ayaka_talent,sara_talent)
            for (talent in visibleTalents) {
                talent.visibility = View.VISIBLE
            }
        }

        if (day.text == "수" || day.text == "WEDNESDAY" || day.text == "토" || day.text == "SATURDAY" || day.text == "일" || day.text == "SUNDAY") {
            val visibleTalents = arrayOf(lisa_talent, kaeya_talent, venti_talent, fischl_talent,
                beidou_talent, xingqiu_talent, zhongli_talent, xinyan_talent, albedo_talent,
            rosaria_talent, yanfei_talent, sayu_talent, raiden_talent)
            for (talent in visibleTalents) {
                talent.visibility = View.VISIBLE
            }
        }

        //무기 visi
        val weapons = arrayOf(aquila, skyward_blade, summit_shaper, primordial_jade_cutter, the_flute, iron_sting, prototype_rancour,
            the_black_sword, royal_longsword, lions_roar, sacrificial_sword, blackcliff_longsword, sword_of_descension, festering_desire,
            fillet_blade, harbinger_of_dawn, cool_steel, skyrider_sword, travelers_handy_sword, dark_iron_sword, favonius_sword, the_alley_flash,
            freedom_sworn, mistsplitter_reforged, amenoma_kageuta_blade, wolf_gravestone, skyward_pride1,
            the_unforged,favonius_greatsword, serpent_spine, prototype_archaic, whiteblind,royal_greatsword,
            the_bell, blackcliff_slasher, rainslasher, sacrificial_greatsword, snow_tombed_starsilver,
            skyrider_greatsword, debate_club, white_iron_greatsword, bloodtainted_greatsword, ferrous_shadow,
            lithic_blade, song_of_broken_pines, katsuragi_slasher, luxurious_sea_lord, skyward_atlas, lost_prayer,memory_of_dust,
            prototype_amber,mappa_mare,solar_pearl, royal_grimoire, eye_of_perception, the_widsith, sacrificial_fragments,
            favonius_codex, blackcliff_agate, frostbearer, twin_nephrite, emerald_orb, otherworldly_story,
            thrilling_tales_of_dragon_slayers, magic_guide,wine_and_song1, everlasting_moonglow, dodoco_tales, white_dragon_ring,
            primordial_jade_spear, skyward_spine,vortex_vanquisher, prototype_starglitter, crescent_pike, deathmatch,blackcliff_pole,
            favonius_lance,dragons_bane,royal_spear,dragonspine_spear, blacktassel, halberd, white_tassel, lithic_spear,
            engulfing_lightning, kitain_cross_spear, the_catch, skyward_harp, amos_bow, favonius_warbow, prototype_crescent,
            compound_bow, the_viridescent_hunt, royal_bow, rust, sacrificial_bow, the_stringless, blackcliff_warbow,
            slingshot, messenger, recurve_bow, sharpshooter_oath, raven_bow, staff_of_homa, elegy_for_the_end, alley_hunter,
            windblume_ode, thundering_pulse, demon_slayer_bow, predator, yuya_waltz)


            for (w in weapons) {
                w.visibility = View.GONE
            }

        //고탑 왕, 고운 한림, 먼바다
        if(day.text == "월" || day.text == "MONDAY" || day.text == "목" || day.text == "THURSDAY" || day.text == "일" || day.text == "SUNDAY") {
            val weapons1 = arrayOf(aquila, summit_shaper, favonius_sword, royal_longsword, lions_roar,
                blackcliff_longsword, cool_steel, dark_iron_sword, whiteblind, the_bell, snow_tombed_starsilver,
                ferrous_shadow, solar_pearl, royal_grimoire, favonius_codex, blackcliff_agate, emerald_orb, magic_guide,
                primordial_jade_spear, crescent_pike, white_tassel, the_viridescent_hunt, rust, the_stringless,
                blackcliff_warbow, slingshot, raven_bow, lithic_blade, the_alley_flash, song_of_broken_pines,
                mistsplitter_reforged, amenoma_kageuta_blade, everlasting_moonglow, white_dragon_ring,yuya_waltz)
            for (weapon in weapons1) {
                weapon.visibility = View.VISIBLE
            }
        }

        //칼바람 울프, 안개구름, 나루카미
        if(day.text =="화" || day.text =="TUESDAY" || day.text == "금" || day.text =="FRIDAY" || day.text == "일" || day.text =="SUNDAY") {
            val weapons2 = arrayOf(skyward_blade, primordial_jade_cutter, the_flute, prototype_rancour,
                the_black_sword, sword_of_descension, fillet_blade, harbinger_of_dawn, skyward_pride1, the_unforged,
                blackcliff_slasher, rainslasher, sacrificial_greatsword, debate_club, bloodtainted_greatsword,
                skyward_atlas, prototype_amber, eye_of_perception, the_widsith, twin_nephrite, thrilling_tales_of_dragon_slayers,
                deathmatch, blackcliff_pole, dragons_bane, royal_spear, dragonspine_spear, halberd, skyward_harp, prototype_crescent,
                sacrificial_bow, sharpshooter_oath,elegy_for_the_end,wine_and_song1,katsuragi_slasher,dodoco_tales,
                thundering_pulse,demon_slayer_bow,predator)
            for (weapon in weapons2) {
                weapon.visibility = View.VISIBLE
            }
        }

        //라이언 투사의 족쇄, 흑운철, 금석극화
        if(day.text =="수" || day.text =="WEDNESDAY" || day.text == "토" || day.text =="SATURDAY" || day.text == "일" || day.text =="SUNDAY") {
            val weapons3 = arrayOf(iron_sting, sacrificial_sword, festering_desire, skyrider_sword, travelers_handy_sword,
                wolf_gravestone, favonius_greatsword, serpent_spine, prototype_archaic, royal_greatsword, skyrider_greatsword,
                white_iron_greatsword, lost_prayer, memory_of_dust, mappa_mare, sacrificial_fragments, frostbearer, otherworldly_story,
                skyward_spine, vortex_vanquisher, prototype_starglitter, favonius_lance, blacktassel, amos_bow, favonius_warbow, compound_bow,
                royal_bow,messenger,recurve_bow, staff_of_homa,lithic_spear, alley_hunter, windblume_ode, freedom_sworn, engulfing_lightning, luxurious_sea_lord,
                engulfing_lightning, kitain_cross_spear, the_catch)
            for (weapon in weapons3) {
                weapon.visibility = View.VISIBLE
            }
        }


        //무기누르면 이름팝업
        // TODO : 무기누르면 이름팝업 부분 더 직관적으로 = > Toast 시간 더 짧게하고, 전체적으로 볼 수 있는 Dialog 하나 만들면 될듯.
        aquila.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.aquila_favonia).toString(), Toast.LENGTH_SHORT).show()
        }
        skyward_blade.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.skyward_blade).toString(), Toast.LENGTH_SHORT).show()
        }
        summit_shaper.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.summit_shaper).toString(), Toast.LENGTH_SHORT).show()
        }
        primordial_jade_cutter.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.primordial_jade_cutter).toString(), Toast.LENGTH_SHORT).show()
        }
        the_flute.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.the_flute).toString(), Toast.LENGTH_SHORT).show()
        }
        iron_sting.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.iron_sting).toString(), Toast.LENGTH_SHORT).show()
        }
        prototype_rancour.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.prototype_rancour).toString(), Toast.LENGTH_SHORT).show()
        }
        the_black_sword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.the_black_sword).toString(), Toast.LENGTH_SHORT).show()
        }
        royal_longsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.royal_longsword).toString(), Toast.LENGTH_SHORT).show()
        }
        lions_roar.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.lions_roar).toString(), Toast.LENGTH_SHORT).show()
        }
        sacrificial_sword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.sacrificial_sword).toString(), Toast.LENGTH_SHORT).show()
        }
        blackcliff_longsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.blackcliff_longsword).toString(), Toast.LENGTH_SHORT).show()
        }
        sword_of_descension.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.sword_of_descension).toString(), Toast.LENGTH_SHORT).show()
        }
        festering_desire.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.festering_desire).toString(), Toast.LENGTH_SHORT).show()
        }
        fillet_blade.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.fillet_blade).toString(), Toast.LENGTH_SHORT).show()
        }
        harbinger_of_dawn.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.harbinger_of_dawn).toString(), Toast.LENGTH_SHORT).show()
        }
        cool_steel.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.cool_steel).toString(), Toast.LENGTH_SHORT).show()
        }
        skyrider_sword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.skyrider_sword).toString(), Toast.LENGTH_SHORT).show()
        }
        travelers_handy_sword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.travelers_handy_sword).toString(), Toast.LENGTH_SHORT).show()
        }
        dark_iron_sword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.dark_iron_sword).toString(), Toast.LENGTH_SHORT).show()
        }
        favonius_sword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.favonius_sword).toString(), Toast.LENGTH_SHORT).show()
        }
        the_alley_flash.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.the_alley_flash).toString(), Toast.LENGTH_SHORT).show()
        }
        freedom_sworn.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.freedom_sworn).toString(), Toast.LENGTH_SHORT).show()
        }
        mistsplitter_reforged.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.mistsplitter_reforged).toString(), Toast.LENGTH_SHORT).show()
        }
        amenoma_kageuta_blade.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.amenoma_kageuta_blade).toString(), Toast.LENGTH_SHORT).show()
        }
        //여기까지 한손검

        wolf_gravestone.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.wolfs_gravestone).toString(), Toast.LENGTH_SHORT).show()
        }
        skyward_pride1.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.skyward_pride).toString(), Toast.LENGTH_SHORT).show()
        }
        the_unforged.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.the_unforged).toString(), Toast.LENGTH_SHORT).show()
        }
        favonius_greatsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.favonius_greatsword).toString(), Toast.LENGTH_SHORT).show()
        }
        serpent_spine.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.serpent_spine).toString(), Toast.LENGTH_SHORT).show()
        }
        prototype_archaic.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.prototype_archaic).toString(), Toast.LENGTH_SHORT).show()
        }
        whiteblind.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.whiteblind).toString(), Toast.LENGTH_SHORT).show()
        }
        royal_greatsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.royal_greatsword).toString(), Toast.LENGTH_SHORT).show()
        }
        the_bell.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.the_bell).toString(), Toast.LENGTH_SHORT).show()
        }
        blackcliff_slasher.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.blackcliff_slasher).toString(), Toast.LENGTH_SHORT).show()
        }
        rainslasher.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.rainslasher).toString(), Toast.LENGTH_SHORT).show()
        }
        sacrificial_greatsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.sacrificial_greatsword).toString(), Toast.LENGTH_SHORT).show()
        }
        snow_tombed_starsilver.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.snow_tombed_starsilver).toString(), Toast.LENGTH_SHORT).show()
        }
        skyrider_greatsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.skyrider_greatsword).toString(), Toast.LENGTH_SHORT).show()
        }
        debate_club.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.debate_club).toString(), Toast.LENGTH_SHORT).show()
        }
        white_iron_greatsword.setOnClickListener{
            Toast.makeText(activity, activity?.getString(R.string.white_iron_greatsword).toString(), Toast.LENGTH_SHORT).show()
        }
        bloodtainted_greatsword.setOnClickListener{
            Toast.makeText(activity, getString(R.string.bloodtainted_greatsword), Toast.LENGTH_SHORT).show()
        }
        ferrous_shadow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.ferrous_shadow), Toast.LENGTH_SHORT).show()
        }
        lithic_blade.setOnClickListener{
            Toast.makeText(activity, getString(R.string.lithic_blade), Toast.LENGTH_SHORT).show()
        }
        song_of_broken_pines.setOnClickListener{
            Toast.makeText(activity, getString(R.string.song_of_broken_pines), Toast.LENGTH_SHORT).show()
        }
        katsuragi_slasher.setOnClickListener{
            Toast.makeText(activity, getString(R.string.katsuragis_slasher), Toast.LENGTH_SHORT).show()
        }
        luxurious_sea_lord.setOnClickListener{
            Toast.makeText(activity, getString(R.string.luxurious_sea_lord), Toast.LENGTH_SHORT).show()
        }
        //양손검

        skyward_atlas.setOnClickListener{
            Toast.makeText(activity, getString(R.string.skyward_atlas), Toast.LENGTH_SHORT).show()
        }
        lost_prayer.setOnClickListener{
            Toast.makeText(activity, getString(R.string.lost_prayer_to_the_sacred_winds), Toast.LENGTH_SHORT).show()
        }
        memory_of_dust.setOnClickListener{
            Toast.makeText(activity, getString(R.string.memory_of_dust), Toast.LENGTH_SHORT).show()
        }
        prototype_amber.setOnClickListener{
            Toast.makeText(activity, getString(R.string.prototype_amber), Toast.LENGTH_SHORT).show()
        }
        mappa_mare.setOnClickListener{
            Toast.makeText(activity, getString(R.string.mappa_mare), Toast.LENGTH_SHORT).show()
        }
        solar_pearl.setOnClickListener{
            Toast.makeText(activity, getString(R.string.solar_pearl), Toast.LENGTH_SHORT).show()
        }
        royal_grimoire.setOnClickListener{
            Toast.makeText(activity, getString(R.string.royal_grimoire), Toast.LENGTH_SHORT).show()
        }
        eye_of_perception.setOnClickListener{
            Toast.makeText(activity, getString(R.string.eye_of_perception), Toast.LENGTH_SHORT).show()
        }
        the_widsith.setOnClickListener{
            Toast.makeText(activity, getString(R.string.the_widsith), Toast.LENGTH_SHORT).show()
        }
        sacrificial_fragments.setOnClickListener{
            Toast.makeText(activity, getString(R.string.sacrificial_fragments), Toast.LENGTH_SHORT).show()
        }
        favonius_codex.setOnClickListener{
            Toast.makeText(activity, getString(R.string.favonius_codex), Toast.LENGTH_SHORT).show()
        }
        blackcliff_agate.setOnClickListener{
            Toast.makeText(activity, getString(R.string.blackcliff_agate), Toast.LENGTH_SHORT).show()
        }
        frostbearer.setOnClickListener{
            Toast.makeText(activity, getString(R.string.frostbearer), Toast.LENGTH_SHORT).show()
        }
        twin_nephrite.setOnClickListener{
            Toast.makeText(activity, getString(R.string.twin_nephrite), Toast.LENGTH_SHORT).show()
        }
        emerald_orb.setOnClickListener{
            Toast.makeText(activity, getString(R.string.emerald_orb), Toast.LENGTH_SHORT).show()
        }
        otherworldly_story.setOnClickListener{
            Toast.makeText(activity, getString(R.string.otherworldly_story), Toast.LENGTH_SHORT).show()
        }
        thrilling_tales_of_dragon_slayers.setOnClickListener{
            Toast.makeText(activity, getString(R.string.thrilling_tales_of_dragon_slayer), Toast.LENGTH_SHORT).show()
        }
        magic_guide.setOnClickListener{
            Toast.makeText(activity, getString(R.string.magic_guide), Toast.LENGTH_SHORT).show()
        }
        wine_and_song1.setOnClickListener{
            Toast.makeText(activity, getString(R.string.wine_and_song), Toast.LENGTH_SHORT).show()
        }
        everlasting_moonglow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.everlasting_moonglow), Toast.LENGTH_SHORT).show()
        }
        dodoco_tales.setOnClickListener{
            Toast.makeText(activity, getString(R.string.dodoco_tales), Toast.LENGTH_SHORT).show()
        }
        white_dragon_ring.setOnClickListener{
            Toast.makeText(activity, getString(R.string.white_dragon_ring), Toast.LENGTH_SHORT).show()
        }
        //여기까지 법구

        primordial_jade_spear.setOnClickListener{
            Toast.makeText(activity, getString(R.string.primordial_jade_winged_spear), Toast.LENGTH_SHORT).show()
        }
        skyward_spine.setOnClickListener{
            Toast.makeText(activity, getString(R.string.skyward_spine), Toast.LENGTH_SHORT).show()
        }
        vortex_vanquisher.setOnClickListener{
            Toast.makeText(activity, getString(R.string.vortex_vanquisher), Toast.LENGTH_SHORT).show()
        }
        prototype_starglitter.setOnClickListener{
            Toast.makeText(activity, getString(R.string.prototype_starglitter), Toast.LENGTH_SHORT).show()
        }
        crescent_pike.setOnClickListener{
            Toast.makeText(activity, getString(R.string.crescent_pike), Toast.LENGTH_SHORT).show()
        }
        deathmatch.setOnClickListener{
            Toast.makeText(activity, getString(R.string.deathmatch), Toast.LENGTH_SHORT).show()
        }
        blackcliff_pole.setOnClickListener{
            Toast.makeText(activity, getString(R.string.blackcliff_pole), Toast.LENGTH_SHORT).show()
        }
        favonius_lance.setOnClickListener{
            Toast.makeText(activity, getString(R.string.favonius_lance), Toast.LENGTH_SHORT).show()
        }
        dragons_bane.setOnClickListener{
            Toast.makeText(activity, getString(R.string.dragons_bane), Toast.LENGTH_SHORT).show()
        }
        royal_spear.setOnClickListener{
            Toast.makeText(activity, getString(R.string.royal_spear), Toast.LENGTH_SHORT).show()
        }
        dragonspine_spear.setOnClickListener{
            Toast.makeText(activity, getString(R.string.dragonspine_spear), Toast.LENGTH_SHORT).show()
        }
        blacktassel.setOnClickListener{
            Toast.makeText(activity, getString(R.string.black_tassel), Toast.LENGTH_SHORT).show()
        }
        halberd.setOnClickListener{
            Toast.makeText(activity, getString(R.string.halberd), Toast.LENGTH_SHORT).show()
        }
        white_tassel.setOnClickListener{
            Toast.makeText(activity, getString(R.string.white_tassel), Toast.LENGTH_SHORT).show()
        }
        lithic_spear.setOnClickListener{
            Toast.makeText(activity, getString(R.string.lithic_spear), Toast.LENGTH_SHORT).show()
        }
        engulfing_lightning.setOnClickListener{
            Toast.makeText(activity, getString(R.string.engulfing_lightning), Toast.LENGTH_SHORT).show()
        }
        kitain_cross_spear.setOnClickListener{
            Toast.makeText(activity, getString(R.string.kitain_cross_spear), Toast.LENGTH_SHORT).show()
        }
        the_catch.setOnClickListener{
            Toast.makeText(activity, getString(R.string.the_catch), Toast.LENGTH_SHORT).show()
        }
        staff_of_homa.setOnClickListener{
            Toast.makeText(activity, getString(R.string.staff_of_homa), Toast.LENGTH_SHORT).show()
        }
        //창

        skyward_harp.setOnClickListener{
            Toast.makeText(activity, getString(R.string.skyward_harp), Toast.LENGTH_SHORT).show()
        }
        amos_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.amoss_bow), Toast.LENGTH_SHORT).show()
        }
        favonius_warbow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.favonius_warbow), Toast.LENGTH_SHORT).show()
        }
        prototype_crescent.setOnClickListener{
            Toast.makeText(activity, getString(R.string.prototype_crescent), Toast.LENGTH_SHORT).show()
        }
        compound_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.compound_bow), Toast.LENGTH_SHORT).show()
        }
        the_viridescent_hunt.setOnClickListener{
            Toast.makeText(activity, getString(R.string.the_virdescent_hunt), Toast.LENGTH_SHORT).show()
        }
        royal_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.royal_bow), Toast.LENGTH_SHORT).show()
        }
        rust.setOnClickListener{
            Toast.makeText(activity, getString(R.string.rust), Toast.LENGTH_SHORT).show()
        }
        sacrificial_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.sacrificial_bow), Toast.LENGTH_SHORT).show()
        }
        the_stringless.setOnClickListener{
            Toast.makeText(activity, getString(R.string.the_stringless), Toast.LENGTH_SHORT).show()
        }
        blackcliff_warbow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.blackcliff_warbow), Toast.LENGTH_SHORT).show()
        }
        slingshot.setOnClickListener{
            Toast.makeText(activity, getString(R.string.slingshot), Toast.LENGTH_SHORT).show()
        }
        messenger.setOnClickListener{
            Toast.makeText(activity, getString(R.string.messenger), Toast.LENGTH_SHORT).show()
        }
        recurve_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.recurve_bow), Toast.LENGTH_SHORT).show()
        }
        sharpshooter_oath.setOnClickListener{
            Toast.makeText(activity, getString(R.string.sharpshooters_oath), Toast.LENGTH_SHORT).show()
        }
        raven_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.raven_bow), Toast.LENGTH_SHORT).show()
        }
        elegy_for_the_end.setOnClickListener{
            Toast.makeText(activity, getString(R.string.elegy_for_the_end), Toast.LENGTH_SHORT).show()
        }
        alley_hunter.setOnClickListener{
            Toast.makeText(activity, getString(R.string.alley_hunter), Toast.LENGTH_SHORT).show()
        }
        windblume_ode.setOnClickListener{
            Toast.makeText(activity, getString(R.string.windblume_ode), Toast.LENGTH_SHORT).show()
        }
        thundering_pulse.setOnClickListener{
            Toast.makeText(activity, getString(R.string.thundering_pulse), Toast.LENGTH_SHORT).show()
        }
        yuya_waltz.setOnClickListener{
            Toast.makeText(activity, getString(R.string.mitternachts_waltz), Toast.LENGTH_SHORT).show()
        }
        demon_slayer_bow.setOnClickListener{
            Toast.makeText(activity, getString(R.string.demon_slayer_bow), Toast.LENGTH_SHORT).show()
        }
        predator.setOnClickListener{
            Toast.makeText(activity, getString(R.string.predator), Toast.LENGTH_SHORT).show()
        }
        //활
    }
    /*private fun showSettingpopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.resinalarmpopup, null)
        val textView: TextView = view.findViewById(R.id.afterhourxml) //NPE 뜸.
        //textView.text = "blah blah"

        val alertDialog = AlertDialog.Builder(this)
                .setTitle("레진 알람 설정")
                .setPositiveButton("저장"){dialog, which ->
                    Toast.makeText(applicationContext, "알람이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton("취소", null)
                .create()

        alertDialog.setView(view)
        alertDialog.show()
    }*/
    override fun onPause() {
        super.onPause()
        kakaoAdview?.pause()
    }

    override fun onResume() {
        super.onResume()
        kakaoAdview?.resume()
    }
    override fun onStop() {
        super.onStop()
        Log.d("destroy","on")
        kakaoAdview?.destroy()
        saveData()
    }

    private fun loadData() {
        val pref = requireActivity().getSharedPreferences("pref", 0)
        isPurchasedRemoveAds = pref.getBoolean("isPurchasedRemoveAds", isPurchasedRemoveAds)

        illganvflag = pref.getInt("illganvflag", 1)
        Textflag_illgan = pref.getInt("illgan_flag", 0)
        illgan.paintFlags = pref.getInt("illgan_status", 0)
        if(Textflag_illgan == 1) {
            illgan.setTextColor(Color.parseColor("#939597"))
        }
        resinvflag = pref.getInt("resinvflag", 1)
        Textflag_resin = pref.getInt("resin_flag", 0)
        resin.paintFlags = pref.getInt("resin_status", 0)
        if(Textflag_resin == 1){
            resin.setTextColor(Color.parseColor("#939597"))
        }
        artifactvflag = pref.getInt("artifactvflag", 1)
        Textflag_item = pref.getInt("item_flag", 0)
        item.paintFlags = pref.getInt("item_status", 0)
        if(Textflag_item == 1){
            item.setTextColor(Color.parseColor("#939597"))
        }
        talentvflag = pref.getInt("talentvflag", 1)
        Textflag_talent = pref.getInt("talent_flag", 0)
        talent.paintFlags = pref.getInt("talent_status", 0)
        if(Textflag_talent == 1){
            talent.setTextColor(Color.parseColor("#939597"))
        }
        ascendvflag = pref.getInt("ascendvflag", 1)
        Textflag_ascend = pref.getInt("ascend_flag", 0)
        ascend.paintFlags = pref.getInt("ascend_status", 0)
        if(Textflag_ascend == 1){
            ascend.setTextColor(Color.parseColor("#939597"))
        }
        battlepassvflag = pref.getInt("battlepassvflag", 1)
        Textflag_battle_pass = pref.getInt("battle_pass_flag", 0)
        battle_pass.paintFlags = pref.getInt("battle_pass_status", 0)
        if(Textflag_battle_pass == 1){
            battle_pass.setTextColor(Color.parseColor("#939597"))
        }
        enemyvflag = pref.getInt("enemyvflag", 1)
        Textflag_monster = pref.getInt("monster_flag", 0)
        monster.paintFlags = pref.getInt("monster_status", 0)
        if(Textflag_monster == 1){
            monster.setTextColor(Color.parseColor("#939597"))
        }
        andriusvflag = pref.getInt("andriusvflag", 1)
        Textflag_andrius = pref.getInt("andrius_flag", 0)
        andrius.paintFlags = pref.getInt("andrius_status", 0)
        if(Textflag_andrius == 1){
            andrius.setTextColor(Color.parseColor("#939597"))
        }
        dvalinvflag = pref.getInt("dvalinvflag", 1)
        Textflag_dvalin = pref.getInt("dvalin_flag", 0)
        dvalin.paintFlags = pref.getInt("dvalin_status", 0)
        if(Textflag_dvalin == 1){
            dvalin.setTextColor(Color.parseColor("#939597"))
        }
        tartagliavflag = pref.getInt("tartagliavflag", 1)
        Textflag_tartaglia = pref.getInt("tartaglia_flag", 0)
        tartaglia.paintFlags = pref.getInt("tartaglia_status", 0)
        if(Textflag_tartaglia == 1){
            tartaglia.setTextColor(Color.parseColor("#939597"))
        }
        weeklybattlepassvflag = pref.getInt("weeklybattlepassvflag", 1)
        Textflag_battle_pass_weekly = pref.getInt("battle_pass_weekly_flag", 0)
        battle_pass_weekly.paintFlags = pref.getInt("battle_pass_weekly_status", 0)
        if(Textflag_battle_pass_weekly == 1){
            battle_pass_weekly.setTextColor(Color.parseColor("#939597"))
        }
        reputationvflag = pref.getInt("reputationvflag", 1)
        Textflag_reputation_weekly = pref.getInt("reputation_flag", 0)
        reputation_weekly.paintFlags = pref.getInt("reputation_status", 0)
        if(Textflag_reputation_weekly == 1){
            reputation_weekly.setTextColor(Color.parseColor("#939597"))
        }
        azhdahavflag = pref.getInt("azhdahavflag", 1)
        Textflag_yata = pref.getInt("yata_flag", 0)
        yata.paintFlags = pref.getInt("yata_status", 0)
        if(Textflag_yata == 1){
            yata.setTextColor(Color.parseColor("#939597"))
        }
        signoravflag = pref.getInt("signoravflag", 1)
        Textflag_signora = pref.getInt("signora_flag", 0)
        signora.paintFlags = pref.getInt("signora_status", 0)
        if(Textflag_signora == 1){
            signora.setTextColor(Color.parseColor("#939597"))
        }

        weeklysavedday = pref.getString("weekly_flag", "").toString()
        weeklysaveddate = pref.getString("weekly_date_flag","").toString()
        resin_switch.isChecked = pref.getBoolean("resin_switch_isChecked", true)

        afterhourxml.text = pref.getString("resin_afterhour", "").toString()
        afterminutexml.text = pref.getString("resin_afterminute", "").toString()

        saveddate = pref.getString("saveddate", "").toString()


    }

    private fun saveData(){ //값하고 지우는 행동까지 저장해보자.
        val pref = requireActivity().getSharedPreferences("pref", 0)
        val edit = pref.edit() //수정모드
        edit.putInt("illgan_flag", Textflag_illgan)
        edit.putInt("illgan_status", illgan.paintFlags)
        edit.putInt("illganvflag", illganvflag )
        edit.putInt("resin_flag", Textflag_resin)
        edit.putInt("resin_status", resin.paintFlags)
        edit.putInt("resinvflag", resinvflag)
        edit.putInt("item_flag", Textflag_item)
        edit.putInt("item_status", item.paintFlags)
        edit.putInt("artifactvflag", artifactvflag)
        edit.putInt("talent_flag", Textflag_talent)
        edit.putInt("talent_status", talent.paintFlags)
        edit.putInt("talentvflag", talentvflag)
        edit.putInt("ascend_flag", Textflag_ascend)
        edit.putInt("ascend_status", ascend.paintFlags)
        edit.putInt("ascendvflag", ascendvflag)
        edit.putInt("battle_pass_flag", Textflag_battle_pass)
        edit.putInt("battle_pass_status", battle_pass.paintFlags)
        edit.putInt("battlepassvflag", battlepassvflag)
        edit.putInt("monster_flag", Textflag_monster)
        edit.putInt("monster_status", monster.paintFlags)
        edit.putInt("enemyvflag", enemyvflag)
        edit.putInt("andrius_flag", Textflag_andrius)
        edit.putInt("andrius_status", andrius.paintFlags)
        edit.putInt("andriusvflag", andriusvflag)
        edit.putInt("dvalin_flag", Textflag_dvalin)
        edit.putInt("dvalin_status", dvalin.paintFlags)
        edit.putInt("dvalinvflag", dvalinvflag)
        edit.putInt("tartaglia_flag", Textflag_tartaglia)
        edit.putInt("tartaglia_status", tartaglia.paintFlags)
        edit.putInt("tartagliavflag", tartagliavflag)
        edit.putInt("battle_pass_weekly_flag", Textflag_battle_pass_weekly)
        edit.putInt("battle_pass_weekly_status", battle_pass_weekly.paintFlags)
        edit.putInt("weeklybattlepassvflag", weeklybattlepassvflag)
        edit.putInt("reputation_flag", Textflag_reputation_weekly)
        edit.putInt("reputation_status", reputation_weekly.paintFlags)
        edit.putInt("reputationvflag", reputationvflag)
        edit.putInt("yata_flag", Textflag_yata)
        edit.putInt("yata_status", yata.paintFlags)
        edit.putInt("azhdahavflag", azhdahavflag)
        edit.putInt("signora_flag", Textflag_signora)
        edit.putInt("signora_status", signora.paintFlags)
        edit.putInt("signoravflag", signoravflag)
        edit.putString("weekly_flag", weeklysavedday)
        edit.putString("weekly_date_flag", weeklysaveddate)
        edit.putString("resin_afterhour",afterhourxml.text.toString())
        edit.putString("resin_afterminute",afterminutexml.text.toString())
        edit.putBoolean("resin_switch_isChecked", resin_switch.isChecked)

        edit.putString("saveddate", saveddate)

        edit.apply()
    }

    fun dailyreset(){ //날짜 지나면 초기화하는 함수
        illgan.setPaintFlags(0);
        illgan.setTextColor(Color.parseColor("#000000"))
        Textflag_illgan = 0

        resin.setPaintFlags(0)
        resin.setTextColor(Color.parseColor("#000000"))
        Textflag_resin = 0

        item.setPaintFlags(0)
        item.setTextColor(Color.parseColor("#000000"))
        Textflag_item = 0

        talent.setPaintFlags(0)
        talent.setTextColor(Color.parseColor("#000000"))
        Textflag_talent = 0

        ascend.setPaintFlags(0)
        ascend.setTextColor(Color.parseColor("#000000"))
        Textflag_ascend = 0

        battle_pass.setPaintFlags(0)
        battle_pass.setTextColor(Color.parseColor("#000000"))
        Textflag_battle_pass = 0

        monster.setPaintFlags(0)
        monster.setTextColor(Color.parseColor("#000000"))
        Textflag_monster = 0
    }

    fun weeklyreset(){ //월요일되면 주간퀘 초기화
        andrius.setPaintFlags(0)
        andrius.setTextColor(Color.parseColor("#000000"))
        Textflag_andrius = 0

        dvalin.setPaintFlags(0)
        dvalin.setTextColor(Color.parseColor("#000000"))
        Textflag_dvalin = 0

        tartaglia.setPaintFlags(0)
        tartaglia.setTextColor(Color.parseColor("#000000"))
        Textflag_tartaglia = 0

        battle_pass_weekly.setPaintFlags(0)
        battle_pass_weekly.setTextColor(Color.parseColor("#000000"))
        Textflag_battle_pass_weekly = 0

        reputation_weekly.setPaintFlags(0)
        reputation_weekly.setTextColor(Color.parseColor("#000000"))
        Textflag_reputation_weekly = 0

        yata.setPaintFlags(0)
        yata.setTextColor(Color.parseColor("#000000"))
        Textflag_yata = 0

        signora.setPaintFlags(0)
        signora.setTextColor(Color.parseColor("#000000"))
        Textflag_signora = 0

    }

    fun onTextClicked_illgan(){
        if(Textflag_illgan == 0) {
            illgan.setPaintFlags(illgan.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            illgan.setTextColor(Color.parseColor("#939597"))
            Textflag_illgan = 1
        }
        else{
            illgan.setPaintFlags(0)
            illgan.setTextColor(Color.parseColor("#000000"))
            Textflag_illgan = 0
        }
    }

    fun onTextClicked_resin(){
        if(Textflag_resin == 0) {
            resin.setPaintFlags(resin.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            resin.setTextColor(Color.parseColor("#939597"))
            Textflag_resin = 1
        }
        else{
            resin.setPaintFlags(0)
            resin.setTextColor(Color.parseColor("#000000"))
            Textflag_resin = 0
        }
    }

    fun onTextClicked_item(){
        if(Textflag_item == 0) {
            item.setPaintFlags(item.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            item.setTextColor(Color.parseColor("#939597"))
            Textflag_item = 1
        }
        else{
            item.setPaintFlags(0)
            item.setTextColor(Color.parseColor("#000000"))
            Textflag_item = 0
        }
    }

    fun onTextClicked_talent(){
        if(Textflag_talent == 0) {
            talent.setPaintFlags(talent.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            talent.setTextColor(Color.parseColor("#939597"))
            Textflag_talent = 1
        }
        else{
            talent.setPaintFlags(0)
            talent.setTextColor(Color.parseColor("#000000"))
            Textflag_talent = 0
        }
    }

    fun onTextClicked_ascend(){
        if(Textflag_ascend == 0) {
            ascend.setPaintFlags(ascend.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            ascend.setTextColor(Color.parseColor("#939597"))
            Textflag_ascend = 1
        }
        else{
            ascend.setPaintFlags(0)
            ascend.setTextColor(Color.parseColor("#000000"))
            Textflag_ascend = 0
        }
    }

    fun onTextClicked_battle_pass(){
        if(Textflag_battle_pass == 0) {
            battle_pass.setPaintFlags(battle_pass.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            battle_pass.setTextColor(Color.parseColor("#939597"))
            Textflag_battle_pass = 1
        }
        else{
            battle_pass.setPaintFlags(0)
            battle_pass.setTextColor(Color.parseColor("#000000"))
            Textflag_battle_pass = 0
        }
    }

    fun onTextClicked_monster(){
        if(Textflag_monster == 0) {
            monster.setPaintFlags(battle_pass.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            monster.setTextColor(Color.parseColor("#939597"))
            Textflag_monster = 1
        }
        else{
            monster.setPaintFlags(0)
            monster.setTextColor(Color.parseColor("#000000"))
            Textflag_monster = 0
        }
    }

    //주간퀘
    fun onTextClicked_andrius(){
        if(Textflag_andrius == 0) {
            andrius.setPaintFlags(andrius.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            andrius.setTextColor(Color.parseColor("#939597"))
            Textflag_andrius = 1
        }
        else{
            andrius.setPaintFlags(0)
            andrius.setTextColor(Color.parseColor("#000000"))
            Textflag_andrius = 0
        }
    }
    fun onTextClicked_dvalin(){
        if(Textflag_dvalin == 0) {
            dvalin.setPaintFlags(dvalin.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            dvalin.setTextColor(Color.parseColor("#939597"))
            Textflag_dvalin = 1
        }
        else{
            dvalin.setPaintFlags(0)
            dvalin.setTextColor(Color.parseColor("#000000"))
            Textflag_dvalin = 0
        }
    }
    fun onTextClicked_tartaglia(){
        if(Textflag_tartaglia == 0) {
            tartaglia.setPaintFlags(battle_pass.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            tartaglia.setTextColor(Color.parseColor("#939597"))
            Textflag_tartaglia = 1
        }
        else{
            tartaglia.setPaintFlags(0)
            tartaglia.setTextColor(Color.parseColor("#000000"))
            Textflag_tartaglia = 0
        }
    }
    fun onTextClicked_battle_pass_weekly(){
        if(Textflag_battle_pass_weekly == 0) {
            battle_pass_weekly.setPaintFlags(battle_pass.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            battle_pass_weekly.setTextColor(Color.parseColor("#939597"))
            Textflag_battle_pass_weekly = 1
        }
        else{
            battle_pass_weekly.setPaintFlags(0)
            battle_pass_weekly.setTextColor(Color.parseColor("#000000"))
            Textflag_battle_pass_weekly = 0
        }
    }

    fun onTextClicked_reputation_weekly() {
        if (Textflag_reputation_weekly == 0) {
            reputation_weekly.setPaintFlags(reputation_weekly.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            reputation_weekly.setTextColor(Color.parseColor("#939597"))
            Textflag_reputation_weekly = 1
        }
        else {
            reputation_weekly.setPaintFlags(0);
            reputation_weekly.setTextColor(Color.parseColor("#000000"))
            Textflag_reputation_weekly = 0
        }
    }

    fun onTextClicked_yata() {
        if (Textflag_yata == 0) {
            yata.setPaintFlags(yata.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            yata.setTextColor(Color.parseColor("#939597"))
            Textflag_yata = 1
        }
        else {
            yata.setPaintFlags(0)
            yata.setTextColor(Color.parseColor("#000000"))
            Textflag_yata = 0
        }
    }

    fun onTextClicked_signora() {
        if (Textflag_signora == 0) {
            signora.setPaintFlags(signora.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            signora.setTextColor(Color.parseColor("#939597"))
            Textflag_signora = 1
        }
        else {
            signora.setPaintFlags(0)
            signora.setTextColor(Color.parseColor("#000000"))
            Textflag_signora = 0
        }
    }


    private fun dayGenerator() :String{
        val gst = TimeZone.getTimeZone(activity?.getString(R.string.GMT).toString())
        val instance = Calendar.getInstance(gst)
        val day = instance.get(Calendar.DAY_OF_WEEK).toString()
        var dayString = "error"

        when(day){
            "1" -> dayString = activity?.getString(R.string.day_sun).toString()
            "2" -> dayString = activity?.getString(R.string.day_mon).toString()
            "3" -> dayString = activity?.getString(R.string.day_tue).toString()
            "4" -> dayString = activity?.getString(R.string.day_wed).toString()
            "5" -> dayString = activity?.getString(R.string.day_thu).toString()
            "6" -> dayString = activity?.getString(R.string.day_fri).toString()
            "7" -> dayString = activity?.getString(R.string.day_sat).toString()
        }
        Log.d("daystring", dayString)
        return dayString
    }

    fun timeGenerator() : String{
        val gst = TimeZone.getTimeZone("GMT+4")
        val instance = Calendar.getInstance(gst)
        var hour = instance.get(Calendar.HOUR_OF_DAY).toString()
        var minute = instance.get(Calendar.MINUTE).toString()

        return (hour + minute)
    }

    fun dateGenerator(): String {
        val instance = Calendar.getInstance()
        val year = instance.get(Calendar.YEAR).toString()
        var month = (instance.get(Calendar.MONTH) + 1).toString()
        var date = instance.get(Calendar.DATE).toString()
        if (month.toInt() < 10) {
            monthStr = "0$month"
        }
        else{
            monthStr = month
        }
        if (date.toInt() < 10) {
            dateStr = "0$date"
        }
        else {
            dateStr = date
        }
        return year + monthStr + dateStr
    }

    fun resintimer(resinvalue: Int?) : Pair<Int, Int> { //아무것도 안넣고 버튼 누르면 팅김, 25시 ~~
        val instance = Calendar.getInstance()
        var hour = instance.get(Calendar.HOUR_OF_DAY).toInt()
        var minute = instance.get(Calendar.MINUTE).toInt()
        var resinleft = 0 //160과 현재 들어온 값의 차
        var plushour : Int = 0
        var plusminute : Int = 0

        if (resinvalue != null) {
                resinleft = 160 - resinvalue
        }
        else resinleft = 160

        var summinute = resinleft * 8 //

        if(summinute >= 60)
        {
            plushour = summinute / 60
            plusminute = summinute % 60
        }
        else plusminute = summinute

        var afterhour = hour + plushour

        var afterminute : Int = minute + plusminute
        if (afterminute >= 60) //현재시간이 50분인데 plusminute이 50분 드러오면 ㅇㅇ
        {
            afterminute = afterminute % 60
            afterhour++
        }

        if (afterhour >= 24)
        {
            afterhour = afterhour - 24
        }
        val pair = Pair(afterhour, afterminute) //pair 로 시간, 분 두개 return

        return pair
    }

    fun getIntent(context: Context, requestCode: Int, name: String? = null, id : Int): PendingIntent? {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("이름", name)
        intent.putExtra("id", id)
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT)
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
            calendar.timeInMillis + ((160-time) * 1000 * 480),
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

    fun setOnClickListenerForView(view: TextView, setFlag: (Int) -> Unit) {
        view.setOnClickListener {
            val flag = if (view.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG) 1 else 0
            if (flag == 0) {
                view.setPaintFlags(view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                view.setTextColor(Color.parseColor("#939597"))
                val timeZone = when (language) {
                    "ko" -> TimeZone.getTimeZone("GMT+8") //아시아
                    "ja" -> TimeZone.getTimeZone("GMT+8") //아시아 (eng)
                    "en" -> TimeZone.getTimeZone("GMT-5") //US
                    "fr" -> TimeZone.getTimeZone("GMT+1") //유럽
                    "zh" -> TimeZone.getTimeZone("GMT+8") //TW, HK, MO
                    else -> TimeZone.getTimeZone("GMT+8")
                }

                val date = Calendar.getInstance(timeZone).time

                val dateFormat = SimpleDateFormat("yyyy-MM-dd").apply {
                    this.timeZone = timeZone
                }
                saveddate = dateFormat.format(date)

                val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").apply {
                    this.timeZone = timeZone
                }

                savedtime = timeFormat.format(date)

                Log.d("date : savedtime  : ", savedtime.toString())
                Log.d("date : saveddate  : ", saveddate.toString())

                setFlag(1)

            } else {
                view.setPaintFlags(0)
                view.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
                setFlag(0)
            }
        }
    }

    private fun loadSetting(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "Settings",
            Activity.MODE_PRIVATE
        )
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            Log.d("language", language)
        }
        if (language != null) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        return language
    }

}

