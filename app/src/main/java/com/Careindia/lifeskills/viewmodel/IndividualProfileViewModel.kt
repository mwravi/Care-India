package com.careindia.lifeskills.viewmodel


import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class IndividualProfileViewModel(private val imProfileRepository: IndividualProfileRepository) :
    BaseViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    val State = MutableLiveData<Int>()
    val ReadChecked = MutableLiveData<Int>()
    val IsSecondry = MutableLiveData<Int>()

    var validate: Validate? = null
    val imProfileData = imProfileRepository.getallProfiledata()

    val Date = MutableLiveData<String>()
    val CrpName = MutableLiveData<Int>()
    val SuperverCor = MutableLiveData<Int>()
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


        viewModelScope.launch {
            var statePos = State.value

        }
    }

    fun getSaleVisibility(): Int {
        return if (9 == 10) VISIBLE else GONE
    }

    var canRead = 0
    var accessSphone = 0
    var acessMobdata = 0
    var speakmultiCheck = ""
    var langWriteCheck = ""
    var langReadCheck = ""
    var preferComniSpeak = ""

    fun collectiveData(
        can_read: Int,
        access_sphone: Int,
        acess_mob_data: Int,
        speackCheck: String,
        lang_writeCheck: String,
        lang_readCheck: String,
        prefer_comni_speaking: String
    ) {
        canRead = can_read
        accessSphone = access_sphone
        acessMobdata = acess_mob_data
        speakmultiCheck = speackCheck
        langWriteCheck = lang_writeCheck
        langReadCheck = lang_readCheck
        preferComniSpeak = prefer_comni_speaking
    }

    var langPreferMobile = ""
    var typeEmp = 0
    var isSecondrySource = 0
    fun collectiveProfileThirdData(
        lang_prefer_mobile_use: String,
        rg_type_emp: Int,
        rg_secondary_income: Int
    ) {
        langPreferMobile = lang_prefer_mobile_use
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

    fun saveandUpdateCollectiveProfile() {
        val date: String? = Date.value
        val crpname: String? = returnID(CrpName.value, 41).toString()
        val superverCor: String? = returnID(SuperverCor.value, 42).toString()

        val sex: Int? = returnID(Gender.value, 43)
        val caste: Int? = returnID(CASTE.value, 44)
        val merital_s: Int? = returnID(MARITAL.value, 45)
        val age: String? = AGE.value
        //val age: Int? = Integer.parseInt(AGE.value)
        val contact_no: String? = CONTACT.value
        val hhuid: String? = HHUID.value
        val namerespo: String? = NameRespo.value
        val specify_state: String? = SpecifyState.value
        val specify_speack: String? = SpecifySpeack.value
        val specify_read: String? = SpecifyRead.value
        val specify_write: String? = SpecifyWrite.value
        val specify_communi: String? = SpecifyCommuni.value
        val specify_mobilelaung: String? = SpecifyMobileLaung.value
        val specify_sellwaste: String? = SpecifySellWaste.value
        val specify_primaryoccup: String? = SpecifyPrimaryOccup.value

        val specify_sec_income: String? = specifySecondaryIncome.value



        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) == "") {
            var imProfileGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID, imProfileGuid)
            //save to appsp
            insert(
                IndividualProfileEntity(
                    0,
                    imProfileGuid,
                    "",
                    "",
                    "",
                    "",
                    0,
                    "",
                    0,
                    date,
                    hhuid,
                    "",
                    namerespo,
                    sex,
                    Integer.parseInt(age),
                    caste,
                    merital_s,
                    contact_no,
                    "",
                    0,
                    0,
                    0, 0, 0, 0, "", "", "",
                    "", "",
                    0, 0,
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0, 0, 0, 0, 0, 0, 0,
                    "",
                    "",
                    0,
                    "",
                    "",
                    "",
                    0,
                    "",
                    0,
                    "",
                    validate!!.currentdatetime,
                    0,
                    "",
                    0,
                    0
                )
            )
        } else if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {

            update(
                validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
                date,
                hhuid,
                namerespo,
                sex,
                Integer.parseInt(age),
                caste,
                merital_s,
                contact_no,
                validate!!.currentdatetime
            )
        }

    }

    fun updateProfileFifthData() {
        val availscheme_detail: String? = AvailSchemeDetail.value
        val specify_skillJob: String? = SpecifySkillJob.value
        val alterget_opport: String? = returnID(AlterGetOpport.value, 72).toString()
        val colective_member: String? = CollectiveMember.value

        updateIMProfileFifthData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            availAnyScheme,
            availscheme_detail,
            skillsJobsPicking,
            specify_skillJob,
            newJobsBusiness,
            alterget_opport,
            memberCigShg,
            colective_member,
            validate!!.currentdatetime,
        )
    }

    fun updateForthProfileData() {
        val detailscheme_providerDep: String? = DetailServiceProviderDep.value
        val workDays_SecJob: String? = WorkingDaysSecondaryJob.value
        val avgDaily_secIncome: String? = AvgDailySecIncome.value

        updateIMProfileForthData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            haveAdhar,
            haveVoter,
            havePan,
            haveIncome,
            haveCaste,
            svgBankAct,
            availedServicesPast,
            detailscheme_providerDep,
            Integer.parseInt(workDays_SecJob),
            Integer.parseInt(avgDaily_secIncome),
            validate!!.currentdatetime
        )

    }

    fun updateProfileSecondData() {
        val stateid: Int? = returnID(State.value, 46)
        val b_staylong: String? = BLONGSTAY.value
        val education: Int? = returnID(EDUCATION.value, 48)


        updateIMProfileSecondData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            stateid!!,
            Integer.parseInt(b_staylong),
            canRead,
            education!!,
            accessSphone,
            acessMobdata,
            langReadCheck,
            langWriteCheck,
            speakmultiCheck,
            preferComniSpeak,
            validate!!.currentdatetime
        )
    }

    fun updateThirdData() {
        val kind_waste: String? = KindWaste.value
        val waste_pick: Int? = returnID(WastePick.value, 56)
        val waste_disposal: Int? = returnID(WASTEDISPOSAL.value, 58)
        val primary_occup: Int? = returnID(PrimaryOccup.value, 59)
        val sec_Sourceincome: Int? = returnID(SecSourceIncom.value, 61)
        val dailyinicome: String? = DailyIncome.value
        val days_primaryjob: String? = DayPrimaryJob.value

        updateIMProfileThirdData(
            validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
            langPreferMobile,
            waste_pick,
            typeEmp,
            kind_waste,
            waste_disposal,
            primary_occup,
            Integer.parseInt(dailyinicome),
            Integer.parseInt(days_primaryjob),
            isSecondrySource,
            sec_Sourceincome,
            validate!!.currentdatetime
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
        dateform: String?,
        hhuid: String?,
        name: String?,
        gender: Int?,
        age: Int?,
        caste: Int?,
        marital_status: Int?,
        contact: String?,
        UpdatedOn: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileData(
                IndGUID,
                dateform,
                hhuid,
                name,
                gender,
                age,
                caste,
                marital_status,
                contact,
                UpdatedOn
            )
        }
    }

    fun updateIMProfileSecondData(
        IndGUID: String,
        StateID: Int?,
        ResidingSince: Int?,
        Read_Write: Int?,
        Education: Int??,
        Smartphone: Int?,
        MobileData: Int?,
        Languages_Read: String?,
        Languages_Write: String?,
        Languages_Speak: String?,
        PreferredLanguage_Communication: String?,
        UpdatedOn: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileSecondData(
                IndGUID,
                StateID,
                ResidingSince,
                Read_Write,
                Education,
                Smartphone,
                MobileData,
                Languages_Read,
                Languages_Write,
                Languages_Speak,
                PreferredLanguage_Communication,
                UpdatedOn
            )
        }
    }


    fun updateIMProfileThirdData(
        IndGUID: String,
        preferredLanguage_mobile: String?,
        wp_category: Int?,
        emp_type: Int?,
        waste_type: String?,
        waste_disposal: Int?,
        primary_Occuptn: Int?,
        primary_inc: Int?,
        primary_wd:Int?,
        issecdry_Occuptn: Int?,
        secondary_occupation: Int?,
        updated_on: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imProfileRepository.updateIMProfileThirdData(
                IndGUID,
                preferredLanguage_mobile,
                wp_category,
                emp_type,
                waste_type,
                waste_disposal,
                primary_Occuptn,
                primary_inc,
                primary_wd,
                issecdry_Occuptn,
                secondary_occupation,
                updated_on
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
        updated_on: String?
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
                updated_on
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
        secondary_wd: Int?,
        secondary_inc: Int?,
        updated_on: String?
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
                secondary_wd,
                secondary_inc,
                updated_on
            )
        }
    }

    fun returnID(pos: Int?, flag: Int): Int {
        var data: List<MstCommonEntity>? = null
        data =
            imProfileRepository.getmstCommonData(flag)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).id!!
            }
        }
        return id
    }

    fun getIdvProfiledatabyGuid(guid: String): LiveData<List<IndividualProfileEntity>> {
        return imProfileRepository.getIdvProfiledatabyGuid(guid)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
            callbacks.add(callback)

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}

