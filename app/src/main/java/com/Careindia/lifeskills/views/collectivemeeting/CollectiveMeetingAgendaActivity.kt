package com.careindia.lifeskills.views.collectivemeeting

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectivemeetingthirdBinding
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.*
import kotlinx.android.synthetic.main.activity_collectivemeetingthird.btn_bottom
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.meetingtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingAgendaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectivemeetingthirdBinding
    lateinit var collectiveMeetingViewModel: CollectiveMeetingViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collectivemeetingthird)
        validate = Validate(this)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

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
            if (checkValidation() == 0) {
                collectiveMeetingViewModel.updateSaving(this)
                val intent = Intent(this, CollectiveMeetingCollectionActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingAttendanceActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Saving....
        et_Savings1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_Savings2.isEnabled = true
                } else {
                    et_Savings2.isEnabled = false
                    et_Savings2.setText("")
                }
            }
        })

        et_Savings2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_Savings3.isEnabled = true
                } else {
                    et_Savings3.isEnabled = false
                    et_Savings3.setText("")
                }
            }
        })

        //internal_lending....
        et_internal_lending1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_internal_lending2.isEnabled = true
                } else {
                    et_internal_lending2.isEnabled = false
                    et_internal_lending2.setText("")
                }
            }
        })

        et_internal_lending2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_internal_lending3.isEnabled = true
                } else {
                    et_internal_lending3.isEnabled = false
                    et_internal_lending3.setText("")
                }
            }
        })

        //bank_loans....
        et_bank_loans1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_bank_loans2.isEnabled = true
                } else {
                    et_bank_loans2.isEnabled = false
                    et_bank_loans2.setText("")
                }
            }
        })

        et_bank_loans2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_bank_loans3.isEnabled = true
                } else {
                    et_bank_loans3.isEnabled = false
                    et_bank_loans3.setText("")
                }
            }
        })

        //schemes_and_services....
        et_schemes_and_services1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_schemes_and_services2.isEnabled = true
                } else {
                    et_schemes_and_services2.isEnabled = false
                    et_schemes_and_services2.setText("")
                }
            }
        })

        et_schemes_and_services2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_schemes_and_services3.isEnabled = true
                } else {
                    et_schemes_and_services3.isEnabled = false
                    et_schemes_and_services3.setText("")
                }
            }
        })
//entrepreneurial_activities....
        et_entrepreneurial_activities1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_entrepreneurial_activities2.isEnabled = true
                } else {
                    et_entrepreneurial_activities2.isEnabled = false
                    et_entrepreneurial_activities2.setText("")
                }
            }
        })

        et_entrepreneurial_activities2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_entrepreneurial_activities3.isEnabled = true
                } else {
                    et_entrepreneurial_activities3.isEnabled = false
                    et_entrepreneurial_activities3.setText("")
                }
            }
        })

//training_activities....
        et_training_activities1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_training_activities2.isEnabled = true
                } else {
                    et_training_activities2.isEnabled = false
                    et_training_activities2.setText("")
                }
            }
        })

        et_training_activities2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_training_activities3.isEnabled = true
                } else {
                    et_training_activities3.isEnabled = false
                    et_training_activities3.setText("")
                }
            }
        })

//leadership....
        et_leadership1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_leadership2.isEnabled = true
                } else {
                    et_leadership2.isEnabled = false
                    et_leadership2.setText("")
                }
            }
        })

        et_leadership2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_leadership3.isEnabled = true
                } else {
                    et_leadership3.isEnabled = false
                    et_leadership3.setText("")
                }
            }
        })

//change....
        et_change1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_change2.isEnabled = true
                } else {
                    et_change2.isEnabled = false
                    et_change2.setText("")
                }
            }
        })

        et_change2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_change3.isEnabled = true
                } else {
                    et_change3.isEnabled = false
                    et_change3.setText("")
                }
            }
        })

