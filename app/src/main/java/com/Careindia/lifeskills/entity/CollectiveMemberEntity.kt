package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblCollectiveMember")
data class CollectiveMemberEntity(
    @PrimaryKey @ColumnInfo(name = "GUID") val GUID: String,
    @ColumnInfo(name = "Col_GUID") val Col_GUID: String? = "",
    @ColumnInfo(name = "MemberID") val MemberID: String? = "",
    @ColumnInfo(name = "Name") val Name: String? = "",
    @ColumnInfo(name = "Gender") val Gender: Int? = 0,
    @ColumnInfo(name = "Age") val Age: Int? = 0,
    @ColumnInfo(name = "Position") val Position: String? = "",
    @ColumnInfo(name = "Isbank") val Isbank: Int? = 0,
    @ColumnInfo(name = "Contact") val Contact: String? = "",
    @ColumnInfo(name = "Aadhaar") val Aadhaar: String? = "",
    @ColumnInfo(name = "CreatedOn") val CreatedOn: String? = "",
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: String? = "",
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "Actionby") val Actionby: Int? = 0
)
