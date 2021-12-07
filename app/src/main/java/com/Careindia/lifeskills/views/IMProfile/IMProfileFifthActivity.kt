package com.careindia.lifeskills.views.improfile

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
import com.careindia.lifeskills.databinding.ActivityImprofileFifthBinding
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_third.*
import kotlinx.android.synthetic.main.activity_improfile_fifth.*
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileFifthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileFifthBinding
    var validate: Validate? = null
    var iLanguageID: Int? = 0
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_fifth)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.im_profile)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = IndividualProfileRepository(improfiledao!!,mstDistrictDao)

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
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateProfileFifthData()
                    var intent = Intent(this, IMProfileListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                if (checkValidation() == 1) {
                    val intent = Intent(this, IMProfileFourthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }

    }

    override fun initializeController() {

        //apply click on view
        applyClickOnView()
        topLayClick()
        // fill spinner view
        fillSpinner()
        fillRadio()
        hideShowView()
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

//        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))

        validate!!.dynamicMultiCheckChange(
            this,
            skills_jobs_picking,
            mstLookupViewModel,
            15,
            1,
            et_skills_jobs_picking,
            lay_et_specify
        )

    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_details_service_avail.setText(validate!!.returnStringValue(it.get(0).SchemeDetails_Cur.toString()))
                    et_skills_jobs_picking.setText(validate!!.returnStringValue(it.get(0).Jobs))
                    et_name_collective_part.setText(validate!!.returnStringValue(it.get(0).Collective_name))

                    spin_alternative_get_opportunity.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(0).Interested_JobDetail.toString()
                            ), 16
                        )
                    )

                    (it.get(0).SchemesAvailed_Cur?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_avail_any_scheme,
                            it1
                        )
                    })
                    (it.get(0).Interested_Job?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_new_jobs_business,
                            it1
                        )
                    })
                    (it.get(0).Member_Collective?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_member_cig_shg,
                            it1
                        )
                    })

                    (validate!!.SetAnswerTypeCheckBoxButton(
                        skills_jobs_picking,
                        it.get(0).SchemeSP_Cur
                    ))

                }
            })

    }

    fun fillRadio() {
        validate!!.fillradioNew(
            this,
            rg_avail_any_scheme,
            -1,
            mstLookupViewModel,
            3,
            1
        )
        validate!!.fillradioNew(
            this,
            rg_new_jobs_business,
            -1,
            mstLookupViewModel,
            3,
            1
        )



        validate!!.fillradioNew(
            this,
            rg_member_cig_shg,
            -1,
            mstLookupViewModel,
            3,
            1
        )

    }

    fun fillSpinner() {
        bindCommonTable("Select", spin_alternative_get_opportunity, 16, 1)
    }

    fun sendData() {
        imProfileViewModel.collectiveProfileFifthData(
            validate!!.GetAnswerTypeRadioButtonID(rg_avail_any_scheme),
            validate!!.GetAnswerTypeCheckBoxButtonID(skills_jobs_picking),
            validate!!.GetAnswerTypeRadioButtonID(rg_new_jobs_business),
            validate!!.GetAnswerTypeRadioButtonID(rg_member_cig_shg)
        )
    }

    fun hideShowView() {

        imProfileViewModel.IsCIGMember.observe(this, Observer {
            if (it == 1) {
                lay_et_name_collective_part.visibility = View.VISIBLE
            } else {
                lay_et_name_collective_part.visibility = View.GONE
            }


        })
    }

    private fun checkValidation(): Int {
        var value = 1



        if (et_details_service_avail.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_details_service_avail,
                resources.getString(R.string.please_prvd_service_detail)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(skills_jobs_picking).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
        } else if (et_skills_jobs_picking.text.toString().length==0 && lay_et_specify.visibility==View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_skills_jobs_picking,
                resources.getString(R.string.plz_skills_jobs_picking)
            )
            value = 0

        } else if (spin_alternative_get_opportunity.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_alternative_get_opportunity,
                resources.getString(R.string.plz_select_alternative_jobbussi)
            )
            value = 0

        } else if (et_name_collective_part.text.toString().length==0 && lay_et_name_collective_part.visibility==View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_name_collective_part,
                resources.getString(R.string.plz_select_name_coltiv_part)
            )
            value = 0
        }
        return value
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

    fun topLayClick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

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
}