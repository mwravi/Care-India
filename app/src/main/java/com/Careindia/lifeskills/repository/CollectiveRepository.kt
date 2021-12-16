package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveDao
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstDistrictEntity


class CollectiveRepository(
    private val collectiveDao: CollectiveDao,
    private val mstDistrictDao: MstDistrictDao
) {

    fun insert(collectiveEntity: CollectiveEntity) {
        collectiveDao.insertCollectiveData(collectiveEntity)
    }

    fun update(
        guid: String, date: String,
        groupName: String,
        wardname: Int,
        localityname: String,
        collectiveid: String,
        zonename: Int,
        districcode: Int,
        panchayatcode: Int,pw_code:String,initials:String,IsEdited:Int
    ) {
        collectiveDao.updateCollectiveData(
            guid, date, groupName, wardname, localityname,
            collectiveid, zonename, districcode, panchayatcode,pw_code,initials,IsEdited
        )
    }


    fun getallCollectivedata(): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getallCollectivedata()
    }

    fun getCollectivedatabyGuid(guid: String): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCollectivedatabyGuid(guid)
    }
    fun getCommWData(izone: Int, iward: Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommWData(izone, iward)
    }

    fun getCommZData(izone: Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommZData(izone)
    }

    fun getCommPData(iPanchayat: Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommPData(iPanchayat)
    }
    fun delete(collectiveEntity: CollectiveEntity) {
        return collectiveDao.deleteCollectivedata(collectiveEntity)
    }

    fun updatecollectivesecond(
        guid: String,
        formationdate: String, Type: Int,
        TypeOther: String,
        Registration: Int,
        RegistrationOther: String, Objective: String,
        headgroupname: String,
        headsex: Int,
        totalmember: Int,
        totalmale: Int,
        totalfemale: Int,
        totaltrangender: Int,
        IsEdited:Int
    ) {
        collectiveDao.updatecollectivesecond(
            guid, formationdate, Type,
            TypeOther,
            Registration,
            RegistrationOther, Objective, headgroupname, headsex, totalmember,
            totalmale, totalfemale, totaltrangender,IsEdited
        )
    }

    fun updatecollectivefourth(
        guid: String,
        tenure: String,Rolerotation:Int,
        electionob: Int,
        electionfreq: String,
        bankac: Int,
        groupsaving: Int,
        otherinr: String,
        freqsaving: Int,
        othersaving: String,
        cbank: String,
        IsEdited:Int
    ) {
        collectiveDao.updateCollectivefour(
            guid,
            tenure,Rolerotation,
            electionob,
            electionfreq,
            bankac,
            groupsaving,
            otherinr,
            freqsaving,
            othersaving,
            cbank,
            IsEdited
        )
    }

    fun updatecollectivefive(
        guid: String,
        memberSaving: Int,MemberSavingOther:String,
        availloan: Int,AvailloanWhere:Int,AvailloanOther:String,
        loanChallange: String,
        meetingconducted: Int,
        frequencyMeeting: Int,FrequencyMeetingOther:String,
        regularityMeeting: Int,meetingschedule:Int,IsEdited:Int
    ) {
        collectiveDao.updateCollectivefive(
            guid,
            memberSaving,MemberSavingOther,
            availloan,AvailloanWhere,AvailloanOther,
            loanChallange,
            meetingconducted,
            frequencyMeeting,FrequencyMeetingOther,
            regularityMeeting,meetingschedule,IsEdited
        )
    }

    fun updatecollectivesix(
        guid: String,
        service: String?,
        serviceAvailing: String?,
        serviceProvider: String?,
        recordbook: Int,
        recordbookupdate: Int,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String,Linkages_oth:String,Collective_opp_Other:String,IsEdited:Int
    ) {
        collectiveDao.updateCollectiveSix(
            guid,
            service,
            serviceAvailing,
            serviceProvider,
            recordbook,
            recordbookupdate,
            serviceschemes,
            enterprisebuisness,
            collectivelinkage,
            collectiveplanbuisness,Linkages_oth,Collective_opp_Other,IsEdited
        )
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode)
    }

    fun getCommunityCount():Int{
        return collectiveDao!!.getCommunityCount()
    }
    fun getCommunityID(CollectiveID:String):Int{
        return collectiveDao!!.getCommunityID(CollectiveID)
    }
}