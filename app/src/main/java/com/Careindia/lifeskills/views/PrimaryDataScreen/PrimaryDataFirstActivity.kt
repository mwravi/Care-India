package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.ActivityPrimaryDataFirstBinding
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataFirstBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_first)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        tv_title.text = resources.getString(R.string.primary_data)

        initializeController()

    }

    override fun initializeController() {
        applyClickOnView()
        bindData()
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

    fun bindData() {

        fillSpinner(
            resources.getString(R.string.select),
            spin_gender,
            5,
            iLanguageID
        )

        fillSpinner(
            resources.getString(R.string.select),
            spin_shg_jlg_cig,
            5,
            iLanguageID
        )

        fillSpinner(
            resources.getString(R.string.select),
            spin_social_category,
            5,
            77
        )

        fillSpinner(
            resources.getString(R.string.select),
            spin_caste_income_certificate,
            5,
            iLanguageID
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

    fun sendData() {
        primaryDataViewModel.collectDataPrimaryFirst(
            validate!!.GetAnswerTypeRadioButtonID(rg_aadhar_card),
            validate!!.GetAnswerTypeRadioButtonID(rg_pan_card)
        )
    }


    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun fillSpinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        mstLookupViewModel!!.getMstLookup(flag, iLanguageID)
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

}