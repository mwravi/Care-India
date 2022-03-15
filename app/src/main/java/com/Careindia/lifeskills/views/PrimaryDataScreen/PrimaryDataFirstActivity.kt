package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataFirstBinding
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.primary_data_tab.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class PrimaryDataFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrimaryDataFirstBinding
    var validate: Validate? = null
    val HHID = MutableLiveData<Int>()
    val IMID = MutableLiveData<Int>()
    lateinit var primaryDataViewModel: PrimaryDataViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var iLanguageID = 0
    var hhGUID = ""
    var hhGUIDShow = ""
    var imGUIDShow = ""
    var formattedDate = ""
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var disCode = 0
    var sWardPanchayat = ""
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var zonCode = 0
    var HHCode = ""
    var stateCode = 0
    var PanchayatCode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_first)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        stateCode = validate!!.RetriveSharepreferenceInt(AppSP.StateCode)

        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao, mstDistrictDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)
        )[PrimaryDataViewModel::class.java]
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)

        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)

        tv_title.text = resources.getString(R.string.primary_data)
        et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
        et_supervisor.setText(validate!!.RetriveSharepreferenceString(AppSP.FCID_Name))

        spin_districtname.isEnabled = false
        spin_zone.isEnabled = false
        spin_bbmp.isEnabled = false
        spin_panchayatname.isEnabled = false

        initializeController()

        filldata()
        hideview()
        bottomclick()

        if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PDCGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        } else {
            hhIndvspinner()
            fillDataFromIM(
                validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString()
            )
        }

    }

    fun hhIndvspinner() {

        val them =
            arrayOf<String>(validate!!.RetriveSharepreferenceString(AppSP.SubDashHHID).toString())

        var adaptertheme = ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them)
        adaptertheme.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin_hh_id?.adapter = adaptertheme

        val them1 =
            arrayOf<String>(validate!!.RetriveSharepreferenceString(AppSP.SubDashIMID).toString())

        var adaptertheme1 = ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them1)
        adaptertheme1.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin_im_id?.adapter = adaptertheme1

        spin_hh_id.isEnabled = false
        spin_im_id.isEnabled = false
    }

    fun filldata() {
        bindDistrict(resources.getString(R.string.select), spin_districtname)

        // bindHHIDTable(resources.getString(R.string.select), spin_hh_id)


    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_sixth.setBackgroundColor(resources.getColor(R.color.back))
        ll_seventh.setBackgroundColor(resources.getColor(R.color.back))
        /*  lay_first.setOnClickListener {

              val intent = Intent(this, CollectiveMeetingActivity::class.java)
              startActivity(intent)
              finish()
          }*/
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_fourth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataFourthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_sixth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSixthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_seventh.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID)!!.length > 0) {
                val intent = Intent(this, PrimaryDataSeventhActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun hideview() {

        spin_shg_jlg_cig.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val lookupCode = validate!!.returnLookupCode(
                        spin_shg_jlg_cig,
                        mstLookupViewModel,
                        28,
                        iLanguageID
                    )
                    if (lookupCode == 4) {
                        lay_collective_name.visibility = View.GONE

                    } else {
                        lay_collective_name.visibility = View.VISIBLE
                    }


                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
        spin_districtname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val isUrban = returnUrban_rural(position, stateCode)
                    disCode = returnDistrictID(position, stateCode)
                    if (isUrban == 1) {
                        lay_NameofZone.visibility = View.VISIBLE
                        lay_bbmpName.visibility = View.VISIBLE
                        sWardPanchayat = "W"
                        lay_panchayatName.visibility = View.GONE
                        spin_panchayatname.setSelection(0)
                        bindMstZone(resources.getString(R.string.select), spin_zone, disCode)
                        spin_zone.setSelection(returnposZone(ZoneCode, DistCode))
                    } else if (isUrban == 2) {
                        lay_NameofZone.visibility = View.GONE
                        spin_zone.setSelection(0)
                        lay_bbmpName.visibility = View.GONE
                        spin_bbmp.setSelection(0)
                        lay_panchayatName.visibility = View.VISIBLE
                        sWardPanchayat = "P"
                        bindPanchayat(
                            resources.getString(R.string.select),
                            spin_panchayatname,
                            disCode
                        )
                        spin_panchayatname.setSelection(returnposPanchayat(WardCode, disCode))
                    } else {
                        lay_NameofZone.visibility = View.GONE
                        lay_bbmpName.visibility = View.GONE
                        lay_panchayatName.visibility = View.GONE
                        spin_bbmp.setSelection(0)
                        spin_zone.setSelection(0)
                        spin_panchayatname.setSelection(0)

                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }


        spin_hh_id.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    var Ward1 = 0
                    val isUrban =
                        returnUrban_rural(spin_districtname.selectedItemPosition, stateCode)
                    if (isUrban == 1) {
                        Ward1 = returnWardID(
                            spin_bbmp.selectedItemPosition,
                            zonCode
                        )
                    } else {
                        Ward1 = returnPanchayatID(
                            spin_panchayatname.selectedItemPosition,
                            returnDistrictID(spin_districtname.selectedItemPosition, stateCode)
                        )
                    }

                    hhGUID = returnHH_GUID(position, isUrban, zonCode, Ward1)
                    bindIMID(resources.getString(R.string.select), spin_im_id, hhGUID)
                    spin_im_id.setSelection(returnposIM_GUID(imGUIDShow, hhGUIDShow))

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        spin_zone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    zonCode = returnZoneID(position, disCode)
                    bindMstWard(resources.getString(R.string.select), spin_bbmp, ZoneCode)
                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spin_bbmp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val Ward1 = returnWardID(
                        spin_bbmp.selectedItemPosition,
                        zonCode
                    )
                    if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                            AppSP.PDCGUID
                        )!!.trim().length > 0
                    ) {
                        val isUrban =
                            returnUrban_rural(spin_districtname.selectedItemPosition, stateCode)
                        bindHHIDTable("Select", spin_hh_id, zonCode, Ward1, isUrban, 0)
                        spin_hh_id.setSelection(
                            returnposHHcode(
                                hhGUIDShow, isUrban, ZoneCode, WardCode, 0
                            )
                        )
                    }
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spin_panchayatname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val district =
                        returnDistrictID(spin_districtname.selectedItemPosition, stateCode)
                    val PanchayatID = returnPanchayatID(
                        spin_panchayatname.selectedItemPosition,
                        district
                    )
                    if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                            AppSP.PDCGUID
                        )!!.trim().length > 0
                    ) {
                        val isUrban =
                            returnUrban_rural(spin_districtname.selectedItemPosition, stateCode)
                        bindHHIDTable("Select", spin_hh_id, 0, 0, isUrban, PanchayatID)
                        spin_hh_id.setSelection(
                            returnposHHcode(
                                hhGUIDShow, isUrban, 0, 0, PanchayatID
                            )
                        )
                    }
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

