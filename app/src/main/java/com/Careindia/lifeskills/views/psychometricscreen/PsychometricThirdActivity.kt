package com.careindia.lifeskills.views.psychometricscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.ActivityPsychometricThirdBinding
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_psychometric_third.*
import kotlinx.android.synthetic.main.activity_psychometric_third.btn_prev
import kotlinx.android.synthetic.main.activity_psychometric_third.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricThirdBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID: Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_third)
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
        //fillSpinner/multicheck
        fillSpinner()
        fillMultiCheck()


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
                var intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.btn_prev -> {
                var intent = Intent(this, PsychometricSecondActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }


    fun fillSpinner(){
        bindCommonTable("Select", spin_evaluate_risk, 7,iLanguageID)
        bindCommonTable("Select", spin_income_gen_prefer, 7,iLanguageID)
        bindCommonTable("Select", spin_staff_required_prefer, 7,iLanguageID)
        bindCommonTable("Select", spin_women_entrepreneurs, 7,iLanguageID)
        bindCommonTable("Select", spin_requires_financial, 7,iLanguageID)
        bindCommonTable("Select", spin_willingness_invest, 7,iLanguageID)


    }

    fun fillMultiCheck(){
        validate!!.dynamicMultiCheckChange(
            this,
            multiCheck_areas_succes_entrep,
            mstLookupViewModel,
            9,
            1,
            et_specifyareas_succes_entrep,
            lay_specify_areas_succes_entrep
        )
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