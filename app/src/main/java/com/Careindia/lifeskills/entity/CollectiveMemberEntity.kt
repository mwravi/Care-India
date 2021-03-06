package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblCollectiveMember")
data class CollectiveMemberEntity(
    @PrimaryKey @ColumnInfo(name = "GUID") val GUID: String,
    @ColumnInfo(name = "Col_GUID") val Col_GUID: String? = "",
    @ColumnInfo(name = "HHGUID") val HHGUID: String? = "",
    @ColumnInfo(name = "IndvGuid") val IndvGuid: String? = "",
    @ColumnInfo(name = "Name") val Name: String? = "",
    @ColumnInfo(name = "Gender") val Gender: Int? = 0,
    @ColumnInfo(name = "Category") val Category: Int? = 0,
    @ColumnInfo(name = "Age") val Age: Int? = 0,
    @ColumnInfo(name = "Position") val Position: String? = "",
    @ColumnInfo(name = "Isbank") val Isbank: Int? = 0,
    @ColumnInfo(name = "Contact") val Contact: String? = "",
    @ColumnInfo(name = "Aadhaar") val Aadhaar: String? = "",
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "Actionby") val Actionby: Int? = 0,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int = 0
)
