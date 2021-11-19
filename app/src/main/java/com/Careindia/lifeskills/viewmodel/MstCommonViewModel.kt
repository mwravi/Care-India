package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.MstCommonRepository

class MstCommonViewModel : AndroidViewModel {
    var mstCommonRepository: MstCommonRepository? = null

    constructor(application: Application) : super(application) {
        mstCommonRepository = MstCommonRepository(application)
    }

    fun getMstCommon(flag: Int): List<MstCommonEntity> {
        return mstCommonRepository!!.getMstCommon(flag)
    }

    fun getCount(): Int {
        return mstCommonRepository!!.getCount()
    }


}