package com.careindia.lifeskills.views.IMProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.careindia.lifeskills.R
import com.careindia.lifeskills.ui.base.BaseActivity
import com.careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class IMProfileOneActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    private val imProfileViewModel by viewModel<IMProfileViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_one)
        validate = Validate(this)
        tv_title.text = "IM Profile"

        initializeController()
    }


    override fun initializeController() {

      //apply click on view
        applyClickOnView()

        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngjgDate
            )

        }
        validate!!.fillSpinnerLanguage(
            this,
            spin_name_crp,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_name_crp)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_SupervisingFC,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_SupervisingFC)
        )

        validate!!.fillSpinnerLanguage(
            this,
            spin_sexrepo,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_sexrepo)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_casterespo,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_casterespo)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_marital,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_marital)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_state,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_state)
        )

        validate!!.fillSpinnerLanguage(
            this,
            spin_access_sphone,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_education,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_education)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_can_read,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )

    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_next.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_next -> {
                if (imProfileViewModel.isValid()) {

                }
                var intent = Intent(this, IMProfileTwoActivity::class.java)
                startActivity(intent)
//                if (CheckValidation() == 1) {

//                }
            }
        }
    }


    private fun CheckValidation(): Int {
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
}