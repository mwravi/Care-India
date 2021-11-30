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
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_fifth.*
import kotlinx.android.synthetic.main.activity_improfile_fifth.btn_prev
import kotlinx.android.synthetic.main.activity_improfile_fifth.btn_save
import kotlinx.android.synthetic.main.activity_improfile_fourth.*
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileFifthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileFifthBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_fifth)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.im_profile)

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val improfileRepository = IndividualProfileRepository(improfiledao!!, commondao!!)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this


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
            R.id.btn_save-> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateProfileFifthData()
                    var intent = Intent(this, IMProfileListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev-> {
                if (checkValidation() == 1) {
                    val intent = Intent(this,IMProfileFourthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    override fun initializeController() {

        //apply click on view
        applyClickOnView()

        // fill spinner view
        fillSpinner()
        if(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.trim().length>0) {
            showLiveData()
        }
        validate!!.fillradio(
            rg_avail_any_scheme,
            -1,
            mstCommonViewModel,
            69,
            this
        )
        validate!!.fillradio(
            rg_new_jobs_business,
            -1,
            mstCommonViewModel,
            69,
            this
        )

        validate!!.fillSpinner(
            this,
            spin_alternative_get_opportunity,
            resources.getString(R.string.select),
            mstCommonViewModel,
            72
        )

        validate!!.fillradio(
            rg_member_cig_shg,
            -1,
            mstCommonViewModel,
            68,
            this
        )

//        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))

        validate!!.dynamicMultiCheck(this, skills_jobs_picking, mstCommonViewModel, 70)

    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid)).observe(this, Observer {
            if (it != null && it.size>0) {
                et_details_service_avail.setText(validate!!.returnStringValue(it.get(0).SchemeDetails_Cur.toString()))
                et_skills_jobs_picking.setText(validate!!.returnStringValue(it.get(0).Jobs))
                et_name_collective_part.setText(validate!!.returnStringValue(it.get(0).Collective_name))

                spin_alternative_get_opportunity.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Interested_JobDetail.toString()),72))

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

    fun fillSpinner(){
        bindCommonTable("Select",spin_alternative_get_opportunity,72)
    }

    fun sendData() {
        imProfileViewModel.collectiveProfileFifthData(
            validate!!.GetAnswerTypeRadioButtonID(rg_avail_any_scheme),
            validate!!.GetAnswerTypeCheckBoxButtonID(skills_jobs_picking),
            validate!!.GetAnswerTypeRadioButtonID(rg_new_jobs_business),
            validate!!.GetAnswerTypeRadioButtonID(rg_member_cig_shg)
        )
    }

    private fun checkValidation(): Int {
        var value = 1

        if (validate!!.GetAnswerTypeRadioButtonID(rg_avail_any_scheme) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_availing_scheme)
            )
            value = 0


        } else if (et_details_service_avail.text.toString().isEmpty()) {
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
        } else if (et_skills_jobs_picking.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_skills_jobs_picking,
                resources.getString(R.string.plz_specify_othr)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_new_jobs_business) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_interested_newjob)
            )
            value = 0
        } else if (spin_alternative_get_opportunity.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_alternative_get_opportunity,
                resources.getString(R.string.plz_select_alternative_jobbussi)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_member_cig_shg) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_member_cig_shg)
            )
            value = 0
        } else if (et_name_collective_part.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_name_collective_part,
                resources.getString(R.string.plz_select_name_coltiv_part)
            )
            value = 0
        }
        return value
    }


    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int) {
        mstCommonViewModel.getMstCommondata(flag).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).value
                }
                val adapter_category = ArrayAdapter<String>(
                    this,
                    R.layout.my_spinner_space_dashboard, name
                )
                adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                spin.adapter = adapter_category
            }
        })
        /*if (distric>0) {
            spin_district_name.setSelection(returnpos(distric, 3))
        }*/
    }


    fun returnpos(id: Int,flag: Int): Int {
        val combobox = mstCommonViewModel.getMstCommon(flag)
        var posi = 0
        for (i in 0 until combobox.size) {
            if (id == combobox[i].id) {
                posi = i + 1
            }
        }
        return posi
    }
}