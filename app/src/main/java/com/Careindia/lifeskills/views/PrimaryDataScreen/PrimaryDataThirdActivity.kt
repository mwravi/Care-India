package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataThirdBinding
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataThirdBinding
    var validate: Validate? = null

    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_third)
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

        if(validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.trim().length>0) {
            showLiveData()
        }

    }

    override fun initializeController() {
        applyClickOnView()

    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
              primaryDataViewModel.update_third_data(this,mstLookupViewModel,iLanguageID)
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }



    fun fillSpinner() {

        fill_Spinner(
            resources.getString(R.string.select),
            spin_source_income,
            30,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_stage_of_self_employment,
            33,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_availed_loan,
            33,
            iLanguageID
        )

        fill_Spinner(
            resources.getString(R.string.select),
            spin_expecting_support,
            34,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_business_registered,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_loans_availed_already,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_loans_availed_already,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_expecting_financial_assistance,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)
    }


    private fun checkValidation(): Int {
        var value = 1
        if (et_what_kind_of_business.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_what_kind_of_business,
                resources.getString(R.string.please_enter_what_kind_of_business_it_is)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_business_registered) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_is_the_business_registered)
            )
            value = 0
        } else if (spin_stage_of_self_employment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_stage_of_self_employment,
                resources.getString(R.string.please_select_stage_of_self_employment_enterprise_idea)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_loans_availed_already) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_are_they_availing_loans_already)
            )
            value = 0
        } else if (spin_availed_loan.selectedItemPosition==0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_availed_loan,
                resources.getString(R.string.please_enter_from_where_have_availed_loans)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_expecting_financial_assistance) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_are_they_expecting_any_financial_assistance)
            )
            value = 0
        }  else if (spin_expecting_support.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_expecting_support,
                resources.getString(R.string.please_select) + resources.getString(R.string.are_you_expecting_any_support)
            )
            value = 0
        }
        return value
    }

    fun sendData() {
        primaryDataViewModel.collectDataPrimaryThird(
            validate!!.GetAnswerTypeRadioButtonID(rg_business_registered),
            validate!!.GetAnswerTypeRadioButtonID(rg_loans_availed_already),
            validate!!.GetAnswerTypeRadioButtonID(rg_expecting_financial_assistance)
        )
    }


    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataSecondActivity::class.java)
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
                if (it != null && it.size>0) {

                    et_what_kind_of_business.setText(it.get(0).Business_type)
                   // spin_source_income.setSelection(validate!!.returnpos(it.get(0).Loan_interested,mstLookupViewModel,30,iLanguageID))
                    validate!!.SetAnswerTypeRadioButton(rg_business_registered,it.get(0).Business_registered)
                    validate!!.SetAnswerTypeRadioButton(rg_loans_availed_already,it.get(0).Loan_availed)


                    spin_stage_of_self_employment.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).State_selfemp),mstLookupViewModel,33,iLanguageID))
                    spin_availed_loan.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).Loan_source),mstLookupViewModel,33,iLanguageID))
                    validate!!.SetAnswerTypeRadioButton(rg_expecting_financial_assistance,it.get(0).Financial_assist)
                    spin_expecting_support.setSelection(validate!!.returnpos(it.get(0).Financial_assist_amt,mstLookupViewModel,34,iLanguageID))




                }
            })
        }

    }
}