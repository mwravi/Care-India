package com.careindia.lifeskills.repository

import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.IndividualProfileDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity

class IndividualProfileRepository(
    private val imProfileDao: IndividualProfileDao,
    private val mstCommonDao: MstCommonDao
) {

    fun insert(individualProfileEntity: IndividualProfileEntity) {
        imProfileDao.insertIMProfileData(individualProfileEntity)
    }

//    fun update(individualProfileEntity: IndividualProfileEntity){
//        imProfileDao.updateIMProfileData(individualProfileEntity)
//    }

    fun getmstCommonData(flag: Int): List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
    }

    fun getallProfiledata(): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallIMProfiledata()
    }

    fun delete() {
        imProfileDao.deleteAllData()
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


    internal fun updateIMProfileThirdData(
        IndGUID: String,
        preferredLanguage_mobile: String?,
        wp_category: Int?,
        emp_type: Int?,
        waste_type: String?,
        waste_disposal: Int?,
        primary_Occuptn: Int?,
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

}