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

    @Query("select * from mst_2District where StateCode=:StateCode")
    fun getMstDistrict(StateCode: Int): LiveData<List<MstDistrictEntity>>

    @Query("select * from mst_2District where StateCode=:StateCode")
    fun getMstDist(StateCode: Int): List<MstDistrictEntity>

    @Query("DELETE FROM mst_2District")
    fun deleteAll()


}