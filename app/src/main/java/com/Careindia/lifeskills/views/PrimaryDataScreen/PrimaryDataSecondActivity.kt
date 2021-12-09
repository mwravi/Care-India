package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataSecondBinding
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataSecondBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_second)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)
        )[PrimaryDataViewModel::class.java]
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        tv_title.text = resources.getString(R.string.primary_data)

        initializeController()
        fillSpinner()
        if(validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.trim().length>0) {
            showLiveData()
        }

    }

    override fun initializeController() {
        applyClickOnView()

    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    primaryDataViewModel.update_second_data(this,mstLookupViewModel,iLanguageID)
                    val intent = Intent(this, PrimaryDataThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataFirstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun fillSpinner() {


        fill_Spinner(
            resources.getString(R.string.select),
            spin_how_much_invest,
            30,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_planning_investment,
            31,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_expected_support,
            32,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_which_source,
            32,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_bank_account,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_new_business,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_business_plan,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_ready_to_invest,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_availing_loan_subsidies,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)
    }


    private fun checkValidation(): Int {
        var value = 1
        if (validate!!.GetAnswerTypeRadioButtonID(rg_bank_account) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_bank_account)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_new_business) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_new_business)
            )
            value = 0
        } else if (et_kind_of_business.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_kind_of_business,
                resources.getString(R.string.please_enter_kind_of_business)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_business_plan) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_business_plan)
            )
            value = 0
        } else if (et_investment_range.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_investment_range,
                resources.getString(R.string.please_enter_range_of_investment)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_ready_to_invest) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_ready_to_invest)
            )
            value = 0
        } else if (spin_how_much_invest.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_how_much_invest,
                resources.getString(R.string.please_select_how_much_you_will_invest)
            )
            value = 0
        } else if (spin_planning_investment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_planning_investment,
                resources.getString(R.string.please_select_how_they_planning_their_investment)
            )
            value = 0
        } else if (et_financial_assistance.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_financial_assistance,
                resources.getString(R.string.please_enter_how_much_financial_assistance_is_required)
            )
            value = 0
        } else if (spin_expected_support.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_expected_support,
                resources.getString(R.string.please_select_expected_support)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_availing_loan_subsidies) == 0) {
            validate!!.CustomAlert(this, resources.getString(R.string.please_select_availing_loans))
            value = 0
        } else if (et_how_much_they_invested.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_how_much_they_invested,
                resources.getString(R.string.please_enter_how_much_they_have_invested)
            )
            value = 0
        }
        return value
    }

    fun sendData() {
        primaryDataViewModel.collectDataPrimarySecond(
            validate!!.GetAnswerTypeRadioButtonID(rg_bank_account),
            validate!!.GetAnswerTypeRadioButtonID(rg_new_business),
            validate!!.GetAnswerTypeRadioButtonID(rg_business_plan),
            validate!!.GetAnswerTypeRadioButtonID(rg_ready_to_invest),
            validate!!.GetAnswerTypeRadioButtonID(rg_availing_loan_subsidies)
        )
    }

    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataFirstActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun fill_Spinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        mstLookupViewModel.getMstLookup(flag, iLanguageID)
            .observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    val iGen = it.size
                    val name = arrayOfNulls<String>(iGen + 1)
                    name[0] = strValue

                    for (i in 0 until it.size) {
                        name[i + 1] = it.get(i).Description
                    }
                    val adapter_category = ArrayAdapter<String>(
                        this,
                        R.layout.my_spinner_space_dashboard, name
                    )
                    adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                    spin.adapter = adapter_category
                }
            })

    }

    fun showLiveData() {
        val primaryGuid = validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)
        if (primaryGuid != null) {
            primaryDataViewModel.getdatabyPDCGuid(primaryGuid).observe(this, Observer {
                if (it != null && it.size>0) {
                    //et_collection_date.setText(validate!!.returnStringValue(it.get(0).Dateform))
                    // et_crp_name.setText(validate!!.returnStringValue(it.get(0).Dateform))
                    //et_supervisor.setText(validate!!.returnStringValue(it.get(0).Dateform))

                    validate!!.SetAnswerTypeRadioButton(rg_bank_account,it.get(0).ValidBank)
                    validate!!.SetAnswerTypeRadioButton(rg_new_business,it.get(0).Business_Interested)
                    et_kind_of_business.setText(it.get(0).Business_details)
                    validate!!.SetAnswerTypeRadioButton(rg_business_plan,it.get(0).Business_Plan)
                    et_investment_range.setText(it.get(0).Business_investment.toString())
                    validate!!.SetAnswerTypeRadioButton(rg_ready_to_invest,it.get(0).Invest_readiness)

                    spin_how_much_invest.setSelection(validate!!.returnpos(it.get(0).Invest_source,mstLookupViewModel,30,iLanguageID))
                    spin_planning_investment.setSelection(validate!!.returnpos(it.get(0).Invest_finance,mstLookupViewModel,31,iLanguageID))
                    et_financial_assistance.setText(it.get(0).Invest_support)
                    spin_expected_support.setSelection(validate!!.returnpos(it.get(0).Loan_interested,mstLookupViewModel,32,iLanguageID))
                    validate!!.SetAnswerTypeRadioButton(rg_availing_loan_subsidies,it.get(0).Loan_amount)



                }
            })
        }

    }

}