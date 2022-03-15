package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstDistrictEntity

@Dao
interface MstDistrictDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstDistrictEntity>?)

    @Query("select * from mst_2District where StateCode=:StateCode and DistrictCode in (:DistrictIn)")
    fun getMstDistrict(StateCode: Int,DistrictIn:List<String>): LiveData<List<MstDistrictEntity>>

    @Query("select * from mst_2District where StateCode=:StateCode  and DistrictCode in (:DistrictIn)")
    fun getMstDist(StateCode: Int,DistrictIn: List<String>): List<MstDistrictEntity>

    @Query("select * from mst_2District where StateCode=:StateCode")
    fun getMstDist(StateCode: Int): List<MstDistrictEntity>

    @Query("DELETE FROM mst_2District")
    fun deleteAll()

    @Query("select Urban_rural from mst_2District limit 1")
    fun getUrban_rural(): Int

    @Query("select DistrictCode from mst_2District limit 1")
    fun getDisCode(): Int

    @Query("select * from mst_2District")
    fun getMstDistrict(): List<MstDistrictEntity>

    @Query("select DistrictName from mst_2District where StateCode=:StateCode and DistrictCode=:DistrictCode")
    fun getMstDistrict(StateCode:Int,DistrictCode:Int): String
}