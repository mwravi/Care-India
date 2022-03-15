package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblBeneficiaryProgress")
data class  BeneficiaryEntity(
    @PrimaryKey @ColumnInfo(name = "Bene_GUID") val Bene_GUID: String,
    @ColumnInfo(name = "IMGUID") val IMGUID: String?="",
    @ColumnInfo(name = "Col_GUID") val Col_GUID: String? = "",
    @ColumnInfo(name = "CRPID") val CRPID: Int = 0,
    @ColumnInfo(name = "FCID") val FCID: Int = 0,
    @ColumnInfo(name = "StateCode") val StateCode: Int = 0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int? = 0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int? = 0,
    @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int = 0,
    @ColumnInfo(name = "PWCode") val PWCode: String? = "",
    @ColumnInfo(name = "DateForm") val DateForm: Long?,
    @ColumnInfo(name = "Locality") val Locality: String? = "",
    @ColumnInfo(name = "Name_Collective") val Name_Collective: String? = "",
    @ColumnInfo(name = "Role_Collective") val Role_Collective: Int? = 0,
    @ColumnInfo(name = "Role_Collective_Othr") val Role_Collective_Othr: String? = "",
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "Actionby") val Actionby: Int? = 0,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int=0

)
