package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.BeneficiaryDetailEntity
import com.careindia.lifeskills.repository.BeneficiaryDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeneficiaryDetailViewModel:AndroidViewModel {
    var beneficiaryTrackerDetailRepository: BeneficiaryDetailRepository? = null

    constructor(application: Application) : super(application) {
        beneficiaryTrackerDetailRepository = BeneficiaryDetailRepository(application)
    }


    fun insert(assessmentEntity: BeneficiaryDetailEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            beneficiaryTrackerDetailRepository!!.insert(assessmentEntity)
        }
    }


    fun getAllCount(Bene_GUID: String, QID: Int): Int {
        return beneficiaryTrackerDetailRepository!!.getAllCount(Bene_GUID, QID)
    }

    fun getData(Bene_GUID: String, QID: Int): List<BeneficiaryDetailEntity> {
        return beneficiaryTrackerDetailRepository!!.getData(Bene_GUID, QID)
    }

    fun updateData(
        Bene_GUID: String,
        QID: Int,
        QusAns: String
    ) {
        beneficiaryTrackerDetailRepository!!.updateData(
            Bene_GUID, QID, QusAns
        )
    }

}