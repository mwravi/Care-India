package com.Careindia.lifeskills.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Careindia.lifeskills.R
import com.Careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
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
            var intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)

        }

        /*et_formfilngDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngDate
            )

        }*/


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
            spin_can_read,
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
            spin_access_sphone,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_acess_mob_data,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_cate_picker_belong,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_cate_picker_belong)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_source_income,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_source_income)
        )
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
        validate!!.fillSpinnerLanguage(
            this,
            spin_type_emp,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_type_emp)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_sell_waste_collect,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_sell_waste_collect)
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

        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))
        validate!!.fillCheckBoxes(this, multiCheck, resources.getStringArray(R.array.language))
        validate!!.fillCheckBoxes(
            this,
            lang_prefer_mobile_use,
            resources.getStringArray(R.array.language)
        )
        validate!!.fillCheckBoxes(
            this,
            skills_jobs_picking,
            resources.getStringArray(R.array.skills_jobs_picking)
        )

    }

}