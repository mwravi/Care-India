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
import com.careindia.lifeskills.databinding.ActivityPrimaryDataSeventhBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_seventh.*
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

class PrimaryDataSeventhActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataSeventhBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_seventh)

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

        et_purpose_of_loan1.addTextChangedListener(object : TextWatcher {
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
                    et_purpose_of_loan2.isEnabled = true
                } else {
                    et_purpose_of_loan2.isEnabled = false
                    et_purpose_of_loan2.setText("")
                }
            }
        })

        et_purpose_of_loan2.addTextChangedListener(object : TextWatcher {
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
                    et_purpose_of_loan3.isEnabled = true
                } else {
                    et_purpose_of_loan3.isEnabled = false
                    et_purpose_of_loan3.setText("")
                }
            }
        })

        et_business_attribute1.addTextChangedListener(object : TextWatcher {
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
                    et_business_attribute2.isEnabled = true
                } else {
                    et_business_attribute2.isEnabled = false
                    et_business_attribute2.setText("")
                }
            }
        })

        et_business_attribute2.addTextChangedListener(object : TextWatcher {
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
                    et_business_attribute3.isEnabled = true
                } else {
                    et_business_attribute3.isEnabled = false
                    et_business_attribute3.setText("")
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
                    primaryDataViewModel.update_seventh_data(this, mstLookupViewModel, iLanguageID)
                    CustomAlert(this, resources.getString(R.string.data_saved_successfully))
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataFifthActivity::class.java)
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
                    (validate!!.SetAnswerTypeCheckBoxButton(
                        check_finance_support_source,
                        it.get(0).Finance_Support_Source
                    ))
                    et_business_reasons_other.setText(it.get(0).Business_Reason_Othr)
                    it.get(0).Finance_Support_Purpose?.let { it1 ->
                        validate!!.setAgenda(
                            et_purpose_of_loan1,
                            et_purpose_of_loan2,
                            et_purpose_of_loan3,
                            it1
                        )
                    }
                    it.get(0).Finance_Support_Amount?.let { it1 ->
                        setDefBlank(et_loan_amount_taken, it1)
                    }
                    it.get(0).Finance_Support_Interest?.let { it1 ->
                        setDefBlank(et_loan_repayment_interest_rate, it1)
                    }
                    validate!!.SetAnswerTypeRadioButton(
                        rg_business_running_smoothly,
                        it.get(0).Business_Running_Smoothly
                    )
                    spin_business_reasons.setSelection(
                        validate!!.returnpos(
                            validate!!.returnIntegerValue(it.get(0).Business_Reason_ID.toString()),
                            mstLookupViewModel,
                            132,
                            iLanguageID
                        )
                    )
                    it.get(0).Successful_Business_Attri?.let { it1 ->
                        validate!!.setAgenda(
                            et_business_attribute1,
                            et_business_attribute2,
                            et_business_attribute3,
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

        spin_business_reasons.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_business_reasons,
                        mstLookupViewModel,
                        132,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_business_reasons_other.visibility = View.VISIBLE
                    } else {
                        lay_business_reasons_other.visibility = View.GONE
                        et_business_reasons_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        rg_business_running_smoothly.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_business_attribute.visibility = View.VISIBLE
                lay_business_reasons.visibility = View.GONE
                et_business_reasons_other.setText("")
            } else if (i == 2){
                lay_business_attribute.visibility = View.GONE
                lay_business_reasons.visibility = View.VISIBLE
                et_business_attribute1.setText("")
                et_business_attribute2.setText("")
                et_business_attribute3.setText("")
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }


    }

    private fun checkValidation(): Int {
        var value = 1

        if (validate!!.GetAnswerTypeCheckBoxButtonID(check_finance_support_source).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.source_of_finance_support_post)
            )
            value = 0
        } else if (et_purpose_of_loan1.text.toString()
                .isEmpty() && lay_purpose_of_loan.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_purpose_of_loan1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.purpose_of_taking_the_loan)
            )
            value = 0

        } else if (et_loan_amount_taken.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_loan_amount_taken,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.amount_of_loan_taken)
            )
            value = 0
        } else if (et_loan_repayment_interest_rate.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_loan_repayment_interest_rate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.loan_repayment_at_what_rate_of_interest)

            )
            value = 0
        } else if (rg_business_running_smoothly.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_your_business_running_smoothly)
            )
            value = 0
        } else if (spin_business_reasons.selectedItemPosition == 0 && lay_business_reasons.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_business_reasons,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.if_no_reasons)
            )
            value = 0
        } else if (et_business_reasons_other.text.toString()
                .isEmpty() && lay_business_reasons_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_business_reasons_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_thirda)
            )
            value = 0
        } else if (et_business_attribute1.text.toString()
                .isEmpty() && lay_business_attribute.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_business_attribute1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_three_things_would_you_attribute_to_successful_running_of_business)
            )
            value = 0
        }

        return value
    }


    fun fillSpinnerView() {

        dynamicMultiCheckChange(
            this,
            check_finance_support_source,
            mstLookupViewModel,
            127,
            1,
            lay_purpose_of_loan
        )

        validate!!.fillradio(
            this,
            rg_business_running_smoothly,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_business_reasons,
            132,
            iLanguageID
        )

    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_sixth.setBackgroundColor(resources.getColor(R.color.back))
        ll_seventh.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

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

        /*ll_seventh.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSeventhActivity::class.java)
                startActivity(intent)
                finish()
            }
        }*/
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
        primaryDataFourthActivity: PrimaryDataSeventhActivity,
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
            horizontalScroll.smoothScrollBy(2800, 0)
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
        tbl: LinearLayout
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
                        if (multicheck1.id == 1 || multicheck1.id == 2) {
                            tbl.visibility = View.VISIBLE
                        }
                    } else {
                        if (multicheck1.id == 1 || multicheck1.id == 2) {
                            tbl.visibility = View.GONE
                        }

                    }
                }

            }

        }

    }
}