//        spin_im_id.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0) {
//                    val IndGUID = returnIM_GUID(position, hhGUID)
//                    if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
//                            AppSP.PDCGUID
//                        )!!.trim().length > 0
//                    ) {
//
//                    }else{
//                        fillDataFromIM(IndGUID)
//                    }
//
//                }
//
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // your code here
//            }
//        })
    }


    override fun initializeController() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }

        et_collection_date.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_collection_date
            )
        }
        et_collection_date.setText(validate!!.currentdatetimeNew)
        applyClickOnView()
        bindData()


        if (validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter) > 0) {
            DistCode = validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)
            spin_districtname.setSelection(
                returnposDistrict(DistCode)
            )
            if (validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter) > 0) {
                lay_NameofZone.visibility = View.VISIBLE

                ZoneCode = validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter)

                spin_zone.setSelection(returnposZone(ZoneCode, DistCode))

                if (validate!!.RetriveSharepreferenceInt(AppSP.WardFilter) > 0) {
                    lay_bbmpName.visibility = View.VISIBLE
                    WardCode = validate!!.RetriveSharepreferenceInt(AppSP.WardFilter)
                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))
                }
            } else if (validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter) > 0) {
                lay_panchayatName.visibility = View.VISIBLE
                PanchayatCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
                WardCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)

            }
        }

    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(0)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    primaryDataViewModel.saveAndUpdate(this, mstLookupViewModel, iLanguageID)
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
            R.id.img_back -> {
                val intent = Intent(this, PrimaryDataListActivity::class.java)
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


    }

    private fun checkValidation(): Int {
        var value = 1
        if (et_collection_date.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_collection_date,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.date)
            )
            value = 0
