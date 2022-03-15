package com.careindia.lifeskills.views.householdscreen

import  android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.careindia.lifeskills.database.AppDataBase
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileFirstBinding
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_first.btn_prev
import kotlinx.android.synthetic.main.activity_household_profile_first.btn_save
import kotlinx.android.synthetic.main.activity_household_profile_first.et_crp_name
import kotlinx.android.synthetic.main.activity_household_profile_first.et_last_code
import kotlinx.android.synthetic.main.activity_household_profile_first.et_localityname
import kotlinx.android.synthetic.main.activity_household_profile_first.et_sfc_name
import kotlinx.android.synthetic.main.activity_household_profile_first.lay_NameofZone
import kotlinx.android.synthetic.main.activity_household_profile_first.lay_bbmpName
import kotlinx.android.synthetic.main.activity_household_profile_first.lay_panchayatName
import kotlinx.android.synthetic.main.activity_household_profile_first.spin_bbmp
import kotlinx.android.synthetic.main.activity_household_profile_first.spin_districtname
import kotlinx.android.synthetic.main.activity_household_profile_first.spin_panchayatname
import kotlinx.android.synthetic.main.activity_household_profile_first.spin_zone
import kotlinx.android.synthetic.main.activity_pre_post_assessment_list.*
import kotlinx.android.synthetic.main.hhnavigationtab.*

