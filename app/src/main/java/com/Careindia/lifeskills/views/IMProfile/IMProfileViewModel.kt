package com.careindia.lifeskills.views.improfile



import com.careindia.lifeskills.views.base.BaseViewModel

class IMProfileViewModel (val apiRequest: IMProfileRequest):BaseViewModel() {


    /**
     * This method is being used to check field validation
     */
    fun isValid(): Boolean {
        return if (apiRequest.email.isNullOrBlank()) {
//            mContext.getString(R.string.empty_email).showShortToast(mContext)
            false
        } else if (!apiRequest.email?.isNullOrBlank()!!) {
//            mContext.getString(R.string.empty_email).showShortToast(mContext)
            false
        } else if (apiRequest.password!!.isNullOrBlank()) {
//            mContext.getString(R.string.err_password).showLongToast(mContext)
            false

        } else {
            true
        }
    }
}

