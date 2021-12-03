package com.careindia.lifeskills.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileActivitySec
import java.text.SimpleDateFormat
import java.util.*

class CollectiveViewModel(private val collectiveRepository: CollectiveRepository): BaseViewModel() {
    var validate: Validate? = null

    val collectiveData = collectiveRepository.getallCollectivedata()

    val Date = MutableLiveData<String?>()
   // var CrpName = MutableLiveData<Int?>()
    val SfcName = MutableLiveData<Int?>()
    val District = MutableLiveData<Int?>()
    val ZoneName = MutableLiveData<Int?>()
    val WardName = MutableLiveData<Int?>()
    val PanchayatName = MutableLiveData<Int?>()
    val localityName = MutableLiveData<String?>()
    val collectiveId = MutableLiveData<String?>()
    val groupName = MutableLiveData<String?>()
    val Formationdate = MutableLiveData<String?>()
    val Grouptype = MutableLiveData<Int?>()
    val Specifyothergroup = MutableLiveData<String?>()
    val Groupregistered = MutableLiveData<Int?>()
    val Specifygroupregistered = MutableLiveData<String?>()
    val Headgroupname = MutableLiveData<String?>()
    val Headsex = MutableLiveData<Int?>()
    val Totalmember = MutableLiveData<String?>()
    val Totalmale = MutableLiveData<String?>()
    val Totalfemale = MutableLiveData<String?>()
    val Totaltransgender = MutableLiveData<String?>()

    val Tenure = MutableLiveData<String?>()
    val Rolerotation = MutableLiveData<Int?>()
    val Electionob = MutableLiveData<Int?>()
    val Electionfrequency = MutableLiveData<String?>()
    val Bankac = MutableLiveData<Int?>()
    val cBank = MutableLiveData<String?>()
    val Groupsaving = MutableLiveData<Int?>()
    val Otherinr = MutableLiveData<String?>()
    val Frequencygroupsaving = MutableLiveData<Int?>()
    val Otherfreq = MutableLiveData<String?>()

    val MemberSaving = MutableLiveData<Int?>()
    val Otherfrequency1 = MutableLiveData<String?>()
     val Availloan = MutableLiveData<Int?>()
    val Fromwhereloan = MutableLiveData<Int?>()
    val OtherSpecify = MutableLiveData<String?>()
    val LoanChallange = MutableLiveData<String?>()
    val Meetingconducted = MutableLiveData<Int?>()
    val FrequencyMeeting = MutableLiveData<Int?>()
    val Specifyoth = MutableLiveData<String?>()
    val RegularityMeeting = MutableLiveData<Int?>()

    val Other1 = MutableLiveData<String?>()
    val Service = MutableLiveData<String?>()
    val ServiceAvailing = MutableLiveData<String?>()
    val ServiceProvider = MutableLiveData<String?>()
    val Other2 = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()

    var meetingschedule = 0
    var recordbook = 0
    var recordbookupdate = 0
    var serviceschemes = 0
    var enterprisebuisness = 0
    var collectivelinkage = ""
    var collectiveplanbuisness = ""

