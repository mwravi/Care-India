package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblProfileIndividual")
data class IndividualProfileEntity(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "IndGUID") val IndGUID: String?="",
    @ColumnInfo(name = "HHGUID") val HHGUID: String?="",
    @ColumnInfo(name = "StateCode") val StateCode: String?="",
    @ColumnInfo(name = "DistrictCode") val DistrictCode: String?="",
    @ColumnInfo(name = "ZoneCode") val ZoneCode: String?="",
    @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int?=0,
    @ColumnInfo(name = "PWCode") val PWCode: String?="",
    @ColumnInfo(name = "Localitycode") val Localitycode: Int?=0,
    @ColumnInfo(name = "DateForm") val DateForm: String?="",
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
    @ColumnInfo(name = "ResidingSince") val ResidingSince: Int?=0,
    @ColumnInfo(name = "Read_Write") val Read_Write: Int?=0,
    @ColumnInfo(name = "Education") val Education: Int?=0,
    @ColumnInfo(name = "Smartphone") val Smartphone: Int?=0,
    @ColumnInfo(name = "MobileData") val MobileData: Int?=0,
    @ColumnInfo(name = "Languages_Read") val Languages_Read: String?="",
    @ColumnInfo(name = "Languages_Write") val Languages_Write: String?="",
    @ColumnInfo(name = "Languages_Speak") val Languages_Speak: String?="",
    @ColumnInfo(name = "PreferredLanguage_Communication") val PreferredLanguage_Communication: String?="",
    @ColumnInfo(name = "PreferredLanguage_Mobile") val PreferredLanguage_Mobile: String?="",
    @ColumnInfo(name = "WP_category") val WP_category: Int?=0,
    @ColumnInfo(name = "Employment_Type") val Employment_Type: Int?=0,
    @ColumnInfo(name = "Waste_Type") val Waste_Type: String?="",
    @ColumnInfo(name = "Waste_Disposal") val Waste_Disposal: Int?=0,
    @ColumnInfo(name = "Primary_Occupation") val Primary_Occupation: Int?=0,
    @ColumnInfo(name = "Primary_WD") val Primary_WD: Int?=0,
    @ColumnInfo(name = "Primary_Inc") val Primary_Inc: Int?=0,
    @ColumnInfo(name = "IsSecondary_Occupation") val IsSecondary_Occupation: Int?=0,
    @ColumnInfo(name = "Secondary_Occupation") val Secondary_Occupation: Int?=0,
    @ColumnInfo(name = "Secondary_WD") val Secondary_WD: Int?=0,
    @ColumnInfo(name = "Secondary_Inc") val Secondary_Inc: Int?=0,
    @ColumnInfo(name = "Aadhaar") val Aadhaar: Int?=0,
    @ColumnInfo(name = "Voter") val Voter: Int?=0,
    @ColumnInfo(name = "PAN") val PAN: Int?=0,
    @ColumnInfo(name = "IncomeCertificate") val IncomeCertificate: Int?=0,
    @ColumnInfo(name = "CasteCertificate") val CasteCertificate: Int?=0,
    @ColumnInfo(name = "BankAccount") val BankAccount: Int?=0,
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
    @ColumnInfo(name = "CreatedOn") val CreatedOn: String?="",
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int?=0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: String?="",
    @ColumnInfo(name = "Status") val Status: Int?=0,
    @ColumnInfo(name = "Actionby") val Actionby: Int?=0
)
