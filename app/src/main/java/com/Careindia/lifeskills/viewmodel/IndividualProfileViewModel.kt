package com.careindia.lifeskills.viewmodel


import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.improfile.*
import kotlinx.android.synthetic.main.activity_improfile_fourth.*
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndividualProfileViewModel(private val imProfileRepository: IndividualProfileRepository) :
    BaseViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    val State = MutableLiveData<Int>()

    var validate: Validate? = null
    val imProfileData = imProfileRepository.getallProfiledata()

    val Date = MutableLiveData<String>()


    //    val HHId = MutableLiveData<Int>()
    val IMPRFUniqueID = MutableLiveData<String>()
    val CrpName = MutableLiveData<String>()
    val SuperverCor = MutableLiveData<String>()
    val district = MutableLiveData<Int>()
    val PsyHHID = MutableLiveData<Int>()
    val PsyIMID = MutableLiveData<Int>()
    val zone = MutableLiveData<Int>()
    val ward = MutableLiveData<Int>()
    val panchayat = MutableLiveData<Int>()
    val HHUID = MutableLiveData<String>()
    val NameRespo = MutableLiveData<String>()
    val Gender = MutableLiveData<Int>()
    val AGE = MutableLiveData<String>()
    val CASTE = MutableLiveData<Int>()
    val MARITAL = MutableLiveData<Int>()
    val CONTACT = MutableLiveData<String>()
    val SpecifyState = MutableLiveData<String>()
    val SpecifySpeack = MutableLiveData<String>()
    val SpecifyRead = MutableLiveData<String>()
    val SpecifyWrite = MutableLiveData<String>()
    val SpecifyCommuni = MutableLiveData<String>()
    val SpecifyMobileLaung = MutableLiveData<String>()

    val WastePick = MutableLiveData<Int>()
    val BLONGSTAY = MutableLiveData<String>()
    val EDUCATION = MutableLiveData<Int>()
    val KindWaste = MutableLiveData<String>()
    val WASTEDISPOSAL = MutableLiveData<Int>()
    val SpecifySellWaste = MutableLiveData<String>()
    val PrimaryOccup = MutableLiveData<Int>()
    val SpecifyPrimaryOccup = MutableLiveData<String>()
    val DayPrimaryJob = MutableLiveData<String>()
    val DailyIncome = MutableLiveData<String>()
    val SecSourceIncom = MutableLiveData<Int>()
    val specifySecondaryIncome = MutableLiveData<String>()
    val WorkingDaysSecondaryJob = MutableLiveData<String>()
    val AvgDailySecIncome = MutableLiveData<String>()
    val DetailServiceProviderDep = MutableLiveData<String>()
    val AvailSchemeDetail = MutableLiveData<String>()
    val SpecifySkillJob = MutableLiveData<String>()
    val AlterGetOpport = MutableLiveData<Int>()
    val CollectiveMember = MutableLiveData<String>()


    val saveandnextText = MutableLiveData<String>()


    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
        CrpName.value = validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name)
        SuperverCor.value = validate!!.RetriveSharepreferenceString(AppSP.FCID_Name)

