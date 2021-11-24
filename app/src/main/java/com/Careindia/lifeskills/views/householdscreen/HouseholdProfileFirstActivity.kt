package com.careindia.lifeskills.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.database.AppDataBase
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileFirstBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHouseholdProfileFirstBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var householdProfileEntity: HouseholdProfileEntity
    lateinit var householdProfileViewModel: HouseholdProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_household_profile_first)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        householdProfileViewModel = ViewModelProviders.of(this).get(HouseholdProfileViewModel::class.java)*/

        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_first)
        val hhdao = AppDataBase.getDatabase(this).hhProfileDao()
        val hhRepository = HouseholdProfileRepository(hhdao)
        householdProfileViewModel =
            ViewModelProvider(this, HouseholdProfileViewModelFactory(hhRepository)).get(
                HouseholdProfileViewModel::class.java
            )
        binding.householdProfileViewModel = householdProfileViewModel
        binding.lifecycleOwner = this

        //  val hhdao:HouseholdProfileDao =


        tv_title.text = "Household Profile"


        initializeController()

//        et_formfillingDate.setOnClickListener {
//            validate!!.datePickerwithmindate(
//                validate!!.Daybetweentime("01-01-1990"),
//                et_formfillingDate
//            )
//        }
    }


    fun showLiveData() {
        householdProfileViewModel.hhProfileData.observe(this, Observer {
            Log.i("MYTAG", it.toString())
        })

    }

    override fun initializeController() {
        applyClickOnView()
        fillSpinner()
        fillRadio()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (CheckValidation() == 0) {
                    val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, HouseholdProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun fillSpinner() {

        validate!!.fillSpinner(
            this,
            spin_crpfillingform,
            resources.getString(R.string.select),
            mstCommonViewModel,
            1
        )
        validate!!.fillSpinner(
            this,
            spin_SupervisingFC,
            resources.getString(R.string.select),
            mstCommonViewModel,
            2
        )
        validate!!.fillSpinner(
            this,
            spin_districtname,
            resources.getString(R.string.select),
            mstCommonViewModel,
            3
        )
        validate!!.fillSpinner(
            this,
            spin_zone,
            resources.getString(R.string.select),
            mstCommonViewModel,
            4
        )
        validate!!.fillSpinner(
            this,
            spin_bbmp,
            resources.getString(R.string.select),
            mstCommonViewModel,
            5
        )
        validate!!.fillSpinner(
            this,
            spin_panchayatname,
            resources.getString(R.string.select),
            mstCommonViewModel,
            6
        )

    }

    fun fillRadio() {

    }

    fun CheckValidation(): Int {
        var iValue = 0
        if (et_formfillingDate.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_formfillingDate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form),
            )
        } else if (spin_crpfillingform.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_crpfillingform,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_CRP_filling_the_form),
            )
        } else if (spin_SupervisingFC.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_SupervisingFC,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_supervising_field_cordinator),
            )
        } else if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_distric),
            )
        } else if (spin_zone.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_panchayat),
            )
        } else if (et_localityname.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_localityname,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q108_name_of_locality),
            )
        } else if (et_hh_unique_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hh_unique_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hhh_unique_id),
            )
        } else if (et_hh_unique_id.text.toString().length != 14) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hh_unique_id,
                resources.getString(R.string.hh_name),
            )
        } else if (et_hhName.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hhName,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hh_name),
            )
        }
        return iValue
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
        val intent = Intent(this, HouseholdProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }

}