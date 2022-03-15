package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileListActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.coll_prog_tracker_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollProgTrackerListActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var iDistrictCode = 0
    var iZoneCode = 0

    var DistCode = 0
    var ZoneCode = 0
    var WardCode = 0
    var cuid=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_list)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.collective_prog_tracker)
        collectiveProgressTrackerViewModel = ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)
        validate = Validate(this)
        mstDistrictViewModel = ViewModelProvider(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel = ViewModelProvider(this).get(MstZoneViewModel::class.java)
        mstPanchayatWardViewModel = ViewModelProvider(this).get(MstPanchayatWardViewModel::class.java)
        cuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveUniqueId).toString()
        bindDistrict(resources.getString(R.string.select), spin_districtname)
        setLocation()
        spin_districtname.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val isUrban = returnUrban_rural(
                        position,
                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
                    )
                    iDistrictCode = returnDistrictID(
                        position,
                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
                    )
                    fillRecyclerView(iDistrictCode, 0, 0, 0,cuid)
                    if (isUrban == 1) {
                        tv_NameofZone.visibility = View.VISIBLE
                        tv_bbmpName.visibility = View.VISIBLE
                        spin_zone.visibility = View.VISIBLE
                        spin_bbmp.visibility = View.VISIBLE
                        tv_panchayatName.visibility = View.GONE
                        spin_panchayatname.visibility = View.GONE
                        spin_panchayatname.setSelection(0)
                        bindMstZone(resources.getString(R.string.select), spin_zone, iDistrictCode)
                        spin_zone.setSelection(returnposZone(ZoneCode, iDistrictCode))
                    } else {
                        tv_NameofZone.visibility = View.GONE
                        tv_bbmpName.visibility = View.GONE
                        spin_zone.visibility = View.GONE
                        spin_zone.setSelection(0)
                        spin_bbmp.visibility = View.GONE
                        spin_bbmp.setSelection(0)
                        tv_panchayatName.visibility = View.VISIBLE
                        spin_panchayatname.visibility = View.VISIBLE
                        bindPanchayat(
                            resources.getString(R.string.select),
                            spin_panchayatname,
                            iDistrictCode
                        )

                        spin_panchayatname.setSelection(returnposPanchayat(WardCode, iDistrictCode))

                    }


                } else {
                    fillRecyclerView(0, 0, 0, 0,cuid)
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        })
        btn_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CPTGUID, "")
            val intent = Intent(this, CollProgTrackerFirst::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        spin_zone.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    iZoneCode = returnZoneID(position, iDistrictCode)
                    bindMstWard(resources.getString(R.string.select), spin_bbmp, iZoneCode)
                    fillRecyclerView(
                        iDistrictCode,
                        iZoneCode,
                        0, 0,cuid
                    )

                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))
                } else {
                    bindMstWard(resources.getString(R.string.select), spin_bbmp, 0)
                    fillRecyclerView(
                        iDistrictCode,
                        iZoneCode,
                        0, 0,cuid
                    )
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        spin_bbmp.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    val wardCode = returnWardID(position, iZoneCode)
                    validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, iDistrictCode)
                    validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, iZoneCode)
                    validate!!.SaveSharepreferenceInt(AppSP.WardFilter, wardCode)
                    validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
                    fillRecyclerView(
                        iDistrictCode,
                        iZoneCode,
                        wardCode, 0, cuid
                    )

                } else {
                    fillRecyclerView(
                        iDistrictCode,
                        iZoneCode,
                        0, 0, cuid
                    )
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        spin_panchayatname.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    val panchayatid = returnPanchayatID(position, iDistrictCode)
                    validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, iDistrictCode)
                    validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
                    validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
                    validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, panchayatid)
                    fillRecyclerView(iDistrictCode, 0, 0, panchayatid,cuid)

                } else {
                    fillRecyclerView(iDistrictCode, 0, 0, 0,cuid)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        })

        fillRecyclerView(0, 0, 0, 0,cuid)
    }

    fun fillRecyclerView(iDisCode: Int, iZoneCode: Int, WardID: Int, PanchayatID: Int,cuid:String) {
        if (iDisCode > 0 && iZoneCode > 0 && WardID > 0) {
            collectiveProgressTrackerViewModel?.getCommWData(iDisCode, iZoneCode, WardID,cuid)
                ?.observe(this, Observer {
                    rv_list.layoutManager = LinearLayoutManager(this)
                    if (it != null) {
                        rv_list.adapter = CollProgTrackerAdapter(it, this, validate!!,collectiveProgressTrackerViewModel)
                    }
                })
        } else if (iDisCode > 0 && iZoneCode > 0) {
            collectiveProgressTrackerViewModel.getCommWData(iDisCode, iZoneCode,cuid).observe(this, Observer {

                rv_list.layoutManager = LinearLayoutManager(this)
                if (it != null) {
                    rv_list.adapter = CollProgTrackerAdapter(it, this, validate!!,collectiveProgressTrackerViewModel)
                }
            })
        } else if (iDisCode > 0) {
            collectiveProgressTrackerViewModel.getCommWData(iDisCode,cuid).observe(this, Observer {

                rv_list.layoutManager = LinearLayoutManager(this)
                if (it != null) {
                    rv_list.adapter = CollProgTrackerAdapter(it, this, validate!!,collectiveProgressTrackerViewModel)
                }
            })
        }else if (iDisCode > 0 && PanchayatID > 0) {
            collectiveProgressTrackerViewModel.getCommWData(iDisCode,PanchayatID,cuid).observe(this, Observer {

                rv_list.layoutManager = LinearLayoutManager(this)
                if (it != null) {
                    rv_list.adapter = CollProgTrackerAdapter(it, this, validate!!,collectiveProgressTrackerViewModel)
                }
            })
        } else if (PanchayatID > 0) {
            collectiveProgressTrackerViewModel.getCommWData(PanchayatID,cuid).observe(this, Observer {

                rv_list.layoutManager = LinearLayoutManager(this)
                if (it != null) {
                    rv_list.adapter = CollProgTrackerAdapter(it, this, validate!!,collectiveProgressTrackerViewModel)
                }
            })
        } else{
            collectiveProgressTrackerViewModel.getallCollProgTrackerdata(cuid).observe(this, Observer {

                rv_list.layoutManager = LinearLayoutManager(this)
                if (it != null) {
                    rv_list.adapter = CollProgTrackerAdapter(it, this, validate!!,collectiveProgressTrackerViewModel)
                }
            })
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
        zonedata = if (!list.isNullOrEmpty()) {
            mstZoneViewModel.getMstZone(districtCode, list)
        } else {


            mstZoneViewModel.getMstZone(districtCode)

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
                R.layout.my_spinner_space_psy_layout, name
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
                R.layout.my_spinner_space_psy_layout, name
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
            mstPanchayatWardViewModel.getMstPanchayat(districtCode, list)
        } else {
            mstPanchayatWardViewModel.getMstPanchayat(districtCode)

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
                R.layout.my_spinner_space_psy_layout, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


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

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
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
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    list
                )
            }
        } else {


            mstDistrictViewModel.getMstDistrict(validate!!.RetriveSharepreferenceInt(AppSP.StateCode))

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
                    StateCode,
                    list
                )
            }
        } else {


            mstDistrictViewModel.getMstDistrict(StateCode)

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

        if (it != null) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i).DistrictName
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_psy_layout, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
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

    fun CheckValidation(): Int {
        var iValue = 0

        if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.district),
            )
        } else if (spin_zone.selectedItemPosition == 0 && spin_zone.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.zone),
            )
        } else if (spin_bbmp.selectedItemPosition == 0 && spin_bbmp.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.bbmp_ward),
            )
        } else if (spin_panchayatname.selectedItemPosition == 0 && spin_panchayatname.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.panchayat),
            )
        }


        return iValue
    }

}