    fun collectDataSix(
        rg_meeting_schedule: Int,
        rg_record_book: Int,
        rg_record_book_update: Int,
        rg_services_schemes: Int,
        rg_enterprise_business: Int,
        chk_options_below:String,
        chk_collective_plan:String,
    ){
        meetingschedule = rg_meeting_schedule
        recordbook = rg_record_book
        recordbookupdate = rg_record_book_update
        serviceschemes = rg_services_schemes
        enterprisebuisness = rg_enterprise_business
        collectivelinkage = chk_options_below
        collectiveplanbuisness = chk_collective_plan
    }

    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
    }



    fun saveandUpdateCollectiveProfile() {
        val date: String? = Date.value
       // val crpname: String? = returnID(CrpName.value, 1).toString()
        val sfcname: String? = returnID(SfcName.value, 2).toString()
        val districcode: String? = returnID(District.value, 3).toString()
        val zonename: String? = returnID(ZoneName.value, 4).toString()
        val wardname: Int? = returnID(WardName.value, 5)
        val panchayatcode: String? = returnID(PanchayatName.value, 6).toString()
        val localityname: String? = localityName.value
        val collectiveid: String? = collectiveId.value
        val groupname: String? = groupName.value
        val formationdate : String? = Formationdate.value
        val grouptype : Int? = returnID(Grouptype.value,7)
        val specifyothergroup :String? = Specifyothergroup.value
        val headgroupname : String? = Headgroupname.value
        val headsex : Int? = returnID(Headsex.value,8)
        val totalmember: Int? = validate!!.returnIntegerValue(Totalmember.value)
        val totalmale : Int? = validate!!.returnIntegerValue(Totalmale.value)
        val totalfemale : Int? = validate!!.returnIntegerValue(Totalfemale.value)
        val totaltrangender : Int? = validate!!.returnIntegerValue(Totaltransgender.value)

        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)=="") {
            val collectiveGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID,collectiveGuid)

            insert(
                CollectiveEntity(
                    0,
                    collectiveGuid,
                    "",
                    districcode,
                    zonename,
                    wardname,
                    panchayatcode,
                    localityname,
                    date,
                    collectiveid,
                    groupname,
                    formationdate, 0, 0, "", "",
                    headgroupname,
                    headsex,
                    totalmember,
                    totalmale,
                    totalfemale,
                    totaltrangender, 0, 0, 0, 0, "", 0, 0, 0, "", 0, 0, "", 0, 0, 0,
                    0, 0, 0, 0, "", "", "", 0, "", "", 0, "", "", 0, "", 0, 0
                )
            )
        }else if(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length>0){
                    update(
                        validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                        date!!,
                        groupname!!,
                        wardname!!,
                        localityname!!,
                        collectiveid!!,
                        zonename!!,
                        districcode!!,
                        panchayatcode!!,
                    )
        }
    }

    fun updatecollectiveprofilesecond(){
        val formationdate : String? = Formationdate.value
        val grouptype : Int? = returnID(Grouptype.value,7)
        val specifyothergroup :String? = Specifyothergroup.value
        val headgroupname : String? = Headgroupname.value
        val headsex : Int? = returnID(Headsex.value,8)
        val totalmember: Int? = validate!!.returnIntegerValue(Totalmember.value)
        val totalmale : Int? = validate!!.returnIntegerValue(Totalmale.value)
        val totalfemale : Int? = validate!!.returnIntegerValue(Totalfemale.value)
        val totaltrangender : Int? = validate!!.returnIntegerValue(Totaltransgender.value)

        updatecollectivesecond(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            formationdate!!,
            headgroupname!!,
            headsex!!,
            totalmember!!,
            totalmale!!,
            totalfemale!!,
            totaltrangender!!,
        )
    }


    fun updatecollectiveprofilefour(){
        updatecollectivefour(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            Tenure.value!!,
            returnID(Electionob.value,13),
            Electionfrequency.value!!,
            returnID(Bankac.value,14),
            returnID(Groupsaving.value,15),
            Otherinr.value!!,
            returnID(Frequencygroupsaving.value,16),
            Otherfreq.value!!,
            cBank.value!!
        )
    }

    fun updatecollectiveprofilefive(){
        updatecollectivefive(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            returnID(MemberSaving.value,17),
            returnID(Availloan.value,19),
            LoanChallange.value!!,
            returnID(Meetingconducted.value,21),
            returnID(FrequencyMeeting.value,22),
            returnID(RegularityMeeting.value,23)
        )
    }

    fun updatecollectiveprofileSix(){
        updatecollectivesix(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            Service.value,
            ServiceAvailing.value,
            ServiceProvider.value,
            meetingschedule,
                    recordbook,
                    recordbookupdate,
                    serviceschemes,
                    enterprisebuisness,
                    collectivelinkage,
                    collectiveplanbuisness
        )
    }

    private fun updatecollectivesix(
        guid: String,
        Service: String?,
        ServiceAvailing: String?,
        ServiceProvider: String?,
        meetingschedule: Int,
        recordbook: Int,
        recordbookupdate: Int,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivesix(
                guid,
                Service,
            ServiceAvailing,
            ServiceProvider,
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


    fun insert(collectiveEntity: CollectiveEntity){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.insert(collectiveEntity)
        }
    }

    fun update(guid:String,
               date:String,
               groupName:String,
               wardname:Int,
               localityname:String,
               collectiveid:String,
               zonename:String,
               districcode:String,
               panchayatcode:String){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.update(
                guid,
                date,
                groupName,
                wardname,
                localityname,
                collectiveid,
                zonename,
                districcode,
                panchayatcode
            )
        }
    }

    fun updatecollectivesecond(guid:String,
                               formationdate:String,
                               headgroupname:String,
                               headsex:Int,
                               totalmember:Int,
                               totalmale:Int,
                               totalfemale:Int,
                               totaltrangender:Int){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivesecond(
                guid,
                formationdate,
                headgroupname,
                headsex,
                totalmember,
                totalmale,
                totalfemale,
                totaltrangender
            )
        }
    }

    fun updatecollectivefour(guid:String,
                               tenure:String,
                               electionob:Int,
                               electionfreq:String,
                               bankac:Int,
                               groupsaving:Int,
                               otherinr:String,
                               freqsaving:Int,
                               othersaving:String,
                               cbank:String){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivefourth(
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
    }

    fun updatecollectivefive(guid:String,
                             MemberSaving:Int,
                             Availloan:Int,
                             LoanChallange:String,
                             Meetingconducted:Int,
                             FrequencyMeeting:Int,
                             RegularityMeeting:Int){
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivefive(
                guid,
                MemberSaving,
                Availloan,
                LoanChallange,
                Meetingconducted,
                FrequencyMeeting,
                RegularityMeeting
            )
        }
    }


    fun returnID(pos: Int?, flag:Int): Int {
        var data: List<MstCommonEntity>? = null
        data =
            collectiveRepository.getmstCommonData(flag)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).id!!
            }
        }
        return id
    }

    fun getCollectivedatabyGuid(guid:String):LiveData<List<CollectiveEntity>>{
        return collectiveRepository.getCollectivedatabyGuid(guid)
    }

    fun deletecollective(collectiveEntity: CollectiveEntity) {
        viewModelScope.launch {
            collectiveRepository.delete(collectiveEntity)
        }
    }

}