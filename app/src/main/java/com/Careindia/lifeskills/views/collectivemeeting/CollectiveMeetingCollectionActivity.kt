package com.careindia.lifeskills.views.collectivemeeting

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
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectivemeetingFourthBinding
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting_fourth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.meetingtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingCollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectivemeetingFourthBinding
    lateinit var collectiveMeetingViewModel: CollectiveMeetingViewModel
    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collectivemeeting_fourth)
        validate = Validate(this)
        val collectivedao = CareIndiaApplication.database?.collectiveMeetingDao()
        val collectiveRepository = CollectiveMeetingRepository(collectivedao!!)
        collectiveMeetingViewModel =
            ViewModelProvider(this, CollectiveMeetingViewModelFactory(collectiveRepository))[
                    CollectiveMeetingViewModel::class.java]
        binding.collectiveMeetingViewModel = collectiveMeetingViewModel
        binding.lifecycleOwner = this
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
        btn_save.setOnClickListener {
//            if (checkValidation() == 0) {
                collectiveMeetingViewModel.updateCollection(this)
                CustomAlert(resources.getString(R.string.data_saved_successfully))
//            }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingAgendaActivity::class.java)
            startActivity(intent)
            finish()
        }
        bottomclick()
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMeetingGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_first.setOnClickListener {

            val intent = Intent(this, CollectiveMeetingDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingAttendanceActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingAgendaActivity::class.java)
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
        val view: View = inflater.inflate(R.layout.dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingListActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }


    override fun onBackPressed() {
//        super.onBackPressed()
        /* val intent = Intent(this, CollectiveMeetingListActivity::class.java)
         startActivity(intent)
         finish()*/
    }

    fun checkValidation(): Int {

        var iValue = 0
        if (et_savings.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_savings,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_savings_for_the_month_in_inr),
            )
        } else if (et_loanlending.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_loanlending,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_loan_availed_through_internal_lending_within_the_group_for_the_month_in_inr),
            )
        } else if (et_amount.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_amount,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_amount_accrued_from_interest_in_a_month_in_inr),
            )
        }
        return iValue
    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)
        collectiveMeetingViewModel.getCollectivedatabyGuid(
            validate!!.returnStringValue(
                collectiveGuid
            )
        )
            ?.observe(this, Observer {
                if (it != null && it.size > 0) {
                    if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                        btn_bottom.visibility = View.GONE
                    } else {
                        btn_bottom.visibility = View.VISIBLE
                    }
                    it.get(0).TotalSavings?.let { it1 -> setDefBlank(et_savings, it1) }
                    it.get(0).LoanAvailed_int?.let { it1 -> setDefBlank(et_loanlending, it1) }
                    it.get(0).LoanAvailed_ext?.let { it1 -> setDefBlank(et_loanschemes, it1) }
                    it.get(0).Interest_acc?.let { it1 -> setDefBlank(et_amount, it1) }


                }
            })
    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(1000, 0)
        }, 100)
    }
}