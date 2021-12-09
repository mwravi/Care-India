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
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_psychometric_first.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricFirstBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel
    var iLanguageID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_first)
        validate = Validate(this)
        tv_title.text = "Psychometric"

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

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        //fillSpinner
        fillSpinner()
        hideShowView()
    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.visibility = View.GONE
        btn_save.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {

                val intent = Intent(this, PsychometricSecondActivity::class.java)
                startActivity(intent)
                finish()
            }


        }

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
        } else if (et_name_participant.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_name_participant,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_age_participant.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_age_participant,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_primary_occup.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_primary_occup,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_secondry_occup.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_secondry_occup,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_name_community.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_name_community,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_name_entrprise.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_name_entrprise,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_contactnumber.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnumber,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        }
        return value
    }

    fun fillSpinner() {
        bindCommonTable("Select", spin_primary, 13, iLanguageID)
        bindCommonTable("Select", spin_secondry, 14, iLanguageID)
        bindCommonTable("Select", spin_cammunity, 5, iLanguageID)

        bindHHIDTable("Select", spin_hhid)
    }

    fun hideShowView() {
        imProfileViewModel.PsyHHID.observe(this, Observer {
            var hhCodess: Any? = null
             hhCodess = spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition)
            bindIMIDTable(resources.getString(R.string.select), spin_imid, hhCodess.toString())
        })

        imProfileViewModel.PsyIMID.observe(this, Observer {
            var imCode: Any? = null
            imCode = spin_imid.getItemAtPosition(spin_imid.selectedItemPosition)
            bindIMProfileData(imCode.toString())
        })
    }

    fun bindIMProfileData(idvcode: String) {
        imProfileViewModel.getallIdvdata(idvcode).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                for (i in 0 until it.size) {
                    et_name_participant.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_age_participant.setText(validate!!.returnStringValue(it.get(0).Age.toString()))
                    et_contactnumber.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
                    et_date.setText(validate!!.returnStringValue(it.get(0).DateForm.toString()))

                    spin_primary.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Primary_Occupation.toString()),
                            13
                        )
                    )
                    spin_secondry.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Secondary_Occupation.toString()),
                            14
                        )
                    )
                    spin_cammunity.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Caste.toString()),
                            5
                        )
                    )

                    var ss = returnpos(
                        validate!!.returnIntegerValue(it.get(0).Caste.toString()),
                        5
                    )

                    //Log.i("MyTest", "$ss")


                    var cammunity =
                        spin_cammunity.getItemAtPosition(spin_cammunity.selectedItemPosition)
                    var primary_occup =
                        spin_primary.getItemAtPosition(spin_primary.selectedItemPosition)
                    var secondry_occup =
                        spin_secondry.getItemAtPosition(spin_secondry.selectedItemPosition)
                    //Log.i("MyTest2", "$cammunity")
                    et_name_community.setText(cammunity.toString())
                    et_primary_occup.setText(primary_occup.toString())
                    et_secondry_occup.setText(secondry_occup.toString())


                }

            }
        })
    }

    fun bindHHIDTable(strValue: String, spin: Spinner) {
        imProfileViewModel.gethhProfileData().observe(this, androidx.lifecycle.Observer {
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
        imProfileViewModel.getallhhProfiledata(hhcode).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).IndvCode
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


    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int, iLanguageID: Int) {
        mstLookupViewModel.getMstUser(flag, iLanguageID).observe(this, androidx.lifecycle.Observer {
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

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}