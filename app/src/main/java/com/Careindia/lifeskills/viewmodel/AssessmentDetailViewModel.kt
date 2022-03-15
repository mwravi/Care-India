package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.entity.AssessmentDetailEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.repository.AssessmentDetailRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingDetailsActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingAttendanceActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingAgendaActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingCollectionActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.activity_collectivemeeting_fourth.*
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssessmentDetailViewModel : AndroidViewModel {
    var assessmentDetailRepository: AssessmentDetailRepository? = null

    constructor(application: Application) : super(application) {
        assessmentDetailRepository = AssessmentDetailRepository(application)
    }


    fun insert(assessmentDetailEntity: AssessmentDetailEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            assessmentDetailRepository!!.insert(assessmentDetailEntity)
        }
    }

    fun getAllCount(AUID: Int, QID: Int, GroupBatchID: Int): Int {
        return assessmentDetailRepository!!.getAllCount(AUID, QID, GroupBatchID)
    }

    fun updateAssessmentDetail(
        AUID: Int,
        QID: Int,
        GroupBatchID: Int
    ) {
        assessmentDetailRepository!!.updateAssessmentDetail(
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
        assessmentDetailRepository!!.updatePre(
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
        assessmentDetailRepository!!.updatePost(
            TrainingID, QID, PostScore, AggScore, GroupBatchID
        )
    }

    fun getLiveAssessmentDetailbyGuid(TrainingID: Int): LiveData<List<AssessmentDetailEntity>> {
        return assessmentDetailRepository!!.getLiveAssessmentDetailbyGuid(TrainingID)
    }

    fun getData(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): List<AssessmentDetailEntity> {
        return assessmentDetailRepository!!.getData(TrainingID, QID, GroupBatchID)
    }

    fun getPreScore(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): Int {
        return assessmentDetailRepository!!.getPreScore(TrainingID, QID, GroupBatchID)
    }

    fun getPostScore(
        TrainingID: Int, QID: Int,
        GroupBatchID: Int
    ): Int {
        return assessmentDetailRepository!!.getPostScore(TrainingID, QID, GroupBatchID)
    }

}