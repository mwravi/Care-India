package com.careindia.lifeskills.firebase

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.legacy.content.WakefulBroadcastReceiver

class NotificationReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        playNotificationSound(context)
    }

    fun playNotificationSound(context: Context?) {
        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(context, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}