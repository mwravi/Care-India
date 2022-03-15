package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstAssessmentDao
import com.careindia.lifeskills.entity.MstAssessmentEntity
class MstAssessmentRepository {
    var mstAssessmentDao: MstAssessmentDao?=null
    constructor(application: Application) {
        mstAssessmentDao = CareIndiaApplication.database?.mstAssessmentDao()
    }

    fun getAllQuestion(ModuleID:Int,PageID:Int): List<MstAssessmentEntity> {
        return mstAssessmentDao!!.getAllQuestion(ModuleID, PageID)
    }

    fun getDistinctPage(ModuleID:Int): List<MstAssessmentEntity> {
        return mstAssessmentDao!!.getDistinctPage(ModuleID)
    }

    fun getDistinctPageBen(ModuleID:Int): List<MstAssessmentEntity> {
        return mstAssessmentDao!!.getDistinctPageBen(ModuleID)
    }

    fun getPageHeader(PageID: Int,FormID:Int): String {
        return mstAssessmentDao!!.getPageHeader(PageID,FormID)
    }

}