package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.AssessmentDetailDao
import com.careindia.lifeskills.entity.AssessmentDetailEntity

class AssessmentDetailRepository {

    var assessmentDetailDao: AssessmentDetailDao? = null

    constructor(application: Application) {
        assessmentDetailDao = CareIndiaApplication.database?.assessmentDetailDao()
    }

    fun insert(assessmentDetailEntity: AssessmentDetailEntity) {
        assessmentDetailDao!!.insertAssessmentDetail(assessmentDetailEntity)
    }

    fun getAllCount(AUID: Int, QID: Int, GroupBatchID: Int): Int {
        return assessmentDetailDao!!.getAllCount(AUID, QID, GroupBatchID)
    }

    fun updateAssessmentDetail(
        AUID: Int,
        QID: Int,
        GroupBatchID: Int
    ) {
        assessmentDetailDao!!.updateAssessmentDetail(
            AUID, QID, GroupBatchID
        )
    }

    fun updatePre(
        TrainingID: Int,
        QID: Int,
        PreScore: Int,
        AggScore: Int,
        GroupBatchID: Int
    ) {
        assessmentDetailDao!!.updatePre(
            TrainingID, QID, PreScore, AggScore, GroupBatchID
        )
    }

    fun updatePost(
        TrainingID: Int,
        QID: Int,
        PostScore: Int,
        AggScore: Int,
        GroupBatchID: Int
    ) {
        assessmentDetailDao!!.updatePost(
            TrainingID, QID, PostScore, AggScore, GroupBatchID
        )
    }

    fun getLiveAssessmentDetailbyGuid(TrainingID: Int): LiveData<List<AssessmentDetailEntity>> {
        return assessmentDetailDao!!.getLiveAssessmentDetailbyGuid(TrainingID)
    }

    fun getData(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): List<AssessmentDetailEntity> {
        return assessmentDetailDao!!.getData(TrainingID, QID, GroupBatchID)
    }

    fun getPreScore(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): Int {
        return assessmentDetailDao!!.getPreScore(TrainingID, QID, GroupBatchID)
    }

    fun getPostScore(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): Int {
        return assessmentDetailDao!!.getPostScore(TrainingID, QID, GroupBatchID)
    }

}
