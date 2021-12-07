package com.careindia.lifeskills.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity


@Dao
interface CollectiveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectiveData(collectiveEntity: CollectiveEntity?)

    @Query("update tblCollective set DateForm=:date,CollectiveName=:groupName,Panchayat_Ward=:wardname,Localitycode=:localityname,CollectiveID=:collectiveid,ZoneCode=:zonename,DistrictCode=:districcode,PWCode=:panchayatcode where Col_GUID=:guid")
    fun updateCollectiveData(guid:String, date:String,
                             groupName:String,
                             wardname:Int,
                             localityname:String,
                             collectiveid:String,
                             zonename:String,
                             districcode:String,
                             panchayatcode:String)

    @Query("Select * from tblCollective")
    fun getallCollectivedata(): LiveData<List<CollectiveEntity>>

    @Query("Select * from tblCollective where Col_GUID=:Guid")
    fun getCollectivedatabyGuid(Guid:String): LiveData<List<CollectiveEntity>>

    @Delete
    fun deleteCollectivedata(collectiveEntity: CollectiveEntity)

    @Query("update tblCollective set Date_formation=:formationdate,Type=:Type,TypeOther=:TypeOther,Registration=:Registration,RegistrationOther=:RegistrationOther,Objective=:Objective,Head_name=:headgroupname,Head_gender=:headsex,NoMembers=:totalmember,NoMembers_M=:totalmale,NoMembers_F=:totalfemale,NoMembers_T=:totaltrangender where Col_GUID=:guid")
    fun updatecollectivesecond(
        guid: String,
        formationdate: String,  Type: Int,
        TypeOther: String,
        Registration: Int,
        RegistrationOther: String, Objective: String,
        headgroupname: String,
        headsex: Int,
        totalmember: Int,
        totalmale: Int,
        totalfemale: Int,
        totaltrangender: Int
    )

    @Query("update tblCollective set Tenure_President=:tenure,Rolerotation=:Rolerotation,Elections=:electionob,Election_Freq=:electionfreq,IsBank=:bankac,Bank_Challenges=:cbank,Savings=:groupsaving,Savings_Oth=:otherinr,Savings_Freq=:freqsaving,Savings_FreqOth=:othersaving where Col_GUID=:guid")
    fun updateCollectivefour(
        guid: String,
        tenure: String,Rolerotation:Int,
        electionob: Int,
        electionfreq: String,
        bankac: Int,
        groupsaving: Int,
        otherinr: String,
        freqsaving: Int,
        othersaving: String,
        cbank: String
    )

    @Query("update tblCollective set Savings_Regularity=:memberSaving,Savings_RegularityOther=:MemberSavingOther,Loan_Availed=:availloan,Loan_Availed_Where=:AvailloanWhere,Loan_Availed_Others=:AvailloanOther,Loan_Challenges=:loanChallange,Meeting_Held=:meetingconducted,Meeting_Freq=:frequencyMeeting,Meeting_FreqOth=:FrequencyMeetingOther,Meeting_Regularity=:regularityMeeting,Meeting_Schedule=:meetingschedule where Col_GUID=:guid")
    fun updateCollectivefive(
        guid: String,
        memberSaving: Int,MemberSavingOther:String,
        availloan: Int,AvailloanWhere:Int,AvailloanOther:String,
        loanChallange: String,
        meetingconducted: Int,
        frequencyMeeting: Int,FrequencyMeetingOther:String,
        regularityMeeting: Int,meetingschedule:Int
    )

    @Query("update tblCollective set Linkage_Services=:service,Linkages_oth=:Linkages_oth,Collective_opp_Other=:Collective_opp_Other,Services_availed=:serviceAvailing,Services_dept=:serviceProvider,Register_maintained=:recordbook,Register_regular=:recordbookupdate,Linkages=:collectivelinkage,IsService_availed=:serviceschemes,Collective_Schemes=:enterprisebuisness,Collective_opp=:collectiveplanbuisness where Col_GUID=:guid")
    fun updateCollectiveSix(
        guid: String,
        service: String?,
        serviceAvailing: String?,
        serviceProvider: String?,
        recordbook: Int,
        recordbookupdate: Int,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String,Linkages_oth:String,Collective_opp_Other:String
    )

    @Query("Select count(CollectiveID) from tblCollective")
    fun getCommunityCount():Int

    @Query("Select DistrictCode from tblCollective where Col_GUID=:Guid")
    fun getDistCode(Guid:String): String

    @Query("Select PWCode from tblCollective where Col_GUID=:Guid")
    fun getPWCode(Guid: String): String

    @Query("Select Panchayat_Ward from tblCollective where Col_GUID=:Guid")
    fun getPanchayat_Ward(Guid: String): Int
}