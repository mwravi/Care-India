package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstLocalityEntity

@Dao
interface MstLocalityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstLocalityEntity>?)

    @Query("select * from mst_5Locality where StateCode=:StateCode and DistrictCode=:DistrictCode and ZoneCode=:ZoneCode and PWCode=:PWCode")
    fun getMstLocality(StateCode: Int,DistrictCode:Int,ZoneCode:Int,PWCode:Int): List<MstLocalityEntity>

    @Query("DELETE FROM mst_5Locality")
    fun deleteAll()




}