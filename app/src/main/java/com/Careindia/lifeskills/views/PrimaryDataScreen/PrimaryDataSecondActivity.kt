package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.ActivityPrimaryDataSecondBinding
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataSecondBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var primaryDataViewModel: PrimaryDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_second)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        tv_title.text = resources.getString(R.string.primary_data)

        initializeController()

    }

    override fun initializeController() {
        applyClickOnView()
        fillSpinner()
        fillRadio()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
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
        validate!!.fillSpinner(
            this,
            spin_how_much_you_invest,
            resources.getString(R.string.select),
            mstCommonViewModel,
            86
        )
        validate!!.fillSpinner(
            this,
            spin_how_they_planning_investment,
            resources.getString(R.string.select),
            mstCommonViewModel,
            87
        )
        validate!!.fillSpinner(
            this,
            spin_expected_support,
            resources.getString(R.string.select),
            mstCommonViewModel,
            88
        )
    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_bank_account,
            -1,
            mstCommonViewModel,
            82,
            this
        )
        validate!!.fillradio(
            rg_new_business,
            -1,
            mstCommonViewModel,
            83,
            this
        )
        validate!!.fillradio(
            rg_business_plan,
            -1,
            mstCommonViewModel,
            84,
            this
        )
        validate!!.fillradio(
            rg_ready_to_invest,
            -1,
            mstCommonViewModel,
            85,
            this
        )
        validate!!.fillradio(
            rg_availing_loan_subsidies,
            -1,
            mstCommonViewModel,
            89,
            this
        )
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
        } else if (spin_how_much_you_invest.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_how_much_you_invest,
                resources.getString(R.string.please_select_how_much_you_will_invest)
            )
            value = 0
        } else if (spin_how_they_planning_investment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_how_they_planning_investment,
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
}