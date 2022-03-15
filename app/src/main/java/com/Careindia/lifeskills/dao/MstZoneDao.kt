package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstZoneEntity

@Dao
interface MstZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstZoneEntity>?)

    @Query("DELETE FROM mst_3zone")
    fun deleteAll()
    @Query("select * from mst_3zone where DistrictCode=:DistrictCode order by ZoneName")
    fun getMstZoneLive(DistrictCode:Int): LiveData<List<MstZoneEntity>>

    @Query("select * from mst_3zone where DistrictCode=:DistrictCode and ZoneCode in(:ZoneIn) order by ZoneName")
    fun getMstZone(DistrictCode:Int,ZoneIn: List<String>): List<MstZoneEntity>

    @Query("select * from mst_3zone where DistrictCode=:DistrictCode order by ZoneName")
    fun getMstZone(DistrictCode:Int): List<MstZoneEntity>

    @Query("select ZoneName from mst_3zone where DistrictCode=:DistrictCode and ZoneCode=:ZoneCode order by ZoneName")
    fun getMstZone(DistrictCode:Int,ZoneCode:Int): String
}