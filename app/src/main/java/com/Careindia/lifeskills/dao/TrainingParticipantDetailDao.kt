package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.TrainingEntity
import com.careindia.lifeskills.entity.TrainingParticipantDetailEntity


@Dao
interface TrainingParticipantDetailDao {
    @Query("DELETE from tblTrainingParticipantDetail")
    fun deleteTrainingParticipant()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrainingParticipantData(trainingParticipantDetailEntity: List<TrainingParticipantDetailEntity>?)
}