package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivitySec : AppCompatActivity() {

    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_second)
        validate= Validate(this)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {
        btn_save.setOnClickListener {
           val intent = Intent(this, CollectiveProfileActivitythird::class.java)
           startActivity(intent)
            finish()
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        validate!!.fillSpinnerLanguage(
            this,
            spin_head_group_sex,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_member_sex,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_savings_account,
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