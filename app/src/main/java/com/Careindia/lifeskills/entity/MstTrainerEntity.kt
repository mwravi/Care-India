package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mstTrainer")
data class MstTrainerEntity(
    @PrimaryKey @ColumnInfo(name = "TrainerID") val TrainerID: Int,
    @ColumnInfo(name = "TrainerName") val TrainerName: String? = "",
    @ColumnInfo(name = "IsActive") val IsActive: Int? = 0,
)
