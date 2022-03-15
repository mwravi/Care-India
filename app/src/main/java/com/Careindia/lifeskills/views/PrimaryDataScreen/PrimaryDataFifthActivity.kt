package com.careindia.lifeskills.views.primarydatascreen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataFifthBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_fifth.*
import kotlinx.android.synthetic.main.activity_primary_data_fifth.btn_bottom
import kotlinx.android.synthetic.main.activity_primary_data_fifth.et_amount
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.primary_data_tab.*
import kotlinx.android.synthetic.main.primary_data_tab.horizontalScroll
import kotlinx.android.synthetic.main.primary_data_tab.lay_first
import kotlinx.android.synthetic.main.primary_data_tab.lay_secnd
import kotlinx.android.synthetic.main.primary_data_tab.ll_fifth
import kotlinx.android.synthetic.main.primary_data_tab.ll_fourth
import kotlinx.android.synthetic.main.primary_data_tab.ll_third
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataFifthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataFifthBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_fifth)

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

        et_loan_purpose1.addTextChangedListener(object : TextWatcher {
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
                    et_loan_purpose2.isEnabled = true
                } else {
                    et_loan_purpose2.isEnabled = false
                    et_loan_purpose2.setText("")
                }
            }
        })

        et_loan_purpose2.addTextChangedListener(object : TextWatcher {
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
                    et_loan_purpose3.isEnabled = true
                } else {
                    et_loan_purpose3.isEnabled = false
                    et_loan_purpose3.setText("")
                }
            }
        })

        et_repayment_reason1.addTextChangedListener(object : TextWatcher {
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
                    et_repayment_reason2.isEnabled = true
                } else {
                    et_repayment_reason2.isEnabled = false
                    et_repayment_reason2.setText("")
                }
            }
        })

        et_repayment_reason2.addTextChangedListener(object : TextWatcher {
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
                    et_repayment_reason3.isEnabled = true
                } else {
                    et_repayment_reason3.isEnabled = false
                    et_repayment_reason3.setText("")
                }
            }
        })

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
                    primaryDataViewModel.update_fifth_data(this, mstLookupViewModel, iLanguageID)
                    val intent = Intent(this, PrimaryDataSixthActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataFourthActivity::class.java)
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
                    if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                        btn_bottom.visibility = View.GONE
                    } else {
                        btn_bottom.visibility = View.VISIBLE
                    }
                    et_business_name.setText(it.get(0).Business_Name)
                    spin_business_stage.setSelection(
                        validate!!.returnpos(
                            validate!!.returnIntegerValue(it.get(0).Business_Stage_ID.toString()),
                            mstLookupViewModel,
                            126,
                            iLanguageID
                        )
                    )
                    et_business_start_other.setText(it.get(0).Business_Stage_Othr)
                    (validate!!.SetAnswerTypeCheckBoxButton(
                        check_investment_mode,
                        it.get(0).Investment_Mode
                    ))
                    et_investment_mode_other.setText(it.get(0).Investment_Mode_Othr)
                    it.get(0).Self_Amount_Invested?.let { it1 -> setDefBlank(et_amount_invested, it1) }
                    it.get(0).Investment_Mode_Purpose?.let { it1 ->
                        validate!!.setAgenda(
                            et_loan_purpose1,
                            et_loan_purpose2,
                            et_loan_purpose3,
                            it1
                        )
                    }
                    validate!!.SetAnswerTypeRadioButton(
                        rg_loan_repayment,
                        it.get(0).Loan_Repayment
                    )
                    it.get(0).Loan_Amount_Returned?.let { it1 -> setDefBlank(et_amount, it1) }
                    it.get(0).Loan_Interest_Returned?.let { it1 -> setDefBlank(et_rate_of_interest, it1) }
                    it.get(0).Non_Repayment_Reason?.let { it1 ->
                        validate!!.setAgenda(
                            et_repayment_reason1,
                            et_repayment_reason2,
                            et_repayment_reason3,
                            it1
                        )
                    }
                }
            })
        }

    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }


    fun hideshowView() {

        spin_business_stage.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_business_stage,
                        mstLookupViewModel,
                        126,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_business_start_other.visibility = View.VISIBLE
                    } else {
                        lay_business_start_other.visibility = View.GONE
                        et_business_start_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        rg_loan_repayment.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_loan_amount_returned.visibility = View.VISIBLE
                lay_non_repayment_reason.visibility = View.GONE
                et_repayment_reason1.setText("")
                et_repayment_reason2.setText("")
                et_repayment_reason3.setText("")
            } else if(i == 2) {
                lay_loan_amount_returned.visibility = View.GONE
                lay_non_repayment_reason.visibility = View.VISIBLE
                et_amount.setText("")
                et_rate_of_interest.setText("")
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }


    }

    private fun checkValidation(): Int {
        var value = 1

        if (et_business_name.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_business_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_ongoing_business)
            )
            value = 0

        } else if (spin_business_stage.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_business_stage,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.stage_of_the_ongoing_business)
            )
            value = 0
        } else if (et_business_start_other.text.toString()
                .isEmpty() && lay_business_start_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_business_start_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_third)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(check_investment_mode).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.mode_of_investment)
            )
            value = 0
        } else if (et_investment_mode_other.text.toString()
                .isEmpty() && lay_investment_mode_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_investment_mode_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_fifth)

            )
            value = 0
        } else if (et_amount_invested.text.toString()
                .isEmpty() && lay_amount_invested.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_amount_invested,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.amount_invested_through_self)
            )
            value = 0
        } else if (et_loan_purpose1.text.toString()
                .isEmpty() && lay_loan_purpose.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_loan_purpose1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.purpose_of_taking_loan)
            )
            value = 0
        } else if (rg_loan_repayment.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_repayment_of_loan_happening)
            )
            value = 0
        } else if (et_amount.text.toString()
                .isEmpty() && lay_loan_amount_returned.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_amount,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.amount)
            )
            value = 0
        } else if (et_rate_of_interest.text.toString()
                .isEmpty() && lay_loan_amount_returned.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_rate_of_interest,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.rate_of_interest)
            )
            value = 0
        } else if (et_repayment_reason1.text.toString()
                .isEmpty() && lay_non_repayment_reason.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_repayment_reason1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_is_reason_of_non_repayment)
            )
            value = 0
        }

        return value
    }


    fun fillSpinnerView() {

        fill_Spinner(
            resources.getString(R.string.select),
            spin_business_stage,
            126,
            iLanguageID
        )

        dynamicMultiCheckChange(
            this,
            check_investment_mode,
            mstLookupViewModel,
            127,
            1,
            et_investment_mode_other,
            lay_investment_mode_other,
            lay_amount_invested,
            lay_loan_purpose,
            lay_loan_repayment
        )

        validate!!.fillradio(
            this,
            rg_loan_repayment,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
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


        ll_fourth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataFourthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        /*ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }*/

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
        primaryDataFourthActivity: PrimaryDataFifthActivity,
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
            horizontalScroll.smoothScrollBy(1800, 0)
        }, 100)
    }

    override fun onBackPressed() {

    }

    fun dynamicMultiCheckChange(
        context: Context,
        liear: LinearLayout,
        mstCommonViewModel: MstLookupViewModel?,
        flag: Int,
        iLanguageID: Int,
        edt: EditText,
        tbl: LinearLayout,
        tblinvest: LinearLayout,
        tblsource: LinearLayout,
        tblloan: LinearLayout
    ) {
        liear as LinearLayout
        var data: List<MstLookupEntity>? = null
        data =
            mstCommonViewModel!!.getMstAllData(flag, iLanguageID)
        if (data != null) {
            val iGen = data.size
            val value = arrayOfNulls<String>(iGen + 1)
            for (i in 0 until data.size) {
                val multicheck1 = CheckBox(context)
                multicheck1.layoutParams =
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                multicheck1.setText(data.get(i).Description?.trim())
                multicheck1.id = data.get(i).LookupCode!!

                if (liear != null) {

                    liear.addView(multicheck1)
                }

                multicheck1.setOnCheckedChangeListener { compoundButton, b ->
                    if (multicheck1.isChecked) {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.VISIBLE
                        } else if (multicheck1.id == 1) {
                            tblinvest.visibility = View.VISIBLE
                        } else if (multicheck1.id == 2 || multicheck1.id == 3) {
                            tblsource.visibility = View.VISIBLE
                            tblloan.visibility = View.VISIBLE
                        }
                    } else {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.GONE
                            edt.setText("")
                        } else if (multicheck1.id == 1) {
                            tblinvest.visibility = View.GONE
                        } else if (multicheck1.id == 2 || multicheck1.id == 3) {
                            tblsource.visibility = View.GONE
                            tblloan.visibility = View.GONE
                        }

                    }
                }

            }

        }

    }
}