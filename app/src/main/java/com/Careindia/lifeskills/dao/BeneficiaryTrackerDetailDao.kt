package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.*

@Dao
interface BeneficiaryTrackerDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(beneficiaryDetailEntity: List<BeneficiaryDetailEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeneficiaryDetailEntityDetail(beneficiaryDetailEntity: BeneficiaryDetailEntity?)


    @Query("Select * from tblBeneficiaryDetail where isEdited = 1")
    fun getAllBeneficiaryDetaildata(): List<BeneficiaryDetailEntity>

    @Query("Update tblBeneficiaryDetail set IsEdited=0")
    fun updateIsEdited()

    @Query("DELETE FROM tblBeneficiaryDetail")
    fun deleteAll()

    @Query("Select Count(*) from tblBeneficiaryDetail where Bene_GUID=:Bene_GUID and QID=:QID")
    fun getAllCount(Bene_GUID: String, QID: Int): Int

    @Query("Select * from tblBeneficiaryDetail where Bene_GUID=:Bene_GUID and QID=:QID")
    fun getData(Bene_GUID: String, QID: Int): List<BeneficiaryDetailEntity>

    @Query("update tblBeneficiaryDetail set QuestionValue=:QusAns,IsEdited=1  where Bene_GUID=:Bene_GUID and QID=:QID")
    fun updateData(
        Bene_GUID: String,
        QID: Int,
        QusAns: String
    )

}