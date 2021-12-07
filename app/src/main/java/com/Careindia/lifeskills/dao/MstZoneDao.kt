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
    @Query("select * from mst_3zone where DistrictCode=:DistrictCode")
    fun getMstZoneLive(DistrictCode:Int): LiveData<List<MstZoneEntity>>

    @Query("select * from mst_3zone where DistrictCode=:DistrictCode")
    fun getMstZone(DistrictCode:Int): List<MstZoneEntity>
}