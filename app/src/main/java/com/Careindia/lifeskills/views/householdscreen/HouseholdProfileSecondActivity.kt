package com.careindia.lifeskills.views.householdscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileSecondBinding
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.hhnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHouseholdProfileSecondBinding
    var validate: Validate? = null
    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_second)
        validate = Validate(this)

        val hhdao = CareIndiaApplication.database?.hhProfileDao()!!

        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val hhRepository = HouseholdProfileRepository(hhdao, mstDistrictDao)
        householdProfileViewModel =
            ViewModelProvider(
                this,
                HouseholdProfileViewModelFactory(hhRepository)
            )[HouseholdProfileViewModel::class.java]
        binding.householdProfileViewModel = householdProfileViewModel
        binding.lifecycleOwner = this


        tv_title.text = "Household Profile"


        initializeController()
        bottomclick()

        if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.HHGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

    }

    fun bottomclick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        lay_first.setOnClickListener {

            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {

            val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
            startActivity(intent)
            finish()
        }

        ll_third.setOnClickListener {

            val intent = Intent(this, HouseholdProfileThirdActivity::class.java)
            startActivity(intent)
            finish()
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
                val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun CheckValidation(): Int {
        var iValue = 0;
        if (et_total_adult.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_adult,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_adult),
            )
        } else if (et_adult_male.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_male,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adult_male),
            )
        }    else if (validate!!.returnIntegerValue(et_adult_male.text.toString())  > validate!!.returnIntegerValue(
                et_total_adult.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_male,
                resources.getString(R.string.adult_males_must_be_equal_to_or_less_than_the_total_adult_members)
            )
        } else if (et_adult_female.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_female,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adult_female),
            )
        }
        else if (validate!!.returnIntegerValue(et_adult_female.text.toString())  > validate!!.returnIntegerValue(
                et_total_adult.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_female,
                resources.getString(R.string.adult_females_must_be_equal_to_or_less_than_the_total_adult_members)
            )
        }
        else if (validate!!.returnIntegerValue(et_adult_male.text.toString()) +
            validate!!.returnIntegerValue(et_adult_female.text.toString()) > validate!!.returnIntegerValue(
                et_total_adult.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adult_male,
                resources.getString(R.string.the_sum_of_adult_males_and_females_must_be_equal_to_or_less_than_the_total_adult_members)
            )
        }


        else if (et_adolescent.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescent,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adolescent_boys_girls),
            )
        } else if (et_adolescentboys.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentboys,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adolescent_boys),
            )
        } else if (validate!!.returnIntegerValue(et_adolescentboys.text.toString())  > validate!!.returnIntegerValue(
                et_adolescent.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentboys,
                resources.getString(R.string.adolescent_boys_must_be_equal_to_or_less_than_the_total_adult_members)
            )
        }

        else if (et_adolescentgirls.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentgirls,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.adolescent_girls),
            )
        }  else if (validate!!.returnIntegerValue(et_adolescentgirls.text.toString())  > validate!!.returnIntegerValue(
                et_adolescent.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentgirls,
                resources.getString(R.string.adolescent_girls_females_must_be_equal_to_or_less_than_the_total_adult_members)
            )
        }   else if (validate!!.returnIntegerValue(et_adolescentboys.text.toString()) +
            validate!!.returnIntegerValue(et_adolescentgirls.text.toString()) > validate!!.returnIntegerValue(
                et_adolescent.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentboys,
                resources.getString(R.string.the_sum_of_adolescent_boys_and_girls_must_be_equal_to_or_less_than_the_total_adolescent)
            )
        }


        else if (et_totalChildren.text.toString().length == 0) {
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
        } else if (validate!!.returnIntegerValue(et_maleChildren.text.toString())  > validate!!.returnIntegerValue(
                et_totalChildren.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleChildren,
                resources.getString(R.string.male_children_must_be_equal_to_or_less_than_the_total_children_members)
            )
        }  else if (et_femalechildren.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_femalechildren,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female_children),
            )
        } else if (validate!!.returnIntegerValue(et_femalechildren.text.toString())  > validate!!.returnIntegerValue(
                et_totalChildren.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_femalechildren,
                resources.getString(R.string.female_children_females_must_be_equal_to_or_less_than_the_total_number_of_children_members)
            )
        } else if (validate!!.returnIntegerValue(et_maleChildren.text.toString()) +
            validate!!.returnIntegerValue(et_femalechildren.text.toString()) > validate!!.returnIntegerValue(
                et_totalChildren.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleChildren,
                resources.getString(R.string.the_sum_of_male_and_female_children_must_be_equal_to_or_less_than_the_total_number_of_children)
            )
        }





        return iValue;
    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size > 0) {
                    setDefBlank(et_total_adult, it.get(0).No_adults!!)
                    setDefBlank(et_adult_male, it.get(0).No_adults_M!!)
                    setDefBlank(et_adult_female, it.get(0).No_adults_F!!)
                    setDefBlank(et_adolescent, it.get(0).No_adolescent!!)
                    setDefBlank(et_adolescentboys, it.get(0).No_adolescent_M!!)
                    setDefBlank(et_adolescentgirls, it.get(0).No_adolescent_F!!)
                    setDefBlank(et_totalChildren, it.get(0).No_Children!!)
                    setDefBlank(et_maleChildren, it.get(0).No_Children_M!!)
                    setDefBlank(et_femalechildren, it.get(0).No_Children_M!!)

                }
            })
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
       else edi.setText(data.toString())

    }


}