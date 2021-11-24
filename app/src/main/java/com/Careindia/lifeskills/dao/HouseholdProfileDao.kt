package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.HouseholdProfileEntity

@Dao
interface HouseholdProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(hhprofileEntity: HouseholdProfileEntity?)

    @Query("Select * from tblProfileHH")
    fun getallHHdata():LiveData<List<HouseholdProfileEntity>>

    @Query("DELETE from tblprofilehh")
    fun deleteProfiledata()

}