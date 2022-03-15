package com.careindia.lifeskills.views.beneficiaryProgressTracker

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.homescreen.SubDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_list.*
import kotlinx.android.synthetic.main.activity_improfile_list.img_add
import kotlinx.android.synthetic.main.activity_improfile_list.rvList
import kotlinx.android.synthetic.main.activity_improfile_list.spin_bbmp
import kotlinx.android.synthetic.main.activity_improfile_list.spin_districtname
import kotlinx.android.synthetic.main.activity_improfile_list.spin_panchayatname
import kotlinx.android.synthetic.main.activity_improfile_list.spin_zone
import kotlinx.android.synthetic.main.activity_improfile_list.tv_NameofZone
import kotlinx.android.synthetic.main.activity_improfile_list.tv_bbmpName
import kotlinx.android.synthetic.main.activity_improfile_list.tv_panchayatName
import kotlinx.android.synthetic.main.activity_psychometric_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class BeneficiaryProgressListActivity : AppCompatActivity() {
    var validate: Validate? = null
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var beneficiaryViewModel: BeneficiaryViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var listim : List<IndividualProfileEntity>?= null
    var iDistrictCode = 0
    var iZoneCode = 0

    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beneficiary_progress_list)
        tv_title.text = resources.getString(R.string.beneficiary_progress_tracker)

        validate = Validate(this)
        mstZoneViewModel =
            ViewModelProvider(this).get(MstZoneViewModel::class.java)
        mstPanchayatWardViewModel =
            ViewModelProvider(this).get(MstPanchayatWardViewModel::class.java)
        mstDistrictViewModel =
            ViewModelProvider(this).get(MstDistrictViewModel::class.java)


        beneficiaryViewModel = ViewModelProvider(this).get(BeneficiaryViewModel::class.java)

        listim = beneficiaryViewModel.getINDIDdata(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())
        var disfind = listim!!.get(0).DistrictCode
        var panfind = listim!!.get(0).Panchayat_Ward
        var zonefind = listim!!.get(0).ZoneCode

        if(disfind == 1){
            tv_NameofZone.visibility = View.VISIBLE
            tv_bbmpName.visibility = View.VISIBLE
            spin_zone.visibility = View.VISIBLE
            spin_bbmp.visibility = View.VISIBLE
            tv_panchayatName.visibility = View.GONE
            spin_panchayatname.visibility = View.GONE
        }else{
            tv_NameofZone.visibility = View.GONE
            tv_bbmpName.visibility = View.GONE
            spin_zone.visibility = View.GONE
            spin_bbmp.visibility = View.GONE
            tv_panchayatName.visibility = View.VISIBLE
            spin_panchayatname.visibility = View.VISIBLE
        }

        spin_districtname.setSelection(
            returnposDistrict(
                disfind
            )
        )
        bindMstZone(resources.getString(R.string.select), spin_zone, disfind)
        spin_zone.setSelection(returnposZone(zonefind, disfind))

        if(zonefind >0){
            bindMstWard(resources.getString(R.string.select), spin_bbmp, zonefind)
            spin_bbmp.setSelection(returnposWard(panfind, zonefind))
            spin_bbmp.isEnabled = false
//            fillRecyclerView(disfind, zonefind, panfind!!, 0)
        }else{
            bindPanchayat(resources.getString(R.string.select), spin_panchayatname, disfind)
            spin_panchayatname.setSelection(returnposPanchayat(panfind, disfind))
            spin_panchayatname.isEnabled = false
//            fillRecyclerView(disfind, 0, 0, panfind!!)
        }

        spin_districtname.isEnabled = false
        spin_zone.isEnabled = false

        bindDistrict(resources.getString(R.string.select), spin_districtname)

