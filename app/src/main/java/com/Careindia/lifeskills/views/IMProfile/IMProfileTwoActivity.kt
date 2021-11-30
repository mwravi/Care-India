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
import com.careindia.lifeskills.databinding.ActivityImprofileTwoBinding
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
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileTwoActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileTwoBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel

    var imProfileGUID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_two)
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

    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        // fill spinner view
        fillSpinner()
        if(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.trim().length>0) {
            showLiveData()
        }

        validate!!.fillradio(
            rg_can_read,
            -1,
            mstCommonViewModel,
            49,
            this
        )

        validate!!.fillradio(
            rg_access_sphone,
            -1,
            mstCommonViewModel,
            47,
            this
        )
        validate!!.fillradio(
            rg_acess_mob_data,
            -1,
            mstCommonViewModel,
            60,
            this
        )

        validate!!.dynamicMultiCheck(this, multiCheck_lang_read, mstCommonViewModel, 51)
        validate!!.dynamicMultiCheck(this, lang_write, mstCommonViewModel, 52)
        validate!!.dynamicMultiCheck(this, prefer_comni_speaking, mstCommonViewModel, 53)
        validate!!.dynamicMultiCheck(this, multiCheck, mstCommonViewModel, 54)

    }

    fun fillSpinner(){
        bindCommonTable("Select",spin_state,46)
        bindCommonTable("Select",spin_education,48)
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
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateProfileSecondData()
                    var intent = Intent(this, IMProfileThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                var intent = Intent(this, IMProfileOneActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }




    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid)).observe(this, Observer {
            if (it != null && it.size>0) {
                et_long_stay.setText(validate!!.returnStringValue(it.get(0).ResidingSince.toString()))
                spin_state.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).StateID.toString()),46))
                spin_education.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Education.toString()),48))

            }
        })

    }

    fun sendData() {
        imProfileViewModel.collectiveData(
            validate!!.GetAnswerTypeRadioButtonID(rg_can_read),
            validate!!.GetAnswerTypeRadioButtonID(rg_access_sphone),
            validate!!.GetAnswerTypeRadioButtonID(rg_acess_mob_data),
            validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck),
            validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_lang_read),
            validate!!.GetAnswerTypeCheckBoxButtonID(lang_write),
            validate!!.GetAnswerTypeCheckBoxButtonID(prefer_comni_speaking),
        )
    }

    private fun checkValidation(): Int {
        var value = 1

        if (spin_state.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_state,
                resources.getString(R.string.plz_select_state)
            )
            value = 0

        } else if (et_specify_state.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_state,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (et_long_stay.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_long_stay,
                resources.getString(R.string.plz_entr_sty_long)
            )
            value = 0

        } else if (Integer.parseInt(et_long_stay.text.toString()) <= 0 || Integer.parseInt(
                et_long_stay.text.toString()
            ) >= 99
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_long_stay,
                resources.getString(R.string.plz_entr_less_input)
            )
            value = 0


        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_can_read) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_read_write)
            )
            value = 0
        } else if (spin_education.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_education,
                resources.getString(R.string.plz_select_education)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_access_sphone) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_smartphone)
            )

            value = 0

        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_acess_mob_data) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_speack_lang)
            )
            value = 0
        } else if (et_specify.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_lang_read).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_read_lang)
            )
            value = 0

        } else if (et_specifyread.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specifyread,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(lang_write).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_write_lang)
            )
            value = 0


        } else if (et_specifywrite.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specifywrite,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(prefer_comni_speaking).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_communication_lang)
            )
            value = 0

        } else if (et_specifyprefer.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specifyprefer,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0

        }
        return value
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