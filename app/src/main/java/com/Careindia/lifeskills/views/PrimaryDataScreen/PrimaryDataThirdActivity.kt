package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.householdscreen.HouseholdProfileListActivity
import kotlinx.android.synthetic.main.activity_primary_data_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataThirdActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_self_employment: List<MstCommonEntity>? = null
    var dataspin_require_support: List<MstCommonEntity>? = null
    var dataspin_business_registered: List<MstCommonEntity>? = null
    var dataspin_availed_loan: List<MstCommonEntity>? = null
    var dataspin_financial_assistance: List<MstCommonEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_third)
        validate = Validate(this)

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        tv_title.text = "Primary Data"

        dataspin_self_employment =
            mstCommonViewModel.getMstCommon(91)
        dataspin_require_support =
            mstCommonViewModel.getMstCommon(94)
        dataspin_business_registered =
            mstCommonViewModel.getMstCommon(90)
        dataspin_availed_loan =
            mstCommonViewModel.getMstCommon(92)
        dataspin_financial_assistance =
            mstCommonViewModel.getMstCommon(93)

        /*img_back.setOnClickListener {
            val intent = Intent(this, PrimaryDataSecondActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*btn_cancel.setOnClickListener {
            val intent = Intent(this, PrimaryDataSecondActivity::class.java)
            startActivity(intent)
        }*/

        fillSpinner()
        fillRadio()
    }

    fun fillSpinner() {
        validate!!.fillSpinner(
            this,
            spin_stage_of_self_employment,
            resources.getString(R.string.select),
            dataspin_self_employment
        )
        validate!!.fillSpinner(
            this,
            spin_self_employed_support,
            resources.getString(R.string.select),
            dataspin_require_support
        )
    }

    fun fillRadio() {
        validate!!.dynamicRadio(
            this,
            rg_business_registered,
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.dynamicRadio(
            this,
            rg_loans_availed_already,
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.dynamicRadio(
            this,
            rg_expecting_financial_assistance,
            resources.getStringArray(R.array.yes_no)
        )

    }

    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataSecondActivity::class.java)
        startActivity(intent)
        finish()
    }
}