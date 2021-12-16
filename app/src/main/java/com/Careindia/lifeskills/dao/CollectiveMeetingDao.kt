package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveMeetingEntity

@Dao
interface CollectiveMeetingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(collectiveMeetingEntity: CollectiveMeetingEntity)

    @Query("DELETE from tblCollectiveMeeting")
    fun deleteAllData()

    @Query("Select * from tblCollectiveMeeting where Col_GUID=:Col_GUID")
    fun getAllMemberData(Col_GUID:String): LiveData<List<CollectiveMeetingEntity>>

    @Query("Select * from tblCollectiveMeeting")
    fun getAllMemberData(): LiveData<List<CollectiveMeetingEntity>>

    @Query("update tblCollectiveMeeting set Dateform=:Dateform, Col_GUID=:Col_GUID, Meeting_date=:Meeting_date, Meeting_place=:Meeting_place, Meet_start_time=:Meet_start_time, Meet_end_time=:Meet_end_time, Meet_purpose=:Meet_purpose, Meet_purpose_oth=:Meet_purpose_oth,IsEdited=:IsEdited where CollMeetGUID=:CollMeetGUID")
    fun updatecollectiveMember(
        CollMeetGUID: String,
        Dateform: String,
        Col_GUID: String,
        Meeting_date: String,
        Meeting_place: String,
        Meet_start_time: String,
        Meet_end_time: String,
        Meet_purpose: String,
        Meet_purpose_oth: String,
        IsEdited:Int
    )

    @Query("update tblCollectiveMeeting set Member_male_WP=:Member_male_WP, Member_male_NWP=:Member_male_NWP, Member_female_WP=:Member_female_WP, Member_female_NWP=:Member_female_NWP, Member_Transgender_WP=:Member_Transgender_WP, Member_Transgender_NWP=:Member_Transgender_NWP,Attn_male_WP=:Attn_male_WP, Attn_male_NWP=:Attn_male_NWP, Attn_female_WP=:Attn_female_WP, Attn_female_NWP=:Attn_female_NWP, Attn_Transgender_WP=:Attn_Transgender_WP, Attn_Transgender_NWP=:Attn_Transgender_NWP,IsEdited=:IsEdited where CollMeetGUID=:CollMeetGUID")
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
        IsEdited:Int
    )


    @Query("update tblCollectiveMeeting set Savings=:Savings, InternalLending=:InternalLending, BankLoans=:BankLoans, Schemes_and_services=:Schemes_and_services, EntrepreneurialActivities=:EntrepreneurialActivities,TrainingActivities=:TrainingActivities, Change_position=:Change_position,ChangeGrpMember=:ChangeGrpMember, OtherPoint=:OtherPoint, TotalSavings=:TotalSavings, LoanAvailed_int=:LoanAvailed_int, LoanAvailed_ext=:LoanAvailed_ext,Interest_acc=:Interest_acc,IsEdited=:IsEdited where CollMeetGUID=:CollMeetGUID")
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
        TotalSavings: Int?,
        LoanAvailed_int: Int?,
        LoanAvailed_ext: Int?,
        Interest_acc: Int?,
        IsEdited:Int
    )

    @Query("select * from tblCollectiveMeeting where CollMeetGUID=:guid")
    fun getCollectiveMemberdatabyGuid(guid: String): LiveData<List<CollectiveMeetingEntity>>

    @Delete
    fun deletemember(collectiveMemberEntity: CollectiveMeetingEntity)


    @Query("select count(*) from tblCollectiveMeeting where CollMeetGUID=:MemberID")
    fun getMemberID(MemberID:String):Int

    @Query("DELETE from tblCollectiveMeeting where CollMeetGUID=:GUID")
    fun delete_record(GUID: String)

    @Query("Select * from tblCollectiveMeeting")
    fun getMemberAllData(): List<CollectiveMeetingEntity>
}