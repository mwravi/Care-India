package com.careindia.lifeskills.repository


import androidx.lifecycle.LiveData
import com.careindia.lifeskills.dao.CollectiveDao
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstCommonEntity


class CollectiveRepository(private val collectiveDao: CollectiveDao,private val mstCommonDao: MstCommonDao) {

    fun insert(collectiveEntity: CollectiveEntity){
        collectiveDao.insertCollectiveData(collectiveEntity)
    }

     fun update(guid:String,date:String,
                groupName:String,
                wardname:Int,
                localityname:String,
                collectiveid:String,
                zonename:String,
                districcode:String,
                panchayatcode:String){
        collectiveDao.updateCollectiveData(guid,date,groupName,wardname,localityname,
            collectiveid,zonename,districcode,panchayatcode)
    }

    fun getmstCommonData(flag:Int):List<MstCommonEntity> {
        return mstCommonDao.getMstCommon(flag)
    }

    fun getallCollectivedata():LiveData<List<CollectiveEntity>>{
        return collectiveDao.getallCollectivedata()
    }

    fun getCollectivedatabyGuid(guid:String):LiveData<List<CollectiveEntity>>{
        return collectiveDao.getCollectivedatabyGuid(guid)
    }

     fun delete(collectiveEntity: CollectiveEntity) {
        return collectiveDao.deleteCollectivedata(collectiveEntity)
    }

    fun updatecollectivesecond(
        guid: String,
        formationdate: String,
        headgroupname: String,
        headsex: Int,
        totalmember: Int,
        totalmale: Int,
        totalfemale: Int,
        totaltrangender: Int
    ) {
        collectiveDao.updatecollectivesecond(guid,formationdate,headgroupname,headsex,totalmember,
            totalmale,totalfemale,totaltrangender)
    }

    fun updatecollectivefourth(
        guid: String,
        tenure: String,
        electionob: Int,
        electionfreq: String,
        bankac: Int,
        groupsaving: Int,
        otherinr: String,
        freqsaving: Int,
        othersaving: String,
        cbank: String,
    ) {
      collectiveDao.updateCollectivefour(
          guid,
          tenure,
          electionob,
          electionfreq,
          bankac,
          groupsaving,
          otherinr,
          freqsaving,
          othersaving,
          cbank
      )
    }

    fun updatecollectivefive(
        guid: String,
        memberSaving: Int,
        availloan: Int,
        loanChallange: String,
        meetingconducted: Int,
        frequencyMeeting: Int,
        regularityMeeting: Int
    ) {
    collectiveDao.updateCollectivefive(
            guid,
        memberSaving,
        availloan,
        loanChallange,
        meetingconducted,
        frequencyMeeting,
        regularityMeeting
            )
    }

    fun updatecollectivesix(
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
    ) {
collectiveDao.updateCollectiveSix(
    guid,
    service,
    serviceAvailing,
    serviceProvider,
    meetingschedule,
    recordbook,
    recordbookupdate,
    serviceschemes,
    enterprisebuisness,
    collectivelinkage,
    collectiveplanbuisness
)
    }

}