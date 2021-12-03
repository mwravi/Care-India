package com.careindia.lifeskills.views.householdscreen

import android.content.Intent
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
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodel.MstDistrictViewModel
import com.careindia.lifeskills.viewmodel.MstZoneViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_household_profile_first.*
import kotlinx.android.synthetic.main.activity_household_profile_first.btn_prev
import kotlinx.android.synthetic.main.activity_household_profile_first.btn_save

import kotlinx.android.synthetic.main.toolbar_layout.*


class HouseholdProfileFirstActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHouseholdProfileFirstBinding
    public var instance: AppDataBase? = null
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var householdProfileEntity: HouseholdProfileEntity
    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    val CrpName = MutableLiveData<Int>()
    val SuperverCor = MutableLiveData<Int>()
    val UniqueID = MutableLiveData<String>()
    val district = MutableLiveData<Int>()
   // 1) "CIN" for CARE India
  //  2) Letter locality identifier- "W" for ward and "P" for panchayat.
   // 3) Character number for Ward or Panchayat code.
   // 4) Letter community identifier- "HH" for HouseHold.
  //  5) Character number for household code.
    //CINW001HH00001
    //CINP001HH00003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_first)
        instance = AppDataBase.getDatabase(this)
        validate= Validate(this)


       val hhdao = CareIndiaApplication.database?.hhProfileDao()!!
       val mstcmnDoa = CareIndiaApplication.database?.mstCommonDao()!!
       val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!

        val hhRepository = HouseholdProfileRepository(hhdao,mstcmnDoa,mstDistrictDao)
        householdProfileViewModel =
            ViewModelProvider(this,
                HouseholdProfileViewModelFactory(hhRepository))[HouseholdProfileViewModel::class.java]
        binding.householdProfileViewModel = householdProfileViewModel
        binding.lifecycleOwner = this
        tv_title.text = "Household Profile"

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)

        initializeController()
        fillSpinner()
        setHHCode()
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


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size>0) {
                    et_formfillingDate.setText(validate!!.returnStringValue(it.get(0).Dateform))
                    et_hhName.setText(validate!!.returnStringValue(it.get(0).Name))

                    //spin_name_crp.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),41))
                    //spin_SupervisingFC.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),42))
                    spin_crpfillingform.setSelection(validate!!.returnpos(it.get(0).CRP_Code,mstCommonViewModel,1))
                    spin_SupervisingFC.setSelection(validate!!.returnpos(it.get(0).FieldCoordinator,mstCommonViewModel,2))
                    spin_districtname.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),mstCommonViewModel,3))
                    spin_zone.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).ZoneCode),mstCommonViewModel,4))
                    spin_bbmp.setSelection(validate!!.returnpos(it.get(0).Panchayat_Ward,mstCommonViewModel,5))
                    spin_panchayatname.setSelection(validate!!.returnpos(validate!!.returnIntegerValue(it.get(0).PWCode),mstCommonViewModel,6))


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
                    householdProfileViewModel.saveandUpdateHHProfile()
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



        bindCommonTable(resources.getString(R.string.select),spin_crpfillingform,1)

        validate!!.fillradio(
            rg_hh_sex,
            -1,
            mstCommonViewModel,
            10,
            this
        )

        bindCommonTable(resources.getString(R.string.select),spin_SupervisingFC,2)
        bindCommonTable(resources.getString(R.string.select),spin_districtname,3)
        bindDistrict(resources.getString(R.string.select),spin_districtname)
        bindCommonTable(resources.getString(R.string.select),spin_bbmp,5)
        bindCommonTable(resources.getString(R.string.select),spin_panchayatname,6)




    }

    fun setHHCode()
    {
        UniqueID.value=getHHCode()
        et_hh_unique_id.setText(getHHCode())
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
        } else if (spin_crpfillingform.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_crpfillingform,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_CRP_filling_the_form),
            )
        } else if (spin_SupervisingFC.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_SupervisingFC,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_supervising_field_cordinator),
            )
        } else if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_distric),
            )
        } else if (spin_zone.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_bbmp_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0) {
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



    fun hideview()
    {
        householdProfileViewModel.district.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            var districtCode = it
            if (districtCode>0)
            {
                bindMstZone(resources.getString(R.string.select),spin_zone,districtCode)
            }


        })
    }


    fun getHHCode(): String
    {
        var hh_code=""
        var cin="CIN"
        var locality="W"
        var ward_or_panchayat_code="001"
        var household="HH"
        var character_number="00001"

        hh_code=cin+locality+ward_or_panchayat_code+household+getCharacterNumber(character_number)

        return hh_code
    }

    fun getCharacterNumber(character_number:String):String
    {
        var number=""
        if (character_number.length==1)
        {
            number="0000"+character_number
        } else if (character_number.length==2)
        {
            number="000"+character_number

        } else if (character_number.length==3)
        {
            number="00"+character_number

        } else if (character_number.length==4)
        {
            number="0"+character_number

        } else
        {
            number=character_number

        }

        return number

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


    fun bindDistrict(strValue: String, spin: Spinner) {
        mstDistrictViewModel.getMstDistrict(10).observe(this, androidx.lifecycle.Observer {
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
        /*if (distric>0) {
            spin_district_name.setSelection(returnpos(distric, 3))
        }*/
    }

    fun bindMstZone(strValue: String, spin: Spinner,districtCode:Int) {
        mstZoneViewModel.getMstZone(districtCode).observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                val iGen = it.size
                val name = arrayOfNulls<String>(iGen + 1)
                name[0] = strValue

                for (i in 0 until it.size) {
                    name[i + 1] = it.get(i).ZoneName
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





}