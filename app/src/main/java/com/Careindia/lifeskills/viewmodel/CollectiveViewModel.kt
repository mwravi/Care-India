package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.collectiveProfile.*
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.activity_collective_profile_sixth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectiveViewModel(private val collectiveRepository: CollectiveRepository) :
    BaseViewModel() {
    var validate: Validate? = null

    val collectiveData = collectiveRepository.getallCollectivedata()


    var CrpName = MutableLiveData<String?>()
    val SfcName = MutableLiveData<String?>()
    val District = MutableLiveData<Int?>()
    val ZoneName = MutableLiveData<Int>()
    val WardName = MutableLiveData<Int>()
    val PanchayatName = MutableLiveData<Int>()
    val localityName = MutableLiveData<String?>()
    val collectiveId = MutableLiveData<String?>()
    val groupName = MutableLiveData<String?>()
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

    //    val LoanChallange = MutableLiveData<String?>()
    val meetingschedule = MutableLiveData<Int?>()
    val FrequencyMeeting = MutableLiveData<Int?>(0)
    val Specifyoth = MutableLiveData<String?>()
    val RegularityMeeting = MutableLiveData<Int?>(0)
    val RecordBook = MutableLiveData<Int?>()
    val ServiceScheme = MutableLiveData<Int?>()
    val Enterprise = MutableLiveData<Int?>()

    val Other1 = MutableLiveData<String?>()
    val Other2 = MutableLiveData<String?>()

    val saveandnextText = MutableLiveData<String>()


    var collectivelinkage = ""
    var collectiveplanbuisness = ""

    fun collectDataSix(
        chk_options_below: String,
        chk_collective_plan: String,
    ) {

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
        val District1 =
            returnDistrictID(collectiveProfileActivity.spin_district_name.selectedItemPosition, 10)
        val Zone1: Int = collectiveProfileActivity.returnZoneID(
            collectiveProfileActivity.spin_zone_name.selectedItemPosition,
            District1
        )

        if (Zone1 > 0) {
            Ward1 = collectiveProfileActivity.returnWardID(
                collectiveProfileActivity.spin_ward_name.selectedItemPosition,
                Zone1
            )
            PWCode = "W"
        } else {
            Ward1 = collectiveProfileActivity.returnPanchayatID(
                collectiveProfileActivity.spin_panchayat_name.selectedItemPosition,
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
                    collectiveProfileActivity.et_landmark.text.toString(),
                    collectiveProfileActivity.et_pincode.text.toString(),
                    validate!!.getDaysfromdates(collectiveProfileActivity.et_date_of_filling.text.toString(), 1),
//                    collectiveId.value,
                    collectiveProfileActivity.et_collective_id.text.toString(),
                    groupName.value,
                    0,
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
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    0,
                    0,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID), initials, "", 1
                )
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
            update(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                validate!!.getDaysfromdates(collectiveProfileActivity.et_date_of_filling.text.toString(), 1),
                groupName.value!!,
                Ward1!!,
                localityName.value!!,
//                collectiveId.value!!,
                collectiveProfileActivity.et_collective_id.text.toString(),
                Zone1!!,
                District1, Ward1,
                PWCode, initials, 1,
                collectiveProfileActivity.et_landmark.text.toString(),
                collectiveProfileActivity.et_pincode.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2)

            )
        }
    }

    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.DistrictIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }


        var data: List<MstDistrictEntity>? = null
        data = if (!list.isNullOrEmpty()) {
            list.let {
                collectiveRepository.getMstDist(
                    StateCode,
                    list
                )
            }
        } else {
            collectiveRepository.getMstDist(StateCode)
        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode!!

            }
        }
        return id
    }

    fun updatecollectiveprofilesecond(
        collectiveProfileActivitySec: CollectiveProfileActivitySec,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {

        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatecollectivesecond(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            validate!!.getDaysfromdates(collectiveProfileActivitySec.et_date_of_group_formation.text.toString(),1),
            collectiveProfileActivitySec.returnID(Grouptype.value!!, 18, langID),
            Specifyothergroup.value!!,
            collectiveProfileActivitySec.returnID(Groupregistered.value!!, 19, langID),
            Specifygroupregistered.value!!, validate!!.getschemes(
                collectiveProfileActivitySec.et_objective1,
                collectiveProfileActivitySec.et_objective2,
                collectiveProfileActivitySec.et_objective3,
                collectiveProfileActivitySec.et_objective4,
                collectiveProfileActivitySec.et_objective5
            ),
            Headgroupname.value!!,
            collectiveProfileActivitySec.returnID(Headsex.value!!, 1, langID),
            validate!!.returnIntegerValue(Totalmember.value),
            validate!!.returnIntegerValue(Totalmale.value),
            validate!!.returnIntegerValue(Totalfemale.value),
            validate!!.returnIntegerValue(Totaltransgender.value),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
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
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
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
            validate!!.getAgenda(
                collectiveProfileActivityFifth.et_challenges1,
                collectiveProfileActivityFifth.et_challenges2,
                collectiveProfileActivityFifth.et_challenges3,
            ),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_meeting_conducted),
            collectiveProfileActivityFifth.returnID(FrequencyMeeting.value!!, 24, langID),
            Specifyoth.value!!,
            collectiveProfileActivityFifth.returnID(RegularityMeeting.value!!, 11, langID),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_meeting_schedule),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_bank_account),
            collectiveProfileActivityFifth.returnID(Groupsaving.value!!, 20, langID),
            Otherinr.value!!,
            collectiveProfileActivityFifth.returnID(Frequencygroupsaving.value!!, 21, langID),
            Otherfreq.value!!,
            cBank.value!!,
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_record_book),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivityFifth.rg_record_book_update),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun updatecollectiveprofileSix(collectiveProfileActivitySixth: CollectiveProfileActivitySixth) {
        updatecollectivesix(
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
            validate!!.getschemes(
                collectiveProfileActivitySixth.et_other_q501b1,
                collectiveProfileActivitySixth.et_other_q501b2,
                collectiveProfileActivitySixth.et_other_q501b3,
                collectiveProfileActivitySixth.et_other_q501b4,
                collectiveProfileActivitySixth.et_other_q501b5
            ),
            validate!!.getschemes(
                collectiveProfileActivitySixth.et_details_service1,
                collectiveProfileActivitySixth.et_details_service2,
                collectiveProfileActivitySixth.et_details_service3,
                collectiveProfileActivitySixth.et_details_service4,
                collectiveProfileActivitySixth.et_details_service5
            ),
            validate!!.getschemes(
                collectiveProfileActivitySixth.et_service_provider1,
                collectiveProfileActivitySixth.et_service_provider2,
                collectiveProfileActivitySixth.et_service_provider3,
                collectiveProfileActivitySixth.et_service_provider4,
                collectiveProfileActivitySixth.et_service_provider5
            ),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivitySixth.rg_services_schemes),
            validate!!.GetAnswerTypeRadioButtonID(collectiveProfileActivitySixth.rg_enterprise_business),
            collectivelinkage,
            collectiveplanbuisness, Other1.value!!, Other2.value!!,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2), 1
        )
    }

    private fun updatecollectivesix(
        guid: String,
        Service: String?,
        ServiceAvailing: String?,
        ServiceProvider: String?,
        serviceschemes: Int,
        enterprisebuisness: Int,
        collectivelinkage: String,
        collectiveplanbuisness: String, Linkages_oth: String, Collective_opp_Other: String,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivesix(
                guid,
                Service,
                ServiceAvailing,
                ServiceProvider,
                serviceschemes,
                enterprisebuisness,
                collectivelinkage,
                collectiveplanbuisness, Linkages_oth, Collective_opp_Other,
                updatedBy,
                updated_on,
                IsEdited
            )
        }
    }

    fun insert(collectiveEntity: CollectiveEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.insert(collectiveEntity)
        }
    }

    fun update(
        guid: String, date: Long?,
        groupName: String,
        wardname: Int,
        localityname: String,
        collectiveid: String,
        zonename: Int,
        districcode: Int,
        panchayatcode: Int, pw_code: String, initials: String, IsEdited: Int,
        LandMark: String?,
        PinCode: String?,
        updatedBy: Int?,
        updated_on: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.update(
                guid, date, groupName, wardname, localityname,
                collectiveid, zonename, districcode, panchayatcode, pw_code, initials, IsEdited,
                LandMark,
                PinCode,
                updatedBy,
                updated_on
            )
        }
    }

    fun updatecollectivesecond(
        guid: String,
        formationdate: Long?,
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
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
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
                updatedBy,
                updated_on,
                IsEdited
            )
        }
    }

    fun updatecollectivefour(
        guid: String,
        tenure: String, Rolerotation: Int,
        electionob: Int,
        electionfreq: String,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivefourth(
                guid,
                tenure, Rolerotation,
                electionob,
                electionfreq,
                updatedBy,
                updated_on,
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
        RegularityMeeting: Int, meetingschedule: Int,
        bankac: Int,
        groupsaving: Int,
        otherinr: String,
        freqsaving: Int,
        othersaving: String,
        cbank: String,
        recordbook: Int,
        recordbookupdate: Int,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            collectiveRepository.updatecollectivefive(
                guid,
                MemberSaving, MemberSavingOther,
                Availloan, AvailloanWhere, AvailloanOther,
                LoanChallange,
                Meetingconducted,
                FrequencyMeeting, FrequencyMeetingOther,
                RegularityMeeting, meetingschedule,
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
    }


    fun getCollectivedatabyGuid(guid: String): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCollectivedatabyGuid(guid)
    }

    fun getCommWData(iDisCode: Int, izone: Int, iward: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommWData(iDisCode, izone, iward)
    }

     fun getcollectiveData(iDisCode: Int, izone: Int, iward: Int):List<CollectiveEntity> {
        return collectiveRepository.getcollectiveData(iDisCode, izone, iward)
    }


    fun getCommWData(iDisCode: Int, izone: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommWData(iDisCode, izone)
    }

    fun getCommWData(iDisCode: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommWData(iDisCode)
    }


    fun getCommPData(iDisCode: Int, iPanchayat: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommPData(iDisCode, iPanchayat)
    }

    fun getCommPData(iDisCode: Int): LiveData<List<CollectiveEntity>> {
        return collectiveRepository.getCommPData(iDisCode)
    }

    fun deletecollective(collectiveEntity: CollectiveEntity) {
        viewModelScope.launch {
            collectiveRepository.delete(collectiveEntity)
        }
    }

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return collectiveRepository.getMstDist(StateCode, DistrictIn)
    }

    fun getCommunityCount(): Int {
        return collectiveRepository!!.getCommunityCount()
    }

    fun getCommunityID(CollectiveID: String): Int {
        return collectiveRepository!!.getCommunityID(CollectiveID)
    }

    fun getCollectiveDataByCollectiveGuid(collectiveGuid: String): List<CollectiveEntity> {
        return collectiveRepository.getCollectiveDataByCollectiveGuid(collectiveGuid)
    }


}