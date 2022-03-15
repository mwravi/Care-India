package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileDemographicBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_demographic.*
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileDemographicActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileDemographicBinding
    var validate: Validate? = null
    var iLanguageID: Int = 0
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_demographic)
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


    }


    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        topLayClick()
        // fill spinnerview
        fillSpinnerView()
        fillRadio()
        hideShowView()

        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }


    }


    fun fillSpinnerView() {

        fillSpinner("Select", spin_sexrepo, 1, iLanguageID)
        fillSpinner("Select", spin_casterespo, 5, iLanguageID)
        fillSpinner("Select", spin_marital, 6, iLanguageID)
        fillSpinner(
            resources.getString(R.string.select),
            spin_state,
            7,
            iLanguageID
        )
        fillSpinner("Select", spin_education, 8, iLanguageID)
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
                    imProfileViewModel.updateProfileDemographicData(this)
                    var intent = Intent(this, IMProfileTwoActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                var intent = Intent(this, IMProfileOneActivity::class.java)
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


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                        btn_bottom.visibility = View.GONE
                    } else {
                        btn_bottom.visibility = View.VISIBLE
                    }

                    if (it.get(0).IsEdited == 0) {
                        if (it.get(0).Contact.toString().length == 0) {
                            et_contactnorespo.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
                        } else {
                            var lastthree =
                                it.get(0).Contact.toString()
                                    .substring(it.get(0).Contact.toString().length - 3)
                            var getcontact = "XXXXXXX" + lastthree
                            et_contactnorespo.setText(getcontact)
                        }

                        if (it.get(0).Alt_contact.toString().length == 0) {
                            et_alter_contactnorespo.setText(validate!!.returnStringValue(it.get(0).Alt_contact.toString()))
                        } else {
                            var lastthree1 =
                                it.get(0).Alt_contact.toString()
                                    .substring(it.get(0).Alt_contact.toString().length - 3)
                            var getAltcontact = "XXXXXXX" + lastthree1
                            et_alter_contactnorespo.setText(getAltcontact)
                        }
                    } else {
                        et_contactnorespo.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
                        et_alter_contactnorespo.setText(validate!!.returnStringValue(it.get(0).Alt_contact.toString()))
                    }

                    et_namerespo.setText(validate!!.returnStringValue(it.get(0).Name))

                    setDefBlank(et_long_stay, it.get(0).ResidingSince!!)
                    et_specify_state.setText(validate!!.returnStringValue(it.get(0).State_Other))

                    spin_sexrepo.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Gender.toString()),
                            1
                        )
                    )

                    spin_casterespo.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Caste.toString()),
                            5
                        )
                    )
                    spin_marital.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).MaritalStatus.toString()),
                            6
                        )
                    )
                    et_agerespo.setText(validate!!.returnStringValue(it.get(0).Age.toString()))






                    spin_state.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).StateID.toString()), 7
                        )
                    )

                    spin_education.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Education.toString()),
                            8
                        )
                    )
                    validate!!.SetAnswerTypeRadioButton(rg_access_sphone, it.get(0).Smartphone)
                    validate!!.SetAnswerTypeRadioButton(rg_acess_mob_data, it.get(0).MobileData)
                    validate!!.SetAnswerTypeRadioButton(rg_can_read, it.get(0).Read_Write)

                }
            })

    }

    fun fillRadio() {

        validate!!.fillradio(
            this,
            rg_can_read,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradio(
            this,
            rg_access_sphone,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
        validate!!.fillradio(
            this,
            rg_acess_mob_data,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

    }


    fun hideShowView() {

        imProfileViewModel.State.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_state,
                mstLookupViewModel,
                7,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_et_specify_state.visibility = View.VISIBLE
            } else {
                lay_et_specify_state.visibility = View.GONE
                et_specify_state.setText("")
            }

        })

        rg_can_read.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_spin_education.visibility = View.VISIBLE

            } else {
                lay_spin_education.visibility = View.GONE

            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }
        rg_access_sphone.setOnCheckedChangeListener { radioGroup, i ->

            validate!!.hideSoftKeyboard(this, radioGroup)
        }
        rg_acess_mob_data.setOnCheckedChangeListener { radioGroup, i ->

            validate!!.hideSoftKeyboard(this, radioGroup)
        }

    }


    private fun checkValidation(): Int {
        var value = 1

        if (et_namerespo.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_namerespo,
                resources.getString(R.string.plz_enter_name_respondent)
            )
            value = 0

        } else if (spin_sexrepo.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_sexrepo,
                resources.getString(R.string.plz_enter_sex_respondent)
            )
            value = 0
        } else if (et_agerespo.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_agerespo,
                resources.getString(R.string.plz_enter_age_respondent)
            )
            value = 0
        } else if (Integer.parseInt(et_agerespo.text.toString()) < 18 || Integer.parseInt(
                et_agerespo.text.toString()
            ) > 65
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_agerespo,
                resources.getString(R.string.plz_enter_age_value)
            )
            value = 0

        } else if (spin_casterespo.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_casterespo,
                resources.getString(R.string.plz_enter_caste_respo)
            )
            value = 0
        } else if (spin_marital.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_marital,
                resources.getString(R.string.plz_enter_marital_respo)
            )
            value = 0

