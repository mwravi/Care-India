package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.CollectiveMemberEntity

@Dao
interface CollectiveMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveMemberEntity: CollectiveMemberEntity)

    @Query("DELETE from tblCollectiveMember")
    fun deleteAllData()
}