package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity

@Dao
interface MstPanchayatWardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstPanchayat_WardEntity>?)

    @Query("select * from mst_4panchayat_ward where StateCode=:StateCode and DistrictCode=:DistrictCode and ZoneCode=:ZoneCode")
    fun getMstPanchayat_Ward(StateCode: Int,DistrictCode:Int,ZoneCode:Int): List<MstPanchayat_WardEntity>



}