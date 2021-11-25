package com.careindia.lifeskills.repository


import com.careindia.lifeskills.dao.HouseholdProfileDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity


class HouseholdProfileRepository(private val hhdao: HouseholdProfileDao,private val mstcmnDoa: MstCommonDao) {

     val hhProfileData = hhdao.getallHHdata()

   fun insert(hhProfileEntity: HouseholdProfileEntity){
       hhdao.insertData(hhProfileEntity)
   }

//    fun getMstCommon(flag: Int): List<MstCommonEntity> {
//        return mstCommonDao!!.getMstCommon(flag)
//    }


    fun getallData(flag:Int):List<MstCommonEntity> {
      return mstcmnDoa.getMstCommon(flag)
    }

    fun delete(){
        hhdao.deleteProfiledata()
    }
}