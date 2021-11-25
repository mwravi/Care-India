package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileTwoActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_gender: List<MstCommonEntity>? = null
    var imProfileGUID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_two)
        validate = Validate(this)
        tv_title.text = "IM Profile"




        initializeController()

    }

    override fun initializeController() {

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        //apply click on view
        applyClickOnView()

        validate!!.fillSpinner(
            this,
            spin_state,
            resources.getString(R.string.select),
            mstCommonViewModel,
            46
        )
        validate!!.fillSpinner(
            this,
            spin_access_sphone,
            resources.getString(R.string.select),
            mstCommonViewModel,
            47
        )
        validate!!.fillSpinner(
            this,
            spin_education,
            resources.getString(R.string.select),
            mstCommonViewModel,
            48
        )
        validate!!.fillSpinner(
            this,
            spin_education,
            resources.getString(R.string.select),
            mstCommonViewModel,
            48
        )
        validate!!.fillSpinner(
            this,
            spin_can_read,
            resources.getString(R.string.select),
            mstCommonViewModel,
            49
        )



        validate!!.fillSpinner(
            this,
            spin_acess_mob_data,
            resources.getString(R.string.select),
            mstCommonViewModel,
            60
        )


        validate!!.dynamicMultiCheck(this, multiCheck_lang_read, mstCommonViewModel,51)
        validate!!.dynamicMultiCheck(this, lang_write, mstCommonViewModel,52)
        validate!!.dynamicMultiCheck(this, prefer_comni_speaking, mstCommonViewModel,53)
        validate!!.dynamicMultiCheck(this, multiCheck, mstCommonViewModel,54)

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
            R.id.btn_prev -> {
//                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileOneActivity::class.java)
                    startActivity(intent)
                    finish()
//                }
            } R.id.btn_save -> {
//                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileThirdActivity::class.java)
                    startActivity(intent)
                    finish()
//                }
            }
        }
    }


    private fun saveData(){
        var save = 0
        imProfileGUID = validate!!.random()

        var imProfileEntity = IndividualProfileEntity(
            0,
            imProfileGUID,
            "","","","",0,"",0,
            validate!!.returnStringValue(et_formfilngjgDate.text.toString()),
            validate!!.returnStringValue(ethouseid.text.toString()),
            "",
            validate!!.returnID(spin_name_crp, mstCommonViewModel, 41).toString(),
            validate!!.returnID(spin_sexrepo, mstCommonViewModel, 43),
            Integer.parseInt(et_agerespo.text.toString()),
            validate!!.returnID(spin_casterespo, mstCommonViewModel, 44),
            validate!!.returnID(spin_marital, mstCommonViewModel, 45),
            validate!!.returnStringValue(et_contactnorespo.text.toString()),"",0,0,0,0,
            0,0,"","","","",
            "",0,0,"",0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,"","",0,"","",
            "",0,"",0,"","",0,"",
            0,0)
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


        } else if (spin_can_read.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_can_read,
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
        } else if (spin_access_sphone.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_access_sphone,
                resources.getString(R.string.plz_select_smartphone)
            )

            value = 0

        }else if (spin_acess_mob_data.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_acess_mob_data,
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


    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}