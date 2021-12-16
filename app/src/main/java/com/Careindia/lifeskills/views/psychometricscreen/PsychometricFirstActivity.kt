package com.careindia.lifeskills.views.psychometricscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPsychometricFirstBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.improfile.*
import kotlinx.android.synthetic.main.activity_psychometric_first.*
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricFirstBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var psychometricViewModel: PsychometricViewModel
    var iLanguageID: Int = 0
    var hhCodess: Any? = null

    var CodeHh = ""
    var CodeIm = ""
    var IdvCode=""
    var HHCode=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_first)
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
        //fillSpinner
        topLayClick()
        fillSpinner()
        hideShowView()

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
        btn_prev.visibility = View.GONE
        btn_save.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    psychometricViewModel.saveandUpdatePsychometricData(this)
                    val intent = Intent(this, PsychometricSecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
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



    fun hideShowView() {
        psychometricViewModel.PsyHHID.observe(this, Observer {
            val hhPos = it
            if (hhPos > 0) {
                CodeHh = returnHhPrfID(hhPos) as String
                bindIdvSpinner(resources.getString(R.string.select), spin_imid, CodeHh)
                spin_imid.setSelection(returnposIMcode(IdvCode,HHCode))
            }

        })

        psychometricViewModel.PsyIMID.observe(this, Observer {
            val CodejsIm = it
            if (CodejsIm > 0) {
                CodeIm = returnIMCodeID(CodejsIm,CodeHh) as String
//               val imCode = spin_imid.getItemAtPosition(spin_imid.selectedItemPosition)
                bindPsychometricData(CodeIm)
            }

        })
    }

    fun showLiveData() {
        val patGuid = validate!!.RetriveSharepreferenceString(AppSP.PATGUID)
        psychometricViewModel.getPsychometricbyGuid(validate!!.returnStringValue(patGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_name_participant.setText(validate!!.returnStringValue(it.get(0).Name_participant))
                    et_age_participant.setText(validate!!.returnStringValue(it.get(0).Age_partcipant.toString()))
                    et_primary_occup.setText(validate!!.returnStringValue(it.get(0).Primary_occ.toString()))
                    et_secondry_occup.setText(validate!!.returnStringValue(it.get(0).Secondary_occ.toString()))
                    et_name_community.setText(validate!!.returnStringValue(it.get(0).Name_Community.toString()))
                    et_name_shg.setText(validate!!.returnStringValue(it.get(0).SHG_Name.toString()))
                    et_name_entrprise.setText(validate!!.returnStringValue(it.get(0).Nature_entrprise.toString()))
                    et_contactnumber.setText(validate!!.returnStringValue(it.get(0).Contact_number))
                    et_namecrp.setText(validate!!.returnStringValue(it.get(0).Name_CRP.toString()))
                    et_date.setText(validate!!.returnStringValue(it.get(0).Date))

                    HHCode=validate!!.returnStringValue(it.get(0).HHID)
                    IdvCode=validate!!.returnStringValue(it.get(0).IMID)
                    spin_hhid.setSelection(
                        returnposHHcode(
                            validate!!.returnStringValue(it.get(0).HHID)
                        )
                    )
//                    spin_imid.setSelection(
//                        returnposIMcode(
//                            validate!!.returnStringValue(it.get(0).IMID)
//                        )
//                    )

                }
            })

    }


    private fun checkValidation(): Int {
        var value = 1

        if (spin_hhid.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_hhid,
                resources.getString(R.string.psy_plz_select_hhid)
            )
            value = 0

        } else if (spin_imid.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_imid,
                resources.getString(R.string.psy_plz_select_imid)
            )
            value = 0
