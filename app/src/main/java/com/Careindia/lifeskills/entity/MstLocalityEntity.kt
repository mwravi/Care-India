package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mst_5Locality")
data class MstLocalityEntity(
    @PrimaryKey@ColumnInfo(name = "LocalityCode") val LocalityCode: Int,
    @ColumnInfo(name = "StateCode") val StateCode: Int=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int=0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int?=0,
    @ColumnInfo(name = "PWCode") val PWCode: Int?=0,
    @ColumnInfo(name = "Localityname") val Localityname: String?="",
    @ColumnInfo(name = "LocatitynameUN") val LocatitynameUN: String?="",
    @ColumnInfo(name = "IsActive") val IsActive: Int?=0

)



















