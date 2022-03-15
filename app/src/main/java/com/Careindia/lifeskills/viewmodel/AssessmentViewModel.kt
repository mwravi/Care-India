package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.repository.AssessmentRepository
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

class AssessmentViewModel:AndroidViewModel {
    var assessmentRepository: AssessmentRepository? = null

    constructor(application: Application) : super(application) {
        assessmentRepository = AssessmentRepository(application)
    }


    fun insert(assessmentEntity: AssessmentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            assessmentRepository!!.insert(assessmentEntity)
        }
    }

    fun getLiveAssessmentbyGuid(TrainingID: Int): LiveData<List<AssessmentEntity>> {
        return assessmentRepository!!.getLiveAssessmentbyGuid(TrainingID)
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
        assessmentRepository!!.updateAssessment(
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
        return assessmentRepository!!.getAssessmentData()
    }

    fun deleteAssessmentdata( TrainingID: Int?){
        return assessmentRepository!!.deleteAssessmentdata(TrainingID)
    }

    fun getGroupBatchID( TrainingID: Int?): Int {
        return assessmentRepository!!.getGroupBatchID(TrainingID)
    }


    fun updateIsCompleted(
        TrainingID: Int?,
        ModuleID: Int,
        IsCompleted: Int

    ) {
        assessmentRepository!!.updateIsCompleted(
            TrainingID,
            ModuleID,
            IsCompleted
        )
    }

    fun getAssessmentCount(trainingId: Int?): Int {
        return assessmentRepository!!.getAssessmentCount(trainingId)
    }

}