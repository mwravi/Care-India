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
    @ColumnInfo(name = "Statecode") val Statecode: Int?=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int?=0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int?=0,
    @ColumnInfo(name = "PWCode") val PWCode: String?="",
    @ColumnInfo(name = "CRPID") val CRPID: Int=0,
    @ColumnInfo(name = "CRPID_Name") val CRPID_Name: String="",
    @ColumnInfo(name = "FCID") val FCID: Int=0,
    @ColumnInfo(name = "FCID_Name") val FCID_Name: String="",
)



















