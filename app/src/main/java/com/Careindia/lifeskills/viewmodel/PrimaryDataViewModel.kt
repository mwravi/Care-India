package com.careindia.lifeskills.viewmodel

import androidx.lifecycle.MutableLiveData
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel


class PrimaryDataViewModel : BaseViewModel() {
    var validate: Validate? = null


    val HHID = MutableLiveData<String>()
    val IMID = MutableLiveData<String>()
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


}