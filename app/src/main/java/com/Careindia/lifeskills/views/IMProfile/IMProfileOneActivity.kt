package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileOneBinding
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IMProfileOneActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileOneBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel
    var imProfileGUID = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_one)
        validate = Validate(this)
        tv_title.text = "IM Profile"

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val improfileRepository = IndividualProfileRepository(improfiledao!!, commondao!!)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this



        initializeController()
//        GlobalScope.launch {
//
//        }
    }

    override fun initializeController() {

        //apply click on view
        applyClickOnView()
        // fill spinner view
        fillSpinner()
        if(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.trim().length>0) {
            showLiveData()
        }

        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngjgDate
            )

        }



//        validate!!.fillSpinner(
//            this,
//            spin_marital,
//            resources.getString(R.string.select),
//            mstCommonViewModel,
//            45
//        )


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
                if (checkValidation() == 1) {
                    imProfileViewModel.saveandUpdateCollectiveProfile()
                    val intent = Intent(this, IMProfileTwoActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

        }
    }
    fun showLiveData() {
            val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
            imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid)).observe(this, Observer {
                if (it != null && it.size>0) {
                    et_formfilngjgDate.setText(validate!!.returnStringValue(it.get(0).DateForm))
                    et_namerespo.setText(validate!!.returnStringValue(it.get(0).Name))
                    ethouseid.setText(validate!!.returnStringValue(it.get(0).HHCode))
                    //spin_name_crp.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),41))
                    //spin_SupervisingFC.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),42))
                    spin_sexrepo.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Gender.toString()),43))
                    spin_casterespo.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Caste.toString()),44))
                    spin_marital.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).MaritalStatus.toString()),45))
                    et_agerespo.setText(validate!!.returnStringValue(it.get(0).Age.toString()))
                    et_contactnorespo.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
                }
            })

    }


    private fun checkValidation(): Int {
        var value = 1
        if (et_formfilngjgDate.text.toString().trim().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_formfilngjgDate,
                resources.getString(R.string.plz_enter_date)
            )
            value = 0
        } else if (spin_name_crp.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_name_crp,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (spin_SupervisingFC.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_SupervisingFC,
                resources.getString(R.string.plz_enter_name_superng_coordntr)
            )
            value = 0

        } else if (ethouseid.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                ethouseid,
                resources.getString(R.string.plz_enter_house_uniqueid)
            )
            value = 0
        } else if (et_namerespo.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_namerespo,
                resources.getString(R.string.plz_enter_name_respondent)
            )
            value = 0

        } else if (spin_sexrepo.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_sexrepo,
                resources.getString(R.string.plz_enter_sex_respondent)
            )
            value = 0
        } else if (et_agerespo.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_agerespo,
                resources.getString(R.string.plz_enter_age_respondent)
            )
            value = 0
        } else if (Integer.parseInt(et_agerespo.text.toString()) < 18 || Integer.parseInt(
                et_agerespo.text.toString()
            ) > 65
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_agerespo,
                resources.getString(R.string.plz_enter_age_value)
            )
            value = 0

        } else if (spin_casterespo.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_casterespo,
                resources.getString(R.string.plz_enter_caste_respo)
            )
            value = 0

        } else if (spin_marital.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_marital,
                resources.getString(R.string.plz_enter_marital_respo)
            )
            value = 0


        } else if (et_contactnorespo.text.toString().isEmpty() &&
            validate!!.checkmobileno(et_contactnorespo.text.toString()) == 0
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.plz_enter_contct_no_respo)
            )
            value = 0
        } else if (et_contactnorespo.text.toString().trim().length < 10) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.plz_enter_contct_no_proper)
            )


            value = 0
        }
        return value
    }



    fun fillSpinner(){
        bindCommonTable("Select",spin_name_crp,41)
        bindCommonTable("Select",spin_SupervisingFC,42)
        bindCommonTable("Select",spin_sexrepo,43)
        bindCommonTable("Select",spin_casterespo,44)
        bindCommonTable("Select",spin_marital,45)
    }




    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int) {
        mstCommonViewModel.getMstCommondata(flag).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).value
                }
                val adapter_category = ArrayAdapter<String>(
                    this,
                    R.layout.my_spinner_space_dashboard, name
                )
                adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                spin.adapter = adapter_category
            }
        })
        /*if (distric>0) {
            spin_district_name.setSelection(returnpos(distric, 3))
        }*/
    }


    fun returnpos(id: Int,flag: Int): Int {
        val combobox = mstCommonViewModel.getMstCommon(flag)
        var posi = 0
        for (i in 0 until combobox.size) {
            if (id == combobox[i].id) {
                posi = i + 1
            }
        }
        return posi
    }



    private fun saveData() {
        var save = 0
        imProfileGUID = validate!!.random()

        var imProfileEntity = IndividualProfileEntity(
            0,
            imProfileGUID,
            "", "", "", "", 0, "", 0,
            validate!!.returnStringValue(et_formfilngjgDate.text.toString()),
            validate!!.returnStringValue(ethouseid.text.toString()),
            "",
            validate!!.returnID(spin_name_crp, mstCommonViewModel, 41).toString(),
            validate!!.returnID(spin_sexrepo, mstCommonViewModel, 43),
            Integer.parseInt(et_agerespo.text.toString()),
            validate!!.returnID(spin_casterespo, mstCommonViewModel, 44),
            validate!!.returnID(spin_marital, mstCommonViewModel, 45),
            validate!!.returnStringValue(et_contactnorespo.text.toString()), "", 0, 0, 0, 0,
            0, 0, "", "", "", "",
            "", 0, 0, "", 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, "", "", 0, "", "",
            "", 0, "", 0, "", "", 0, "",
            0, 0
        )
    }
    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}