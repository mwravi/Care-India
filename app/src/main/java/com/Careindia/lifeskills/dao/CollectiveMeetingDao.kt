package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity

@Dao
interface CollectiveMeetingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveMeetingEntity: CollectiveMeetingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeetingData(collectiveMeetingEntity: List<CollectiveMeetingEntity>?)

    @Query("DELETE from tblCollectiveMeeting")
    fun deleteAllData()

    @Query("Select * from tblCollectiveMeeting where Col_GUID=:Col_GUID")
    fun getAllMemberData(Col_GUID: String): LiveData<List<CollectiveMeetingEntity>>


    @Query("Select * from tblCollectiveMeeting")
    fun getAllMemberData(): LiveData<List<CollectiveMeetingEntity>>

    @Query("Select Count(*) from tblCollectiveMeeting where IsEdited=1")
    fun getMeetunsyncCount(): String

    @Query("Select Count(*) from tblCollectiveMeeting")
    fun getMeettotalCount(): String

    @Query("update tblCollectiveMeeting set UpdatedBy=:updatedBy,UpdatedOn=:updatedOn, Dateform=:Dateform, Col_GUID=:Col_GUID, Meeting_date=:Meeting_date, Meeting_place=:Meeting_place, Meet_start_time=:Meet_start_time, Meet_end_time=:Meet_end_time, Meet_purpose=:Meet_purpose, Meet_purpose_oth=:Meet_purpose_oth,IsEdited=:IsEdited,Status=0 where CollMeetGUID=:CollMeetGUID")
    fun updatecollectiveMember(
        CollMeetGUID: String,
        Dateform: Long,
        Col_GUID: String,
        Meeting_date: Long,
        Meeting_place: String,
        Meet_start_time: String,
        Meet_end_time: String,
        Meet_purpose: String,
        Meet_purpose_oth: String,
        updatedBy:Int?,
        updatedOn:Long?,
        IsEdited: Int
    )

    @Query("update tblCollectiveMeeting set UpdatedBy=:updatedBy,UpdatedOn=:updatedOn, Attn_Transgender_HHM=:attn_Transgender_HHM, Attn_female_HHM=:attn_female_HHM, Attn_male_HHM=:attn_male_HHM, Member_Transgender_HHM=:member_Transgender_HHM, Member_female_HHM=:member_female_HHM, Member_male_HHM=:member_male_HHM, Member_male_WP=:Member_male_WP, Member_male_NWP=:Member_male_NWP, Member_female_WP=:Member_female_WP, Member_female_NWP=:Member_female_NWP, Member_Transgender_WP=:Member_Transgender_WP, Member_Transgender_NWP=:Member_Transgender_NWP,Attn_male_WP=:Attn_male_WP, Attn_male_NWP=:Attn_male_NWP, Attn_female_WP=:Attn_female_WP, Attn_female_NWP=:Attn_female_NWP, Attn_Transgender_WP=:Attn_Transgender_WP, Attn_Transgender_NWP=:Attn_Transgender_NWP,IsEdited=:IsEdited,Status=0 where CollMeetGUID=:CollMeetGUID")
    fun updatecollectiveMeetSec(
        CollMeetGUID: String,
        Member_male_WP: Int,
        Member_male_NWP: Int,
        Member_female_WP: Int,
        Member_female_NWP: Int,
        Member_Transgender_WP: Int,
        Member_Transgender_NWP: Int,
        Attn_male_WP: Int,
        Attn_male_NWP: Int,
        Attn_female_WP: Int,
        Attn_female_NWP: Int,
        Attn_Transgender_WP: Int,
        Attn_Transgender_NWP: Int,
        member_male_HHM: Int,
        member_female_HHM: Int,
        member_Transgender_HHM: Int,
        attn_male_HHM: Int,
        attn_female_HHM: Int,
        attn_Transgender_HHM: Int,
        updatedBy:Int?,
        updatedOn:Long?,
        IsEdited: Int
    )


    @Query("update tblCollectiveMeeting set MeetingDiscussion_Points=:meetingDiscussion_Points, UpdatedBy=:updatedBy,UpdatedOn=:updatedOn, Savings=:Savings, InternalLending=:InternalLending, BankLoans=:BankLoans, Schemes_and_services=:Schemes_and_services, EntrepreneurialActivities=:EntrepreneurialActivities,TrainingActivities=:TrainingActivities, Change_position=:Change_position,ChangeGrpMember=:ChangeGrpMember, OtherPoint=:OtherPoint,IsEdited=:IsEdited,Status=0 where CollMeetGUID=:CollMeetGUID")
    fun updatecollectiveMeetThird(
        CollMeetGUID: String,
        Savings: String?,
        InternalLending: String?,
        BankLoans: String?,
        Schemes_and_services: String?,
        EntrepreneurialActivities: String?,
        TrainingActivities: String?,
        Change_position: String?,
        ChangeGrpMember: String?,
        OtherPoint: String?,
        meetingDiscussion_Points: String?,
        updatedBy:Int?,
        updatedOn:Long?,
        IsEdited: Int
    )

    @Query("update tblCollectiveMeeting set UpdatedBy=:updatedBy,UpdatedOn=:updatedOn, TotalSavings=:TotalSavings, LoanAvailed_int=:LoanAvailed_int, LoanAvailed_ext=:LoanAvailed_ext,Interest_acc=:Interest_acc,IsEdited=:IsEdited,Status=0 where CollMeetGUID=:CollMeetGUID")
    fun updatecollectiveMeetFourth(
        CollMeetGUID: String,
        TotalSavings: Int?,
        LoanAvailed_int: Int?,
        LoanAvailed_ext: Int?,
        Interest_acc: Int?,
        updatedBy:Int?,
        updatedOn:Long?,
        IsEdited: Int
    )

    @Query("select * from tblCollectiveMeeting where CollMeetGUID=:guid")
    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMeetingEntity>>

    @Query("select * from tblCollective where Col_GUID=:guid")
    fun getNW_NWP_data(guid: String): List<CollectiveEntity>

    @Query("select CollectiveName from tblCollective where Col_GUID=:guid")
    fun getGroupName(guid: String): String

    @Query("select CollectiveID from tblCollective where Col_GUID=:guid")
    fun getCollectiveID(guid: String): String

    @Query("select MAX(MtgNo)+1 from tblCollectiveMeeting where Col_GUID=:guid")
    fun getMaxmtg(guid: String): Int

    @Query("select MAX(Meeting_date) from tblCollectiveMeeting where Col_GUID=:guid")
    fun getMaxmtgdate(guid: String): Int

    @Query("select Count(*) from tblCollectiveMember where Gender=1 and Category=1 and Col_GUID=:guid")
    fun getMaleWP(guid: String): Int


    @Query("select Count(*) from tblCollectiveMember where Gender=2 and Category=1 and Col_GUID=:guid")
    fun getFeMaleWP(guid: String): Int

    @Query("select Count(*) from tblCollectiveMember where Gender=3 and Category=1 and Col_GUID=:guid")
    fun getTransWP(guid: String): Int


    @Query("select Count(*) from tblCollectiveMember where Gender=1 and Category=2 and Col_GUID=:guid")
    fun getMaleNWP(guid: String): Int


    @Query("select Count(*) from tblCollectiveMember where Gender=2 and Category=2 and Col_GUID=:guid")
    fun getFeMaleNWP(guid: String): Int

    @Query("select Count(*) from tblCollectiveMember where Gender=3 and Category=2 and Col_GUID=:guid")
    fun getTransNWP(guid: String): Int

    @Delete
    fun deletemember(collectiveMemberEntity: CollectiveMeetingEntity)


    @Query("select count(*) from tblCollectiveMeeting where CollMeetGUID=:MemberID")
    fun getMemberID(MemberID: String): Int

    @Query("DELETE from tblCollectiveMeeting where CollMeetGUID=:GUID")
    fun delete_record(GUID: String)

    @Query("Select * from tblCollectiveMeeting where isEdited = 1")
    fun getMemberAllData(): List<CollectiveMeetingEntity>

    @Query("Update tblCollectiveMeeting set IsEdited=0")
    fun updateIsEdited()

    @Query("select Count(*) from tblCollectiveMember where Gender=1 and Category=3 and Col_GUID=:guid")
    fun getMaleHHM(guid: String): Int

    @Query("select Count(*) from tblCollectiveMember where Gender=2 and Category=3 and Col_GUID=:guid")
    fun getFeMaleHHM(guid: String): Int

    @Query("select Count(*) from tblCollectiveMember where Gender=3 and Category=3 and Col_GUID=:guid")
    fun getTransHHM(guid: String): Int

    @Query("Update tblCollectiveMeeting set Remarks=:remarks, Status=:status where CollMeetGUID =:colMeetguid")
    fun updateStatusData(remarks:String,status: Int, colMeetguid: String)
}