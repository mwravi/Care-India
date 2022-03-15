package com.careindia.lifeskills.views.psychometricscreen

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPsychometricFirstBinding
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_psychometric_first.*
import kotlinx.android.synthetic.main.bottom_nav_psycho_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PsychometricFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPsychometricFirstBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var psychometricViewModel: PsychometricViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel

    var iLanguageID: Int = 0
    var hhCodess: Any? = null
    var formattedDate: String = ""
    var CodeHh = ""
    var CodeIm = ""
    var IdvCode = ""
    var HHCode = ""

    var PrimaryOccu: Int = 0
    var SecondryOccu: Int = 0

    var isUrban = 0
    var disCode = 0
    var zonCode = 0
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0
    var sWardPanchayat = ""
    var im_code_starting = ""
    var iShow = 0
    var Ward1 = 0
    var Panchayat1 = 0
    var stateCode = 0

    var hhGUID = ""
    var hhGUIDShow = ""
    var imGUIDShow = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_first)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.psychometric)

        stateCode = validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val psychometricRepository = PsychometricRepository(psychometricdao!!, mstDistrictDao)
        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(psychometricRepository)
        )[PsychometricViewModel::class.java]

        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)

        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)

        binding.psychometricViewModel = psychometricViewModel
        binding.lifecycleOwner = this
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)


        spin_districtname_psy.isEnabled = false
        spin_zone.isEnabled = false
        spin_bbmp.isEnabled = false
        spin_panchayatname.isEnabled = false

        initializeController()
    }

    fun hhIndvspinner() {

        val them =
            arrayOf<String>(validate!!.RetriveSharepreferenceString(AppSP.SubDashHHID).toString())

        var adaptertheme = ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them)
        adaptertheme.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin_hhid?.adapter = adaptertheme

        val them1 =
            arrayOf<String>(validate!!.RetriveSharepreferenceString(AppSP.SubDashIMID).toString())

        var adaptertheme1 = ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them1)
        adaptertheme1.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin_imid?.adapter = adaptertheme1

        spin_hhid.isEnabled = false
        spin_imid.isEnabled = false
    }

    override fun initializeController() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }
        //apply click on view
        applyClickOnView()
        //fillSpinner
        topLayClick()
        fillSpinnerView()
        hideShowView()

        if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PATGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        } else {
            hhIndvspinner()
            bindPsychometricData(
                validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString()
            )
            setLocation()
        }

        et_date.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_date
            )

        }
        et_date.setText(validate!!.currentdatetimeNew)

