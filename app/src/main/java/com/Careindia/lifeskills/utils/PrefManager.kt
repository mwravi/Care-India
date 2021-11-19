package com.careindia.lifeskills.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.careindia.lifeskills.application.CareIndiaApplication.Companion.myApplication


@SuppressLint("ALL")
class PrefManager private constructor(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("MUHAMMADIYAH_DONATION", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    /**
     * PrefManager constructor
     *
     * @param context current application context
     */
    init {
        editor = preferences.edit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    fun getStringValue(key: String): String? {
        return preferences.getString(key, "")
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    fun saveStringValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun savePreferencesBoolean(key: String, value: Boolean) {
        setBoolean(key, value)
    }

    fun getPreferencesBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * Removes all the fields from SharedPrefs
     */
    fun clearPrefs() {
        savePreferencesBoolean(IS_LOGIN, false)
        savePreferencesBoolean(IS_SYNC, false)
        saveStringValue(USER_ID, "")
        saveStringValue(AUTH_TOKEN, "")


    }

    /**
     * Remove the preference for the particular key
     *
     * @param key : Key for which the preference to be cleared.
     */
    fun removeFromPreference(key: String) {
        editor.remove(key)
        editor.commit()
    }

    companion object {

        const val FCM_TOKEN = "fcmToken"
        const val IS_LOGIN = "isLogin"
        const val USER_ID = "userId"
        const val AUTH_TOKEN = "authToken"
        const val IS_SYNC = "isSync"
        const val IS_WELCOME_DONE = "isWelcomeDone"
        const val USER_NAME="name"
        const val EMAIL="email"
        const val MOBILE="mobile"
        const val COUNTRY_CODE="country_code"
        const val SOCIAL_PROVIDER="social_provider"
        const val PROFILE_PHOTO="profile_phot"

        private var instance: PrefManager? = null

        /**
         * get single instance
         *
         * @param context current application context
         * @return instance of PrefManager
         */
        @JvmStatic
        fun getInstance(context: Context): PrefManager? {
            if (instance == null) {
                instance = PrefManager(context)
            }
            return instance
        }

        fun isLogin(): Boolean {
            return PrefManager.getInstance(myApplication)!!.getPreferencesBoolean(IS_LOGIN)
        }
    }


}