package com.careindia.lifeskills.views.collectivemeeting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.meetinglist.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingListActivity : AppCompatActivity() {

    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meetinglist)
        validate = Validate(this)

        tv_title.text = getString(R.string.collmeetinglist)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, "")
            val intent = Intent(this, CollectiveMeetingActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, "")
            val intent = Intent(this, CollectiveMeetingActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}