//        if (validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter) > 0) {
//            DistCode = validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)
////            spin_districtname_psy.setSelection(
////                returnposDistrict(DistCode)
////            )
//            if (validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter) > 0) {
//                lay_NameofZone.visibility = View.VISIBLE
//
//                ZoneCode = validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter)
//
////                spin_zone.setSelection(returnposZone(ZoneCode, DistCode))
//
//                if (validate!!.RetriveSharepreferenceInt(AppSP.WardFilter) > 0) {
//                    lay_bbmpName.visibility = View.VISIBLE
//                    WardCode = validate!!.RetriveSharepreferenceInt(AppSP.WardFilter)
////                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))
//                }
//            } else if (validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter) > 0) {
//                lay_panchayatName.visibility = View.VISIBLE
//                PanchayatCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
//                WardCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
//
////                spin_panchayatname.setSelection(returnposPanchayat(WardCode, disCode))
//
//            }
//        }


    }

    fun setLocation() {
        spin_districtname_psy.setSelection(
            returnposDistrict(
                validate!!.RetriveSharepreferenceInt(
                    AppSP.DistrictFilter
                )
            )
        )
        DistCode = validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)
        ZoneCode = validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter)
        if (ZoneCode > 0) {
            WardCode = validate!!.RetriveSharepreferenceInt(AppSP.WardFilter)
        } else {
            WardCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
        }


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
                    psychometricViewModel.saveandUpdatePsychometricData(this)
                    val intent = Intent(this, PsychometricSecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {

                val intent = Intent(this, PsychometricListActivity::class.java)
                startActivity(intent)
                finish()

            }
            R.id.img_back -> {
                val intent = Intent(this, PsychometricListActivity::class.java)
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


    fun hideShowView() {

        spin_districtname_psy.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
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

        spin_hhid.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    var Ward1 = 0
                    val isUrban =
                        returnUrban_rural(spin_districtname_psy.selectedItemPosition, stateCode)
                    if (isUrban == 1) {
                        Ward1 = returnWardID(
                            spin_bbmp.selectedItemPosition,
                            zonCode
                        )
                        CodeHh = returnHhPrfID(position, isUrban, ZoneCode, Ward1, 0) as String
                    } else {
                        Ward1 = returnPanchayatID(
                            spin_panchayatname.selectedItemPosition,
                            returnDistrictID(spin_districtname_psy.selectedItemPosition, stateCode)
                        )

                        CodeHh = returnHhPrfID(position, isUrban, 0, 0, Ward1) as String
                    }

                    hhGUID = returnHH_GUID(position, isUrban, zonCode, Ward1)
                    bindIMID(resources.getString(R.string.select), spin_imid, hhGUID)
                    spin_imid.setSelection(returnposIM_GUID(imGUIDShow, hhGUIDShow))

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

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
                    if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) != null && validate!!.RetriveSharepreferenceString(
                            AppSP.PATGUID
                        )!!.trim().length > 0
                    ) {
                        val isUrban =
                            returnUrban_rural(spin_districtname_psy.selectedItemPosition, stateCode)
                        bindHHIDTable("Select", spin_hhid, zonCode, Ward1, isUrban, 0)
                        spin_hhid.setSelection(
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
                        returnDistrictID(spin_districtname_psy.selectedItemPosition, stateCode)
                    val PanchayatID = returnPanchayatID(
                        spin_panchayatname.selectedItemPosition,
                        district
                    )
                    if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) != null && validate!!.RetriveSharepreferenceString(
                            AppSP.PATGUID
                        )!!.trim().length > 0
                    ) {
                        val isUrban =
                            returnUrban_rural(spin_districtname_psy.selectedItemPosition, stateCode)
                        bindHHIDTable("Select", spin_hhid, 0, 0, isUrban, PanchayatID)
                        spin_hhid.setSelection(
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


//        spin_imid.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0) {
//                    var CodeIm = returnIMCodeID(position, CodeHh) as String
//
//                    if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) != null && validate!!.RetriveSharepreferenceString(
//                            AppSP.PATGUID
//                        )!!.trim().length > 0
//                    ) {
//
//                    } else {
//                        bindPsychometricData(CodeIm)
//                    }
//
//                }
//
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // your code here
//            }
//        }

        psychometricViewModel.PrimaryOccup.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_primary_income,
                mstLookupViewModel,
                13,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_et_specif_source_income.visibility = View.VISIBLE
            } else {
                lay_et_specif_source_income.visibility = View.GONE
                et_specif_primary_occu.setText("")
            }

        })


        psychometricViewModel.SecSourceIncom.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_secondry_income,
                mstLookupViewModel,
                14,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_et_specify_source_secondary_income.visibility = View.VISIBLE
            } else {
                lay_et_specify_source_secondary_income.visibility = View.GONE
                et_specify_source_secondary_income.setText("")
            }

        })


    }

    fun showLiveData() {
        val patGuid = validate!!.RetriveSharepreferenceString(AppSP.PATGUID)
        psychometricViewModel.getPsychometricbyGuid(validate!!.returnStringValue(patGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                        btn_bottom.visibility = View.GONE
                    } else {
                        btn_bottom.visibility = View.VISIBLE
                    }

                     if (it.get(0).IsEdited == 0) {
                         if (it.get(0).Contact_number.toString().length == 0) {
                             et_contactnumber.setText(validate!!.returnStringValue(it.get(0).Contact_number))
                         } else {
                             var lastthree =
                                 it.get(0).Contact_number.toString()
                                     .substring(it.get(0).Contact_number.toString().length - 3)
                             var getcontact = "XXXXXXX" + lastthree

                             et_contactnumber.setText(getcontact)

                         }
                    } else {
                         et_contactnumber.setText(validate!!.returnStringValue(it.get(0).Contact_number))

                    }

//                    val them = arrayOf<String>(it.get(0).HHID.toString())
//
//                    var adaptertheme = ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them)
//                    adaptertheme.setDropDownViewResource(R.layout.my_spinner_dashboard)
//                    spin_hhid?.adapter = adaptertheme
//
//                    val them1 = arrayOf<String>(it.get(0).IMID.toString())
//
//                    var adaptertheme1 = ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them1)
//                    adaptertheme1.setDropDownViewResource(R.layout.my_spinner_dashboard)
//                    spin_imid?.adapter = adaptertheme1


                    spin_hhid.isEnabled = false
                    spin_imid.isEnabled = false

                    spin_districtname_psy.setSelection(
                        returnposDistrict(
                            it.get(0).DistrictCode

                        )
                    )
//                    hhGUIDShow = returnHHGUID(1, it.get(0).HHID!!)!!
//                    imGUIDShow = returnIMGUID(1, it.get(0).IMID!!)!!
                    hhGUIDShow = it.get(0).HHID!!
                    imGUIDShow = it.get(0).IMID!!
                    DistCode = it.get(0).DistrictCode
                    ZoneCode = it.get(0).ZoneCode
                    WardCode = it.get(0).Panchayat_Ward!!
                    PanchayatCode = it.get(0).Panchayat_Ward!!

//                    HHCode = validate!!.returnStringValue(it.get(0).HHID)
//                    IdvCode = validate!!.returnStringValue(it.get(0).IMID)

//                    spin_hhid.setSelection(
//                        returnposHHcodeShowData(
//                            validate!!.returnStringValue(it.get(0).HHID)
//                        )
//                    )

                    et_name_participant.setText(validate!!.returnStringValue(it.get(0).Name_participant))
                    et_age_participant.setText(validate!!.returnStringValue(it.get(0).Age_partcipant.toString()))
                    et_name_community.setText(validate!!.returnStringValue(it.get(0).Name_Community.toString()))
                    et_name_shg.setText(validate!!.returnStringValue(it.get(0).SHG_Name.toString()))
                    et_name_entrprise.setText(validate!!.returnStringValue(it.get(0).Nature_entrprise.toString()))




//                    et_namecrp.setText(validate!!.returnStringValue(it.get(0).Name_CRP.toString()))

                    et_date.setText(validate!!.addDays(it.get(0).Date!!.toInt()))

                    spin_primary_income.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Primary_occ.toString()),
                            13
                        )
                    )
                    spin_secondry_income.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(0).Secondary_occ.toString()
                            ), 14
                        )
                    )

                    et_specif_primary_occu.setText(validate!!.returnStringValue(it.get(0).Primary_occ_othr))
                    et_specify_source_secondary_income.setText(validate!!.returnStringValue(it.get(0).Secondary_occ_othr))

