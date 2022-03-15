package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblProfileHH")

data class HouseholdProfileEntity(
    @PrimaryKey @ColumnInfo(name = "HHGUID") val HHGUID: String,
    @ColumnInfo(name = "CRPID") val CRPID: Int=0,
    @ColumnInfo(name = "FCID") val FCID: Int=0,
    @ColumnInfo(name = "StateCode") val StateCode: Int? =0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int? =0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int? =0,
    @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int?,
    @ColumnInfo(name = "PWCode") val PWCode: String? = "",
    @ColumnInfo(name = "Locality") val Locality: String? = "",
    @ColumnInfo(name = "LandMark") val LandMark: String? = "",
    @ColumnInfo(name = "PinCode") val PinCode: String? = "",
    @ColumnInfo(name = "Dateform") val Dateform: Long?,
    @ColumnInfo(name = "HHCode") val HHCode: String? = "",
    @ColumnInfo(name = "Name") val Name: String? = "",
    @ColumnInfo(name = "Gender") val Gender: Int?,
    @ColumnInfo(name = "No_adults") val No_adults: Int?,
    @ColumnInfo(name = "No_adults_M") val No_adults_M: Int?,
    @ColumnInfo(name = "No_adults_F") val No_adults_F: Int?,
    @ColumnInfo(name = "No_adolescent") val No_adolescent: Int?,
    @ColumnInfo(name = "No_adolescent_M") val No_adolescent_M: Int?,
    @ColumnInfo(name = "No_adolescent_F") val No_adolescent_F: Int?,
    @ColumnInfo(name = "No_Children") val No_Children: Int?,
    @ColumnInfo(name = "No_Children_M") val No_Children_M: Int?,
    @ColumnInfo(name = "No_Children_F") val No_Children_F: Int?,
    @ColumnInfo(name = "No_Earningmembers") val No_Earningmembers: Int?,
    @ColumnInfo(name = "No_Earningmembers_M") val No_Earningmembers_M: Int?,
    @ColumnInfo(name = "No_Earningmembers_F") val No_Earningmembers_F: Int?,
    @ColumnInfo(name = "Dwelling_type") val Dwelling_type: Int?,
    @ColumnInfo(name = "Dwelling_Oth") val Dwelling_Oth: String? = "",
    @ColumnInfo(name = "Dwelling_Registered") val Dwelling_Registered: Int?,
    @ColumnInfo(name = "Type_Ration") val Type_Ration: Int?,
    @ColumnInfo(name = "Other_Ration") val Other_Ration: String?,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn:Long?,
    @ColumnInfo(name = "Status") val Status: Int?,
    @ColumnInfo(name = "Actionby") val Actionby: Int?,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int =0,
    @ColumnInfo(name = "Initials") val Initials: String? = "",
    @ColumnInfo(name = "Remarks") val Remarks: String? = ""
)

