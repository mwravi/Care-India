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

                var intent = Intent(this, PsychometricThirdActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.btn_prev -> {
                var intent = Intent(this, PsychometricFirstActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }

    fun fillSpinner(){
        bindCommonTable("Select", spin_min_age_limit, 7,iLanguageID)
        bindCommonTable("Select", spin_educ_applicant, 8,iLanguageID)
        bindCommonTable("Select", spin_socially_category, 7,iLanguageID)
        bindCommonTable("Select", spin_economic_category, 7,iLanguageID)
        bindCommonTable("Select", spin_emp_category, 7,iLanguageID)
        bindCommonTable("Select", spin_year_exp, 7,iLanguageID)
        bindCommonTable("Select", spin_stage_emp, 7,iLanguageID)
        bindCommonTable("Select", spin_size_investment, 7,iLanguageID)
        bindCommonTable("Select", spin_invest_money, 7,iLanguageID)
        bindCommonTable("Select", spin_awareness_market, 7,iLanguageID)


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

}