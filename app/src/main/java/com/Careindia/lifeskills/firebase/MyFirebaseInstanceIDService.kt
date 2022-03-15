package com.careindia.lifeskills.firebase

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.v(TAG, "sendRegistrationToServer: $refreshedToken")

        //saving token to shared preference
        //   BaseManager.saveDataIntoPreferences(refreshedToken,kDeviceToken);

    }

    companion object {

        private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
    }


}