//        } else if (spin_hh_id.selectedItemPosition == 0) {
//            validate!!.CustomAlertSpinner(
//                this,
//                spin_hh_id,
//                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_id)
//            )
//            value = 0
//        } else if (spin_im_id.selectedItemPosition == 0) {
//            validate!!.CustomAlertSpinner(
//                this,
//                spin_im_id,
//                resources.getString(R.string.please_select) + " " + resources.getString(R.string.im_id)
//            )
//            value = 0
        } else if (et_community_name.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_community_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.community_name)
            )
            value = 0
//        } else if (et_beneficiary_name.text.toString().isEmpty()) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_beneficiary_name,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_beneficiary)
//            )
//            value = 0
//        } else if (et_age.text.toString().isEmpty()) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_age,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.age)
//            )
//            value = 0
//        } else if (validate!!.returnIntegerValue(et_age.text.toString()) < 18 && validate!!.returnIntegerValue(
//                et_age.text.toString()
//            ) > 65
//        ) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_age,
//                resources.getString(R.string.please_enter_valid_age)
//            )
//            value = 0
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
        } else if (validate!!.checkmobileno(et_contact_no.text.toString()) == 0) {
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
        } else if (et_collective_name.text.toString()
                .isEmpty() && lay_collective_name.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_collective_name,
                resources.getString(R.string.plz_collective_name)
            )
            value = 0
        }
        return value
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

        CareIndiaApplication.database!!.hhProfileDao().getallHHdata()
            .observe(this, androidx.lifecycle.Observer {
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

    fun bindHHIDTable(
        strValue: String,
        spin: Spinner,
        zoneCode: Int,
        ward: Int,
        isUrban: Int,
        Panchayat: Int

    ) {
        val adapter_category: ArrayAdapter<String?>
        var it: List<HouseholdProfileEntity>? = null
        if (isUrban == 1) {
            it = CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(zoneCode, ward)
        } else {
            it = CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(Panchayat)
        }

        if (it.isNotEmpty()) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i).HHCode
            }
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category

        } else {
            val sValue = arrayOfNulls<String>(it.size + 1)
            sValue[0] = strValue
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, sValue
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }

    }

    fun bindIMID(strValue: String, spin: Spinner, hhguid: String) {

        var zonedata: List<IndividualProfileEntity>? = null

        if (validate!!.RetriveSharepreferenceString(AppSP.PDCGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PDCGUID
            )!!.trim().length > 0
        ) {
            zonedata =
                CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
        } else {
            zonedata =
                CareIndiaApplication.database!!.primaryDataDao().findIdvPrfdata(hhguid)
        }

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

    fun fillDataFromIM(IndGUID: String) {
        validate!!.SaveSharepreferenceString(AppSP.EdpId, IndGUID)
        CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(IndGUID)
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_beneficiary_name.setText(it.get(0).Name)

                    spin_gender.setSelection(
                        validate!!.returnpos(
                            it.get(0).Gender,
                            mstLookupViewModel,
                            1,
                            iLanguageID
                        )
                    )
                    et_contact_no.setText(it.get(0).Contact)
                    et_community_name.setText(validate!!.returnStringValue(it.get(0).Locality))
//                    et_age.setText(it.get(0).Age.toString())
                    val date1 = validate!!.addDays(it.get(0).DateForm!!.toInt())
                    val date2=validate!!.currentdatetimeNew
                    val first: org.joda.time.LocalDate = org.joda.time.LocalDate.parse(validate!!.changeDateFormat(date1))
                    val last: org.joda.time.LocalDate = org.joda.time.LocalDate.parse(validate!!.changeDateFormat(date2))

                    val age = it.get(0).Age!! + validate!!.getDiffYears(first.toDate(), last.toDate())
                    et_age.setText(age.toString())


                    /*    validate!!.SetAnswerTypeRadioButton(rg_cast_income,it.get(0).CasteCertificate)
                        validate!!.SetAnswerTypeRadioButton(rg_aadhar_card,it.get(0).Aadhaar)
                        validate!!.SetAnswerTypeRadioButton(rg_pan_card,it.get(0).PAN)*/


                }
            })
        /* CareIndiaApplication.database!!.hhProfileDao().gethhdatabyGuid(hhGUID)
             .observe(this, Observer {
                 if (it != null && it.size > 0) {
                     et_community_name.setText(it.get(0).Locality)
                 }
             })*/


    }
