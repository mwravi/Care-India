package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFourth : AppCompatActivity() {

    var validate: Validate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_fourth)
        validate = Validate(this)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {
        btn_save.setOnClickListener {
            /* val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
             startActivity(intent)
             finish()*/
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitythird::class.java)
            startActivity(intent)
            finish()
        }

        validate!!.fillSpinnerLanguage(
            this,
            spin_meeting_conducted,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )

        validate!!.fillSpinnerLanguage(
            this,
            spin_frequency_of_meetings,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )

        validate!!.fillSpinnerLanguage(
            this,
            spin_meeting_schedule,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_record_book,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_record_book_update,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )

        validate!!.fillSpinnerLanguage(
            this,
            spin_services_schemes,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_enterprise_business,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}