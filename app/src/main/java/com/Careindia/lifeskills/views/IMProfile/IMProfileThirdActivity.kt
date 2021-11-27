package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileThirdActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_third)
        validate = Validate(this)
        tv_title.text = "IM Profile"
        initializeController()
    }

    override fun initializeController() {
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        //apply click on view
        applyClickOnView()



        validate!!.dynamicMultiCheck(this, lang_prefer_mobile_use, mstCommonViewModel,55)
        validate!!.fillSpinner(
            this,
            spin_cate_picker_belong,
            resources.getString(R.string.select),
            mstCommonViewModel,
            56
        )
        validate!!.fillSpinner(
            this,
            spin_source_income,
            resources.getString(R.string.select),
            mstCommonViewModel,
            59
        )

        validate!!.fillradio(
            rg_type_emp,
            -1,
            mstCommonViewModel,
            57,
            this
        )
        validate!!.fillradio(
            rg_secondary_income,
            -1,
            mstCommonViewModel,
            60,
            this
        )
        validate!!.fillSpinner(
            this,
            spin_sell_waste_collect,
            resources.getString(R.string.select),
            mstCommonViewModel,
            58
        )

        validate!!.fillSpinner(
            this,
            spin_what_secondary_income,
            resources.getString(R.string.select),
            mstCommonViewModel,
            61
        )

    }


    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.setOnClickListener(this)
        btn_save.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
//                if (checkValidation() == 1) {
                    val intent = Intent(this, IMProfileFourthActivity::class.java)
                    startActivity(intent)
                    finish()
//                }
            }
            R.id.btn_prev -> {
                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }


    }

    private fun checkValidation(): Int {
        var value = 1

        if (validate!!.GetAnswerTypeCheckBoxButtonID(lang_prefer_mobile_use).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
        } else if (et_specify_perfer_mob.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_perfer_mob,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (spin_cate_picker_belong.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_cate_picker_belong,
                resources.getString(R.string.plz_select_waste_pickr)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_type_emp) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_employmentr)
            )
            value = 0
        } else if (et_waste_pick.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_waste_pick,
                resources.getString(R.string.plz_select_wastes_pick)
            )
            value = 0

        } else if (spin_sell_waste_collect.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_sell_waste_collect,
                resources.getString(R.string.plz_select_dispose_wastes)
            )
            value = 0


        } else if (et_specify_sell_waste_collect.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_sell_waste_collect,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0

        } else if (spin_source_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_source_income,
                resources.getString(R.string.plz_select_source_income)
            )
            value = 0
        } else if (et_specif_source_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specif_source_income,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (et_no_days_job.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_no_days_job,
                resources.getString(R.string.plz_select_working_days)
            )
            value = 0
        } else if (Integer.parseInt(et_no_days_job.text.toString()) < 1 || Integer.parseInt(
                et_no_days_job.text.toString()
            ) > 29
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_no_days_job,
                resources.getString(R.string.please_entr_input_months)
            )
            value = 0
        } else if (et_avg_daily_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_avg_daily_income,
                resources.getString(R.string.plz_select_avg_daily_income)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_secondary_income) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata)
            )
            value = 0
        } else if (spin_what_secondary_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_what_secondary_income,
                resources.getString(R.string.plz_select_secondary_income)
            )
            value = 0

        } else if (et_specify_source_secondary_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_source_secondary_income,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0

        }
        return value
    }


    override fun onBackPressed() {
        val intent = Intent(this, IMProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}