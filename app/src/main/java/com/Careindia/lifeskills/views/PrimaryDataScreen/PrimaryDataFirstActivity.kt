package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataFirstActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_gender: List<MstCommonEntity>? = null
    var dataspin_member: List<MstCommonEntity>? = null
    var dataspin_category: List<MstCommonEntity>? = null
    var dataspin_certificate: List<MstCommonEntity>? = null
    var dataspin_aadhar: List<MstCommonEntity>? = null
    var dataspin_pan: List<MstCommonEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_first)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        tv_title.text = "Primary Data"

        dataspin_gender =
            mstCommonViewModel.getMstCommon(75)
        dataspin_member =
            mstCommonViewModel.getMstCommon(76)
        dataspin_category =
            mstCommonViewModel.getMstCommon(77)
        dataspin_certificate =
            mstCommonViewModel.getMstCommon(78)
        dataspin_aadhar =
            mstCommonViewModel.getMstCommon(79)
        dataspin_pan =
            mstCommonViewModel.getMstCommon(80)

        /*img_back.setOnClickListener {
            val intent = Intent(this, PrimaryDataListActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            val intent = Intent(this, PrimaryDataSecondActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*btn_cancel.setOnClickListener {
            val intent = Intent(this, PrimaryDataListActivity::class.java)
            startActivity(intent)
        }*/

        fillSpinner()
        fillRadio()

    }

    fun fillSpinner() {
        validate!!.fillSpinner(
            this,
            spin_gender,
            resources.getString(R.string.select),
            dataspin_gender
        )

        validate!!.fillSpinner(
            this,
            spin_shg_jlg_cig,
            resources.getString(R.string.select),
            dataspin_member
        )

        validate!!.fillSpinner(
            this,
            spin_social_category,
            resources.getString(R.string.select),
            dataspin_category
        )

        validate!!.fillSpinner(
            this,
            spin_caste_income_certificate,
            resources.getString(R.string.select),
            dataspin_certificate
        )
    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_aadhar_card,
            -1,
            dataspin_aadhar,
            this
        )
        validate!!.fillradio(
            rg_pan_card,
            -1,
            dataspin_aadhar,
            this
        )
    }

    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataListActivity::class.java)
        startActivity(intent)
        finish()
    }

}