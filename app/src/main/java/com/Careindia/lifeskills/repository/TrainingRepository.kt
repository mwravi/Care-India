package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.TrainingDao
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.TrainingEntity
import com.careindia.lifeskills.model.PrePostModel

class TrainingRepository {
    var trainingDao: TrainingDao? = null

    constructor(application: Application) {
        trainingDao = CareIndiaApplication.database?.trainingDao()
    }


    fun getTrainingData(): LiveData<List<TrainingEntity>> {
        return trainingDao!!.getTrainingData()
    }

    fun getTrainingFilterData(datefrom: Long, dateto: Long): LiveData<List<TrainingEntity>> {
        return trainingDao!!.getTrainingFilterData(datefrom, dateto)
    }

    fun getTrainingDatabyTrainingID(trainingID: Int): LiveData<List<TrainingEntity>> {
        return trainingDao!!.getTrainingDatabyTrainingID(trainingID)
    }

    fun getTrainingIndvData(participantID: Int): List<IndividualProfileEntity> {
        return trainingDao!!.getTrainingIndvData(participantID)
    }

    fun getTrainingParticipantData(trainingID: Int): List<PrePostModel> {
        return trainingDao!!.getTrainingParticipantData(trainingID)
    }

}