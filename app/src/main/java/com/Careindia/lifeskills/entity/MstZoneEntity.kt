package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mst_3Zone")
data class MstZoneEntity(
    @PrimaryKey@ColumnInfo(name = "ZoneCode") val ZoneCode: Int,
    @ColumnInfo(name = "StateCode") val StateCode: Int=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int?=0,
    @ColumnInfo(name = "ZoneName") val ZoneName: String?="",
    @ColumnInfo(name = "BlockShort") val BlockShort: String?="",
    @ColumnInfo(name = "BlockNameUN") val BlockNameUN: String?="",
    @ColumnInfo(name = "InterventionStart") val InterventionStart: String?="",
    @ColumnInfo(name = "IsActive") val IsActive: Int?=0

)



















