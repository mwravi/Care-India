package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstLookupEntity

@Dao
interface MstLookupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstLookupEntity>?)

    @Query("select * from mst_9Lookup where LookupFlag=:LookupFlag and Language=:Language")
    fun getMstLookup(LookupFlag: Int,Language:Int): List<MstLookupEntity>


    @Query("DELETE FROM mst_9Lookup")
    fun deleteAll()

}