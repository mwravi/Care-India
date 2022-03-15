package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileFifthBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.activity_improfile_fifth.*
import kotlinx.android.synthetic.main.activity_improfile_fifth.btn_bottom
import kotlinx.android.synthetic.main.activity_improfile_fifth.btn_prev
import kotlinx.android.synthetic.main.activity_improfile_fifth.btn_save
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileFifthActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileFifthBinding
    var validate: Validate? = null
    var iLanguageID: Int = 0
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
        val improfileRepository = IndividualProfileRepository(improfiledao!!, mstDistrictDao)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this

        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        initializeController()



        et_service_detail_respodent1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_detail_respodent2.isEnabled = true
                }else{
                    et_service_detail_respodent2.isEnabled = false
                    et_service_detail_respodent2.setText("")
                }
            }
        })

        et_service_detail_respodent2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_detail_respodent3.isEnabled = true
                }else{
                    et_service_detail_respodent3.isEnabled = false
                    et_service_detail_respodent3.setText("")
                }
            }
        })

        et_service_detail_respodent3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_detail_respodent4.isEnabled = true
                }else{
                    et_service_detail_respodent4.isEnabled = false
                    et_service_detail_respodent4.setText("")
                }

            }
        })


        et_service_detail_respodent4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_detail_respodent5.isEnabled = true
                }else{
                    et_service_detail_respodent5.isEnabled = false
                    et_service_detail_respodent5.setText("")
                }
            }
        })


        //Q509...//

        et_service_provider_department1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_provider_department2.isEnabled = true
                }else{
                    et_service_provider_department2.isEnabled = false
                    et_service_provider_department2.setText("")
                }
            }
        })

        et_service_provider_department2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_provider_department3.isEnabled = true
                }else{
                    et_service_provider_department3.isEnabled = false
                    et_service_provider_department3.setText("")
                }
            }
        })

        et_service_provider_department3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_provider_department4.isEnabled = true
                }else{
                    et_service_provider_department4.isEnabled = false
                    et_service_provider_department4.setText("")
                }

            }
        })


        et_service_provider_department4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_service_provider_department5.isEnabled = true
                }else{
                    et_service_provider_department5.isEnabled = false
                    et_service_provider_department5.setText("")
                }
            }
        })

        //Q511.//..
        et_details_service_avail1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_details_service_avail2.isEnabled = true
                }else{
                    et_details_service_avail2.isEnabled = false
                    et_details_service_avail2.setText("")
                }
            }
        })

        et_details_service_avail2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_details_service_avail3.isEnabled = true
                }else{
                    et_details_service_avail3.isEnabled = false
                    et_details_service_avail3.setText("")
                }
            }
        })

        et_details_service_avail3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_details_service_avail4.isEnabled = true
                }else{
                    et_details_service_avail4.isEnabled = false
                    et_details_service_avail4.setText("")
                }

            }
        })


        et_details_service_avail4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_details_service_avail5.isEnabled = true
                }else{
                    et_details_service_avail5.isEnabled = false
                    et_details_service_avail5.setText("")
                }
            }
        })

        //..Q512..//

        et_darpartment_service_provide1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_darpartment_service_provide2.isEnabled = true
                }else{
                    et_darpartment_service_provide2.isEnabled = false
                    et_darpartment_service_provide2.setText("")
                }
            }
        })

        et_darpartment_service_provide2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_darpartment_service_provide3.isEnabled = true
                }else{
                    et_darpartment_service_provide3.isEnabled = false
                    et_darpartment_service_provide3.setText("")
                }
            }
        })

        et_darpartment_service_provide3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_darpartment_service_provide4.isEnabled = true
                }else{
                    et_darpartment_service_provide4.isEnabled = false
                    et_darpartment_service_provide4.setText("")
                }

            }
        })


        et_darpartment_service_provide4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_darpartment_service_provide5.isEnabled = true
                }else{
                    et_darpartment_service_provide5.isEnabled = false
                    et_darpartment_service_provide5.setText("")
                }
            }
        })


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
                    imProfileViewModel.updateProfileFifthData(this)
                    val intent = Intent(this, IMProfileSixActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                val intent = Intent(this, IMProfileFourthActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.img_back -> {
                val intent = Intent(this, IMProfileListActivity::class.java)
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


    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }

                    it.get(0).SchemeDetails_Cur?.let { it1 ->
                        validate!!.setSchemes(
                            et_details_service_avail1,
                            et_details_service_avail2,
                            et_details_service_avail3,
                            et_details_service_avail4,
                            et_details_service_avail5,
                            it1
                        )
                    }

                    it.get(0).SchemeSP?.let { it1 ->
                        validate!!.setSchemes(
                            et_service_provider_department1,
                            et_service_provider_department2,
                            et_service_provider_department3,
                            et_service_provider_department4,
                            et_service_provider_department5,
                            it1
                        )
                    }

                    it.get(0).SchemeSP_Cur?.let { it1 ->
                        validate!!.setSchemes(
                            et_darpartment_service_provide1,
                            et_darpartment_service_provide2,
                            et_darpartment_service_provide3,
                            et_darpartment_service_provide4,
                            et_darpartment_service_provide5,
                            it1
                        )
                    }

                    it.get(0).SchemeDetails?.let { it1 ->
                        validate!!.setSchemes(
                            et_service_detail_respodent1,
                            et_service_detail_respodent2,
                            et_service_detail_respodent3,
                            et_service_detail_respodent4,
                            et_service_detail_respodent5,
                            it1
                        )
                    }


                    (it.get(0).SchemesAvailed_Cur?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_avail_any_scheme,
                            it1
                        )
                    })
                    (it.get(0).SchemesAvailed?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_availed_services_past,
                            it1
                        )
                    })

                }
            })

    }


    fun fillRadio() {
        validate!!.fillradio(
            this,
            rg_avail_any_scheme,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_availed_services_past,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

    }

    fun fillSpinner() {

    }

    fun sendData() {
        imProfileViewModel.collectiveProfileFifthData(
            validate!!.GetAnswerTypeRadioButtonID(rg_avail_any_scheme),
            validate!!.GetAnswerTypeRadioButtonID(rg_availed_services_past)
        )
    }

    fun setEdidTextFill(){


        et_service_detail_respodent1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

                if (s.length != 0) {
                    et_service_detail_respodent2.isEnabled = true
                }else{
                    et_service_detail_respodent2.isEnabled = false
                    et_service_detail_respodent2.setText("")
                }

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        et_service_detail_respodent2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                if (s.length != 0) {
                    et_service_detail_respodent3.isEnabled = true
                }else{
                    et_service_detail_respodent3.isEnabled = false
                    et_service_detail_respodent3.setText("")
                }

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        et_service_detail_respodent3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                if (s.length != 0) {
                    et_service_detail_respodent4.isEnabled = true
                }else{
                    et_service_detail_respodent4.isEnabled = false
                    et_service_detail_respodent4.setText("")
                }

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })


        et_service_detail_respodent4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                if (s.length != 0) {
                    et_service_detail_respodent5.isEnabled = true
                }else{
                    et_service_detail_respodent5.isEnabled = false
                    et_service_detail_respodent5.setText("")
                }

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

    }


    private fun checkValidation(): Int {
        var value = 1
        if (rg_availed_services_past.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_services_past_month)
            )
            value = 0

        } else if (et_service_detail_respodent1.text.toString().isEmpty() && lay_et_service_detail_respodent.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_service_detail_respodent1,
                resources.getString(R.string.answer_this_quest)
            )
            value = 0
        } else if (et_service_provider_department1.text.toString().isEmpty() && lay_et_service_provider_department.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_service_provider_department1,
                resources.getString(R.string.plz_detail_srvic_provider)
            )
            value = 0

        } else if (rg_avail_any_scheme.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.avail_any_scheme)
            )
            value = 0
        } else if (et_details_service_avail1.text.toString().isEmpty() && lay_et_details_service_avail.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_details_service_avail1,
                resources.getString(R.string.please_prvd_service_detail)
            )
            value = 0

        } else if (et_darpartment_service_provide1.text.toString().isEmpty() && lay_darpartment_service_provide.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_darpartment_service_provide1,
                resources.getString(R.string.answer_this_questdd)
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
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_schemess.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_demographic.setBackgroundColor(resources.getColor(R.color.back))
        ll_other_detailss.setBackgroundColor(resources.getColor(R.color.back))

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

        lay_demographic.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileDemographicActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_schemess.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_other_detailss.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileSixActivity::class.java)
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
            horizontalScroll.smoothScrollBy(1500, 0)
        }, 100)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, IMProfileListActivity::class.java)
//        startActivity(intent)
//        finish()
    }


    fun hideShowView() {

        rg_availed_services_past.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_et_service_detail_respodent.visibility = View.VISIBLE
                lay_et_service_provider_department.visibility = View.VISIBLE
            } else {
                lay_et_service_detail_respodent.visibility = View.GONE
                lay_et_service_provider_department.visibility = View.GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }

        rg_avail_any_scheme.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_et_details_service_avail.visibility = View.VISIBLE
                lay_darpartment_service_provide.visibility = View.VISIBLE
            } else {
                lay_et_details_service_avail.visibility = View.GONE
                lay_darpartment_service_provide.visibility = View.GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }


    }
}