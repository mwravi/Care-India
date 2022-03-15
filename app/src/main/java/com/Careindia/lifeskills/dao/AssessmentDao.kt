package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.AssessmentDetailEntity
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.CollectiveProgressTrackerEntity

@Dao
interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(assessmentEntity: List<AssessmentEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAssessment(assessmentEntity: AssessmentEntity?)

    @Query("Select * from tblAssessment where TrainingID=:TrainingID")
    fun getLiveAssessmentbyGuid(TrainingID: Int): LiveData<List<AssessmentEntity>>

    @Query("Select * from tblAssessment where isEdited = 1")
    fun getAllAssessmentdata(): List<AssessmentEntity>

    @Query("Update tblAssessment set IsEdited=0")
    fun updateIsEdited()

    @Query("DELETE FROM tblAssessment")
    fun deleteAll()

    @Query("Select Count(*) from tblAssessment where isEdited = 1")
    fun getAssunsyncCount(): String

    @Query("Select Count(*) from tblAssessment")
    fun getAsstotalCount(): String


    @Query("update tblAssessment set ModuleID=:ModuleID, CRPID=:CRPID,FCID=:FCID,TrainingLocation=:TrainingLocation,DateForm=:DateForm,Community=:Community,NoBaches=:NoBaches,ParticipantsM=:ParticipantsM,ParticipantsF=:ParticipantsF,ParticipantsT=:ParticipantsT,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,Status=0,IsEdited=1 where TrainingID=:TrainingID")
    fun updateAssessment(
        TrainingID: Int,
        ModuleID: Int,
        CRPID: Int,
        FCID: Int,
        TrainingLocation: String,
        DateForm: Long,
        Community: String,
        NoBaches: Int,
        ParticipantsM: Int,
        ParticipantsF: Int,
        ParticipantsT: Int,
        UpdatedBy: Int,
        UpdatedOn: Long
    )

    @Query("Select * from tblAssessment")
    fun getAssessmentData(): LiveData<List<AssessmentEntity>>

    @Query("DELETE from tblAssessment where TrainingID=:TrainingID")
    fun deleteAssessmentdata( TrainingID: Int?)

    @Query("Select NoBaches from tblAssessment where TrainingID=:TrainingID")
    fun getGroupBatchID( TrainingID: Int?): Int


    @Query("update tblAssessment set IsCompleted=:IsCompleted where TrainingID=:TrainingID and ModuleID=:ModuleID")
    fun updateIsCompleted(
        TrainingID: Int?,
        ModuleID: Int,
        IsCompleted: Int

    )

    @Query("Select Count(TrainingID) from tblAssessment where TrainingID=:trainingId")
    fun getAssessmentCount(trainingId: Int?): Int

    @Query("Update tblAssessment set Status=:status where TrainingID =:trainingID")
    fun updateStatusData(status: Int, trainingID: Int)
}