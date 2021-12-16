package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface IndividualProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIMProfileData(imProfileEntity: IndividualProfileEntity)


    @Query("update tblProfileIndividual set Initials=:initials, StateID=:stateid,ResidingSince =:residing_since,State_Other=:stateOther, PWCode=:Panchayat1, Panchayat_Ward=:Ward1, ZoneCode=:Zone1, DistrictCode=:District1, Name_SC=:superverCor, Name_CRP=:crpname, Contact =:contact,MaritalStatus =:marital_status,Caste=:caste, Age=:age, Gender=:gender, Name=:name, HHCode=:hhuid,DateForm =:dateform,UpdatedOn=:UpdatedOn,IsEdited=:IsEdited where IndGUID=:IndGUID")
    fun updateIMProfileData(
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
        stateid: Int?,
        stateOther: String,
        residing_since: Int?,
        initials:String,
        UpdatedOn: String?,
        IsEdited:Int
    )

    @Query("Select * from tblProfileIndividual")
    fun getallIMProfiledata(): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual")
    fun getIMProfileAlldata(): List<IndividualProfileEntity>


    @Query("Select * from tblProfileIndividual where IndGUID=:Guid")
    fun getIdvProfiledatabyGuid(Guid: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where IndGUID=:Guid")
    fun getIdvProfiledatabyGuidNew(Guid: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileHH")
    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>>

    @Query("Select * from tblProfileIndividual where HHCode=:hhcode")
    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where IndvCode=:indvcode")
    fun getallIdvdata(indvcode: String): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where ZoneCode=:izone and Panchayat_Ward=:iward")
    fun getIDWData(izone: Int, iward: Int): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where ZoneCode=:izone")
    fun getIDZData(izone: Int): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where Panchayat_Ward=:iPanchayat")
    fun getIDPData(iPanchayat: Int): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileHH where zoneCode=:zoneCode and Panchayat_Ward=:ward")
    fun gethhDataZone(zoneCode: Int, ward: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:panchayat")
    fun gethhDataPanchayat(panchayat: Int): List<HouseholdProfileEntity>

    @Query("DELETE from tblProfileIndividual where IndGUID=:Guid")
    fun deleteAllData(Guid: String)

    @Query("Select * from tblProfileHH where ZoneCode=:ZoneCode and Panchayat_Ward=:WardCode")
    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:PanchayatCode")
    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity>

    @Query("update tblProfileIndividual set PreferredLanguage_Mobile =:preferredLanguage_mobile,Mobile_Other=:preferMobile_other,Prefer_Commu_Other=:communi_other, Write_Other=:write_other,Read_Other=:read_other,Speak_Other=:speak_other,  Smartphone =:smartphone,MobileData=:mobileData,PreferredLanguage_Communication=:preferredLanguage_communication,Languages_Speak=:languages_speak,Languages_Write =:languages_write,Languages_Read=:languages_read,Read_Write =:read_write,Education =:education,UpdatedOn=:updated_on,IsEdited=:IsEdited where IndGUID=:IndGUID")
    fun updateIMProfileSecondData(
        IndGUID: String,
        read_write: Int?,
        education: Int?,
        smartphone: Int?,
        mobileData: Int?,
        languages_read: String?,
        languages_write: String?,
        languages_speak: String?,
        speak_other: String?,
        read_other: String?,
        write_other: String?,
        communi_other: String?,
        preferredLanguage_communication: String?,
        preferredLanguage_mobile: String?,
        preferMobile_other: String?,
        updated_on: String?,
        IsEdited:Int
    )


    @Query("update tblProfileIndividual set Secondary_Inc=:secondary_inc, Secondary_WD=:secondary_wd,Sec_Inc_Other=:secondry_occup_other, Primary_Inc_Other=:primary_occo_other, Dispose_Other=:dispose_other,Primary_WD=:primary_wd, Primary_Inc =:primary_inc,Secondary_Occupation=:secondary_occupation,IsSecondary_Occupation=:issecdry_Occuptn, Primary_Occupation=:primary_Occuptn, Waste_Disposal=:waste_disposal,Employment_Type=:emp_type,Waste_Type=:waste_type,WP_category=:wp_category,UpdatedOn=:updated_on,IsEdited=:IsEdited where IndGUID=:IndGUID")
    fun updateIMProfileThirdData(
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
    )

    @Query("update tblProfileIndividual set SchemeDetails=:scheme_details,SchemesAvailed=:schemes_availed, Aadhaar=:aadhaar, Voter=:voter,PAN=:pan,IncomeCertificate=:income_certificate,CasteCertificate =:caste_certificate,BankAccount=:bank_account,UpdatedOn=:updated_on,IsEdited=:IsEdited where IndGUID=:IndGUID")
    fun updateIMProfileForthData(
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
    )

    @Query("update tblProfileIndividual set Collective_name=:collective_name, Member_Collective =:member_collective,Interested_JobDetail=:interested_jobDetail, Interested_Job =:interested_job, Skills_oth=:skills_othr, Skills=:skills, SchemeDetails_Cur=:scheme_details_cur, SchemesAvailed_Cur=:schemes_availed_cur,UpdatedOn=:updated_on,IsEdited=:IsEdited where IndGUID=:IndGUID")
    fun updateIMProfileFifthData(
        IndGUID: String,
        schemes_availed_cur: Int?,
        scheme_details_cur: String?,
        skills: String?,
        skills_othr: String?,
        interested_job: Int?,
        interested_jobDetail: String?,
        member_collective: Int?,
        collective_name: String?,
        updated_on: String?,
        IsEdited:Int
    )

    @Query("select Count(HHCode) from tblProfileIndividual")
    fun getHHCount(): Int

    @Query("select Count(*) from tblProfileIndividual where IndvCode=:IndvCode")
    fun getIndividualID(IndvCode: String): Int

    @Delete
    fun deleteindividualProfiledata(individualProfileEntity: IndividualProfileEntity)

    @Query("Select * from tblProfileIndividual where HHGUID=:hhguid")
    fun getallIMProfileBYHHGUIDdata(hhguid: String): List<IndividualProfileEntity>

}