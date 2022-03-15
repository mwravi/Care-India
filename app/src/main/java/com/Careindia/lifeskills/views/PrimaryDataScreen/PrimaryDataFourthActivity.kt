package com.careindia.lifeskills.views.primarydatascreen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataFourthBinding
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_demographic.*
import kotlinx.android.synthetic.main.activity_primary_data_fourth.*
import kotlinx.android.synthetic.main.activity_primary_data_fourth.btn_bottom
import kotlinx.android.synthetic.main.activity_psychometric_forth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.primary_data_tab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataFourthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataFourthBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_fourth)

        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao, mstDistrictDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)
        )[PrimaryDataViewModel::class.java]
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        tv_title.text = resources.getString(R.string.primary_data)




        initializeController()
    }

    override fun initializeController() {
        bottomclick()
        hideshowView()
        applyClickOnView()
        fillSpinnerView()


        if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PDCGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

    }


    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    primaryDataViewModel.update_fourth_data(this, mstLookupViewModel, iLanguageID)
                    val intent = Intent(this, PrimaryDataFifthActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_back -> {
                val intent = Intent(this, PrimaryDataListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_setting -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun showLiveData() {
        val primaryGuid = validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)
        if (primaryGuid != null) {
            primaryDataViewModel.getdatabyPDCGuid(primaryGuid).observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    et_business_invest_other.setText(it.get(0).Business_Invested_Othr)
                    et_how_much_they_invested.setText(it.get(0).Loan_amount)

                    (validate!!.SetAnswerTypeCheckBoxButton(
                        check_business_invested,
                        it.get(0).Business_type
                    ))


                    spin_source_income.setSelection(
                        validate!!.returnpos(
                            it.get(0).Business_Invest_Source,
                            mstLookupViewModel,
                            54,
                            iLanguageID
                        )
                    )


                    validate!!.SetAnswerTypeRadioButton(
                        rg_business_registered,
                        it.get(0).Business_registered
                    )



                    spin_stage_of_self_employment.setSelection(
                        validate!!.returnpos(
                            it.get(0).Stage_Self_Emp,
                            mstLookupViewModel,
                            41,
                            iLanguageID
                        )
                    )
                    et_stage_other.setText(it.get(0).Stage_Self_Emp_Oth)

                    validate!!.SetAnswerTypeRadioButton(
                        rg_loans_availed_already,
                        it.get(0).Loan_availed
                    )
                    spin_availed_loan.setSelection(
                        validate!!.returnpos(
                            it.get(0).Loan_availed_from,
                            mstLookupViewModel,
                            110,
                            iLanguageID
                        )
                    )
                    validate!!.SetAnswerTypeRadioButton(
                        rg_expecting_financial_assistance,
                        it.get(0).Financial_assist
                    )
//                    et_amt_financial_assistance.setText(it.get(0).Financial_assist_amt)
                    spin_amt_financial_asistnc.setSelection(
                        validate!!.returnpos(
                            it.get(0).Financial_assist_amt,
                            mstLookupViewModel,
                            58,
                            iLanguageID
                        )
                    )
//                    spin_expecting_support.setSelection(
//                        validate!!.returnpos(
//                            it.get(0).Support_Expecting,
//                            mstLookupViewModel,
//                            52,
//                            iLanguageID
//                        )
//                    )


                    (validate!!.SetAnswerTypeCheckBoxButton(
                        check_expecting_support,
                        it.get(0).Support_Expecting
                    ))
                    et_support_other.setText(it.get(0).Support_Expecting_Oth)

                }
            })
        }

    }


    fun hideshowView() {
        rg_business_registered.setOnCheckedChangeListener { radioGroup, i ->
            validate!!.hideSoftKeyboard(this, radioGroup)
        }

        spin_stage_of_self_employment.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_stage_of_self_employment,
                        mstLookupViewModel,
                        41,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_stage_other.visibility = View.VISIBLE
                    } else {
                        lay_stage_other.visibility = View.GONE
                        et_stage_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }


        rg_loans_availed_already.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_from_where_availed_loans.visibility = View.VISIBLE
            } else {
                lay_from_where_availed_loans.visibility = View.GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }
        rg_expecting_financial_assistance.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_amt_financial_assistance.visibility = View.VISIBLE
            } else {
                lay_amt_financial_assistance.visibility = View.GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }


    }

    private fun checkValidation(): Int {
        var value = 1

        if (spin_stage_of_self_employment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_stage_of_self_employment,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.stage_of_self_employment_enterprise_idea)

            )
            value = 0
        } else if (lay_stage_other.visibility == View.VISIBLE && et_stage_other.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_stage_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_self_employment)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(check_business_invested).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.in_what_kind_of_bussiness_you_have_invested)
            )
            value = 0
        } else if (et_business_invest_other.text.toString()
                .isEmpty() && lay_business_invest_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_business_invest_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_business_invest)
            )
            value = 0
        } else if (et_how_much_they_invested.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_how_much_they_invested,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.how_much_amount_you_have_invested)

            )
            value = 0
        } else if (rg_business_registered.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.have_you_registered_your_business)
            )
            value = 0


        } else if (spin_source_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_source_income,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.how_did_you_source_their_investment)
            )
            value = 0


        } else if (rg_loans_availed_already.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,

                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_they_availing_loans_already)

            )
            value = 0
        } else if (spin_availed_loan.selectedItemPosition == 0 && lay_from_where_availed_loans.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_availed_loan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.from_where_have_availed_loans)
            )
            value = 0
        } else if (rg_expecting_financial_assistance.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_they_expecting_any_financial_assistance)
            )
            value = 0
        } else if (spin_amt_financial_asistnc.selectedItemPosition == 0 && lay_amt_financial_assistance.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertSpinner(
                this,
                spin_amt_financial_asistnc,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.amount_of_financial_assistance_required_in_rs)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(check_expecting_support)
                .isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_you_expecting_any_support)
            )
            value = 0
        } else if (lay_support_other.visibility == View.VISIBLE && et_support_other.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_support_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_expecting_support)
            )
            value = 0
        }

        return value
    }


    fun fillSpinnerView() {

        validate!!.dynamicMultiCheckChange(
            this,
            check_business_invested,
            mstLookupViewModel,
            60,
            1,
            et_business_invest_other,
            lay_business_invest_other
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_source_income,
            54,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_stage_of_self_employment,
            41,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_availed_loan,
            110,
            iLanguageID
        )

//        fill_Spinner(
//            resources.getString(R.string.select),
//            spin_expecting_support,
//            52,
//            iLanguageID
//        )

        validate!!.dynamicMultiCheckChange(
            this,
            check_expecting_support,
            mstLookupViewModel,
            52,
            iLanguageID,
            et_support_other,
            lay_support_other
        )

        validate!!.fillradio(
            this,
            rg_business_registered,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_loans_availed_already,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_expecting_financial_assistance,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_amt_financial_asistnc,
            58,
            iLanguageID
        )

    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_sixth.setBackgroundColor(resources.getColor(R.color.back))
        ll_seventh.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {

            val intent = Intent(this, PrimaryDataFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


//        ll_fourth.setOnClickListener {
//            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
//                val intent = Intent(this, PrimaryDataFourthActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_sixth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSixthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_seventh.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSeventhActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun fill_Spinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        mstLookupViewModel.getMstLookup(flag, iLanguageID)
            .observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    val iGen = it.size
                    val name = arrayOfNulls<String>(iGen + 1)
                    name[0] = strValue

                    for (i in 0 until it.size) {
                        name[i + 1] = it.get(i).Description
                    }
                    val adapter_category = ArrayAdapter<String>(
                        this,
                        R.layout.my_spinner_space_dashboard, name
                    )
                    adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                    spin.adapter = adapter_category
                }
            })

    }


    fun CustomAlert(
        primaryDataFourthActivity: PrimaryDataFourthActivity,
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
            val intent = Intent(primaryDataFourthActivity, PrimaryDataListActivity::class.java)
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
            horizontalScroll.smoothScrollBy(1000, 0)
        }, 100)
    }

    override fun onBackPressed() {

    }
}