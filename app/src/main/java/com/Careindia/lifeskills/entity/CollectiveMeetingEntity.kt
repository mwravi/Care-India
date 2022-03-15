package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblCollectiveMeeting")

data class CollectiveMeetingEntity(
    @PrimaryKey @ColumnInfo(name = "CollMeetGUID") val CollMeetGUID: String,
    @ColumnInfo(name = "CRPID") val CRPID: Int=0,
    @ColumnInfo(name = "FCID") val FCID: Int=0,
    @ColumnInfo(name = "Dateform") val Dateform: Long?,
    @ColumnInfo(name = "Col_GUID") val Col_GUID: String? = "",
    @ColumnInfo(name = "MtgNo") val MtgNo: Int?,
    @ColumnInfo(name = "Meeting_date") val Meeting_date: Long?,
    @ColumnInfo(name = "Meeting_place") val Meeting_place: String?,
    @ColumnInfo(name = "Meet_start_time") val Meet_start_time: String?,
    @ColumnInfo(name = "Meet_end_time") val Meet_end_time: String?,
    @ColumnInfo(name = "Meet_purpose") val Meet_purpose: String?,
    @ColumnInfo(name = "Meet_purpose_oth") val Meet_purpose_oth: String?,
    @ColumnInfo(name = "Member_male_WP") val Member_male_WP: Int?,
    @ColumnInfo(name = "Member_male_NWP") val Member_male_NWP: Int?,
    @ColumnInfo(name = "Member_female_WP") val Member_female_WP: Int?,
    @ColumnInfo(name = "Member_female_NWP") val Member_female_NWP: Int?,
    @ColumnInfo(name = "Member_Transgender_WP") val Member_Transgender_WP: Int?,
    @ColumnInfo(name = "Member_Transgender_NWP") val Member_Transgender_NWP: Int?,
    @ColumnInfo(name = "Attn_male_WP") val Attn_male_WP: Int?,
    @ColumnInfo(name = "Attn_male_NWP") val Attn_male_NWP: Int?,
    @ColumnInfo(name = "Attn_female_WP") val Attn_female_WP: Int?,
    @ColumnInfo(name = "Attn_female_NWP") val Attn_female_NWP: Int?,
    @ColumnInfo(name = "Attn_Transgender_WP") val Attn_Transgender_WP: Int?,
    @ColumnInfo(name = "Attn_Transgender_NWP") val Attn_Transgender_NWP: Int?,
    @ColumnInfo(name = "Savings") val Savings: String?="",
    @ColumnInfo(name = "InternalLending") val InternalLending: String?="",
    @ColumnInfo(name = "BankLoans") val BankLoans: String?="",
    @ColumnInfo(name = "Schemes_and_services") val Schemes_and_services: String?="",
    @ColumnInfo(name = "EntrepreneurialActivities") val EntrepreneurialActivities: String?="",
    @ColumnInfo(name = "TrainingActivities") val TrainingActivities: String?="",
    @ColumnInfo(name = "Change_position") val Change_position: String?="",
    @ColumnInfo(name = "ChangeGrpMember") val ChangeGrpMember: String?="",
    @ColumnInfo(name = "OtherPoint") val OtherPoint: String?="",
    @ColumnInfo(name = "TotalSavings") val TotalSavings: Int?,
    @ColumnInfo(name = "LoanAvailed_int") val LoanAvailed_int: Int?,
    @ColumnInfo(name = "LoanAvailed_ext") val LoanAvailed_ext: Int?,
    @ColumnInfo(name = "Interest_acc") val Interest_acc: Int?,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn:Long?,
    @ColumnInfo(name = "Status") val Status: Int?,
    @ColumnInfo(name = "Actionby") val Actionby: Int?,
    @ColumnInfo(name = "IsEdited") val IsEdited: Int = 0,
    @ColumnInfo(name = "Member_male_HHM") val Member_male_HHM: Int?,
    @ColumnInfo(name = "Member_female_HHM") val Member_female_HHM: Int?,
    @ColumnInfo(name = "Member_Transgender_HHM") val Member_Transgender_HHM: Int?,
    @ColumnInfo(name = "Attn_male_HHM") val Attn_male_HHM: Int?,
    @ColumnInfo(name = "Attn_female_HHM") val Attn_female_HHM: Int?,
    @ColumnInfo(name = "Attn_Transgender_HHM") val Attn_Transgender_HHM: Int?,
    @ColumnInfo(name = "Remarks") val Remarks: String? = "",
    @ColumnInfo(name = "MeetingDiscussion_Points") val MeetingDiscussion_Points: String? = "",
)

