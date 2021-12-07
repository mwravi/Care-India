package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mst_9Lookup", primaryKeys = ["LookupCode", "LookupFlag"])
data class MstLookupEntity(
    @ColumnInfo(name = "LookupCode") val LookupCode: Int,
    @ColumnInfo(name = "LookupFlag") val LookupFlag: String = "",
    @ColumnInfo(name = "SeqNo") val SeqNo: Int? = 0,
    @ColumnInfo(name = "IsActive") val IsActive: Int? = 0,
    @ColumnInfo(name = "Description") val Description: String? = "",
    @ColumnInfo(name = "LanguageID") val LanguageID: Int? = 0,
    @ColumnInfo(name = "IsDefault") val IsDefault: Int? = 0,
    @ColumnInfo(name = "Hintdetails") val Hintdetails: String? = ""

)



















