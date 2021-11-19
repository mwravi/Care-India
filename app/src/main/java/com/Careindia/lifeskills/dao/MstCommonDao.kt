package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstCommonEntity

@Dao
interface MstCommonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(faqEntity: List<MstCommonEntity>?)

    @Query("select * from mst_common where flag=:flag")
    fun getMstCommon(flag: Int): List<MstCommonEntity>

    @Query("select Count(*) from mst_common")
    fun getCount(): Int

    @Query("DELETE FROM mst_common")
    fun deleteAll()


}