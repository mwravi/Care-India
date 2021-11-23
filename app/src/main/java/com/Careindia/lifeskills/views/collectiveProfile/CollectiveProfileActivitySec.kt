package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivitySec : AppCompatActivity() {

    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_second)
        validate= Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {
        btn_save.setOnClickListener {
           val intent = Intent(this, CollectiveProfileActivitythird::class.java)
            if (CheckValidation()==0) {
                startActivity(intent)
                finish()
           }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        validate!!.fillSpinner(
            this,
            spin_head_group_sex,
            resources.getString(R.string.select),
            mstCommonViewModel,
            9
        )

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
    }

    fun CheckValidation():Int {
        var sumofMember = validate!!.returnIntegerValue(et_male_members.text.toString())+validate!!.returnIntegerValue(et_female_members.text.toString())+
        validate!!.returnIntegerValue(et_transgender_members.text.toString())

        var iValue = 0;
        if (et_head_group_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_head_group_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_head_group),
            )
        } else if (spin_head_group_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_head_group_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_head_of_group),
            )
        }  else if (et_total_no_of_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_no_of_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_members_in_group),
            )
        } else if (et_male_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_male_members_in_group),
            )
        }else if (et_female_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_female_members_in_group),
            )
        } else if (et_transgender_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_transgender_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_transgender_members_in_group),
            )
        }  else if (et_member_name.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_member),
            )
        } else if (et_member_id.text.toString().length == 0 ) {
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
        }else if (et_member_age.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.age_of_the_member),
            )
        } else if (validate!!.returnIntegerValue(et_member_age.text.toString())<18 || validate!!.returnIntegerValue(et_member_age.text.toString())>65) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.valid_age_of_the_member),
            )
        } else if (et_contact_number.text.toString().length == 0 ) {
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
        }else if (et_role_of_member.text.toString().length == 0 ) {
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
        }  else if (validate!!.returnIntegerValue(et_total_no_of_members.text.toString())!=sumofMember) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_no_of_members,
                resources.getString(R.string.sum_of_collective_member),
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