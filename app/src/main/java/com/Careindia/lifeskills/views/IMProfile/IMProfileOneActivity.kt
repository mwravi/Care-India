package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class IMProfileOneActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    private val imProfileViewModel by viewModel<IMProfileViewModel>()
    lateinit var mstCommonViewModel: MstCommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_one)
        validate = Validate(this)
        tv_title.text = "IM Profile"

        initializeController()
    }


    override fun initializeController() {
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        //apply click on view
        applyClickOnView()



        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngjgDate
            )

        }

        validate!!.fillSpinner(
            this,
            spin_name_crp,
            resources.getString(R.string.select),
            mstCommonViewModel,
            41
        )

        validate!!.fillSpinner(
            this,
            spin_SupervisingFC,
            resources.getString(R.string.select),
            mstCommonViewModel,
            42
        )
        validate!!.fillSpinner(
            this,
            spin_sexrepo,
            resources.getString(R.string.select),
            mstCommonViewModel,
            43
        )
        validate!!.fillSpinner(
            this,
            spin_casterespo,
            resources.getString(R.string.select),
            mstCommonViewModel,
            44
        )
        validate!!.fillSpinner(
            this,
            spin_marital,
            resources.getString(R.string.select),
            mstCommonViewModel,
            45
        )

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
//                if (imProfileViewModel.isValid()) {
//
//                }

                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileTwoActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
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
        } else if (spin_state.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_state,
                resources.getString(R.string.plz_select_state)
            )
            value = 0

        } else if (et_specify.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_specify,
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
        }
        return value
    }


    private fun CheckValidationd(): Int {
        var value = 1

        if (et_formfilngjgDate.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q101) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (spin_name_crp.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q102) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (spin_SupervisingFC.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q103) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (ethouseid.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q104) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (et_namerespo.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q201) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (spin_sexrepo.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q203) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (et_agerespo.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q204) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (Integer.parseInt(et_agerespo.text.toString()) <= 18 || Integer.parseInt(
                et_agerespo.text.toString()
            ) >= 65
        ) {
            Toast.makeText(this, "(Q204) Input value between 18-65", Toast.LENGTH_SHORT).show()
            value = 0
            return value
        } else if (spin_casterespo.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q205) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (spin_marital.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q206) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (et_contactnorespo.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q207) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (et_contactnorespo.text.toString().trim().length < 10) {
            Toast.makeText(this, "(Q207) Input proper 10 digit mobile no.", Toast.LENGTH_SHORT)
                .show()

            value = 0
            return value
        } else if (spin_state.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q209) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value


        } else if (et_specify.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q209a) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (et_long_stay.text.toString().trim().length == 0) {
            Toast.makeText(this, "(Q210) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value

        } else if (Integer.parseInt(et_long_stay.text.toString()) <= 0 || Integer.parseInt(
                et_long_stay.text.toString()
            ) >= 99
        ) {
            Toast.makeText(
                this,
                "(Q210) Input value between 0-99 and that should be less",
                Toast.LENGTH_SHORT
            ).show()
            value = 0
            return value
        } else if (spin_can_read.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q211) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (spin_education.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q212) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value
        } else if (spin_access_sphone.selectedItemPosition == 0) {
            Toast.makeText(this, "(Q213) Please answer this question.", Toast.LENGTH_SHORT).show()

            value = 0
            return value

        }
        return value
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}