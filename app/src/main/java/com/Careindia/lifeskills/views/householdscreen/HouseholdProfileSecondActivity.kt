package com.careindia.lifeskills.views.householdscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
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

        dataspin_dwelling_type =
            mstCommonViewModel.getMstCommon(38)
        dataspin_ration_card =
            mstCommonViewModel.getMstCommon(40)
        dataspin_dwelling_place =
            mstCommonViewModel.getMstCommon(39)

      /*  img_back.setOnClickListener {
            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
        }*/

        btn_save.setOnClickListener {
            /*val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
            startActivity(intent)*/
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
            dataspin_dwelling_type
        )
        validate!!.fillSpinner(
            this,
            spin_hh_ration_card,
            resources.getString(R.string.select),
            dataspin_ration_card
        )

    }

    fun fillRadio() {
        validate!!.fillradio(
            rg_dwelling_place_registered,
            -1,
            dataspin_dwelling_place,
            this
        )

    }
}