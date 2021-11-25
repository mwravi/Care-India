package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
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

        et_date_of_group_formation.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_group_formation
            )


        }
        btn_save.setOnClickListener {
           val intent = Intent(this, CollectiveProfileActivityThird::class.java)
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
            spin_collective_group,
            resources.getString(R.string.select),
            mstCommonViewModel,
            7
        )

        validate!!.fillSpinner(
            this,
            spin_group_registered,
            resources.getString(R.string.select),
            mstCommonViewModel,
            8
        )
        validate!!.fillSpinner(
            this,
            spin_head_group_sex,
            resources.getString(R.string.select),
            mstCommonViewModel,
            9
        )


    }

    fun CheckValidation():Int {
        var sumofMember = validate!!.returnIntegerValue(et_male_members.text.toString())+validate!!.returnIntegerValue(et_female_members.text.toString())+
        validate!!.returnIntegerValue(et_transgender_members.text.toString())

        var iValue = 0;
         if (et_date_of_group_formation.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_group_formation,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_formation_of_group),
            )
        } else if (spin_collective_group.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collective_group,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q112_what_type_of_collective_group_it_is),
            )
        } else if (et_specify_others_group.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_others_group,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others),
            )
        }else if (spin_group_registered.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_group_registered,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_your_griup_registered),
            )
        } else if (et_specify_others_group_registered.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_others_group_registered,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others_registered),
            )
        } else if (et_head_group_name.text.toString().length == 0) {
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
        }   else if (validate!!.returnIntegerValue(et_total_no_of_members.text.toString())!=sumofMember) {
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