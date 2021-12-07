package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileFirstBinding
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstDistrictViewModel
import com.careindia.lifeskills.viewmodel.MstPanchayatWardViewModel
import com.careindia.lifeskills.viewmodel.MstZoneViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.activity_collective_profile_first.btn_prev
import kotlinx.android.synthetic.main.activity_collective_profile_first.btn_save
import kotlinx.android.synthetic.main.activity_collective_profile_first.lay_NameofZone
import kotlinx.android.synthetic.main.activity_collective_profile_first.lay_panchayatName
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.DecimalFormat

class CollectiveProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectiveProfileFirstBinding
    var validate: Validate? = null
    lateinit var collectiveViewModel: CollectiveViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var disCode = 0
    var zonCode = 0
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0
    var sWardPanchayat=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_first)
        validate = Validate(this)


        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val collectiveRepository = CollectiveRepository(collectivedao!!, mstDistrictDao)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                    CollectiveViewModel::class.java]
        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)
        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)
        binding.collectiveViewModel = collectiveViewModel
        binding.lifecycleOwner = this

        tv_title.text = "Collective Profile"

        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }

        initializeController()
        bottomCLick()
    }

    override fun initializeController() {
        et_date_of_filling.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_filling
            )
        }

        fillSpinner()
        applyClickOnView()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)

        collectiveViewModel.District.observe(this, Observer {

            val pos = it
            val isUrban = returnUrban_rural(pos, 10)
            disCode = returnDistrictID(pos, 10)

            if (isUrban == 1) {
                lay_NameofZone.visibility = View.VISIBLE
                lay_bbmpWard.visibility = View.VISIBLE
                lay_panchayatName.visibility = View.GONE
                spin_panchayat_name.setSelection(0)
                bindMstZone(resources.getString(R.string.select), spin_zone_name, disCode)
                spin_zone_name.setSelection(returnposZone(ZoneCode, DistCode))
                sWardPanchayat="W"
            } else if (isUrban == 2) {
                lay_NameofZone.visibility = View.GONE
                lay_bbmpWard.visibility = View.GONE
                lay_panchayatName.visibility = View.VISIBLE
                spin_ward_name.setSelection(0)
                spin_zone_name.setSelection(0)
                bindPanchayat(resources.getString(R.string.select), spin_panchayat_name, disCode)
                sWardPanchayat="P"
            } else {
                lay_NameofZone.visibility = View.GONE
                lay_bbmpWard.visibility = View.GONE
                lay_panchayatName.visibility = View.GONE
                spin_panchayat_name.setSelection(0)
                spin_ward_name.setSelection(0)
                spin_zone_name.setSelection(0)
                sWardPanchayat=""
            }
        })
        collectiveViewModel.ZoneName.observe(this, Observer {
            val zonePos = it
            if (zonePos > 0) {
                zonCode = returnZoneID(zonePos, disCode)
                bindMstWard(resources.getString(R.string.select), spin_ward_name, zonCode)
                spin_ward_name.setSelection(returnposWard(WardCode, ZoneCode))


            }

        })

        collectiveViewModel.WardName.observe(this, Observer {
            val wardPos = it
            if(wardPos>0){
                val Ward1: Int = returnWardID(
                    wardPos,
                    zonCode
                )
                if(WardCode==0){
                    et_collective_id.setText(getCollectiveUniqueID(Ward1))
                }
            }
        })
        collectiveViewModel.PanchayatName.observe(this, Observer {
            val panchayatPos = it
            if(panchayatPos>0){
                val Panchayat1: Int = returnPanchayatID(
                    panchayatPos,
                    disCode
                )
                if(PanchayatCode==0){
                    et_collective_id.setText(getCollectiveUniqueID(Panchayat1))
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    collectiveViewModel.saveandUpdateCollectiveProfile(this)
                    val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_date_of_filling.setText(validate!!.returnStringValue(it.get(0).DateForm))
                    et_locality_name.setText(validate!!.returnStringValue(it.get(0).Localitycode))
                    et_collective_id.setText(validate!!.returnStringValue(it.get(0).CollectiveID))
                    et_group_collective_name.setText(validate!!.returnStringValue(it.get(0).CollectiveName))
                    spin_district_name.setSelection(
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
                }
            })
    }


    fun checkValidation(): Int {

        var iValue = 0

        if (et_date_of_filling.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_filling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form_profile),
            )
        }  else if (spin_district_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_district_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_district),
            )
        }else if (spin_zone_name.selectedItemPosition == 0 && lay_NameofZone.visibility==VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_ward_name.selectedItemPosition == 0 && lay_bbmpWard.visibility== VISIBLE) {

            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_ward_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_bbmp_ward),
            )
        } else if (spin_panchayat_name.selectedItemPosition == 0 && lay_panchayatName.visibility== VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayat_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_panchayat),
            )
        } else if (et_locality_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_locality_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_locality),
            )
        } else if (et_collective_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_collective_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.collective_unique_id),
            )
        } else if (et_group_collective_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_group_collective_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_group_collective_sangha),
            )
        }
        return iValue
    }


    fun fillSpinner() {
        bindDistrict(resources.getString(R.string.select), spin_district_name)
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


    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            collectiveViewModel.getMstDist(StateCode)

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
            collectiveViewModel.getMstDist(StateCode)

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


    fun bottomCLick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_six.setBackgroundColor(resources.getColor(R.color.back))

        /* lay_first.setOnClickListener {
             val intent = Intent(this, CollectiveProfileActivity::class.java)
             startActivity(intent)
             finish()
         }*/
        lay_secnd.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.saveandUpdateCollectiveProfile(this)
                val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_third.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.saveandUpdateCollectiveProfile(this)
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fourth.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.saveandUpdateCollectiveProfile(this)
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fifth.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.saveandUpdateCollectiveProfile(this)
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_six.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.saveandUpdateCollectiveProfile(this)
                val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun getCollectiveUniqueID(ward_or_panchayat_code:Int): String {
        var hh_code = ""
        val cin = "CIN"
        val initials = "C"
        var character_number = collectiveViewModel.getCommunityCount()+1

        hh_code = cin + sWardPanchayat + getCharacterNumber(ward_or_panchayat_code,"000" )+ initials + getCharacterNumber(
            character_number,"000"
        )

        return hh_code
    }

    fun getCharacterNumber(character_number: Int,pattern:String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }


    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}