package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface CollectiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveEntity: CollectiveEntity)

    @Query("DELETE from tblCollective")
    fun deleteAllData()
}