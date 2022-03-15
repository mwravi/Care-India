package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileOneBinding
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IMProfileOneActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileOneBinding
    var validate: Validate? = null
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel

    var formattedDate: String = ""
    var initials = ""
    var CommInitials = ""
    var ComInitials = ""
    var isUrban = 0

    // 1) "CIN" for CARE India
    //  2) Letter locality identifier- "W" for ward and "P" for panchayat.
    // 3) Character number for Ward or Panchayat code.
    // 4) Letter community identifier- "IM" for IndividualPrf.
    //  5) Character number for IndividualPrf code.
    //CINW001IM00001
    //CINP001IM00003
    var disCode = 0
    var zonCode = 0
    var iLanguageID = 0
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0
    var sWardPanchayat = ""
    var im_code_starting = ""
    var iShow = 0
    var HHCode = ""
    var Ward1 = 0
    var Panchayat1 = 0
    var flaghhorim   = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_one)
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

        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)

        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)

        var collectivemax = imProfileViewModel.getHHCount() + 1

//        et_last_code.setText(
//            getCharacterNumber(
//                collectivemax,
//                "00003"
//            )
//        )
        initializeController()
//        GlobalScope.launch {
//
//        }
    }

    override fun initializeController() {
        spin_districtname.isEnabled = false
        spin_zone.isEnabled = false
        spin_bbmp.isEnabled = false
        spin_panchayatname.isEnabled = false
        spin_hhid.isEnabled=false
        if (validate!!.RetriveSharepreferenceString(AppSP.IMClick).equals("HH")) {
            flaghhorim=1
        }else
        {
            flaghhorim=2
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }
        //apply click on view
        applyClickOnView()
        topLayClick()
        // fill spinner view
        fillSpinner()
        hideShowView()
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }
        else{
            // setLocation()
            if (validate!!.RetriveSharepreferenceString(AppSP.IMClick).equals("HH")) {
                HHCode = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!
            }
        }


        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_formfilngjgDate
            )

        }

        et_formfilngjgDate.setText(validate!!.currentdatetimeNew)

        et_last_code.addTextChangedListener(object : TextWatcher {
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
                if (s.length != 0) et_imprf_id.setText(
                    im_code_starting + getCharacterNumber(
                        validate!!.returnIntegerValue(et_last_code.text.toString()),
                        "00000"
                    )
                )
            }
        })



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

            }
        }


    }


    fun setLocation() {
        spin_districtname.setSelection(returnposDistrict(validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)))
        DistCode = validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)
        ZoneCode = validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter)
        if (ZoneCode > 0) {
            WardCode = validate!!.RetriveSharepreferenceInt(AppSP.WardFilter)
        } else {
            WardCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
        }
    }


    fun fillSpinner() {
        bindDistrict(resources.getString(R.string.select), spin_districtname)
        bindCommunity(resources.getString(R.string.select), spin_community_idv)

    }


    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.saveandUpdateCollectiveProfile(this, initials)
                    val intent = Intent(this, IMProfileDemographicActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                val intent = Intent(this, IMProfileListActivity::class.java)
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
//                    et_formfilngjgDate.setText(validate!!.returnStringValue(it.get(0).DateForm))
                    et_formfilngjgDate.setText(validate!!.addDays(it.get(0).DateForm!!.toInt()))
                    et_imprf_id.setText(validate!!.returnStringValue(it.get(0).IndvCode))

                    spin_districtname.setSelection(
                        returnposDistrict(
                            it.get(0).DistrictCode

                        )
                    )
                    HHCode = validate!!.returnStringValue(it.get(0).HHGUID)
                    DistCode = it.get(0).DistrictCode
                    ZoneCode = it.get(0).ZoneCode
                    WardCode = it.get(0).Panchayat_Ward!!
                    PanchayatCode = it.get(0).Panchayat_Ward!!


                    CommInitials = it.get(0).Initials
                    if (CommInitials.equals("C")) {
                        spin_community_idv.setSelection(1)
                        spin_community_idv.isEnabled = false
                    } else if (CommInitials.equals("D")) {
                        spin_community_idv.setSelection(2)
                        spin_community_idv.isEnabled = false
                    }
                    et_last_code.visibility = View.GONE
                    et_localityname.setText(validate!!.returnStringValue(it.get(0).Locality))
                    iShow = 1
                }
            })

    }

    fun sendData() {
        imProfileViewModel.collectiveProfileOneData(
            spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition) as String,
            et_imprf_id.text.toString()
        )
    }


    fun hideShowView() {

        spin_districtname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    var districtCode = position
                    isUrban = returnUrban_rural(districtCode, 10)
                    disCode = returnDistrictID(districtCode, 10)
                    if (districtCode > 0) {
                        isUrban = returnUrban_rural(districtCode, 10)
                        disCode = returnDistrictID(districtCode, 10)
                        if (isUrban == 1) {
                            lay_NameofZone.visibility = View.VISIBLE
                            lay_bbmpName.visibility = View.VISIBLE
                            sWardPanchayat = "W"
                            lay_panchayatName.visibility = View.GONE
                            spin_panchayatname.setSelection(0)
                            bindMstZone(resources.getString(R.string.select), spin_zone, disCode)
                            spin_zone.setSelection(returnposZone(ZoneCode, DistCode))
                        } else {
                            lay_NameofZone.visibility = View.GONE
                            spin_zone.setSelection(0)
                            spin_bbmp.setSelection(0)
                            lay_panchayatName.visibility = View.VISIBLE
                            lay_bbmpName.visibility = View.GONE
                            sWardPanchayat = "P"
                            bindPanchayat(
                                resources.getString(R.string.select),
                                spin_panchayatname,
                                disCode
                            )
                            spin_panchayatname.setSelection(
                                returnposPanchayat(
                                    PanchayatCode,
                                    disCode
                                )
                            )
                        }

                    } else {
                        lay_NameofZone.visibility = View.GONE
                        lay_bbmpName.visibility = View.GONE
                        spin_zone.setSelection(0)
                        spin_bbmp.setSelection(0)
                        lay_panchayatName.visibility = View.GONE
                        spin_panchayatname.setSelection(0)
                        sWardPanchayat = ""
                    }

                } else {

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }


//        imProfileViewModel.district.observe(this, Observer {
//            var districtCode = it
//            isUrban = returnUrban_rural(districtCode, 10)
//            disCode = returnDistrictID(districtCode, 10)
//            if (districtCode > 0) {
//                isUrban = returnUrban_rural(districtCode, 10)
//                disCode = returnDistrictID(districtCode, 10)
//                if (isUrban == 1) {
//                    lay_NameofZone.visibility = View.VISIBLE
//                    lay_bbmpName.visibility = View.VISIBLE
//                    sWardPanchayat = "W"
//                    lay_panchayatName.visibility = View.GONE
//                    spin_panchayatname.setSelection(0)
//                    bindMstZone(resources.getString(R.string.select), spin_zone, disCode)
//                    spin_zone.setSelection(returnposZone(ZoneCode, DistCode))
//                } else {
//                    lay_NameofZone.visibility = View.GONE
//                    spin_zone.setSelection(0)
//                    spin_bbmp.setSelection(0)
//                    lay_panchayatName.visibility = View.VISIBLE
//                    lay_bbmpName.visibility = View.GONE
//                    sWardPanchayat = "P"
//                    bindPanchayat(resources.getString(R.string.select), spin_panchayatname, disCode)
//                    spin_panchayatname.setSelection(returnposPanchayat(PanchayatCode, disCode))
//                }
//
//            } else {
//                lay_NameofZone.visibility = View.GONE
//                lay_bbmpName.visibility = View.GONE
//                spin_zone.setSelection(0)
//                spin_bbmp.setSelection(0)
//                lay_panchayatName.visibility = View.GONE
//                spin_panchayatname.setSelection(0)
//                sWardPanchayat = ""
//            }
//
//
//        })


        spin_zone.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                zonePos: Int, id: Long
            ) {
                if (zonePos > 0) {
                    zonCode = returnZoneID(zonePos, disCode)
                    bindMstWard(resources.getString(R.string.select), spin_bbmp, zonCode)
                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))


                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }


//        imProfileViewModel.zone.observe(this, Observer {
//            val zonePos = it
//            if (zonePos > 0) {
//                zonCode = returnZoneID(zonePos, disCode)
//                bindMstWard(resources.getString(R.string.select), spin_bbmp, zonCode)
//                spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))
//
//
//            }
//
//
//        })

