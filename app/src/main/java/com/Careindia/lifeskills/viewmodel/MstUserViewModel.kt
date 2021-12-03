package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstUserEntity
import com.careindia.lifeskills.repository.MstUserRepository

class MstUserViewModel : AndroidViewModel {
    var mstUserRepository: MstUserRepository? = null

    constructor(application: Application) : super(application) {
        mstUserRepository = MstUserRepository(application)
    }

    fun getMstUser(): List<MstUserEntity> {
        return mstUserRepository!!.getMstUser()
    }

    fun getusersCount():Int{
        return mstUserRepository!!.getusersCount()
    }



}