package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.MstLookupRepository

class MstLookupViewModel : AndroidViewModel {
    var mstLookupRepository: MstLookupRepository? = null

    constructor(application: Application) : super(application) {
        mstLookupRepository = MstLookupRepository(application)
    }

    fun getMstLookup(flag:Int,iLanguage:Int): LiveData<List<MstLookupEntity>> {
        return mstLookupRepository!!.getMstLookup(flag,iLanguage)
    }

    fun getLookup(flag:Int,iLanguage:Int): List<MstLookupEntity> {
        return mstLookupRepository!!.getLookup(flag,iLanguage)
    }

    fun getMstAllData(flag: Int, iLanguage: Int): List<MstLookupEntity> {
        return mstLookupRepository!!.getMstDataLookup(flag, iLanguage)
    }

    fun getMstLookupFlag(flag: Int): List<MstLookupEntity> {
        return mstLookupRepository!!.getMstLookupdata(flag)
    }

    fun getMstUser(flag: Int, iLanguage: Int): LiveData<List<MstLookupEntity>> {
        return mstLookupRepository!!.getMstLookup(flag, iLanguage)
    }
}