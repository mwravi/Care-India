package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataSecondBinding
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.primary_data_tab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataSecondBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_second)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)
        )[PrimaryDataViewModel::class.java]
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        tv_title.text = resources.getString(R.string.primary_data)

        initializeController()
        fillSpinner()
        hideview()
        bottomclick()
        if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PDCGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

    }

    override fun initializeController() {
        applyClickOnView()

    }

    fun bottomclick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
          lay_first.setOnClickListener {

              val intent = Intent(this, PrimaryDataFirstActivity::class.java)
              startActivity(intent)
              finish()
          }
  /*      lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }*/

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun hideview() {
        spin_planning_investment.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_planning_investment,
                        mstLookupViewModel,
                        31,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_panning_other.visibility = View.VISIBLE
                    } else {
                        lay_panning_other.visibility = View.GONE
                        et_panning_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        spin_expected_support.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_expected_support,
                        mstLookupViewModel,
                        52,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_any_support_other.visibility = View.VISIBLE
                    } else {
                        lay_any_support_other.visibility = View.GONE
                        et_any_support_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        spin_which_source.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = validate!!.returnID(
                        spin_which_source,
                        mstLookupViewModel,
                        53,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_source_other.visibility = View.VISIBLE
                    } else {
                        lay_source_other.visibility = View.GONE
                        et_source_other.setText("")
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })


        rg_availing_loan_subsidies.setOnCheckedChangeListener { radioGroup, i ->
            val lookupCode = validate!!.GetAnswerTypeRadioButtonIDNew(rg_availing_loan_subsidies)
            if (lookupCode == 0) {
                lay_which_source.visibility = View.GONE
                spin_which_source.setSelection(0)
                lay_source_other.visibility = View.GONE
                et_source_other.setText("")
            } else {
                lay_which_source.visibility = View.VISIBLE
                lay_source_other.visibility = View.VISIBLE

            }

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
                    primaryDataViewModel.update_second_data(this, mstLookupViewModel, iLanguageID)
                    val intent = Intent(this, PrimaryDataThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataFirstActivity::class.java)
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

    fun fillSpinner() {


        fill_Spinner(
            resources.getString(R.string.select),
            spin_how_much_invest,
            30,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_planning_investment,
            31,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_expected_support,
            52,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_which_source,
            53,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_bank_account,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_new_business,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_business_plan,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_ready_to_invest,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_availing_loan_subsidies,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
    }


    private fun checkValidation(): Int {
        var value = 1
        if (rg_bank_account.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_bank_account)
            )
            value = 0
        } else if (rg_new_business.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_you_interested_in_starting_new_business)
            )
            value = 0
        } else if (et_kind_of_business.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_kind_of_business,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_kind_of_business_related_skill_training_needed)
            )
            value = 0
        } else if (rg_business_plan.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_you_business_plan)
            )
            value = 0
        } else if (et_investment_range.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_investment_range,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_is_the_amount_iin_thousands_of_investment_it_requires_in_rs)
            )
            value = 0
        } else if (rg_ready_to_invest.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_you_ready_to_invest)

            )
            value = 0
        } else if (spin_how_much_invest.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_how_much_invest,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.how_much_you_they_will_invest_more_than)
            )
            value = 0
        } else if (spin_planning_investment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_planning_investment,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.how_are_you_planning_your_investment)
            )
            value = 0
        } else if (lay_panning_other.visibility == View.VISIBLE && et_panning_other.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_panning_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.others_specify)
            )
            value = 0
        } else if (et_financial_assistance.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_financial_assistance,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.how_much_financial_assistance_is_required)

            )
            value = 0
        } else if (spin_expected_support.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_expected_support,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.any_support_required_in_form_of)

            )
            value = 0
        } else if (lay_any_support_other.visibility == View.VISIBLE && et_any_support_other.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_any_support_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.others_specify)
            )
            value = 0
        } else if (rg_availing_loan_subsidies.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_you_interested_in_availing_loans_or_subsidies)
            )
            value = 0
        } else if (lay_which_source.visibility==View.VISIBLE && spin_which_source.selectedItemPosition == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.if_yes_through_which_source)
            )
            value = 0
        } else if (lay_source_other.visibility == View.VISIBLE && et_source_other.text.toString()
                .isEmpty()
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_source_other,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.others_specify)
            )
            value = 0
        } else if (et_how_much_they_invested.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_how_much_they_invested,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.how_much_amount_you_have_invested)

            )
            value = 0
        }
        return value
    }


    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataListActivity::class.java)
        startActivity(intent)
        finish()
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

    fun showLiveData() {
        val primaryGuid = validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)
        if (primaryGuid != null) {
            primaryDataViewModel.getdatabyPDCGuid(primaryGuid).observe(this, Observer {
                if (it != null && it.size > 0) {

                    validate!!.SetAnswerTypeRadioButton(rg_bank_account, it.get(0).ValidBank)
                    validate!!.SetAnswerTypeRadioButton(
                        rg_new_business,
                        it.get(0).Business_Interested
                    )
                    et_kind_of_business.setText(it.get(0).Business_Training)
                    validate!!.SetAnswerTypeRadioButton(rg_business_plan, it.get(0).Business_Plan)
                    et_investment_range.setText(it.get(0).Business_Investment_Amt.toString())
                    validate!!.SetAnswerTypeRadioButton(
                        rg_ready_to_invest,
                        it.get(0).Invest_readiness
                    )
                    spin_how_much_invest.setSelection(
                        validate!!.returnpos(
                            it.get(0).Invest_HowMuch,
                            mstLookupViewModel,
                            30,
                            iLanguageID
                        )
                    )
                    spin_planning_investment.setSelection(
                        validate!!.returnpos(
                            it.get(0).Invest_Plan,
                            mstLookupViewModel,
                            31,
                            iLanguageID
                        )
                    )
                    et_panning_other.setText(it.get(0).Invest_Plan_Oth)
                    et_financial_assistance.setText(it.get(0).Financial_Assistance)
                    spin_expected_support.setSelection(
                        validate!!.returnpos(
                            it.get(0).Invest_support,
                            mstLookupViewModel,
                            52,
                            iLanguageID
                        )
                    )
                    et_any_support_other.setText(validate!!.returnStringValue(it.get(0).Invest_support_Oth))
                    validate!!.SetAnswerTypeRadioButton(
                        rg_availing_loan_subsidies,
                        it.get(0).Loan_interested
                    )
                    spin_which_source.setSelection(
                        validate!!.returnpos(
                            it.get(0).Loan_Source,
                            mstLookupViewModel,
                            53,
                            iLanguageID
                        )
                    )
                    et_source_other.setText(validate!!.returnStringValue(it.get(0).Loan_Source_Oth))
                    et_how_much_they_invested.setText(it.get(0).Loan_amount)


                }
            })
        }

    }

}