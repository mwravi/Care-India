package com.careindia.lifeskills.views.loginscreen

import androidx.lifecycle.liveData
import com.careindia.lifeskills.R
import com.careindia.lifeskills.network.Resource
import com.careindia.lifeskills.network.StatusCode
import com.careindia.lifeskills.utils.PrefManager
import com.careindia.lifeskills.views.base.BaseViewModel
import com.mymuhammadiyah.app.network.ApiFailure
import isValidEmail
import isValidPasswordLength
import kotlinx.coroutines.Dispatchers
import showLongToast
import showShortToast

class LoginViewModel(val apiRequest: LoginRequest, val loginRepository: LoginRepository) :
    BaseViewModel() {


    /**
     * This method is being used to call login API.*****
     */
    fun loginApiData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val apiResponse = loginRepository.getLoginApi(apiRequest)
            when (apiResponse.code) {
                StatusCode.SUCCESS -> {
                    saveUserData(apiResponse.message)
                    emit(Resource.success(apiResponse))

                }
                else -> {
                    emit(Resource.error(apiResponse.errorMessage, null))
                }
            }

        } catch (error: Exception) {
            emit(Resource.error(ApiFailure.getCustomErrorMessage(error), null))
        }


    }

    private fun saveUserData(message: RegistrationResponse.Message) {
        PrefManager.getInstance(mContext)
            ?.saveStringValue(
                PrefManager.USER_ID,
                message.userId.toString()
            )
        PrefManager.getInstance(mContext)
            ?.saveStringValue(
                PrefManager.USER_NAME,
                message.name.toString()
            )

        PrefManager.getInstance(mContext)
            ?.savePreferencesBoolean(
                PrefManager.IS_LOGIN,
                true
            )
    }


    /**
     * This method is being used to check field validation
     */
    fun isValid(): Boolean {
        return if (apiRequest.email.isNullOrBlank()) {
            mContext.getString(R.string.empty_email).showShortToast(mContext)
            false
        } else if (!apiRequest.email?.isValidEmail()!!) {
            mContext.getString(R.string.empty_email).showShortToast(mContext)
            false
        } else if (apiRequest.password!!.isNullOrBlank()) {
            mContext.getString(R.string.empty_email).showLongToast(mContext)
            false
        } else if (!apiRequest.password!!.isValidPasswordLength()) {
            mContext.getString(R.string.empty_email).showLongToast(mContext)
            false
        } else {
            true
        }
    }
}