//        viewModelScope.launch {
//            var statePos = State.value
//
//        }
    }

    var speakmultiCheck = ""
    var langWriteCheck = ""
    var langReadCheck = ""
    var preferComniSpeak = ""
    var langPreferMobile = ""
    fun collectiveData(
        speackCheck: String,
        lang_readCheck: String,
        lang_writeCheck: String,
        prefer_comni_speaking: String,
        lang_prefer_mobile_use: String
    ) {
        speakmultiCheck = speackCheck
        langWriteCheck = lang_writeCheck
        langReadCheck = lang_readCheck
        preferComniSpeak = prefer_comni_speaking
        langPreferMobile = lang_prefer_mobile_use
    }

    var hhUid = ""
    var IndvCode = ""

    fun collectiveProfileOneData(
        hhid: String,
        indvcode: String
    ) {
        hhUid = hhid
        IndvCode = indvcode
    }


    var typeEmp = 0
    var isSecondrySource = 0
    fun collectiveProfileThirdData(
        rg_type_emp: Int,
        rg_secondary_income: Int
    ) {
        typeEmp = rg_type_emp
        isSecondrySource = rg_secondary_income
    }

    var haveAdhar = 0
    var haveVoter = 0
    var havePan = 0
    var haveIncome = 0
    var haveCaste = 0
    var svgBankAct = 0
    var availedServicesPast = 0
    fun collectiveProfileForthData(
        rg_have_adhar: Int,
        rg_have_voter: Int,
        rg_have_pan: Int,
        rg_have_income: Int,
        rg_have_caste: Int,
        rg_svg_bank_act: Int,
        rg_availed_services_past: Int

    ) {
        haveAdhar = rg_have_adhar
        haveVoter = rg_have_voter
        havePan = rg_have_pan
        haveIncome = rg_have_income
        haveCaste = rg_have_caste
        svgBankAct = rg_svg_bank_act
        availedServicesPast = rg_availed_services_past
    }

    var availAnyScheme = 0
    var skillsJobsPicking = ""
    var newJobsBusiness = 0
    var memberCigShg = 0
    fun collectiveProfileFifthData(
        rg_avail_any_scheme: Int,
        skills_jobs_picking: String,
        rg_new_jobs_business: Int,
        rg_member_cig_shg: Int
    ) {
        availAnyScheme = rg_avail_any_scheme
        skillsJobsPicking = skills_jobs_picking
        newJobsBusiness = rg_new_jobs_business
        memberCigShg = rg_member_cig_shg
    }

    fun saveandUpdateCollectiveProfile(
        individualProfileFirstActivity: IMProfileOneActivity,
        initials: String
    ) {
        val date: String? = Date.value
        val crpname: String? = CrpName.value
        val superverCor: String? = SuperverCor.value
        val b_staylong: String? = BLONGSTAY.value
        val age: String? = AGE.value
        val contact_no: String? = CONTACT.value
        val namerespo: String? = NameRespo.value
        var Ward1 = 0
        var PWCode = ""

        val District1 = returnDistrictID(district.value, 10)
        val Zone1 = individualProfileFirstActivity.returnZoneID(
            zone.value,
            District1
        )
        if (Zone1 > 0) {
            Ward1 = individualProfileFirstActivity.returnWardID(
                ward.value,
                Zone1
            )
            PWCode = "W"
        } else {
            Ward1 = individualProfileFirstActivity.returnPanchayatID(
                panchayat.value,
                District1
            )
            PWCode = "P"
        }

        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) == "") {
            var imProfileGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID, imProfileGuid)
            //save to appsp
            insert(
                IndividualProfileEntity(
                    imProfileGuid,
                    individualProfileFirstActivity.returnHH_GUID(individualProfileFirstActivity.spin_hhid.selectedItemPosition),
                    crpname,
                    superverCor,
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode,
                    0,
                    date,
                    hhUid,
                    IndvCode,
                    namerespo,
                    individualProfileFirstActivity.returnID(Gender.value!!, 1, langID),
                    Integer.parseInt(age),
                    individualProfileFirstActivity.returnID(CASTE.value!!, 5, langID),
                    individualProfileFirstActivity.returnID(MARITAL.value!!, 6, langID),
                    contact_no,
                    "",
                    individualProfileFirstActivity.returnID(State.value!!, 7, langID),
                    SpecifyState.value.toString(),
                    validate!!.returnIntegerValue(b_staylong),
                    -1, 0, -1, -1, "", "", "",
                    "", "", "", "", "", "",
                    "",
                    0, -1,
                    "",
                    0,
                    "",
                    0,
                    "",
                    -1,
                    -1,
                    -1,
                    0,
                    "",
                    -1,
                    -1,
                    -1, -1, -1, -1, -1, -1, -1,
                    "",
                    "",
                    -1,
                    "",
                    "",
                    "",
                    "",
                    -1,
                    "",
                    -1,
                    "",
                    validate!!.currentdatetime,
                    0,
                    "",
                    0,
                    0,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),initials,"","",
                    1
                )
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {

            update(
                validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
                crpname,
                superverCor,
                District1,
                Zone1,
                Ward1,
                PWCode,
                date,
                hhUid,
                namerespo,
                individualProfileFirstActivity.returnID(Gender.value!!, 1, langID),
                Integer.parseInt(age),
                individualProfileFirstActivity.returnID(CASTE.value!!, 5, langID),
                individualProfileFirstActivity.returnID(MARITAL.value!!, 6, langID),
                contact_no,
                individualProfileFirstActivity.returnID(State.value!!, 7, langID),
                SpecifyState.value.toString(),
                validate!!.returnIntegerValue(b_staylong),initials,
                validate!!.currentdatetime,
                1
            )
        }

    }

    fun updateProfileFifthData(imProfileFifthActivity: IMProfileFifthActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val availscheme_detail: String? = AvailSchemeDetail.value
        val specify_skillJob: String? = SpecifySkillJob.value
        val alterget_opport: String? = AlterGetOpport.value.toString()
        val colective_member: String? = CollectiveMember.value

        updateIMProfileFifthData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            availAnyScheme,
            availscheme_detail,
            skillsJobsPicking,
            specify_skillJob,
            newJobsBusiness,
            imProfileFifthActivity.returnID(AlterGetOpport.value!!, 16, langID).toString(),
            memberCigShg,
            colective_member,
            validate!!.currentdatetime,
            1
        )
    }

    fun updateForthProfileData(imProfileFourthActivity: IMProfileFourthActivity) {
        val detailscheme_providerDep: String? = DetailServiceProviderDep.value


        updateIMProfileForthData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            validate!!.GetAnswerTypeRadioButtonID(imProfileFourthActivity.rg_have_adhar),
            haveVoter,
            havePan,
            haveIncome,
            haveCaste,
            svgBankAct,
            availedServicesPast,
            detailscheme_providerDep,
            validate!!.currentdatetime,
            1
        )

    }

    fun updateProfileSecondData(imProfileTwoActivity: IMProfileTwoActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val stateid: Int? = State.value
        val specify_mobilelaung: String? = SpecifyMobileLaung.value
        val education: Int? = EDUCATION.value
        val specify_state: String? = SpecifyState.value.toString()
        val specify_speack: String? = SpecifySpeack.value
        val specify_read: String? = SpecifyRead.value
        val specify_write: String? = SpecifyWrite.value
        val specify_communi: String? = SpecifyCommuni.value

        updateIMProfileSecondData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            validate!!.GetAnswerTypeRadioButtonID(imProfileTwoActivity.rg_can_read),
            imProfileTwoActivity.returnID(EDUCATION.value!!, 8, langID),
            validate!!.GetAnswerTypeRadioButtonID(imProfileTwoActivity.rg_access_sphone),
            validate!!.GetAnswerTypeRadioButtonID(imProfileTwoActivity.rg_acess_mob_data),
            langReadCheck,
            langWriteCheck,
            speakmultiCheck,
            specify_speack,
            specify_read,
            specify_write,
            specify_communi,
            preferComniSpeak,
            langPreferMobile,
            specify_mobilelaung,
            validate!!.currentdatetime,
            1
        )
    }

    fun updateThirdData(imProfileThirdActivity: IMProfileThirdActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val kind_waste: String? = KindWaste.value
        val dailyinicome: String? = DailyIncome.value
        val days_primaryjob: String? = DayPrimaryJob.value

        val specify_sellwaste: String? = SpecifySellWaste.value
        val specify_primaryoccup: String? = SpecifyPrimaryOccup.value
        val specify_sec_income: String? = specifySecondaryIncome.value
        val workDays_SecJob: String? = WorkingDaysSecondaryJob.value
        val avgDaily_secIncome: String? = AvgDailySecIncome.value

        var secSource = 0
        if (isSecondrySource == 0) {
            secSource = 0
        } else {
            secSource = imProfileThirdActivity.returnID(SecSourceIncom.value!!, 14, langID)
        }

        updateIMProfileThirdData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            imProfileThirdActivity.returnID(WastePick.value!!, 10, langID),
            typeEmp,
            kind_waste,
            imProfileThirdActivity.returnID(WASTEDISPOSAL.value!!, 12, langID),
            specify_sellwaste,
            imProfileThirdActivity.returnID(PrimaryOccup.value!!, 13, langID),
            specify_primaryoccup,
            Integer.parseInt(dailyinicome),
            Integer.parseInt(days_primaryjob),
            isSecondrySource,
            secSource,
            specify_sec_income,
            validate!!.returnIntegerValue(workDays_SecJob),
            validate!!.returnIntegerValue(avgDaily_secIncome),
            validate!!.currentdatetime,
            1
        )


    }

    fun insert(imProfileEntity: IndividualProfileEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.insert(imProfileEntity)
        }
    }

    fun deleteImProfile(imProfileEntity: IndividualProfileEntity) {
        viewModelScope.launch {
            imProfileRepository.delete(imProfileEntity)
        }
    }


    fun update(
        IndGUID: String,
        crpname: String?,
        superverCor: String?,
        District1: Int?,
        Zone1: Int?,
        Ward1: Int?,
        Panchayat1: String?,
        dateform: String?,
        hhuid: String?,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        StateID: Int?,
        StateOther: String,
        ResidingSince: Int?,
        initials:String,
        UpdatedOn: String?,
        IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileData(
                IndGUID,
                crpname,
                superverCor,
                District1,
                Zone1,
                Ward1,
                Panchayat1,
                dateform,
                hhuid,
                name,
                gender,
                age,
                caste,
                marital_status,
                contact,
                StateID,
                StateOther,
                ResidingSince,
                initials,
                UpdatedOn,
                IsEdited
            )
        }
    }

    fun updateIMProfileSecondData(
        IndGUID: String,
        Read_Write: Int?,
        Education: Int??,
        Smartphone: Int?,
        MobileData: Int?,
        Languages_Read: String?,
        Languages_Write: String?,
        Languages_Speak: String?,
        speak_other: String?,
        read_other: String?,
        write_other: String?,
        communi_other: String?,
        PreferredLanguage_Communication: String?,
        preferredLanguage_mobile: String?,
        preferMobile_other: String?,
        UpdatedOn: String?,
        IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileSecondData(
                IndGUID,
                Read_Write,
                Education,
                Smartphone,
                MobileData,
                Languages_Read,
                Languages_Write,
                Languages_Speak,
                speak_other,
                read_other,
                write_other,
                communi_other,
                PreferredLanguage_Communication,
                preferredLanguage_mobile,
                preferMobile_other,
                UpdatedOn,
                IsEdited
            )
        }
    }


    fun updateIMProfileThirdData(
        IndGUID: String,
        wp_category: Int?,
        emp_type: Int?,
        waste_type: String?,
        waste_disposal: Int?,
        dispose_other: String?,
        primary_Occuptn: Int?,
        Primary_occu_other: String?,
        primary_inc: Int?,
        primary_wd: Int?,
        issecdry_Occuptn: Int?,
        secondary_occupation: Int?,
        secondry_occu_other: String?,
        secondary_wd: Int?,
        secondary_inc: Int?,
        updated_on: String?,
        IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileThirdData(
                IndGUID,
                wp_category,
                emp_type,
                waste_type,
                waste_disposal,
                dispose_other,
                primary_Occuptn,
                Primary_occu_other,
                primary_inc,
                primary_wd,
                issecdry_Occuptn,
                secondary_occupation,
                secondry_occu_other,
                secondary_wd,
                secondary_inc,
                updated_on,
                IsEdited
            )
        }
    }


    fun updateIMProfileFifthData(
        IndGUID: String,
        schemes_availed_cur: Int?,
        scheme_details_cur: String?,
        schemeSP_cur: String?,
        jobs: String?,
        interested_job: Int?,
        interested_jobDetail: String?,
        member_collective: Int?,
        collective_name: String?,
        updated_on: String?,
        IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileFifthData(
                IndGUID,
                schemes_availed_cur,
                scheme_details_cur,
                schemeSP_cur,
                jobs,
                interested_job,
                interested_jobDetail,
                member_collective,
                collective_name,
                updated_on,
                IsEdited
            )
        }
    }

    fun updateIMProfileForthData(
        IndGUID: String,
        aadhaar: Int?,
        voter: Int?,
        pan: Int?,
        income_certificate: Int?,
        caste_certificate: Int?,
        bank_account: Int?,
        schemes_availed: Int?,
        scheme_details: String?,
        updated_on: String?,
        IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileForthData(
                IndGUID,
                aadhaar,
                voter,
                pan,
                income_certificate,
                caste_certificate,
                bank_account,
                schemes_availed,
                scheme_details,
                updated_on,
                IsEdited
            )
        }
    }


    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            imProfileRepository.getMstDist(StateCode)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode

            }
        }
        return id
    }


    fun getIdvProfiledatabyGuid(guid: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIdvProfiledatabyGuid(guid)
    }

    fun getIdvProfiledatabyGuidNew(guid: String): List<IndividualProfileEntity> {
        return imProfileRepository.getIdvProfiledatabyGuidNew(guid)
    }

    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return imProfileRepository.gethhProfileData()
    }

    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhProfileDataWard(ZoneCode, WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhProfileDataPanchayat(PanchayatCode)
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    fun getHHCount(): Int {
        return imProfileRepository.getHHCount()
    }

    fun getIndividualID(IndvCode: String): Int {
        return imProfileRepository.getIndividualID(IndvCode)
    }

    fun getMstDist(StateCode: Int): List<MstDistrictEntity> {
        return imProfileRepository.getMstDist(StateCode)
    }

    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getallhhProfiledata(hhcode)
    }

    fun getallIdvdata(idvcode: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getallIdvdata(idvcode)
    }

    fun getIDWData(izone: Int, iward: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDWData(izone, iward)
    }

    fun getIDZData(izone: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDZData(izone)
    }

    fun getIDPData(iPanchayat: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDPData(iPanchayat)
    }

    fun gethhDataZone(zoneCode: Int, ward: Int): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhDataZone(zoneCode, ward)
    }

    fun gethhDataPanchayat(panchayat: Int): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhDataPanchayat(panchayat)
    }
}

