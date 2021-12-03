package com.careindia.lifeskills.views.improfile

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
import com.careindia.lifeskills.databinding.ActivityImprofileThirdBinding
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_improfile_third.*
import kotlinx.android.synthetic.main.activity_improfile_third.btn_prev
import kotlinx.android.synthetic.main.activity_improfile_third.btn_save
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileThirdBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_improfile_third)
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
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateThirdData()
                    val intent = Intent(this, IMProfileFourthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                if (checkValidation() == 1) {
                    var intent = Intent(this, IMProfileTwoActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }


    }

    override fun initializeController() {
        hideShowView()
        //apply click on view
        applyClickOnView()

        // fill spinner view
        fillSpinner()
        if(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.trim().length>0) {
            showLiveData()
        }


        validate!!.dynamicMultiCheck(this, lang_prefer_mobile_use, mstCommonViewModel,55)



        validate!!.fillradio(
            rg_type_emp,
            -1,
            mstCommonViewModel,
            57,
            this
        )
        validate!!.fillradio(
            rg_secondary_income,
            -1,
            mstCommonViewModel,
            60,
            this
        )

    }

    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid)).observe(this, Observer {
            if (it != null && it.size>0) {

                et_waste_pick.setText(validate!!.returnStringValue(it.get(0).Waste_Type))
                et_avg_daily_income.setText(validate!!.returnStringValue(it.get(0).Primary_Inc.toString()))
                et_no_days_job.setText(validate!!.returnStringValue(it.get(0).Primary_WD.toString()))
                spin_cate_picker_belong.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).WP_category.toString()),56))
                spin_sell_waste_collect.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Waste_Disposal.toString()),58))
                spin_source_income.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Primary_Occupation.toString()),58))
                spin_what_secondary_income.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Secondary_Occupation.toString()),61))


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

                (validate!!.SetAnswerTypeCheckBoxButton(lang_prefer_mobile_use, it.get(0).PreferredLanguage_Mobile))
            }
        })

    }
    fun fillSpinner(){
        bindCommonTable("Select",spin_cate_picker_belong,56)
        bindCommonTable("Select",spin_source_income,59)
        bindCommonTable("Select",spin_sell_waste_collect,58)
        bindCommonTable("Select",spin_what_secondary_income,61)
    }



    fun sendData(){
        imProfileViewModel.collectiveProfileThirdData(
            validate!!.GetAnswerTypeCheckBoxButtonID(lang_prefer_mobile_use),
            validate!!.GetAnswerTypeRadioButtonID(rg_type_emp),
            validate!!.GetAnswerTypeRadioButtonID(rg_secondary_income)
        )
    }


    fun  hideShowView() {

        imProfileViewModel.WASTEDISPOSAL.observe(this, Observer {
            var disposalid = it
            if(disposalid==4){
                lay_et_specify_sell_waste_collect.visibility = View.VISIBLE
            }else{
                lay_et_specify_sell_waste_collect.visibility = View.GONE
            }

        })


         imProfileViewModel.PrimaryOccup.observe(this, Observer {
            var primaryid = it
            if(primaryid==8){
                lay_et_specif_source_income.visibility = View.VISIBLE
            }else{
                lay_et_specif_source_income.visibility = View.GONE
            }

        })




        imProfileViewModel.IsSecondry.observe(this, Observer {
//            Log.i("MYTAGTWOffffff", it.toString())

            if(it ==1){
                lay_spin_what_secondary_income.visibility = View.VISIBLE
            }else{
                lay_spin_what_secondary_income.visibility = View.GONE
            }


        })

         imProfileViewModel.SecSourceIncom.observe(this, Observer {
            var primaryid = it
            if(primaryid==7){
                lay_et_specify_source_secondary_income.visibility = View.VISIBLE
                lay_et_specify_source_secondary_income.visibility = View.VISIBLE
            }else{
                lay_et_specify_source_secondary_income.visibility = View.GONE
                lay_et_specify_source_secondary_income.visibility = View.GONE
            }

        })


    }

    private fun checkValidation(): Int {
        var value = 1

        if (validate!!.GetAnswerTypeCheckBoxButtonID(lang_prefer_mobile_use).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
//        } else if (et_specify_perfer_mob.text.toString().isEmpty()) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_specify_perfer_mob,
//                resources.getString(R.string.plz_specify_othr)
//            )
//            value = 0
        } else if (spin_cate_picker_belong.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_cate_picker_belong,
                resources.getString(R.string.plz_select_waste_pickr)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_type_emp) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_employmentr)
            )
            value = 0
        } else if (et_waste_pick.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_waste_pick,
                resources.getString(R.string.plz_select_wastes_pick)
            )
            value = 0

        } else if (spin_sell_waste_collect.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_sell_waste_collect,
                resources.getString(R.string.plz_select_dispose_wastes)
            )
            value = 0


//        } else if (et_specify_sell_waste_collect.text.toString().isEmpty()) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_specify_sell_waste_collect,
//                resources.getString(R.string.plz_specify_othr)
//            )
//            value = 0

        } else if (spin_source_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_source_income,
                resources.getString(R.string.plz_select_source_income)
            )
            value = 0
//        } else if (et_specif_source_income.text.toString().isEmpty()) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_specif_source_income,
//                resources.getString(R.string.plz_specify_othr)
//            )
//            value = 0
        } else if (et_no_days_job.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_no_days_job,
                resources.getString(R.string.plz_select_working_days)
            )
            value = 0
        } else if (Integer.parseInt(et_no_days_job.text.toString()) < 1 || Integer.parseInt(
                et_no_days_job.text.toString()
            ) > 29
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_no_days_job,
                resources.getString(R.string.please_entr_input_months)
            )
            value = 0
        } else if (et_avg_daily_income.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_avg_daily_income,
                resources.getString(R.string.plz_select_avg_daily_income)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_secondary_income) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata)
            )
            value = 0
        } else if (spin_what_secondary_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_what_secondary_income,
                resources.getString(R.string.plz_select_secondary_income)
            )
            value = 0

//        } else if (et_specify_source_secondary_income.text.toString().isEmpty()) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_specify_source_secondary_income,
//                resources.getString(R.string.plz_specify_othr)
//            )
//            value = 0

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


    override fun onBackPressed() {
        val intent = Intent(this, IMProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}