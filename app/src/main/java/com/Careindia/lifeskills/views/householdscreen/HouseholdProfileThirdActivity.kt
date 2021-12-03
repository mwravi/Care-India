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
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileThirdBinding
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.activity_household_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding:ActivityHouseholdProfileThirdBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_dwelling_type: List<MstCommonEntity>? = null
    var dataspin_ration_card: List<MstCommonEntity>? = null
    var dataspin_dwelling_place: List<MstCommonEntity>? = null
    lateinit var householdProfileViewModel: HouseholdProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_third)
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
        tv_title.text = "Household Profile"


        initializeController()

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
                    householdProfileViewModel.updatehh_third()
                    val intent = Intent(this, HouseholdProfileListActivity::class.java)
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


    fun fillSpinner() {

        validate!!.fillradio(
            rg_dwelling_place_registered,
            -1,
            mstCommonViewModel,
            11,
            this
        )


        validate!!.fillSpinner(
            this,
            spin_dwelling,
            resources.getString(R.string.select),
            mstCommonViewModel,
            38
        )
        validate!!.fillSpinner(
            this,
            spin_hh_ration_card,
            resources.getString(R.string.select),
            mstCommonViewModel,
            40
        )

    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_dwelling_place_registered,
            -1,
            mstCommonViewModel,
            39,
            this
        )

    }

    fun CheckValidation(): Int {
        var iValue = 0;
        if (et_totalEarningMember.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalEarningMember,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_earning_member),
            )
        } else if (et_maleearningmember.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleearningmember,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male_earning_member),
            )
        } else if (et_femaleearningmember.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_femaleearningmember,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female_earning_member),
            )
        } else if (spin_dwelling.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_dwelling,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.type_of_dwelling),
            )
        } else if (et_othertypeofdwelling.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_othertypeofdwelling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.if_other_type_of_dwelling),
            )
        } else if (spin_hh_ration_card.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_hh_ration_card,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_ration_card),
            )
        }  else if (validate!!.returnIntegerValue(et_maleearningmember.text.toString()) +
            validate!!.returnIntegerValue(et_femaleearningmember.text.toString()) > validate!!.returnIntegerValue(
                et_totalEarningMember.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalEarningMember,
                resources.getString(R.string.valid_earning_member)
            )
        } else if(validate!!.returnIntegerValue(et_maleearningmember.text.toString())+
            validate!!.returnIntegerValue(et_femaleearningmember.text.toString())>validate!!.returnIntegerValue(et_totalEarningMember.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalEarningMember,
                resources.getString(R.string.valid_earning_member)
            )
        }
        return iValue;
    }

    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size>0) {
                    et_totalEarningMember.setText(it.get(0).No_Earningmembers.toString())
                    et_maleearningmember.setText(it.get(0).No_Earningmembers_M.toString())
                    et_femaleearningmember.setText(it.get(0).No_Earningmembers_F.toString())
                    spin_dwelling.setSelection(validate!!.returnpos(it.get(0).Dwelling_type,mstCommonViewModel,38))
                    et_othertypeofdwelling.setText(it.get(0).Dwelling_Oth!!)
                    spin_hh_ration_card.setSelection(validate!!.returnpos(it.get(0).Type_Ration,mstCommonViewModel,38))
                    validate!!.SetAnswerTypeRadioButton(rg_dwelling_place_registered,it.get(0).Dwelling_Registered!!)


                }
            })
        }

    }
}