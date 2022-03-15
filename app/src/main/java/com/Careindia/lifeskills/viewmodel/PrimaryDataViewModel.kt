package com.careindia.lifeskills.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.PrimaryDataEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseViewModel
import com.careindia.lifeskills.views.primarydatascreen.*
import kotlinx.android.synthetic.main.activity_primary_data_fifth.*

import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.activity_primary_data_fourth.*
import kotlinx.android.synthetic.main.activity_primary_data_fourth.et_business_invest_other
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.activity_primary_data_seventh.*
import kotlinx.android.synthetic.main.activity_primary_data_sixth.*
import kotlinx.android.synthetic.main.activity_primary_data_third.*
import kotlinx.android.synthetic.main.activity_primary_data_third.et_business_start_other
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

    var businessPlan = 0
    var readyInvest = 0
    var availingLoanSubsidies = 0
    fun collectDataPrimarySecond(
        rg_bank_account: Int,

        rg_business_plan: Int,
        rg_ready_to_invest: Int,
        rg_availing_loan_subsidies: Int,
    ) {
        bankAccount = rg_bank_account

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
        var PWCode = ""
        var Ward1 = 0
        var isUrban = 0
        val District1 =
            primaryDataFirstActivity.returnDistrictID(
                primaryDataFirstActivity.spin_districtname.selectedItemPosition,
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
            )
        val Zone1 = primaryDataFirstActivity.returnZoneID(
            primaryDataFirstActivity.spin_zone.selectedItemPosition,
            District1
        )
        if (Zone1 > 0) {
            Ward1 = primaryDataFirstActivity.returnWardID(
                primaryDataFirstActivity.spin_bbmp.selectedItemPosition,
                Zone1
            )
            PWCode = "W"
            isUrban = 1

        } else {
            Ward1 = primaryDataFirstActivity.returnPanchayatID(
                primaryDataFirstActivity.spin_panchayatname.selectedItemPosition,
                District1
            )
            PWCode = "P"
            isUrban = 2

        }
        if (validate?.RetriveSharepreferenceString(AppSP.PDCGUID) == "") {
            val pdcguid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.PDCGUID, pdcguid)
            val hhguid =
                primaryDataFirstActivity.returnHH_GUID(
                    1,
                    isUrban,
                    Zone1,
                    Ward1
                )
            insert(
                PrimaryDataEntity(
                    pdcguid,
                    validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!,
                    validate!!.RetriveSharepreferenceString(AppSP.HhProfileGUID)!!,
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode,
                    primaryDataFirstActivity.et_community_name.text.toString(),
                    validate!!.getDaysfromdates(
                        primaryDataFirstActivity.et_collection_date.text.toString(),
                        1
                    ),
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                    primaryDataFirstActivity.et_community_name.text.toString(),
                    primaryDataFirstActivity.et_beneficiary_name.text.toString(),
                    validate!!.returnIntegerValue(primaryDataFirstActivity.et_age.text.toString()),
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
                        28,
                        iLanguageID
                    ),
                    0,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    "",
                    -1,
                    "",
                    -1,
                    0,
                    "",
                    "",
                    0,
                    0,
                    "",
                    -1,
                    0,
                    "",
                    "",
                    "",
                    "",
                    0,
                    -1,
                    0,
                    "",
                    -1,
                    0,
                    -1,
                    0,
                    "",
                    "",
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    "",
                    0,
                    0,
                    1,
                    primaryDataFirstActivity.et_collective_name.text.toString(),
                    "", "", "", "", "", "",
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode), 0
                )
            )
        } else {
            primaryDataRepository.update_primary_first_data(
                validate!!.RetriveSharepreferenceString(AppSP.PDCGUID),
                District1,
                Zone1,
                Ward1,
                primaryDataFirstActivity.et_community_name.text.toString(),
                validate!!.getDaysfromdates(
                    primaryDataFirstActivity.et_collection_date.text.toString(),
                    1
                ),
                primaryDataFirstActivity.et_community_name.text.toString(),
                primaryDataFirstActivity.et_beneficiary_name.text.toString(),
                validate!!.returnIntegerValue(primaryDataFirstActivity.et_age.text.toString()),
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
                    28,
                    iLanguageID
                ),
                0,
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                primaryDataFirstActivity.et_collective_name.text.toString(),
                1
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
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_cast_income),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_aadhar_card),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_pan_card),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSecondActivity.rg_bank_account),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun update_primary_second_data(
        PDCGUID: String,
        CastIncomeCertificate: Int?,
        ValidAadhaar: Int?,
        ValidPAN: Int?,
        ValidBank: Int,
        updatedBy: Int?,
        updatedOn: Long,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_second_data(
                PDCGUID,
                CastIncomeCertificate,
                ValidAadhaar,
                ValidPAN,
                ValidBank,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }

    }


    fun update_third_data(
        primaryDataThirdActivity: PrimaryDataThirdActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {
        var checkId = ""
        var much_invest = 0
        var source = 0
        if (primaryDataThirdActivity.lay_kind_business_start.visibility == View.VISIBLE) {
            checkId =
                validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataThirdActivity.check_kind_business_start)
        }

        if (primaryDataThirdActivity.lay_how_much_you_invest.visibility == View.VISIBLE) {
            much_invest = validate!!.returnID(
                primaryDataThirdActivity.spin_how_much_invest,
                mstLookupViewModel,
                30, iLanguageID
            )
        }

        if (primaryDataThirdActivity.lay_which_source.visibility == View.VISIBLE) {
            source = validate!!.returnID(
                primaryDataThirdActivity.spin_which_source,
                mstLookupViewModel,
                53,
                iLanguageID
            )
        }


        update_primary_third(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_new_business),
            checkId,
            primaryDataThirdActivity.et_business_start_other.text.toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_training_skill),
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataThirdActivity.kind_of_business),
            primaryDataThirdActivity.et_business_other.text.toString(),
            primaryDataThirdActivity.et_investment_range.text.toString(),
            primaryDataThirdActivity.et_no_days_traing.text.toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_business_plan),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataThirdActivity.rg_ready_to_invest),
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataThirdActivity.check_planning_investment),
            primaryDataThirdActivity.et_panning_other.text.toString(),
            much_invest,
            validate!!.returnID(
                primaryDataThirdActivity.spin_financial_assistance,
                mstLookupViewModel,
                58,
                iLanguageID
            ),
            source,
            primaryDataThirdActivity.et_source_other.text.toString(),
            validate!!.returnID(
                primaryDataThirdActivity.spin_expected_support,
                mstLookupViewModel,
                52,
                iLanguageID
            ),
            primaryDataThirdActivity.et_any_support_other.text.toString(),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )


    }


    fun update_fourth_data(
        primaryDataFourthActivity: PrimaryDataFourthActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {
        update_primary_fourth(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            validate!!.returnID(
                primaryDataFourthActivity.spin_stage_of_self_employment,
                mstLookupViewModel,
                41,
                iLanguageID
            ),
            primaryDataFourthActivity.et_stage_other.text.toString(),
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataFourthActivity.check_business_invested),
            primaryDataFourthActivity.et_business_invest_other.text.toString(),
            primaryDataFourthActivity.et_how_much_they_invested.text.toString(),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataFourthActivity.rg_business_registered),
            validate!!.returnID(
                primaryDataFourthActivity.spin_source_income,
                mstLookupViewModel,
                54,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataFourthActivity.rg_loans_availed_already),
            validate!!.returnID(
                primaryDataFourthActivity.spin_availed_loan,
                mstLookupViewModel,
                110,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataFourthActivity.rg_expecting_financial_assistance),
            validate!!.returnID(
                primaryDataFourthActivity.spin_amt_financial_asistnc,
                mstLookupViewModel,
                58,
                iLanguageID
            ),
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataFourthActivity.check_expecting_support),
            primaryDataFourthActivity.et_support_other.text.toString(),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )


    }

    fun update_primary_fourth(
        PDCGUID: String,
        Stage_selfemp: Int,
        stage_self_emp_Oth: String,
        Business_type: String,
        business_invested_othr: String,
        Loan_amount: String,
        Business_registered: Int,
        Business_Invest_Source: Int,
        Loan_availed: Int,
        Loan_availed_from: Int,
        Financial_assist: Int,
        Financial_assist_amt: Int,
        Support_Expecting: String,
        Support_Expecting_Oth: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_fourth(
                PDCGUID,
                Stage_selfemp,
                stage_self_emp_Oth,
                Business_type,
                business_invested_othr,
                Loan_amount,
                Business_registered,
                Business_Invest_Source,
                Loan_availed,
                Loan_availed_from,
                Financial_assist,
                Financial_assist_amt,
                Support_Expecting,
                Support_Expecting_Oth,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }


    fun update_primary_third(
        PDCGUID: String,
        Business_Interested: Int,
        business_start: String,
        business_start_othr: String,
        training_skills: Int,
        Business_Training: String,
        business_training_othr: String,
        Business_Investment_Amt: String,
        training_days: String,
        Business_Plan: Int,
        Invest_readiness: Int,
        Invest_Plan: String,
        Invest_Plan_Oth: String,
        Invest_HowMuch: Int,
        Financial_Assistance: Int,
        Loan_Source: Int,
        Loan_Source_Oth: String,
        Invest_support: Int,
        Invest_support_Oth: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_third(
                PDCGUID,
                Business_Interested,
                business_start,
                business_start_othr,
                training_skills,
                Business_Training,
                business_training_othr,
                Business_Investment_Amt,
                training_days,
                Business_Plan,
                Invest_readiness,
                Invest_Plan,
                Invest_Plan_Oth,
                Invest_HowMuch,
                Financial_Assistance,
                Loan_Source,
                Loan_Source_Oth,
                Invest_support,
                Invest_support_Oth,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }

    }

    fun getdatabyGuid(guid: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getdatabyGuid(guid)
    }

    fun getdatabyPDCGuid(guid: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getdatabyPDCGuid(guid)
    }

    fun getMstDist(StateCode: Int, DistrictIn: List<String>): List<MstDistrictEntity> {
        return primaryDataRepository.getMstDist(StateCode, DistrictIn)
    }

    fun getIDPDisData(iPanchayat: Int, idiscode: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getIDPDisData(iPanchayat, idiscode)
    }

    fun getEDPDList(imGUID: String): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getEDPDList(imGUID)
    }

    fun getIDDisWData(idis: Int, izone: Int, iward: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getIDDisWData(idis, izone, iward)
    }

    fun getIDZData(idiscode: Int, izone: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getIDZData(idiscode, izone)
    }

    fun getIDDistrictData(idiscode: Int): LiveData<List<PrimaryDataEntity>> {
        return primaryDataRepository.getIDDistrictData(idiscode)
    }

    fun deleteprimary(primaryDataEntity: PrimaryDataEntity) {
        viewModelScope.launch {
            primaryDataRepository.delete(primaryDataEntity)
        }
    }

    fun getINDIDdata(indGUID: String): List<IndividualProfileEntity> {
        return primaryDataRepository.getINDIDdata(indGUID)
    }

    fun getEdpIDdata(indGUID: String): List<PrimaryDataEntity> {
        return primaryDataRepository.getEdpIDdata(indGUID)
    }

    fun update_fifth_data(
        primaryDataFifthActivity: PrimaryDataFifthActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {
        update_primary_fifth(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            primaryDataFifthActivity.et_business_name.text.toString(),
            validate!!.returnID(
                primaryDataFifthActivity.spin_business_stage,
                mstLookupViewModel,
                126,
                iLanguageID
            ),
            primaryDataFifthActivity.et_business_start_other.text.toString(),
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataFifthActivity.check_investment_mode),
            primaryDataFifthActivity.et_investment_mode_other.text.toString(),
            validate!!.returnIntegerValue(primaryDataFifthActivity.et_amount_invested.text.toString()),
            validate!!.getAgenda(
                primaryDataFifthActivity.et_loan_purpose1,
                primaryDataFifthActivity.et_loan_purpose2,
                primaryDataFifthActivity.et_loan_purpose3
            ),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataFifthActivity.rg_loan_repayment),
            validate!!.returnIntegerValue(primaryDataFifthActivity.et_amount.text.toString()),
            validate!!.returnIntegerValue(primaryDataFifthActivity.et_rate_of_interest.text.toString()),
            validate!!.getAgenda(
                primaryDataFifthActivity.et_repayment_reason1,
                primaryDataFifthActivity.et_repayment_reason2,
                primaryDataFifthActivity.et_repayment_reason3
            ),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )

    }

    fun update_primary_fifth(
        PDCGUID: String,
        Business_Name: String,
        Business_Stage_ID: Int,
        Business_Stage_Othr: String,
        Investment_Mode: String,
        Investment_Mode_Othr: String,
        Self_Amount_Invested: Int,
        Investment_Mode_Purpose: String,
        Loan_Repayment: Int,
        Loan_Amount_Returned: Int,
        Loan_Interest_Returned: Int,
        Non_Repayment_Reason: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_fifth(
                PDCGUID,
                Business_Name,
                Business_Stage_ID,
                Business_Stage_Othr,
                Investment_Mode,
                Investment_Mode_Othr,
                Self_Amount_Invested,
                Investment_Mode_Purpose,
                Loan_Repayment,
                Loan_Amount_Returned,
                Loan_Interest_Returned,
                Non_Repayment_Reason,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }

    fun update_sixth_data(
        primaryDataSixthActivity: PrimaryDataSixthActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {
        update_primary_sixth(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            validate!!.returnID(
                primaryDataSixthActivity.spin_business_support,
                mstLookupViewModel,
                128,
                iLanguageID
            ),
            primaryDataSixthActivity.et_business_support_other.text.toString(),
            validate!!.getAgenda(
                primaryDataSixthActivity.et_asset_support1,
                primaryDataSixthActivity.et_asset_support2,
                primaryDataSixthActivity.et_asset_support3
            ),
            validate!!.getAgenda(
                primaryDataSixthActivity.et_asset_cost1,
                primaryDataSixthActivity.et_asset_cost2,
                primaryDataSixthActivity.et_asset_cost3
            ),
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataSixthActivity.check_training_skill_nature),
            primaryDataSixthActivity.et_training_skill_nature_other.text.toString(),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_total_training_cost.text.toString()),
            validate!!.getAgenda(
                primaryDataSixthActivity.et_training_helped1,
                primaryDataSixthActivity.et_training_helped2,
                primaryDataSixthActivity.et_training_helped3
            ),
            validate!!.returnID(
                primaryDataSixthActivity.spin_market_linkage,
                mstLookupViewModel,
                130,
                iLanguageID
            ),
            primaryDataSixthActivity.et_market_linkage_other.text.toString(),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_cost_involved_market.text.toString()),
            validate!!.returnID(
                primaryDataSixthActivity.spin_branding_support_kind,
                mstLookupViewModel,
                131,
                iLanguageID
            ),
            primaryDataSixthActivity.et_branding_support_other.text.toString(),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_cost_involved_branding.text.toString()),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_expenses_per_month.text.toString()),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_sales_per_month.text.toString()),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_profit_per_month.text.toString()),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_money_invested.text.toString()),
            validate!!.returnIntegerValue(primaryDataSixthActivity.et_net_saving_month.text.toString()),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun update_primary_sixth(
        PDCGUID: String,
        Business_Support_ID: Int,
        Business_Support_Othr: String,
        Asset_Support_Kind: String,
        Cost_Asset: String,
        Skill_Training_Nature: String,
        Skill_Training_Nature_Othr: String,
        Total_Training_Cost: Int,
        Training_Helped_Business: String,
        Market_Linkage_ID: Int,
        Market_Linkage_Othr: String,
        Market_Linkage_Amount: Int,
        Branding_Support_ID: Int,
        Branding_Support_Othr: String,
        Branding_Support_Amount: Int,
        Expenses_Month: Int,
        Sales_Month: Int,
        Profit_Month: Int,
        Money_Invested_Month: Int,
        Net_Saving_Month: Int,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_sixth(
                PDCGUID,
                Business_Support_ID,
                Business_Support_Othr,
                Asset_Support_Kind,
                Cost_Asset,
                Skill_Training_Nature,
                Skill_Training_Nature_Othr,
                Total_Training_Cost,
                Training_Helped_Business,
                Market_Linkage_ID,
                Market_Linkage_Othr,
                Market_Linkage_Amount,
                Branding_Support_ID,
                Branding_Support_Othr,
                Branding_Support_Amount,
                Expenses_Month,
                Sales_Month,
                Profit_Month,
                Money_Invested_Month,
                Net_Saving_Month,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }

    fun update_seventh_data(
        primaryDataSeventhActivity: PrimaryDataSeventhActivity,
        mstLookupViewModel: MstLookupViewModel,
        iLanguageID: Int
    ) {
        update_primary_seventh(
            validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!,
            validate!!.GetAnswerTypeCheckBoxButtonID(primaryDataSeventhActivity.check_finance_support_source),
            validate!!.getAgenda(
                primaryDataSeventhActivity.et_purpose_of_loan1,
                primaryDataSeventhActivity.et_purpose_of_loan2,
                primaryDataSeventhActivity.et_purpose_of_loan3
            ),
            validate!!.returnIntegerValue(primaryDataSeventhActivity.et_loan_amount_taken.text.toString()),
            validate!!.returnIntegerValue(primaryDataSeventhActivity.et_loan_repayment_interest_rate.text.toString()),
            validate!!.GetAnswerTypeRadioButtonID(primaryDataSeventhActivity.rg_business_running_smoothly),
            validate!!.returnID(
                primaryDataSeventhActivity.spin_business_reasons,
                mstLookupViewModel,
                132,
                iLanguageID
            ),
            primaryDataSeventhActivity.et_business_reasons_other.text.toString(),
            validate!!.getAgenda(
                primaryDataSeventhActivity.et_business_attribute1,
                primaryDataSeventhActivity.et_business_attribute2,
                primaryDataSeventhActivity.et_business_attribute3
            ),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )

    }

    fun update_primary_seventh(
        PDCGUID: String,
        Finance_Support_Source: String,
        Finance_Support_Purpose: String,
        Finance_Support_Amount: Int,
        Finance_Support_Interest: Int,
        Business_Running_Smoothly: Int,
        Business_Reason_ID: Int,
        Business_Reason_Othr: String,
        Successful_Business_Attri: String,
        updatedBy: Int?,
        updatedOn: Long?,
        IsEdited: Int
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            primaryDataRepository.update_primary_seventh(
                PDCGUID,
                Finance_Support_Source,
                Finance_Support_Purpose,
                Finance_Support_Amount,
                Finance_Support_Interest,
                Business_Running_Smoothly,
                Business_Reason_ID,
                Business_Reason_Othr,
                Successful_Business_Attri,
                updatedBy,
                updatedOn,
                IsEdited
            )
        }
    }


//    pdcguid,
//    primaryDataFirstActivity.returnIM_GUID(
//    1,
//    hhguid
//    ),
//    hhguid,

}