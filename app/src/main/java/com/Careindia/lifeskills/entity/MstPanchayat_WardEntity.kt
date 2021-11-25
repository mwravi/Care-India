package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mst_4Panchayat_Ward")
data class MstPanchayat_WardEntity(
    @PrimaryKey@ColumnInfo(name = "PWCode") val PWCode: Int,
    @ColumnInfo(name = "StateCode") val StateCode: Int=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int=0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int?=0,
    @ColumnInfo(name = "PWName") val PWName: String?="",
    @ColumnInfo(name = "PWNameUN") val PWNameUN: String?="",
    @ColumnInfo(name = "IsActive") val IsActive: Int?=0

)



















