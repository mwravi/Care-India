package com.careindia.lifeskills.views.psychometricscreen

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
import com.careindia.lifeskills.databinding.ActivityPsychometricThirdBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_psychometric_third.*
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricThirdBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var psychometricViewModel: PsychometricViewModel
    var iLanguageID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_third)
        validate = Validate(this)
        tv_title.text = "Psychometric"

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val psychometricRepository = PsychometricRepository(psychometricdao!!)
        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(psychometricRepository)
        )[PsychometricViewModel::class.java]

        binding.psychometricViewModel = psychometricViewModel
        binding.lifecycleOwner = this
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        initializeController()
    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        //fillSpinnner
        fillSpinner()
        topLayClick()

        if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PATGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }
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
                    psychometricViewModel.updateSaveThirdData(this)
                    var intent = Intent(this, PsychometricForthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                var intent = Intent(this, PsychometricSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_back -> {
                val intent = Intent(this, PsychometricSecondActivity::class.java)
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
        fillSpinner(resources.getString(R.string.select), spin_emp_category, 39, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_year_exp, 40, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_stage_emp, 41, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_size_investment, 42, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_invest_money, 43, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_awareness_market, 44, iLanguageID)
    }


    fun showLiveData() {
        val patGuid = validate!!.RetriveSharepreferenceString(AppSP.PATGUID)
        psychometricViewModel.getPsychometricbyGuid(validate!!.returnStringValue(patGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    spin_emp_category.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).self_emp_exp.toString()),
                            39
                        )
                    )
                    spin_year_exp.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).year_exp_self_emp.toString()),
                            40
                        )
                    )
                    spin_stage_emp.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).stage_self_emp_idea.toString()),
                            41
                        )
                    )
                    spin_size_investment.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).size_self_emp_planned.toString()
                            ), 42
                        )
                    )
                    spin_invest_money.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).wil_invst_marg_mny.toString()),
                            43
                        )
                    )
                    spin_awareness_market.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).awr_rel_market_self_emp.toString()
                            ), 44
                        )
                    )


                }
            })
    }
    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {

            val intent = Intent(this, PsychometricFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
                val intent = Intent(this, PsychometricSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
                val intent = Intent(this, PsychometricThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_forth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
                val intent = Intent(this, PsychometricForthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun checkValidation(): Int {
        var value = 1

        if (spin_emp_category.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_emp_category,
                resources.getString(R.string.psy_plz_ans_epm_cate)
            )
            value = 0
        } else if (spin_year_exp.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_year_exp,
                resources.getString(R.string.psy_plz_ans_yrs_exp)
            )
            value = 0
        } else if (spin_stage_emp.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_stage_emp,
                resources.getString(R.string.psy_plz_ans_stage_epm)
            )
            value = 0
        } else if (spin_size_investment.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_size_investment,
                resources.getString(R.string.psy_plz_ans_size_invest)
            )
            value = 0
        } else if (spin_invest_money.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_invest_money,
                resources.getString(R.string.psy_plz_ans_willgns_invest)
            )
            value = 0
        } else if (spin_awareness_market.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_awareness_market,
                resources.getString(R.string.psy_plz_ans_awrness_emp)
            )
            value = 0
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
            horizontalScroll.smoothScrollBy(500, 0)
        }, 100)
    }
}