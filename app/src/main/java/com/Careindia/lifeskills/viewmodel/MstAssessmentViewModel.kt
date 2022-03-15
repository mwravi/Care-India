package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstAssessmentEntity

import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.MstAssessmentRepository


class MstAssessmentViewModel : AndroidViewModel {
    var mstAssessmentRepository: MstAssessmentRepository? = null

    constructor(application: Application) : super(application) {
        mstAssessmentRepository = MstAssessmentRepository(application)
    }

    fun getAllQuestion(ModuleID:Int,PageID:Int): List<MstAssessmentEntity> {
        return mstAssessmentRepository!!.getAllQuestion(ModuleID,PageID)
    }

    fun getDistinctPage(ModuleID:Int): List<MstAssessmentEntity> {
        return mstAssessmentRepository!!.getDistinctPage(ModuleID)
    }

    fun getDistinctPageBen(ModuleID:Int): List<MstAssessmentEntity> {
        return mstAssessmentRepository!!.getDistinctPageBen(ModuleID)
    }

    fun getPageHeader(PageID: Int,FormID:Int): String {
        return mstAssessmentRepository!!.getPageHeader(PageID,FormID)
    }





}