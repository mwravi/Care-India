package com.careindia.lifeskills.views.psychometricscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPsychometricFirstBinding
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_psychometric_first.*
import kotlinx.android.synthetic.main.activity_psychometric_first.btn_prev
import kotlinx.android.synthetic.main.activity_psychometric_first.btn_save
import kotlinx.android.synthetic.main.activity_psychometric_first.spin_hhid
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricFirstBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_first)
        validate = Validate(this)
        tv_title.text = "Psychometric"

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = IndividualProfileRepository(improfiledao!!, mstDistrictDao)
        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]



        initializeController()
    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        //fillSpinner
        fillSpinner()
    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.visibility = View.GONE
        btn_save.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                val intent = Intent(this, PsychometricSecondActivity::class.java)
                startActivity(intent)
                finish()
            }


        }

    }

    fun fillSpinner() {

        bindHHIDTable("Select", spin_hhid)
    }


    fun bindHHIDTable(strValue: String, spin: Spinner) {
        imProfileViewModel.gethhProfileData().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).HHCode
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


    fun bindIMIDTable(strValue: String, spin: Spinner) {
        imProfileViewModel.gethhProfileData().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).HHCode
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