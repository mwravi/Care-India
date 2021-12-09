package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.PrimaryDataEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataFirstActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataSecondActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataThirdActivity
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.activity_primary_data_third.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PrimaryDataViewModel(private val primaryDataRepository: PrimaryDataRepository) :
    BaseViewModel() {
    var validate: Validate? = null

    val Date = MutableLiveData<String?>()
    val CommutyNameBlock = MutableLiveData<String>()
    val NameCandidate = MutableLiveData<String>()
    val Age = MutableLiveData<String>()
    val Gender = MutableLiveData<Int>()
    val ContactNo = MutableLiveData<String>()
    val MemberSHG = MutableLiveData<Int>()
    val SocialCate = MutableLiveData<Int>()
    val IncomeCerti = MutableLiveData<Int>()
    val TypeBussi = MutableLiveData<String>()
    val RangeInvesment = MutableLiveData<String>()
    val muchInvest = MutableLiveData<Int>()
    val PlanningInvest = MutableLiveData<Int>()
    val FinancialREQ = MutableLiveData<String>()
    val ExpectSupport = MutableLiveData<Int>()
    val haveInvested = MutableLiveData<String>()
    val TypeBusiness = MutableLiveData<String>()
    val selfEmpIdea = MutableLiveData<Int>()
    val AvailedLoans = MutableLiveData<String>()
    val FinancialAssistance = MutableLiveData<String>()
    val selfEmpReqSupport = MutableLiveData<Int>()
    val primaryData = primaryDataRepository.getallPrimarydata()

    init {

        validate = Validate(mContext)


    }

    var adhar = 0
    var pan = 0
    fun collectDataPrimaryFirst(
        rg_aadhar_card: Int,
        rg_pan_card: Int,
    ) {
        adhar = rg_aadhar_card
        pan = rg_pan_card

    }

    var bankAccount = 0
    var newBusiness = 0
    var businessPlan = 0
    var readyInvest = 0
    var availingLoanSubsidies = 0
    fun collectDataPrimarySecond(
        rg_bank_account: Int,
        rg_new_business: Int,
        rg_business_plan: Int,
        rg_ready_to_invest: Int,
        rg_availing_loan_subsidies: Int,
    ) {
        bankAccount = rg_bank_account
        newBusiness = rg_new_business
        businessPlan = rg_business_plan
        readyInvest = rg_ready_to_invest
        availingLoanSubsidies = rg_availing_loan_subsidies
    }

    var businessRegistered = 0
    var loansAvailedAlready = 0
    var expectingFinancialAssistance = 0
    fun collectDataPrimaryThird(
        rg_business_registered: Int,
        rg_loans_availed_already: Int,
        rg_expecting_financial_assistance: Int,
    ) {
        businessRegistered = rg_business_registered
        loansAvailedAlready = rg_loans_availed_already
        expectingFinancialAssistance = rg_expecting_financial_assistance

    }

    fun delete_record(HHGUID: String) {
        viewModelScope.launch {
            primaryDataRepository.delete_record(HHGUID)
        }
    }

    fun insert(primaryDataEntity: PrimaryDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.insert(primaryDataEntity)
        }
    }

    fun saveAndUpdate(
        primaryDataFirstActivity: PrimaryDataFirstActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {
        if (validate?.RetriveSharepreferenceString(AppSP.PDCGUID) == "") {
            var pdcguid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.PDCGUID, pdcguid)
            var hhguid =
                primaryDataFirstActivity.returnHH_GUID(primaryDataFirstActivity.spin_hh_id.selectedItemPosition)
            insert(
                PrimaryDataEntity(
                    pdcguid,
                    primaryDataFirstActivity.returnIM_GUID(
                        primaryDataFirstActivity.spin_im_id.selectedItemPosition,
                        hhguid
                    ),
                    hhguid,
                    "",
                    validate!!.returnIntegerValue(primaryDataFirstActivity.et_age.text.toString()),
                    primaryDataFirstActivity.et_beneficiary_name.text.toString(),
                    validate!!.returnID(
                        primaryDataFirstActivity.spin_gender,
                        mstLookupViewModel,
                        1,
                        iLanguageID
                    ),
                    primaryDataFirstActivity.et_contact_no.text.toString(),
                    validate!!.returnID(
                        primaryDataFirstActivity.spin_shg_jlg_cig,
                        mstLookupViewModel,
                        1,
                        iLanguageID
                    ),
                    0,
                    validate!!.GetAnswerTypeRadioButtonID(primaryDataFirstActivity.rg_cast_income),
                    validate!!.GetAnswerTypeRadioButtonID(primaryDataFirstActivity.rg_aadhar_card),
                    validate!!.GetAnswerTypeRadioButtonID(primaryDataFirstActivity.rg_pan_card),
                    0,
                    0,
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    "",
                    0,
                    "",
                    0,
                    "",
                    0,
                    0,
                    "",
                    0,
                    "",
                    0,
                    "",
                    "",
                    0,
                    0


                )
            )
        } else {
            primaryDataRepository.update_primary_first_data(
                validate!!.RetriveSharepreferenceString(AppSP.PDCGUID),
                "",
                validate!!.returnIntegerValue(primaryDataFirstActivity.et_age.text.toString()),
                primaryDataFirstActivity.et_beneficiary_name.text.toString(),
                validate!!.returnID(
                    primaryDataFirstActivity.spin_gender,
                    mstLookupViewModel,
                    1,
                    iLanguageID
                ),
                primaryDataFirstActivity.et_contact_no.text.toString(),
                validate!!.returnID(
                    primaryDataFirstActivity.spin_shg_jlg_cig,
                    mstLookupViewModel,
                    1,
                    iLanguageID
                ),
                0,
                validate!!.GetAnswerTypeRadioButtonID(primaryDataFirstActivity.rg_cast_income),
                validate!!.GetAnswerTypeRadioButtonID(primaryDataFirstActivity.rg_aadhar_card),
                validate!!.GetAnswerTypeRadioButtonID(primaryDataFirstActivity.rg_pan_card)
            )
        }
    }


    fun update_second_data(
        primaryDataSecondActivity: PrimaryDataSecondActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {


        update_primary_second_data(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_bank_account),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_new_business),
            primaryDataSecondActivity.et_kind_of_business.text.toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_business_plan),
            primaryDataSecondActivity.et_investment_range.text.toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_ready_to_invest),
            validate!!.returnID(primaryDataSecondActivity.spin_how_much_invest,mstLookupViewModel,30,iLanguageID),
            validate!!.returnID(primaryDataSecondActivity.spin_planning_investment,mstLookupViewModel,31,iLanguageID),
            primaryDataSecondActivity.et_financial_assistance.text.toString(),
            validate!!.returnID(primaryDataSecondActivity.spin_expected_support,mstLookupViewModel,32,iLanguageID),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_availing_loan_subsidies)
                  /*  validate!!.returnID(primaryDataSecondActivity.spin_which_source,mstLookupViewModel,32,iLanguageID),
            primaryDataSecondActivity.et_how_much_they_invested.text.toString()*/


            )


    }

    fun update_primary_second_data(
        PDCGUID: String?,
        ValidBank: Int?,
        Business_Interested: Int?,
        Business_details: String?,
        Business_Plan: Int?,
        Business_investment: String?,
        Invest_readiness: Int?,
        Invest_source: Int?,
        Invest_finance: Int,
        Invest_support: String,
        Loan_interested: Int,
        Loan_amount: Int


    ) {
        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_second_data(
                PDCGUID,
                ValidBank,
                Business_Interested,
                Business_details,
                Business_Plan,
                Business_investment,
                Invest_readiness,
                Invest_source,
                Invest_finance,
                Invest_support,
                Loan_interested,
                Loan_amount

            )
        }

    }


    fun update_third_data(
        primaryDataThirdActivity: PrimaryDataThirdActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {


        update_primary_third(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            primaryDataThirdActivity.et_what_kind_of_business.text.toString(),
        /*    validate!!.returnID(primaryDataThirdActivity.spin_source_income,mstLookupViewModel,30,iLanguageID),*/
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_business_registered),
            validate!!.returnID(primaryDataThirdActivity.spin_stage_of_self_employment,mstLookupViewModel,33,iLanguageID).toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_loans_availed_already),
            validate!!.returnID(primaryDataThirdActivity.spin_availed_loan,mstLookupViewModel,33,iLanguageID).toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_expecting_financial_assistance),
              validate!!.returnID(primaryDataThirdActivity.spin_expecting_support,mstLookupViewModel,32,iLanguageID),
            ""
     /* primaryDataSecondActivity.et_how_much_they_invested.text.toString()*/


        )


    }


    fun update_primary_third(
        PDCGUID: String,
        Business_type: String,
        Business_registered: Int,
        State_selfemp: String?,
        Loan_availed: Int?,
        Loan_source: String?,
        Financial_assist: Int?,
        Financial_assist_amt: Int?,
        Support_selfemp: String?


    ) {
        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_third(
                PDCGUID,
                Business_type,
                Business_registered,
                State_selfemp,
                Loan_availed,
                Loan_source,
                Financial_assist,
                Financial_assist_amt,
                Support_selfemp

            )
        }

    }

    fun getdatabyGuid(guid: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getdatabyGuid(guid)
    }
    fun getdatabyPDCGuid(guid: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getdatabyPDCGuid(guid)
    }

}