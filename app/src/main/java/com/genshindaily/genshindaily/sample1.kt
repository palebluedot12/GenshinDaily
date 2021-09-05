package com.genshindaily.genshindaily

import android.app.AlarmManager
import android.content.Context
import java.util.*

var monthStr : String =""
var dateStr : String =""
var hourStr : String=""
var minStr : String = ""



fun main() {
    var p = farming_afterhour()
    print(p)
}

fun farming_afterhour(): String {
    val instance = Calendar.getInstance()
    val year = instance.get(Calendar.YEAR).toString()
    val month = (instance.get(Calendar.MONTH) + 1).toString()
    val date = instance.get(Calendar.DATE).toString()
    val hour = instance.get(Calendar.HOUR_OF_DAY)
    val minute = instance.get(Calendar.MINUTE)

    monthStr = "${month}월"
    dateStr = "${date}일"

    when(hour)
    {
        in 1..11 -> hourStr = "오전${hour}시"
        in 13..23 -> hourStr = "오후${hour-12}시"
        12 -> hourStr ="오후${hour}시"
        0 -> hourStr ="오전${hour}시"
        24 -> hourStr ="오전${hour}시"
    }

    minStr = "${minute}분"

    return  monthStr + dateStr + hourStr + minStr
}

fun timeinMillis(){
    val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis() //UTC 1970년 1월 1일 00:00:00.000 을 기준으로한 현제 시간의 차이를 long형으로 반환한다.
    }




}

