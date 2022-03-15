package com.careindia.lifeskills.firebase

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import androidx.core.app.NotificationCompat
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.AppSP
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat


class NotificationUtils(private val mContext: Context) {

    var notificationReceiver: NotificationReceiver? = null

    @JvmOverloads
    fun showNotificationMessage(
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent,
        imageUrl: String? = null
    ) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return


        // notification icon
        val icon = R.drawable.care
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val resultPendingIntent =
            PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val mBuilder = NotificationCompat.Builder(mContext)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length > 4 && Patterns.WEB_URL.matcher(imageUrl)
                    .matches()
            ) {
                val bitmap = getBitmapFromURL(imageUrl)
                if (bitmap != null) {
                    showBigNotification(
                        bitmap,
                        mBuilder,
                        icon,
                        title,
                        message,
                        timeStamp,
                        resultPendingIntent,
                        alarmSound
                    )
                    //  playNotificationSound()
                } else {
                    showSmallNotification(
                        mBuilder,
                        icon,
                        title,
                        message,
                        timeStamp,
                        resultPendingIntent,
                        alarmSound
                    )
                    //playNotificationSound()
                }
            }

        } else {
            showSmallNotification(
                mBuilder,
                icon,
                title,
                message,
                timeStamp,
                resultPendingIntent,
                alarmSound
            )
            // playNotificationSound()
        }
        notificationReceiver!!.playNotificationSound(mContext)

    }


    private fun showSmallNotification(
        mBuilder: NotificationCompat.Builder,
        icon: Int,
        title: String,
        message: String,
        timeStamp: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri
    ) {

        val inboxStyle = NotificationCompat.InboxStyle()

        inboxStyle.addLine(message)
        val notification: Notification
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)
            .setSound(alarmSound)
            .setWhen(getTimeMilliSec(timeStamp))
            .setSmallIcon(R.drawable.care)
            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
            .setContentText(message)
            .build()

        val notificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1234",
                "Default channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            // channel.setSound(alarmSound,createAudioAttributes())
            val attributes: AudioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
            channel.setSound(alarmSound, attributes)
            notificationManager.createNotificationChannel(channel);
        }
        //  manager.notify(0, builder.build());
        notificationManager.notify(AppSP.NOTIFICATION_ID, notification)

        /* notificationEntity = NotificationEntity(
             0,
             1,
             title,
             1,
             validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
             validate!!.currentdate,
             validate!!.RetriveSharepreferenceInt(AppSP.iUserID)
         )*/


    }

    private fun showBigNotification(
        bitmap: Bitmap,
        mBuilder: NotificationCompat.Builder,
        icon: Int,
        title: String,
        message: String,
        timeStamp: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri
    ) {
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(bitmap)
        val notification: Notification
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)
            .setSound(alarmSound)
            .setStyle(bigPictureStyle)
            .setWhen(getTimeMilliSec(timeStamp))
            .setSmallIcon(R.drawable.care)
            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
            .setContentText(message)
            .build()

        val notificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(AppSP.NOTIFICATION_ID_BIG_IMAGE, notification)
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    // Playing notification sound
    fun playNotificationSound() {
        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(mContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        /* try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    companion object {

        private val TAG = NotificationUtils::class.java.getSimpleName()

        /**
         * Method checks if the app is in background or not
         */
        @SuppressLint("NewApi")
        fun isAppIsInBackground(context: Context): Boolean {
            var isInBackground = true
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                val runningProcesses = am.getRunningAppProcesses()
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.getPackageName()) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } else {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo.get(0).topActivity
                if (componentInfo!!.getPackageName() == context.getPackageName()) {
                    isInBackground = false
                }
            }

            return isInBackground
        }

        // Clears notification tray messages
        fun clearNotifications(context: Context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        fun getTimeMilliSec(timeStamp: String): Long {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                val date = format.parse(timeStamp)
                return date.getTime()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return 0
        }
    }


}
