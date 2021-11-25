package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mstUser")
data class MstUserEntity(
    @PrimaryKey@ColumnInfo(name = "UserID") val UserID: Int,
    @ColumnInfo(name = "UserName") val UserName: String?="",
    @ColumnInfo(name = "Password") val Password: String?="",
    @ColumnInfo(name = "UserLevel") val UserLevel: Int?=0,
    @ColumnInfo(name = "Role") val Role: String?="",
    @ColumnInfo(name = "Statecode") val Statecode: String?="",
    @ColumnInfo(name = "DistrictCode") val DistrictCode: String?="",
    @ColumnInfo(name = "ZoneCode") val ZoneCode: String?="",
    @ColumnInfo(name = "PWCode") val PWCode: String?=""
)



















