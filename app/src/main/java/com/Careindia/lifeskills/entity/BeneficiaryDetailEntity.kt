package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tblBeneficiaryDetail",primaryKeys = ["Bene_GUID","QID"])
data class BeneficiaryDetailEntity(
    @ColumnInfo(name = "Bene_GUID") val Bene_GUID: String,
    @ColumnInfo(name = "QID") val QID: Int,
    @ColumnInfo(name = "QuestionValue") val QuestionValue: String?,
    @ColumnInfo(name = "IsEdited") var IsEdited: Int = 0
)