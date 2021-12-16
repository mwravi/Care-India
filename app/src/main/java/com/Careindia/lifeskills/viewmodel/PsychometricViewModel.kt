package com.careindia.lifeskills.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.PsychometricEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.psychometricscreen.PsychometricFirstActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricSecondActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricForthActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricThirdActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PsychometricViewModel(private val psychometricRepository: PsychometricRepository) :
    BaseViewModel() {
    var validate: Validate? = null

    val psychometricData = psychometricRepository.getallPsychometricdata()

    val saveandnextText = MutableLiveData<String>()

    val PsyHHID = MutableLiveData<Int>()
    val PsyIMID = MutableLiveData<Int>()
    val NameParticipant = MutableLiveData<String>()
    val AgeParticipant = MutableLiveData<String>()
    val PrimaryOccu = MutableLiveData<String>()
    val SecondaryOccu = MutableLiveData<String>()
    val CommunityName = MutableLiveData<String>()
    val SHGName = MutableLiveData<String>()
    val EnterPrise = MutableLiveData<String>()
    val ContactNo = MutableLiveData<String>()
    val NameCrp = MutableLiveData<String>()
    val Date = MutableLiveData<String>()

    //TwoActivity
    val MinAgeLimit = MutableLiveData<Int>()
    val EducationAppli = MutableLiveData<Int>()
    val WomenSCate = MutableLiveData<Int>()
    val WomenEcoCate = MutableLiveData<Int>()
    val EmpCategory = MutableLiveData<Int>()
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
    }

    var hhid: String = ""
    var imid: String = ""
    fun collectivefirstData(hhCodess: Any, imCode: Any) {
        hhid = hhCodess.toString()
        imid = imCode.toString()
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
        val date: String? = Date.value


        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) == "") {
            var patGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.PATGUID, patGuid)

            insert(
                PsychometricEntity(
                    validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
                    hhid,
                    imid,
                    nameparticipant,
                    ageparticipant,
                    primaryOccu,
                    secondryOccu,
                    namecommunity,
                    nameshg,
                    enterprise,
                    contactNo,
                    namecrp,
                    date,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    "", ",", 0,
                    validate!!.currentdatetime,
                    0, "", "", 0, 0,1
                )
            )

        } else if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
            updatePsychometricFirstData(
                validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
                hhid,
                imid,
                nameparticipant,
                ageparticipant,
                primaryOccu,
                secondryOccu,
                namecommunity,
                nameshg,
                enterprise,
                contactNo,
                namecrp,
                date,
                validate!!.currentdatetime,
                1
            )

        }

    }

    fun updateSaveSecData(psychometricSecondActivity: PsychometricSecondActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatePsychometricSecData(
            validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
            psychometricSecondActivity.returnID(MinAgeLimit.value!!, 35, langID),
            psychometricSecondActivity.returnID(EducationAppli.value!!, 36, langID),
            psychometricSecondActivity.returnID(WomenSCate.value!!, 37, langID),
            psychometricSecondActivity.returnID(WomenEcoCate.value!!, 38, langID),
            validate!!.currentdatetime,
            1
        )

    }


    fun updateSaveThirdData(psychometricThirdActivity: PsychometricThirdActivity) {
        val langID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        updatePsychometricThirdData(
            validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!,
            psychometricThirdActivity.returnID(EmpCategory.value!!, 39, langID),
            psychometricThirdActivity.returnID(YearExp.value!!, 40, langID),
            psychometricThirdActivity.returnID(StageEmp.value!!, 41, langID),
            psychometricThirdActivity.returnID(SizeInvestment.value!!, 42, langID),
            psychometricThirdActivity.returnID(InvestMoney.value!!, 43, langID),
            psychometricThirdActivity.returnID(AwrnessMarket.value!!, 44, langID),
            validate!!.currentdatetime,
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
            validate!!.currentdatetime,
         1

        )

    }


    fun updatePsychometricFirstData(
        patGUID: String,
        hhID: String?,
        imID: String?,
        name_participant: String?,
        age_partcipant: Int?,
        primary_occ: String?,
        secondary_occ: String?,
        name_community: String?,
        name_shg: String?,
        nature_entrprise: String?,
        contact_no: String?,
        name_crp: String?,
        date: String?,
        updated_on: String?,
        IsEdited:Int
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
        updated_on: String?,
        IsEdited:Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            psychometricRepository.updatePsychometricSecData(
                patGUID,
                min_age_appli,
                applicantEdu,
                prefwoman_socialbw,
                prefwoman_ecobw,
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
        updated_on: String?,
        IsEdited:Int
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
        updated_on: String?,
        IsEdited:Int
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

    fun getallIdvPrfdata(hhcode: String): List<IndividualProfileEntity> {
        return psychometricRepository.getallIdvPrfdata(hhcode)
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


}