package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblTraining")
data class TrainingEntity(
    @PrimaryKey @ColumnInfo(name = "TrainingID") val TrainingID: Int,
    @ColumnInfo(name = "TrainerID") val TrainerID: String? = "",
    @ColumnInfo(name = "CRPID") val CRPID: String? = "",
    @ColumnInfo(name = "FCID") val FCID: String? = "",
    @ColumnInfo(name = "SPOID") val SPOID: String? = "",
    @ColumnInfo(name = "DateoftrainingFrom") val DateoftrainingFrom: Long?,
    @ColumnInfo(name = "DateoftrainingTo") val DateoftrainingTo: Long?,
    @ColumnInfo(name = "StartTime") val StartTime: String? = "",
    @ColumnInfo(name = "EndTime") val EndTime: String? = "",
    @ColumnInfo(name = "VenuOfTraning") val VenuOfTraning: String? = "",
    @ColumnInfo(name = "total_Participant") val total_Participant: Int? = 0,
    @ColumnInfo(name = "BatchID") val BatchID: Int? = 0,
    @ColumnInfo(name = "ModuleID") val ModuleID: Int? = 0,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int? = 0,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "ProcessID") val ProcessID: Int? = 0,
    @ColumnInfo(name = "Remarks") val Remarks: String? = ""
)
