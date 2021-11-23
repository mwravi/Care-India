package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivitythird : AppCompatActivity() {

    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_third)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        tv_title.text = "Collective Profile"
        initCall()

    }

    fun initCall() {
        btn_save.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
             if (CheckValidation()==0) {
            startActivity(intent)
            finish()
              }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
            finish()
        }

        validate!!.fillSpinner(
            this,
            spin_rotation_of_roles,
            resources.getString(R.string.select),
            mstCommonViewModel,
            12
        )
        validate!!.fillSpinner(
            this,
        spin_office_bearer,
            resources.getString(R.string.select),
            mstCommonViewModel,
            13
        )
        validate!!.fillSpinner(
            this,
            spin_bank_account,
            resources.getString(R.string.select),
            mstCommonViewModel,
            14
        )

        validate!!.fillSpinner(
            this,
            spin_group_savings,
            resources.getString(R.string.select),
            mstCommonViewModel,
            15
        )

        validate!!.fillSpinner(
            this,
            spin_frequency_group_savings,
            resources.getString(R.string.select),
            mstCommonViewModel,
            16
        )

        validate!!.fillSpinner(
            this,
            spin_regular_savings,
            resources.getString(R.string.select),
            mstCommonViewModel,
            17
        )
        validate!!.fillSpinner(
            this,
            spin_avial_loan,
            resources.getString(R.string.select),
            mstCommonViewModel,
            19
        )
        validate!!.fillSpinner(
            this,
            spin_easily_avial_loan,
            resources.getString(R.string.select),
            mstCommonViewModel,
            19
        )


    }

    fun CheckValidation():Int {

        var iValue = 0;
        if (et_tenure.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_tenure,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q301_what_is_the_tenure_of_the_president_office_bearer_in_complete_years),
            )
        } else if(validate!!.returnIntegerValue(et_tenure.text.toString())<1 || validate!!.returnIntegerValue(et_tenure.text.toString())>5) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_tenure,
                resources.getString(R.string.valid_tenure_of_the_president_office_bearer_in_complete_years),
            )
        } else if (spin_rotation_of_roles.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_rotation_of_roles,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q302_has_there_been_any_rotation_of_the_roles_of_the_member_in_last_one_year),
            )
        } else if (spin_office_bearer.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_office_bearer,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q303_has_there_been_any_election_for_the_office_bearer),
            )
        }  else if (et_bearer_happens.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q304_in_what_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        } else if (validate!!.returnIntegerValue(et_bearer_happens.text.toString())<1 || validate!!.returnIntegerValue(et_bearer_happens.text.toString())>10) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.valid_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        }  else if (spin_bank_account.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bank_account,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q305_does_your_sangha_have_a_common_group_bank_account),
            )
        } else if (et_cbank_account.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_cbank_account,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q305a_what_are_the_challenges_in_opening_bank_account),
            )
        } else if (spin_group_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_group_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q306_how_much_does_each_member_contribute_to_group_savings),
            )
        } else if (et_other_inr.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_inr,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q306a_please_specify_the_others_in_inr),
            )
        } else if (spin_frequency_group_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_frequency_group_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q307_in_what_frequency_the_members_contribute_to_the_group_savings),
            )
        }else if (et_other_frequency.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_frequency,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q307a_please_specify_others),
            )
        } else if (spin_regular_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_regular_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q308_are_the_members_regularly_saving_as_per_the_group_decision),
            )
        } else if (et_other_frequency1.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_frequency1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q308a_please_specify_others),
            )
        } else if (spin_easily_avial_loan.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_easily_avial_loan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q309_are_members_avail_the_loan),
            )
        } else if (spin_avial_loan.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_avial_loan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q309a_from_where_the_members_avail_the_loan),
            )
        } else if (et_other_specify_q309b.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_specify_q309b,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q309b_please_specify_others),
            )
        } else if (et_challenges.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_challenges,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q310_what_are_the_challenges_in_availing_loan_minimum_1_and_maximum_upto_3_responses_possible),
            )
        }
        return iValue;
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}