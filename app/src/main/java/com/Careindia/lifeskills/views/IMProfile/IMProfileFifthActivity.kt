package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileFifthBinding
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_fifth.*
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
        tv_title.text = "IM Profile"

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
}