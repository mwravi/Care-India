package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.MstDistrictRepository

class MstDistrictViewModel : AndroidViewModel {
    var mstDistrictRepository: MstDistrictRepository? = null

    constructor(application: Application) : super(application) {
        mstDistrictRepository = MstDistrictRepository(application)
    }

    fun getMstDistrict(StateCode:Int,DistrictIn:List<String>): List<MstDistrictEntity> {
        return mstDistrictRepository!!.getMstDistrict(StateCode,DistrictIn)
    }

    fun getMstDistrict(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictRepository!!.getMstDistrict(StateCode)
    }

    fun getMstDistrictLive(StateCode:Int,DistrictIn:List<String>): LiveData<List<MstDistrictEntity>> {
        return mstDistrictRepository!!.getMstDistrictLive(StateCode,DistrictIn)
    }

    fun getMstDistrict(): List<MstDistrictEntity> {
        return mstDistrictRepository!!.getMstDistrict()
    }

    fun getMstDistrict(StateCode:Int,DistrictCode:Int): String {
        return mstDistrictRepository!!.getMstDistrict(StateCode,DistrictCode)
    }
}