//        }else if(et_contactnorespo.text.toString().contains("X")){
//
//            value = 1
        } else if (et_contactnorespo.text.toString().isEmpty() &&
            validate!!.checkmobileno(et_contactnorespo.text.toString()) == 0
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.plz_enter_contct_no_respo)
            )
            value = 0
        } else if (validate!!.checkmobileno(et_contactnorespo.text.toString()) == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.please_enter_valid_contact_number)
            )
            value = 0
        } else if (et_contactnorespo.text.toString().trim().length < 10) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.plz_enter_contct_no_proper)
            )
            value = 0
        } else if (!et_alter_contactnorespo.text.toString().isNullOrEmpty() &&
            validate!!.checkmobileno(et_alter_contactnorespo.text.toString()) == 0
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_alter_contactnorespo,
                resources.getString(R.string.plz_enter_alter_contact_no)
            )
            value = 0
        } else if (!et_alter_contactnorespo.text.toString()
                .isNullOrEmpty() && et_alter_contactnorespo.text.toString().trim().length < 10
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_alter_contactnorespo,
                resources.getString(R.string.plz_enter_alter_contact_no)
            )
            value = 0
        } else if (spin_state.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_state,
                resources.getString(R.string.plz_select_state)
            )
            value = 0

        } else if (et_specify_state.text.toString().length == 0 && lay_et_specify_state.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_state,
                resources.getString(R.string.plz_enter_state)
            )
            value = 0
        } else if (et_long_stay.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_long_stay,
                resources.getString(R.string.plz_entr_sty_long)
            )
            value = 0

        } else if (Integer.parseInt(et_long_stay.text.toString()) > Integer.parseInt(et_agerespo.text.toString())) {
            validate!!.CustomAlertEdit(
                this,
                et_long_stay,
                resources.getString(R.string.plz_entr_less_input) + " " + (Integer.parseInt(
                    et_agerespo.text.toString()
                ))
            )
            value = 0
        } else if (rg_can_read.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_read_write)
            )
            value = 0
        } else if (spin_education.selectedItemPosition == 0 && lay_spin_education.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_education,
                resources.getString(R.string.plz_select_education)
            )
            value = 0
        } else if (rg_access_sphone.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_smartphone)
            )
            value = 0
        } else if (rg_acess_mob_data.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata)
            )
            value = 0

        }
        return value

    }


    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

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

    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_schemess.setBackgroundColor(resources.getColor(R.color.back))
        lay_demographic.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
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
            horizontalScroll.smoothScrollBy(400, 0)
        }, 100)
    }

    override fun onBackPressed() {
//        super.onBackPressed()

    }

}