//        imProfileViewModel.ward.observe(this, Observer {
//            val wardPos = it
//            if (wardPos > 0) {
//                Ward1 = returnWardID(
//                    wardPos,
//                    zonCode
//                )
//                if (WardCode == 0) {
//                    et_imprf_id.setText(getHHCode(Ward1))
//                    et_last_code.isEnabled = true
//                }
//                bindHHIDTable("Select", spin_hhid, zonCode, Ward1)
//                spin_hhid.setSelection(
//                    returnposHHcode(
//                        HHCode, DistCode, ZoneCode, WardCode, PanchayatCode
//                    )
//                )
//            }
//
//
//        })

        spin_bbmp.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                wardPos: Int, id: Long
            ) {
                if (wardPos > 0) {
                    Ward1 = returnWardID(
                        wardPos,
                        zonCode
                    )
                    if (WardCode == 0) {
                        et_imprf_id.setText(getHHCode(Ward1))
                        et_last_code.isEnabled = true
                    }
                    bindHHIDTable("Select", spin_hhid, zonCode, Ward1)
                    spin_hhid.setSelection(
                        returnposHHcode(
                            HHCode, DistCode, ZoneCode, WardCode, PanchayatCode
                        )
                    )

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }

//        imProfileViewModel.panchayat.observe(this, Observer {
//
//            val panchayatPos = it
//            if (panchayatPos > 0) {
//                Panchayat1 = returnPanchayatID(
//                    panchayatPos,
//                    disCode
//                )
//                if (PanchayatCode == 0) {
//                    et_imprf_id.setText(getHHCode(Panchayat1))
//                    et_last_code.isEnabled = true
//                }
//                bindHHIDTable1("Select", spin_hhid, Panchayat1)
//                spin_hhid.setSelection(
//                    returnposHHcode(
//                        HHCode, DistCode, ZoneCode, WardCode, PanchayatCode
//                    )
//                )
//            }
//
//
//        })


        spin_panchayatname.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                panchayatPos: Int, id: Long
            ) {
                if (panchayatPos > 0) {
                    Ward1 = returnPanchayatID(panchayatPos, disCode)
//                    Panchayat1 = returnPanchayatID(panchayatPos, disCode)
                    if (PanchayatCode == 0) {
                        et_imprf_id.setText(getHHCode(Ward1))
                        et_last_code.isEnabled = true
                    }
                    bindHHIDTable1("Select", spin_hhid, Ward1)
                    spin_hhid.setSelection(
                        returnposHHcode(
                            HHCode, DistCode, ZoneCode, WardCode, PanchayatCode
                        )
                    )


                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }


        spin_community_idv.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    if (position == 1) {
                        initials = "C"
                    } else if (position == 2) {
                        initials = "D"
                    }
                    if (CommInitials.equals("")) {
                        if (isUrban == 1) {
                            val hhmax = imProfileViewModel.getHHCount() + 1
                            et_last_code.setText(
                                getCharacterNumber(
                                    hhmax,
                                    "00000"
                                )
                            )
                            et_imprf_id.setText(getHHCode(Ward1) + et_last_code.text.toString())


//                        et_imprf_id.setText(getHHCode(Ward1))
                        } else {
                            val hhmax = imProfileViewModel.getHHCount() + 1
                            et_last_code.setText(
                                getCharacterNumber(
                                    hhmax,
                                    "00000"
                                )
                            )
                            et_imprf_id.setText(getHHCode(Ward1) + et_last_code.text.toString())

//                        et_imprf_id.setText(getHHCode(Panchayat1))
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }

        spin_hhid.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                            AppSP.IndividualProfileGUID
                        )!!.trim().length > 0
                    ) {

                    } else {
                        fillDataFromHH(spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition) as String)
                    }
                }


            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

    }

    fun fillDataFromHH(hhCode: String) {
        CareIndiaApplication.database!!.hhProfileDao().gethhdatabyhhCode(hhCode)
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_localityname.setText(it.get(0).Locality)
                    ComInitials = it.get(0).Initials!!
                    if (ComInitials.equals("C")) {
                        spin_community_idv.setSelection(1)
                        spin_community_idv.isEnabled = false
                    } else if (ComInitials.equals("D")) {
                        spin_community_idv.setSelection(2)
                        spin_community_idv.isEnabled = false
                    }

                }
            })

    }


    private fun checkValidation(): Int {
        var value = 1
        val iCount = imProfileViewModel.getIndividualID(et_imprf_id.text.toString())
        if (et_formfilngjgDate.text.toString().trim().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_formfilngjgDate,
                resources.getString(R.string.plz_enter_date)
            )
            value = 0
        } else if (et_crp_name.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_crp_name,
                resources.getString(R.string.plz_enter_name_crp)
            )
            value = 0
        } else if (et_sfc_name.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_sfc_name,
                resources.getString(R.string.plz_enter_name_superng_coordntr)
            )
            value = 0
        } else if (spin_districtname.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_distric_psy),
            )
            value = 0
        } else if (spin_zone.selectedItemPosition == 0 && lay_NameofZone.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone_psy),
            )
            value = 0
        } else if (spin_bbmp.selectedItemPosition == 0 && lay_bbmpName.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward_psy),
            )
            value = 0
        } else if (spin_panchayatname.selectedItemPosition == 0 && lay_panchayatName.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_panchayat_psy),
            )
            value = 0
        } else if (et_localityname.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_localityname,
                resources.getString(R.string.plz_enter_localty)
            )
            value = 0
        } else if (spin_hhid.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_hhid,
                resources.getString(R.string.plz_select_hhid)
            )
            value = 0

        } else if (spin_community_idv.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_community_idv,
                resources.getString(R.string.plz_select_community_dwcc)
            )
            value = 0
        } else if (et_last_code.text.toString()
                .isEmpty() && et_last_code.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_enter_idvid)
            )
            value = 0
        } else if (iCount > 0 && iShow == 0) {
            validate!!.CustomAlert(
                this,
                getString(R.string.indiexists)
            )
            value = 0

        }
        return value

    }


    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_schemess.setBackgroundColor(resources.getColor(R.color.back))
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

        if (!list.isNullOrEmpty()) {
            data = list.let { mstDistrictViewModel.getMstDistrict(StateCode, list) }
        } else {


            data = mstDistrictViewModel.getMstDistrict(StateCode)

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
        if (!list.isNullOrEmpty()) {
            data = list.let { mstDistrictViewModel.getMstDistrict(StateCode, list) }
        } else {


            data = mstDistrictViewModel.getMstDistrict(StateCode)

        }

        StateCode

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
        var data: List<MstZoneEntity>? = null
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

    fun returnposHHcode(
        strValue: String,
        DistCode: Int,
        ZoneCode: Int,
        WardCode: Int,
        PanchayatCode: Int
    ): Int {
        var posi = 0
        var hhcode: List<HouseholdProfileEntity>
        if (flaghhorim==1)
        {
            if (DistCode == 1) {
                hhcode = imProfileViewModel.gethhByGUIDProfileDataWard(validate!!.RetriveSharepreferenceString(AppSP.HHGUID),ZoneCode, WardCode)
            } else {
                hhcode = imProfileViewModel.gethhByGUIDProfileDataPanchayat(validate!!.RetriveSharepreferenceString(AppSP.HHGUID),PanchayatCode)
            }
        } else
        {
            if (DistCode == 1) {
                hhcode = imProfileViewModel.gethhProfileDataWard(ZoneCode, WardCode)
            } else {
                hhcode = imProfileViewModel.gethhProfileDataPanchayat(PanchayatCode)
            }
        }

        if (hhcode.isNotEmpty()) {

            for (i in 0 until hhcode.size) {

                if (strValue == hhcode.get(i).HHGUID) {
                    posi = i + 1
                }
            }

        }

        Log.i("MyTagRETURN", "$posi")
        return posi
    }

    fun bindHHIDTable(
        strValue: String,
        spin: Spinner,
        zoneCode: Int,
        ward: Int,

        ) {
        val adapter_category: ArrayAdapter<String?>

        var it: List<HouseholdProfileEntity>? = null
        if (flaghhorim==1)
        {
            it = imProfileViewModel.gethhByGUIDDataZone(validate!!.RetriveSharepreferenceString(AppSP.HHGUID),zoneCode, ward)
        } else
        {
            it = imProfileViewModel.gethhDataZone(zoneCode, ward)
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

        }else {
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

    fun bindHHIDTable1(
        strValue: String,
        spin: Spinner,
        panchayat: Int

    ) {
        val adapter_category: ArrayAdapter<String?>
        var it: List<HouseholdProfileEntity>? = null
        if (flaghhorim==1) {
            it = imProfileViewModel.gethhByGUIDDataPanchayat(validate!!.RetriveSharepreferenceString(AppSP.HHGUID), panchayat)
        } else
        {
            it = imProfileViewModel.gethhDataPanchayat(panchayat)
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

        }else {
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

//    fun bindDistrict(strValue: String, spin: Spinner) {
//        var it: List<MstDistrictEntity>? = null
//        var list: List<String>? = null
//        if (validate?.RetriveSharepreferenceString(AppSP.DistrictIn)!!.contains(",")) {
//            list = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
//                ?.split(",")?.let {
//                    listOf(
//                        *it
//                            .toTypedArray()
//                    )
//                }
//        } else {
//            list = null
//        }
//        if (list != null) {
//
//            if (!list.isNullOrEmpty()) {
//                it = list.let {
//                    mstDistrictViewModel.getMstDistrict(
//                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode), list
//                    )
//                }
//            } else {
//
//
//                it =
//                    mstDistrictViewModel.getMstDistrict(validate!!.RetriveSharepreferenceInt(AppSP.StateCode))
//
//            }
//            if (!it.isNullOrEmpty()) {
//                val iGen = it.size
//                val name = arrayOfNulls<String>(iGen + 1)
//                name[0] = strValue
//
//                for (i in 0 until it.size) {
//                    name[i + 1] = it.get(i).DistrictName
//                }
//                val adapter_category = ArrayAdapter<String>(
//                    this,
//                    R.layout.my_spinner_space_dashboard, name
//                )
//                adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
//                spin.adapter = adapter_category
//            }
//        }
//
//
//    }

    fun bindDistrict(strValue: String, spin: Spinner) {

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

        var it: List<MstDistrictEntity>? = null
        if (!list.isNullOrEmpty()) {
            it = list.let {
                CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    it
                )
            }
        } else {

            it = CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
            )

        }
        if (it != null) {
            if (it.isNotEmpty()) {
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

        if (!list.isNullOrEmpty()) {
            data = list.let {
                mstDistrictViewModel.getMstDistrict(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode), list
                )
            }
        } else {


            data =
                mstDistrictViewModel.getMstDistrict(validate!!.RetriveSharepreferenceInt(AppSP.StateCode))

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


    fun getHHCode(ward_or_panchayat_code: Int): String {
        var im_code = ""
        val cin = "CIN"
        val imProfileTxt = "IM"
        var character_number = imProfileViewModel.getHHCount() + 1

        im_code = cin + sWardPanchayat + getCharacterNumber(
            ward_or_panchayat_code,
            "000"
        ) + imProfileTxt + initials
        im_code_starting = im_code

        return im_code
    }

    fun getCharacterNumber(character_number: Int, pattern: String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

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


    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(0)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

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

    fun returnHH_GUID(pos: Int?, isUrban: Int?, Zone1: Int, Ward1: Int): String {
        var data: List<HouseholdProfileEntity>? = null
        if (flaghhorim==1)
        {
            if (isUrban == 1) {
                data = imProfileViewModel.gethhByGUIDProfileDataWard(validate!!.RetriveSharepreferenceString(AppSP.HHGUID),Zone1, Ward1)
            } else {
                data = imProfileViewModel.gethhByGUIDProfileDataPanchayat(validate!!.RetriveSharepreferenceString(AppSP.HHGUID),Ward1)
            }
        } else
        {
            if (isUrban == 1) {
                data = imProfileViewModel.gethhProfileDataWard(Zone1, Ward1)
            } else {
                data = imProfileViewModel.gethhProfileDataPanchayat(Ward1)
            }
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
    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }


    fun bindCommunity(strValue: String, spin: Spinner) {
        var it = ArrayList<String>()
        it.add("Community")
        it.add("DWCC")
        if (it != null) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i)
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }

    }


    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(0, 500)
        }, 100)
    }

    override fun onBackPressed() {
//        val intent = Intent(this, IMProfileListActivity::class.java)
//        startActivity(intent)
//        finish()
    }
}