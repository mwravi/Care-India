package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileThirdActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_third)
        validate = Validate(this)
        tv_title.text = "IM Profile"
        initializeController()
    }

    override fun initializeController() {
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        //apply click on view
        applyClickOnView()

        validate!!.fillSpinner(
            this,
            spin_secondary_income,
            resources.getString(R.string.select),
            mstCommonViewModel,
            60
        )
        validate!!.fillSpinner(
            this,
            spin_what_secondary_income,
            resources.getString(R.string.select),
            mstCommonViewModel,
            61
        )
        validate!!.fillSpinner(
            this,
            spin_have_adhar,
            resources.getString(R.string.select),
            mstCommonViewModel,
            62
        )

        validate!!.fillSpinner(
            this,
            spin_have_voter,
            resources.getString(R.string.select),
            mstCommonViewModel,
            63
        )
        validate!!.fillSpinner(
            this,
            spin_have_pan,
            resources.getString(R.string.select),
            mstCommonViewModel,
            64
        )
        validate!!.fillSpinner(
            this,
            spin_have_income,
            resources.getString(R.string.select),
            mstCommonViewModel,
            65
        )
        validate!!.fillSpinner(
            this,
            spin_have_caste,
            resources.getString(R.string.select),
            mstCommonViewModel,
            66
        )
        validate!!.fillSpinner(
            this,
            spin_svg_bank_act,
            resources.getString(R.string.select),
            mstCommonViewModel,
            67
        )
        validate!!.fillSpinner(
            this,
            spin_availed_services_past,
            resources.getString(R.string.select),
            mstCommonViewModel,
            68
        )
        validate!!.fillSpinner(
            this,
            spin_avail_any_scheme,
            resources.getString(R.string.select),
            mstCommonViewModel,
            69
        )
        validate!!.fillSpinner(
            this,
            spin_new_jobs_business,
            resources.getString(R.string.select),
            mstCommonViewModel,
            69
        )
        validate!!.fillSpinner(
            this,
            spin_alternative_get_opportunity,
            resources.getString(R.string.select),
            mstCommonViewModel,
            72
        )
        validate!!.fillSpinner(
            this,
            spin_member_cig_shg,
            resources.getString(R.string.select),
            mstCommonViewModel,
            68
        )


//        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))


        validate!!.fillCheckBoxes(
            this,
            skills_jobs_picking,
            resources.getStringArray(R.array.skills_jobs_picking)
        )

    }


    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)

    }

    private fun checkValidation(): Int {
        var value = 1

        if (et_no_days_job.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_no_days_job,
                resources.getString(R.string.plz_select_working_days)
            )
            value = 0
        } else if (et_avg_daily_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_avg_daily_income,
                resources.getString(R.string.plz_select_avg_daily_income)
            )
            value = 0

        } else if (spin_secondary_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_secondary_income,
                resources.getString(R.string.plz_select_mobiledata)
            )
            value = 0
        } else if (spin_what_secondary_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_what_secondary_income,
                resources.getString(R.string.plz_select_secondary_income)
            )
            value = 0

        } else if (et_specify_source_secondary_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_source_secondary_income,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (et_days_secondary_job.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_days_secondary_job,
                resources.getString(R.string.plz_select_wrking_days_months)
            )
            value = 0
        } else if (et_avg_daily_secondry_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_avg_daily_secondry_income,
                resources.getString(R.string.plz_select_avg_daily_socndry_incm)
            )
            value = 0

        } else if (spin_have_adhar.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_have_adhar,
                resources.getString(R.string.plz_ans_adhar)
            )
            value = 0
        } else if (spin_have_voter.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_have_voter,
                resources.getString(R.string.plz_ans_icard)
            )
            value = 0
        } else if (spin_have_pan.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_have_pan,
                resources.getString(R.string.plz_ans_pan)
            )
            value = 0
        } else if (spin_have_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_have_income,
                resources.getString(R.string.plz_ans_incm_certict)
            )
            value = 0
        } else if (spin_have_caste.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_have_caste,
                resources.getString(R.string.plz_ans_caste_certict)
            )
            value = 0
        } else if (spin_svg_bank_act.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_svg_bank_act,
                resources.getString(R.string.plz_ans_bank_acct)
            )
            value = 0
        } else if (spin_availed_services_past.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_availed_services_past,
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

        } else if (spin_avail_any_scheme.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_avail_any_scheme,
                resources.getString(R.string.plz_select_availing_scheme)
            )
            value = 0


        } else if (et_details_service_avail.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_details_service_avail,
                resources.getString(R.string.plz_select_waste_pick_skills)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(skills_jobs_picking).isEmpty()) {
            validate!!.CustomAlertCheckbox(
                this,
                skills_jobs_picking,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
        } else if (et_skills_jobs_picking.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_skills_jobs_picking,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0

        } else if (spin_new_jobs_business.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_new_jobs_business,
                resources.getString(R.string.plz_select_interested_newjob)
            )
            value = 0
        } else if (spin_alternative_get_opportunity.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_alternative_get_opportunity,
                resources.getString(R.string.plz_select_alternative_jobbussi)
            )
            value = 0
        } else if (spin_member_cig_shg.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_member_cig_shg,
                resources.getString(R.string.plz_select_member_cig_shg)
            )
            value = 0

        } else if (et_name_collective_part.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_name_collective_part,
                resources.getString(R.string.plz_select_name_coltiv_part)
            )
            value = 0

        }
        return value
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    val intent = Intent(this, HomeDashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}