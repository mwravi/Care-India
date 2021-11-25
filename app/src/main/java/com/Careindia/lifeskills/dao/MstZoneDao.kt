package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstZoneEntity

@Dao
interface MstZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstZoneEntity>?)

    @Query("select * from mst_3zone where StateCode=:StateCode and DistrictCode=:DistrictCode")
    fun getMstZone(StateCode: Int,DistrictCode:Int): List<MstZoneEntity>




}