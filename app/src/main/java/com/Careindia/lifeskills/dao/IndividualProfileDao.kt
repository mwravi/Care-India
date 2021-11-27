package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface IndividualProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertIMProfileData(imProfileEntity: IndividualProfileEntity)

    @Query("DELETE from tblProfileIndividual")
     fun deleteAllData()

    @Update
    fun updateIMProfileData(imProfileEntity: IndividualProfileEntity?)

    @Query("Select * from tblProfileIndividual")
    fun getallIMProfiledata(): LiveData<List<IndividualProfileEntity>>

}