package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.BeneficiaryTrackerDetailDao
import com.careindia.lifeskills.entity.BeneficiaryDetailEntity

class BeneficiaryDetailRepository {

    var beneficiaryTrackerDetailDao: BeneficiaryTrackerDetailDao? = null

    constructor(application: Application) {
        beneficiaryTrackerDetailDao = CareIndiaApplication.database?.beneficiaryTrackerDetailDao()
    }

    fun insert(beneficiaryDetailEntity: BeneficiaryDetailEntity) {
        beneficiaryTrackerDetailDao!!.insertBeneficiaryDetailEntityDetail(beneficiaryDetailEntity)
    }


    fun getAllCount(Bene_GUID: String, QID: Int): Int {
        return beneficiaryTrackerDetailDao!!.getAllCount(Bene_GUID, QID)
    }

    fun getData(Bene_GUID: String, QID: Int): List<BeneficiaryDetailEntity> {
        return beneficiaryTrackerDetailDao!!.getData(Bene_GUID, QID)
    }

    fun updateData(
        Bene_GUID: String,
        QID: Int,
        QusAns: String
    ) {
        beneficiaryTrackerDetailDao!!.updateData(
            Bene_GUID, QID, QusAns
        )
    }

}
