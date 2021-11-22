package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileTwoActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_gender: List<MstCommonEntity>? = null


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
            spin_cate_picker_belong,
            resources.getString(R.string.select),
            mstCommonViewModel,
            56
        )
        validate!!.fillSpinner(
            this,
            spin_source_income,
            resources.getString(R.string.select),
            mstCommonViewModel,
            59
        )

        validate!!.fillSpinner(
            this,
            spin_type_emp,
            resources.getString(R.string.select),
            mstCommonViewModel,
            57
        )
        validate!!.fillSpinner(
            this,
            spin_sell_waste_collect,
            resources.getString(R.string.select),
            mstCommonViewModel,
            58
        )
        validate!!.fillSpinner(
            this,
            spin_acess_mob_data,
            resources.getString(R.string.select),
            mstCommonViewModel,
            60
        )

        validate!!.fillCheckBoxes(
            this,
            multiCheck_lang_read,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(this, lang_write, resources.getStringArray(R.array.language))
        validate!!.fillCheckBoxes(
            this,
            prefer_comni_speaking,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(
            this,
            lang_prefer_mobile_use,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(this, multiCheck, resources.getStringArray(R.array.language))
    }


    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun checkValidation(): Int {
        var value = 1

        if (spin_acess_mob_data.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_acess_mob_data,
                resources.getString(R.string.plz_select_mobiledata)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck).isEmpty()) {
            validate!!.CustomAlertCheckbox(
                this,
                multiCheck,
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
            validate!!.CustomAlertCheckbox(
                this,
                multiCheck_lang_read,
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
            validate!!.CustomAlertCheckbox(
                this,
                lang_write,
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
            validate!!.CustomAlertCheckbox(
                this,
                prefer_comni_speaking,
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

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(lang_prefer_mobile_use).isEmpty()) {
            validate!!.CustomAlertCheckbox(
                this,
                lang_prefer_mobile_use,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
        } else if (et_specify_perfer_mob.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_perfer_mob,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (spin_cate_picker_belong.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_cate_picker_belong,
                resources.getString(R.string.plz_select_waste_pickr)
            )
            value = 0
        } else if (spin_type_emp.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_type_emp,
                resources.getString(R.string.plz_select_employmentr)
            )
            value = 0
        } else if (et_waste_pick.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_waste_pick,
                resources.getString(R.string.plz_select_wastes_pick)
            )
            value = 0

        } else if (spin_sell_waste_collect.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_sell_waste_collect,
                resources.getString(R.string.plz_select_dispose_wastes)
            )
            value = 0


        } else if (et_specify_sell_waste_collect.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_sell_waste_collect,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0

        } else if (spin_source_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_source_income,
                resources.getString(R.string.plz_select_source_income)
            )
            value = 0
        } else if (et_specif_source_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specif_source_income,
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