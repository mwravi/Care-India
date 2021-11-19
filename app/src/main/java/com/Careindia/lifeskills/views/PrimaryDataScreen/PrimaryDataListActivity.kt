package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_list)

        tv_title.text = "Primary Data List"

        /*img_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/

        img_Add.setOnClickListener {
            val intent = Intent(this, PrimaryDataFirstActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()

    }

    private fun fillRecyclerView() {
        val primaryDataAdapter = PrimaryDataAdapter(this)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = primaryDataAdapter
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}