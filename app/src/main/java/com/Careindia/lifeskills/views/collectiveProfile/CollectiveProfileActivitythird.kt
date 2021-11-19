package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivitythird : AppCompatActivity() {

    var validate: Validate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_third)
        validate = Validate(this)
        tv_title.text = "Collective Profile"
        initCall()

    }

    fun initCall() {
        btn_save.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
            startActivity(intent)
            finish()
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
            finish()
        }

        validate!!.fillSpinnerLanguage(
            this,
            spin_rotation_of_roles,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_office_bearer,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_bank_account,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_group_savings,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_frequency_group_savings,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_regular_savings,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_avial_loan,
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