package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.careindia.lifeskills.R
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_profile_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_profile_list)
        tv_title.setText(resources.getString(R.string.colective_profile))

        img_add.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}