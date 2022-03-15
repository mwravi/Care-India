package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblAssessmentDetail",primaryKeys = ["TrainingID","QID","GroupBatchID"])
data class AssessmentDetailEntity(
    @ColumnInfo(name = "TrainingID") val TrainingID: Int,
    @ColumnInfo(name = "QID") val QID: Int,
    @ColumnInfo(name = "PreScore") val PreScore: Int? = -1,
    @ColumnInfo(name = "PostScore") val PostScore: Int? = -1,
    @ColumnInfo(name = "AggScore") val AggScore: Int? = 0,
    @ColumnInfo(name = "GroupBatchID") val GroupBatchID: Int,
    @ColumnInfo(name = "IsEdited") var IsEdited: Int? = 0

)