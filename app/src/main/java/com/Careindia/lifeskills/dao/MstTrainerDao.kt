package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.MstTrainerEntity
import com.careindia.lifeskills.entity.TrainingEntity


@Dao
interface MstTrainerDao {
    @Query("DELETE from mstTrainer")
    fun deleteTrainerData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrainerData(mstTrainerEntity: List<MstTrainerEntity>?)

//    @Query("Select TrainerName from mstTrainer where TrainerID IN(:trainerId)")
//    fun getTrainerName(trainerId:List<Int>): String

    @Query("Select TrainerName from mstTrainer where TrainerID IN(:trainerId)")
    fun getTrainerName(trainerId:List<String>): List<String>

}