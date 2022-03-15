package com.careindia.lifeskills.views.homescreen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileListActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingListActivity
import com.careindia.lifeskills.views.householdscreen.HouseholdProfileListActivity
import com.careindia.lifeskills.views.improfile.IMProfileListActivity
import com.careindia.lifeskills.views.loginscreen.LoginActivity
import com.careindia.lifeskills.views.prepostassessment.PrePostAssessmentListActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataListActivity
import com.careindia.lifeskills.views.psychometricscreen.PsychometricListActivity
import com.careindia.lifeskills.views.synchronization.SyncronizationActivity
import kotlinx.android.synthetic.main.activity_home_dashboard.*
import kotlinx.android.synthetic.main.homescreen.*

class HomeDashboardActivity : BaseActivity(), View.OnClickListener {

    var validate: Validate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dashboard)
        validate = Validate(this)
        validate!!.SaveSharepreferenceInt(AppSP.iLanguageID, 1)
        initializeController()
        tv_title.text = resources.getString(R.string.home_screen)
        img_logout.setOnClickListener {
            CustomAlert(resources.getString(R.string.are_you_sure_log_out))
        }
    }

    override fun initializeController() {
        applyClickOnView()
    }

    private fun applyClickOnView() {
        linear_household.setOnClickListener(this)
        linear_individual.setOnClickListener(this)
        ll_progress.setOnClickListener(this)
        ll_collective.setOnClickListener(this)
        ll_collective_meeting.setOnClickListener(this)
        ll_sync.setOnClickListener(this)
        ll_psychometric.setOnClickListener(this)
        ll_pre_post_assessment.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.linear_household -> {

                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                val intent = Intent(this, HouseholdProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.linear_individual -> {
                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                validate!!.SaveSharepreferenceString(AppSP.IMClick,"Dashboard")
                val intent = Intent(this, IMProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_progress -> {
                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                val intent = Intent(this, PrimaryDataListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_collective -> {
                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                val intent = Intent(this, CollectiveProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_collective_meeting -> {
                val intent = Intent(this, CollectiveMeetingListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_psychometric -> {
                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                val intent = Intent(this, PsychometricListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ll_sync -> {
                val intent = Intent(this, SyncronizationActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.ll_pre_post_assessment -> {
                val intent = Intent(this, PrePostAssessmentListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun CustomAlert(
        msg: String?
    ) { // Create custom dialog object
        val dialog = Dialog(this)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.logout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        val btn_no =
            dialog.findViewById<View>(R.id.btn_no) as Button
        btnok.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        btn_no.setOnClickListener {
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }


    override fun onBackPressed() {
//        super.onBackPressed()
    }
}