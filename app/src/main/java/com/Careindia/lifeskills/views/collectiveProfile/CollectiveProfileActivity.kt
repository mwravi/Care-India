package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileFirstBinding
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectiveProfileFirstBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveViewModel: CollectiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_first)
        validate = Validate(this)
        mstCommonViewModel = ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val collectiveRepository = CollectiveRepository(collectivedao!!,commondao!!)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                CollectiveViewModel::class.java]

        binding.collectiveViewModel = collectiveViewModel
        binding.lifecycleOwner = this
//        showLiveData()


        tv_title.text = "Collective Profile"


        initializeController()

        et_date_of_filling.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_filling
            )
        }
    }


    fun showLiveData() {
        if(validate!!.returnStringValue(AppSP.CollectiveGUID).trim().length>0) {
            collectiveViewModel.collectiveData.observe(this, Observer {
                if (it != null) {
                    et_date_of_filling.setText(validate!!.returnStringValue(it.get(0).DateForm))
                }
            })
        }
    }

    override fun initializeController() {
       applyClickOnView()
        fillSpinner()
        /*fillRadio()*/
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                    collectiveViewModel.saveandUpdateCollectiveProfile()
                    val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                    startActivity(intent)
                    finish()

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun CheckValidation():Int {
        var iValue = 0;
        if (et_date_of_filling.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_filling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form_profile),
            )
        } else if (spin_name_of_crp.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_name_of_crp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_crp_filling),
            )
        } else if (spin_name_of_supervising.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_name_of_supervising,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_supervising_care_india),
            )
        } else if (spin_district_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_district_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_district),
            )
        }else if (spin_zone_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_ward_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_ward_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_bbmp_ward),
            )
        } else if (spin_panchayat_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayat_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_panchayat),
            )
        } else if (et_locality_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_locality_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_locality),
            )
        } else if (et_collective_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_collective_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.collective_unique_id),
            )
        }else if (et_group_collective_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_group_collective_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_group_collective_sangha),
            )
        }
        return iValue;
    }


    fun fillSpinner(){
        validate!!.fillSpinner(
            this,
            spin_name_of_crp,
            resources.getString(R.string.select),
            mstCommonViewModel,
            1
        )

        validate!!.fillSpinner(
            this,
            spin_name_of_supervising,
            resources.getString(R.string.select),
            mstCommonViewModel,
            2
        )
        validate!!.fillSpinner(
            this,
            spin_district_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            3
        )

        validate!!.fillSpinner(
            this,
            spin_zone_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            4
        )

        validate!!.fillSpinner(
            this,
            spin_ward_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            5
        )
        validate!!.fillSpinner(
            this,
            spin_panchayat_name,
            resources.getString(R.string.select),
            mstCommonViewModel,
            6
        )
    }

    /* fun save(){
         if(AppSP.HHGUID=="") {
             val hhguid = validate!!.random()
             validate!!.SaveSharepreferenceString(AppSP.HHGUID,hhguid)
             householdProfileEntity = HouseholdProfileEntity(
                 0,
                 validate!!.RetriveSharepreferenceString(AppSP.HHGUID),
                 "",
                 validate!!.returnID(spin_districtname, mstCommonViewModel, 3).toString(),
                 validate!!.returnID(spin_zone, mstCommonViewModel, 4).toString(),
                 validate!!.returnID(spin_panchayatname, mstCommonViewModel, 6),
                 "",
                 0,
                 validate!!.returnStringValue(et_formfillingDate.text.toString()),
                 validate!!.returnStringValue(et_hh_unique_id.text.toString()),
                 validate!!.returnStringValue(et_hhName.text.toString()),
                 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0,
                 validate!!.RetriveSharepreferenceInt(AppSP.userId),
                 validate!!.returnStringValue(validate!!.currentdatetime),
                 0, "",
                 0, 0
             )
             householdProfileViewModel.insertHouseholdProfile(householdProfileEntity)
         }

     }*/

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}