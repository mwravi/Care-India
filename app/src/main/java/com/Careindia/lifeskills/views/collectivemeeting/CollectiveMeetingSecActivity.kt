package com.careindia.lifeskills.views.collectivemeeting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingSecActivity:AppCompatActivity() {

    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collectivemeetingsec)
        validate = Validate(this)
        tv_title.text = getString(R.string.collmeeting)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingListActivity::class.java)
            startActivity(intent)
            finish()
        }
       /* btn_save.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingSecActivity::class.java)
            startActivity(intent)
            finish()
        }*/
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, CollectiveMeetingListActivity::class.java)
        startActivity(intent)
        finish()
    }
}