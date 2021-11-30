package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface IndividualProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIMProfileData(imProfileEntity: IndividualProfileEntity)

    @Query("DELETE from tblProfileIndividual")
    fun deleteAllData()

    @Query("update tblProfileIndividual set Contact =:contact,MaritalStatus =:marital_status,Caste=:caste, Age=:age, Gender=:gender, Name=:name, HHCode=:hhuid,DateForm =:dateform,UpdatedOn=:updated_on where IndGUID=:IndGUID")
    fun updateIMProfileData(
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
    )

    @Query("Select * from tblProfileIndividual")
    fun getallIMProfiledata(): LiveData<List<IndividualProfileEntity>>


    @Query("Select * from tblProfileIndividual where IndGUID=:Guid")
    fun getIdvProfiledatabyGuid(Guid: String): LiveData<List<IndividualProfileEntity>>

    @Query("update tblProfileIndividual set Smartphone =:smartphone,MobileData=:mobileData,PreferredLanguage_Communication=:preferredLanguage_communication,Languages_Speak=:languages_speak,Languages_Write =:languages_write,Languages_Read=:languages_read,StateID=:stateid,ResidingSince =:residing_since,Read_Write =:read_write,Education =:education,UpdatedOn=:updated_on where IndGUID=:IndGUID")
    fun updateIMProfileSecondData(
        IndGUID: String,
        stateid: Int?,
        residing_since: Int?,
        read_write: Int?,
        education: Int??,
        smartphone: Int?,
        mobileData: Int?,
        languages_read: String?,
        languages_write: String?,
        languages_speak: String?,
        preferredLanguage_communication: String?,
        updated_on: String?
    )


    @Query("update tblProfileIndividual set Secondary_Occupation=:secondary_occupation,IsSecondary_Occupation=:issecdry_Occuptn, Primary_Occupation=:primary_Occuptn, Waste_Disposal=:waste_disposal,Employment_Type=:emp_type,Waste_Type=:waste_type,PreferredLanguage_Mobile =:preferredLanguage_mobile,WP_category=:wp_category,UpdatedOn=:updated_on where IndGUID=:IndGUID")
    fun updateIMProfileThirdData(
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
    )

    @Query("update tblProfileIndividual set SchemeDetails=:scheme_details,SchemesAvailed=:schemes_availed, Aadhaar=:aadhaar, Voter=:voter,PAN=:pan,IncomeCertificate=:income_certificate,CasteCertificate =:caste_certificate,BankAccount=:bank_account,UpdatedOn=:updated_on where IndGUID=:IndGUID")
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
        updated_on: String?
    )

    @Query("update tblProfileIndividual set Collective_name=:collective_name, Member_Collective =:member_collective,Interested_JobDetail=:interested_jobDetail, Interested_Job =:interested_job, Jobs=:jobs, SchemeSP_Cur=:schemeSP_cur, SchemeDetails_Cur=:scheme_details_cur, SchemesAvailed_Cur=:schemes_availed_cur,UpdatedOn=:updated_on where IndGUID=:IndGUID")
    fun updateIMProfileFifthData(
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
    )

}