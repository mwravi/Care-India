package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.improfile.IMProfileTwoActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.coll_prog_tracker_first.*
import kotlinx.android.synthetic.main.coll_prog_tracker_first.btn_bottom
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*

class CollProgTrackerFirst : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    var disCode = 0
    var zonCode = 0
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0
    var sWardPanchayat = ""
    var coll_code_starting = ""
    var initials = ""
    var isUrban = 0
    var Ward1 = 0
    var Panchayat1 = 0
    var stateCode = 0
    var iShow = 0
    var formattedDate = ""
    var collectiveGuid = ""
    var cpdistrictId = 0
    var cpZoneID = 0
    var wPanchayatID = 0
    var cplist: List<CollectiveEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_first)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.collective_prog_tracker)
        collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID).toString()
        stateCode = validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        mstDistrictViewModel = ViewModelProvider(this).get(MstDistrictViewModel::class.java)

        mstLookupViewModel = ViewModelProvider(this).get(MstLookupViewModel::class.java)

        collectiveProgressTrackerViewModel =
            ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)

        mstZoneViewModel = ViewModelProvider(this).get(MstZoneViewModel::class.java)

        mstPanchayatWardViewModel =
            ViewModelProvider(this).get(MstPanchayatWardViewModel::class.java)

        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val collectiveRepository = CollectiveRepository(collectivedao!!, mstDistrictDao)
        collectiveViewModel = ViewModelProvider(
            this,
            CollectiveViewModelFactory(collectiveRepository)
        )[CollectiveViewModel::class.java]

        et_PersonName.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))

        fillSpinner()
        initializeController()


        if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CPTGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        } else {
            setLocation()
        }
    }

    override fun initializeController() {
        topLayClick()
        var collectiveUniqueId=""
        var locality=""
        var headName=""
        var gcRegistered=0
        var collName=""
        cplist = collectiveViewModel.getCollectiveDataByCollectiveGuid(collectiveGuid)
        if (cplist != null && cplist!!.size > 0) {
           collectiveUniqueId = cplist!!.get(0).CollectiveID.toString()
            locality = cplist!!.get(0).Localitycode.toString()
            headName = cplist!!.get(0).Head_name.toString()
            gcRegistered = cplist!!.get(0).Registration!!
            collName = cplist!!.get(0).CollectiveName.toString()

        }
        if (gcRegistered==1){
            validate!!.fillradio(this, rg_registered, 1, mstLookupViewModel, 3, iLanguageID)
            lay_Regdate.visibility = View.VISIBLE
            lay_org.visibility = View.VISIBLE
        } else{
            validate!!.fillradio(this, rg_registered, 0, mstLookupViewModel, 3, iLanguageID)
            lay_Regdate.visibility = View.GONE
            lay_org.visibility = View.GONE
        }
        et_collcode.setText(collectiveUniqueId)
        et_localityname.setText(locality)
        et_CIGCheifname.setText(headName)
        et_shg_cig_name.setText(collName)
        spin_districtname.isEnabled = false
        spin_zone.isEnabled = false
        spin_bbmp.isEnabled = false
        spin_panchayatname.isEnabled = false
        et_collcode.isEnabled = false
        et_localityname.isEnabled = false
//        et_CIGCheifname.isEnabled = false
        et_shg_cig_name.isEnabled = false
//        for (i in 0 until rg_registered.getChildCount()) {
//            (rg_registered.getChildAt(i) as RadioButton).isEnabled = false
//        }

        applyClickOnView()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }

        et_Regdate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_Regdate
            )
        }
        et_Date.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_Date
            )
        }

        et_Date.setText(validate!!.currentdatetimeNew)

        spin_districtname.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                pos: Int, id: Long
            ) {
                if (pos > 0) {
                    isUrban = returnUrban_rural(pos, stateCode)
                    disCode = returnDistrictID(pos, stateCode)

                    if (isUrban == 1) {
                        lay_NameofZone.visibility = View.VISIBLE
                        lay_bbmpName.visibility = View.VISIBLE
                        lay_panchayatName.visibility = View.GONE
                        spin_panchayatname.setSelection(0)
                        bindMstZone(resources.getString(R.string.select), spin_zone, disCode)
                        spin_zone.setSelection(returnposZone(ZoneCode, DistCode))
                        sWardPanchayat = "W"
                    } else if (isUrban == 2) {
                        lay_NameofZone.visibility = View.GONE
                        lay_bbmpName.visibility = View.GONE
                        lay_panchayatName.visibility = View.VISIBLE
                        spin_bbmp.setSelection(0)
                        spin_zone.setSelection(0)
                        bindPanchayat(
                            resources.getString(R.string.select),
                            spin_panchayatname,
                            disCode
                        )
                        spin_panchayatname.setSelection(returnposPanchayat(WardCode, disCode))
                        sWardPanchayat = "P"
                    } else {
                        lay_NameofZone.visibility = View.GONE
                        lay_bbmpName.visibility = View.GONE
                        lay_panchayatName.visibility = View.GONE
                        spin_panchayatname.setSelection(0)
                        spin_bbmp.setSelection(0)
                        spin_zone.setSelection(0)
                        sWardPanchayat = ""
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
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)

    }

    fun getCharacterNumber(character_number: Int, pattern: String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }


    fun setLocation() {
        cplist = collectiveViewModel.getCollectiveDataByCollectiveGuid(collectiveGuid)
        if (cplist != null && cplist!!.size > 0) {
            cpdistrictId = cplist!!.get(0).DistrictCode
            cpZoneID = cplist!!.get(0).ZoneCode
            wPanchayatID = cplist!!.get(0).Panchayat_Ward!!
        }
        spin_districtname.setSelection(returnposDistrict(cpdistrictId))
        DistCode = cpdistrictId
        ZoneCode = cpZoneID
        WardCode = wPanchayatID
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    saveData()
                    val intent = Intent(this, CollProgTrackerSecond::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollProgTrackerListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.img_back -> {
                val intent = Intent(this, CollProgTrackerListActivity::class.java)
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


    fun fillSpinner() {
        cplist = collectiveViewModel.getCollectiveDataByCollectiveGuid(collectiveGuid)
        if (cplist != null && cplist!!.size > 0) {
            cpdistrictId = cplist!!.get(0).DistrictCode
        }
        bindDistrict("Select", spin_districtname)

        spin_districtname.setSelection(returnposDistrict(cpdistrictId))
        validate!!.fillradio(this, rg_registered, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinnerlookup("Select", spin_org, 65, iLanguageID)
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
                    stateCode,
                    list
                )
            }
        } else {

            mstDistrictViewModel.getMstDistrict(stateCode)

        }
        if (it?.isNotEmpty() == true) {
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
                collectiveProgressTrackerViewModel.getMstDist(
                    StateCode,
                    list
                )
            }
        } else {
            collectiveProgressTrackerViewModel.getMstDist(StateCode)
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

    fun returnposDistrict(
        id: Int?
    ): Int {

        var data: List<MstDistrictEntity>? = null
        data = mstDistrictViewModel.getMstDistrict()
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

    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
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
                    stateCode,
                    list
                )
            }
        } else {


            mstDistrictViewModel.getMstDistrict(stateCode)

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
        zonedata = if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(districtCode, list)
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
        data = if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(distCode, list)
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
        data = if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(distCode, list)
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


    fun fillSpinnerlookup(
        strValue: String,
        spin: Spinner,
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

    fun returnID(
        pos: Int,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0)
                id = data.get(pos - 1).LookupCode
        }
        return id
    }


    fun returnpos(
        id: Int?,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).LookupCode)
                        pos = i + 1
                }
            }
        }
        return pos
    }


    fun checkValidation(): Int {

        var iValue = 0
        var iCount = collectiveProgressTrackerViewModel.getCommunityID(et_collcode.text.toString())

        if (et_PersonName.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_PersonName,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_person_filling_the_form),
            )
        } else if (et_Designation.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_Designation,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.designation),
            )
        } else if (et_Date.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_Date,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date),
            )
        } else if (et_shg_cig_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_shg_cig_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_cig_shg_jlg),
            )
        } else if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.district),
            )
        } else if (spin_zone.selectedItemPosition == 0 && lay_NameofZone.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0 && lay_bbmpName.visibility == View.VISIBLE) {

            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.bbmp_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0 && lay_panchayatName.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.panchayat),
            )
        } else if (et_localityname.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_localityname,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.locality),
            )
        } else if (et_collcode.text.toString().length == 0 && et_last_code.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.collective_unique_code),
            )
        } else if (iCount > 0 && iShow == 0) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                getString(R.string.collexists),
            )
        } else if (rg_registered.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_the_collective_registered),
            )
        } else if (et_Regdate.text.toString().length == 0 && lay_Regdate.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_Regdate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_registration),
            )
        } else if (spin_org.selectedItemPosition == 0 && lay_org.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_org,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.with_which_organization_it_was_registered),
            )
        } else if (et_CIGCheifname.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_CIGCheifname,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_cig_shg_jlg_chief),
            )
        }
        return iValue
    }


    fun showLiveData() {
        val cptGuid = validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)
        collectiveProgressTrackerViewModel.getLiveCollProgTrackerdatabyGuid(
            validate!!.returnStringValue(
                cptGuid
            )
        )
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    et_PersonName.setText(validate!!.returnStringValue(it.get(0).FPersonName))
                    et_Designation.setText(validate!!.returnStringValue(it.get(0).Designation))
                    et_Date.setText(validate!!.addDays(it.get(0).Date!!.toInt()))
                    et_shg_cig_name.setText(validate!!.returnStringValue(it.get(0).ShgName))
                    spin_districtname.setSelection(returnposDistrict(it.get(0).DistrictCode))
                    et_localityname.setText(validate!!.returnStringValue(it.get(0).Locality))
                    et_collcode.setText(validate!!.returnStringValue(it.get(0).CollectiveID))
                    validate!!.SetAnswerTypeRadioButton(
                        rg_registered,
                        it[0].Is_Collective_Registered
                    )
                    et_Regdate.setText(validate!!.addDays(it.get(0).Date_Registration!!.toInt()))
                    spin_org.setSelection(returnpos(it.get(0).RegisteredWith, 65, iLanguageID))
                    et_CIGCheifname.setText(validate!!.returnStringValue(it.get(0).ShgChiefName))

                    DistCode = it.get(0).DistrictCode!!
                    ZoneCode = it.get(0).ZoneCode!!
                    WardCode = it.get(0).Panchayat_Ward!!

                    et_last_code.visibility = View.GONE
                    iShow = 1

                }
            })
    }

    fun saveData() {
        var RegDate = 0L
        if (lay_Regdate.visibility == View.VISIBLE) {
            RegDate = validate!!.getDaysfromdates(et_Regdate.text.toString(), 1)
        } else {
            RegDate = 0
        }
        var WardPan = 0
        val District1 = returnDistrictID(spin_districtname.selectedItemPosition, 10)
        val Zone1: Int = returnZoneID(spin_zone.selectedItemPosition, District1)

        if (Zone1 > 0) {
            WardPan = returnWardID(spin_bbmp.selectedItemPosition, Zone1)
        } else {
            WardPan = returnPanchayatID(spin_panchayatname.selectedItemPosition, District1)
        }
        if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID) == "") {
            validate!!.SaveSharepreferenceString(AppSP.CPTGUID, validate!!.random())

            collectiveProgressTrackerViewModel.insert(
                CollectiveProgressTrackerEntity(
                    validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!,
                    et_PersonName.text.toString(),
                    et_Designation.text.toString(),
                    validate!!.getDaysfromdates(et_Date.text.toString(), 1),
                    et_shg_cig_name.text.toString(),
                    et_collcode.text.toString(),
                    stateCode,
                    District1,
                    Zone1,
                    WardPan,
                    "",
                    et_localityname.text.toString(),
                    validate!!.GetAnswerTypeRadioButtonID(rg_registered),
                    RegDate,
                    returnID(spin_org.selectedItemPosition, 65, iLanguageID),
                    et_CIGCheifname.text.toString(),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    -1,
                    -1,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    -1,
                    0,
                    0,
                    -1,
                    0,
                    0,
                    0,
                    0,
                    0,
                    -1,
                    0,
                    "",
                    "",
                    0,
                    0,
                    -1,
                    0,
                    "",
                    0,
                    0,
                    0,
                    0,
                    -1,
                    0,
                    0,
                    0,
                    -1,
                    -1,
                    0,
                    "",
                    0,
                    0,
                    "",
                    0,
                    0,
                    -1,
                    0,
                    -1,
                    0,
                    0,
                    -1,
                    validate!!.RetriveSharepreferenceInt(AppSP.userId),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    0,
                    0,
                    "",
                    "",
                    1
                )
            )
        } else {
            collectiveProgressTrackerViewModel.updatecptFirst(
                validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!,
                et_PersonName.text.toString(),
                et_Designation.text.toString(),
                validate!!.getDaysfromdates(et_Date.text.toString(), 1),
                et_shg_cig_name.text.toString(),
                et_collcode.text.toString(),
                stateCode,
                District1,
                Zone1,
                WardPan,
                "",
                et_localityname.text.toString(),
                validate!!.GetAnswerTypeRadioButtonID(rg_registered),
                RegDate,
                returnID(spin_org.selectedItemPosition, 65, iLanguageID),
                et_CIGCheifname.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                1
            )
        }
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first_basic_info.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_second_member_strength.setBackgroundColor(resources.getColor(R.color.back))
        lay_third_meeting_details.setBackgroundColor(resources.getColor(R.color.back))
        lay_fourth_effect1.setBackgroundColor(resources.getColor(R.color.back))
        lay_fifth_effect2.setBackgroundColor(resources.getColor(R.color.back))
        lay_sixth_sustainability.setBackgroundColor(resources.getColor(R.color.back))
        lay_seventh_access_scheme.setBackgroundColor(resources.getColor(R.color.back))

        lay_first_basic_info.setOnClickListener {
            val intent = Intent(this, CollProgTrackerFirst::class.java)
            startActivity(intent)
            finish()
        }
        lay_second_member_strength.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerSecond::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_third_meeting_details.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerThird::class.java)
                startActivity(intent)
                finish()
            }
        }
        lay_fourth_effect1.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerFourth::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_fifth_effect2.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerFifth::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_sixth_sustainability.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerSixth::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_seventh_access_scheme.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerSeventh::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(0, 500)
        }, 100)
    }
}