//                    spin_imid.setSelection(
//                        returnposIMcode(
//                            validate!!.returnStringValue(it.get(0).IMID)
//                        )
//                    )

                }
            })

    }


    private fun checkValidation(): Int {
        var value = 1


        if (et_date.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_date,
                resources.getString(R.string.plz_enter_date_psy)
            )
            value = 0

        } else if (spin_districtname_psy.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname_psy,
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
        } else if (spin_hhid.selectedItemPosition == -1) {
            validate!!.CustomAlertSpinner(
                this,
                spin_hhid,
                resources.getString(R.string.psy_plz_select_hhid)
            )
            value = 0

        } else if (spin_imid.selectedItemPosition == -1) {
            validate!!.CustomAlertSpinner(
                this,
                spin_imid,
                resources.getString(R.string.psy_plz_select_imid)
            )
            value = 0
//        } else if (et_name_participant.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_name_participant,
//                resources.getString(R.string.plz_enter_name_participant_psy)
//            )
//            value = 0
//        } else if (et_age_participant.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_age_participant,
//                resources.getString(R.string.plz_enter_age_participant_psy)
//            )
//            value = 0

        } else if (et_contactnumber.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnumber,
                resources.getString(R.string.plz_enter_contact_no_psy)
            )
            value = 0
        } else if (validate!!.checkmobileno(et_contactnumber.text.toString()) == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnumber,
                resources.getString(R.string.please_enter_valid_contact_number)
            )
            value = 0
        } else if (spin_primary_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_primary_income,
                resources.getString(R.string.plz_enter_primary_occu_psy)
            )
            value = 0
        } else if (et_specif_primary_occu.text.toString()
                .isEmpty() && lay_et_specif_source_income.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_specif_primary_occu,
                resources.getString(R.string.please_specify_psch_pdc)
            )
            value = 0
        } else if (spin_secondry_income.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_secondry_income,
                resources.getString(R.string.plz_enter_secondry_occu_psy)
            )
            value = 0
        } else if (et_specify_source_secondary_income.text.toString()
                .isEmpty() && lay_et_specify_source_secondary_income.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_source_secondary_income,
                resources.getString(R.string.please_specify_psch_pdc)
            )
            value = 0
        } else if (et_name_community.text.toString().length == 0) {
            validate!!.CustomAlertEdit(
                this,
                et_name_community,
                resources.getString(R.string.plz_enter_name_community_psy)
            )
            value = 0
//        } else if (et_name_entrprise.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_name_entrprise,
//                resources.getString(R.string.plz_enter_nature_enterprise_psy)
//            )
//            value = 0

//        } else if (et_namecrp.text.toString().length == 0) {
//            validate!!.CustomAlertEdit(
//                this,
//                et_contactnumber,
//                resources.getString(R.string.plz_enter_name_crp_psy)
//            )
//            value = 0

        }
        return value
    }

    fun fillSpinnerView() {
        bindDistrict(resources.getString(R.string.select), spin_districtname_psy)

        fillSpinner("Select", spin_primary_income, 13, iLanguageID)

        fillSpinner("Select", spin_secondry_income, 14, iLanguageID)
//        fillSpinnerHHIDTable(resources.getString(R.string.select), spin_hhid)

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

    fun sendData() {
        val hhCode = spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition)
        val imCode = spin_imid.getItemAtPosition(spin_imid.selectedItemPosition)
        psychometricViewModel.collectivefirstData(
            hhCode,
            imCode, PrimaryOccu, SecondryOccu,
        )


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(0)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

    }

    fun bindPsychometricData(indvGUID: String) {
        psychometricViewModel.getallIdvdata(indvGUID).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                for (i in 0 until it.size) {
                    et_name_participant.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_age_participant.setText(validate!!.returnStringValue(it.get(0).Age.toString()))
                    et_contactnumber.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
//                    et_date.setText(validate!!.returnStringValue(it.get(0).DateForm.toString()))
//                    et_namecrp.setText(validate!!.returnStringValue(it.get(0).Name_CRP.toString()))


                    PrimaryOccu = it.get(0).Primary_Occupation!!
                    SecondryOccu = it.get(0).Secondary_Occupation!!


                    spin_primary_income.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Primary_Occupation.toString()),
                            13
                        )
                    )
                    spin_secondry_income.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(0).Secondary_Occupation.toString()
                            ), 14
                        )
                    )

                    et_specify_source_secondary_income.setText(validate!!.returnStringValue(it.get(0).Sec_Inc_Other))
                    et_specif_primary_occu.setText(validate!!.returnStringValue(it.get(0).Primary_Inc_Other))
                    et_name_community.setText(validate!!.returnStringValue(it.get(0).Locality))
