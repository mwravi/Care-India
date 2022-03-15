package com.careindia.lifeskills.views.collectiveProfile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileSixthBinding
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collective_profile_sixth.*
import kotlinx.android.synthetic.main.activity_collective_profile_sixth.btn_bottom
import kotlinx.android.synthetic.main.activity_improfile_demographic.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CollectiveProfileActivitySixth : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityCollectiveProfileSixthBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_sixth)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val collectiveRepository = CollectiveRepository(collectivedao!!, mstDistrictDao)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                    CollectiveViewModel::class.java]

        binding.collectiveViewModel = collectiveViewModel
        binding.lifecycleOwner = this
        tv_title.text = getString(R.string.collprofile)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
        btn_save.text = resources.getString(R.string.save_close)
        initializeController()
        bottomCLick()

        //Q501b...//

        et_other_q501b1.addTextChangedListener(object : TextWatcher {
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
                    et_other_q501b2.isEnabled = true
                } else {
                    et_other_q501b2.isEnabled = false
                    et_other_q501b2.setText("")
                }
            }
        })

        et_other_q501b2.addTextChangedListener(object : TextWatcher {
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
                    et_other_q501b3.isEnabled = true
                } else {
                    et_other_q501b3.isEnabled = false
                    et_other_q501b3.setText("")
                }
            }
        })
        et_other_q501b3.addTextChangedListener(object : TextWatcher {
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
                    et_other_q501b4.isEnabled = true
                } else {
                    et_other_q501b4.isEnabled = false
                    et_other_q501b4.setText("")
                }

            }
        })
        et_other_q501b4.addTextChangedListener(object : TextWatcher {
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
                    et_other_q501b5.isEnabled = true
                } else {
                    et_other_q501b5.isEnabled = false
                    et_other_q501b5.setText("")
                }
            }
        })

        //..Q502a..//


        et_details_service1.addTextChangedListener(object : TextWatcher {
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
                    et_details_service2.isEnabled = true
                } else {
                    et_details_service2.isEnabled = false
                    et_details_service2.setText("")
                }
            }
        })

        et_details_service2.addTextChangedListener(object : TextWatcher {
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
                    et_details_service3.isEnabled = true
                } else {
                    et_details_service3.isEnabled = false
                    et_details_service3.setText("")
                }
            }
        })

        et_details_service3.addTextChangedListener(object : TextWatcher {
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
                    et_details_service4.isEnabled = true
                } else {
                    et_details_service4.isEnabled = false
                    et_details_service4.setText("")
                }

            }
        })


        et_details_service4.addTextChangedListener(object : TextWatcher {
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
                    et_details_service5.isEnabled = true
                } else {
                    et_details_service5.isEnabled = false
                    et_details_service5.setText("")
                }
            }
        })

        //..Q502b..//

        et_service_provider1.addTextChangedListener(object : TextWatcher {
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
                    et_service_provider2.isEnabled = true
                } else {
                    et_service_provider2.isEnabled = false
                    et_service_provider2.setText("")
                }
            }
        })

        et_service_provider2.addTextChangedListener(object : TextWatcher {
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
                    et_service_provider3.isEnabled = true
                } else {
                    et_service_provider3.isEnabled = false
                    et_service_provider3.setText("")
                }
            }
        })

        et_service_provider3.addTextChangedListener(object : TextWatcher {
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
                    et_service_provider4.isEnabled = true
                } else {
                    et_service_provider4.isEnabled = false
                    et_service_provider4.setText("")
                }

            }
        })
        et_service_provider4.addTextChangedListener(object : TextWatcher {
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
                    et_service_provider5.isEnabled = true
                } else {
                    et_service_provider5.isEnabled = false
                    et_service_provider5.setText("")
                }
            }
        })

    }


    override fun initializeController() {
        applyClickOnView()
        fillspinner()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)



        rg_services_schemes.setOnCheckedChangeListener { radioGroup, i ->
            val lookupCode = validate!!.GetAnswerTypeRadioButtonIDNew(rg_services_schemes)
            if (lookupCode == 1) {
                lay_details_service.visibility = VISIBLE
                lay_service_provider.visibility = VISIBLE
            } else {
                lay_details_service.visibility = GONE
                lay_service_provider.visibility = GONE
                et_details_service1.setText("")
                et_details_service2.setText("")
                et_details_service3.setText("")
                et_details_service4.setText("")
                et_details_service5.setText("")
                et_service_provider1.setText("")
                et_service_provider2.setText("")
                et_service_provider3.setText("")
                et_service_provider4.setText("")
                et_service_provider5.setText("")
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }

        rg_enterprise_business.setOnCheckedChangeListener { radioGroup, i ->
            val lookupCode = validate!!.GetAnswerTypeRadioButtonIDNew(rg_enterprise_business)
            if (lookupCode == 1) {
                lay_others_q602.visibility = VISIBLE
            } else {
                lay_others_q602.visibility = GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    senddata()
                    collectiveViewModel.updatecollectiveprofileSix(this)


                    CustomAlert(this, resources.getString(R.string.data_saved_successfully))
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun fillspinner() {


        validate!!.fillradioNew(
            this,
            rg_services_schemes,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradioNew(
            this,
            rg_enterprise_business,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.dynamicMultiCheckChange501(
            this,
            chk_options_below,
            mstLookupViewModel,
            26,
            iLanguageID,
            et_other_q501a,
            lay_other_q501a, et_other_q501b1, et_other_q501b2,
            et_other_q501b3, et_other_q501b4,
            et_other_q501b5, lay_other_q501b
        )

        validate!!.dynamicMultiCheckChange(
            this,
            chk_collective_plan,
            mstLookupViewModel,
            27,
            iLanguageID,
            et_others_q602a,
            lay_others_q602a
        )


    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }

                    et_other_q501a.setText(it.get(0).Linkages_oth)

                    it.get(0).Linkage_Services?.let { it1 ->
                        validate!!.setSchemes(
                            et_other_q501b1, et_other_q501b2, et_other_q501b3, et_other_q501b4,
                            et_other_q501b5,
                            it1
                        )
                    }

                    it.get(0).Services_availed?.let { it1 ->
                        validate!!.setSchemes(
                            et_details_service1,
                            et_details_service2,
                            et_details_service3,
                            et_details_service4,
                            et_details_service5,
                            it1
                        )
                    }

                    it.get(0).Services_dept?.let { it1 ->
                        validate!!.setSchemes(
                            et_service_provider1,
                            et_service_provider2,
                            et_service_provider3,
                            et_service_provider4,
                            et_service_provider5,
                            it1
                        )
                    }


                    et_others_q602a.setText(it.get(0).Collective_opp_Other)


                    validate!!.SetAnswerTypeRadioButton(
                        rg_services_schemes,
                        it.get(0).IsService_availed!!
                    )
                    validate!!.SetAnswerTypeRadioButton(
                        rg_enterprise_business,
                        it.get(0).Collective_Schemes!!
                    )

                    validate!!.SetAnswerTypeCheckBoxButton(chk_options_below, it.get(0).Linkages)
                    validate!!.SetAnswerTypeCheckBoxButton(
                        chk_collective_plan,
                        it.get(0).Collective_opp
                    )

                }
            })
    }

    fun GetAnswerTypeCheckBoxButtonIDChange(linear: LinearLayout): String {
        var QusAns = ""
        for (i in 0 until linear.childCount) {

            val checkbox = linear.getChildAt(i) as CheckBox
            checkbox.setOnClickListener {
                if (checkbox.isChecked) {
                    if (QusAns.length == 0) {
                        QusAns = checkbox.id.toString()
                    } else {
                        QusAns = (QusAns + "," + checkbox.id.toString())

                        //   Log.i("MYTAGTWO____-----++", checkbox.id.toString

                        /*for (i in QusAns) {
                            if (i.equals("7")) {
                                break
                            }*/
                        // }
                    }
                }
            }
        }
        return QusAns
    }

    fun senddata() {
        collectiveViewModel.collectDataSix(
            validate!!.GetAnswerTypeCheckBoxButtonID(chk_options_below),
            validate!!.GetAnswerTypeCheckBoxButtonID(chk_collective_plan),
        )
    }

    fun checkValidation(): Int {

        var iValue = 0
        if (validate!!.GetAnswerTypeCheckBoxButtonID(chk_options_below).equals("")) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q501_has_the_sangha_group_collective_established_any_linkage_with_options_below),
            )
        } else if (et_other_q501a.text.toString().length == 0 && lay_other_q501a.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q501a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501a_please_specify_the_others),
            )
        } else if (et_other_q501b1.text.toString().length == 0 && lay_other_q501b.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q501b1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501b_what_service_services_you_are_getting_through_this_linkage_more_than_one_response_upto_three_is_possible),
            )
        } else if (rg_services_schemes.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502_is_your_sangha_collective_currently_availing_any_services_schemes),
            )
        } else if (et_details_service1.text.toString().length == 0 && lay_details_service.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_details_service1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502a_please_provide_details_of_the_service_scheme_that_you_are_availing_more_than_one_response_upto_three_is_possible),
            )
        } else if (et_service_provider1.text.toString().length == 0 && lay_service_provider.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_service_provider1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502b_from_which_department_service_provider_you_are_availing_this_scheme_service),
            )
        } else if (rg_enterprise_business.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q601_is_your_sangha_collective_members_interested_in_taking_up_any_collective_enterprise_business),
            )
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(chk_collective_plan)
                .equals("") && lay_others_q602.visibility == VISIBLE
        ) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q602_what_business_would_the_collective_plan_to_do_if_given_an_opportunity),
            )
        } else if (et_others_q602a.text.toString().length == 0 && lay_others_q602a.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_others_q602a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q602a_please_specify_others),
            )
        }
        return iValue
    }

    fun bottomCLick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_six.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

        lay_first.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
            finish()
        }
        ll_third.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityThird::class.java)
            startActivity(intent)
            finish()
        }
        ll_fourth.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
            startActivity(intent)
            finish()
        }
        ll_fifth.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
            startActivity(intent)
            finish()
        }
        /*   ll_six.setOnClickListener {
               val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
               startActivity(intent)
               finish()
           }*/
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        /*   val intent = Intent(this, CollectiveProfileListActivity::class.java)
           startActivity(intent)
           finish()*/
    }


    fun CustomAlert(
        collectiveProfileMemberActivity: CollectiveProfileActivitySixth,
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
            val intent =
                Intent(collectiveProfileMemberActivity, CollectiveProfileListActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(2000, 0)
        }, 100)
    }

}