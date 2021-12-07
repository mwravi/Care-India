package com.careindia.lifeskills.views.householdscreen

import  android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.careindia.lifeskills.database.AppDataBase
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileFirstBinding
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_first.btn_prev
import kotlinx.android.synthetic.main.activity_household_profile_first.btn_save
import kotlinx.android.synthetic.main.hhnavigationtab.*

import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.DecimalFormat


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
    var disCode=0
    var zonCode=0
   // 1) "CIN" for CARE India
  //  2) Letter locality identifier- "W" for ward and "P" for panchayat.
   // 3) Character number for Ward or Panchayat code.
   // 4) Letter community identifier- "HH" for HouseHold.
  //  5) Character number for household code.
    //CINW001HH00001
    //CINP001HH00003
    var iLangiageID=0
    var ZoneCode=0
    var DistCode=0
    var WardCode=0
    var PanchayatCode=0
    var sWardPanchayat=""
    var ScreenCheck=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_first)
        instance = AppDataBase.getDatabase(this)
        validate= Validate(this)
        iLangiageID=validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

       val hhdao = CareIndiaApplication.database?.hhProfileDao()!!

       val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!

        val hhRepository = HouseholdProfileRepository(hhdao,mstDistrictDao)
        householdProfileViewModel =
            ViewModelProvider(this,
                HouseholdProfileViewModelFactory(hhRepository))[HouseholdProfileViewModel::class.java]
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
        if(validate!!.RetriveSharepreferenceString(AppSP.HHGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.trim().length>0) {
            showLiveData()
        }

        et_formfillingDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfillingDate
            )
        }
    }

    fun bottomclick()
    {
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        lay_first.setOnClickListener {

            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
           if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.length>0) {
               val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
               startActivity(intent)
               finish()
           }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID)!!.length>0  && ScreenCheck>=0) {
                val intent = Intent(this, HouseholdProfileThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size>0) {
                    et_formfillingDate.setText(validate!!.returnStringValue(it.get(0).Dateform))
                    et_hhName.setText(validate!!.returnStringValue(it.get(0).Name))
                    et_localityname.setText(validate!!.returnStringValue(it.get(0).Localitycode))
                    et_hh_unique_id.setText(validate!!.returnStringValue(it.get(0).HHCode))

                    //spin_name_crp.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),41))
                    //spin_SupervisingFC.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),42))
                    et_crp_name.setText(it.get(0).Crpid)
                    et_sfc_name.setText(it.get(0).fcid)
                    spin_districtname.setSelection(returnposDistrict(validate!!.returnIntegerValue(it.get(0).DistrictCode)))
                    DistCode=validate!!.returnIntegerValue(it.get(0).DistrictCode)
                    ZoneCode=validate!!.returnIntegerValue(it.get(0).ZoneCode)
                    WardCode=it.get(0).Panchayat_Ward!!
                    PanchayatCode=validate!!.returnIntegerValue(it.get(0).PWCode)
                    //spin_panchayatname.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).PWCode),mstCommonViewModel,6))
                    validate!!.SetAnswerTypeRadioButton(rg_hh_sex,it.get(0).Gender)
                    ScreenCheck=it.get(0).No_adults!!


                }
            })
        }

    }

    override fun initializeController() {
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
                    householdProfileViewModel.saveandUpdateHHProfile(this)
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



        validate!!.fillradio(
            this,
            rg_hh_sex,
            -1,
            mstLookupViewModel,
            1,
            iLangiageID)



        bindDistrict(resources.getString(R.string.select),spin_districtname)





    }

    fun hideview()
    {
        householdProfileViewModel.district.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            var districtCode = it
            if (districtCode>0)
            {
                var isUrban=returnUrban_rural(districtCode,10)
                 disCode=returnDistrictID(districtCode,10)
                if (isUrban==1)
                {
                    lay_NameofZone.visibility=View.VISIBLE
                    lay_bbmpName.visibility=View.VISIBLE
                    sWardPanchayat="W"
                    lay_panchayatName.visibility=View.GONE
                    spin_panchayatname.setSelection(0)
                    bindMstZone(resources.getString(R.string.select),spin_zone,disCode)
                    spin_zone.setSelection(returnposZone(ZoneCode,DistCode))
                } else
                {
                    lay_NameofZone.visibility=View.GONE
                    spin_zone.setSelection(0)
                    spin_zone.visibility=View.GONE
                    spin_bbmp.setSelection(0)
                    lay_panchayatName.visibility=View.VISIBLE
                    sWardPanchayat="P"
                    bindPanchayat(resources.getString(R.string.select),spin_panchayatname,disCode)
                }

            }


        })



      /*  spin_districtname.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                position: Int, id: Long
            ) {
                if (position>0)
                {
                    var isUrban=returnUrban_rural(position,10)
                    disCode=returnDistrictID(position,10)
                    if (isUrban==1)
                    {
                        lay_NameofZone.visibility=View.VISIBLE
                        lay_bbmpName.visibility=View.VISIBLE
                        lay_panchayatName.visibility=View.GONE
                        spin_panchayatname.setSelection(0)
                        bindMstZone(resources.getString(R.string.select),spin_zone,disCode)
                    } else
                    {
                        lay_NameofZone.visibility=View.GONE
                        spin_districtname.setSelection(0)
                        lay_bbmpName.visibility=View.GONE
                        spin_zone.setSelection(0)
                        lay_panchayatName.visibility=View.VISIBLE
                        bindPanchayat(resources.getString(R.string.select),spin_zone,disCode)
                    }

                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        })*/




        householdProfileViewModel.zone.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val zonePos = it
            if (zonePos>0)
            {
                zonCode=returnZoneID(zonePos,disCode)
                bindMstWard(resources.getString(R.string.select),spin_bbmp,zonCode)
                spin_bbmp.setSelection(returnposWard(WardCode,ZoneCode))


            }


        })

        householdProfileViewModel.ward.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val wardPos = it
            if (wardPos>0)
            {
                val Ward1: Int = returnWardID(
                    wardPos,
                    zonCode
                )
                if (WardCode==0)
                {
                    et_hh_unique_id.setText(getHHCode(Ward1))
                }


            }


        })

        householdProfileViewModel.panchayat.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val panchayatPos = it
            if (panchayatPos>0)
            {
                val Panchayat1: Int = returnPanchayatID(
                    panchayatPos,
                    disCode
                )
                if (PanchayatCode==0)
                {
                    et_hh_unique_id.setText(getHHCode(Panchayat1))
                }



            }


        })
    }

    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            householdProfileViewModel.getMstDist(StateCode)

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
            householdProfileViewModel.getMstDist(StateCode)

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
        var data: List<MstZoneEntity>? = null
        data =
            mstZoneViewModel.getMstZone(distCode)

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
        var data: List<MstPanchayat_WardEntity>? = null
        data =
            mstPanchayatWardViewModel.getMstWard(zoneCode)

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
        var data: List<MstPanchayat_WardEntity>? = null
        data =
            mstPanchayatWardViewModel.getMstPanchayat(disCode)

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
        } else if (spin_zone.selectedItemPosition == 0 && lay_NameofZone.visibility==View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0 && lay_bbmpName.visibility==View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0 && lay_panchayatName.visibility==View.VISIBLE) {
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
        } else if (et_hh_unique_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hh_unique_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hhh_unique_id),
            )
        } /*else if (et_hh_unique_id.text.toString().length != 14) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hh_unique_id,
                resources.getString(R.string.hh_name),
            )
        }*/ else if (et_hhName.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_hhName,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.hh_name),
            )
        }
        return iValue
    }



    override fun onBackPressed() {
        val intent = Intent(this, HouseholdProfileListActivity::class.java)
        startActivity(intent)
        finish()
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

    fun bindMstZone(strValue: String, spin: Spinner,districtCode:Int) {
       var zonedata= mstZoneViewModel.getMstZone(districtCode)
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


    fun bindPanchayat(strValue: String, spin: Spinner,districtCode:Int) {
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

    fun bindMstWard(strValue: String, spin: Spinner,zoneCode:Int) {

        var mstWard=mstPanchayatWardViewModel.getMstWard(zoneCode)
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



    fun fillSpinner(strValue: String, spin: Spinner,
                    flag:Int,
                    iLanguageID:Int) {
        mstLookupViewModel!!.getMstLookup(flag,iLanguageID).observe(this, androidx.lifecycle.Observer {
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
        id: Int?): Int {
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
        id: Int?,distCode: Int): Int {
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

    fun returnposWard(id: Int?,zoneCode: Int): Int {
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


   /* fun sendData() {
        householdProfileViewModel.hhfirstData(
            returnDistrictID(spin_districtname.selectedItemPosition,10),
            returnZoneID(spin_zone.selectedItemPosition,disCode),
            return(spin_zone.selectedItemPosition,disCode),
            validate!!.GetAnswerTypeRadioButtonID(rg_member_cig_shg)
        )
    }*/


    fun getHHCode(ward_or_panchayat_code:Int): String {
        var hh_code = ""
        val cin = "CIN"
        val household = "HH"
        var character_number = householdProfileViewModel.getHHCount()+1

        hh_code = cin + sWardPanchayat + getCharacterNumber(ward_or_panchayat_code,"000" )+ household + getCharacterNumber(
            character_number,"00000"
        )

        return hh_code
    }

    fun getCharacterNumber(character_number: Int,pattern:String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }





}