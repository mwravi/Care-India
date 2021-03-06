package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblCollective")
data class CollectiveEntity(
    @PrimaryKey @ColumnInfo(name = "Col_GUID") val Col_GUID: String,
    @ColumnInfo(name = "StateCode") val StateCode: Int=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int=0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int=0,
    @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int? = 0,
    @ColumnInfo(name = "PWCode") val PWCode: String? = "",
    @ColumnInfo(name = "Localitycode") val Localitycode: String? = "",
    @ColumnInfo(name = "LandMark") val LandMark: String? = "",
    @ColumnInfo(name = "PinCode") val PinCode: String? = "",
    @ColumnInfo(name = "DateForm") val DateForm: Long?,
    @ColumnInfo(name = "CollectiveID") val CollectiveID: String? = "",
    @ColumnInfo(name = "CollectiveName") val CollectiveName: String? = "",
    @ColumnInfo(name = "Date_formation") val Date_formation: Long?,
    @ColumnInfo(name = "Type") val Type: Int? = 0,
    @ColumnInfo(name = "TypeOther") val TypeOther: String? = "",
    @ColumnInfo(name = "Registration") val Registration: Int? = 0,
    @ColumnInfo(name = "RegistrationOther") val RegistrationOther: String? = "",
    @ColumnInfo(name = "Objective") val Objective: String? = "",
    @ColumnInfo(name = "Head_name") val Head_name: String? = "",
    @ColumnInfo(name = "Head_gender") val Head_gender: Int? = 0,
    @ColumnInfo(name = "NoMembers") val NoMembers: Int? = 0,
    @ColumnInfo(name = "NoMembers_M") val NoMembers_M: Int? = 0,
    @ColumnInfo(name = "NoMembers_F") val NoMembers_F: Int? = 0,
    @ColumnInfo(name = "NoMembers_T") val NoMembers_T: Int? = 0,
    @ColumnInfo(name = "Tenure_President") val Tenure_President: Int? = 0,
    @ColumnInfo(name = "Rolerotation") val Rolerotation: Int? = -1,
    @ColumnInfo(name = "Elections") val Elections: Int? = 0,
    @ColumnInfo(name = "Election_Freq") val Election_Freq: Int? = 0,
    @ColumnInfo(name = "IsBank") val IsBank: Int? = 0,
    @ColumnInfo(name = "Bank_Challenges") val Bank_Challenges: String? = "",
    @ColumnInfo(name = "Savings") val Savings: Int? = 0,
    @ColumnInfo(name = "Savings_Oth") val Savings_Oth: Int? = 0,
    @ColumnInfo(name = "Savings_Freq") val Savings_Freq: Int? = 0,
    @ColumnInfo(name = "Savings_FreqOth") val Savings_FreqOth: String? = "",
    @ColumnInfo(name = "Savings_Regularity") val Savings_Regularity: Int? = 0,
    @ColumnInfo(name = "Savings_RegularityOther") val Savings_RegularityOther: String? = "",
    @ColumnInfo(name = "Loan_Availed") val Loan_Availed: Int? = 0,
    @ColumnInfo(name = "Loan_Availed_Where") val Loan_Availed_Where: Int? = 0,
    @ColumnInfo(name = "Loan_Availed_Others") val Loan_Availed_Others: String? = "",
    @ColumnInfo(name = "Loan_Challenges") val Loan_Challenges: String? = "",
    @ColumnInfo(name = "Meeting_Held") val Meeting_Held: Int? = 0,
    @ColumnInfo(name = "Meeting_Freq") val Meeting_Freq: Int? = 0,
    @ColumnInfo(name = "Meeting_FreqOth") val Meeting_FreqOth: String? = "",
    @ColumnInfo(name = "Meeting_Regularity") val Meeting_Regularity: Int? = 0,
    @ColumnInfo(name = "Meeting_Schedule") val Meeting_Schedule: Int? = 0,
    @ColumnInfo(name = "Register_maintained") val Register_maintained: Int? = 0,
    @ColumnInfo(name = "Register_regular") val Register_regular: Int? = 0,
    @ColumnInfo(name = "Linkages") val Linkages: String? = "",
    @ColumnInfo(name = "Linkages_oth") val Linkages_oth: String? = "",
    @ColumnInfo(name = "Linkage_Services") val Linkage_Services: String? = "",
    @ColumnInfo(name = "IsService_availed") val IsService_availed: Int? = 0,
    @ColumnInfo(name = "Services_availed") val Services_availed: String? = "",
    @ColumnInfo(name = "Services_dept") val Services_dept: String? = "",
    @ColumnInfo(name = "Collective_Schemes") val Collective_Schemes: Int? = 0,
    @ColumnInfo(name = "Collective_opp") val Collective_opp: String? = "",
    @ColumnInfo(name = "Collective_opp_Other") val Collective_opp_Other: String? = "",
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "Actionby") val Actionby: Int? = 0,
    @ColumnInfo(name = "CRPID") val CRPID: Int = 0,
    @ColumnInfo(name = "FCID") val FCID: Int = 0,
    @ColumnInfo(name = "Initials") val Initials: String? = "",
    @ColumnInfo(name = "Remarks") val Remarks: String? = "",
    @ColumnInfo(name = "IsEdited") val IsEdited: Int = 0
)
