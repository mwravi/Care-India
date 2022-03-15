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
        dateform: Long?,
        hhuid: String?,
        imuid: String?,
        initials: String,
        locality: String,
        updatedBy: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
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
            imuid,
            initials,
            locality,
            updatedBy,
            UpdatedOn,
            IsEdited
        )
    }

    internal fun updateIMProfileDemographicData(
        IndGUID: String,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        alter_contact: String?,
        StateID: Int?,
        StateOther: String,
        ResidingSince: Int?,
        Read_Write: Int?,
        Education: Int??,
        Smartphone: Int?,
        MobileData: Int?,
        Updatedby: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
    ) {
        imProfileDao.updateIMProfileDemographicData(
            IndGUID,
            name,
            gender,
            age,
            caste,
            marital_status,
            contact,
            alter_contact,
            StateID,
            StateOther,
            ResidingSince,
            Read_Write,
            Education,
            Smartphone,
            MobileData,
            Updatedby,
            UpdatedOn,
            IsEdited
        )
    }

    internal fun updateIMProfileSecondData(
        IndGUID: String,
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
        updatedBy: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
    ) {
        imProfileDao.updateIMProfileSecondData(
            IndGUID,
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
            updatedBy,
            UpdatedOn,
            IsEdited
        )
    }

    fun getIdvProfiledatabyGuid(guid: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIdvProfiledatabyGuid(guid)
    }

    fun getIdvProfiledatabyGuidNew(guid: String): List<IndividualProfileEntity> {
        return imProfileDao.getIdvProfiledatabyGuidNew(guid)
    }

    fun gethhByGUIDDataZone(HHGUID: String?,zoneCode: Int, ward: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhByGUIDDataZone(HHGUID,zoneCode, ward)
    }

    fun gethhByGUIDDataPanchayat(HHGUID: String?,panchayat: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhByGUIDDataPanchayat(HHGUID,panchayat)
    }

    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return imProfileDao.gethhProfileData()
    }

    fun gethhByGUIDProfileDataWard(HHGUID: String?,ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhByGUIDProfileDataWard(HHGUID,ZoneCode, WardCode)
    }

    fun gethhByGUIDProfileDataPanchayat(HHGUID: String?,PanchayatCode: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhByGUIDProfileDataPanchayat(HHGUID,PanchayatCode)
    }
    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhProfileDataWard(ZoneCode, WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return imProfileDao.gethhProfileDataPanchayat(PanchayatCode)
    }

    internal fun updateIMProfileThirdData(
        IndGUID: String,
        wp_category: Int?,
        emp_type: Int?,
        waste_type: String?,
        waste_disposal: String?,
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
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
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
            updatedBy,
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
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        imProfileDao.updateIMProfileForthData(
            IndGUID,
            aadhaar,
            voter,
            pan,
            income_certificate,
            caste_certificate,
            bank_account,
            updatedBy,
            updated_on,
            IsEdited
        )
    }

    internal fun updateIMProfileFifthData(
        IndGUID: String,
        schemes_availed: Int?,
        scheme_details: String?,
        scheme_sp: String?,
        schemes_availed_cur: Int?,
        scheme_details_cur: String?,
        schemesp_Cur: String?,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        imProfileDao.updateIMProfileFifthData(
            IndGUID,
            schemes_availed,
            scheme_details,
            scheme_sp,
            schemes_availed_cur,
            scheme_details_cur,
            schemesp_Cur,
            updatedBy,
            updated_on,
            IsEdited
        )
    }

    internal fun updateIMProfileSixData(
        IndGUID: String,
        schemeSP_cur: String?,
        jobs: String?,
        interested_job: Int?,
        interested_jobDetail: String?,
        job: String?,
        member_collective: Int?,
        collective_name: String?,
        laboure_oth: String?,
        business_oth: String?,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        imProfileDao.updateIMProfileSixData(
            IndGUID,
            schemeSP_cur,
            jobs,
            interested_job,
            interested_jobDetail,
            job,
            member_collective,
            collective_name,
            laboure_oth,
            business_oth,
            updatedBy,
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

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode, DistrictIn)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode)
    }

    fun delete(individualProfileEntity: IndividualProfileEntity) {
        return imProfileDao.deleteindividualProfiledata(individualProfileEntity)
    }

    fun getIDPDisData(iPanchayat: Int, idiscode: Int,HHGUID:String?): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDPDisData(iPanchayat, idiscode,HHGUID)
    }
    fun getIDDisWData(idis: Int, izone: Int, iward: Int,HHGUID:String?): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDDisWData(idis, izone, iward,HHGUID)
    }
    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDZData(idiscode, izone)
    }


    fun getIDDistrictData(idiscode: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getIDDistrictData(idiscode)
    }


}