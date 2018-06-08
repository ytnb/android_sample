package com.example.ytnb.alarmnoti

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    private val channelId: String = "AlarmNotiCh"
    private fun initChannels(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context?.getString(R.string.channel_name)
            val channel = NotificationChannel(channelId,name,NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = context?.getString(R.string.channel_description)
            val notificationManager = context?.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val bootIntent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(context, 0, bootIntent, 0)
        initChannels(context)
        val builder = context?.let {
            NotificationCompat.Builder(it,channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("時間ですよ")
                .setContentText(intent?.getCharSequenceExtra("todo"))
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
        }

        val myNotification = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent?.getIntExtra("notificationId", 0)
        notificationId?.let { myNotification.notify(it,builder?.build()) }

    }

}