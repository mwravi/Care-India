package com.careindia.lifeskills.views.beneficiaryProgressTracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.*
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.btn_prev
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.btn_save
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.lay_NameofZone
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.lay_bbmpName
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.lay_panchayatName
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.spin_bbmp
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.spin_panchayatname
import kotlinx.android.synthetic.main.activity_benificiary_progress_details.spin_zone
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class BeneficiaryProgressDetailActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var beneficiaryViewModel: BeneficiaryViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    lateinit var collList: List<CollectiveEntity>
    var listim: List<IndividualProfileEntity>? = null

    var iLanguageID: Int = 0

    var disCode = 0
    var sWardPanchayat = ""
    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var zonCode = 0
    var stateCode = 0
    var PanchayatCode = 0
    var hhGUID = ""
    var formattedDate = ""
    var iShow = 0
    var Flag = 0

    var BPTGUIDShow = ""
    var collGuid = ""
    var collCodeShow = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benificiary_progress_details)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.beneficiary_progress_tracker)


        stateCode = validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
        mstLookupViewModel = ViewModelProvider(this).get(MstLookupViewModel::class.java)

        beneficiaryViewModel = ViewModelProvider(this).get(BeneficiaryViewModel::class.java)
        mstDistrictViewModel =
            ViewModelProvider(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProvider(this).get(MstZoneViewModel::class.java)

        mstPanchayatWardViewModel =
            ViewModelProvider(this).get(MstPanchayatWardViewModel::class.java)

        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val collectiveRepository = CollectiveRepository(collectivedao!!, mstDistrictDao)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                    CollectiveViewModel::class.java]



        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        if (validate!!.RetriveSharepreferenceString(AppSP.Ben_GUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.Ben_GUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        } else {
            setLocation()
        }

        initializeController()
    }


    override fun initializeController() {
        et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
        et_sfc_name.setText(validate!!.RetriveSharepreferenceString(AppSP.FCID_Name))
        et_indv_mem.setText(validate!!.RetriveSharepreferenceString(AppSP.SubDashIMID))
        et_localityname.setText(validate!!.RetriveSharepreferenceString(AppSP.IndvLocality))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }

        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_formfilngjgDate
            )
        }
        et_formfilngjgDate.setText(validate!!.currentdatetimeNew)
        fillSpinnerView()
        applyClickOnView()
        hideShowView()
        spin_districtname.isEnabled = false
        spin_zone.isEnabled = false
        spin_bbmp.isEnabled = false
        spin_panchayatname.isEnabled = false
        et_indv_mem.isEnabled = false


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
                    val isUrban =
                        returnUrban_rural(spin_districtname.selectedItemPosition, stateCode)
                    fillSpinnerCS(spin_sangha_collective, "Select", zonCode, Ward1, isUrban, 0)


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
                    val isUrban =
                        returnUrban_rural(spin_districtname.selectedItemPosition, stateCode)
                    fillSpinnerCS(spin_sangha_collective, "Select", 0, 0, isUrban, PanchayatID)
