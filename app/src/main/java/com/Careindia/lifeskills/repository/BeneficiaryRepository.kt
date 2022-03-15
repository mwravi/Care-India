package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.BeneficiaryProgressDao
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.BeneficiaryEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity

class BeneficiaryRepository {
    var beneficiaryDao: BeneficiaryProgressDao? = null
    var mstDistrictDao: MstDistrictDao? = null

    constructor(application: Application) {
        beneficiaryDao = CareIndiaApplication.database?.beneficiaryDao()
        mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()
    }

    fun insert(beneficiaryEntity: BeneficiaryEntity) {
        beneficiaryDao!!.insertBeneficiaryData(beneficiaryEntity)
    }

    fun getallProfiledata(): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getallbeneficiarydata()
    }


    internal fun updatebeneficiaryData(
        bene_GUID: String,
        col_GUID: String,
        dateform: Long?,
        crpname: Int?,
        superverCor: Int?,
        District1: Int?,
        Zone1: Int?,
        Ward1: Int?,
        Panchayat1: String?,
        locality: String,
        nameCollective: String,
        rolecollective: Int?,
        rolecollectiveOthr: String?,
        updatedBy: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
    ) {
        beneficiaryDao!!.updatebeneficiaryData(
            bene_GUID,
            col_GUID,
            dateform,
            crpname,
            superverCor,
            District1,
            Zone1,
            Ward1,
            Panchayat1,
            locality,
            nameCollective,
            rolecollective,
            rolecollectiveOthr,
            updatedBy,
            UpdatedOn,
            IsEdited
        )
    }

    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getIDPDisData(iPanchayat, idiscode)
    }
    fun getBeneficiaryList(indv_guid:String): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getBeneficiaryList(indv_guid)
    }


    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode, DistrictIn)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode)
    }

    fun delete(beneficiaryEntity: BeneficiaryEntity) {
        return beneficiaryDao!!.deleteibeneficiarydata(beneficiaryEntity)
    }

    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getIDZData(idiscode, izone)
    }


    fun getIDDistrictData(idiscode: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getIDDistrictData(idiscode)
    }

    fun getbeneficiarydatabyGuid(guid: String): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryDao!!.getbeneficiarydatabyGuid(guid)
    }
    fun getBendatabyIndGuid(guid: String): List<BeneficiaryEntity> {
        return beneficiaryDao!!.getBendatabyIndGuid(guid)
    }
    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity> {
        return beneficiaryDao!!.getINDIDdata(indGUID)
    }

}