//                    et_primary_occup.setText(
//                        returnStringByPos(
//                            it.get(0).Primary_Occupation!!,
//                            13,
//                            iLanguageID
//                        )
//                    )
//                    et_secondry_occup.setText(
//                        returnStringByPos(
//                            it.get(0).Secondary_Occupation!!,
//                            14,
//                            iLanguageID
//                        )
//                    )
                }

            }
        })
    }


    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {

            val intent = Intent(this, PsychometricFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
                val intent = Intent(this, PsychometricSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
                val intent = Intent(this, PsychometricThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_forth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID)!!.length > 0) {
                val intent = Intent(this, PsychometricForthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun fillSpinnerHHIDTable(strValue: String, spin: Spinner) {
        psychometricViewModel.gethhProfileData().observe(this, androidx.lifecycle.Observer {
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


    fun bindIMIDTable(strValue: String, spin: Spinner, hhcode: String) {
        psychometricViewModel.getallhhProfiledata(hhcode)
            .observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    val iGen = it.size
                    val name = arrayOfNulls<String>(iGen + 1)
                    name[0] = strValue

                    for (i in 0 until it.size) {
                        name[i + 1] = it.get(i).IndvCode

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

        if (validate!!.RetriveSharepreferenceString(AppSP.PATGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.PATGUID
            )!!.trim().length > 0
        ) {
            zonedata = psychometricViewModel.getallIdvPrfdata(hhguid)
        } else {
            zonedata = psychometricViewModel.findIdvPrfdata(hhguid)
        }

//        val zonedata =
//            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)

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
        it = if (!list.isNullOrEmpty()) {
            list.let {
                mstDistrictViewModel.getMstDistrict(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    list
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


    fun returnStringByPos(pos: Int, flag: Int, iLanguageID: Int): String? {
        var id: String? = ""
        var data: List<MstLookupEntity>? = null
        data = mstLookupViewModel.getMstAllDataNew(flag, iLanguageID, pos)
        if (!data.isNullOrEmpty()) {
            return data.get(0).Description
        } else {
            return ""
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

    fun returnposDistrict(
        id: Int?
    ): Int {

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

        var data: List<MstDistrictEntity>? = null
        data = if (!list.isNullOrEmpty()) {
            list.let {
                mstDistrictViewModel.getMstDistrict(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    list
                )
            }
        } else {


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
        var data: List<MstPanchayat_WardEntity>? = null
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


    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
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

        var data: List<MstDistrictEntity>? = null
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

    fun returnHhPrfID(
        pos: Int?,
        isUrban: Int,
        ZoneCode: Int,
        WardCode: Int,
        PanchayatCode: Int
    ): String? {
        var data: List<HouseholdProfileEntity>? = null
//        data = psychometricViewModel.gethhProfileDataNew()


        if (isUrban == 1) {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
        } else {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(PanchayatCode)
        }

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHCode

            }
        }
        Log.i("MyGETHHIDATA", "$id")
        return id
    }


    fun returnIMCodeID(pos: Int?, hhcode: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data = psychometricViewModel.getallIdvPrfdataUpdate(hhcode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndvCode

            }
        }
        Log.i("MyIIIIMMM", "$id")
        return id
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


    fun returnposHHcode(
        strValue: String,
        DistCode: Int,
        ZoneCode: Int,
        WardCode: Int,
        PanchayatCode: Int
    ): Int {
        var posi = 0
        var hhcode: List<HouseholdProfileEntity>

        if (DistCode == 1) {
            hhcode = psychometricViewModel.gethhProfileDataWard(ZoneCode, WardCode)
        } else {
            hhcode = psychometricViewModel.gethhProfileDataPanchayat(PanchayatCode)
        }

        /* if (isUrban == 1) {
             hhcode = CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
         } else {
             hhcode = CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(PanchayatCode)
         }

 */

        if (hhcode != null) {

            for (i in 0 until hhcode.size) {

                if (strValue == hhcode.get(i).HHGUID) {
                    posi = i + 1
                }
            }

        }

        return posi
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


    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(0, 500)
        }, 100)
    }


    override fun onBackPressed() {
//        val intent = Intent(this, HomeDashboardActivity::class.java)
//        startActivity(intent)
//        finish()
    }


    fun returnIMGUID(pos: Int?, indvCode: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallGUIDIMProfileBYIMIDDdata(indvCode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndGUID

            }
        }
        Log.i("MyIIMM", "$id")
        return id
    }

    fun returnHHGUID(pos: Int?, hhCode: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallGUIDBYHHIDDdata(hhCode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHGUID

            }
        }
        Log.i("MyIdhh", "$id")
        return id
    }


}