//                    spin_sangha_collective.setSelection(
//                        returnPosCS(
//                            BPTGUIDShow, isUrban, 0, 0, PanchayatID
//                        )
//                    )

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spin_sangha_collective.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        collGuid = CareIndiaApplication.database!!.collectiveDao()
                            .getCollectiveguid(
                                spin_sangha_collective.getItemAtPosition(position).toString()
                            )

                        if (collGuid.isNotEmpty()) {
                            spin_collective_memberId.setSelection(0)
                            fillSpinnerMem(spin_collective_memberId, "Select")
                        } else {
                            spin_collective_memberId.setSelection(0)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        /*val collProgData = CareIndiaApplication.database!!.collectiveDao().getCollectiveDataByCollectiveGuid(collGuid)
        collProgData.get()*/
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(0)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    saveandUpdate()
                    if (Flag == 1) {
                        val intent = Intent(this, BeneficiaryQuizActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            R.id.btn_prev -> {

                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()

            }
            R.id.img_back -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
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



        spin_collective_sangha.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val lookupCode = validate!!.returnLookupCode(
                        spin_collective_sangha,
                        mstLookupViewModel,
                        79,
                        iLanguageID
                    )
                    if (lookupCode == 99) {
                        lay_collective_sangha.visibility = View.VISIBLE

                    } else {
                        lay_collective_sangha.visibility = View.GONE
                        et_specify_collective_sangha.setText("")
                    }


                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

    }


    fun fillSpinnerView() {
        bindDistrict(resources.getString(R.string.select), spin_districtname)
        fillSpinner(resources.getString(R.string.select), spin_collective_sangha, 79, iLanguageID)

        //  fillSpinner(resources.getString(R.string.select),spin_sangha_collective, 18 , iLanguageID)


    }

    fun fillSpinnerMem(
        spin: Spinner,
        header: String?,
    ) {
        val adapter_category: ArrayAdapter<String?>
        var it: List<CollectiveMemberEntity>? = null
        it = CareIndiaApplication.database!!.collectiveMemDao()
            .getCollectiveDataByCollectiveGuid(collGuid)
        var colldata: String = ""
        if (it.isNotEmpty()) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = header

            for (i in 0 until it.size) {
                var imid = it.get(i).IndvGuid!!
                listim = beneficiaryViewModel.getINDIDdata(imid)
                var indvcode = listim!!.get(0).IndvCode!!

                name[i + 1] = indvcode + " (" + it.get(i).Name + ")"

                colldata = imid + " (" + it.get(i).Name + ")"
            }
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
            spin.setSelection(returnPosBPT(colldata.toString(), it))
        } else {
            val sValue = arrayOfNulls<String>(1)
            sValue[0] = header
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, sValue
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
            spin.setSelection(0)
        }
    }

    fun fillSpinnerCS(
        spin: Spinner,
        header: String?,
        zoneCode: Int,
        ward: Int,
        isUrban: Int,
        Panchayat: Int
    ) {
        // collList=collectiveViewModel.getCollectivedataList(PanchayatCode)
        val adapter_category: ArrayAdapter<String?>
        var it: List<CollectiveEntity>? = null
        if (isUrban == 1) {
            it = CareIndiaApplication.database!!.collectiveDao()
                .getCollectivedataUrbanList(zoneCode, ward)
        } else {
            it = CareIndiaApplication.database!!.collectiveDao().getCollectivedataList(Panchayat)
        }
        if (it.isNotEmpty()) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = header

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i).CollectiveName
            }
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category

            spin_sangha_collective.setSelection(
                returnPosCS(
                    BPTGUIDShow, it
                )
            )

        } else {
//            val sValue = arrayOfNulls<String>(it.size+1)
//            sValue[0] = header
//            adapter_category = ArrayAdapter(
//                this,
//                R.layout.my_spinner_space_dashboard, sValue
//            )
//            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
//            spin.adapter = adapter_category
//            spin_sangha_collective.setSelection(0)

            val sValue = arrayOfNulls<String>(1)
            sValue[0] = header
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, sValue
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
            spin_sangha_collective.setSelection(0)

        }
    }


    fun returnPosCS(
        strValue: String,
        list: List<CollectiveEntity>?,

        ): Int {
        var posi = 0
//        var cscode: List<BeneficiaryEntity>

//        if (DistCode == 1) {
//            cscode = CareIndiaApplication.database!!.beneficiaryDao().getbenidataUrbanList(ZoneCode, WardCode)
//        } else {
//            cscode = CareIndiaApplication.database!!.beneficiaryDao().getbenidataList(PanchayatCode)
//        }

        if (!list.isNullOrEmpty()) {

            for (i in 0 until list.size) {

                if (strValue == list.get(i).CollectiveName) {
                    posi = i + 1
                }
            }

        }

        return posi
    }

    fun returnPosBPT(
        strValue: String,
        list: List<CollectiveMemberEntity>,
    ): Int {
        var posi = 0
//        var bptcode: List<BeneficiaryEntity>


//            bptcode = CareIndiaApplication.database!!.beneficiaryDao().getbenificiaryList()


        if (!list.isNullOrEmpty()) {

            for (i in 0 until list.size) {

                if (strValue == list.get(i).IndvGuid + " (" + list.get(i).Name + ")") {
                    posi = i + 1
                }
            }

        }

        return posi
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
        if (it.isNotEmpty() == true) {
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

    fun fillDataFromIM(IndGUID: String) {


        CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(IndGUID)
            .observe(this, Observer {
                if (it != null && it.size > 0) {

//                    et_beneficiary_name.setText(it.get(0).Name)
//                    et_age.setText(it.get(0).Age.toString())
//                    spin_gender.setSelection(
//                        validate!!.returnpos(
//                            it.get(0).Gender,
//                            mstLookupViewModel,
//                            1,
//                            iLanguageID
//                        )
//                    )
//                    et_contact_no.setText(it.get(0).Contact)
//                    et_community_name.setText(validate!!.returnStringValue(it.get(0).Locality))


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
            if (pos > 0) id = data.get(pos - 1).LookupCode
        }
        return id
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
                CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
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


    fun showLiveData() {
        val Ben_GUID = validate!!.RetriveSharepreferenceString(AppSP.Ben_GUID)
        beneficiaryViewModel.getbeneficiarydatabyGuid(validate!!.returnStringValue(Ben_GUID))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_formfilngjgDate.setText(it.get(0).DateForm?.let { it1 ->
                        validate!!.addDays(
                            it1.toInt()
                        )
                    })
                    et_localityname.setText(validate!!.returnStringValue(it.get(0).Locality))
                    spin_collective_sangha.setSelection(
                        validate!!.returnpos(
                            it.get(0).Role_Collective,
                            mstLookupViewModel,
                            79,
                            iLanguageID
                        )
                    )
                    et_specify_collective_sangha.setText(validate!!.returnStringValue(it.get(0).Role_Collective_Othr))
                    spin_districtname.setSelection(
                        returnposDistrict(
                            it.get(0).DistrictCode
                        )
                    )
//                    collCodeShow = it.get(0).Collective_Code.toString()
                    BPTGUIDShow = it.get(0).Name_Collective.toString()
                    DistCode = it.get(0).DistrictCode!!
                    ZoneCode = it.get(0).ZoneCode!!
                    WardCode = it.get(0).Panchayat_Ward
                    iShow = 1
                }
            })
    }


    fun saveandUpdate(): Int {

        var Ward1 = 0
        var PWCode = ""
        var isUrban = 0
        var initials = ""
        val District1 = returnDistrictID(
            spin_districtname.selectedItemPosition,
            validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
        )

        val Zone1 = returnZoneID(spin_zone.selectedItemPosition, District1)
        if (Zone1 != null) {
            if (Zone1 > 0) {
                Ward1 = returnWardID(spin_bbmp.selectedItemPosition, Zone1)
                PWCode = "W"
                isUrban = 1
            } else {
                Ward1 = returnPanchayatID(spin_panchayatname.selectedItemPosition, District1)
                PWCode = "P"
                isUrban = 2
            }
        }
        if (validate!!.RetriveSharepreferenceString(AppSP.Ben_GUID) == "") {
            var beneficiaryGuid = validate!!.random()
            validate!!.SaveSharepreferenceString(AppSP.Ben_GUID, beneficiaryGuid)

            beneficiaryViewModel.insert(
                BeneficiaryEntity(
                    beneficiaryGuid,
                    validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID),
                    collGuid,
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    District1,
                    Zone1,
                    Ward1,
                    PWCode,
                    validate!!.getDaysfromdates(et_formfilngjgDate.text.toString(), 1),
                    et_localityname.text.toString(),
                    spin_sangha_collective.getItemAtPosition(spin_sangha_collective.selectedItemPosition)
                        .toString(),
                    returnID(spin_collective_sangha.selectedItemPosition, 79, iLanguageID),
                    et_specify_collective_sangha.text.toString(),
                    validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2), 0, 0, 0, 0, 1

                )
            )

            Flag = 1
        } else if (validate!!.RetriveSharepreferenceString(AppSP.Ben_GUID)!!.length > 0) {

            beneficiaryViewModel.update(
                validate!!.RetriveSharepreferenceString(AppSP.Ben_GUID)!!,
                collGuid,
                validate!!.getDaysfromdates(et_formfilngjgDate.text.toString(), 1),
                validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                District1,
                Zone1,
                Ward1,
                PWCode,
                et_localityname.text.toString(),
                spin_sangha_collective.getItemAtPosition(spin_sangha_collective.selectedItemPosition)
                    .toString(),
                returnID(spin_collective_sangha.selectedItemPosition, 79, iLanguageID),
                et_specify_collective_sangha.text.toString(),
                validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                1
            )
            Flag = 1
        }
        return Flag
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


    fun checkValidation(): Int {

        var iValue = 0
        if (et_formfilngjgDate.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_formfilngjgDate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_filling_the_form),
            )
        } else if (et_crp_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_crp_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_crp_bene),
            )
        } else if (et_sfc_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_sfc_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.Supervising_fc_bene),
            )
        } else if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_district),
            )
        } else if (spin_zone.selectedItemPosition == 0 && lay_NameofZone.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0 && lay_bbmpName.visibility == View.VISIBLE) {

            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0 && lay_panchayatName.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_panchayat),
            )
        } else if (et_localityname.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_localityname,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_thelocality),
            )
        } else if (et_indv_mem.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_indv_mem,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.individual_member_id),
            )
        } else if (spin_sangha_collective.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_sangha_collective,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_the_sangha_collective),
            )
        } else if (spin_collective_memberId.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collective_memberId,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.collective_memberid),
            )
        } else if (spin_collective_sangha.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collective_sangha,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.role_collective_sangha),
            )
        } else if (et_specify_collective_sangha.text.toString().length == 0 && lay_collective_sangha.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_collective_sangha,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specifyothers),
            )
        }
        return iValue
    }

    override fun onBackPressed() {
//        val intent = Intent(this, PrimaryDataListActivity::class.java)
//        startActivity(intent)
//        finish()
    }

}