//others....
        et_others1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_others2.isEnabled = true
                } else {
                    et_others2.isEnabled = false
                    et_others2.setText("")
                }
            }
        })

        et_others2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_others3.isEnabled = true
                } else {
                    et_others3.isEnabled = false
                    et_others3.setText("")
                }
            }
        })


        bottomclick()
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMeetingGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
        fillMulticheck()
    }

    fun fillMulticheck() {

        validate!!.dynamicMultiCheckforAgenda(
            this,
            check_meeting_points,
            mstLookupViewModel,
            109,
            1,
            et_others1,
            ll_others,
            et_Savings1,
            ll_Savings,
            et_internal_lending1,
            ll_internal_lending,
            et_bank_loans1,
            ll_bank_loans,
            et_schemes_and_services1,
            ll_schemes_and_services,
            et_entrepreneurial_activities1,
            ll_entrepreneurial_activities,
            et_training_activities1,
            ll_training_activities,
            et_leadership1,
            ll_leadership,
            et_change1,
            ll_change
        )
    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
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

        /* ll_third.setOnClickListener {
             if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                 val intent = Intent(this, CollectiveMeetingThirdActivity::class.java)
                 startActivity(intent)
                 finish()
             }
         }*/

        ll_fourth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingCollectionActivity::class.java)
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

        if (et_Savings1.text.toString().length == 0 && ll_Savings.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_Savings1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.savings),
            )
        } else if (et_internal_lending1.text.toString().length == 0 && ll_internal_lending.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_internal_lending1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.internal_lending),
            )
        } else if (et_bank_loans1.text.toString().length == 0 && ll_bank_loans.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bank_loans1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.bank_loans),
            )
        } else if (et_schemes_and_services1.text.toString().length == 0 && ll_schemes_and_services.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_schemes_and_services1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.schemes_and_services),
            )
        } else if (et_entrepreneurial_activities1.text.toString().length == 0 && ll_entrepreneurial_activities.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_entrepreneurial_activities1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.entrepreneurial_activities),
            )
        } else if (et_training_activities1.text.toString().length == 0 && ll_training_activities.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_training_activities1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.training_activities),
            )
        } else if (et_leadership1.text.toString().length == 0 && ll_leadership.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_leadership1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.change_in_leadership_member_position),
            )
        } else if (et_change1.text.toString().length == 0 && ll_change.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_change1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.change_in_group_members),
            )
        } else if (et_others1.text.toString().length == 0 && ll_others.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_others1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.others_if_any),
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
        )?.observe(this, Observer {
            if (it != null && it.size > 0) {

                if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                    btn_bottom.visibility = View.GONE
                }else{
                    btn_bottom.visibility = View.VISIBLE
                }

                validate!!.SetAnswerTypeCheckBoxButton(
                    check_meeting_points,
                    it.get(0).MeetingDiscussion_Points
                )

//                    et_Savings.setText(it.get(0).Savings.toString())
                it.get(0).Savings?.let { it1 ->
                    validate!!.setAgenda(
                        et_Savings1,
                        et_Savings2,
                        et_Savings3,
                        it1
                    )
                }
//                    et_internal_lending.setText(it.get(0).InternalLending.toString())

                it.get(0).InternalLending?.let { it1 ->
                    validate!!.setAgenda(
                        et_internal_lending1,
                        et_internal_lending2,
                        et_internal_lending3,
                        it1
                    )
                }

//                    et_bank_loans.setText(it.get(0).BankLoans.toString())
                it.get(0).BankLoans?.let { it1 ->
                    validate!!.setAgenda(
                        et_bank_loans1,
                        et_bank_loans2,
                        et_bank_loans3,
                        it1
                    )
                }
//                    et_schemes_and_services.setText(it.get(0).Schemes_and_services.toString())
                it.get(0).Schemes_and_services?.let { it1 ->
                    validate!!.setAgenda(
                        et_schemes_and_services1,
                        et_schemes_and_services2,
                        et_schemes_and_services3,
                        it1
                    )
                }
//                    et_entrepreneurial_activities.setText(it.get(0).EntrepreneurialActivities.toString())
                it.get(0).EntrepreneurialActivities?.let { it1 ->
                    validate!!.setAgenda(
                        et_entrepreneurial_activities1,
                        et_entrepreneurial_activities2,
                        et_entrepreneurial_activities3,
                        it1
                    )
                }
//                    et_training_activities.setText(it.get(0).TrainingActivities.toString())
                it.get(0).TrainingActivities?.let { it1 ->
                    validate!!.setAgenda(
                        et_training_activities1,
                        et_training_activities2,
                        et_training_activities3,
                        it1
                    )
                }
//                    et_leadership.setText(it.get(0).Change_position.toString())
                it.get(0).Change_position?.let { it1 ->
                    validate!!.setAgenda(
                        et_leadership1,
                        et_leadership2,
                        et_leadership3,
                        it1
                    )
                }
//                    et_change.setText(it.get(0).ChangeGrpMember.toString())
                it.get(0).ChangeGrpMember?.let { it1 ->
                    validate!!.setAgenda(
                        et_change1,
                        et_change2,
                        et_change3,
                        it1
                    )
                }
//                    et_others.setText(it.get(0).OtherPoint.toString())
                it.get(0).OtherPoint?.let { it1 ->
                    validate!!.setAgenda(
                        et_others1,
                        et_others2,
                        et_others3,
                        it1
                    )
                }


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
            horizontalScroll.smoothScrollBy(500, 0)
        }, 100)
    }
}