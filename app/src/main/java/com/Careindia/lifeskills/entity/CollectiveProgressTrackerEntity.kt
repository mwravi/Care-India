package com.careindia.lifeskills.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblCollectiveProgressTracker")
data class CollectiveProgressTrackerEntity(
    @PrimaryKey @ColumnInfo(name = "CPT_GUID") val CPT_GUID: String,
    @ColumnInfo(name = "FPersonName") val FPersonName: String?="",
    @ColumnInfo(name = "Designation") val Designation: String?="",
    @ColumnInfo(name = "Date") val Date: Long?=0,
    @ColumnInfo(name = "ShgName") val ShgName: String="",
    @ColumnInfo(name = "CollectiveID") val CollectiveID: String? = "",
    @ColumnInfo(name = "StateCode") val StateCode: Int?=0,
    @ColumnInfo(name = "DistrictCode") val DistrictCode: Int?=0,
    @ColumnInfo(name = "ZoneCode") val ZoneCode: Int?=0,
    @ColumnInfo(name = "Panchayat_Ward") val Panchayat_Ward: Int? = 0,
    @ColumnInfo(name = "PWCode") val PWCode: String? = "",
    @ColumnInfo(name = "Locality") val Locality: String? = "",
    @ColumnInfo(name = "Is_Collective_Registered") val Is_Collective_Registered: Int?,
    @ColumnInfo(name = "Date_Registration") val Date_Registration: Long? = 0,
    @ColumnInfo(name = "RegisteredWith") val RegisteredWith: Int? =0,
    @ColumnInfo(name = "ShgChiefName") val ShgChiefName: String? ="",
    @ColumnInfo(name = "NoCollectiveMember_M") val NoCollectiveMember_M: Int? = 0,
    @ColumnInfo(name = "NoCollectiveMember_F") val NoCollectiveMember_F: Int? = 0,
    @ColumnInfo(name = "NoNewMembers_M") val NoNewMembers_M: Int?=0,
    @ColumnInfo(name = "NoNewMembers_F") val NoNewMembers_F: Int?=0,
    @ColumnInfo(name = "NoMembersLeft_M") val NoMembersLeft_M: Int?=0,
    @ColumnInfo(name = "NoMembersLeft_F") val NoMembersLeft_F: Int?=0,
    @ColumnInfo(name = "NoMeetingHeld") val NoMeetingHeld: Int?=0,
    @ColumnInfo(name = "NoMembersMeetingOut_M") val NoMembersMeetingOut_M: Int?=0,
    @ColumnInfo(name = "NoMembersMeetingOut_F") val NoMembersMeetingOut_F: Int?=0,
    @ColumnInfo(name = "ActionTaken") val ActionTaken: Int?,
    @ColumnInfo(name = "IsEqualRepresentation") val IsEqualRepresentation: Int?,
    @ColumnInfo(name = "VoicePercentage") val VoicePercentage: Int? = 0,
    @ColumnInfo(name = "LeadersElected") val LeadersElected: Int? = 0,
    @ColumnInfo(name = "LeadershipRotation") val LeadershipRotation: Int? = 0,
    @ColumnInfo(name = "ActiveParticipation") val ActiveParticipation: Int? = 0,
    @ColumnInfo(name = "BookKeeping") val BookKeeping: Int? = 0,
    @ColumnInfo(name = "FinancialLiteracy") val FinancialLiteracy: Int? = 0,
    @ColumnInfo(name = "GroupSavingManage") val GroupSavingManage: Int? = 0,
    @ColumnInfo(name = "MoneyLanding") val MoneyLanding: Int?,
    @ColumnInfo(name = "ReceivingLoanPercentage") val ReceivingLoanPercentage: Int? = 0,
    @ColumnInfo(name = "RepaymentFreq") val RepaymentFreq: Int? = 0,
    @ColumnInfo(name = "AddressIssue") val AddressIssue: Int?,
    @ColumnInfo(name = "LifeSkill") val LifeSkill: Int? = 0,
    @ColumnInfo(name = "LifeSkillPlus") val LifeSkillPlus: Int? = 0,
    @ColumnInfo(name = "EDP") val EDP: Int? = 0,
    @ColumnInfo(name = "Collectivization") val Collectivization: Int? = 0,
    @ColumnInfo(name = "StartingBusiness") val StartingBusiness: Int? = 0,
    @ColumnInfo(name = "BusinessInitiativeTaken") val BusinessInitiativeTaken: Int?,
    @ColumnInfo(name = "BusinessInitiativeReceived") val BusinessInitiativeReceived: Int? = 0,
    @ColumnInfo(name = "BusinessInitiativeOthers") val BusinessInitiativeOthers: String? ="",
    @ColumnInfo(name = "BusinessInitiativeKind") val BusinessInitiativeKind: String? = "",
    @ColumnInfo(name = "NoFederation") val NoFederation: Int? = 0,
    @ColumnInfo(name = "AlternativeJobs") val AlternativeJobs: Int? = 0,
    @ColumnInfo(name = "AJInitiative") val AJInitiative: Int?,
    @ColumnInfo(name = "AJInitiativeReceived") val AJInitiativeReceived: Int? = 0,
    @ColumnInfo(name = "AJInitiativeReceivedKind") val AJInitiativeReceivedKind: String? = "",
    @ColumnInfo(name = "LinkageEstablished") val LinkageEstablished: Int? = 0,
    @ColumnInfo(name = "PostTraining") val PostTraining: Int? = 0,
    @ColumnInfo(name = "NoCapacityBuilding") val NoCapacityBuilding: Int? = 0,
    @ColumnInfo(name = "WomanTrainedBooks") val WomanTrainedBooks: Int? = 0,
    @ColumnInfo(name = "GenderSensitive") val GenderSensitive: Int?,
    @ColumnInfo(name = "CorpusFundStatus") val CorpusFundStatus: Int? = 0,
    @ColumnInfo(name = "FrequencyRepayment") val FrequencyRepayment: Int? = 0,
    @ColumnInfo(name = "InternalLoanManage") val InternalLoanManage: Int? = 0,
    @ColumnInfo(name = "SelfSustaining") val SelfSustaining: Int?,
    @ColumnInfo(name = "ExposureVisit") val ExposureVisit: Int?,
    @ColumnInfo(name = "NoVisits") val NoVisits: Int? = 0,
    @ColumnInfo(name = "OrgName") val OrgName: String? = "",
    @ColumnInfo(name = "NoMembersParticipated_M") val NoMembersParticipated_M: Int?=0,
    @ColumnInfo(name = "NoMembersParticipated_F") val NoMembersParticipated_F: Int?=0,
    @ColumnInfo(name = "Purpose") val Purpose: String?="",
    @ColumnInfo(name = "NoWomenArticulated") val NoWomenArticulated: Int?=0,
    @ColumnInfo(name = "NoEcoSystem") val NoEcoSystem: Int?=0,
    @ColumnInfo(name = "SchemesMobilized") val SchemesMobilized: Int?,
    @ColumnInfo(name = "NoSchemesMobilized") val NoSchemesMobilized: Int?=0,
    @ColumnInfo(name = "BankAccountOpened") val BankAccountOpened: Int?,
    @ColumnInfo(name = "NoBankAccountOpened_M") val NoBankAccountOpened_M: Int?=0,
    @ColumnInfo(name = "NoBankAccountOpened_F") val NoBankAccountOpened_F: Int?=0,
    @ColumnInfo(name = "AccessFinancialRes") val AccessFinancialRes: Int?,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: Int?=0,
    @ColumnInfo(name = "CreatedOn") val CreatedOn: Long?=0,
    @ColumnInfo(name = "UpdatedBy") val UpdatedBy: Int? = 0,
    @ColumnInfo(name = "UpdatedOn") val UpdatedOn: Long?,
    @ColumnInfo(name = "Status") val Status: Int? = 0,
    @ColumnInfo(name = "Actionby") val Actionby: Int? = 0,
    @ColumnInfo(name = "Initials") val Initials: String = "",
    @ColumnInfo(name = "Remarks") val Remarks: String = "",
    @ColumnInfo(name = "IsEdited") val IsEdited: Int = 0

)