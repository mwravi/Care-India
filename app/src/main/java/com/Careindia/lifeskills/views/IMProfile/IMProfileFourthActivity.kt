package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileFourthBinding
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_fourth.*
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileFourthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileFourthBinding
    var validate: Validate? = null
    var iLanguageID: Int = 0
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_fourth)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.im_profile)


        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = IndividualProfileRepository(improfiledao!!, mstDistrictDao)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this

        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        initializeController()

    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateForthProfileData(this)
                    val intent = Intent(this, IMProfileFifthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                var intent = Intent(this, IMProfileThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_back -> {
                val intent = Intent(this, IMProfileThirdActivity::class.java)
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

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        topLayClick()
        fillRadio()
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }


    }

    fun fillRadio() {

        validate!!.fillradio(
            this,
            rg_have_adhar,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_have_voter,
            -1,
            mstLookupViewModel,
            3, iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_have_pan,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_have_income,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_have_caste,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_svg_bank_act,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_availed_services_past,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

    }

    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {

                    et_service_provider_department.setText(validate!!.returnStringValue(it.get(0).SchemeDetails))

                    (it.get(0).Aadhaar?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_have_adhar,
                            it1
                        )
                    })
                    (it.get(0).Voter?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_have_voter,
                            it1
                        )
                    })
                    (it.get(0).PAN?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_have_pan,
                            it1
                        )
                    })
                    (it.get(0).IncomeCertificate?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_have_income,
                            it1
                        )
                    })
                    (it.get(0).CasteCertificate?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_have_caste,
                            it1
                        )
                    })
                    (it.get(0).BankAccount?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_svg_bank_act,
                            it1
                        )
                    })
                    (it.get(0).SchemesAvailed?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_availed_services_past,
                            it1
                        )
                    })

                }
            })

    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun sendData() {
        imProfileViewModel.collectiveProfileForthData(
            validate!!.GetAnswerTypeRadioButtonID(rg_have_adhar),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_voter),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_pan),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_income),
            validate!!.GetAnswerTypeRadioButtonID(rg_have_caste),
            validate!!.GetAnswerTypeRadioButtonID(rg_svg_bank_act),
            validate!!.GetAnswerTypeRadioButtonID(rg_availed_services_past)
        )
    }

    private fun checkValidation(): Int {
        var value = 1
        if (rg_have_adhar.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_adhar)
            )
            value = 0
        } else if (rg_have_voter.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_icard)
            )
            value = 0

        } else if (rg_have_pan.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_pan)
            )
            value = 0
        } else if (rg_have_income.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_incm_certict)
            )
            value = 0
        } else if (rg_have_caste.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_caste_certict)
            )
            value = 0
        } else if (rg_svg_bank_act.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_ans_bank_acct)
            )
            value = 0
        } else if (rg_availed_services_past.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_services_past_month)
            )
            value = 0

        } else if (et_service_provider_department.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_service_provider_department,
                resources.getString(R.string.plz_detail_srvic_provider)
            )
            value = 0

        }
        return value
    }


    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {

            val intent = Intent(this, IMProfileOneActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileTwoActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_forth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFourthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(700, 0)
        }, 100)
    }
}