package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveProgressTrackerEntity
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity

@Dao
interface CollectiveProgressTrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveProgressTrackerEntity: List<CollectiveProgressTrackerEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollProgTrackerData(collectiveEntity: CollectiveProgressTrackerEntity?)

    @Query("Select * from tblCollectiveProgressTracker where CollectiveID=:cuid")
    fun getallCollProgTrackerdata(cuid:String): LiveData<List<CollectiveProgressTrackerEntity>>

    @Query("Select * from tblCollectiveProgressTracker where CPT_GUID=:Guid")
    fun getLiveCollProgTrackerdatabyGuid(Guid: String): LiveData<List<CollectiveProgressTrackerEntity>>

    @Query("Select * from tblCollectiveProgressTracker where CPT_GUID=:Guid")
    fun getCollProgTrackerdatabyGuid(Guid: String): List<CollectiveProgressTrackerEntity>

    @Delete
    fun deleteCollProgTrackerdata(collectiveProgressTrackerEntity: CollectiveProgressTrackerEntity)

    @Query("DELETE from tblCollectiveProgressTracker where CPT_GUID=:CPTGUID")
    fun deleteCollProgTrackerdata(CPTGUID: String?)

    @Query("DELETE from tblCollectiveProgressTracker")
    fun deleteAllData()

    @Query("select MAX(substr(CollectiveID, 9, 11)) from tblCollectiveProgressTracker")
    fun getCommunityCount(): Int

    @Query("select count(*) from tblCollectiveProgressTracker where CollectiveID=:CollectiveID")
    fun getCommunityID(CollectiveID: String): Int

    @Query("Select Count(*) from tblCollectiveProgressTracker where IsEdited=1")
    fun getColltrackerunsyncCount(): String

    @Query("Select Count(*) from tblCollectiveProgressTracker")
    fun getColltrackertotalCount(): String


    @Query("update tblCollectiveProgressTracker set  CPT_GUID=:CPT_GUID, FPersonName=:PersonName, Designation=:Designation,Date=:Date,ShgName=:ShgCigName,CollectiveID=:collcode,StateCode=:StateCode,DistrictCode=:DistrictName,ZoneCode=:i,Panchayat_Ward=:i1,PWCode=:s,Locality=:LocalityName,Is_Collective_Registered=:Registered,Date_Registration=:RegDate,RegisteredWith=:Orgnization,ShgChiefName=:CIGCheifName,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,IsEdited=:IsEdited,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptFirst(
        CPT_GUID: String,
        PersonName: String,
        Designation: String,
        Date: Long,
        ShgCigName: String,
        collcode: String,
        StateCode: Int,
        DistrictName: Int,
        i: Int,
        i1: Int,
        s: String,
        LocalityName: String,
        Registered: Int,
        RegDate: Long,
        Orgnization: Int,
        CIGCheifName: String,
        UpdatedBy: Int,
        UpdatedOn: Long,
        IsEdited:Int
    )

    @Query("update tblCollectiveProgressTracker set  CPT_GUID=:CPT_GUID, NoCollectiveMember_M=:Male, NoCollectiveMember_F=:Female,NoNewMembers_M=:MaleJoined,NoNewMembers_F=:FemaleJoined,NoMembersLeft_M=:MaleLeft,NoMembersLeft_F=:FemaleLeft,NoMeetingHeld=:MeetingHeld,NoMembersMeetingOut_M=:MaleParticipated,NoMembersMeetingOut_F=:FemaleParticipated,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,IsEdited=:IsEdited,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptSecond(
        CPT_GUID:String,
        Male: String,
        Female: String,
        MaleJoined: String,
        FemaleJoined: String,
        MaleLeft: String,
        FemaleLeft: String,
        MeetingHeld: String,
        MaleParticipated: String,
        FemaleParticipated: String,
        UpdatedBy: Int,
        UpdatedOn: Long,
        IsEdited:Int
    )

    @Query("update tblCollectiveProgressTracker set  CPT_GUID=:CPT_GUID, ActionTaken=:Action, IsEqualRepresentation=:Representation,VoicePercentage=:Voice,LeadersElected=:Elected,LeadershipRotation=:Rotation,ActiveParticipation=:Active,BookKeeping=:Book,FinancialLiteracy=:Financial,GroupSavingManage=:Manage,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,IsEdited=:IsEdited,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptThird(
        CPT_GUID: String,
        Action: Int,
        Representation: Int,
        Voice: Int,
        Elected: Int,
        Rotation: Int,
        Active: Int,
        Book: Int,
        Financial: Int,
        Manage: Int,
        UpdatedBy: Int,
        UpdatedOn: Long,
        IsEdited:Int
    )

    @Query("update tblCollectiveProgressTracker set  MoneyLanding=:MoneyLanding, ReceivingLoanPercentage=:ReceivingLoanPercentage, RepaymentFreq=:RepaymentFreq,AddressIssue=:AddressIssue,LifeSkill=:LifeSkill,LifeSkillPlus=:LifeSkillPlus,EDP=:EDP,Collectivization=:Collectivization,StartingBusiness=:StartingBusiness,BusinessInitiativeTaken=:BusinessInitiativeTaken,BusinessInitiativeReceived=:BusinessInitiativeReceived,BusinessInitiativeOthers=:BusinessInitiativeOthers,BusinessInitiativeKind=:BusinessInitiativeKind,IsEdited=:IsEdited,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptfourth(
        CPT_GUID: String?,
        MoneyLanding: Int?,
        ReceivingLoanPercentage: Int?,
        RepaymentFreq: Int?,
        AddressIssue: Int?,
        LifeSkill: Int?,
        LifeSkillPlus: Int?,
        EDP: Int?,
        Collectivization: Int?,
        StartingBusiness: Int?,
        BusinessInitiativeTaken: Int?,
        BusinessInitiativeReceived: Int?,
        BusinessInitiativeOthers: String?,
        BusinessInitiativeKind: String?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    )

    @Query("update tblCollectiveProgressTracker set  NoFederation=:NoFederation, AlternativeJobs=:AlternativeJobs, AJInitiative=:AJInitiative,AJInitiativeReceived=:AJInitiativeReceived,AJInitiativeReceivedKind=:AJInitiativeReceivedKind,LinkageEstablished=:LinkageEstablished,PostTraining=:PostTraining,NoCapacityBuilding=:NoCapacityBuilding,WomanTrainedBooks=:WomanTrainedBooks,GenderSensitive=:GenderSensitive,IsEdited=:IsEdited,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptfifth(
        CPT_GUID: String?,
        NoFederation: Int?,
        AlternativeJobs: Int?,
        AJInitiative: Int?,
        AJInitiativeReceived: Int?,
        AJInitiativeReceivedKind: String?,
        LinkageEstablished: Int?,
        PostTraining: Int?,
        NoCapacityBuilding: Int?,
        WomanTrainedBooks: Int?,
        GenderSensitive: Int?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    )

    @Query("update tblCollectiveProgressTracker set  CorpusFundStatus=:CorpusFundStatus, FrequencyRepayment=:FrequencyRepayment, InternalLoanManage=:InternalLoanManage,SelfSustaining=:SelfSustaining,ExposureVisit=:ExposureVisit,NoVisits=:NoVisits,OrgName=:OrgName,NoMembersParticipated_M=:NoMembersParticipated_M,NoMembersParticipated_F=:NoMembersParticipated_F,Purpose=:Purpose,NoWomenArticulated=:NoWomenArticulated,NoEcoSystem=:NoEcoSystem,IsEdited=:IsEdited,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptsixth(
        CPT_GUID: String?,
        CorpusFundStatus: Int?,
        FrequencyRepayment: Int?,
        InternalLoanManage: Int?,
        SelfSustaining: Int?,
        ExposureVisit: Int?,
        NoVisits: Int?,
        OrgName: String?,
        NoMembersParticipated_M: Int?,
        NoMembersParticipated_F: Int?,
        Purpose: String?,
        NoWomenArticulated: Int?,
        NoEcoSystem: Int?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    )

    @Query("update tblCollectiveProgressTracker set  SchemesMobilized=:SchemesMobilized, NoSchemesMobilized=:NoSchemesMobilized, BankAccountOpened=:BankAccountOpened,NoBankAccountOpened_M=:NoBankAccountOpened_M,NoBankAccountOpened_F=:NoBankAccountOpened_F,AccessFinancialRes=:AccessFinancialRes,IsEdited=:IsEdited,UpdatedBy=:UpdatedBy,UpdatedOn=:UpdatedOn,Status=0 where CPT_GUID=:CPT_GUID")
    fun updatecptseventh(
        CPT_GUID: String?,
        SchemesMobilized: Int?,
        NoSchemesMobilized: Int?,
        BankAccountOpened: Int?,
        NoBankAccountOpened_M: Int?,
        NoBankAccountOpened_F: Int?,
        AccessFinancialRes: Int?,
        IsEdited: Int,
        UpdatedBy: Int?,
        UpdatedOn: Long?
    )

    @Query("Select * from tblCollectiveProgressTracker where DistrictCode=:iDisCode and ZoneCode=:izone and Panchayat_Ward=:iward and CollectiveID=:cuid")
    fun getCommWData(iDisCode:Int,izone: Int, iward: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>>

    @Query("Select * from tblCollectiveProgressTracker where DistrictCode=:iDisCode and ZoneCode=:izone and CollectiveID=:cuid")
    fun getCommWData(iDisCode:Int,izone: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>>

    @Query("Select * from tblCollectiveProgressTracker where DistrictCode=:iDisCode and CollectiveID=:cuid")
    fun getCommWData(iDisCode:Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>>


    @Query("Select * from tblCollectiveProgressTracker where DistrictCode=:iDisCode and Panchayat_Ward=:iPanchayat and CollectiveID=:cuid")
    fun getCommPData(iDisCode:Int,iPanchayat: Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>>

    @Query("Select * from tblCollectiveProgressTracker where DistrictCode=:iDisCode and CollectiveID=:cuid")
    fun getCommPData(iDisCode:Int,cuid:String): LiveData<List<CollectiveProgressTrackerEntity>>

    @Query("Select * from tblCollectiveProgressTracker where IsEdited=1")
    fun getallCollProgdata(): List<CollectiveProgressTrackerEntity>

    @Query("Update tblCollectiveProgressTracker set IsEdited=0")
    fun updateIsEdited()

    @Query("Update tblCollectiveProgressTracker set Remarks=:remarks, Status=:status where CPT_GUID =:cptguid")
    fun updateStatusData(remarks:String,status: Int, cptguid: String)
}