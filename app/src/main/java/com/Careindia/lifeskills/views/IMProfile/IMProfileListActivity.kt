package com.careindia.lifeskills.views.improfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_list)
        tv_title.text = "IM Profile List"


        img_Add.setOnClickListener {
            val intent = Intent(this, IMProfileOneActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()
    }


    private fun fillRecyclerView() {
        val imprfAdapter = IMProfileAdapter(this)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = imprfAdapter
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}