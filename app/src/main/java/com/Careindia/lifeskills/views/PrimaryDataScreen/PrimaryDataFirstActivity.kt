package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataFirstBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*
import android.widget.AdapterView
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.PrimaryDataEntity
import kotlinx.android.synthetic.main.activity_primary_data_first.et_crp_name


class PrimaryDataFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataFirstBinding
    var validate: Validate? = null
    val HHID = MutableLiveData<Int>()
    val IMID = MutableLiveData<Int>()
    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0
    var hhGUID=""
    var hhGUIDShow=""
    var imGUIDShow=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_first)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)
        )[PrimaryDataViewModel::class.java]
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        tv_title.text = resources.getString(R.string.primary_data)

        et_collection_date.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_collection_date
            )
        }
        initializeController()
        filldata()
        hideview()

        if(validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.trim().length>0) {
            showLiveData()
        }


    }
    fun filldata()
    {
        bindHHIDTable(resources.getString(R.string.select),spin_hh_id)



    }

    fun hideview()
    {


        spin_hh_id.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position>0)
                {
                    hhGUID=returnHH_GUID(position)
                    bindIMID(resources.getString(R.string.select),spin_im_id,hhGUID)
                    spin_im_id.setSelection(returnposIM_GUID(imGUIDShow,hhGUIDShow))

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        spin_im_id.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position>0)
                {
                   val IndGUID=returnIM_GUID(position,hhGUID)
                    fillDataFromIM(IndGUID)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
    }



    override fun initializeController() {
        applyClickOnView()
        bindData()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    primaryDataViewModel.saveAndUpdate(this,mstLookupViewModel,iLanguageID)
                    val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrimaryDataListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun bindData() {

        fillSpinner(
            resources.getString(R.string.select),
            spin_gender,
            1,
            iLanguageID
        )

        fillSpinner(
            resources.getString(R.string.select),
            spin_shg_jlg_cig,
            28,
            iLanguageID
        )

   /*     fillSpinner(
            resources.getString(R.string.select),
            spin_social_category,
            5,
            iLanguageID
        )
*/


        validate!!.fillradio(
            this,
            rg_cast_income,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

        validate!!.fillradio(
            this,
            rg_aadhar_card,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)
        validate!!.fillradio(
            this,
            rg_pan_card,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID)

    }


    private fun checkValidation(): Int {
        var value = 1
        if (spin_hh_id.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_hh_id,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_id)
            )
            value = 0
        } else if (spin_im_id.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_im_id,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.im_id)
            )
            value = 0
        } else if (et_community_name.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_community_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.community_name)
            )
            value = 0
        } else if (et_beneficiary_name.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_beneficiary_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_beneficiary)
            )
            value = 0
        } else if (et_age.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_age,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.age)
            )
            value = 0
        } else if (et_age.text.toString().length < 18 && et_age.text.toString().length > 65) {
            validate!!.CustomAlertEdit(
                this,
                et_age,
                resources.getString(R.string.please_enter_valid_age)
            )
            value = 0
        } else if (spin_gender.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_gender,
                resources.getString(R.string.please_select_gender)
            )
            value = 0
        } else if (et_contact_no.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_contact_no,
                resources.getString(R.string.please_enter_contact_number)
            )
            value = 0
        } else if (validate!!.checkmobileno(et_contact_no.text.toString()) == 0
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_contact_no,
                resources.getString(R.string.please_enter_valid_contact_number)
            )
            value = 0
        } else if (spin_shg_jlg_cig.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_shg_jlg_cig,
                resources.getString(R.string.please_select_member_shg_jlg_clg)
            )
            value = 0
        } /*else if (spin_social_category.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_social_category,
                resources.getString(R.string.please_select_social_category)
            )
            value = 0
        }*/ else if (validate!!.GetAnswerTypeRadioButtonID(rg_cast_income) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_caste_and_income_certificate)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_aadhar_card) == 0) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select_aadhar_card)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_pan_card) == 0) {
            validate!!.CustomAlert(this, resources.getString(R.string.please_select_pan_card))
            value = 0
        }
        return value
    }

    fun sendData() {
        primaryDataViewModel.collectDataPrimaryFirst(
            validate!!.GetAnswerTypeRadioButtonID(rg_aadhar_card),
            validate!!.GetAnswerTypeRadioButtonID(rg_pan_card)
        )
    }


    override fun onBackPressed() {
        val intent = Intent(this, PrimaryDataListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun fillSpinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        mstLookupViewModel!!.getMstLookup(flag, iLanguageID)
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


    fun bindHHIDTable(strValue: String, spin: Spinner) {

        //var sWardPanchayat = CareIndiaApplication.database?.hhProfileDao()?.getallHHdata()
        CareIndiaApplication.database!!.hhProfileDao().getallHHdata().observe(this, androidx.lifecycle.Observer {
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

    fun bindIMID(strValue: String, spin: Spinner,hhguid:String) {
        val zonedata= CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
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

    fun fillDataFromIM(IndGUID:String)
    {


        CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(IndGUID).observe(this, Observer {
            if (it != null && it.size>0) {
                it.get(0).IndGUID



            }
        })
    }


    fun returnHH_GUID(pos: Int?): String {
        var data: List<HouseholdProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.hhProfileDao().getHHdata()

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHGUID

            }
        }
        return id
    }

    fun returnIM_GUID(pos: Int?,hhguid:String): String {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndGUID.toString()

            }
        }
        return id
    }

    fun returnposHH_GUID(
        id: String?): Int {
        var data: List<HouseholdProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.hhProfileDao().getHHdata()
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id != null && id.length>0) {
                for (i in data.indices) {
                    if (id.equals(data.get(i).HHGUID))
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun returnposIM_GUID(id:String?,hhguid:String): Int {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id != null && id.length>0) {
                for (i in data.indices) {
                    if (id.equals(data.get(i).HHGUID))
                        pos = i + 1
                }
            }
        }
        return pos
    }


    fun showLiveData() {
        val primaryGuid = validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)
        if (primaryGuid != null) {
            primaryDataViewModel.getdatabyPDCGuid(primaryGuid).observe(this, Observer {
                if (it != null && it.size>0) {
                    //et_collection_date.setText(validate!!.returnStringValue(it.get(0).Dateform))
                   // et_crp_name.setText(validate!!.returnStringValue(it.get(0).Dateform))
                    //et_supervisor.setText(validate!!.returnStringValue(it.get(0).Dateform))
                    spin_hh_id.setSelection(returnposHH_GUID(it.get(0).HHGUID))
                    hhGUIDShow=it.get(0).HHGUID!!
                    imGUIDShow=it.get(0).HHGUID!!
                  //  et_community_name.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_beneficiary_name.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_age.setText(validate!!.returnStringValue(it.get(0).Age.toString()))
                    et_contact_no.setText(validate!!.returnStringValue(it.get(0).Contact))
                    spin_gender.setSelection(validate!!.returnpos(it.get(0).Gender,mstLookupViewModel,1,iLanguageID))
                    spin_shg_jlg_cig.setSelection(validate!!.returnpos(it.get(0).Group_Link,mstLookupViewModel,28,iLanguageID))
                    validate!!.SetAnswerTypeRadioButton(rg_cast_income,it.get(0).IncomeCertificate)
                    validate!!.SetAnswerTypeRadioButton(rg_aadhar_card,it.get(0).ValidAadhaar)
                    validate!!.SetAnswerTypeRadioButton(rg_pan_card,it.get(0).ValidPAN)


                }
            })
        }

    }


}