package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface CollectiveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectiveData(collectiveEntity: CollectiveEntity?)

    @Update
    fun updateCollectiveData(collectiveEntity: CollectiveEntity?)

    @Query("Select * from tblCollective")
    fun getallCollectivedata(): LiveData<List<CollectiveEntity>>

    @Query("DELETE from tblCollective")
    fun deleteCollectivedata()
}