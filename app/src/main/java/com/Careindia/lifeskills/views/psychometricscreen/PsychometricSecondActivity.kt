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
import com.careindia.lifeskills.databinding.ActivityPsychometricSecondBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_psychometric_second.*
import kotlinx.android.synthetic.main.activity_psychometric_second.btn_prev
import kotlinx.android.synthetic.main.activity_psychometric_second.btn_save
import kotlinx.android.synthetic.main.bottom_nav_psycho_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricSecondActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricSecondBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var psychometricViewModel: PsychometricViewModel
    var iLanguageID: Int = 0
    var paricipantAge: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_second)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.psychometric)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val psychometricRepository = PsychometricRepository(psychometricdao!!,mstDistrictDao)
        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(psychometricRepository)
        )[PsychometricViewModel::class.java]

        binding.psychometricViewModel = psychometricViewModel
        binding.lifecycleOwner = this
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        paricipantAge = validate!!.RetriveSharepreferenceInt(AppSP.PSYAGE)


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

        hideShowView()

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
                    psychometricViewModel.updateSaveSecData(this)
                    var intent = Intent(this, PsychometricThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                var intent = Intent(this, PsychometricFirstActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_back -> {
                val intent = Intent(this, PsychometricListActivity::class.java)
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
        fillSpinner(resources.getString(R.string.select), spin_min_age_limit, 35, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_educ_applicant, 36, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_socially_category, 37, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_economic_category, 38, iLanguageID)
    }


    fun hideShowView(){

        if((paricipantAge > 17) && (paricipantAge < 25)){
            spin_min_age_limit.setSelection(returnpos(4,35))
        }else if((paricipantAge > 24) && (paricipantAge < 35)){
            spin_min_age_limit.setSelection(returnpos(3,35))
        }else if((paricipantAge > 34) && (paricipantAge < 45)){
            spin_min_age_limit.setSelection(returnpos(2,35))
        }else if(paricipantAge > 44){
            spin_min_age_limit.setSelection(returnpos(1,35))
        }
        spin_min_age_limit.isEnabled = false

    }

    fun showLiveData() {
        val patGuid = validate!!.RetriveSharepreferenceString(AppSP.PATGUID)
        psychometricViewModel.getPsychometricbyGuid(validate!!.returnStringValue(patGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
//                    spin_min_age_limit.setSelection(
//                        returnpos(
//                            validate!!.returnIntegerValue(it.get(0).min_age_applicant.toString()),
//                            35
//                        )
//                    )

                    spin_educ_applicant.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).applicant_edu.toString()
                            ), 36
                        )
                    )
                    spin_socially_category.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).pref_woman_social_bw.toString()
                            ), 37
                        )
                    )

                    et_cast_belong.setText( it.get(
                        0
                    ).CastBelong)
                    spin_economic_category.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).pref_woman_eco_bw.toString()
                            ), 38
                        )
                    )


                }
            })
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
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
        if (spin_min_age_limit.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_min_age_limit,
                resources.getString(R.string.psy_plz_ans_min_age)
            )
            value = 0

        } else if (spin_educ_applicant.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_educ_applicant,
                resources.getString(R.string.psy_plz_ans_applicat_edu)
            )
            value = 0
        } else if (spin_socially_category.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_socially_category,
                resources.getString(R.string.psy_plz_ans_socily_cate)
            )
            value = 0
        } else if (et_cast_belong.text.toString().length==0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.psy_plz_ans_econmic_cate)
            )
            value = 0
        } else if (spin_economic_category.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_economic_category,
                resources.getString(R.string.psy_plz_ans_castte)
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
            horizontalScroll.smoothScrollBy(400, 0)
        }, 100)
    }


    override fun onBackPressed() {

    }
}