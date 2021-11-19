package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mst_common")
data class MstCommonEntity(
    @PrimaryKey@ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "flag") val flag: Int?=0,
    @ColumnInfo(name = "id") val id: Int?=0,
    @ColumnInfo(name = "value") val value: String?=""

)



















