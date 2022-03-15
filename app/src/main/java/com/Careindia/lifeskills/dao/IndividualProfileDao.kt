package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface IndividualProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIMProfileData(imProfileEntity: IndividualProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(imProfileEntity: List<IndividualProfileEntity>?)

    @Query("update tblProfileIndividual set UpdatedBy=:updatedBy, Locality=:locality, IndvCode=:imuid, Initials=:initials, PWCode=:Panchayat1, Panchayat_Ward=:Ward1, ZoneCode=:Zone1, DistrictCode=:District1, Name_SC=:superverCor, Name_CRP=:crpname,  HHCode=:hhuid,DateForm =:dateform,UpdatedOn=:UpdatedOn,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileData(
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
    )

    @Query("DELETE from tblProfileIndividual")
    fun deleteIMProfiledata()

    @Query("Select * from tblProfileIndividual")
    fun getallIMProfiledata(): LiveData<List<IndividualProfileEntity>>

    @Query("update tblProfileIndividual set IsEdited=:isEdited where IndGUID=:indvGUID")
    fun updateIMIsEditeddata(indvGUID: String, isEdited: Int?)

    @Query("Select * from tblProfileIndividual where isEdited = 1")
    fun getIMProfileAlldata(): List<IndividualProfileEntity>


    @Query("Select * from tblProfileIndividual where IndGUID=:Guid")
    fun getIdvProfiledatabyGuid(Guid: String): LiveData<List<IndividualProfileEntity>>


    @Query("Select IndvCode from tblProfileIndividual where IndGUID=:Guid")
    fun getIdvPrfbyGuid(Guid: String): String


    @Query("Select * from tblProfileIndividual where IndGUID=:Guid")
    fun getIdvProfiledatabyGuidNew(Guid: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileHH where zoneCode=:zoneCode and Panchayat_Ward=:ward and HHGUID=:HHGUID")
    fun gethhByGUIDDataZone(HHGUID: String?, zoneCode: Int, ward: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:panchayat and HHGUID=:HHGUID")
    fun gethhByGUIDDataPanchayat(HHGUID: String?, panchayat: Int): List<HouseholdProfileEntity>

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

    @Query("Select * from tblProfileHH where ZoneCode=:ZoneCode and Panchayat_Ward=:WardCode and HHGUID=:HHGUID")
    fun gethhByGUIDProfileDataWard(
        HHGUID: String?,
        ZoneCode: Int,
        WardCode: Int
    ): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:PanchayatCode and HHGUID=:HHGUID")
    fun gethhByGUIDProfileDataPanchayat(
        HHGUID: String?,
        PanchayatCode: Int
    ): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where ZoneCode=:ZoneCode and Panchayat_Ward=:WardCode")
    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity>

    @Query("Select * from tblProfileHH where Panchayat_Ward=:PanchayatCode")
    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity>

    @Query("update tblProfileIndividual set UpdatedBy=:updatedBy,PreferredLanguage_Mobile =:preferredLanguage_mobile,Mobile_Other=:preferMobile_other,Prefer_Commu_Other=:communi_other, Write_Other=:write_other,Read_Other=:read_other,Speak_Other=:speak_other, PreferredLanguage_Communication=:preferredLanguage_communication,Languages_Speak=:languages_speak,Languages_Write =:languages_write,Languages_Read=:languages_read,UpdatedOn=:updated_on,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileSecondData(
        IndGUID: String,
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
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    )

    @Query("update tblProfileIndividual set UpdatedBy=:Updatedby, Alt_contact=:alter_contact, Read_Write =:read_write,Education =:education,Smartphone =:smartphone,MobileData=:mobileData, Contact =:contact,MaritalStatus =:marital_status,Caste=:caste, Age=:age, Gender=:gender, Name=:name, StateID=:stateid,ResidingSince =:residing_since,State_Other=:stateOther,UpdatedOn=:updated_on,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileDemographicData(
        IndGUID: String,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        alter_contact: String?,
        stateid: Int?,
        stateOther: String,
        residing_since: Int?,
        read_write: Int?,
        education: Int?,
        smartphone: Int?,
        mobileData: Int?,
        Updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    )


    @Query("update tblProfileIndividual set UpdatedBy=:updatedBy, Secondary_Inc=:secondary_inc, Secondary_WD=:secondary_wd,Sec_Inc_Other=:secondry_occup_other, Primary_Inc_Other=:primary_occo_other, Dispose_Other=:dispose_other,Primary_WD=:primary_wd, Primary_Inc =:primary_inc,Secondary_Occupation=:secondary_occupation,IsSecondary_Occupation=:issecdry_Occuptn, Primary_Occupation=:primary_Occuptn, Waste_Disposal=:waste_disposal,Employment_Type=:emp_type,Waste_Type=:waste_type,WP_category=:wp_category,UpdatedOn=:updated_on,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileThirdData(
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
    )

    @Query("update tblProfileIndividual set UpdatedBy=:updatedBy,  Aadhaar=:aadhaar, Voter=:voter,PAN=:pan,IncomeCertificate=:income_certificate,CasteCertificate =:caste_certificate,BankAccount=:bank_account,UpdatedOn=:updated_on,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileForthData(
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
    )

    @Query("update tblProfileIndividual set UpdatedBy=:updatedBy, SchemeSP_Cur=:schemesp_Cur, SchemeSP=:scheme_sp, SchemeDetails=:scheme_details,SchemesAvailed=:schemes_availed, SchemeDetails_Cur=:scheme_details_cur, SchemesAvailed_Cur=:schemes_availed_cur,UpdatedOn=:updated_on,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileFifthData(
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
    )


    @Query("update tblProfileIndividual set UpdatedBy=:updatedBy, Business_oth=:business_oth, Laboure_oth=:laboure_oth,Jobs=:job,Collective_name=:collective_name, Member_Collective =:member_collective,Interested_JobDetail=:interested_jobDetail, Interested_Job =:interested_job, Skills_oth=:skills_othr, Skills=:skills,UpdatedOn=:updated_on,IsEdited=:IsEdited,Status=0 where IndGUID=:IndGUID")
    fun updateIMProfileSixData(
        IndGUID: String,
        skills: String?,
        skills_othr: String?,
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
    )


    @Query("select Count(HHCode) from tblProfileIndividual")
    fun getHHCount(): Int

    @Query("select Count(*) from tblProfileIndividual where IndvCode=:IndvCode")
    fun getIndividualID(IndvCode: String): Int

    @Delete
    fun deleteindividualProfiledata(individualProfileEntity: IndividualProfileEntity)

    @Query("Select * from tblProfileIndividual where HHGUID=:hhguid")
    fun getallIMProfileBYHHGUIDdata(hhguid: String): List<IndividualProfileEntity>

    @Query("Update tblProfileIndividual set IsEdited=0")
    fun updateIsEdited()


    @Query("Select * from tblProfileIndividual where DistrictCode=:idiscode and Panchayat_Ward=:iPanchayat and HHGUID=:HHGUID")
    fun getIDPDisData(
        iPanchayat: Int,
        idiscode: Int,
        HHGUID: String?
    ): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where DistrictCode=:idis and ZoneCode=:izone and Panchayat_Ward=:iward and HHGUID=:HHGUID")
    fun getIDDisWData(
        idis: Int,
        izone: Int,
        iward: Int,
        HHGUID: String?
    ): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where DistrictCode=:idis and ZoneCode=:izone and Panchayat_Ward=:iward")
    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<IndividualProfileEntity>>


    @Query("Select * from tblProfileIndividual where DistrictCode=:idiscode and ZoneCode=:izone")
    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where DistrictCode=:idiscode")
    fun getIDDistrictData(idiscode: Int): LiveData<List<IndividualProfileEntity>>

    @Query("Select * from tblProfileIndividual where HHCode=:hhcode")
    fun getallGUIDBYHHIDDdata(hhcode: String): List<IndividualProfileEntity>

    @Query("Select * from tblProfileIndividual where IndvCode=:indvcode")
    fun getallGUIDIMProfileBYIMIDDdata(indvcode: String): List<IndividualProfileEntity>

    @Query("select Count(*) from tblProfileIndividual where IsEdited=1")
    fun getIndunsyncCount(): String

    @Query("select Count(*) from tblProfileIndividual")
    fun getIndtotalCount(): String

    @Query("Update tblProfileIndividual set Remarks=:remarks, Status=:status where IndGUID =:indvguid")
    fun updateStatusData(remarks:String,status: Int, indvguid: String)
}