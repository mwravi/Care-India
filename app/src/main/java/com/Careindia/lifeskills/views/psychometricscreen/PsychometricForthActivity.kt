package com.careindia.lifeskills.views.psychometricscreen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPsychometricForthBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_demographic.*
import kotlinx.android.synthetic.main.activity_psychometric_forth.*
import kotlinx.android.synthetic.main.activity_psychometric_forth.btn_bottom
import kotlinx.android.synthetic.main.activity_psychometric_forth.btn_prev
import kotlinx.android.synthetic.main.activity_psychometric_forth.btn_save
import kotlinx.android.synthetic.main.bottom_nav_psycho_layout.*

import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricForthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricForthBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var psychometricViewModel: PsychometricViewModel
    var iLanguageID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_forth)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.psychometric)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = PsychometricRepository(psychometricdao!!,mstDistrictDao)
        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(improfileRepository)
        )[PsychometricViewModel::class.java]

        binding.psychometricViewModel = psychometricViewModel
        binding.lifecycleOwner = this
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)


        initializeController()
    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        //fillSpinner/multicheck
        fillSpinner()
        topLayClick()
        fillMultiCheck()

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
                    sendData()
                    psychometricViewModel.updateSaveForthData(this)
                    CustomAlert(resources.getString(R.string.data_saved_successfully))
                }
            }
            R.id.btn_prev -> {
                var intent = Intent(this, PsychometricThirdActivity::class.java)
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
        fillSpinner(resources.getString(R.string.select), spin_evaluate_risk, 45, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_income_gen_prefer, 46, iLanguageID)
        fillSpinnerChange(
            resources.getString(R.string.select),
            spin_staff_required_prefer,
            47,
            iLanguageID
        )
        fillSpinner(resources.getString(R.string.select), spin_women_entrepreneurs, 48, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_requires_financial, 49, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_willingness_invest, 50, iLanguageID)
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
                    spin_evaluate_risk.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).evaluate_risk.toString()),
                            45
                        )
                    )
                    spin_income_gen_prefer.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).income_gen_act_invst.toString()
                            ), 46
                        )
                    )
                    spin_staff_required_prefer.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(0).income_gen_act_manage.toString()
                            ), 47
                        )
                    )
                    spin_women_entrepreneurs.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).woman_ct_good_ent.toString()
                            ), 48
                        )
                    )
                    spin_requires_financial.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).ent_req_fin_res.toString()
                            ), 49
                        )
                    )
                    spin_willingness_invest.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(
                                    0
                                ).wil_invst_cap_building.toString()
                            ), 50
                        )
                    )
                    (validate!!.SetAnswerTypeCheckBoxButton(
                        multiCheck_areas_succes_entrep,
                        it.get(0).successful_ent
                    ))
                    et_specifyareas_succes_entrep.setText(validate!!.returnStringValue(it.get(0).others_ent))
                }
            })
    }

    fun sendData() {
        psychometricViewModel.collectiveData(
            validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_areas_succes_entrep)
        )
    }

    fun fillMultiCheck() {
        validate!!.dynamicMultiCheckChange(
            this,
            multiCheck_areas_succes_entrep,
            mstLookupViewModel,
            51,
            iLanguageID,
            et_specifyareas_succes_entrep,
            lay_specify_areas_succes_entrep
        )
    }



    private fun checkValidation(): Int {
        var value = 1

        if (spin_evaluate_risk.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_evaluate_risk,
                resources.getString(R.string.psy_plz_ans_idea_buss)
            )
            value = 0

        } else if (spin_income_gen_prefer.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_income_gen_prefer,
                resources.getString(R.string.psy_plz_ans_income_day)
            )
            value = 0
        } else if (spin_staff_required_prefer.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_staff_required_prefer,
                resources.getString(R.string.psy_plz_ans_income_gen)
            )
            value = 0
        } else if (spin_women_entrepreneurs.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_women_entrepreneurs,
                resources.getString(R.string.psy_plz_ans_women_entrprenur)
            )
            value = 0
        } else if (spin_requires_financial.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_requires_financial,
                resources.getString(R.string.psy_plz_ans_financial_entrprenur)
            )
            value = 0
        } else if (spin_willingness_invest.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_willingness_invest,
                resources.getString(R.string.psy_plz_ans_willngness_potential)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_areas_succes_entrep)
                .isEmpty()
        ) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.psy_plz_ans_area_capacity)
            )
            value = 0

        } else if (et_specifyareas_succes_entrep.text.toString().length == 0 && lay_specify_areas_succes_entrep.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specifyareas_succes_entrep,
                resources.getString(R.string.psy_plz_ans_area_specify)
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
 fun fillSpinnerChange(
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
                        R.layout.my_spinner_space_psy_layout, name
                    )
                    adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                    spin.adapter = adapter_category
                }
            })

    }


    fun CustomAlert(
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
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            val intent = Intent(this, PsychometricListActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
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


    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(1000, 0)
        }, 100)
    }
    override fun onBackPressed() {

    }
}