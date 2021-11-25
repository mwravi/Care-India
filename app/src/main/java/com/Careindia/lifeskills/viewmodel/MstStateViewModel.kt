package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstStateEntity
import com.careindia.lifeskills.repository.MstStateRepository

class MstStateViewModel : AndroidViewModel {
    var mstStateRepository: MstStateRepository? = null

    constructor(application: Application) : super(application) {
        mstStateRepository = MstStateRepository(application)
    }

    fun getMstUser(): List<MstStateEntity> {
        return mstStateRepository!!.getMstState()
    }



}