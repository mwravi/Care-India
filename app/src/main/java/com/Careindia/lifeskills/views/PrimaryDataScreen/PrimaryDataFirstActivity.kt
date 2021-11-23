package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataFirstActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_first)
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
                    val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun fillSpinner() {
        validate!!.fillSpinner(
            this,
            spin_gender,
            resources.getString(R.string.select),
            mstCommonViewModel,
            75
        )

        validate!!.fillSpinner(
            this,
            spin_shg_jlg_cig,
            resources.getString(R.string.select),
            mstCommonViewModel,
            76
        )

        validate!!.fillSpinner(
            this,
            spin_social_category,
            resources.getString(R.string.select),
            mstCommonViewModel,
            77
        )

        validate!!.fillSpinner(
            this,
            spin_caste_income_certificate,
            resources.getString(R.string.select),
            mstCommonViewModel,
            78
        )
    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_aadhar_card,
            -1,
            mstCommonViewModel,
            79,
            this
        )
        validate!!.fillradio(
            rg_pan_card,
            -1,
            mstCommonViewModel,
            80,
            this
        )
    }

    private fun checkValidation(): Int {
        var value = 1
        if (et_hh_id.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_hh_id,
                resources.getString(R.string.please_enter_hh_id)
            )
            value = 0
        } else if (et_hh_id.text.toString().length != 14) {
            validate!!.CustomAlertEdit(
                this,
                et_hh_id,
                resources.getString(R.string.please_enter_valid_hh_id)
            )
            value = 0
        } else if (et_im_id.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_im_id,
                resources.getString(R.string.please_enter_im_id)
            )
            value = 0
        } else if (et_im_id.text.toString().length != 14) {
            validate!!.CustomAlertEdit(
                this,
                et_im_id,
                resources.getString(R.string.please_enter_valid_im_id)
            )
            value = 0
        } else if (et_community_name.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_community_name,
                resources.getString(R.string.please_enter_community_name)
            )
            value = 0
        } else if (et_beneficiary_name.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_beneficiary_name,
                resources.getString(R.string.please_enter_beneficiary_name)
            )
            value = 0
        } else if (et_age.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_age,
                resources.getString(R.string.please_enter_age)
            )
            value = 0
        } else if (et_age.text.toString().length < 18 && et_age.text.toString().length > 65) {
            validate!!.CustomAlertEdit(
                this,
                et_age,
                resources.getString(R.string.please_enter_valid_age)
            )
            value = 0
        } else if (spin_gender.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_gender,
                resources.getString(R.string.please_select_gender)
            )
            value = 0
        } else if (et_contact_no.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_contact_no,
                resources.getString(R.string.please_enter_contact_number)
            )
            value = 0
        } else if (validate!!.checkmobileno(et_contact_no.text.toString()) == 0
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_contact_no,
                resources.getString(R.string.please_enter_valid_contact_number)
            )
            value = 0
        } else if (spin_shg_jlg_cig.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_shg_jlg_cig,
                resources.getString(R.string.please_select_member_shg_jlg_clg)
            )
            value = 0
        } else if (spin_social_category.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_social_category,
                resources.getString(R.string.please_select_social_category)
            )
            value = 0
        } else if (spin_caste_income_certificate.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_caste_income_certificate,
                resources.getString(R.string.please_select_caste_and_income_certificate)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_aadhar_card) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_aadhar_card)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_pan_card) == 0) {
            validate!!.CustomAlert(this, resources.getString(R.string.please_select_pan_card))
            value = 0
        }
        return value
    }

    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataListActivity::class.java)
        startActivity(intent)
        finish()
    }

}