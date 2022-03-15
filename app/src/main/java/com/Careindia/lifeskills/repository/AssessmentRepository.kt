package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.AssessmentDao
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.CollectiveProgressTrackerEntity

class AssessmentRepository {

    var assessmentDao: AssessmentDao? = null

    constructor(application: Application) {
        assessmentDao = CareIndiaApplication.database?.assessmentDao()
    }

    fun insert(assessmentEntity: AssessmentEntity) {
        assessmentDao!!.insertAssessment(assessmentEntity)
    }

    fun getLiveAssessmentbyGuid(TrainingID: Int): LiveData<List<AssessmentEntity>> {
        return assessmentDao!!.getLiveAssessmentbyGuid(TrainingID)
    }

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
    ) {
        assessmentDao!!.updateAssessment(
            TrainingID,
            ModuleID,
            CRPID,
            FCID,
            TrainingLocation,
            DateForm,
            Community,
            NoBaches,
            ParticipantsM,
            ParticipantsF,
            ParticipantsT,
            UpdatedBy,
            UpdatedOn
        )
    }

    fun getAssessmentData(): LiveData<List<AssessmentEntity>> {
        return assessmentDao!!.getAssessmentData()
    }

    fun deleteAssessmentdata(TrainingID: Int?){
        return assessmentDao!!.deleteAssessmentdata(TrainingID)
    }

    fun getGroupBatchID( TrainingID: Int?): Int {
        return assessmentDao!!.getGroupBatchID(TrainingID)
    }

    fun updateIsCompleted(
        TrainingID: Int?,
        ModuleID: Int,
        IsCompleted: Int

    ) {
        assessmentDao!!.updateIsCompleted(
            TrainingID,
            ModuleID,
            IsCompleted
        )
    }




    fun getAssessmentCount(trainingId: Int?): Int {
        return assessmentDao!!.getAssessmentCount(trainingId)
    }

}
