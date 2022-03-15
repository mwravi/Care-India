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
        guid: String, date: Long?,
        groupName: String,
        wardname: Int,
        localityname: String,
        collectiveid: String,
        zonename: Int,
        districcode: Int,
        panchayatcode: Int,pw_code:String,initials:String,IsEdited:Int,
        LandMark: String?,
        PinCode: String?,
        updatedBy:Int?,
        updated_on:Long?
    ) {
        collectiveDao.updateCollectiveData(
            guid, date, groupName, wardname, localityname,
            collectiveid, zonename, districcode, panchayatcode,pw_code,initials,IsEdited,
            LandMark,
            PinCode,
            updatedBy,
            updated_on
        )
    }


    fun getallCollectivedata(): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getallCollectivedata()
    }

    fun getCollectivedatabyGuid(guid: String): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCollectivedatabyGuid(guid)
    }

    fun getColldatabyGuid(guid: String): List<CollectiveEntity> {
        return collectiveDao.getColldatabyGuid(guid)
    }

    fun getCommWData(iDisCode:Int,izone: Int, iward: Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommWData(iDisCode,izone, iward)
    }

  fun getcollectiveData(iDisCode:Int,izone: Int, iward: Int): List<CollectiveEntity> {
        return collectiveDao.getcollectiveData(iDisCode,izone, iward)
    }

    fun getCommWData(iDisCode:Int,izone: Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommWData(iDisCode,izone)
    }

    fun getCommWData(iDisCode:Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommWData(iDisCode)
    }



    fun getCommPData(iDisCode:Int,iPanchayat: Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommPData(iDisCode,iPanchayat)
    }

    fun getCommPData(iDisCode:Int): LiveData<List<CollectiveEntity>> {
        return collectiveDao.getCommPData(iDisCode)
    }
    fun delete(collectiveEntity: CollectiveEntity) {
        return collectiveDao.deleteCollectivedata(collectiveEntity)
    }

    fun updatecollectivesecond(
        guid: String,
        formationdate: Long?, Type: Int,
        TypeOther: String,
        Registration: Int,
        RegistrationOther: String, Objective: String,
        headgroupname: String,
        headsex: Int,
        totalmember: Int,
        totalmale: Int,
        totalfemale: Int,
        totaltrangender: Int,
        updatedBy:Int?,
        updated_on: Long?,
        IsEdited:Int
    ) {
        collectiveDao.updatecollectivesecond(
            guid, formationdate, Type,
            TypeOther,
            Registration,
            RegistrationOther, Objective, headgroupname, headsex, totalmember,
            totalmale, totalfemale, totaltrangender,
            updatedBy,
            updated_on,
            IsEdited
        )
    }

    fun updatecollectivefourth(
        guid: String,
        tenure: String,Rolerotation:Int,
        electionob: Int,
        electionfreq: String,
        updatedBy:Int?,
        updated_on: Long?,
        IsEdited:Int
    ) {
        collectiveDao.updateCollectivefour(
            guid,
            tenure,Rolerotation,
            electionob,
            electionfreq,
            updatedBy,
            updated_on,
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
        regularityMeeting: Int,meetingschedule:Int,
        bankac: Int,
        groupsaving: Int,
        otherinr: String,
        freqsaving: Int,
        othersaving: String,
        cbank: String,
        recordbook: Int,
        recordbookupdate: Int,
        updatedBy:Int?,
        updated_on: Long?,
        IsEdited:Int
    ) {
        collectiveDao.updateCollectivefive(
            guid,
            memberSaving,MemberSavingOther,
            availloan,AvailloanWhere,AvailloanOther,
            loanChallange,
            meetingconducted,
            frequencyMeeting,FrequencyMeetingOther,
            regularityMeeting,meetingschedule,
            bankac,
            groupsaving,
            otherinr,
            freqsaving,
            othersaving,
            cbank,
            recordbook,
            recordbookupdate,
            updatedBy,
            updated_on,
            IsEdited
        )
    }

    fun updatecollectivesix(
        guid: String,
        service: String?,
        serviceAvailing: String?,
        serviceProvider: String?,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String,Linkages_oth:String,Collective_opp_Other:String,
        updatedBy:Int?,
        updated_on: Long?,
        IsEdited:Int
    ) {
        collectiveDao.updateCollectiveSix(
            guid,
            service,
            serviceAvailing,
            serviceProvider,
            serviceschemes,
            enterprisebuisness,
            collectivelinkage,
            collectiveplanbuisness,Linkages_oth,Collective_opp_Other,
            updatedBy,
            updated_on,
            IsEdited
        )
    }

    fun getMstDist(StateCode: Int,DistrictIn: List<String>): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode,DistrictIn)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return mstDistrictDao.getMstDist(StateCode)
    }

    fun getCommunityCount():Int{
        return collectiveDao!!.getCommunityCount()
    }
    fun getCommunityID(CollectiveID:String):Int{
        return collectiveDao!!.getCommunityID(CollectiveID)
    }

    fun getCollectiveDataByCollectiveGuid(collectiveGuid: String): List<CollectiveEntity> {
        return collectiveDao!!.getCollectiveDataByCollectiveGuid(collectiveGuid)
    }

}