import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class HouseholdProfileFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHouseholdProfileFirstBinding
    public var instance: AppDataBase? = null
    var validate: Validate? = null
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var householdProfileEntity: HouseholdProfileEntity
    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    val CrpName = MutableLiveData<Int>()
    val SuperverCor = MutableLiveData<Int>()
    val UniqueID = MutableLiveData<String>()
    val district = MutableLiveData<Int>()
    val zone = MutableLiveData<Int>()
    var disCode = 0
    var zonCode = 0

    // 1) "CIN" for CARE India
    //  2) Letter locality identifier- "W" for ward and "P" for panchayat.
    // 3) Character number for Ward or Panchayat code.
    // 4) Letter community identifier- "HH" for HouseHold.
    //  5) Character number for household code.
    //CINW001HH00001
    //CINP001HH00003
    var iLanguageID = 0
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0
    var sWardPanchayat = ""
    var ScreenCheck = -1
    var hh_code_starting = ""
    var iShow=0

    var initials = ""
    var CommInitials = ""
    var isUrban = 0
    var Ward1 = 0
    var Panchayat1 = 0
    var stateCode=0
    var formattedDate: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_first)
        instance = AppDataBase.getDatabase(this)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        stateCode= validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
        val hhdao = CareIndiaApplication.database?.hhProfileDao()!!

        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!

        val hhRepository = HouseholdProfileRepository(hhdao, mstDistrictDao)
        householdProfileViewModel =
            ViewModelProvider(
                this,
                HouseholdProfileViewModelFactory(hhRepository)
            )[HouseholdProfileViewModel::class.java]
        binding.householdProfileViewModel = householdProfileViewModel
        binding.lifecycleOwner = this
        tv_title.text = "Household Profile"



        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)


        initializeController()
        bottomclick()
        fillSpinner()
        hideview()

        if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.HHGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        } else {
            setLocation()
        }

        et_formfillingDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(
                    formattedDate
                ),
                et_formfillingDate
            )
        }

        et_formfillingDate.setText(validate!!.currentdatetimeNew)

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
                if (s.length != 0) et_hh_unique_id.setText(
                    hh_code_starting + getCharacterNumber(
                        validate!!.returnIntegerValue(et_last_code.text.toString()),
                        "00000"
                    )
                )
            }
        })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(0)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

    }

    fun bottomclick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        lay_first.setOnClickListener {

            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
//        lay_secnd.setOnClickListener {
//            if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.length > 0) {
//                val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//
//        ll_third.setOnClickListener {
//            if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.length > 0 && ScreenCheck >= 0) {
//                val intent = Intent(this, HouseholdProfileThirdActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

        img_back.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size > 0) {

                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE

                        lay_secnd.setOnClickListener {
                            if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.length > 0) {
                                val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                        ll_third.setOnClickListener {
                            if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.length > 0 && ScreenCheck >= 0) {
                                val intent = Intent(this, HouseholdProfileThirdActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }

                    et_formfillingDate.setText(it.get(0).Dateform?.let { it1 ->
                        validate!!.addDays(
                            it1.toInt())
                    })
                    et_hhName.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_localityname.setText(validate!!.returnStringValue(it.get(0).Locality))
                    et_landmark.setText(validate!!.returnStringValue(it.get(0).LandMark))
                    et_pincode.setText(validate!!.returnStringValue(it.get(0).PinCode))
                    et_hh_unique_id.setText(validate!!.returnStringValue(it.get(0).HHCode))

                    //spin_name_crp.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),41))
                    //spin_SupervisingFC.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),42))
                    et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
                    et_sfc_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
                    spin_districtname.setSelection(returnposDistrict(it.get(0).DistrictCode))
                    DistCode = it.get(0).DistrictCode!!
                    ZoneCode = it.get(0).ZoneCode!!
                    //spin_panchayatname.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).PWCode),mstCommonViewModel,6))


                    spin_sex.setSelection(validate!!.returnpos(
                        validate!!.returnIntegerValue(it.get(0).Gender.toString()),
                        mstLookupViewModel,
                        1,
                        iLanguageID))
                    ScreenCheck = it.get(0).No_adults!!
                    et_last_code.visibility = View.GONE
                    WardCode=it.get(0).Panchayat_Ward!!
                    PanchayatCode=it.get(0).Panchayat_Ward!!
                    CommInitials = it.get(0).Initials!!
                    if (CommInitials.equals("C")) {
                        spin_community_hh.setSelection(1)
                    } else if(CommInitials.equals("D")){
                        spin_community_hh.setSelection(2)
                    }
                    iShow=1
                    var dte=it.get(0).CreatedOn?.let { it1 -> validate!!.addDays(it1.toInt()) }
                    Log.i("MYTAGTWO", dte.toString())


                }
            })
        }

    }

   fun setLocation() {
        spin_districtname.setSelection(returnposDistrict(validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)))
        DistCode = validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)
        ZoneCode = validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter)
        if(ZoneCode>0)
        {
            WardCode = validate!!.RetriveSharepreferenceInt(AppSP.WardFilter)
        } else
        {
            WardCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
        }


    }

    override fun initializeController() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }
        applyClickOnView()


    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (CheckValidation() == 0) {
                    householdProfileViewModel.saveandUpdateHHProfile(this,initials,iLanguageID)
                    val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, HouseholdProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun fillSpinner() {
        fillSpinner("Select", spin_sex, 1, iLanguageID)
        bindDistrict(resources.getString(R.string.select), spin_districtname)
        bindCommunity(resources.getString(R.string.select), spin_community_hh)
    }

    fun hideview() {
        spin_districtname.isEnabled = false
        spin_zone.isEnabled = false
        spin_bbmp.isEnabled = false
        spin_panchayatname.isEnabled = false

      /*  householdProfileViewModel.district.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val districtCode = it
            if (districtCode > 0) {
                val isUrban = returnUrban_rural(districtCode, stateCode)
                disCode = returnDistrictID(districtCode, stateCode)
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
                    bindPanchayat(resources.getString(R.string.select), spin_panchayatname, disCode)
                    spin_panchayatname.setSelection(returnposPanchayat(WardCode,disCode))
                } else {
                    lay_NameofZone.visibility = View.GONE
                    lay_bbmpName.visibility = View.GONE
                    lay_panchayatName.visibility = View.GONE
                    spin_bbmp.setSelection(0)
                    spin_zone.setSelection(0)
                    spin_panchayatname.setSelection(0)

                }

            }


        })*/


          spin_districtname.setOnItemSelectedListener(object :
              AdapterView.OnItemSelectedListener {

              override fun onItemSelected(
                  parent: AdapterView<*>, view: View?,
                  districtCode: Int, id: Long
              ) {
                  if (districtCode > 0) {
                      val isUrban = returnUrban_rural(districtCode, stateCode)
                      disCode = returnDistrictID(districtCode, stateCode)
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
                          bindPanchayat(resources.getString(R.string.select), spin_panchayatname, disCode)
                          spin_panchayatname.setSelection(returnposPanchayat(WardCode,disCode))
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

              override fun onNothingSelected(parent: AdapterView<*>) {
                  // TODO Auto-generated method stub

              }
          })


        spin_zone.setOnItemSelectedListener(object :
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
          })






    /*    householdProfileViewModel.zone.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val zonePos = it
            if (zonePos > 0) {
                zonCode = returnZoneID(zonePos, disCode)
                bindMstWard(resources.getString(R.string.select), spin_bbmp, zonCode)
                spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))


            }


        })*/

/*        householdProfileViewModel.ward.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val wardPos = it
            if (wardPos > 0) {
                val Ward1: Int = returnWardID(
                    wardPos,
                    zonCode
                )
                if (WardCode == 0) {
                   // et_hh_unique_id.setText(getHHCode(Ward1))
                   // et_last_code.isEnabled=true
                }

            }


        })*/


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
                         et_hh_unique_id.setText(getHHCode(Ward1))
                         et_last_code.isEnabled=true
                    }

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }



        spin_panchayatname.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                panchayatPos: Int, id: Long
            ) {
                if (panchayatPos > 0) {
                    Ward1 = returnPanchayatID(
                        panchayatPos,
                        disCode
                    )
                    if (PanchayatCode == 0) {
//                         et_hh_unique_id.setText(getHHCode(Panchayat1))
//                         et_last_code.isEnabled=true
                    }

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }

//        householdProfileViewModel.panchayat.observe(this, Observer {
//            Log.i("MYTAGTWO", it.toString())
//            val panchayatPos = it
//            if (panchayatPos > 0) {
//                val Panchayat1: Int = returnPanchayatID(
//                    panchayatPos,
//                    disCode
//                )
//                if (PanchayatCode == 0) {
//                   // et_hh_unique_id.setText(getHHCode(Panchayat1))
//                   // et_last_code.isEnabled=true
//                }
//
//
//            }
//
//
//        })

        spin_community_hh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position>0) {
                    if (position == 1) {
                        initials = "C"
                    } else if (position == 2) {
                        initials = "D"
                    }
                    if (CommInitials.equals("")) {
                        if (isUrban == 1) {
                            val hhmax = householdProfileViewModel.getHHCount() + 1
                            et_last_code.setText(
                                getCharacterNumber(
                                    hhmax,
                                    "00000"
                                )
                            )
                            et_hh_unique_id.setText(getHHCode(Ward1) + et_last_code.text.toString())
                        } else {
                            val hhmax = householdProfileViewModel.getHHCount() + 1
                            et_last_code.setText(
                                getCharacterNumber(
                                    hhmax,
                                    "00000"
                                )
                            )
                            et_hh_unique_id.setText(getHHCode(Ward1) + et_last_code.text.toString())
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
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
            data = list.let { mstDistrictViewModel.getMstDistrict(stateCode, list) }
        } else
        {


            data = mstDistrictViewModel.getMstDistrict(stateCode)

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
            data = list.let { mstDistrictViewModel.getMstDistrict(stateCode, list) }
        } else
        {


            data = mstDistrictViewModel.getMstDistrict(stateCode)

        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode!!

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
        data= if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(distCode,list)
        } else {


            mstZoneViewModel.getMstZone(distCode)

        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).ZoneCode!!

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
                    id = data.get(pos - 1).pwcode!!

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
                    id = data.get(pos - 1).pwcode!!

            }
        }
        return id
    }


    fun CheckValidation(): Int {
        var iValue = 0

        val iCount=householdProfileViewModel.getIndividualID(et_hh_unique_id.text.toString())

        val strPattern = "^[1-9][0-9]{5}$"



        if (et_formfillingDate.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_formfillingDate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form),
            )
        } else if (et_crp_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_crp_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.Name_of_CRP_filling_the_form),
            )
        } else if (et_sfc_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_sfc_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.Name_of_supervising_field_cordinator),
            )
        } else if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_distric),
            )
        } else if (spin_zone.selectedItemPosition == 0 && lay_NameofZone.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0 && lay_bbmpName.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0 && lay_panchayatName.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_panchayat),
            )
        } else if (et_localityname.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_localityname,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q108_name_of_locality),
            )
        } else if (et_landmark.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_landmark,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q109_closest_landmark),
            )
        } else if (et_pincode.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_pincode,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q110_area_pincode),
            )
        } else if (et_pincode.text.toString().length < 6) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_pincode,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.valid_pin_code),
            )
        }  else if (et_pincode.text.toString().length <6) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_pincode,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.valid_pin_code),
            )
        } else if (spin_community_hh.selectedItemPosition== 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_community_hh,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.community_dwcc),
            )
        } else if (et_hh_unique_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hh_unique_id, resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hhh_unique_id),
            )
        } else if (iCount>0 && iShow==0) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                getString(R.string.hhidexists),
            )
        } else if (et_hhName.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hhName,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hh_name),
            )
        } else if (spin_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_sex),
            )
        }
        return iValue
    }


    override fun onBackPressed() {
      /*  val intent = Intent(this, HouseholdProfileListActivity::class.java)
        startActivity(intent)
        finish()*/
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
        if (!list.isNullOrEmpty()) {
            it = list.let {
                CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    it
                )
            }
        } else
        {

            it= CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode))

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
        zonedata= if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(districtCode,list)
        } else {


            mstZoneViewModel.getMstZone(districtCode)

        }
        if (zonedata != null) {
            if (zonedata.isNotEmpty()) {
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
            mstPanchayatWardViewModel.getMstPanchayat(districtCode, list)
        } else {
            mstPanchayatWardViewModel.getMstPanchayat(districtCode)

        }
        if (mstPanchayat != null) {
            if (mstPanchayat.isNotEmpty()) {
                val iGen = mstPanchayat?.size
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
            if (mstWard.isNotEmpty()) {
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
            data = list.let { mstDistrictViewModel.getMstDistrict(stateCode, list) }
        } else
        {


            data = mstDistrictViewModel.getMstDistrict(stateCode)

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
        data= if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(distCode,list)
        } else {


            mstZoneViewModel.getMstZone(distCode)

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


    /* fun sendData() {
         householdProfileViewModel.hhfirstData(
             returnDistrictID(spin_districtname.selectedItemPosition,10),
             returnZoneID(spin_zone.selectedItemPosition,disCode),
             return(spin_zone.selectedItemPosition,disCode),
             validate!!.GetAnswerTypeRadioButtonID(rg_member_cig_shg)
         )
     }*/


    fun getHHCode(ward_or_panchayat_code: Int): String {
        var hh_code = ""
        val cin = "CIN"
        val household = "HH"
        var character_number = householdProfileViewModel.getHHCount() + 1

        hh_code =
            cin + sWardPanchayat + getCharacterNumber(ward_or_panchayat_code, "000") + household+initials
        hh_code_starting = hh_code
        return hh_code
    }

    fun getCharacterNumber(character_number: Int, pattern: String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }

    fun returnposPanchayat(id: Int?,distCode: Int): Int {
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
            mstPanchayatWardViewModel.getMstPanchayat(distCode, list)
        } else {
            mstPanchayatWardViewModel.getMstPanchayat(distCode)

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

}