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

    fun getMstUser(
        StateCode: Int,
        DistrictCode: Int,
        ZoneCode: Int
    ): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardRepository!!.getMstPanchayat_Ward(StateCode, DistrictCode, ZoneCode)
    }

    fun getMstWard(ZoneCode: Int,PanchayatIn: List<String>): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardRepository!!.getMstWard(ZoneCode,PanchayatIn)
    }

    fun getMstWard(ZoneCode: Int): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardRepository!!.getMstWard(ZoneCode)
    }

    fun getMstPanchayat(disCode: Int,WardIn: List<String>): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardRepository!!.getMstPanchayat(disCode,WardIn)
    }

    fun getMstPanchayat(disCode: Int): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardRepository!!.getMstPanchayat(disCode)
    }
    fun getMstPanchayat(disCode: Int,WardCode:Int): String {
        return mstPanchayatWardRepository!!.getMstPanchayat(disCode,WardCode)
    }

    fun getMstWard(ZoneCode: Int,WardCode:Int): String {
        return mstPanchayatWardRepository!!.getMstWard(ZoneCode,WardCode)
    }

}