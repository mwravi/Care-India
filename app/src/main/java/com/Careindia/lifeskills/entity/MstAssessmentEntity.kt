package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mstAssessment")
data class MstAssessmentEntity(
    @PrimaryKey @ColumnInfo(name = "QID") val QID: Int,
    @ColumnInfo(name = "ModuleID") val ModuleID: Int? = 0,
    @ColumnInfo(name = "Question") val Question: String? = "",
    @ColumnInfo(name = "SeqNo") val SeqNo: Int? = 0,
    @ColumnInfo(name = "LookupID") val LookupID: Int? = 0,
    @ColumnInfo(name = "IsActive") val IsActive: Int? = 0,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int? = 0,
    @ColumnInfo(name = "Createdon") val Createdon: String? = "",
    @ColumnInfo(name = "Updatedby") val Updatedby: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: String? = "",
    @ColumnInfo(name = "PageID") val PageID: Int? = 0,
    @ColumnInfo(name = "PageHeader") val PageHeader: String? = "",
    @ColumnInfo(name = "FormID") val FormID: Int? = 0,
    @ColumnInfo(name = "QuestionType") val QuestionType: Int? = 0,
    @ColumnInfo(name = "QNo") val QNo: String? = "",
    @ColumnInfo(name = "DependentQuestion") val DependentQuestion: String? = "",
    @ColumnInfo(name = "DependentOption") val DependentOption: String? = "",
    @ColumnInfo(name = "Skip") val Skip: String? = "",
    @ColumnInfo(name = "Link") val Link: String? = ""
)