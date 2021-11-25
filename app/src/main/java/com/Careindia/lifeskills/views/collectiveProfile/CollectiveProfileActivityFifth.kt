package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFifth : AppCompatActivity() {

    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_fifth)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {
        btn_save.setOnClickListener {
              /* if (CheckValidation()==0) {
                 }*/

            val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
            startActivity(intent)
            finish()

        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
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



    }

    fun CheckValidation():Int {

        var iValue = 0;

        if (et_challenges.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_challenges,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q310_what_are_the_challenges_in_availing_loan_minimum_1_and_maximum_upto_3_responses_possible),
            )
        } else if (spin_meeting_conducted.selectedItemPosition == 0) {
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