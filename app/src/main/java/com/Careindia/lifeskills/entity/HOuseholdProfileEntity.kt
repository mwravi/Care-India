package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblProfileHH")
data class HouseholdProfileEntity(@PrimaryKey @ColumnInfo(name = "uid") val uid: Int,
                                  @ColumnInfo(name = "HHGUID") val HHGUID: String?="",
                                  @ColumnInfo(name = "StateCode") val StateCode: String?="",
                                  @ColumnInfo(name = "DistrictCode") val DistrictCode: String?="",
                                  @ColumnInfo(name = "ZoneCode") val ZoneCode: String?="",
                                  @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int?=0,
                                  @ColumnInfo(name = "PWCode") val PWCode: String?="",
                                  @ColumnInfo(name = "Localitycode") val Localitycode: Int?=0,
                                  @ColumnInfo(name = "Dateform") val Dateform: String?="",
                                  @ColumnInfo(name = "HHCode") val HHCode: String?="",
                                  @ColumnInfo(name = "Name") val Name: String?="",
                                  @ColumnInfo(name = "Gender") val Gender: Int? =0,
                                  @ColumnInfo(name = "No_adults") val No_adults: Int?=0,
                                  @ColumnInfo(name = "No_adults_M") val No_adults_M: Int?=0,
                                  @ColumnInfo(name = "No_adults_F") val No_adults_F: Int?=0,
                                  @ColumnInfo(name = "No_adolescent") val No_adolescent: Int?=0,
                                  @ColumnInfo(name = "No_adolescent_M") val No_adolescent_M: Int?=0,
                                  @ColumnInfo(name = "No_adolescent_F") val No_adolescent_F: Int?=0,
                                  @ColumnInfo(name = "No_Children") val No_Children: Int?=0,
                                  @ColumnInfo(name = "No_Children_M") val No_Children_M: Int?=0,
                                  @ColumnInfo(name = "No_Children_F") val No_Children_F: Int?=0,
                                  @ColumnInfo(name = "No_Earningmembers") val No_Earningmembers: Int?=0,
                                  @ColumnInfo(name = "No_Earningmembers18_59") val No_Earningmembers18_59: Int?=0,
                                  @ColumnInfo(name = "Dwelling_type") val Dwelling_type: Int?=0,
                                  @ColumnInfo(name = "Dwelling_Oth") val Dwelling_Oth: String?="",
                                  @ColumnInfo(name = "Dwelling_Registered") val Dwelling_Registered: Int?=0,
                                  @ColumnInfo(name = "Type_Ration") val Type_Ration: Int?=0,
                                  @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?=0,
                                  @ColumnInfo(name = "CreatedOn") val CreatedOn: String?="",
                                  @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?=0,
                                  @ColumnInfo(name = "UpdatedOn") val UpdatedOn: String?="",
                                  @ColumnInfo(name = "Status") val Status: Int?=0,
                                  @ColumnInfo(name = "Actionby") val Actionby: Int?=0)

