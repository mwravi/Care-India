package com.careindia.lifeskills.repository

import android.os.AsyncTask
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.HouseholdProfileDao
import com.careindia.lifeskills.entity.HouseholdProfileEntity


class HouseholdProfileRepository(private val hhdao: HouseholdProfileDao) {

     val hhProfileData = hhdao.getallHHdata()

   fun insert(hhProfileEntity: HouseholdProfileEntity){
       hhdao.insertData(hhProfileEntity)
   }

    fun delete(){
        hhdao.deleteProfiledata()
    }
}