package com.careindia.lifeskills.views.psychometricscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.ActivityPsychometricSecondBinding
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_psychometric_second.*
import kotlinx.android.synthetic.main.activity_psychometric_second.btn_prev
import kotlinx.android.synthetic.main.activity_psychometric_second.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricSecondBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_second)
        validate = Validate(this)
        tv_title.text = "Psychometric"

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        initializeController()
    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        //fillSpinnner
        fillSpinner()
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
                    var intent = Intent(this, PsychometricThirdActivity::class.java)
                    startActivity(intent)
                    finish()
//                }
            }

            R.id.btn_prev -> {
                var intent = Intent(this, PsychometricFirstActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }

    fun fillSpinner(){
        bindCommonTable("Select", spin_min_age_limit, 35,iLanguageID)
        bindCommonTable("Select", spin_educ_applicant, 36,iLanguageID)
        bindCommonTable("Select", spin_socially_category, 37,iLanguageID)
        bindCommonTable("Select", spin_economic_category, 38,iLanguageID)
        bindCommonTable("Select", spin_emp_category, 39,iLanguageID)
        bindCommonTable("Select", spin_year_exp, 40,iLanguageID)
        bindCommonTable("Select", spin_stage_emp, 41,iLanguageID)
        bindCommonTable("Select", spin_size_investment, 42,iLanguageID)
        bindCommonTable("Select", spin_invest_money, 43,iLanguageID)
        bindCommonTable("Select", spin_awareness_market, 44,iLanguageID)


    }


    private fun checkValidation():Int{
        var value = 1
     if (spin_min_age_limit.selectedItemPosition == 0) {
        validate!!.CustomAlertSpinner(
            this,
            spin_min_age_limit,
            resources.getString(R.string.psy_plz_ans_min_age)
        )
        value = 0

    } else if (spin_educ_applicant.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_educ_applicant,
             resources.getString(R.string.psy_plz_ans_applicat_edu)
         )
         value = 0
     }  else if (spin_socially_category.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_socially_category,
             resources.getString(R.string.psy_plz_ans_socily_cate)
         )
         value = 0
     }  else if (spin_economic_category.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_economic_category,
             resources.getString(R.string.psy_plz_ans_econmic_cate)
         )
         value = 0
     }else if (spin_emp_category.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_emp_category,
             resources.getString(R.string.psy_plz_ans_epm_cate)
         )
         value = 0
     }else if (spin_year_exp.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_year_exp,
             resources.getString(R.string.psy_plz_ans_yrs_exp)
         )
         value = 0
     }
     else if (spin_stage_emp.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_stage_emp,
             resources.getString(R.string.psy_plz_ans_stage_epm)
         )
         value = 0
     }else if (spin_size_investment.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_size_investment,
             resources.getString(R.string.psy_plz_ans_size_invest)
         )
         value = 0
     }else if (spin_invest_money.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_invest_money,
             resources.getString(R.string.psy_plz_ans_willgns_invest)
         )
         value = 0
     }else if (spin_awareness_market.selectedItemPosition == 0) {
         validate!!.CustomAlertSpinner(
             this,
             spin_awareness_market,
             resources.getString(R.string.psy_plz_ans_awrness_emp)
         )
         value = 0
     }
        return value
    }




    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int, iLanguageID:Int) {
        mstLookupViewModel.getMstUser(flag,iLanguageID).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).Description
                }
                val adapter_category = ArrayAdapter<String>(
                    this,
                    R.layout.my_spinner_space_dashboard, name
                )
                adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                spin.adapter = adapter_category
            }
        })

    }


    override fun onBackPressed() {
        val intent = Intent(this, PsychometricFirstActivity::class.java)
        startActivity(intent)
        finish()
    }
}