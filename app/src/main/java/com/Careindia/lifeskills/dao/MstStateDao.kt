package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstStateEntity

@Dao
interface MstStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstStateEntity>?)

    @Query("select * from mst_1State")
    fun getMstState(): List<MstStateEntity>



}