//        spin_districtname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0) {
//                    val isUrban = returnUrban_rural(
//                        position,
//                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
//                    )
//                    iDistrictCode = returnDistrictID(
//                        position,
//                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
//                    )
//                    fillRecyclerView(iDistrictCode, 0, 0, 0)
//                    if (isUrban == 1) {
//                        tv_NameofZone.visibility = View.VISIBLE
//                        tv_bbmpName.visibility = View.VISIBLE
//                        spin_zone.visibility = View.VISIBLE
//                        spin_bbmp.visibility = View.VISIBLE
//                        tv_panchayatName.visibility = View.GONE
//                        spin_panchayatname.visibility = View.GONE
//                        spin_panchayatname.setSelection(0)
//                        bindMstZone(resources.getString(R.string.select), spin_zone, iDistrictCode)
//                        spin_zone.setSelection(returnposZone(ZoneCode, DistCode))
//                    } else {
//                        tv_NameofZone.visibility = View.GONE
//                        tv_bbmpName.visibility = View.GONE
//                        spin_zone.visibility = View.GONE
//                        spin_zone.setSelection(0)
//                        spin_bbmp.visibility = View.GONE
//                        spin_bbmp.setSelection(0)
//                        tv_panchayatName.visibility = View.VISIBLE
//                        spin_panchayatname.visibility = View.VISIBLE
//                        bindPanchayat(
//                            resources.getString(R.string.select),
//                            spin_panchayatname,
//                            iDistrictCode
//                        )
//                        spin_panchayatname.setSelection(returnposPanchayat(PanchayatCode, DistCode))
//                    }
//
//
//                } else {
//
//                }
//
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // your code here
//            }
//        }

        img_add.setOnClickListener {
            if (checkValidation() == 1) {

                var disCode = returnDistrictID(
                    spin_districtname.selectedItemPosition, validate!!.RetriveSharepreferenceInt(
                        AppSP.StateCode
                    )
                )
                var zoneCode = returnZoneID(spin_zone.selectedItemPosition, disCode)
                var wardCode = returnWardID(spin_bbmp.selectedItemPosition, zoneCode)
                var panchayatCode =
                    returnPanchayatID(spin_panchayatname.selectedItemPosition, disCode)

                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, disCode)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, zoneCode)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, wardCode)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, panchayatCode)

                validate!!.SaveSharepreferenceString(AppSP.Ben_GUID, "")
                val intent = Intent(this, BeneficiaryProgressDetailActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        img_back.setOnClickListener {
            val intent = Intent(this, SubDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

//        spin_zone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0) {
//                    iZoneCode = returnZoneID(position, iDistrictCode)
//                    bindMstWard(resources.getString(R.string.select), spin_bbmp, iZoneCode)
//                    fillRecyclerView(
//                        iDistrictCode,
//                        iZoneCode,
//                        0, 0
//                    )
//
//                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))
//                } else {
//                    bindMstWard(resources.getString(R.string.select), spin_bbmp, 0)
//                    fillRecyclerView(
//                        iDistrictCode, 0,
//                        0, 0
//                    )
//                }
//
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // your code here
//            }
//        }
//
//        spin_bbmp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0) {
//                    fillRecyclerView(
//                        iDistrictCode,
//                        iZoneCode,
//                        returnWardID(position, iZoneCode), 0
//                    )
//
//                } else {
//                    fillRecyclerView(
//                        iDistrictCode,
//                        iZoneCode,
//                        0, 0
//                    )
//                }
//
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // your code here
//            }
//        }
//
//        spin_panchayatname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0) {
//                    fillRecyclerView(
//                        iDistrictCode,
//                        0,
//                        0,
//                        returnPanchayatID(position, iDistrictCode)
//                    )
//
//                } else {
//                    fillRecyclerView(iDistrictCode, 0, 0, 0)
//
//                }
//
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // your code here
//            }
//        }

        if (validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter) > 0) {

            DistCode = validate!!.RetriveSharepreferenceInt(AppSP.DistrictFilter)

            spin_districtname.setSelection(
                returnposDistrict(
                    DistCode
                )
            )
            if (validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter) > 0) {
                spin_zone.visibility = View.VISIBLE
                ZoneCode = validate!!.RetriveSharepreferenceInt(AppSP.ZoneFilter)
                spin_zone.setSelection(returnposZone(ZoneCode, DistCode))

                if (validate!!.RetriveSharepreferenceInt(AppSP.WardFilter) > 0) {
                    spin_bbmp.visibility = View.VISIBLE
                    WardCode = validate!!.RetriveSharepreferenceInt(AppSP.WardFilter)
                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))

                }
            } else if (validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter) > 0) {
                spin_panchayatname.visibility = View.VISIBLE
                PanchayatCode = validate!!.RetriveSharepreferenceInt(AppSP.PanchayatFilter)
                spin_panchayatname.setSelection(returnposPanchayat(PanchayatCode, DistCode))
            }
        }

