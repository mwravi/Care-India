package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFourth : AppCompatActivity() {

    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_fourth)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {
        btn_save.setOnClickListener {
               if (CheckValidation()==0) {
                 }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitythird::class.java)
            startActivity(intent)
            finish()
        }

        validate!!.fillSpinner(
            this,
            spin_meeting_conducted,
            resources.getString(R.string.select),
            mstCommonViewModel,
            21
        )

        validate!!.fillSpinner(
            this,
            spin_frequency_of_meetings,
            resources.getString(R.string.select),
            mstCommonViewModel,
            22
        )

        validate!!.fillSpinner(
            this,
            spin_attending_meeting,
            resources.getString(R.string.select),
            mstCommonViewModel,
            23
        )

        validate!!.fillSpinner(
            this,
            spin_meeting_schedule,
            resources.getString(R.string.select),
            mstCommonViewModel,
            24
        )
        validate!!.fillSpinner(
            this,
            spin_record_book,
            resources.getString(R.string.select),
            mstCommonViewModel,
            25
        )
        validate!!.fillSpinner(
            this,
            spin_record_book_update,
            resources.getString(R.string.select),
            mstCommonViewModel,
            26
        )
        validate!!.dynamicMultiCheck(this, chk_options_below, mstCommonViewModel,27)

        validate!!.fillSpinner(
            this,
            spin_services_schemes,
            resources.getString(R.string.select),
            mstCommonViewModel,
            28
        )
        validate!!.fillSpinner(
            this,
            spin_enterprise_business,
            resources.getString(R.string.select),
            mstCommonViewModel,
            29
        )

        validate!!.dynamicMultiCheck(this, chk_collective_plan, mstCommonViewModel,30)



    }

    fun CheckValidation():Int {

        var iValue = 0;
       if (spin_meeting_conducted.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_meeting_conducted,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q401_are_meetings_conducted_in_your_sangha_group_collective),
            )
        } else if (spin_frequency_of_meetings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_frequency_of_meetings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q402_what_is_the_frequency_of_meetings),
            )
        }  else if (et_other_q402a.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q402a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q402a_please_specify_others),
            )
        }  else if (spin_attending_meeting.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_attending_meeting,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q403_regularity_of_members_attending_meeting),
            )
        } else if (spin_meeting_schedule.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_meeting_schedule,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q404_does_the_meetings_happen_as_per_the_schedule_check_meeting_register),
            )
        }  else if (spin_record_book.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_record_book,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q405_does_your_sangha_group_collective_have_a_record_book_check_for_the_record_book),
            )
        } else if (spin_record_book_update.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_record_book_update,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q406_is_the_record_book_register_updated_in_every_meeting),
            )
        } else if (et_other_q501a.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q501a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501a_please_specify_the_others),
            )
        } else if (et_other_q501b.text.toString().length == 0 ) {
           iValue = 1
           validate!!.CustomAlertEdit(
               this,
               et_other_q501b,
               resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501b_what_service_services_you_are_getting_through_this_linkage_more_than_one_response_upto_three_is_possible),
           )
       } else if (spin_services_schemes.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_services_schemes,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q502_is_your_sangha_collective_currently_availing_any_services_schemes),
            )
        }  else if (et_details_service.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_details_service,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502a_please_provide_details_of_the_service_scheme_that_you_are_availing_more_than_one_response_upto_three_is_possible),
            )
        } else if (et_service_provider.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_service_provider,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502b_from_which_department_service_provider_you_are_availing_this_scheme_service),
            )
        } else if (spin_enterprise_business.selectedItemPosition == 0) {
           iValue = 1
           validate!!.CustomAlertSpinner(
               this,
               spin_enterprise_business,
               resources.getString(R.string.please_select) + " " + resources.getString(R.string.q601_is_your_sangha_collective_members_interested_in_taking_up_any_collective_enterprise_business),
           )
       } else if (et_others_q602a.text.toString().length == 0 ) {
           iValue = 1
           validate!!.CustomAlertEdit(
               this,
               et_others_q602a,
               resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q602a_please_specify_others),
           )
       }
        return iValue;
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}