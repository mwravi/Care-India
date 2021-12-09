package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileOneBinding
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_one)
        validate = Validate(this)
        tv_title.text = "IM Profile"


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

        initializeController()
//        GlobalScope.launch {
//
//        }
    }

    override fun initializeController() {
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



        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_formfilngjgDate
            )

        }


//        validate!!.fillSpinner(
//            this,
//            spin_marital,
//            resources.getString(R.string.select),
//            mstCommonViewModel,
//            45
//        )


    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.visibility = View.GONE
        btn_save.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.saveandUpdateCollectiveProfile(this)
                    val intent = Intent(this, IMProfileTwoActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

        }
    }

    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_formfilngjgDate.setText(validate!!.returnStringValue(it.get(0).DateForm))
                    et_namerespo.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_imprf_id.setText(validate!!.returnStringValue(it.get(0).IndvCode))

//                    var hhCode =it.get(0).HHCode!!



                    spin_hhid.setSelection(
                        returnposHHcode(
                            validate!!.returnStringValue(it.get(0).HHCode)
                        ))
                   // spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition) as String


//                    et_crp_name.setText(it.get(0).CRP_Code)
//                    et_sfc_name.setText(it.get(0).FieldCoordinator)
                    spin_districtname.setSelection(
                        returnposDistrict(
                            validate!!.returnIntegerValue(
                                it.get(0).DistrictCode
                            )
                        )
                    )
                    DistCode = validate!!.returnIntegerValue(it.get(0).DistrictCode)
                    ZoneCode = validate!!.returnIntegerValue(it.get(0).ZoneCode)
                    WardCode = it.get(0).Panchayat_Ward!!
                    PanchayatCode = validate!!.returnIntegerValue(it.get(0).PWCode)

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
                    et_contactnorespo.setText(validate!!.returnStringValue(it.get(0).Contact.toString()))
                }
            })

    }

    fun sendData() {
        imProfileViewModel.collectiveProfileOneData(
            spin_hhid.getItemAtPosition(spin_hhid.selectedItemPosition) as String,
            et_imprf_id.text.toString()

        )


    }

    fun topLayClick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))

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
        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    private fun checkValidation(): Int {
        var value = 1
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
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_distric),
            )
            value = 0
        } else if (spin_zone.selectedItemPosition == 0 && lay_NameofZone.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
            value = 0
        } else if (spin_bbmp.selectedItemPosition == 0 && lay_bbmpName.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward),
            )
            value = 0
        } else if (spin_panchayatname.selectedItemPosition == 0 && lay_panchayatName.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_panchayat),
            )
            value = 0
        } else if (spin_hhid.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_hhid,
                resources.getString(R.string.plz_select_hhid)
            )
            value = 0

        } else if (et_imprf_id.text.toString().isEmpty()) {
            validate!!.CustomAlertEdit(
                this,
                et_imprf_id,
                resources.getString(R.string.plz_enter_idvid)
            )
            value = 0
        } else if (et_namerespo.text.toString().isEmpty()) {
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


        } else if (et_contactnorespo.text.toString().isEmpty() &&
            validate!!.checkmobileno(et_contactnorespo.text.toString()) == 0
        ) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.plz_enter_contct_no_respo)
            )
            value = 0
        } else if (et_contactnorespo.text.toString().trim().length < 10) {
            validate!!.CustomAlertEdit(
                this,
                et_contactnorespo,
                resources.getString(R.string.plz_enter_contct_no_proper)
            )


            value = 0
        }
        return value

    }


    fun fillSpinner() {


        bindCommonTable("Select", spin_sexrepo, 1, iLanguageID)
        bindCommonTable("Select", spin_casterespo, 5, iLanguageID)
        bindCommonTable("Select", spin_marital, 6, iLanguageID)
        bindHHIDTable("Select", spin_hhid)

        bindDistrict(resources.getString(R.string.select), spin_districtname)
    }


    fun hideShowView() {

        imProfileViewModel.district.observe(this, Observer {
            var districtCode = it
            if (districtCode > 0) {
                var isUrban = returnUrban_rural(districtCode, 10)
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
                    spin_zone.visibility = View.GONE
                    spin_bbmp.setSelection(0)
                    lay_panchayatName.visibility = View.VISIBLE
                    sWardPanchayat = "P"
                    bindPanchayat(resources.getString(R.string.select), spin_panchayatname, disCode)
                }

            }


        })

        imProfileViewModel.zone.observe(this, Observer {
            val zonePos = it
            if (zonePos > 0) {
                zonCode = returnZoneID(zonePos, disCode)
                bindMstWard(resources.getString(R.string.select), spin_bbmp, zonCode)
                spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))


            }


        })

        imProfileViewModel.ward.observe(this, Observer {
            val wardPos = it
            if (wardPos > 0) {
                val Ward1: Int = returnWardID(
                    wardPos,
                    zonCode
                )
                if (WardCode == 0) {
                    et_imprf_id.setText(getHHCode(Ward1))
                }


            }


        })

        imProfileViewModel.panchayat.observe(this, Observer {

            val panchayatPos = it
            if (panchayatPos > 0) {
                val Panchayat1: Int = returnPanchayatID(
                    panchayatPos,
                    disCode
                )
                if (PanchayatCode == 0) {
                    et_imprf_id.setText(getHHCode(Panchayat1))
                }


            }


        })
    }


    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            imProfileViewModel.getMstDist(StateCode)

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
        data =
            imProfileViewModel.getMstDist(StateCode)

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
        data =
            mstZoneViewModel.getMstZone(distCode)

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
        var data: List<MstPanchayat_WardEntity>? = null
        data =
            mstPanchayatWardViewModel.getMstWard(zoneCode)

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
        var data: List<MstPanchayat_WardEntity>? = null
        data =
            mstPanchayatWardViewModel.getMstPanchayat(disCode)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).pwcode

            }
        }
        return id
    }


    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int, iLanguageID: Int) {
        mstLookupViewModel.getMstUser(flag, iLanguageID).observe(this, androidx.lifecycle.Observer {
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

    fun returnposHHcode(strValue: String): Int {
        var posi = 0
        var hhcode = imProfileViewModel.gethhProfileDataNew()
            if (hhcode != null) {

                for (i in 0 until hhcode.size) {

                    if(strValue == hhcode.get(i).HHCode){
                        posi = i + 1
                    }
                }

            }

                Log.i("MyTagRETURN","$posi")
        return posi
    }



    fun bindHHIDTable(strValue: String, spin: Spinner) {
        imProfileViewModel.gethhProfileData().observe(this, androidx.lifecycle.Observer {
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


    fun bindDistrict(strValue: String, spin: Spinner) {
        mstDistrictViewModel.getMstDistrictLive(10).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
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
        })

    }



    fun bindMstZone(strValue: String, spin: Spinner, districtCode: Int) {
        var zonedata = mstZoneViewModel.getMstZone(districtCode)
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
        var mstPanchayat = mstPanchayatWardViewModel.getMstPanchayat(districtCode)
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

        var mstWard = mstPanchayatWardViewModel.getMstWard(zoneCode)
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
        data =
            mstDistrictViewModel.getMstDistrict(10)
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
        var data: List<MstZoneEntity>? = null
        data =
            mstZoneViewModel.getMstZone(distCode)
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
        data =
            mstPanchayatWardViewModel.getMstWard(zoneCode)
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
        ) + imProfileTxt + getCharacterNumber(
            character_number, "00000"
        )

        return im_code
    }

    fun getCharacterNumber(character_number: Int, pattern: String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }


    override fun onBackPressed() {
        val intent = Intent(this, IMProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(5)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

    }


}