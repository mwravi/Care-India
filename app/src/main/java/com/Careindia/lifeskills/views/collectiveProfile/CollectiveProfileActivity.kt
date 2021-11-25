package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.IOException
import java.io.InputStream
import com.google.gson.Gson
import kotlinx.android.synthetic.main.buttons_save_cancel.*


class CollectiveProfileActivity : AppCompatActivity() {

    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_first)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {

        btn_save.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            if (CheckValidation()==0) {
                startActivity(intent)
                finish()
            }
        }

        et_date_of_filling.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_filling
            )
        }


        validate!!.fillSpinner(
            this,
            spin_name_of_crp,
            resources.getString(R.string.select),
            mstCommonViewModel,
            1
        )

        validate!!.fillSpinner(
            this,
            spin_name_of_supervising,
            resources.getString(R.string.select),
            mstCommonViewModel,
            2
        )
        validate!!.fillSpinner(
            this,
            spin_district_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            3
        )

        validate!!.fillSpinner(
            this,
            spin_zone_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            4
        )

        validate!!.fillSpinner(
            this,
            spin_ward_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            5
        )
        validate!!.fillSpinner(
            this,
            spin_panchayat_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            6
        )

    }

        fun CheckValidation():Int {
            var iValue = 0;
            if (et_date_of_filling.text.toString().length == 0) {
                iValue = 1
                validate!!.CustomAlertEdit(
                    this,
                    et_date_of_filling,
                    resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form_profile),
                )
            } else if (spin_name_of_crp.selectedItemPosition == 0) {
                iValue = 1
                validate!!.CustomAlertSpinner(
                    this,
                    spin_name_of_crp,
                    resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_crp_filling),
                )
            } else if (spin_name_of_supervising.selectedItemPosition == 0) {
                iValue = 1
                validate!!.CustomAlertSpinner(
                    this,
                    spin_name_of_supervising,
                    resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_supervising_care_india),
                )
            } else if (spin_district_name.selectedItemPosition == 0) {
                iValue = 1
                validate!!.CustomAlertSpinner(
                    this,
                    spin_district_name,
                    resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_district),
                )
            }else if (spin_zone_name.selectedItemPosition == 0) {
                iValue = 1
                validate!!.CustomAlertSpinner(
                    this,
                    spin_zone_name,
                    resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
                )
            } else if (spin_ward_name.selectedItemPosition == 0) {
                iValue = 1
                validate!!.CustomAlertSpinner(
                    this,
                    spin_ward_name,
                    resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_bbmp_ward),
                )
            } else if (spin_panchayat_name.selectedItemPosition == 0) {
                iValue = 1
                validate!!.CustomAlertSpinner(
                    this,
                    spin_panchayat_name,
                    resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_panchayat),
                )
            } else if (et_locality_name.text.toString().length == 0) {
                iValue = 1
                validate!!.CustomAlertEdit(
                    this,
                    et_locality_name,
                    resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_locality),
                )
            } else if (et_collective_id.text.toString().length == 0) {
                iValue = 1
                validate!!.CustomAlertEdit(
                    this,
                    et_collective_id,
                    resources.getString(R.string.please_enter) + " " + resources.getString(R.string.collective_unique_id),
                )
            }else if (et_group_collective_name.text.toString().length == 0) {
                iValue = 1
                validate!!.CustomAlertEdit(
                    this,
                    et_group_collective_name,
                    resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_group_collective_sangha),
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