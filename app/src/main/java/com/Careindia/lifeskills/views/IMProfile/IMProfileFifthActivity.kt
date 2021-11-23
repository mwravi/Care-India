package com.careindia.lifeskills.views.improfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_fifth.*
import kotlinx.android.synthetic.main.activity_improfile_fourth.*
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileFifthActivity : BaseActivity(),View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improfile_fifth)
        validate = Validate(this)
        tv_title.text = "IM Profile"
        initializeController()

    }

    override fun initializeController() {
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        //apply click on view
        applyClickOnView()

        validate!!.fillSpinner(
            this,
            spin_avail_any_scheme,
            resources.getString(R.string.select),
            mstCommonViewModel,
            69
        )
        validate!!.fillSpinner(
            this,
            spin_new_jobs_business,
            resources.getString(R.string.select),
            mstCommonViewModel,
            69
        )
        validate!!.fillSpinner(
            this,
            spin_alternative_get_opportunity,
            resources.getString(R.string.select),
            mstCommonViewModel,
            72
        )
        validate!!.fillSpinner(
            this,
            spin_member_cig_shg,
            resources.getString(R.string.select),
            mstCommonViewModel,
            68
        )


//        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))

        validate!!.dynamicMultiCheck(this, skills_jobs_picking, mstCommonViewModel,70)

    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.setOnClickListener(this)
        btn_save.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_prev -> {
                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileOneActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    val intent = Intent(this, IMProfileListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }


    private fun checkValidation(): Int {
        var value = 1

         if (spin_avail_any_scheme.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_avail_any_scheme,
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

        } else if (spin_new_jobs_business.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_new_jobs_business,
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
        } else if (spin_member_cig_shg.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_member_cig_shg,
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