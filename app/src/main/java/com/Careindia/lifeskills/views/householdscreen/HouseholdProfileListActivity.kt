package com.careindia.lifeskills.views.householdscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_household_profile_list)

        tv_title.text = "Household Profile List"

        img_Add.setOnClickListener {
            var intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
        }

        fillRecyclerView()

    }

    private fun fillRecyclerView() {
        val householdProfileAdapter = HouseholdProfileAdapter(this)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = householdProfileAdapter
    }


    override fun onBackPressed() {
       // super.onBackPressed()

        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}