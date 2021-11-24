package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface IndividualProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAllData(imProfileEntity: IndividualProfileEntity)

    @Query("DELETE from tblProfileIndividual")
     fun deleteAllData()
}