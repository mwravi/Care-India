package com.careindia.lifeskills.views.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileListActivity
import com.careindia.lifeskills.views.householdscreen.HouseholdProfileListActivity
import com.careindia.lifeskills.views.improfile.IMProfileListActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataListActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricFirstActivity
import kotlinx.android.synthetic.main.activity_home_dashboard.*

class HomeDashboardActivity : BaseActivity(), View.OnClickListener {

    var validate: Validate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dashboard)
        validate = Validate(this)
        validate!!.SaveSharepreferenceInt(AppSP.iLanguageID,1)
        initializeController()
    }

    override fun initializeController() {
        applyClickOnView()
    }

    private fun applyClickOnView() {
        linear_household.setOnClickListener(this)
        linear_individual.setOnClickListener(this)
        ll_progress.setOnClickListener(this)
        ll_collective.setOnClickListener(this)
        ll_report.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.linear_household -> {
                val intent = Intent(this, HouseholdProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.linear_individual -> {
                val intent = Intent(this, IMProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_progress -> {
                val intent = Intent(this, PrimaryDataListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_collective -> {
                val intent = Intent(this, CollectiveProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_collective_meeting -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_attendence -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_report -> {
                val intent = Intent(this, PsychometricFirstActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_sync -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}