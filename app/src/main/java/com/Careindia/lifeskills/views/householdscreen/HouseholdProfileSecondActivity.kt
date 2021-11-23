package com.careindia.lifeskills.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileSecondActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var dataspin_dwelling_type: List<MstCommonEntity>? = null
    var dataspin_ration_card: List<MstCommonEntity>? = null
    var dataspin_dwelling_place: List<MstCommonEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_household_profile_second)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        tv_title.text = "Household Profile"

        /*dataspin_dwelling_type =
            mstCommonViewModel.getMstCommon(38)
        dataspin_ration_card =
            mstCommonViewModel.getMstCommon(40)
        dataspin_dwelling_place =
            mstCommonViewModel.getMstCommon(39)*/

      /*  img_back.setOnClickListener {
            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            if (CheckValidation()==0) {

            }
        }

      /*  btn_cancel.setOnClickListener {
            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
        }*/

        fillSpinner()
        fillRadio()
    }

    fun fillSpinner() {
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

    fun CheckValidation():Int {
        var iValue = 0;
        if (et_adolescentboysgirls.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentboysgirls,
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
         } else if (et_totalmale.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalmale,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male_children),
            )
        } else if (et_totalfemale.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalfemale,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female_children),
            )
        } else if (et_totalEarningMember.text.toString().length == 0) {
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
        }else if (spin_hh_ration_card.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_hh_ration_card,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_ration_card),
            )
        } else if(validate!!.returnIntegerValue(et_adolescentboys.text.toString())+
            validate!!.returnIntegerValue(et_adolescentgirls.text.toString())>validate!!.returnIntegerValue(et_adolescentboysgirls.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_adolescentboysgirls,
                resources.getString(R.string.valid_adolsent_boys_girls)
            )
        } else if(validate!!.returnIntegerValue(et_totalmale.text.toString())+
            validate!!.returnIntegerValue(et_totalfemale.text.toString())>validate!!.returnIntegerValue(et_totalChildren.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalChildren,
                resources.getString(R.string.valid_total_children)
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
}