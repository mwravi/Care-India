package com.careindia.lifeskills.firebase


import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by nadim on 18/7/2019.
 * Copyright to Mobulous Technology Pvt. Ltd.
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var notificationUtils: NotificationUtils? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage == null)
            return
        try {
            handleMyDataMessage(remoteMessage)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun sendBroadcastMessage() {
        val intent = Intent(ACTION_LOCATION_BROADCAST)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    @Throws(Exception::class)
    private fun handleMyDataMessage(remoteMessage: RemoteMessage) {
        if (NotificationUtils.isAppIsInBackground(applicationContext)) {
            // app is in foreground, broadcast the push message
            val pushNotification = Intent(AppSP.PUSH_NOTIFICATION)
            /* pushNotification.putExtra(kBody, body);
             pushNotification.putExtra(kTitle, "");*/

            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
            // play notification sound
            val notificationUtils = NotificationUtils(applicationContext)
            notificationUtils.playNotificationSound()
//            val jsonObject = JSONObject(remoteMessage.notification.toString())
            /*  if(remoteMessage.data != null) {
                  var serviceAskedFor_GUID = remoteMessage.data.get("ServiceAskedFor_GUID")
                  var StatusId = remoteMessage.data.get("StatusId")
                  SaveMessage(serviceAskedFor_GUID, StatusId)
              }*/
        } else {
            var OrderGUID = ""
            var ServiceRaisedGUID = ""
//            val jsonObject = JSONObject(remoteMessage.data.toString())
//            val resultIntent = Intent(applicationContext, SplashScreen::class.java)

//            if(remoteMessage.data != null) {
////                var serviceAskedFor_GUID = remoteMessage.data.get("ServiceAskedFor_GUID")
////                var StatusId = remoteMessage.data.get("StatusId")
////                SaveMessage(serviceAskedFor_GUID, StatusId)
//                      OrderGUID = remoteMessage.data.get("OrderGUID").toString()
//                      ServiceRaisedGUID = remoteMessage.data.get("ServiceRaisedGUID").toString()
//
//            }

            val resultIntent = Intent(applicationContext, HomeDashboardActivity::class.java)
//            resultIntent.putExtra("OrderGUID",OrderGUID)
//            resultIntent.putExtra("ServiceRaisedGUID",ServiceRaisedGUID)
            startService(resultIntent)

            showNotificationMessage(
                applicationContext,
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!,
                "",
                resultIntent
            )
        }
    }

    private fun convertIntoInteger(value: String): Int? {
        var intvalue: Int? = 0
        try {
            intvalue = Integer.parseInt(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return intvalue
    }

    /**
     * Showing notification with text only
     */
    private fun showNotificationMessage(
        context: Context,
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent
    ) {
        notificationUtils = NotificationUtils(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent)

        //sendBroadcastMessage();
    }


    /**
     * Showing notification with text and image
     */
    private fun showNotificationMessageWithBigImage(
        context: Context,
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent,
        imageUrl: String
    ) {
        notificationUtils = NotificationUtils(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent, imageUrl)
    }

    companion object {

        private val TAG = "PUSH"
        val ACTION_LOCATION_BROADCAST =
            MyFirebaseMessagingService::class.java.name + "NotificationsCountBroadcast"
    }

//    fun SaveMessage(serviceAskedFor_GUID: String?, statusId: String?) {
//        try {
//            WecareApplication?.appDatabase!!.serviceDao()?.updatestatus(
//                statusId!!.toInt(),
//                serviceAskedFor_GUID,
//                validate!!.currentdate,
//                validate!!.RetriveSharepreferenceInt(AppSP.UserID)
//            )
//
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
//
//    }

}
