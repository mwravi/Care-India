package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblProfileIndividual")
data class IndividualProfileEntity(
    @PrimaryKey@ColumnInfo(name = "IndGUID") val IndGUID: String,
    @ColumnInfo(name = "HHGUID") val HHGUID: String?="",
    @ColumnInfo(name = "Name_CRP") val Name_CRP: String?="",
    @ColumnInfo(name = "Name_SC") val Name_SC: String?="",
    @ColumnInfo(name = "StateCode") val StateCode: Int?=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int=0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int=0,
    @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int?=0,
    @ColumnInfo(name = "PWCode") val PWCode: String?="",
    @ColumnInfo(name = "Locality") val Locality: String?="",
    @ColumnInfo(name = "DateForm") val DateForm: Long?,
    @ColumnInfo(name = "HHCode") val HHCode: String?="",
    @ColumnInfo(name = "IndvCode") val IndvCode: String?="",
    @ColumnInfo(name = "Name") val Name: String?="",
    @ColumnInfo(name = "Gender") val Gender: Int?=0,
    @ColumnInfo(name = "Age") val Age: Int?=0,
    @ColumnInfo(name = "Caste") val Caste: Int?=0,
    @ColumnInfo(name = "MaritalStatus") val MaritalStatus: Int?=0,
    @ColumnInfo(name = "Contact") val Contact: String?="",
    @ColumnInfo(name = "Alt_contact") val Alt_contact: String?="",
    @ColumnInfo(name = "StateID") val StateID: Int?=0,
    @ColumnInfo(name = "State_Other") val State_Other: String?="",
    @ColumnInfo(name = "ResidingSince") val ResidingSince: Int?=0,
    @ColumnInfo(name = "Read_Write") val Read_Write: Int?,
    @ColumnInfo(name = "Education") val Education: Int?,
    @ColumnInfo(name = "Smartphone") val Smartphone: Int?,
    @ColumnInfo(name = "MobileData") val MobileData: Int?,
    @ColumnInfo(name = "Languages_Read") val Languages_Read: String?="",
    @ColumnInfo(name = "Languages_Write") val Languages_Write: String?="",
    @ColumnInfo(name = "Languages_Speak") val Languages_Speak: String?="",
    @ColumnInfo(name = "Speak_Other") val Speak_Other: String?="",
    @ColumnInfo(name = "Read_Other") val Read_Other: String?="",
    @ColumnInfo(name = "Write_Other") val Write_Other: String?="",
    @ColumnInfo(name = "Prefer_Commu_Other") val Prefer_Commu_Other: String?="",
    @ColumnInfo(name = "PreferredLanguage_Communication") val PreferredLanguage_Communication: String?="",
    @ColumnInfo(name = "PreferredLanguage_Mobile") val PreferredLanguage_Mobile: String?="",
    @ColumnInfo(name = "Mobile_Other") val Mobile_Other: String?="",
    @ColumnInfo(name = "WP_category") val WP_category: Int?=0,
    @ColumnInfo(name = "Employment_Type") val Employment_Type: Int?=0,
    @ColumnInfo(name = "Waste_Type") val Waste_Type: String?="",
    @ColumnInfo(name = "Waste_Disposal") val Waste_Disposal: String?="",
    @ColumnInfo(name = "Dispose_Other") val Dispose_Other: String?="",
    @ColumnInfo(name = "Primary_Occupation") val Primary_Occupation: Int?=0,
    @ColumnInfo(name = "Primary_Inc_Other") val Primary_Inc_Other: String?="",
    @ColumnInfo(name = "Primary_WD") val Primary_WD: Int?=0,
    @ColumnInfo(name = "Primary_Inc") val Primary_Inc: Int?=0,
    @ColumnInfo(name = "IsSecondary_Occupation") val IsSecondary_Occupation: Int?,
    @ColumnInfo(name = "Secondary_Occupation") val Secondary_Occupation: Int?=0,
    @ColumnInfo(name = "Sec_Inc_Other") val Sec_Inc_Other: String?="",
    @ColumnInfo(name = "Secondary_WD") val Secondary_WD: Int?=0,
    @ColumnInfo(name = "Secondary_Inc") val Secondary_Inc: Int?=0,
    @ColumnInfo(name = "Aadhaar") val Aadhaar: Int?,
    @ColumnInfo(name = "Voter") val Voter: Int?,
    @ColumnInfo(name = "PAN") val PAN: Int?,
    @ColumnInfo(name = "IncomeCertificate") val IncomeCertificate: Int?,
    @ColumnInfo(name = "CasteCertificate") val CasteCertificate: Int?,
    @ColumnInfo(name = "BankAccount") val BankAccount: Int?,
    @ColumnInfo(name = "SchemesAvailed") val SchemesAvailed: Int?=0,
    @ColumnInfo(name = "SchemeDetails") val SchemeDetails: String?="",
    @ColumnInfo(name = "SchemeSP") val SchemeSP: String?="",
    @ColumnInfo(name = "SchemesAvailed_Cur") val SchemesAvailed_Cur: Int?=0,
    @ColumnInfo(name = "SchemeDetails_Cur") val SchemeDetails_Cur: String?="",
    @ColumnInfo(name = "SchemeSP_Cur") val SchemeSP_Cur: String?="",
    @ColumnInfo(name = "Jobs") val Jobs: String?="",
    @ColumnInfo(name = "Interested_Job") val Interested_Job: Int?=0,
    @ColumnInfo(name = "Interested_JobDetail") val Interested_JobDetail: String?="",
    @ColumnInfo(name = "Member_Collective") val Member_Collective: Int?=0,
    @ColumnInfo(name = "Collective_name") val Collective_name: String?="",
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?=0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
    @ColumnInfo(name = "Status") val Status: Int?=0,
    @ColumnInfo(name = "Actionby") val Actionby: Int?=0,
    @ColumnInfo(name = "CRPID") val CRPID: Int = 0,
    @ColumnInfo(name = "FCID") val FCID: Int = 0,
    @ColumnInfo(name = "Initials") val Initials: String = "",
    @ColumnInfo(name = "Skills") val Skills: String? = "",
    @ColumnInfo(name = "Skills_oth") val Skills_oth: String? = "",
    @ColumnInfo(name = "Remarks") val Remarks: String? = "",
    @ColumnInfo(name = "Laboure_oth") val Laboure_oth: String? = "",
    @ColumnInfo(name = "Business_oth") val Business_oth: String? = "",
    @ColumnInfo(name = "IsEdited") val IsEdited: Int = 0
)
