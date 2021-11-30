package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileFourthBinding
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_fourth.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileFourthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileFourthBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_fourth)
        validate = Validate(this)
        tv_title.text = "IM Profile"

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val improfileRepository = IndividualProfileRepository(improfiledao!!, commondao!!)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this


        initializeController()

    }

    override fun initializeController() {
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        //apply click on view
        applyClickOnView()


        validate!!.fillradio(
            rg_have_adhar,
            -1,
            mstCommonViewModel,
            62,
            this
        )
        validate!!.fillradio(
            rg_have_voter,
            -1,
            mstCommonViewModel,
            63,
            this
        )
        validate!!.fillradio(
            rg_have_pan,
            -1,
            mstCommonViewModel,
            64,
            this
        )
        validate!!.fillradio(
            rg_have_income,
            -1,
            mstCommonViewModel,
            65,
            this
        )
        validate!!.fillradio(
            rg_have_caste,
            -1,
            mstCommonViewModel,
            66,
            this
        )
        validate!!.fillradio(
            rg_svg_bank_act,
            -1,
            mstCommonViewModel,
            67,
            this
        )
        validate!!.fillradio(
            rg_availed_services_past,
            -1,
            mstCommonViewModel,
            68,
            this
        )


    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.setOnClickListener(this)
        btn_save.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateForthProfileData()
                    val intent = Intent(this, IMProfileFifthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }

    }

    fun sendData() {
        imProfileViewModel.collectiveProfileForthData(
            validate!!.GetAnswerTypeRadioButtonID(rg_have_adhar),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_voter),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_pan),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_income),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_caste),
            validate!!.GetAnswerTypeRadioButtonID(rg_svg_bank_act),
            validate!!.GetAnswerTypeRadioButtonID(rg_availed_services_past)
        )
    }

    private fun checkValidation(): Int {
        var value = 1

        if (et_days_secondary_job.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_days_secondary_job,
                resources.getString(R.string.plz_select_wrking_days_months)
            )
            value = 0
        } else if (Integer.parseInt(et_days_secondary_job.text.toString()) < 1 || Integer.parseInt(
                et_days_secondary_job.text.toString()
            ) > 29
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_days_secondary_job,
                resources.getString(R.string.please_entr_input_months)
            )
            value = 0
        } else if (et_avg_daily_secondry_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_avg_daily_secondry_income,
                resources.getString(R.string.plz_select_avg_daily_socndry_incm)
            )
            value = 0
        } else if (Integer.parseInt(et_avg_daily_secondry_income.text.toString()) < 50 || Integer.parseInt(
                et_avg_daily_secondry_income.text.toString()
            ) > 9999
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_avg_daily_secondry_income,
                resources.getString(R.string.please_entr_input_daily)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_have_adhar) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_adhar)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_have_voter) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_icard)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_have_pan) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_pan)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_have_income) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_incm_certict)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_have_caste) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_caste_certict)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_svg_bank_act) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_bank_acct)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_availed_services_past) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_services_past_month)
            )
            value = 0

        } else if (et_service_provider_department.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_service_provider_department,
                resources.getString(R.string.plz_detail_srvic_provider)
            )
            value = 0

        }
        return value
    }
}