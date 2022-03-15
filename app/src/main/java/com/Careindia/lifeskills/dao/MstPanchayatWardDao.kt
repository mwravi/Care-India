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

    @Query("select * from mst_4panchayat_ward where StateCode=:StateCode and DistrictCode=:DistrictCode and ZoneCode=:ZoneCode order by PWName")
    fun getMstPanchayat_Ward(
        StateCode: Int,
        DistrictCode: Int,
        ZoneCode: Int
    ): List<MstPanchayat_WardEntity>

    @Query("DELETE FROM mst_4panchayat_ward")
    fun deleteAll()

    @Query("select * from mst_4panchayat_ward where  DistrictCode=:disCode   and pwcode in (:WardIn) order by PWName")
    fun getMstPanchayat(disCode: Int,WardIn: List<String>): List<MstPanchayat_WardEntity>

    @Query("select * from mst_4panchayat_ward where  DistrictCode=:disCode  order by PWName")
    fun getMstPanchayat(disCode: Int): List<MstPanchayat_WardEntity>

    @Query("select * from mst_4panchayat_ward where  ZoneCode=:ZoneCode   and pwcode in (:PanchayatIn) order by PWName")
    fun getMstWard(ZoneCode: Int,PanchayatIn: List<String>): List<MstPanchayat_WardEntity>

    @Query("select * from mst_4panchayat_ward where  ZoneCode=:ZoneCode   order by PWName")
    fun getMstWard(ZoneCode: Int): List<MstPanchayat_WardEntity>

    @Query("select PWName from mst_4panchayat_ward where  ZoneCode=:ZoneCode and pwcode=:WardCode   order by PWName")
    fun getMstWard(ZoneCode: Int,WardCode:Int): String

    @Query("select PWName from mst_4panchayat_ward where  DistrictCode=:disCode and pwcode=:WardCode  order by PWName")
    fun getMstPanchayat(disCode: Int,WardCode:Int): String
}