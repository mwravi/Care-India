package com.careindia.lifeskills.views.householdscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileSecondBinding
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHouseholdProfileSecondBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_second)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        val hhdao = CareIndiaApplication.database?.hhProfileDao()!!
        val mstcmnDoa = CareIndiaApplication.database?.mstCommonDao()!!
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val hhRepository = HouseholdProfileRepository(hhdao,mstcmnDoa,mstDistrictDao)
        householdProfileViewModel =
            ViewModelProvider(this,
                HouseholdProfileViewModelFactory(hhRepository)
            )[HouseholdProfileViewModel::class.java]
        binding.householdProfileViewModel = householdProfileViewModel
        binding.lifecycleOwner = this

     /*   if (!::list.isInitialized) {
            list = LinkedList<String>();
        }*/

        tv_title.text = "Household Profile"


     initializeController()

        if(validate!!.RetriveSharepreferenceString(AppSP.HHGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.trim().length>0) {
            showLiveData()
        }

    }


    override fun initializeController() {
        applyClickOnView()

    }
    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (CheckValidation() == 0) {
                    householdProfileViewModel.updatehh_second()
                    val intent = Intent(this, HouseholdProfileThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun CheckValidation():Int {
        var iValue = 0;
        if (et_total_adult.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_adult,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_adult),
            )
        } else  if (et_adult_male.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_male,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adult_male),
            )
        } else if (et_adult_female.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_female,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adult_female),
            )
        } else if (et_adolescent.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescent,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adolescent_boys_girls),
            )
        } else if(et_adolescentboys.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentboys,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adolescent_boys),
            )
        } else if(et_adolescentgirls.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentgirls,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adolescent_girls),
            )
        }  else if (et_totalChildren.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalChildren,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_children),
            )
         } else if (et_maleChildren.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleChildren,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male_children),
            )
        } else if (et_femalechildren.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_femalechildren,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female_children),
            )
        } else if (validate!!.returnIntegerValue(et_adolescentboys.text.toString()) +
            validate!!.returnIntegerValue(et_adolescentgirls.text.toString()) > validate!!.returnIntegerValue(
                et_adolescent.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescent,
                resources.getString(R.string.valid_adolsent_boys_girls)
            )
        } else if (validate!!.returnIntegerValue(et_maleChildren.text.toString()) +
            validate!!.returnIntegerValue(et_femalechildren.text.toString()) > validate!!.returnIntegerValue(
                et_totalChildren.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalChildren,
                resources.getString(R.string.valid_total_children)
            )
        }





        return iValue;
    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size>0) {
                    et_total_adult.setText(it.get(0).No_adults.toString())
                    et_adult_male.setText(it.get(0).No_adults_M.toString())
                    et_adult_female.setText(it.get(0).No_adults_F.toString())
                    et_adolescent.setText(it.get(0).No_adolescent.toString())
                    et_adolescentboys.setText(it.get(0).No_adolescent_M.toString())
                    et_adolescentgirls.setText(it.get(0).No_adolescent_F.toString())
                    et_totalChildren.setText(it.get(0).No_Children.toString())
                    et_maleChildren.setText(it.get(0).No_Children_M.toString())
                    et_femalechildren.setText(it.get(0).No_Children_M.toString())


                }
            })
        }

    }
}