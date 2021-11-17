package com.careindia.lifeskills.views.IMProfile



import com.careindia.lifeskills.ui.base.BaseViewModel

class IMProfileViewModel (val apiRequest: IMProfileRequest):BaseViewModel() {


    /**
     * This method is being used to check field validation
     */
    fun isValid(): Boolean {
        return if (apiRequest.email.isNullOrBlank()) {
//            mContext.getString(R.string.empty_email).showShortToast(mContext)
            false
        } else if (!apiRequest.name?.isNullOrBlank()!!) {
//            mContext.getString(R.string.invalid_email).showShortToast(mContext)
            false
        } else if (apiRequest.password!!.isNullOrBlank()) {
//            mContext.getString(R.string.err_password).showLongToast(mContext)
            false

        } else {
            true
        }
    }
}

