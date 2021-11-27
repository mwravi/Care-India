package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstCommonEntity


class CollectiveRepository(private val collectiveDao: CollectiveDao,private val mstCommonDao: MstCommonDao) {

    fun insert(collectiveEntity: CollectiveEntity){
        collectiveDao.insertCollectiveData(collectiveEntity)
    }

    fun update(collectiveEntity: CollectiveEntity){
        collectiveDao.updateCollectiveData(collectiveEntity)
    }

    fun getmstCommonData(flag:Int):List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
    }

    fun getallCollectivedata():LiveData<List<CollectiveEntity>>{
        return collectiveDao.getallCollectivedata()
    }

    fun delete(){
        collectiveDao.deleteCollectivedata()
    }

}