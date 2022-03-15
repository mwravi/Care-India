package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblTrainingParticipantDetail")
data class TrainingParticipantDetailEntity(
    @PrimaryKey @ColumnInfo(name = "TrainingID") val TrainingID: Int,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int = 0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int = 0,
    @ColumnInfo(name = "WardCode") val WardCode: Int = 0,
    @ColumnInfo(name = "IndGUID") val IndGUID: String? = "",
    @ColumnInfo(name = "ParticipantID") val ParticipantID: Int? = 0,
    @ColumnInfo(name = "CurrentStatus") val CurrentStatus: Int? = 0,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int? = 0,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?
)