//  householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {

    fun returnHH_GUID(
        pos: Int?, isUrban: Int,
        ZoneCode: Int,
        WardCode: Int
    ): String {
        var data: List<HouseholdProfileEntity>? = null

        if (isUrban == 1) {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
        } else if (isUrban == 2) {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(WardCode)
        }

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHGUID

            }
        }
        return id
    }

    fun returnIM_GUID(pos: Int?, hhguid: String): String {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndGUID

            }
        }
        return id
    }

    fun returnposHH_GUID(
        id: String?
    ): Int {
        var data: List<HouseholdProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.hhProfileDao().getHHdata()
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id != null && id.length > 0) {
                for (i in data.indices) {
                    if (id.equals(data.get(i).HHGUID))
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun returnposIM_GUID(id: String?, hhguid: String): Int {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id != null && id.length > 0) {
                for (i in data.indices) {
                    if (id.equals(data.get(i).IndGUID))
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
                if (it != null && it.size > 0) {

                    if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                        btn_bottom.visibility = View.GONE
                    } else {
                        btn_bottom.visibility = View.VISIBLE
                    }

                     if (it.get(0).IsEdited == 0) {
                         if (it.get(0).Contact.toString().length == 0) {
                             et_contact_no.setText(validate!!.returnStringValue(it.get(0).Contact))
                         } else {
                             var lastthree =
                                 it.get(0).Contact.toString()
                                     .substring(it.get(0).Contact.toString().length - 3)
                             var getcontact = "XXXXXXX" + lastthree

                             et_contact_no.setText(getcontact)

                         }

                    } else {
                         et_contact_no.setText(validate!!.returnStringValue(it.get(0).Contact))

                    }


                    spin_districtname.setSelection(returnposDistrict(it.get(0).DistrictCode))
                    DistCode = it.get(0).DistrictCode!!
                    ZoneCode = it.get(0).ZoneCode!!
                    WardCode = it.get(0).Panchayat_Ward!!
                    //  spin_hh_id.setSelection(returnposHH_GUID(it.get(0).HHGUID))

                    HHCode = validate!!.returnStringValue(it.get(0).HHGUID)
                    hhGUIDShow = it.get(0).HHGUID!!
                    imGUIDShow = it.get(0).IMGUID!!

//                    val isUrban =
//                        returnUrban_rural(1, it.get(0).DistrictCode!!)
//                    if (isUrban == 1) {
//                        val Ward1 = returnWardID(
//                            spin_bbmp.selectedItemPosition,
//                            zonCode
//                        )
//                        bindHHIDTable("Select", spin_hh_id, zonCode, Ward1, isUrban, 0)
//                        spin_hh_id.setSelection(
//                            returnposHHcode(
//                                hhGUIDShow, isUrban, ZoneCode, WardCode, 0
//                            )
//                        )
//
//                        hhGUID = returnHH_GUID(0, isUrban, zonCode, Ward1)
//                        bindIMID(resources.getString(R.string.select), spin_im_id, hhGUID)
//                        spin_im_id.setSelection(returnposIM_GUID(imGUIDShow, hhGUIDShow))
//
//                    } else {
//                        val PanchayatID = returnPanchayatID(
//                            spin_panchayatname.selectedItemPosition,
//                            isUrban
//                        )
//                        bindHHIDTable("Select", spin_hh_id, 0, 0, isUrban, PanchayatID)
//                        spin_hh_id.setSelection(
//                            returnposHHcode(
//                                hhGUIDShow, isUrban, 0, 0, PanchayatID
//                            )
//                        )
//                    }
//


                    spin_hh_id.isEnabled = false
                    spin_im_id.isEnabled = false

                    et_collection_date.setText(validate!!.addDays(it.get(0).CollectionDate!!.toInt()))
                    et_community_name.setText(validate!!.returnStringValue(it.get(0).CommunityName))
                    et_collective_name.setText(validate!!.returnStringValue(it.get(0).Name_Collective))


                    et_beneficiary_name.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_age.setText(validate!!.returnStringValue(it.get(0).Age.toString()))




                    spin_gender.setSelection(
                        validate!!.returnpos(
                            it.get(0).Gender,
                            mstLookupViewModel,
                            1,
                            iLanguageID
                        )
                    )
                    spin_shg_jlg_cig.setSelection(
                        validate!!.returnpos(
                            it.get(0).Group_Link,
                            mstLookupViewModel,
                            28,
                            iLanguageID
                        )
                    )


                }
            })
        }

    }

    fun bindDistrict(strValue: String, spin: Spinner) {
        var it: List<MstDistrictEntity>? = null
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.DistrictIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        it = if (!list.isNullOrEmpty()) {
            list.let {
                mstDistrictViewModel.getMstDistrict(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode), list
                )
            }
        } else {


            mstDistrictViewModel.getMstDistrict(validate!!.RetriveSharepreferenceInt(AppSP.StateCode))

        }
        if (!it.isNullOrEmpty()) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i).DistrictName
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }

    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.DistrictIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        data = if (!list.isNullOrEmpty()) {
            list.let {
                mstDistrictViewModel.getMstDistrict(StateCode, list)
            }
        } else {


            mstDistrictViewModel.getMstDistrict(StateCode)

        }


        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).Urban_rural!!

            }
        }
        return id
    }

    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.DistrictIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        data = if (!list.isNullOrEmpty()) {
            list.let {
                mstDistrictViewModel.getMstDistrict(StateCode, list)
            }
        } else {


            mstDistrictViewModel.getMstDistrict(StateCode)

        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode

            }
        }
        return id
    }

    fun returnZoneID(pos: Int?, distCode: Int): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.ZoneIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.ZoneIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        var data: List<MstZoneEntity>? = null
        if (!list.isNullOrEmpty()) {
            data =
                list.let { mstZoneViewModel.getMstZone(distCode, it) }
        } else {
            data = mstZoneViewModel.getMstZone(distCode)

        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).ZoneCode

            }
        }
        return id
    }

    fun returnWardID(pos: Int?, zoneCode: Int): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }
        var data: List<MstPanchayat_WardEntity>? = null
        data = if (!list.isNullOrEmpty()) {
            mstPanchayatWardViewModel.getMstWard(zoneCode, list)
        } else {


            mstPanchayatWardViewModel.getMstWard(zoneCode)

        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).pwcode

            }
        }
        return id
    }

    fun returnPanchayatID(pos: Int?, disCode: Int): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        var data: List<MstPanchayat_WardEntity>? = null
        data = if (!list.isNullOrEmpty()) {
            mstPanchayatWardViewModel.getMstPanchayat(disCode, list)
        } else {
            mstPanchayatWardViewModel.getMstPanchayat(disCode)

        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).pwcode

            }
        }
        return id
    }

    fun bindMstZone(strValue: String, spin: Spinner, districtCode: Int) {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.ZoneIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.ZoneIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        var zonedata: List<MstZoneEntity>? = null
        if (!list.isNullOrEmpty()) {
            zonedata =
                list.let { mstZoneViewModel.getMstZone(districtCode, it) }
        } else {
            zonedata = mstZoneViewModel.getMstZone(districtCode)

        }
        if (zonedata != null) {
            val iGen = zonedata.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until zonedata.size) {
                name[i + 1] = zonedata.get(i).ZoneName
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }

    fun returnposDistrict(
        id: Int?
    ): Int {
        var data: List<MstDistrictEntity>? = null
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.DistrictIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }
        data = if (!list.isNullOrEmpty()) {
            list.let {
                mstDistrictViewModel.getMstDistrict(stateCode, list)
            }
        } else {


            mstDistrictViewModel.getMstDistrict(stateCode)

        }
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).DistrictCode)
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun returnposZone(
        id: Int?, distCode: Int
    ): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.ZoneIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.ZoneIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }


        var data: List<MstZoneEntity>? = null
        if (!list.isNullOrEmpty()) {
            data =
                list.let { mstZoneViewModel.getMstZone(distCode, it) }
        } else {
            data = mstZoneViewModel.getMstZone(distCode)

        }
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).ZoneCode)
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun returnposWard(id: Int?, zoneCode: Int): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        var data: List<MstPanchayat_WardEntity>? = null
        data = if (!list.isNullOrEmpty()) {
            mstPanchayatWardViewModel.getMstWard(zoneCode, list)
        } else {


            mstPanchayatWardViewModel.getMstWard(zoneCode)

        }
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).pwcode)
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun bindPanchayat(strValue: String, spin: Spinner, districtCode: Int) {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }
        var mstPanchayat: List<MstPanchayat_WardEntity>? = null
        mstPanchayat = if (!list.isNullOrEmpty()) {
            mstPanchayatWardViewModel.getMstPanchayat(disCode, list)
        } else {
            mstPanchayatWardViewModel.getMstPanchayat(disCode)

        }
        if (mstPanchayat != null) {
            val iGen = mstPanchayat.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until mstPanchayat.size) {
                name[i + 1] = mstPanchayat.get(i).PWName
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }

    fun returnposPanchayat(id: Int?, distCode: Int): Int {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        var data: List<MstPanchayat_WardEntity>? = null
        data = if (!list.isNullOrEmpty()) {
            mstPanchayatWardViewModel.getMstPanchayat(disCode, list)
        } else {
            mstPanchayatWardViewModel.getMstPanchayat(disCode)

        }
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).pwcode)
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun bindMstWard(strValue: String, spin: Spinner, zoneCode: Int) {
        var list: List<String>? = null
        if (validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)!!.contains(",")) {
            list = validate?.RetriveSharepreferenceString(AppSP.PWCodeIn)
                ?.split(",")?.let {
                    listOf(
                        *it
                            .toTypedArray()
                    )
                }
        } else {
            list = null
        }

        var mstWard: List<MstPanchayat_WardEntity>? = null
        mstWard = if (!list.isNullOrEmpty()) {
            mstPanchayatWardViewModel.getMstWard(zoneCode, list)
        } else {


            mstPanchayatWardViewModel.getMstWard(zoneCode)

        }
        if (mstWard != null) {
            val iGen = mstWard.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until mstWard.size) {
                name[i + 1] = mstWard.get(i).PWName
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }

    fun returnposHHcode(
        strValue: String,
        isUrban: Int,
        ZoneCode: Int,
        WardCode: Int,
        PanchayatCode: Int
    ): Int {
        var posi = 0
        var hhcode: List<HouseholdProfileEntity>? = null

        if (isUrban == 1) {
            hhcode =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
        } else {
            hhcode =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(PanchayatCode)
        }
        if (hhcode != null) {

            for (i in hhcode.indices) {

                if (strValue.equals(hhcode.get(i).HHGUID)) {
                    posi = i + 1
                }
            }

        }

        Log.i("MyTagRETURN", "$posi")
        return posi
    }


    override fun onBackPressed() {
//        val intent = Intent(this, PrimaryDataListActivity::class.java)
//        startActivity(intent)
//        finish()
    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(0, 500)
        }, 100)
    }

}