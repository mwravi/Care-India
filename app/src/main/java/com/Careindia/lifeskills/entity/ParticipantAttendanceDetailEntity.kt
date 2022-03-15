package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblParticipantAttendanceDetail")
data class ParticipantAttendanceDetailEntity(
    @PrimaryKey @ColumnInfo(name = "TrainingID") val TrainingID: Int,
    @ColumnInfo(name = "ParticipantID") val ParticipantID: Int? = 0,
    @ColumnInfo(name = "Training_Date") val Training_Date: Long?,
    @ColumnInfo(name = "StartTime") val StartTime: String? = "",
    @ColumnInfo(name = "EndTime") val EndTime: String? = "",
    @ColumnInfo(name = "SPOID") val SPOID: String? = "",
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int? = 0,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
)
