package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.TrainingEntity
import com.careindia.lifeskills.model.PrePostModel
import com.careindia.lifeskills.repository.TrainingRepository

class TrainingViewModel : AndroidViewModel {
    var trainingRepository: TrainingRepository? = null

    constructor(application: Application) : super(application) {
        trainingRepository = TrainingRepository(application)
    }


    fun getTrainingData(): LiveData<List<TrainingEntity>> {
        return trainingRepository!!.getTrainingData()
    }

    fun getTrainingFilterData(datefrom: Long, dateto: Long): LiveData<List<TrainingEntity>> {
        return trainingRepository!!.getTrainingFilterData(datefrom, dateto)
    }

    fun getTrainingDatabyTrainingID(trainingID:Int): LiveData<List<TrainingEntity>> {
        return trainingRepository!!.getTrainingDatabyTrainingID(trainingID)
    }

    fun getTrainingIndvData(participantID:Int): List<IndividualProfileEntity> {
        return trainingRepository!!.getTrainingIndvData(participantID)
    }
    fun getTrainingParticipantData(trainingID: Int): List<PrePostModel> {
        return trainingRepository!!.getTrainingParticipantData(trainingID)
    }
}