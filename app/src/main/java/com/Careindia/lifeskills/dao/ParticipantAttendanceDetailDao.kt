package com.careindia.lifeskills.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.careindia.lifeskills.entity.ParticipantAttendanceDetailEntity


@Dao
interface ParticipantAttendanceDetailDao {

    @Query("DELETE from tblParticipantAttendanceDetail")
    fun deleteParticipantAttendance()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllParticipantAttendanceData(participantAttendanceDetailEntity: List<ParticipantAttendanceDetailEntity>?)

}