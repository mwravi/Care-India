package com.careindia.lifeskills.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {
    var validate: Validate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        validate = Validate(this)
        tv_title.text = "Home"


        initCall()
    }

    fun initCall(){

        btn_save.setOnClickListener {

        }

        /*et_formfilngDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngDate
            )

        }*/





        validate!!.fillSpinnerLanguage(
            this,
            spin_secondary_income,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_what_secondary_income,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_source_income)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_have_adhar,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_have_voter,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_have_pan,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_have_income,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_have_caste,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_svg_bank_act,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_availed_services_past,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_avail_any_scheme,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_new_jobs_business,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_alternative_get_opportunity,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_alternative_get_opportunity)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_member_cig_shg,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )




        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))


        validate!!.fillCheckBoxes(
            this,
            skills_jobs_picking,
            resources.getStringArray(R.array.skills_jobs_picking)
        )

    }


    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}