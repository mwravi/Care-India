package com.careindia.lifeskills.views.householdscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_first.spin_SupervisingFC
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
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

        dataspin_crp =
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
            mstCommonViewModel.getMstCommon(37)

       /* img_back.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
            startActivity(intent)
        }

       /* btn_cancel.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
        }*/

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
            dataspin_crp
        )
        validate!!.fillSpinner(
            this,
            spin_SupervisingFC,
            resources.getString(R.string.select),
            dataspin_supervisingFC
        )
        validate!!.fillSpinner(
            this,
            spin_districtname,
            resources.getString(R.string.select),
            dataspin_district
        )
        validate!!.fillSpinner(
            this,
            spin_zone,
            resources.getString(R.string.select),
            dataspin_zone
        )
        validate!!.fillSpinner(
            this,
            spin_bbmp,
            resources.getString(R.string.select),
            dataspin_ward
        )
        validate!!.fillSpinner(
            this,
            spin_panchayatname,
            resources.getString(R.string.select),
            dataspin_panchayat
        )
        validate!!.fillSpinner(
            this,
            spin_hhSex,
            resources.getString(R.string.select),
            dataspin_hh_sex
        )

    }

    fun fillRadio(){

    }
}