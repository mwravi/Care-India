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
import com.careindia.lifeskills.databinding.ActivityCollectivemeetingthirdBinding
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.meetingtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectivemeetingthirdBinding
    lateinit var collectiveMeetingViewModel: CollectiveMeetingViewModel
    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collectivemeetingthird)
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
            if (checkValidation()==0) {
                collectiveMeetingViewModel.updateSaving(this)
                CustomAlert(resources.getString(R.string.data_saved_successfully))
            }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingSecActivity::class.java)
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
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_first.setOnClickListener {

            val intent = Intent(this, CollectiveMeetingActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingSecActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        /* ll_third.setOnClickListener {
             if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                 val intent = Intent(this, CollectiveMeetingThirdActivity::class.java)
                 startActivity(intent)
                 finish()
             }
         }*/

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
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
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
        val intent = Intent(this, CollectiveMeetingListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun checkValidation(): Int {

        var iValue = 0

        if (et_Savings.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_Savings,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.savings),
            )
        } else if (et_internal_lending.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_internal_lending,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.internal_lending),
            )
        } else if (et_bank_loans.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bank_loans,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.bank_loans),
            )
        } else if (et_schemes_and_services.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_schemes_and_services,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.schemes_and_services),
            )
        } else if (et_entrepreneurial_activities.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_entrepreneurial_activities,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.entrepreneurial_activities),
            )
        } else if (et_training_activities.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_training_activities,
                 resources.getString(R.string.please_enter) + " " + resources.getString(R.string.training_activities),
            )
        } else if (et_leadership.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_leadership,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.change_in_leadership_member_position),
            )
        } else if (et_change.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_change,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.change_in_group_members),
            )
        } else if (et_others.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_others,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.others_if_any),
            )
        } else if (et_savings.text.toString().length == 0) {
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
        collectiveMeetingViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            ?.observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_Savings.setText(it.get(0).Savings.toString())
                    et_internal_lending.setText(it.get(0).InternalLending.toString())
                    et_bank_loans.setText(it.get(0).BankLoans.toString())
                    et_schemes_and_services.setText(it.get(0).Schemes_and_services.toString())
                    et_entrepreneurial_activities.setText(it.get(0).EntrepreneurialActivities.toString())
                    et_training_activities.setText(it.get(0).TrainingActivities.toString())
                    et_leadership.setText(it.get(0).Change_position.toString())
                    et_change.setText(it.get(0).ChangeGrpMember.toString())
                    et_others.setText(it.get(0).OtherPoint.toString())
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

}