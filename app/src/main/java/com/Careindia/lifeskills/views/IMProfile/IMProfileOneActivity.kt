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




    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.visibility=View.GONE
        btn_save.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
//                if (imProfileViewModel.isValid()) {
//
//                }

//                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileTwoActivity::class.java)
                    startActivity(intent)
                    finish()
//                }
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
        }
        return value
    }




    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}