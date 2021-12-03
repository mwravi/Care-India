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

    @Query("update tblCollective set Date_formation=:formationdate,Head_name=:headgroupname,Head_gender=:headsex,NoMembers=:totalmember,NoMembers_M=:totalmale,NoMembers_F=:totalfemale,NoMembers_T=:totaltrangender where Col_GUID=:guid")
    fun updatecollectivesecond(
        guid: String,
        formationdate: String,
        headgroupname: String,
        headsex: Int,
        totalmember: Int,
        totalmale: Int,
        totalfemale: Int,
        totaltrangender: Int
    )

    @Query("update tblCollective set Tenure_President=:tenure,Elections=:electionob,Election_Freq=:electionfreq,IsBank=:bankac,Bank_Challenges=:cbank,Savings=:groupsaving,Savings_Oth=:otherinr,Savings_Freq=:freqsaving,Savings_FreqOth=:othersaving where Col_GUID=:guid")
    fun updateCollectivefour(
        guid: String,
        tenure: String,
        electionob: Int,
        electionfreq: String,
        bankac: Int,
        groupsaving: Int,
        otherinr: String,
        freqsaving: Int,
        othersaving: String,
        cbank: String
    )

    @Query("update tblCollective set Savings_Regularity=:memberSaving,Loan_Availed=:availloan,Loan_Challenges=:loanChallange,Meeting_Held=:meetingconducted,Meeting_Freq=:frequencyMeeting,Meeting_Regularity=:regularityMeeting where Col_GUID=:guid")
    fun updateCollectivefive(
        guid: String,
        memberSaving: Int,
        availloan: Int,
        loanChallange: String,
        meetingconducted: Int,
        frequencyMeeting: Int,
        regularityMeeting: Int
    )

    @Query("update tblCollective set Linkage_Services=:service,Services_availed=:serviceAvailing,Services_dept=:serviceProvider,Meeting_Schedule=:meetingschedule,Register_maintained=:recordbook,Register_regular=:recordbookupdate,Linkages=:collectivelinkage,IsService_availed=:serviceschemes,Collective_Schemes=:enterprisebuisness,Collective_opp=:collectiveplanbuisness where Col_GUID=:guid")
    fun updateCollectiveSix(
        guid: String,
        service: String?,
        serviceAvailing: String?,
        serviceProvider: String?,
        meetingschedule: Int,
        recordbook: Int,
        recordbookupdate: Int,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String
    )
}