package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataSecondActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_invest: List<MstCommonEntity>? = null
    var dataspin_planning_investment: List<MstCommonEntity>? = null
    var dataspin_expected_support: List<MstCommonEntity>? = null
    var dataspin_bank_account: List<MstCommonEntity>? = null
    var dataspin_new_business: List<MstCommonEntity>? = null
    var dataspin_business_plan: List<MstCommonEntity>? = null
    var dataspin_ready_to_invest: List<MstCommonEntity>? = null
    var dataspin_availing_loan: List<MstCommonEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_second)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        tv_title.text = "Primary Data"

        dataspin_invest =
            mstCommonViewModel.getMstCommon(86)
        dataspin_planning_investment =
            mstCommonViewModel.getMstCommon(87)
        dataspin_expected_support =
            mstCommonViewModel.getMstCommon(88)
        dataspin_bank_account =
            mstCommonViewModel.getMstCommon(82)
        dataspin_new_business =
            mstCommonViewModel.getMstCommon(83)
        dataspin_business_plan =
            mstCommonViewModel.getMstCommon(84)
        dataspin_ready_to_invest =
            mstCommonViewModel.getMstCommon(85)
        dataspin_availing_loan =
            mstCommonViewModel.getMstCommon(89)

        /*img_back.setOnClickListener {
            val intent = Intent(this, PrimaryDataFirstActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            val intent = Intent(this, PrimaryDataThirdActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*btn_cancel.setOnClickListener {
            val intent = Intent(this, PrimaryDataFirstActivity::class.java)
            startActivity(intent)
        }*/

        fillSpinner()
        fillRadio()
    }

    fun fillSpinner() {
        validate!!.fillSpinner(
            this,
            spin_how_much_you_invest,
            resources.getString(R.string.select),
            dataspin_invest
        )
        validate!!.fillSpinner(
            this,
            spin_how_they_planning_investment,
            resources.getString(R.string.select),
            dataspin_planning_investment
        )
        validate!!.fillSpinner(
            this,
            spin_expected_support,
            resources.getString(R.string.select),
            dataspin_expected_support
        )
    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_bank_account,
            -1,
            dataspin_bank_account,
            this
        )
        validate!!.fillradio(
            rg_new_business,
            -1,
            dataspin_new_business,
            this
        )
        validate!!.fillradio(
            rg_business_plan,
            -1,
            dataspin_business_plan,
            this
        )
        validate!!.fillradio(
            rg_ready_to_invest,
            -1,
            dataspin_ready_to_invest,
            this
        )
        validate!!.fillradio(
            rg_availing_loan_subsidies,
            -1,
            dataspin_availing_loan,
            this
        )
    }

    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataFirstActivity::class.java)
        startActivity(intent)
        finish()
    }
}