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
import kotlinx.android.synthetic.main.activity_improfile_demographic.*
import kotlinx.android.synthetic.main.activity_improfile_fifth.*
import kotlinx.android.synthetic.main.activity_improfile_fourth.*
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_improfile_six.*
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndividualProfileViewModel(private val imProfileRepository: IndividualProfileRepository) :
    BaseViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    val State = MutableLiveData<Int>()

    var validate: Validate? = null
    val imProfileData = imProfileRepository.getallProfiledata()



    //    val HHId = MutableLiveData<Int>()
    val IMPRFUniqueID = MutableLiveData<String>()
    val CrpName = MutableLiveData<String>()
    val SuperverCor = MutableLiveData<String>()

    val PsyHHID = MutableLiveData<Int>()
    val PsyIMID = MutableLiveData<Int>()
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
    val KindWaste = MutableLiveData<String>()
    val EDUCATION = MutableLiveData<Int>(0)
    val SpecifySellWaste = MutableLiveData<String>()
    val PrimaryOccup = MutableLiveData<Int>()
    val SpecifyPrimaryOccup = MutableLiveData<String>()
    val DayPrimaryJob = MutableLiveData<String>()
    val DailyIncome = MutableLiveData<String>()
    val SecSourceIncom = MutableLiveData<Int>()
    val specifySecondaryIncome = MutableLiveData<String>()
    val WorkingDaysSecondaryJob = MutableLiveData<String>()
    val AvgDailySecIncome = MutableLiveData<String>()
    val SpecifySkillJob = MutableLiveData<String>()
    val CollectiveMember = MutableLiveData<String>()
    val SpecifyJobBusiness = MutableLiveData<String>()


    val saveandnextText = MutableLiveData<String>()
    val ALTERCONTACT = MutableLiveData<String>()

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

    fun collectiveProfileForthData(
        rg_have_adhar: Int,
        rg_have_voter: Int,
        rg_have_pan: Int,
        rg_have_income: Int,
        rg_have_caste: Int,
        rg_svg_bank_act: Int,


        ) {
        haveAdhar = rg_have_adhar
        haveVoter = rg_have_voter
        havePan = rg_have_pan
        haveIncome = rg_have_income
        haveCaste = rg_have_caste
        svgBankAct = rg_svg_bank_act

    }

    var availAnyScheme = 0
    var availedServicesPast = 0
    fun collectiveProfileFifthData(
        rg_avail_any_scheme: Int,
        rg_availed_services_past: Int
    ) {
        availAnyScheme = rg_avail_any_scheme
        availedServicesPast = rg_availed_services_past

    }

    var skillsJobsPicking = ""
    var newJobsBusiness = 0
    var memberCigShg = 0
    fun collectiveProfileSixData(
        skills_jobs_picking: String,
        rg_new_jobs_business: Int,
        rg_member_cig_shg: Int

    ) {
        skillsJobsPicking = skills_jobs_picking
        newJobsBusiness = rg_new_jobs_business
        memberCigShg = rg_member_cig_shg
    }

    fun saveandUpdateCollectiveProfile(
        individualProfileFirstActivity: IMProfileOneActivity,
        initials: String
    ) {
//        val date: String? = Date.value
        val crpname: String? = CrpName.value
        val superverCor: String? = SuperverCor.value

        var Ward1 = 0
        var PWCode = ""
        var isUrban = 0

        val District1 = validate?.let {
            returnDistrictID(
                individualProfileFirstActivity.spin_districtname.selectedItemPosition,
                it.RetriveSharepreferenceInt(AppSP.StateCode)
            )
        }
        val Zone1 = District1?.let {
            individualProfileFirstActivity.returnZoneID(
                individualProfileFirstActivity.spin_zone.selectedItemPosition,
                it
            )
        }
        if (Zone1 != null) {
            if (Zone1 > 0) {
                Ward1 = individualProfileFirstActivity.returnWardID(
                    individualProfileFirstActivity.spin_bbmp.selectedItemPosition,
                    Zone1
                )
                PWCode = "W"
                isUrban = 1
            } else {
                Ward1 = individualProfileFirstActivity.returnPanchayatID(
                    individualProfileFirstActivity.spin_panchayatname.selectedItemPosition,
                    District1
                )
                PWCode = "P"
                isUrban = 2
            }
        }

        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) == "") {
            var imProfileGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID, imProfileGuid)
            //save to appsp
            insert(
                IndividualProfileEntity(
                    imProfileGuid,
                    individualProfileFirstActivity.returnHH_GUID(
                        individualProfileFirstActivity.spin_hhid.selectedItemPosition,
                        isUrban,
                        Zone1!!,
                        Ward1
                    ),
                    crpname,
                    superverCor,
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode,
                    individualProfileFirstActivity.et_localityname.text.toString(),
                    validate!!.getDaysfromdates(individualProfileFirstActivity.et_formfilngjgDate.text.toString(), 1),
                    hhUid,
                    IndvCode,
                    "",
                    0,
                    0,
                    0,
                    0,
                    "",
                    "",
                    0,
                    "",
                    0,
                    -1, 0, -1, -1, "", "", "",
                    "", "", "", "", "", "",
                    "",
                    0, -1,
                    "",
                    "",
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
                    -1,
                    "",
                    -1,
                    "",
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    0,
                    0,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID), initials, "", "", "","","",
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
                validate!!.getDaysfromdates(individualProfileFirstActivity.et_formfilngjgDate.text.toString(), 1),
                hhUid,
                IndvCode,
                initials,
                individualProfileFirstActivity.et_localityname.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                1
            )
        }

    }

    fun updateProfileSixData(imProfileSixActivity: IMProfileSixActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val specify_skillJob: String? = SpecifySkillJob.value
        val colective_member: String? = CollectiveMember.value
        val specify_job_business: String? = SpecifyJobBusiness.value


        updateIMProfileSixData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            skillsJobsPicking,
            specify_skillJob,
            newJobsBusiness,
            imProfileSixActivity.recycledata(),
            specify_job_business,
            memberCigShg,
            colective_member,
            imProfileSixActivity.et_specify_laboure.text.toString(),
            imProfileSixActivity.et_specify_business.text.toString(),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )

    }

    fun updateProfileFifthData(imProfileFifthActivity: IMProfileFifthActivity) {

        updateIMProfileFifthData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            availedServicesPast,
            validate!!.getschemes(
                imProfileFifthActivity.et_service_detail_respodent1,
                imProfileFifthActivity.et_service_detail_respodent2,
                imProfileFifthActivity.et_service_detail_respodent3,
                imProfileFifthActivity.et_service_detail_respodent4,
                imProfileFifthActivity.et_service_detail_respodent5
            ),
            validate!!.getschemes(
                imProfileFifthActivity.et_service_provider_department1,
                imProfileFifthActivity.et_service_provider_department2,
                imProfileFifthActivity.et_service_provider_department3,
                imProfileFifthActivity.et_service_provider_department4,
                imProfileFifthActivity.et_service_provider_department5
            ),
            availAnyScheme,
            validate!!.getschemes(
                imProfileFifthActivity.et_details_service_avail1,
                imProfileFifthActivity.et_details_service_avail2,
                imProfileFifthActivity.et_details_service_avail3,
                imProfileFifthActivity.et_details_service_avail4,
                imProfileFifthActivity.et_details_service_avail5
            ),
            validate!!.getschemes(
                imProfileFifthActivity.et_darpartment_service_provide1,
                imProfileFifthActivity.et_darpartment_service_provide2,
                imProfileFifthActivity.et_darpartment_service_provide3,
                imProfileFifthActivity.et_darpartment_service_provide4,
                imProfileFifthActivity.et_darpartment_service_provide5
            ),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun updateForthProfileData(imProfileFourthActivity: IMProfileFourthActivity) {

        updateIMProfileForthData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            validate!!.GetAnswerTypeRadioButtonID(imProfileFourthActivity.rg_have_adhar),
            haveVoter,
            havePan,
            haveIncome,
            haveCaste,
            svgBankAct,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )

    }

    fun updateProfileDemographicData(imProfileDemographicActivity: IMProfileDemographicActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val b_staylong: String? = BLONGSTAY.value
        val age: String? = AGE.value
        val contact_no: String? = CONTACT.value
        val akter_contact_no: String? = ALTERCONTACT.value
        val namerespo: String? = NameRespo.value
        validate!!.SaveSharepreferenceInt(
            AppSP.READWRITE,
            validate!!.GetAnswerTypeRadioButtonID(imProfileDemographicActivity.rg_can_read)
        )
        validate!!.SaveSharepreferenceInt(
            AppSP.SMARTPHONE,
            validate!!.GetAnswerTypeRadioButtonID(imProfileDemographicActivity.rg_access_sphone)
        )
        validate!!.SaveSharepreferenceInt(
            AppSP.ACESSMOBILE,
            validate!!.GetAnswerTypeRadioButtonID(imProfileDemographicActivity.rg_acess_mob_data)
        )

        updateIMProfileDemographicData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            namerespo,
            imProfileDemographicActivity.returnID(Gender.value!!, 1, langID),
            validate!!.returnIntegerValue(age),
            imProfileDemographicActivity.returnID(CASTE.value!!, 5, langID),
            imProfileDemographicActivity.returnID(MARITAL.value!!, 6, langID),
            contact_no,
            akter_contact_no,
            imProfileDemographicActivity.returnID(State.value!!, 7, langID),
            SpecifyState.value.toString(),
            validate!!.returnIntegerValue(b_staylong),
            validate!!.GetAnswerTypeRadioButtonID(imProfileDemographicActivity.rg_can_read),
            imProfileDemographicActivity.returnID(EDUCATION.value!!, 8, langID),
            validate!!.GetAnswerTypeRadioButtonID(imProfileDemographicActivity.rg_access_sphone),
            validate!!.GetAnswerTypeRadioButtonID(imProfileDemographicActivity.rg_acess_mob_data),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )

    }

    fun updateProfileSecondData(imProfileTwoActivity: IMProfileTwoActivity) {
        val specify_mobilelaung: String? = SpecifyMobileLaung.value
        val specify_speack: String? = SpecifySpeack.value
        val specify_read: String? = SpecifyRead.value
        val specify_write: String? = SpecifyWrite.value
        val specify_communi: String? = SpecifyCommuni.value

        updateIMProfileSecondData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
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
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
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
            validate!!.GetAnswerTypeCheckBoxButtonID(imProfileThirdActivity.chk_sell_waste_collect),
            specify_sellwaste,
            imProfileThirdActivity.returnID(PrimaryOccup.value!!, 13, langID),
            specify_primaryoccup,
            validate!!.returnIntegerValue(dailyinicome),
            validate!!.returnIntegerValue(days_primaryjob),
            isSecondrySource,
            secSource,
            specify_sec_income,
            validate!!.returnIntegerValue(workDays_SecJob),
            validate!!.returnIntegerValue(avgDaily_secIncome),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
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
        dateform: Long?,
        hhuid: String?,
        imuid: String?,
        initials: String,
        locality: String,
        updatedBy: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
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
                imuid,
                initials,
                locality,
                updatedBy,
                UpdatedOn,
                IsEdited
            )
        }
    }

    fun updateIMProfileDemographicData(
        IndGUID: String,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        alter_contact: String?,
        StateID: Int?,
        StateOther: String,
        ResidingSince: Int?,
        Read_Write: Int?,
        Education: Int??,
        Smartphone: Int?,
        MobileData: Int?,
        Updatedby: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileDemographicData(
                IndGUID,
                name,
                gender,
                age,
                caste,
                marital_status,
                contact,
                alter_contact,
                StateID,
                StateOther,
                ResidingSince,
                Read_Write,
                Education,
                Smartphone,
                MobileData,
                Updatedby,
                UpdatedOn,
                IsEdited
            )
        }
    }

    fun updateIMProfileSecondData(
        IndGUID: String,
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
        updatedBy: Int?,
        UpdatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileSecondData(
                IndGUID,
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
                updatedBy,
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
        waste_disposal: String?,
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
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
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
                updatedBy,
                updated_on,
                IsEdited
            )
        }
    }


    fun updateIMProfileFifthData(
        IndGUID: String,
        schemes_availed: Int?,
        scheme_details: String?,
        scheme_sp: String?,
        schemes_availed_cur: Int?,
        scheme_details_cur: String?,
        schemesp_Cur: String?,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileFifthData(
                IndGUID,
                schemes_availed,
                scheme_details,
                scheme_sp,
                schemes_availed_cur,
                scheme_details_cur,
                schemesp_Cur,
                updatedBy,
                updated_on,
                IsEdited
            )
        }
    }

    fun updateIMProfileSixData(
        IndGUID: String,
        schemeSP_cur: String?,
        jobs: String?,
        interested_job: Int?,
        interested_jobDetail: String?,
        job: String?,
        member_collective: Int?,
        collective_name: String?,
        laboure_oth: String?,
        business_oth: String?,
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileSixData(
                IndGUID,
                schemeSP_cur,
                jobs,
                interested_job,
                interested_jobDetail,
                job,
                member_collective,
                collective_name,
                laboure_oth,
                business_oth,
                updatedBy,
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
        updatedBy: Int?,
        updated_on: Long?,
        IsEdited: Int
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
                updatedBy,
                updated_on,
                IsEdited
            )
        }
    }


    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
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


        if (!list.isNullOrEmpty()) {
            data = list.let { imProfileRepository.getMstDist(StateCode, list) }
        } else {

            data = imProfileRepository.getMstDist(StateCode)

        }

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
    fun gethhByGUIDDataZone(
        HHGUID: String?,
        zoneCode: Int,
        ward: Int
    ): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhByGUIDDataZone(HHGUID, zoneCode, ward)
    }

    fun gethhByGUIDDataPanchayat(HHGUID: String?, panchayat: Int): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhByGUIDDataPanchayat(HHGUID, panchayat)
    }

    fun gethhByGUIDProfileDataWard(
        HHGUID: String?,
        ZoneCode: Int,
        WardCode: Int
    ): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhByGUIDProfileDataWard(HHGUID, ZoneCode, WardCode)
    }

    fun gethhByGUIDProfileDataPanchayat(
        HHGUID: String?,
        PanchayatCode: Int
    ): List<HouseholdProfileEntity> {
        return imProfileRepository.gethhByGUIDProfileDataPanchayat(HHGUID, PanchayatCode)
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

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return imProfileRepository.getMstDist(StateCode, DistrictIn)
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


    fun getIDPDisData(
        iPanchayat: Int,
        idiscode: Int,
        HHGUID: String?
    ): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDPDisData(iPanchayat, idiscode, HHGUID)
    }

    fun getIDDisWData(
        idis: Int,
        izone: Int,
        iward: Int,
        HHGUID: String?
    ): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDDisWData(idis, izone, iward, HHGUID)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDZData(idiscode, izone)
    }

    fun getIDDistrictData(idiscode: Int): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIDDistrictData(idiscode)
    }
}

