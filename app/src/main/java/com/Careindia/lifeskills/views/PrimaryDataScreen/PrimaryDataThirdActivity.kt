package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataThirdActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_third)
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

                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun fillSpinner() {
        validate!!.fillSpinner(
            this,
            spin_stage_of_self_employment,
            resources.getString(R.string.select),
            mstCommonViewModel,
            91
        )
        validate!!.fillSpinner(
            this,
            spin_self_employed_support,
            resources.getString(R.string.select),
            mstCommonViewModel,
            94
        )
    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_business_registered,
            -1,
            mstCommonViewModel,
            90,
            this
        )
        validate!!.fillradio(
            rg_loans_availed_already,
            -1,
            mstCommonViewModel,
            92,
            this
        )
        validate!!.fillradio(
            rg_expecting_financial_assistance,
            -1,
            mstCommonViewModel,
            93,
            this
        )

    }

    private fun checkValidation(): Int {
        var value = 1
        if (et_what_kind_of_business.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_what_kind_of_business,
                resources.getString(R.string.please_enter_what_kind_of_business_it_is)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_business_registered) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_is_the_business_registered)
            )
            value = 0
        } else if (spin_stage_of_self_employment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_stage_of_self_employment,
                resources.getString(R.string.please_select_stage_of_self_employment_enterprise_idea)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_loans_availed_already) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_are_they_availing_loans_already)
            )
            value = 0
        } else if (et_from_where_availed_loans.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_from_where_availed_loans,
                resources.getString(R.string.please_enter_from_where_have_availed_loans)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_expecting_financial_assistance) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_are_they_expecting_any_financial_assistance)
            )
            value = 0
        } else if (et_financial_assistance_required.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_financial_assistance_required,
                resources.getString(R.string.please_enter_amount_of_financial_assistance_required)
            )
            value = 0
        } else if (spin_self_employed_support.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_self_employed_support,
                resources.getString(R.string.please_select_Those_self_employed_require_any_support)
            )
            value = 0
        }
        return value
    }

    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataSecondActivity::class.java)
        startActivity(intent)
        finish()
    }
}