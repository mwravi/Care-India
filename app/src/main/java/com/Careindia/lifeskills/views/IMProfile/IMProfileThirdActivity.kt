package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileThirdBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.android.synthetic.main.activity_improfile_third.btn_bottom
import kotlinx.android.synthetic.main.activity_improfile_third.btn_prev
import kotlinx.android.synthetic.main.activity_improfile_third.btn_save
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileThirdBinding
    var validate: Validate? = null
    var iLanguageID: Int = 0
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_third)
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
                    imProfileViewModel.updateThirdData(this)
                    val intent = Intent(this, IMProfileFourthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                var intent = Intent(this, IMProfileTwoActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.img_back -> {
                val intent = Intent(this, IMProfileListActivity::class.java)
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
        hideShowView()
        //apply click on view
        applyClickOnView()
        topLayClick()
        // fill spinner view
        fillSpinner()
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }


        validate!!.fillradio(
            this,
            rg_type_emp,
            -1,
            mstLookupViewModel,
            11,
            1
        )
        validate!!.fillradio(
            this,
            rg_secondary_income,
            -1,
            mstLookupViewModel,
            3,
            1
        )
        rg_type_emp.setOnCheckedChangeListener {radioGroup,i ->
            validate!!.hideSoftKeyboard(this, radioGroup)
        }
    }

    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    et_specify_source_secondary_income.setText(validate!!.returnStringValue(it.get(0).Sec_Inc_Other))
                    et_specif_source_income.setText(validate!!.returnStringValue(it.get(0).Primary_Inc_Other))
                    et_specify_sell_waste_collect.setText(validate!!.returnStringValue(it.get(0).Dispose_Other))

                    et_waste_pick.setText(validate!!.returnStringValue(it.get(0).Waste_Type))
                    setDefBlank(et_avg_daily_income, it.get(0).Primary_Inc!!)
                    setDefBlank(et_no_days_job, it.get(0).Primary_WD!!)
                    setDefBlank(et_days_secondary_job,it.get(0).Secondary_WD!!)
                    setDefBlank(et_avg_daily_secondry_income,it.get(0).Secondary_Inc!!)

                    validate!!.SetAnswerTypeCheckBoxButton(chk_sell_waste_collect, it.get(0).Waste_Disposal)
                    spin_source_income.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Primary_Occupation.toString()),
                            13
                        )
                    )
                    spin_what_secondary_income.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(0).Secondary_Occupation.toString()
                            ), 14
                        )
                    )
                    spin_cate_picker_belong.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).WP_category.toString()
                            ), 10
                        )
                    )

                    (it.get(0).Employment_Type?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_type_emp,
                            it1
                        )
                    })
                    (it.get(0).IsSecondary_Occupation?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_secondary_income,
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

    fun fillSpinner() {
        fillSpinner("Select", spin_cate_picker_belong, 10, iLanguageID)
        fillSpinner("Select", spin_source_income, 13, iLanguageID)
        validate!!.dynamicMultiCheckChange(this,chk_sell_waste_collect,mstLookupViewModel,12,iLanguageID,et_specify_sell_waste_collect,lay_et_specify_sell_waste_collect)
        fillSpinner("Select", spin_what_secondary_income, 14, iLanguageID)
    }


    fun sendData() {
        imProfileViewModel.collectiveProfileThirdData(
            validate!!.GetAnswerTypeRadioButtonID(rg_type_emp),
            validate!!.GetAnswerTypeRadioButtonID(rg_secondary_income)
        )
    }


    fun hideShowView() {

        imProfileViewModel.PrimaryOccup.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_source_income,
                mstLookupViewModel,
                13,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_et_specif_source_income.visibility = View.VISIBLE
            } else if (lookupCode == 1) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else if (lookupCode == 2) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else if (lookupCode == 3) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else if (lookupCode == 4) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else if (lookupCode == 5) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else if (lookupCode == 6) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else if (lookupCode == 8) {
                lay_et_no_days_job.visibility = View.VISIBLE
                lay_et_avg_daily_income.visibility = View.VISIBLE
                lay_secondary_income.visibility = View.VISIBLE
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_source_income.setText("")
            } else {
                lay_et_no_days_job.visibility = View.GONE
                lay_et_avg_daily_income.visibility = View.GONE
                lay_et_specif_source_income.visibility = View.GONE
                lay_secondary_income.visibility = View.GONE
                et_specif_source_income.setText("")
            }

        })

        rg_secondary_income.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_spin_what_secondary_income.visibility = View.VISIBLE
                lay_et_days_secondary_job.visibility = View.VISIBLE
                lay_et_avg_daily_secondry_income.visibility = View.VISIBLE
            } else {
                lay_spin_what_secondary_income.visibility = View.GONE
                lay_et_days_secondary_job.visibility = View.GONE
                lay_et_avg_daily_secondry_income.visibility = View.GONE
                lay_et_specify_source_secondary_income.visibility = View.GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }

        imProfileViewModel.SecSourceIncom.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_what_secondary_income,
                mstLookupViewModel,
                14,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_et_specify_source_secondary_income.visibility = View.VISIBLE
            } else {
                lay_et_specify_source_secondary_income.visibility = View.GONE
                et_specify_source_secondary_income.setText("")
            }

        })


        imProfileViewModel.WastePick.observe(this) {


            val lookupCode = validate!!.returnLookupCode(
                spin_cate_picker_belong,
                mstLookupViewModel,
                10,
                iLanguageID
            )
            if (lookupCode > 0) {

                if (lookupCode == 1) {
                    lay_type_emp.visibility = View.VISIBLE
                    lay_et_waste_pick.visibility = View.VISIBLE
                    lay_spin_sell_waste_collect.visibility = View.VISIBLE
                } else if (lookupCode == 2) {
                    lay_type_emp.visibility = View.VISIBLE
                    lay_et_waste_pick.visibility = View.VISIBLE
                    lay_spin_sell_waste_collect.visibility = View.VISIBLE
                } else if (lookupCode == 3) {
                    lay_type_emp.visibility = View.VISIBLE
                    lay_et_waste_pick.visibility = View.VISIBLE
                    lay_spin_sell_waste_collect.visibility = View.VISIBLE
                } else if (lookupCode == 4) {
                    lay_type_emp.visibility = View.VISIBLE
                    lay_et_waste_pick.visibility = View.VISIBLE
                    lay_spin_sell_waste_collect.visibility = View.VISIBLE
                } else {
                    lay_type_emp.visibility = View.GONE
                    lay_et_waste_pick.visibility = View.GONE
                    et_waste_pick.setText("")
                    validate!!.SetAnswerTypeRadioButton(rg_type_emp, 0)
                    validate!!.SetAnswerTypeCheckBoxButton(chk_sell_waste_collect, "")
                    lay_spin_sell_waste_collect.visibility = View.GONE
                }
            }
        }


    }


    private fun checkValidation(): Int {
        var value = 1

        if (spin_cate_picker_belong.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_cate_picker_belong,
                resources.getString(R.string.plz_select_waste_pickr)
            )
            value = 0
        } else if (rg_type_emp.checkedRadioButtonId == -1 && lay_type_emp.visibility == View.VISIBLE) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_employmentr)
            )
            value = 0
        } else if (et_waste_pick.text.toString()
                .isEmpty() && lay_et_waste_pick.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_waste_pick,
                resources.getString(R.string.plz_select_wastes_pick)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(chk_sell_waste_collect).length == 0 && lay_spin_sell_waste_collect.visibility == View.VISIBLE) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_dispose_wastes)
            )
            value = 0


        } else if (et_specify_sell_waste_collect.text.toString()
                .isEmpty() && lay_et_specify_sell_waste_collect.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_sell_waste_collect,
                resources.getString(R.string.plz_enter_sell_waste_collect)
            )
            value = 0

        } else if (spin_source_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_source_income,
                resources.getString(R.string.plz_select_source_income)
            )
            value = 0
        } else if (et_specif_source_income.text.toString().length == 0 && lay_et_specif_source_income.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specif_source_income,
                resources.getString(R.string.plz_enter_source_income)
            )
            value = 0
        } else if (et_no_days_job.text.toString().isEmpty() && lay_et_no_days_job.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_no_days_job,
                resources.getString(R.string.plz_select_working_days)
            )
            value = 0

        }else if(lay_et_no_days_job.visibility == View.VISIBLE && validate!!.returnIntegerValue(et_no_days_job.text.toString()) < 1 || validate!!.returnIntegerValue(
                et_no_days_job.text.toString()
            ) > 29) {

                validate!!.CustomAlertEdit(
                    this,
                    et_no_days_job,
                    resources.getString(R.string.please_entr_input_months)
                )
                value = 0
            }
         else if (lay_et_avg_daily_income.visibility==View.VISIBLE && et_avg_daily_income.text.toString().isEmpty()) {
                validate!!.CustomAlertEdit(
                    this,
                    et_avg_daily_income,
                    resources.getString(R.string.plz_select_avg_daily_income)
                )
                value = 0
            } else if (lay_et_avg_daily_income.visibility==View.VISIBLE && validate!!.returnIntegerValue(et_avg_daily_income.text.toString()) < 50 || validate!!.returnIntegerValue(
                    et_avg_daily_income.text.toString()
                ) > 9999
            ) {
                validate!!.CustomAlertEdit(
                    this,
                    et_avg_daily_income,
                    resources.getString(R.string.please_entr_input_daily)
                )
                value = 0

            } else if (rg_secondary_income.checkedRadioButtonId == -1 && lay_secondary_income.visibility == View.VISIBLE) {
                validate!!.CustomAlert(
                    this,
                    resources.getString(R.string.please_enter) + " " + resources.getString(R.string.secondary_income)
                )
                value = 0
            } else if (validate!!.GetAnswerTypeRadioButtonID(rg_secondary_income) == 1 && spin_what_secondary_income.selectedItemPosition == 0) {
                validate!!.CustomAlertSpinner(
                    this,
                    spin_what_secondary_income,
                    resources.getString(R.string.plz_select_secondary_income)
                )
                value = 0

            } else if (et_specify_source_secondary_income.text.toString().length == 0 && lay_et_specify_source_secondary_income.visibility == View.VISIBLE) {
                validate!!.CustomAlertEdit(
                    this,
                    et_specify_source_secondary_income,
                    resources.getString(R.string.plz_enter_secondary_income)
                )
                value = 0

            } else if (et_days_secondary_job.text.toString()
                    .isEmpty() && lay_et_days_secondary_job.visibility == View.VISIBLE
            ) {
                validate!!.CustomAlertEdit(
                    this,
                    et_days_secondary_job,
                    resources.getString(R.string.plz_select_wrking_days_months)
                )
                value = 0

            } else if (lay_et_days_secondary_job.visibility == View.VISIBLE) {
                if (Integer.parseInt(et_days_secondary_job.text.toString()) < 1 || Integer.parseInt(
                        et_days_secondary_job.text.toString()
                    ) > 29
                ) {

                    validate!!.CustomAlertEdit(
                        this,
                        et_days_secondary_job,
                        resources.getString(R.string.please_entr_input_months)
                    )
                    value = 0
                } else if (et_avg_daily_secondry_income.text.toString()
                        .isEmpty() && lay_et_avg_daily_secondry_income.visibility == View.VISIBLE
                ) {
                    validate!!.CustomAlertEdit(
                        this,
                        et_avg_daily_secondry_income,
                        resources.getString(R.string.plz_select_avg_daily_socndry_incm)
                    )
                    value = 0

                } else if (validate!!.returnIntegerValue(et_avg_daily_secondry_income.text.toString()) < 50 ||
                        validate!!.returnIntegerValue(et_avg_daily_secondry_income.text.toString()
                    ) > 9999 && lay_et_avg_daily_secondry_income.visibility == View.VISIBLE
                ) {

                    validate!!.CustomAlertEdit(
                        this,
                        et_avg_daily_secondry_income,
                        resources.getString(R.string.please_entr_input_daily)
                    )
                    value = 0

                }

        }
        return value
    }


    fun fillSpinner(
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

    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_schemess.setBackgroundColor(resources.getColor(R.color.back))
        lay_demographic.setBackgroundColor(resources.getColor(R.color.back))
        ll_other_detailss.setBackgroundColor(resources.getColor(R.color.back))

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

        lay_demographic.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileDemographicActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_schemess.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_other_detailss.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileSixActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


    fun returnpos(id: Int, flag: Int): Int {
        val combobox = mstLookupViewModel.getMstLookupFlag(flag)
        var posi = 0
        for (i in 0 until combobox.size) {
            if (id == combobox[i].LookupCode) {
                posi = i + 1
            }
        }
        return posi
    }

    fun returnID(
        pos: Int,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode
        }
        return id
    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(600, 0)
        }, 100)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, IMProfileListActivity::class.java)
//        startActivity(intent)
//        finish()
    }
}