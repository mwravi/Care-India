package com.careindia.lifeskills.repository

import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.CollectiveDao
import com.careindia.lifeskills.dao.IndividualProfileDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity

class IndividualProfileRepository(private val imProfileDao: IndividualProfileDao, private val mstCommonDao: MstCommonDao) {

    fun insert(individualProfileEntity: IndividualProfileEntity){
        imProfileDao.insertIMProfileData(individualProfileEntity)
    }

    fun update(individualProfileEntity: IndividualProfileEntity){
        imProfileDao.updateIMProfileData(individualProfileEntity)
    }

    fun getmstCommonData(flag:Int):List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
    }

    fun getallProfiledata(): LiveData<List<IndividualProfileEntity>> {
        return imProfileDao.getallIMProfiledata()
    }

    fun delete(){
        imProfileDao.deleteAllData()
    }

}