package com.careindia.lifeskills.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.careindia.lifeskills.application.MyApplication

open class BaseViewModel : ViewModel() {
    protected val mContext: Context = MyApplication.myApplication
   // protected val userId = PrefManager.getInstance(mContext)?.getStringValue(PrefManager.USER_ID)

}