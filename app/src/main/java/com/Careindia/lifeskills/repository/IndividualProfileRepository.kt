package com.careindia.lifeskills.repository

import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.IndividualProfileDao
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity

class IndividualProfileRepository(
    private val imProfileDao: IndividualProfileDao,
    private val mstDistrictDao: MstDistrictDao
) {

    fun insert(individualProfileEntity: IndividualProfileEntity) {
        imProfileDao.insertIMProfileData(individualProfileEntity)
    }

//    fun update(individualProfileEntity: IndividualProfileEntity){
//        imProfileDao.updateIMProfileData(individualProfileEntity)
//    }


    fun getallhhProfiledata(hhcode: String):  LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallhhProfiledata(hhcode)
    }
 fun getallIdvdata(idvcode: String):  LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallIdvdata(idvcode)
    }

    fun getallProfiledata(): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallIMProfiledata()
    }

    fun delete(Guid: String) {
        imProfileDao.deleteAllData(Guid)
    }

    internal fun updateIMProfileData(
        IndGUID: String,
        dateform: String?,
        hhuid: String?,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        updated_on: String?
    ) {
        imProfileDao.updateIMProfileData(
            IndGUID,
            dateform,
            hhuid,
            name,
            gender,
            age,
            caste,
            marital_status,
            contact,
            updated_on
        )
    }

    internal fun updateIMProfileSecondData(
        IndGUID: String,
        StateID: Int?,
        ResidingSince: Int?,
        Read_Write: Int?,
        Education: Int??,
        Smartphone: Int?,
        MobileData: Int?,
        Languages_Read: String?,
        Languages_Write: String?,
        Languages_Speak: String?,
        PreferredLanguage_Communication: String?,
        UpdatedOn: String?
    ) {
        imProfileDao.updateIMProfileSecondData(
            IndGUID,
            StateID,
            ResidingSince,
            Read_Write,
            Education,
            Smartphone,
            MobileData,
            Languages_Read,
            Languages_Write,
            Languages_Speak,
            PreferredLanguage_Communication,
            UpdatedOn
        )
    }

    fun getIdvProfiledatabyGuid(guid: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIdvProfiledatabyGuid(guid)
    }

 fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return imProfileDao.gethhProfileData()
    }

    fun gethhProfileDataNew(): List<HouseholdProfileEntity> {
        return imProfileDao.gethhProfileDataNew()
    }
    internal fun updateIMProfileThirdData(
        IndGUID: String,
        preferredLanguage_mobile: String?,
        wp_category: Int?,
        emp_type: Int?,
        waste_type: String?,
        waste_disposal: Int?,
        primary_Occuptn: Int?,
        primary_inc:Int?,
        primary_wd:Int?,
        issecdry_Occuptn: Int?,
        secondary_occupation: Int?,
        updated_on: String?
    ) {
        imProfileDao.updateIMProfileThirdData(
            IndGUID,
            preferredLanguage_mobile,
            wp_category,
            emp_type,
            waste_type,
            waste_disposal,
            primary_Occuptn,
            primary_inc,
            primary_wd,
            issecdry_Occuptn,
            secondary_occupation,
            updated_on
        )
    }


    internal fun updateIMProfileForthData(
        IndGUID: String,
        aadhaar: Int?,
        voter: Int?,
        pan: Int?,
        income_certificate: Int?,
        caste_certificate: Int?,
        bank_account: Int?,
        schemes_availed: Int?,
        scheme_details: String?,
        secondary_wd: Int?,
        secondary_inc: Int?,
        updated_on: String?
    ) {
        imProfileDao.updateIMProfileForthData(
            IndGUID,
            aadhaar,
            voter,
            pan,
            income_certificate,
            caste_certificate,
            bank_account,
            schemes_availed,
            scheme_details,
            secondary_wd,
        secondary_inc,
            updated_on
        )
    }

    internal fun updateIMProfileFifthData(
        IndGUID: String,
        schemes_availed_cur: Int?,
        scheme_details_cur: String?,
        schemeSP_cur: String?,
        jobs: String?,
        interested_job: Int?,
        interested_jobDetail: String?,
        member_collective: Int?,
        collective_name: String?,
        updated_on: String?
    ) {
        imProfileDao.updateIMProfileFifthData(
            IndGUID,
            schemes_availed_cur,
            scheme_details_cur,
            schemeSP_cur,
            jobs,
            interested_job,
            interested_jobDetail,
            member_collective,
            collective_name,
            updated_on
        )
    }
    fun getHHCount(): Int {
        return imProfileDao.getHHCount()
    }

    fun getMstDist(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode)
    }

    fun delete(individualProfileEntity: IndividualProfileEntity) {
        return imProfileDao.deleteindividualProfiledata(individualProfileEntity)
    }

}