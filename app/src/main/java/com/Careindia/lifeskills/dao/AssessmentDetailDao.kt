package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.AssessmentDetailEntity
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.PsychometricEntity

@Dao
interface AssessmentDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(assessmentDetailEntity: List<AssessmentDetailEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAssessmentDetail(assessmentDetailEntity: AssessmentDetailEntity?)

    @Query("DELETE FROM tblAssessmentDetail")
    fun deleteAll()

    @Query("Select * from tblAssessmentDetail where isEdited = 1")
    fun getAllAssessmentDetaildata(): List<AssessmentDetailEntity>

    @Query("Update tblAssessmentDetail set IsEdited=0")
    fun updateIsEdited()


    @Query("Select Count(*) from tblAssessmentDetail where TrainingID=:TrainingID and QID=:QID and GroupBatchID=:GroupBatchID")
    fun getAllCount(TrainingID: Int, QID: Int, GroupBatchID: Int): Int

    @Query("update tblAssessmentDetail set GroupBatchID=:GroupBatchID,IsEdited=1 where TrainingID=:TrainingID and QID=:QID")
    fun updateAssessmentDetail(
        TrainingID: Int,
        QID: Int,
        GroupBatchID: Int
    )

    @Query("update tblAssessmentDetail set PreScore=:PreScore,AggScore=:AggScore,IsEdited=1 where TrainingID=:TrainingID and QID=:QID and GroupBatchID=:GroupBatchID")
    fun updatePre(
        TrainingID: Int,
        QID: Int,
        PreScore: Int,
        AggScore: Int,
        GroupBatchID: Int
    )

    @Query("update tblAssessmentDetail set PostScore=:PostScore,AggScore=:AggScore,IsEdited=1  where TrainingID=:TrainingID and QID=:QID and GroupBatchID=:GroupBatchID")
    fun updatePost(
        TrainingID: Int,
        QID: Int,
        PostScore: Int,
        AggScore: Int,
        GroupBatchID: Int
    )

    @Query("Select * from tblAssessmentDetail where TrainingID=:TrainingID")
    fun getLiveAssessmentDetailbyGuid(TrainingID: Int): LiveData<List<AssessmentDetailEntity>>

    @Query("Select * from tblAssessmentDetail where TrainingID=:TrainingID and QID=:QID and GroupBatchID=:GroupBatchID")
    fun getData(TrainingID: Int, QID: Int, GroupBatchID: Int): List<AssessmentDetailEntity>

    @Query("Select PreScore from tblAssessmentDetail where TrainingID=:TrainingID and QID=:QID and GroupBatchID=:GroupBatchID")
    fun getPreScore(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): Int

    @Query("Select PostScore from tblAssessmentDetail where TrainingID=:TrainingID and QID=:QID and GroupBatchID=:GroupBatchID")
    fun getPostScore(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): Int
}