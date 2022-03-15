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
import com.careindia.lifeskills.databinding.ActivityPrimaryDataSecondBinding
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_second.*
import kotlinx.android.synthetic.main.activity_primary_data_second.btn_bottom
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
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
        fillSpinner()
        hideview()
        bottomclick()
        if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PDCGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

        var IndGUID = validate!!.RetriveSharepreferenceString(AppSP.EdpId)
        if (IndGUID != null) {
            fillDataFromIM(IndGUID)
        }
    }

    override fun initializeController() {
        applyClickOnView()

    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_sixth.setBackgroundColor(resources.getColor(R.color.back))
        ll_seventh.setBackgroundColor(resources.getColor(R.color.back))
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

        ll_seventh.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSeventhActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun hideview() {

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


    fun fillDataFromIM(IndGUID: String) {

        CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(IndGUID)
            .observe(this, Observer {
                if (it != null && it.size > 0) {

                    validate!!.SetAnswerTypeRadioButton(rg_cast_income, it.get(0).CasteCertificate)
                    validate!!.SetAnswerTypeRadioButton(rg_aadhar_card, it.get(0).Aadhaar)
                    validate!!.SetAnswerTypeRadioButton(rg_pan_card, it.get(0).PAN)
                    validate!!.SetAnswerTypeRadioButton(rg_bank_account, it.get(0).BankAccount)


                }
            })


    }

    fun fillSpinner() {

        validate!!.fillradio(
            this,
            rg_cast_income,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_aadhar_card,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_pan_card,
            -1,
            mstLookupViewModel,
            3,
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


    }


    private fun checkValidation(): Int {
        var value = 1

        if (rg_cast_income.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,

                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_caste_and_income_certificate)
            )
            value = 0
        } else if (rg_aadhar_card.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_valid_aadhar_card)
            )
            value = 0
        } else if (rg_pan_card.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_valid_pan_card)
            )
            value = 0
        } else if (rg_bank_account.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_bank_account)
            )
            value = 0
        }
        return value
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
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    validate!!.SetAnswerTypeRadioButton(
                        rg_cast_income,
                        it.get(0).CastIncomeCertificate
                    )
                    validate!!.SetAnswerTypeRadioButton(rg_aadhar_card, it.get(0).ValidAadhaar)
                    validate!!.SetAnswerTypeRadioButton(rg_pan_card, it.get(0).ValidPAN)
                    validate!!.SetAnswerTypeRadioButton(rg_bank_account, it.get(0).ValidBank)


                }
            })
        }

    }

    override fun onBackPressed() {
//        val intent = Intent(this, PrimaryDataListActivity::class.java)
//        startActivity(intent)
//        finish()
    }


    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(400, 0)
        }, 100)
    }


}