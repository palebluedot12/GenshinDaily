package com.genshindaily.genshindaily

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver() : BroadcastReceiver() {
    val CHANNEL_ID = "TEST"
    val textContent = "리젠 완료!"
    val textTitle = "완료되엇습니다"


    override fun onReceive(context: Context?, intent: Intent?) {
        // 시스템에 등록하기
        val name2 = intent?.getStringExtra("이름")
        val id : Int? = intent?.getIntExtra("id",1)
        val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // API 26+에서만 NotificationChannel을 작성.
        // NotificationChannel 클래스는 예전 지원 라이브러리에 없다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test channel"
            val descriptionText = "testing channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        // 앱에서의 활동에 대한 명시적 의도
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        //알림을 클릭했을때 실행하는 액티비티 지정
        val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 2, notificationIntent, 0)


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti_icon)
                .setContentTitle(name2)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // 알림을 눌렀을때 실행시키고 알림은 삭제시키기 설정
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        //알림 보이기
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            if (id != null) {
                notify(id, builder.build())
            }
        }
    }
}