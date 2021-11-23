package com.careindia.lifeskills.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_first.et_formfillingDate
import kotlinx.android.synthetic.main.activity_household_profile_first.spin_SupervisingFC
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileFirstActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_crp: List<MstCommonEntity>? = null
    var dataspin_supervisingFC: List<MstCommonEntity>? = null
    var dataspin_district: List<MstCommonEntity>? = null
    var dataspin_zone: List<MstCommonEntity>? = null
    var dataspin_ward: List<MstCommonEntity>? = null
    var dataspin_panchayat: List<MstCommonEntity>? = null
    var dataspin_hh_sex: List<MstCommonEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_household_profile_first)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        tv_title.text = "Household Profile"


       /* dataspin_crp =
            mstCommonViewModel.getMstCommon(31)
        dataspin_supervisingFC =
            mstCommonViewModel.getMstCommon(32)
        dataspin_district =
            mstCommonViewModel.getMstCommon(33)
        dataspin_zone =
            mstCommonViewModel.getMstCommon(34)
        dataspin_ward =
            mstCommonViewModel.getMstCommon(35)
        dataspin_panchayat =
            mstCommonViewModel.getMstCommon(36)
        dataspin_hh_sex =
            mstCommonViewModel.getMstCommon(37)*/

       /* img_back.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
            if (CheckValidation()==0) {
                startActivity(intent)
            }
        }

        btn_prev.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
        }

        et_formfillingDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfillingDate
            )
        }
            fillSpinner()
            fillRadio()

    }

    fun fillSpinner(){

        validate!!.fillSpinner(
            this,
            spin_crpfillingform,
            resources.getString(R.string.select),
            mstCommonViewModel,
            1
        )
        validate!!.fillSpinner(
            this,
            spin_SupervisingFC,
            resources.getString(R.string.select),
            mstCommonViewModel,
            2
        )
        validate!!.fillSpinner(
            this,
            spin_districtname,
            resources.getString(R.string.select),
            mstCommonViewModel,
            3
        )
        validate!!.fillSpinner(
            this,
            spin_zone,
            resources.getString(R.string.select),
            mstCommonViewModel,
            4
        )
        validate!!.fillSpinner(
            this,
            spin_bbmp,
            resources.getString(R.string.select),
            mstCommonViewModel,
            5
        )
        validate!!.fillSpinner(
            this,
            spin_panchayatname,
            resources.getString(R.string.select),
            mstCommonViewModel,
            6
        )
        validate!!.fillSpinner(
            this,
            spin_hhSex,
            resources.getString(R.string.select),
            mstCommonViewModel,
            10
        )

    }

    fun fillRadio(){

    }

    fun CheckValidation():Int {
        var iValue = 0;
         if (et_formfillingDate.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_formfillingDate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form),
            )
        } else if (spin_crpfillingform.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_crpfillingform,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_CRP_filling_the_form),
            )
        } else if (spin_SupervisingFC.selectedItemPosition == 0) {
             iValue = 1
             validate!!.CustomAlertSpinner(
                 this,
                 spin_SupervisingFC,
                 resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_supervising_field_cordinator),
             )
         } else if (spin_districtname.selectedItemPosition == 0) {
             iValue = 1
             validate!!.CustomAlertSpinner(
                 this,
                 spin_districtname,
                 resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_distric),
             )
         }else if (spin_zone.selectedItemPosition == 0) {
             iValue = 1
             validate!!.CustomAlertSpinner(
                 this,
                 spin_zone,
                 resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
             )
         } else if (spin_bbmp.selectedItemPosition == 0) {
             iValue = 1
             validate!!.CustomAlertSpinner(
                 this,
                 spin_bbmp,
                 resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward),
             )
         } else if (spin_panchayatname.selectedItemPosition == 0) {
             iValue = 1
             validate!!.CustomAlertSpinner(
                 this,
                 spin_panchayatname,
                 resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_panchayat),
             )
         } else if (et_localityname.text.toString().length == 0) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_localityname,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q108_name_of_locality),
             )
         } else if (et_hh_unique_id.text.toString().length == 0) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_hh_unique_id,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hhh_unique_id),
             )
         } else if (et_hhName.text.toString().length == 0) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_hhName,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hh_name),
             )
         } else if (spin_hhSex.selectedItemPosition == 0) {
             iValue = 1
             validate!!.CustomAlertSpinner(
                 this,
                 spin_hhSex,
                 resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_sex),
             )
         } else if (et_totalAdult.text.toString().length == 0 ) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_totalAdult,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_adult),
             )
         } else if (et_adultMale.text.toString().length == 0 ) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_adultMale,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adult_male),
             )
         }  else if (et_adultFemale.text.toString().length == 0) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_adultFemale,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adult_female),
             )
         }else if (validate!!.returnIntegerValue(et_adultFemale.text.toString())+validate!!.returnIntegerValue(et_adultMale.text.toString())!=validate!!.returnIntegerValue(et_totalAdult.text.toString())) {
             iValue = 1
             validate!!.CustomAlertEdit(
                 this,
                 et_totalAdult,
                 resources.getString(R.string.adult_female_and_adult_male_total),
             )
         }
        return iValue;
    }
}