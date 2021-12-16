package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.*
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectiveProfile.*
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.activity_collective_profile_sixth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveViewModel(private val collectiveRepository: CollectiveRepository) :
    BaseViewModel() {
    var validate: Validate? = null

    val collectiveData = collectiveRepository.getallCollectivedata()

    val Date = MutableLiveData<String?>()

    var CrpName = MutableLiveData<String?>()
    val SfcName = MutableLiveData<String?>()
    val District = MutableLiveData<Int?>()
    val ZoneName = MutableLiveData<Int>()
    val WardName = MutableLiveData<Int>()
    val PanchayatName = MutableLiveData<Int>()
    val localityName = MutableLiveData<String?>()
    val collectiveId = MutableLiveData<String?>()
    val groupName = MutableLiveData<String?>()
    val Formationdate = MutableLiveData<String?>()
    val Grouptype = MutableLiveData<Int?>()
    val Specifyothergroup = MutableLiveData<String?>()
    val Groupregistered = MutableLiveData<Int?>()
    val Specifygroupregistered = MutableLiveData<String?>()
    val Headgroupname = MutableLiveData<String?>()
    val objective = MutableLiveData<String?>()
    val Headsex = MutableLiveData<Int?>()
    val Totalmember = MutableLiveData<String?>()
    val Totalmale = MutableLiveData<String?>()
    val Totalfemale = MutableLiveData<String?>()
    val Totaltransgender = MutableLiveData<String?>()

    val Tenure = MutableLiveData<String?>()
    val Rolerotation = MutableLiveData<Int?>()
    val Electionfrequency = MutableLiveData<String?>()
    val cBank = MutableLiveData<String?>()
    val Groupsaving = MutableLiveData<Int?>(0)
    val Otherinr = MutableLiveData<String?>()
    val Frequencygroupsaving = MutableLiveData<Int?>(0)
    val Otherfreq = MutableLiveData<String?>()

    val MemberSaving = MutableLiveData<Int?>()
    val Otherfrequency1 = MutableLiveData<String?>()
    val Fromwhereloan = MutableLiveData<Int?>(0)
    val OtherSpecify = MutableLiveData<String?>()
    val LoanChallange = MutableLiveData<String?>()
    val meetingschedule = MutableLiveData<Int?>()
    val FrequencyMeeting = MutableLiveData<Int?>(0)
    val Specifyoth = MutableLiveData<String?>()
    val RegularityMeeting = MutableLiveData<Int?>(0)
    val RecordBook = MutableLiveData<Int?>()
    val ServiceScheme = MutableLiveData<Int?>()
    val Enterprise = MutableLiveData<Int?>()

    val Other1 = MutableLiveData<String?>()
    val Service = MutableLiveData<String?>()
    val ServiceAvailing = MutableLiveData<String?>()
    val ServiceProvider = MutableLiveData<String?>()
    val Other2 = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()

    var recordbookupdate = 0
    var collectivelinkage = ""
    var collectiveplanbuisness = ""

    fun collectDataSix(
        rg_record_book_update: Int,
        chk_options_below: String,
        chk_collective_plan: String,
    ) {
        recordbookupdate = rg_record_book_update
        collectivelinkage = chk_options_below
        collectiveplanbuisness = chk_collective_plan
    }

    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
        CrpName.value = validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name)
        SfcName.value = validate!!.RetriveSharepreferenceString(AppSP.FCID_Name)
    }


    fun saveandUpdateCollectiveProfile(collectiveProfileActivity: CollectiveProfileActivity,initials:String) {
        var Ward1 = 0
        var PWCode = ""
        val District1 = returnDistrictID(District.value, 10)
        val Zone1: Int = collectiveProfileActivity.returnZoneID(
            ZoneName.value,
            District1
        )

        if (Zone1 > 0) {
            Ward1 = collectiveProfileActivity.returnWardID(
                WardName.value,
                Zone1
            )
            PWCode = "W"
        } else {
            Ward1 = collectiveProfileActivity.returnPanchayatID(
                PanchayatName.value,
                District1
            )
            PWCode = "P"
        }


        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID) == "") {
            val collectiveGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, collectiveGuid)

            insert(
                CollectiveEntity(
                    collectiveGuid,
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode,
                    localityName.value,
                    Date.value,
                    collectiveId.value,
                    groupName.value,
                    "",
                    0,
                    "",
                    0,
                    "",
                    "",
                    "",
                    0,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    "",
                    0,
                    -1,
                    0,
                    "",
                    0,
                    "",
                    -1,
                    0,
                    "",
                    "",
                    -1,
                    0,
                    "",
                    0,
                    -1,
                    -1,
                    -1,
                    "",
                    "",
                    "",
                    -1,
                    "",
                    "",
                    -1,
                    "",
                    "",
                    "",
                    0,
                    "",
                    0,
                    0,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),initials,1
                )
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
            update(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                Date.value!!,
                groupName.value!!,
                Ward1!!,
                localityName.value!!,
                collectiveId.value!!,
                Zone1!!,
                District1!!,Ward1,
                PWCode,initials,1
            )
        }
    }

    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            collectiveRepository.getMstDist(StateCode)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode!!

            }
        }
        return id
    }

    fun updatecollectiveprofilesecond(collectiveProfileActivitySec: CollectiveProfileActivitySec) {

        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatecollectivesecond(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            Formationdate.value!!,
            collectiveProfileActivitySec.returnID(Grouptype.value!!, 18, langID),
            Specifyothergroup.value!!,
            collectiveProfileActivitySec.returnID(Groupregistered.value!!, 19, langID),
            Specifygroupregistered.value!!, objective.value!!,
            Headgroupname.value!!,
            collectiveProfileActivitySec.returnID(Headsex.value!!, 1, langID),
            validate!!.returnIntegerValue(Totalmember.value),
            validate!!.returnIntegerValue(Totalmale.value),
            validate!!.returnIntegerValue(Totalfemale.value),
            validate!!.returnIntegerValue(Totaltransgender.value),1
        )
    }


    fun updatecollectiveprofilefour(collectiveProfileActivityFourth: CollectiveProfileActivityFourth) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatecollectivefour(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            Tenure.value!!,
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFourth.rg_rotation_of_roles),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFourth.rg_office_bearer),
            Electionfrequency.value!!,
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFourth.rg_bank_account),
            collectiveProfileActivityFourth.returnID(Groupsaving.value!!, 20, langID),
            Otherinr.value!!,
            collectiveProfileActivityFourth.returnID(Frequencygroupsaving.value!!, 21, langID),
            Otherfreq.value!!,
            cBank.value!!,1
        )


    }

    fun updatecollectiveprofilefive(collectiveProfileActivityFifth: CollectiveProfileActivityFifth) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatecollectivefive(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            collectiveProfileActivityFifth.returnID(MemberSaving.value!!, 22, langID),
            Otherfrequency1.value!!,
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_easily_avial_loan),
            collectiveProfileActivityFifth.returnID(Fromwhereloan.value!!, 23, langID),
            OtherSpecify.value!!,
            LoanChallange.value!!,
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_meeting_conducted),
            collectiveProfileActivityFifth.returnID(FrequencyMeeting.value!!, 24, langID),
            Specifyoth.value!!,
            collectiveProfileActivityFifth.returnID(RegularityMeeting.value!!, 11, langID),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_meeting_schedule),
            1
        )
    }

    fun updatecollectiveprofileSix(collectiveProfileActivitySixth: CollectiveProfileActivitySixth) {
        updatecollectivesix(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            Service.value,
            ServiceAvailing.value,
            ServiceProvider.value,
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivitySixth.rg_record_book),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivitySixth.rg_record_book_update),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivitySixth.rg_services_schemes),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivitySixth.rg_enterprise_business),
            collectivelinkage,
            collectiveplanbuisness, Other1.value!!, Other2.value!!,1
        )
    }

    private fun updatecollectivesix(
        guid: String,
        Service: String?,
        ServiceAvailing: String?,
        ServiceProvider: String?,
        recordbook: Int,
        recordbookupdate: Int,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String, Linkages_oth: String, Collective_opp_Other: String,IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivesix(
                guid,
                Service,
                ServiceAvailing,
                ServiceProvider,
                recordbook,
                recordbookupdate,
                serviceschemes,
                enterprisebuisness,
                collectivelinkage,
                collectiveplanbuisness, Linkages_oth, Collective_opp_Other,IsEdited
            )
        }
    }


    fun insert(collectiveEntity: CollectiveEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.insert(collectiveEntity)
        }
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
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.update(
                guid, date, groupName, wardname, localityname,
                collectiveid, zonename, districcode, panchayatcode,pw_code,initials,IsEdited
            )
        }
    }

    fun updatecollectivesecond(
        guid: String,
        formationdate: String,
        Type: Int,
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
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivesecond(
                guid,
                formationdate, Type,
                TypeOther,
                Registration,
                RegistrationOther, Objective,
                headgroupname,
                headsex,
                totalmember,
                totalmale,
                totalfemale,
                totaltrangender,
                IsEdited
            )
        }
    }

    fun updatecollectivefour(
        guid: String,
        tenure: String, Rolerotation: Int,
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
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivefourth(
                guid,
                tenure, Rolerotation,
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
    }

    fun updatecollectivefive(
        guid: String,
        MemberSaving: Int, MemberSavingOther: String,
        Availloan: Int, AvailloanWhere: Int, AvailloanOther: String,
        LoanChallange: String,
        Meetingconducted: Int,
        FrequencyMeeting: Int, FrequencyMeetingOther: String,
        RegularityMeeting: Int, meetingschedule: Int,IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivefive(
                guid,
                MemberSaving, MemberSavingOther,
                Availloan, AvailloanWhere, AvailloanOther,
                LoanChallange,
                Meetingconducted,
                FrequencyMeeting, FrequencyMeetingOther,
                RegularityMeeting, meetingschedule,IsEdited
            )
        }
    }


    fun getCollectivedatabyGuid(guid: String): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCollectivedatabyGuid(guid)
    }

    fun getCommWData(izone: Int, iward: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommWData(izone, iward)
    }

    fun getCommZData(izone: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommZData(izone)
    }

    fun getCommPData(iPanchayat: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommPData(iPanchayat)
    }

    fun deletecollective(collectiveEntity: CollectiveEntity) {
        viewModelScope.launch {
            collectiveRepository.delete(collectiveEntity)
        }
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return collectiveRepository.getMstDist(StateCode)
    }

    fun getCommunityCount(): Int {
        return collectiveRepository!!.getCommunityCount()
    }

    fun getCommunityID(CollectiveID: String): Int {
        return collectiveRepository!!.getCommunityID(CollectiveID)
    }

}