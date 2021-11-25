package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.repository.MstPanchayatWardRepository

class MstPanchayatWardViewModel : AndroidViewModel {
    var mstPanchayatWardRepository: MstPanchayatWardRepository? = null

    constructor(application: Application) : super(application) {
        mstPanchayatWardRepository = MstPanchayatWardRepository(application)
    }

    fun getMstUser(StateCode:Int,DistrictCode:Int,ZoneCode:Int): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardRepository!!.getMstPanchayat_Ward(StateCode,DistrictCode,ZoneCode)
    }



}