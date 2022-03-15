package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblAssessment")
data class AssessmentEntity(
    @PrimaryKey @ColumnInfo(name = "TrainingID") val TrainingID: Int,
    @ColumnInfo(name = "ModuleID") val ModuleID: Int? = 0,
    @ColumnInfo(name = "CRPID") val CRPID: Int? = 0,
    @ColumnInfo(name = "FCID") val FCID: Int? = 0,
    @ColumnInfo(name = "TrainingLocation") val TrainingLocation: String? = "",
    @ColumnInfo(name = "DateForm") val DateForm: Long? = 0,
    @ColumnInfo(name = "Community") val Community: String? = "",
    @ColumnInfo(name = "NoBaches") val NoBaches: Int? = 0,
    @ColumnInfo(name = "ParticipantsM") val ParticipantsM: Int? = 0,
    @ColumnInfo(name = "ParticipantsF") val ParticipantsF: Int? = 0,
    @ColumnInfo(name = "ParticipantsT") val ParticipantsT: Int? = 0,
    @ColumnInfo(name = "Createdby") val Createdby: Int? = 0,
    @ColumnInfo(name = "Createdon") val Createdon: Long? = 0,
    @ColumnInfo(name = "Updatedby") val Updatedby: Int? = 0,
    @ColumnInfo(name = "Updatedon") val Updatedon: Long? = 0,
    @ColumnInfo(name = "Actionby") val Actionby: Int? = 0,
    @ColumnInfo(name = "ActionOn") val ActionOn: String? = "",
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "IsCompleted") val IsCompleted: Int? = 0,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int? = 0
)