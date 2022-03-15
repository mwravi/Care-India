package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.BeneficiaryEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.BeneficiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeneficiaryViewModel: AndroidViewModel {
    var beneficiaryRepository: BeneficiaryRepository? = null

    constructor(application: Application) : super(application) {
        beneficiaryRepository = BeneficiaryRepository(application)
    }
    fun beneficiaryProgressData(): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getallProfiledata()
    }

    fun update(
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
        viewModelScope.launch(Dispatchers.IO) {
            beneficiaryRepository!!.updatebeneficiaryData(
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
    }


    fun insert(beneficiaryEntity: BeneficiaryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            beneficiaryRepository!!.insert(beneficiaryEntity)
        }
    }

    fun deleteBeneficiary(beneficiaryEntity: BeneficiaryEntity) {
        viewModelScope.launch {
            beneficiaryRepository!!.delete(beneficiaryEntity)
        }
    }


    fun getbeneficiarydatabyGuid(guid: String): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getbeneficiarydatabyGuid(guid)
    }


    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return beneficiaryRepository!!.getMstDist(StateCode, DistrictIn)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return beneficiaryRepository!!.getMstDist(StateCode)
    }


    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getIDPDisData(iPanchayat, idiscode)
    }
    fun getBeneficiaryList(indv_guid:String): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getBeneficiaryList(indv_guid)
    }

    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getIDZData(idiscode, izone)
    }

    fun getIDDistrictData(idiscode: Int): LiveData<List<BeneficiaryEntity>> {
        return beneficiaryRepository!!.getIDDistrictData(idiscode)
    }

    fun getBendatabyIndGuid(guid: String): List<BeneficiaryEntity> {
        return beneficiaryRepository!!.getBendatabyIndGuid(guid)
    }

    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity> {
        return beneficiaryRepository!!.getINDIDdata(indGUID)
    }

}