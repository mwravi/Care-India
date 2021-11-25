package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mst_1State")
data class MstStateEntity(
    @PrimaryKey@ColumnInfo(name = "StateCode") val StateCode: Int,
    @ColumnInfo(name = "StateName") val StateName: String?="",
    @ColumnInfo(name = "StateShort") val StateShort: String?="",
    @ColumnInfo(name = "StatenameUN") val StatenameUN: String?="",
    @ColumnInfo(name = "InterventionStart") val InterventionStart: String?="",
    @ColumnInfo(name = "IsActive") val IsActive: Int?=0

)



















