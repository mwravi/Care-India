package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityThird : AppCompatActivity() {

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
            if (CheckValidation() == 0) {
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
            spin_member_sex,
            resources.getString(R.string.select),
            mstCommonViewModel,
            10
        )
        validate!!.fillSpinner(
            this,
            spin_savings_account,
            resources.getString(R.string.select),
            mstCommonViewModel,
            11
        )
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



    }

    fun CheckValidation(): Int {

        var iValue = 0;

        if (et_member_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_member),
            )
        } else if (et_member_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.individual_member_id),
            )
        } else if (spin_member_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_member_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_the_member),
            )
        } else if (et_member_age.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.age_of_the_member),
            )
        } else if (validate!!.returnIntegerValue(et_member_age.text.toString()) < 18 || validate!!.returnIntegerValue(
                et_member_age.text.toString()
            ) > 65
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.valid_age_of_the_member),
            )
        } else if (et_contact_number.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_contact_number,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.contact_number),
            )
        } else if (validate!!.checkmobileno(et_contact_number.text.toString()) == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_contact_number,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.valid_mobile_no),
            )
        } else if (et_role_of_member.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_role_of_member,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.role_of_member_in_group),
            )
        } else if (spin_savings_account.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_savings_account,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_savings_bank_account),
            )
        } else if (et_tenure.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_tenure,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q301_what_is_the_tenure_of_the_president_office_bearer_in_complete_years),
            )
        } else if (validate!!.returnIntegerValue(et_tenure.text.toString()) < 1 || validate!!.returnIntegerValue(
                et_tenure.text.toString()
            ) > 5
        ) {
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
        } else if (et_bearer_happens.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q304_in_what_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        } else if (validate!!.returnIntegerValue(et_bearer_happens.text.toString()) < 1 || validate!!.returnIntegerValue(
                et_bearer_happens.text.toString()
            ) > 10
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.valid_frequency_the_election_for_office_bearer_happens_in_complete_years),
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