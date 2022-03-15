package com.careindia.lifeskills.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.PsychometricEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.psychometricscreen.PsychometricFirstActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricForthActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricSecondActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricThirdActivity
import kotlinx.android.synthetic.main.activity_psychometric_first.*
import kotlinx.android.synthetic.main.activity_psychometric_second.*
import kotlinx.android.synthetic.main.activity_psychometric_third.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PsychometricViewModel(private val psychometricRepository: PsychometricRepository) :
    BaseViewModel() {
    var validate: Validate? = null

    val psychometricData = psychometricRepository.getallPsychometricdata()

    val saveandnextText = MutableLiveData<String>()

    val district = MutableLiveData<Int>()
    val zone = MutableLiveData<Int>()
    val ward = MutableLiveData<Int>()
    val panchayat = MutableLiveData<Int>()
    val NameParticipant = MutableLiveData<String>()
    val AgeParticipant = MutableLiveData<String>()
    val PrimaryOccu = MutableLiveData<String>()
    val SecondaryOccu = MutableLiveData<String>()
    val CommunityName = MutableLiveData<String>()
    val SHGName = MutableLiveData<String>()
    val EnterPrise = MutableLiveData<String>()
    val ContactNo = MutableLiveData<String>()
    val NameCrp = MutableLiveData<String>()
    val NameFcid = MutableLiveData<String>()
    val PrimaryOccup = MutableLiveData<Int>()
    val SecSourceIncom = MutableLiveData<Int>()

    //TwoActivity
    val EducationAppli = MutableLiveData<Int>()
    val WomenSCate = MutableLiveData<Int>()
    val CastBelong = MutableLiveData<String>()
    val WomenEcoCate = MutableLiveData<Int>()

    //    val EmpCategory = MutableLiveData<Int>()
    val YearExp = MutableLiveData<Int>()
    val StageEmp = MutableLiveData<Int>()
    val SizeInvestment = MutableLiveData<Int>()
    val InvestMoney = MutableLiveData<Int>()
    val AwrnessMarket = MutableLiveData<Int>()

    //thirdActivity
    val EvaluateRisk = MutableLiveData<Int>()
    val IncomeGenPrefer = MutableLiveData<Int>()
    val StaffRequiredPre = MutableLiveData<Int>()
    val WomenEnterpreneurs = MutableLiveData<Int>()
    val RequireFinancial = MutableLiveData<Int>()
    val WillgnessInvest = MutableLiveData<Int>()
    val SuccesOther = MutableLiveData<String>()


    init {
        validate = Validate(mContext)
        saveandnextText.value = "Save & Next"
        NameCrp.value = validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name)
        NameFcid.value = validate!!.RetriveSharepreferenceString(AppSP.FCID_Name)
    }

    var hhid: String = ""
    var imid: String = ""
    var PrimaryOccuption: Int = 0
    var SecondryOccuption: Int = 0
    fun collectivefirstData(
        hhCodess: Any,
        imCode: Any,
        primaryOccu: Int,
        secondryOccu: Int,
    ) {
        hhid = hhCodess.toString()
        imid = imCode.toString()
        PrimaryOccuption = primaryOccu
        SecondryOccuption = secondryOccu
    }

    fun saveandUpdatePsychometricData(psychometricFirstActivity: PsychometricFirstActivity) {
        val nameparticipant: String? = NameParticipant.value
        val ageparticipant: Int? = Integer.parseInt(AgeParticipant.value)
        val primaryOccu: String? = PrimaryOccu.value
        val secondryOccu: String? = SecondaryOccu.value
        val enterprise: String? = EnterPrise.value
        val namecrp: String? = NameCrp.value
        val namecommunity: String? = CommunityName.value
        val nameshg: String? = SHGName.value
        val contactNo: String? = ContactNo.value
//        val date: String? = Date.value

        var PWCode = ""
        var Ward1 = 0
        var isUrban = 0
        val District1 =
            psychometricFirstActivity.returnDistrictID(
                psychometricFirstActivity.spin_districtname_psy.selectedItemPosition,
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
            )
        val Zone1 = psychometricFirstActivity.returnZoneID(
            psychometricFirstActivity.spin_zone.selectedItemPosition,
            District1
        )
        if (Zone1 > 0) {
            Ward1 = psychometricFirstActivity.returnWardID(
                psychometricFirstActivity.spin_bbmp.selectedItemPosition,
                Zone1
            )
            PWCode = "W"
            isUrban = 1

        } else {
            Ward1 = psychometricFirstActivity.returnPanchayatID(
                psychometricFirstActivity.spin_panchayatname.selectedItemPosition,
                District1
            )
            PWCode = "P"
            isUrban = 1
        }
        validate!!.SaveSharepreferenceInt(
            AppSP.PSYAGE,
            validate!!.returnIntegerValue(psychometricFirstActivity.et_age_participant.text.toString())
        )

        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) == "") {
            var patGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.PATGUID, patGuid)


            val hhguid =
                psychometricFirstActivity.returnHH_GUID(
                    psychometricFirstActivity.spin_hhid.selectedItemPosition,
                    isUrban,
                    Zone1,
                    Ward1
                )

            insert(
                PsychometricEntity(
                    validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
                    validate!!.RetriveSharepreferenceString(AppSP.HhProfileGUID)!!,
                    validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
                    nameparticipant,
                    validate!!.returnIntegerValue(psychometricFirstActivity.et_age_participant.text.toString()),
                    psychometricFirstActivity.returnID(
                        psychometricFirstActivity.spin_primary_income.selectedItemPosition,
                        13,
                        langID
                    ),
                    psychometricFirstActivity.returnID(
                        psychometricFirstActivity.spin_secondry_income.selectedItemPosition,
                        14,
                        langID
                    ),
                    namecommunity,
                    nameshg,
                    enterprise,
                    contactNo,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.getDaysfromdates(psychometricFirstActivity.et_date.text.toString(), 1),
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    "", "",
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0, 0, "", 0, 0, 1,
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode, "", "", "", "",
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID), 0,
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode), 0
                )
            )

        } else if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
            updatePsychometricFirstData(
                validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
                validate!!.RetriveSharepreferenceString(AppSP.HhProfileGUID)!!,
                validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
                nameparticipant,
                ageparticipant,
                psychometricFirstActivity.returnID(
                    psychometricFirstActivity.spin_primary_income.selectedItemPosition,
                    13,
                    langID
                ),
                psychometricFirstActivity.returnID(
                    psychometricFirstActivity.spin_secondry_income.selectedItemPosition,
                    14,
                    langID
                ),
                namecommunity,
                nameshg,
                enterprise,
                contactNo,
                validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                validate!!.getDaysfromdates(psychometricFirstActivity.et_date.text.toString(), 1),
                District1,
                Zone1,
                Ward1,
                PWCode,
                psychometricFirstActivity.et_specif_primary_occu.text.toString(),
                psychometricFirstActivity.et_specify_source_secondary_income.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                1
            )

        }

    }

    fun updateSaveSecData(psychometricSecondActivity: PsychometricSecondActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatePsychometricSecData(
            validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
            psychometricSecondActivity.returnID(
                psychometricSecondActivity.spin_min_age_limit.selectedItemPosition,
                35,
                langID
            ),
            psychometricSecondActivity.returnID(EducationAppli.value!!, 36, langID),
            psychometricSecondActivity.returnID(WomenSCate.value!!, 37, langID),
            psychometricSecondActivity.returnID(WomenEcoCate.value!!, 38, langID),
            CastBelong.value!!,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )

    }


    fun updateSaveThirdData(
        psychometricThirdActivity: PsychometricThirdActivity,
        mstLookupViewModel: MstLookupViewModel
    ) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatePsychometricThirdData(
            validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
            psychometricThirdActivity.returnIDPsychoSpin(
                psychometricThirdActivity.spin_emp_category,
                mstLookupViewModel,
                39,
                langID
            ),
            psychometricThirdActivity.returnID(YearExp.value!!, 40, langID),
            psychometricThirdActivity.returnID(StageEmp.value!!, 41, langID),
            psychometricThirdActivity.returnID(SizeInvestment.value!!, 42, langID),
            psychometricThirdActivity.returnID(InvestMoney.value!!, 43, langID),
            psychometricThirdActivity.returnID(AwrnessMarket.value!!, 44, langID),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1

        )

    }

    var successfulEnterpreneurCheck = ""
    fun collectiveData(
        multiCheck_areas_succes_entrep: String,
    ) {
        successfulEnterpreneurCheck = multiCheck_areas_succes_entrep

    }

    fun updateSaveForthData(psychometricForthActivity: PsychometricForthActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatePsychometricForthData(
            validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
            psychometricForthActivity.returnID(EvaluateRisk.value!!, 45, langID),
            psychometricForthActivity.returnID(IncomeGenPrefer.value!!, 46, langID),
            psychometricForthActivity.returnID(StaffRequiredPre.value!!, 47, langID),
            psychometricForthActivity.returnID(WomenEnterpreneurs.value!!, 48, langID),
            psychometricForthActivity.returnID(RequireFinancial.value!!, 49, langID),
            psychometricForthActivity.returnID(WillgnessInvest.value!!, 50, langID),
            successfulEnterpreneurCheck,
            SuccesOther.value,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1

        )

    }


    fun updatePsychometricFirstData(
        patGUID: String,
        hhID: String?,
        imID: String?,
        name_participant: String?,
        age_partcipant: Int?,
        primary_occ: Int?,
        secondary_occ: Int?,
        name_community: String?,
        name_shg: String?,
        nature_entrprise: String?,
        contact_no: String?,
        name_crp: Int?,
        date: Long?,
        District1: Int?,
        Zone1: Int?,
        Ward1: Int?,
        Panchayat1: String?,
        primery_other: String?,
        secondry_other: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            psychometricRepository.updatePsychometricFirstData(
                patGUID,
                hhID,
                imID,
                name_participant,
                age_partcipant,
                primary_occ,
                secondary_occ,
                name_community,
                name_shg,
                nature_entrprise,
                contact_no,
                name_crp,
                date,
                District1,
                Zone1,
                Ward1,
                Panchayat1,
                primery_other,
                secondry_other,
                updatedby,
                updated_on,
                IsEdited
            )
        }
    }


    fun updatePsychometricSecData(
        patGUID: String,
        min_age_appli: Int?,
        applicantEdu: Int?,
        prefwoman_socialbw: Int?,
        prefwoman_ecobw: Int?,
        cast_belong: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            psychometricRepository.updatePsychometricSecData(
                patGUID,
                min_age_appli,
                applicantEdu,
                prefwoman_socialbw,
                prefwoman_ecobw,
                cast_belong,
                updatedby,
                updated_on,
                IsEdited
            )
        }
    }

    fun updatePsychometricThirdData(
        patGUID: String,
        selfemp_exp: Int?,
        yearexp_selfemp: Int?,
        stageself_empidea: Int?,
        sizeself_empplanned: Int?,
        wilinvst_margmny: Int?,
        awr_relmarket_selfemp: Int?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            psychometricRepository.updatePsychometricThirdData(
                patGUID,
                selfemp_exp,
                yearexp_selfemp,
                stageself_empidea,
                sizeself_empplanned,
                wilinvst_margmny,
                awr_relmarket_selfemp,
                updatedby,
                updated_on,
                IsEdited
            )
        }
    }

    fun updatePsychometricForthData(
        patGUID: String,
        evaluateRisk: Int?,
        incomegen_actinvst: Int?,
        incomegen_actmanage: Int?,
        woman_ct_goodent: Int?,
        entreq_finres: Int?,
        wilinvst_capbuilding: Int?,
        successfulEnt: String?,
        othersEnt: String?,
        updatedby: Int?,
        updated_on: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            psychometricRepository.updatePsychometricForthData(
                patGUID,
                evaluateRisk,
                incomegen_actinvst,
                incomegen_actmanage,
                woman_ct_goodent,
                entreq_finres,
                wilinvst_capbuilding,
                successfulEnt,
                othersEnt,
                updatedby,
                updated_on,
                IsEdited
            )
        }
    }


    fun insert(psychometricEntity: PsychometricEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            psychometricRepository.insert(psychometricEntity)
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
            data = list.let { psychometricRepository.getMstDist(StateCode, list) }
        } else {


            data = psychometricRepository.getMstDist(StateCode)

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

    fun getPsychometricbyGuid(guid: String): LiveData<List<PsychometricEntity>> {
        return psychometricRepository.getPsychometricbyGuid(guid)
    }

    fun deletepsychometric(psychometricEntity: PsychometricEntity) {
        viewModelScope.launch {
            psychometricRepository.delete(psychometricEntity)
        }
    }

    fun getallhhProfiledata(hhcode: String): LiveData<List<IndividualProfileEntity>> {
        return psychometricRepository.getallhhProfiledata(hhcode)
    }

    fun getallIdvPrfdata(hhGUID: String): List<IndividualProfileEntity> {
        return psychometricRepository.getallIdvPrfdata(hhGUID)
    }

    fun getallIdvPrfdataUpdate(hhcode: String): List<IndividualProfileEntity> {
        return psychometricRepository.getallIdvPrfdataUpdate(hhcode)
    }

    fun getallIdvdata(idvcode: String): LiveData<List<IndividualProfileEntity>> {
        return psychometricRepository.getallIdvdata(idvcode)
    }

    fun gethhProfileData(): LiveData<List<HouseholdProfileEntity>> {
        return psychometricRepository.gethhProfileData()
    }

    fun getImProfileData(): List<IndividualProfileEntity> {
        return psychometricRepository.getImProfileData()
    }

    fun gethhProfileDataNew(): List<HouseholdProfileEntity> {
        return psychometricRepository.gethhProfileDataNew()
    }

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return psychometricRepository.getMstDist(StateCode, DistrictIn)
    }


    fun gethhDataPanchayat(panchayat: Int): List<HouseholdProfileEntity> {
        return psychometricRepository.gethhDataPanchayat(panchayat)
    }

    fun gethhDataZone(zoneCode: Int, ward: Int): List<HouseholdProfileEntity> {
        return psychometricRepository.gethhDataZone(zoneCode, ward)
    }

    fun gethhProfileDataWard(ZoneCode: Int, WardCode: Int): List<HouseholdProfileEntity> {
        return psychometricRepository.gethhProfileDataWard(ZoneCode, WardCode)
    }

    fun gethhProfileDataPanchayat(PanchayatCode: Int): List<HouseholdProfileEntity> {
        return psychometricRepository.gethhProfileDataPanchayat(PanchayatCode)
    }

    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<PsychometricEntity>> {
        return psychometricRepository.getIDPDisData(iPanchayat, idiscode)
    }
    fun getPsychoList(imGuid: String): LiveData<List<PsychometricEntity>>{
        return psychometricRepository.getPsychoList(imGuid)
    }

    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<PsychometricEntity>> {
        return psychometricRepository.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<PsychometricEntity>> {
        return psychometricRepository.getIDZData(idiscode, izone)
    }

    fun getIDDistrictData(idiscode: Int): LiveData<List<PsychometricEntity>> {
        return psychometricRepository.getIDDistrictData(idiscode)
    }

    fun findIdvPrfdata(hhcode: String): List<IndividualProfileEntity> {
        return psychometricRepository.findIdvPrfdata(hhcode)
    }

    fun getallDataPsychometricdata(hhGUID: String): List<IndividualProfileEntity> {
        return psychometricRepository.getallDataPsychometricdata(hhGUID)
    }

    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity> {
        return psychometricRepository.getINDIDdata(indGUID)
    }

    fun getPsychodata(indvID: String): List<PsychometricEntity> {
        return psychometricRepository.getPsychodata(indvID)
    }

}