package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.views.adapter.PrimaryDataAdapter
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataListActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_data_list)

        tv_title.text = resources.getString(R.string.primary_data_list)

        initializeController()

    }

    override fun initializeController() {
        fillRecyclerView()
        applyClickOnView()
    }

    private fun fillRecyclerView() {
        val primaryDataAdapter = PrimaryDataAdapter(this)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = primaryDataAdapter
    }

    private fun applyClickOnView() {
        img_Add.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_Add -> {
                val intent = Intent(this, PrimaryDataFirstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}