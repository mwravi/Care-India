package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mst_2District")
data class MstDistrictEntity(
    @PrimaryKey@ColumnInfo(name = "DistrictCode") val DistrictCode: Int,
    @ColumnInfo(name = "StateCode") val StateCode: Int?=0,
    @ColumnInfo(name = "DistrictName") val DistrictName: String?="",
    @ColumnInfo(name = "DistrictShort") val DistrictShort: String?="",
    @ColumnInfo(name = "DistrictNameUN") val DistrictNameUN: String?="",
    @ColumnInfo(name = "InterventionStart") val InterventionStart: String?="",
    @ColumnInfo(name = "IsActive") val IsActive: Int?=0

)



















