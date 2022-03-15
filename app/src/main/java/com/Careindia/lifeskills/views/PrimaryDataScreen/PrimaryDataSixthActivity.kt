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
import com.careindia.lifeskills.databinding.ActivityPrimaryDataSixthBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_sixth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.primary_data_tab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataSixthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataSixthBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_sixth)

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

        et_asset_support1.addTextChangedListener(object : TextWatcher {
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
                    et_asset_support2.isEnabled = true
                } else {
                    et_asset_support2.isEnabled = false
                    et_asset_support2.setText("")
                }
            }
        })

        et_asset_support2.addTextChangedListener(object : TextWatcher {
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
                    et_asset_support3.isEnabled = true
                } else {
                    et_asset_support3.isEnabled = false
                    et_asset_support3.setText("")
                }
            }
        })

        et_asset_cost1.addTextChangedListener(object : TextWatcher {
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
                    et_asset_cost2.isEnabled = true
                } else {
                    et_asset_cost2.isEnabled = false
                    et_asset_cost2.setText("")
                }
            }
        })

        et_asset_cost2.addTextChangedListener(object : TextWatcher {
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
                    et_asset_cost3.isEnabled = true
                } else {
                    et_asset_cost3.isEnabled = false
                    et_asset_cost3.setText("")
                }
            }
        })

        et_training_helped1.addTextChangedListener(object : TextWatcher {
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
                    et_training_helped2.isEnabled = true
                } else {
                    et_training_helped2.isEnabled = false
                    et_training_helped2.setText("")
                }
            }
        })

        et_training_helped2.addTextChangedListener(object : TextWatcher {
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
                    et_training_helped3.isEnabled = true
                } else {
                    et_training_helped3.isEnabled = false
                    et_training_helped3.setText("")
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
                    primaryDataViewModel.update_sixth_data(this, mstLookupViewModel, iLanguageID)
                    val intent = Intent(this, PrimaryDataSeventhActivity::class.java)
                    startActivity(intent)
                    finish()
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
                    spin_business_support.setSelection(
                        validate!!.returnpos(
                            validate!!.returnIntegerValue(it.get(0).Business_Support_ID.toString()),
                            mstLookupViewModel,
                            128,
                            iLanguageID
                        )
                    )
                    et_business_support_other.setText(it.get(0).Business_Support_Othr)
                    it.get(0).Asset_Support_Kind?.let { it1 ->
                        validate!!.setAgenda(
                            et_asset_support1,
                            et_asset_support2,
                            et_asset_support3,
                            it1
                        )
                    }
                    it.get(0).Cost_Asset?.let { it1 ->
                        validate!!.setAgenda(
                            et_asset_cost1,
                            et_asset_cost2,
                            et_asset_cost3,
                            it1
                        )
                    }
                    (validate!!.SetAnswerTypeCheckBoxButton(
                        check_training_skill_nature,
                        it.get(0).Skill_Training_Nature
                    ))
                    et_training_skill_nature_other.setText(it.get(0).Skill_Training_Nature_Othr)
                    it.get(0).Total_Training_Cost?.let { it1 ->
                        setDefBlank(
                            et_total_training_cost,
                            it1
                        )
                    }
                    it.get(0).Training_Helped_Business?.let { it1 ->
                        validate!!.setAgenda(
                            et_training_helped1,
                            et_training_helped2,
                            et_training_helped3,
                            it1
                        )
                    }
                    spin_market_linkage.setSelection(
                        validate!!.returnpos(
                            validate!!.returnIntegerValue(it.get(0).Market_Linkage_ID.toString()),
                            mstLookupViewModel,
                            130,
                            iLanguageID
                        )
                    )
                    et_market_linkage_other.setText(it.get(0).Market_Linkage_Othr)
                    it.get(0).Market_Linkage_Amount?.let { it1 ->
                        setDefBlank(
                            et_cost_involved_market,
                            it1
                        )
                    }
                    spin_branding_support_kind.setSelection(
                        validate!!.returnpos(
                            validate!!.returnIntegerValue(it.get(0).Branding_Support_ID.toString()),
                            mstLookupViewModel,
                            131,
                            iLanguageID
                        )
                    )
                    et_branding_support_other.setText(it.get(0).Branding_Support_Othr)
                    it.get(0).Branding_Support_Amount?.let { it1 ->
                        setDefBlank(
                            et_cost_involved_branding,
                            it1
                        )
                    }
                    it.get(0).Expenses_Month?.let { it1 ->
                        setDefBlank(
                            et_expenses_per_month,
                            it1
                        )
                    }
                    it.get(0).Sales_Month?.let { it1 ->
                        setDefBlank(
                            et_sales_per_month,
                            it1
                        )
                    }
                    it.get(0).Profit_Month?.let { it1 ->
                        setDefBlank(
                            et_profit_per_month,
                            it1
                        )
                    }
                    it.get(0).Money_Invested_Month?.let { it1 ->
                        setDefBlank(
                            et_money_invested,
                            it1
                        )
                    }
                    it.get(0).Net_Saving_Month?.let { it1 ->
                        setDefBlank(
                            et_net_saving_month,
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

        spin_business_support.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_business_support,
                        mstLookupViewModel,
                        128,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_business_support_other.visibility = View.VISIBLE
                        lay_asset_support_got.visibility = View.GONE
                        lay_training_skill_nature.visibility = View.GONE
                        lay_market_linkage.visibility = View.GONE
                        dynamicMultiCheckChange(
                            this@PrimaryDataSixthActivity,
                            check_training_skill_nature,
                            mstLookupViewModel,
                            129,
                            1,
                            et_training_skill_nature_other,
                            lay_training_skill_nature_other,
                            1
                        )
                        et_asset_support1.setText("")
                        et_asset_support2.setText("")
                        et_asset_support3.setText("")
                        et_asset_cost1.setText("")
                        et_asset_cost2.setText("")
                        et_asset_cost3.setText("")
                        et_training_skill_nature_other.setText("")
                        et_total_training_cost.setText("")
                        spin_market_linkage.setSelection(0)
                    } else if (ID == 1) {
                        lay_asset_support_got.visibility = View.VISIBLE
                        lay_business_support_other.visibility = View.GONE
                        lay_training_skill_nature.visibility = View.GONE
                        lay_market_linkage.visibility = View.GONE
                        dynamicMultiCheckChange(
                            this@PrimaryDataSixthActivity,
                            check_training_skill_nature,
                            mstLookupViewModel,
                            129,
                            1,
                            et_training_skill_nature_other,
                            lay_training_skill_nature_other,
                            1
                        )
                        et_business_support_other.setText("")
                        et_training_skill_nature_other.setText("")
                        et_total_training_cost.setText("")
                        spin_market_linkage.setSelection(0)
                    } else if (ID == 2) {
                        lay_training_skill_nature.visibility = View.VISIBLE
                        lay_asset_support_got.visibility = View.GONE
                        lay_business_support_other.visibility = View.GONE
                        lay_market_linkage.visibility = View.GONE
                        et_business_support_other.setText("")
                        et_asset_support1.setText("")
                        et_asset_support2.setText("")
                        et_asset_support3.setText("")
                        et_asset_cost1.setText("")
                        et_asset_cost2.setText("")
                        et_asset_cost3.setText("")
                        spin_market_linkage.setSelection(0)
                    } else if (ID == 4) {
                        lay_market_linkage.visibility = View.VISIBLE
                        lay_training_skill_nature.visibility = View.GONE
                        lay_asset_support_got.visibility = View.GONE
                        lay_business_support_other.visibility = View.GONE
                        et_business_support_other.setText("")
                        et_asset_support1.setText("")
                        et_asset_support2.setText("")
                        et_asset_support3.setText("")
                        et_asset_cost1.setText("")
                        et_asset_cost2.setText("")
                        et_asset_cost3.setText("")
                        et_training_skill_nature_other.setText("")
                        et_total_training_cost.setText("")
                        dynamicMultiCheckChange(
                            this@PrimaryDataSixthActivity,
                            check_training_skill_nature,
                            mstLookupViewModel,
                            129,
                            1,
                            et_training_skill_nature_other,
                            lay_training_skill_nature_other,
                            1
                        )
                    } else {
                        lay_business_support_other.visibility = View.GONE
                        lay_asset_support_got.visibility = View.GONE
                        lay_training_skill_nature.visibility = View.GONE
                        lay_market_linkage.visibility = View.GONE
                        et_business_support_other.setText("")
                        et_asset_support1.setText("")
                        et_asset_support2.setText("")
                        et_asset_support3.setText("")
                        et_asset_cost1.setText("")
                        et_asset_cost2.setText("")
                        et_asset_cost3.setText("")
                        et_training_skill_nature_other.setText("")
                        et_total_training_cost.setText("")
                        spin_market_linkage.setSelection(0)
                        dynamicMultiCheckChange(
                            this@PrimaryDataSixthActivity,
                            check_training_skill_nature,
                            mstLookupViewModel,
                            129,
                            1,
                            et_training_skill_nature_other,
                            lay_training_skill_nature_other,
                            1
                        )
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spin_market_linkage.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_market_linkage,
                        mstLookupViewModel,
                        130,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_market_linkage_other.visibility = View.VISIBLE
                    } else {
                        lay_market_linkage_other.visibility = View.GONE
                        et_market_linkage_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spin_branding_support_kind.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_branding_support_kind,
                        mstLookupViewModel,
                        131,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_branding_support_other.visibility = View.VISIBLE
                    } else {
                        lay_branding_support_other.visibility = View.GONE
                        et_branding_support_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

    }

    private fun checkValidation(): Int {
        var value = 1

        if (spin_business_support.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_business_support,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.business_support_from_care_india)
            )
            value = 0
        } else if (et_business_support_other.text.toString()
                .isEmpty() && lay_business_support_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_business_support_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_twelve)
            )
            value = 0
        } else if (et_asset_support1.text.toString()
                .isEmpty() && lay_asset_support_got.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_asset_support1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_kind_of_asset_support_you_got)
            )
            value = 0
        } else if (et_asset_cost1.text.toString()
                .isEmpty() && lay_asset_support_got.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_asset_cost1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.cost_of_the_asset)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(check_training_skill_nature)
                .isEmpty() && lay_training_skill_nature.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.nature_of_skill_training_you_acquired)
            )
            value = 0
        } else if (et_training_skill_nature_other.text.toString()
                .isEmpty() && lay_training_skill_nature_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_training_skill_nature_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_forteen)

            )
            value = 0
        } else if (et_total_training_cost.text.toString()
                .isEmpty() && lay_training_skill_nature.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_total_training_cost,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_cost_of_training)
            )
            value = 0
        } else if (et_training_helped1.text.toString()
                .isEmpty() && lay_how_did_training_helped.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_training_helped1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.how_did_the_training_helped_you_in_starting_business)
            )
            value = 0
        } else if (spin_market_linkage.selectedItemPosition == 0 && lay_market_linkage.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_market_linkage,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.what_kind_of_support_you_got_for_market_linkage)
            )
            value = 0
        } else if (et_market_linkage_other.text.toString()
                .isEmpty() && lay_market_linkage_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_market_linkage_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_sixteen)
            )
            value = 0
        } else if (et_cost_involved_market.text.toString()
                .isEmpty() && lay_cost_involved_market.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_cost_involved_market,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.any_cost_involved_if_yes_what_is_amount)
            )
            value = 0
        } else if (spin_branding_support_kind.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_branding_support_kind,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.what_kind_of_branding_support_you_got)
            )
            value = 0
        } else if (et_branding_support_other.text.toString()
                .isEmpty() && lay_branding_support_other.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_branding_support_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_other_sixteen)
            )
            value = 0
        } else if (et_cost_involved_branding.text.toString()
                .isEmpty() && lay_cost_involved_branding.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_cost_involved_branding,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.any_cost_involved_if_yes_what_is_amount_seventeen)
            )
            value = 0
        } else if (et_expenses_per_month.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_expenses_per_month,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.expenses_per_month)
            )
            value = 0
        } else if (et_sales_per_month.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_sales_per_month,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.sales_per_month)
            )
            value = 0
        } else if (et_profit_per_month.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_profit_per_month,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.profit_per_month)
            )
            value = 0
        } else if (et_money_invested.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_money_invested,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.money_invested_in_business_from_profit_per_month)
            )
            value = 0
        } else if (et_net_saving_month.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_net_saving_month,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.net_saving_month)
            )
            value = 0
        }

        return value
    }


    fun fillSpinnerView() {

        fill_Spinner(
            resources.getString(R.string.select),
            spin_business_support,
            128,
            iLanguageID
        )

        dynamicMultiCheckChange(
            this,
            check_training_skill_nature,
            mstLookupViewModel,
            129,
            1,
            et_training_skill_nature_other,
            lay_training_skill_nature_other,
            0
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_market_linkage,
            130,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_branding_support_kind,
            131,
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
        ll_sixth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
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

        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        /*ll_sixth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSixthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }*/

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
        primaryDataFourthActivity: PrimaryDataSixthActivity,
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
            horizontalScroll.smoothScrollBy(2300, 0)
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
        chkFlag: Int
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
                if (chkFlag == 1) {
                    multicheck1.isChecked = false
                }

                multicheck1.setOnCheckedChangeListener { compoundButton, b ->
                    if (multicheck1.isChecked) {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.VISIBLE
                        }
                    } else {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.GONE
                            edt.setText("")
                        }

                    }
                }

            }

        }

    }
}