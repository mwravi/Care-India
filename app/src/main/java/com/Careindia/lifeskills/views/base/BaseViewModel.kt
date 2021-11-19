package com.careindia.lifeskills.views.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.careindia.lifeskills.application.CareIndiaApplication

open class BaseViewModel : ViewModel() {
    protected val mContext: Context = CareIndiaApplication.myApplication
   // protected val userId = PrefManager.getInstance(mContext)?.getStringValue(PrefManager.USER_ID)

}