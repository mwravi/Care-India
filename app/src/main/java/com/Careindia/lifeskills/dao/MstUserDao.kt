package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstUserEntity

@Dao
interface MstUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithCondition(entity: List<MstUserEntity>?)

    @Query("select * from mstUser")
    fun getMstUser(): List<MstUserEntity>

    @Query("select Count(*) from mstUser")
    fun getusersCount():Int

    @Query("DELETE FROM mstUser")
    fun deleteAllUsers()


}