package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblPsychometric")
data class PsychometricEntity(
    @PrimaryKey @ColumnInfo(name = "PATGUID") val PATGUID: String,
    @ColumnInfo(name = "HHID") val HHID: String? = "",
    @ColumnInfo(name = "IMID") val IMID: String? = "",
    @ColumnInfo(name = "Name_participant") val Name_participant: String? = "",
    @ColumnInfo(name = "Age_partcipant") val Age_partcipant: Int? = 0,
)
