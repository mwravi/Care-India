package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.MstLookupRepository

class MstLookupViewModel : AndroidViewModel {
    var mstLookupRepository: MstLookupRepository? = null

    constructor(application: Application) : super(application) {
        mstLookupRepository = MstLookupRepository(application)
    }

    fun getMstUser(flag:Int,iLanguage:Int): List<MstLookupEntity> {
        return mstLookupRepository!!.getMstLookup(flag,iLanguage)
    }



}