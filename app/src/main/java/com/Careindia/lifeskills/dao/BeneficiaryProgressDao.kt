package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.BeneficiaryEntity
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity

@Dao
interface BeneficiaryProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeneficiaryData(beneficiaryEntity: BeneficiaryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(beneficiaryEntity: List<BeneficiaryEntity>?)

    @Query("Select * from tblBeneficiaryProgress")
    fun getallbeneficiarydata(): LiveData<List<BeneficiaryEntity>>

    @Query("Select * from tblBeneficiaryProgress where isEdited = 1")
    fun getAllBeneficiarydata(): List<BeneficiaryEntity>

    @Query("DELETE FROM tblBeneficiaryProgress")
    fun deleteAll()

    @Query("Update tblBeneficiaryProgress set IsEdited=0")
    fun updateIsEdited()

    @Delete
    fun deleteibeneficiarydata(beneficiaryEntity: BeneficiaryEntity)

    @Query("update tblBeneficiaryProgress set Col_GUID=:col_GUID, UpdatedBy=:updatedBy,Role_Collective=:rolecollective,Role_Collective_Othr=:rolecollectiveOthr, Locality=:locality, Name_Collective=:nameCollective, PWCode=:Panchayat1, Panchayat_Ward=:Ward1, ZoneCode=:Zone1, DistrictCode=:District1, FCID=:superverCor, CRPID=:crpname, DateForm =:dateform,UpdatedOn=:UpdatedOn,IsEdited=:IsEdited,Status=0 where Bene_GUID=:bene_GUID")
    fun updatebeneficiaryData(
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
    )

    @Query("Select * from tblBeneficiaryProgress where DistrictCode=:idiscode and Panchayat_Ward=:iPanchayat")
    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<BeneficiaryEntity>>

    @Query("Select * from tblBeneficiaryProgress where IMGUID=:indv_guid")
    fun getBeneficiaryList(indv_guid: String): LiveData<List<BeneficiaryEntity>>


    @Query("Select * from tblBeneficiaryProgress where DistrictCode=:idis and ZoneCode=:izone and Panchayat_Ward=:iward")
    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<BeneficiaryEntity>>


    @Query("Select * from tblBeneficiaryProgress where DistrictCode=:idiscode and ZoneCode=:izone")
    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<BeneficiaryEntity>>

    @Query("Select * from tblBeneficiaryProgress where DistrictCode=:idiscode")
    fun getIDDistrictData(idiscode: Int): LiveData<List<BeneficiaryEntity>>

    @Query("Select * from tblBeneficiaryProgress where Bene_GUID=:Guid")
    fun getbeneficiarydatabyGuid(Guid: String): LiveData<List<BeneficiaryEntity>>

    @Query("Select * from tblBeneficiaryProgress where Panchayat_Ward=:PW")
    fun getbenidataList(PW: Int): List<BeneficiaryEntity>

    @Query("Select * from tblBeneficiaryProgress where ZoneCode=:ZoneCode and Panchayat_Ward=:wardname")
    fun getbenidataUrbanList(ZoneCode: Int, wardname: Int): List<BeneficiaryEntity>

    @Query("Select * from tblBeneficiaryProgress")
    fun getbenificiaryList(): List<BeneficiaryEntity>

    @Query("Select * from tblBeneficiaryProgress where IMGUID=:Guid")
    fun getBendatabyIndGuid(Guid: String): List<BeneficiaryEntity>

    @Query("Select Count(*) from tblBeneficiaryProgress where IsEdited=1")
    fun getBenunsyncCount(): String

    @Query("Select Count(*) from tblBeneficiaryProgress")
    fun getBentotalCount(): String

    @Query("Select * from tblProfileIndividual where IndGUID=:indGUID")
    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity>


    @Query("Update tblBeneficiaryProgress set Status=:status where Bene_GUID =:ben_guid")
    fun updateStatusData(status: Int, ben_guid: String)


}