//        } else if (et_name_participant.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_name_participant,
//                resources.getString(R.string.plz_enter_name_participant_psy)
//            )
//            value = 0
//        } else if (et_age_participant.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_age_participant,
//                resources.getString(R.string.plz_enter_age_participant_psy)
//            )
//            value = 0
//        } else if (et_primary_occup.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_primary_occup,
//                resources.getString(R.string.plz_enter_primary_occu_psy)
//            )
//            value = 0
//        } else if (et_secondry_occup.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_secondry_occup,
//                resources.getString(R.string.plz_enter_secondry_occu_psy)
//            )
//            value = 0
//        } else if (et_name_community.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_name_community,
//                resources.getString(R.string.plz_enter_name_community_psy)
//            )
//            value = 0
//        } else if (et_name_entrprise.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_name_entrprise,
//                resources.getString(R.string.plz_enter_nature_enterprise_psy)
//            )
//            value = 0
//        } else if (et_contactnumber.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_contactnumber,
//                resources.getString(R.string.plz_enter_contact_no_psy)
//            )
//            value = 0
//        } else if (et_namecrp.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_contactnumber,
//                resources.getString(R.string.plz_enter_name_crp_psy)
//            )
//            value = 0
//        }else if (et_date.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_contactnumber,
//                resources.getString(R.string.plz_enter_date_psy)
//            )
//            value = 0
        }
        return value
    }


    fun fillSpinner() {
        bindHHIDTable(resources.getString(R.string.select), spin_hhid)

    }


    fun sendData() {
        val imCode = spin_imid.getItemAtPosition(spin_imid.selectedItemPosition)
        psychometricViewModel.collectivefirstData(
            CodeHh,
            imCode
        )


    }

    fun bindPsychometricData(idvcode: String) {
        psychometricViewModel.getallIdvdata(idvcode).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                for (i in 0 until it.size) {
                    et_name_participant.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_age_participant.setText(validate!!.returnStringValue(it.get(0).Age.toString()))
                    et_contactnumber.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
                    et_date.setText(validate!!.returnStringValue(it.get(0).DateForm.toString()))
                    et_namecrp.setText(validate!!.returnStringValue(it.get(0).Name_CRP.toString()))
                    et_primary_occup.setText(
                        returnStringByPos(
                            it.get(0).Primary_Occupation,
                            13,
                            iLanguageID
                        )
                    )
                        et_secondry_occup.setText(
                            returnStringByPos(
                                it.get(0).Secondary_Occupation,
                                14,
                                iLanguageID
                            )
                        )
                    et_name_community.setText(returnStringByPos(it.get(0).Caste, 5, iLanguageID))
                }

            }
        })
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
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

    fun bindHHIDTable(strValue: String, spin: Spinner) {
        psychometricViewModel.gethhProfileData().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).HHCode
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


    fun bindIMIDTable(strValue: String, spin: Spinner, hhcode: String) {
        psychometricViewModel.getallhhProfiledata(hhcode)
            .observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    val iGen = it.size
                    val name = arrayOfNulls<String>(iGen + 1)
                    name[0] = strValue

                    for (i in 0 until it.size) {
                        name[i + 1] = it.get(i).IndvCode

                        Log.i("MyInSpinData", "$name")
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



    fun bindIdvSpinner(strValue: String, spin: Spinner,hhcode: String) {
        var zonedata= psychometricViewModel.getallIdvPrfdata(hhcode)
        if (zonedata != null) {
            val iGen = zonedata.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until zonedata.size) {
                name[i + 1] = zonedata.get(i).IndvCode
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }



    fun returnStringByPos(pos: Int?, flag: Int, iLanguageID: Int): String? {
        var id: String? = ""
        var data: List<MstLookupEntity>? = null
        data = mstLookupViewModel.getMstAllData(flag, iLanguageID)

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).Description
            }

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

    fun returnHhPrfID(pos: Int?): String? {
        var data: List<HouseholdProfileEntity>? = null
        data = psychometricViewModel.gethhProfileDataNew()

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHCode

            }
        }
        Log.i("MyGETHHIDATA", "$id")
        return id
    }

fun returnIMCodeID(pos: Int?,hhcode:String): String? {
        var data: List<IndividualProfileEntity>? = null
        data = psychometricViewModel.getallIdvPrfdata(hhcode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndvCode

            }
        }
        Log.i("MyIIIIMMM", "$id")
        return id
    }


    fun returnposHHcode(strValue: String): Int {
        var posi = 0
        var hhcode = psychometricViewModel.gethhProfileDataNew()
        if (hhcode != null) {
            for (i in 0 until hhcode.size) {

                if (strValue == hhcode.get(i).HHCode) {
                    posi = i + 1
                }
            }
        }
//        Log.i("MyTagRETURN","$posi")
        return posi
    }

    fun returnposIMcode(strValue: String,hhcode:String): Int {
        var posi = 0
        var hhcode = psychometricViewModel.getallIdvPrfdata(hhcode)
        if (hhcode != null) {
            for (i in 0 until hhcode.size) {

                if (strValue == hhcode.get(i).IndvCode) {
                    posi = i + 1
                }
            }
        }
        Log.i("MyBholeKK", "$posi")
        return posi
    }


    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(0, 500)
        }, 100)
    }

//            hhCodess = spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition)
//            bindIMIDTable(resources.getString(R.string.select), spin_imid, hhCodess.toString())
//            spin_imid.setSelection(returnposIMcode(hhCodess.toString()))


//    var imCode: Any? = null
//    imCode = spin_imid.getItemAtPosition(spin_imid.selectedItemPosition)
//    bindPsychometricData(imCode.toString())

    override fun onBackPressed() {
//        val intent = Intent(this, HomeDashboardActivity::class.java)
//        startActivity(intent)
//        finish()
    }

}