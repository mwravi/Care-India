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


    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallhhProfiledata(hhcode)
    }

    fun getallIdvdata(idvcode: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallIdvdata(idvcode)
    }

    fun getIDWData(izone: Int, iward: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDWData(izone, iward)
    }

    fun getIDZData(izone: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDZData(izone)
    }

    fun getIDPData(iPanchayat: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDPData(iPanchayat)
    }

    fun gethhDataZone(zoneCode: Int, ward: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhDataZone(zoneCode, ward)
    }

    fun gethhDataPanchayat(panchayat: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhDataPanchayat(panchayat)
    }

    fun getallProfiledata(): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallIMProfiledata()
    }

    fun delete(Guid: String) {
        imProfileDao.deleteAllData(Guid)
    }

    internal fun updateIMProfileData(
        IndGUID: String,
        crpname: String?,
        superverCor: String?,
        District1: Int?,
        Zone1: Int?,
        Ward1: Int?,
        Panchayat1: String?,
        dateform: String?,
        hhuid: String?,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        StateID: Int?,
        StateOther: String,
        ResidingSince: Int?,
        initials:String,
        UpdatedOn: String?,
        IsEdited:Int
    ) {
        imProfileDao.updateIMProfileData(
            IndGUID,
            crpname,
            superverCor,
            District1,
            Zone1,
            Ward1,
            Panchayat1,
            dateform,
            hhuid,
            name,
            gender,
            age,
            caste,
            marital_status,
            contact,
            StateID,
            StateOther,
            ResidingSince,
            initials,
            UpdatedOn,
            IsEdited
        )
    }

    internal fun updateIMProfileSecondData(
        IndGUID: String,
        Read_Write: Int?,
        Education: Int??,
        Smartphone: Int?,
        MobileData: Int?,
        Languages_Read: String?,
        Languages_Write: String?,
        Languages_Speak: String?,
        speak_other: String?,
        read_other: String?,
        write_other: String?,
        communi_other: String?,
        PreferredLanguage_Communication: String?,
        preferredLanguage_mobile: String?,
        preferMobile_other: String?,
        UpdatedOn: String?,
        IsEdited:Int
    ) {
        imProfileDao.updateIMProfileSecondData(
            IndGUID,
            Read_Write,
            Education,
            Smartphone,
            MobileData,
            Languages_Read,
            Languages_Write,
            Languages_Speak,
            speak_other,
            read_other,
            write_other,
            communi_other,
            PreferredLanguage_Communication,
            preferredLanguage_mobile,
            preferMobile_other,
            UpdatedOn,
            IsEdited
        )
    }

    fun getIdvProfiledatabyGuid(guid: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIdvProfiledatabyGuid(guid)
    }

    fun getIdvProfiledatabyGuidNew(guid: String): List<IndividualProfileEntity>{
        return imProfileDao.getIdvProfiledatabyGuidNew(guid)
    }

    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return imProfileDao.gethhProfileData()
    }

    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhProfileDataWard(ZoneCode,WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhProfileDataPanchayat(PanchayatCode)
    }

    internal fun updateIMProfileThirdData(
        IndGUID: String,
        wp_category: Int?,
        emp_type: Int?,
        waste_type: String?,
        waste_disposal: Int?,
        dispose_other: String?,
        primary_Occuptn: Int?,
        primary_occo_other: String?,
        primary_inc: Int?,
        primary_wd: Int?,
        issecdry_Occuptn: Int?,
        secondary_occupation: Int?,
        secondry_occup_other: String?,
        secondary_wd: Int?,
        secondary_inc: Int?,
        updated_on: String?,
        IsEdited:Int
    ) {
        imProfileDao.updateIMProfileThirdData(
            IndGUID,
            wp_category,
            emp_type,
            waste_type,
            waste_disposal,
            dispose_other,
            primary_Occuptn,
            primary_occo_other,
            primary_inc,
            primary_wd,
            issecdry_Occuptn,
            secondary_occupation,
            secondry_occup_other,
            secondary_wd,
            secondary_inc,
            updated_on,
            IsEdited
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
        updated_on: String?,
        IsEdited:Int
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
            updated_on,
            IsEdited
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
        updated_on: String?,
        IsEdited:Int
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
            updated_on,
            IsEdited
        )
    }

    fun getHHCount(): Int {
        return imProfileDao.getHHCount()
    }

    fun getIndividualID(IndvCode: String): Int {
        return imProfileDao.getIndividualID(IndvCode)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode)
    }

    fun delete(individualProfileEntity: IndividualProfileEntity) {
        return imProfileDao.deleteindividualProfiledata(individualProfileEntity)
    }

}