//        fillRecyclerView(0, 0, 0, 0)
        fillRecyclerViewUpdate(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())
    }


    private fun fillRecyclerViewUpdate(indv_Guid:String){
        rvList.layoutManager = LinearLayoutManager(this)
        beneficiaryViewModel.getBeneficiaryList(indv_Guid).observe(this, Observer {
            rvList.adapter = BeneficiaryProgressAdapter(
                it,
                { selectedItem: BeneficiaryEntity -> onItemClicked(selectedItem) },
                { deletedItem: BeneficiaryEntity -> onItemDeleted(deletedItem) },
                { infoItem: BeneficiaryEntity -> onItemInfo(infoItem) },
                validate!!,
                this
            )
        })
    }

    private fun fillRecyclerView(iDisCode: Int, iZoneCode: Int, WardID: Int, PanchayatID: Int) {
        rvList.layoutManager = LinearLayoutManager(this)

        if (iDisCode > 0 && PanchayatID > 0) {
            beneficiaryViewModel.getIDPDisData(PanchayatID, iDisCode).observe(this, Observer {
                rvList.adapter = BeneficiaryProgressAdapter(
                    it,
                    { selectedItem: BeneficiaryEntity -> onItemClicked(selectedItem) },
                    { deletedItem: BeneficiaryEntity -> onItemDeleted(deletedItem) },
                    { infoItem: BeneficiaryEntity -> onItemInfo(infoItem) },
                    validate!!,this
                )
            })

        } else if (iDisCode > 0 && iZoneCode > 0 && WardID > 0) {
            beneficiaryViewModel.getIDDisWData(iDisCode, iZoneCode, WardID).observe(this, Observer {
                rvList.adapter = BeneficiaryProgressAdapter(
                    it,
                    { selectedItem: BeneficiaryEntity -> onItemClicked(selectedItem) },
                    { deletedItem: BeneficiaryEntity -> onItemDeleted(deletedItem) },
                    { infoItem: BeneficiaryEntity -> onItemInfo(infoItem) },
                    validate!!,this
                )
            })

        } else if (iDisCode > 0 && iZoneCode > 0) {
            beneficiaryViewModel.getIDZData(iDisCode, iZoneCode).observe(this, Observer {
                rvList.adapter = BeneficiaryProgressAdapter(
                    it,
                    { selectedItem: BeneficiaryEntity -> onItemClicked(selectedItem) },
                    { deletedItem: BeneficiaryEntity -> onItemDeleted(deletedItem) },
                    { infoItem: BeneficiaryEntity -> onItemInfo(infoItem) },
                    validate!!,this
                )
            })
        } else if (iDisCode > 0) {
            beneficiaryViewModel.getIDDistrictData(iDisCode).observe(this, Observer {
                rvList.adapter = BeneficiaryProgressAdapter(
                    it,
                    { selectedItem: BeneficiaryEntity -> onItemClicked(selectedItem) },
                    { deletedItem: BeneficiaryEntity -> onItemDeleted(deletedItem) },
                    { infoItem: BeneficiaryEntity -> onItemInfo(infoItem) },
                    validate!!,this
                )
            })

        } else {
            beneficiaryViewModel.beneficiaryProgressData().observe(this, Observer {
                rvList.adapter = BeneficiaryProgressAdapter(
                    it,
                    { selectedItem: BeneficiaryEntity -> onItemClicked(selectedItem) },
                    { deletedItem: BeneficiaryEntity -> onItemDeleted(deletedItem) },
                    { infoItem: BeneficiaryEntity -> onItemInfo(infoItem) },
                    validate!!,this
                )
            })
        }

    }


    fun checkValidation(): Int {
        var value = 1
        if (spin_districtname.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.district),
            )
            value = 0
        } else if (spin_zone.selectedItemPosition == 0 && spin_zone.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.zone),
            )
            value = 0
        } else if (spin_bbmp.selectedItemPosition == 0 && spin_bbmp.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.bbmp_ward),
            )
            value = 0
        } else if (spin_panchayatname.selectedItemPosition == 0 && spin_panchayatname.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.panchayat),
            )
            value = 0
        }
        return value
    }

    private fun onItemClicked(beneficiarylist: BeneficiaryEntity) {

        validate!!.SaveSharepreferenceString(AppSP.Ben_GUID, beneficiarylist.Bene_GUID)
        val intent = Intent(this, BeneficiaryProgressDetailActivity::class.java)
        startActivity(intent)


    }

    private fun onItemDeleted(beneficiarylist: BeneficiaryEntity) {

        CustomAlert_Delete(beneficiarylist)

    }

    private fun onItemInfo(beneficiarylist: BeneficiaryEntity) {

       // validate!!.CustomAlertRejected(this, beneficiarylist.Remarks)

    }


    fun CustomAlert_Delete(beneficiarylist: BeneficiaryEntity) { // Create custom dialog object
        val dialog = Dialog(this)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.delete_dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams

        val btnyes =
            dialog.findViewById<View>(R.id.btn_yes) as Button
        val btnno =
            dialog.findViewById<View>(R.id.btn_no) as Button

        btnyes.setOnClickListener {
            beneficiaryViewModel.deleteBeneficiary(beneficiarylist)
            dialog.dismiss()
            val intent = Intent(this, BeneficiaryProgressListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        btnno.setOnClickListener {


            dialog.dismiss()
        }

        // Display the dialog
        dialog.show()
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
        data = list?.let { beneficiaryViewModel.getMstDist(StateCode, it) }

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
            data = list.let { beneficiaryViewModel.getMstDist(StateCode, list) }
        } else {

            data = beneficiaryViewModel.getMstDist(StateCode)

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

        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).ZoneCode)
                        pos = i + 1
                }
            }
        }
        Log.i("MyTagTest44", "$pos")
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


    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }


}