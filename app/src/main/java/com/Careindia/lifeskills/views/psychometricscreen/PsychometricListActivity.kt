package com.careindia.lifeskills.views.psychometricscreen

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
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPsychometricListBinding
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstDistrictViewModel
import com.careindia.lifeskills.viewmodel.MstPanchayatWardViewModel
import com.careindia.lifeskills.viewmodel.MstZoneViewModel
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.homescreen.SubDashboardActivity

import kotlinx.android.synthetic.main.activity_psychometric_list.*
import kotlinx.android.synthetic.main.activity_psychometric_list.spin_bbmp
import kotlinx.android.synthetic.main.activity_psychometric_list.spin_panchayatname
import kotlinx.android.synthetic.main.activity_psychometric_list.spin_zone
import kotlinx.android.synthetic.main.activity_psychometric_list.tv_NameofZone
import kotlinx.android.synthetic.main.activity_psychometric_list.tv_bbmpName
import kotlinx.android.synthetic.main.activity_psychometric_list.tv_panchayatName

import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricListActivity : AppCompatActivity() {
    private lateinit var listbinding: ActivityPsychometricListBinding
    var validate: Validate? = null
    lateinit var psychometricViewModel: PsychometricViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var listim : List<IndividualProfileEntity>?= null
    var listPsy : List<PsychometricEntity>?= null

    var iDistrictCode = 0
    var iZoneCode = 0

    var ZoneCode = 0
    var DistCode = 0
    var WardCode = 0
    var PanchayatCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_list)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.psychometric)

        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)
        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)

        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val psychometricRepository = PsychometricRepository(psychometricdao!!, mstDistrictDao)

        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(psychometricRepository)
        )[PsychometricViewModel::class.java]

        listbinding.lifecycleOwner = this

        listim = psychometricViewModel.getINDIDdata(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())
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

        listPsy = psychometricViewModel.getPsychodata(validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID).toString())

        if(!listPsy.isNullOrEmpty()) {
            var indvIDfind = listPsy!!.get(0).IMID

            lay_addbtn.visibility = View.GONE
        }else{
            lay_addbtn.visibility = View.VISIBLE
        }



        img_add.setOnClickListener {
            if (checkValidation() == 1) {
                var disCode = returnDistrictID(spin_districtname.selectedItemPosition, validate!!.RetriveSharepreferenceInt(AppSP.StateCode))
                var zoneCode = returnZoneID(spin_zone.selectedItemPosition, disCode)
                var wardCode = returnWardID(spin_bbmp.selectedItemPosition, zoneCode)
                var panchayatCode =
                    returnPanchayatID(spin_panchayatname.selectedItemPosition, disCode)

                validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, disCode)
                validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, zoneCode)
                validate!!.SaveSharepreferenceInt(AppSP.WardFilter, wardCode)
                validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, panchayatCode)

                validate!!.SaveSharepreferenceString(AppSP.PATGUID, "")
                val intent = Intent(this, PsychometricFirstActivity::class.java)
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
//                        10
//                    )
//                    iDistrictCode = returnDistrictID(
//                        position,
//                        10
//                    )
//                    fillRecyclerView(iDistrictCode, 0, 0, 0)
//                    if (isUrban == 1) {
//                        tv_NameofZone.visibility = View.VISIBLE
//                        tv_bbmpName.visibility = View.VISIBLE
//                        spin_zone.visibility = View.VISIBLE
//                        spin_bbmp.visibility = View.VISIBLE
//                        tv_panchayatName.visibility = View.GONE
//                        spin_panchayatname.visibility = View.GONE
////                        spin_panchayatname.setSelection(0)
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
//
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
////                    bindMstWard(resources.getString(R.string.select), spin_bbmp, 0)
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


//    private fun fillRecyclerView() {
//        listbinding.rvList.layoutManager = LinearLayoutManager(this)
//        psychometricViewModel.psychometricData.observe(this, Observer {
//            listbinding.rvList.adapter = PsychometricAdapter(it,
//                { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
//                { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },validate!!)
//        })
//    }

    private fun onItemClicked(psychometriclist: PsychometricEntity) {
        validate!!.SaveSharepreferenceString(AppSP.PATGUID, psychometriclist.PATGUID)
        val intent = Intent(this, PsychometricFirstActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(psychometriclist: PsychometricEntity) {

//        if (psychometriclist.IsEdited == 0 && psychometriclist.Status == 0) {
//            validate!!.CustomAlert(this,resources.getString(R.string.delete_record))
//        } else {
            CustomAlert_Delete(psychometriclist)
//        }

    }

    private fun onItemInfo(psychometriclist: PsychometricEntity) {
        validate!!.CustomAlertRejected(this,psychometriclist.Remarks)
    }

    private fun fillRecyclerViewUpdate(imGUID:String){
        listbinding.rvList.layoutManager = LinearLayoutManager(this)

        psychometricViewModel.getPsychoList(imGUID).observe(this, Observer {
            listbinding.rvList.adapter = PsychometricAdapter(
                it,
                { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },
                { infoItem: PsychometricEntity -> onItemInfo(infoItem) },
                this,
                validate!!
            )
        })
    }

    private fun fillRecyclerView(iDisCode: Int, iZoneCode: Int, WardID: Int, PanchayatID: Int) {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)

        if (iDisCode > 0 && PanchayatID > 0) {
            psychometricViewModel.getIDPDisData(PanchayatID, iDisCode).observe(this, Observer {
                listbinding.rvList.adapter = PsychometricAdapter(
                    it,
                    { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                    { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },
                    { infoItem: PsychometricEntity -> onItemInfo(infoItem) },
                    this,
                    validate!!
                )
            })

        } else if (iDisCode > 0 && iZoneCode > 0 && WardID > 0) {
            psychometricViewModel.getIDDisWData(iDisCode, iZoneCode, WardID)
                .observe(this, Observer {
                    listbinding.rvList.adapter = PsychometricAdapter(
                        it,
                        { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                        { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },
                        { infoItem: PsychometricEntity -> onItemInfo(infoItem) },
                        this,
                        validate!!
                    )
                })

        } else if (iDisCode > 0 && iZoneCode > 0) {
            psychometricViewModel.getIDZData(iDisCode, iZoneCode).observe(this, Observer {
                listbinding.rvList.adapter = PsychometricAdapter(
                    it,
                    { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                    { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },
                    { infoItem: PsychometricEntity -> onItemInfo(infoItem) },
                    this,
                    validate!!
                )
            })
        } else if (iDisCode > 0) {
            psychometricViewModel.getIDDistrictData(iDisCode).observe(this, Observer {
                listbinding.rvList.adapter = PsychometricAdapter(
                    it,
                    { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                    { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },
                    { infoItem: PsychometricEntity -> onItemInfo(infoItem) },
                    this,
                    validate!!
                )
            })

        } else {
            psychometricViewModel.psychometricData.observe(this, Observer {
                listbinding.rvList.adapter = PsychometricAdapter(
                    it,
                    { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                    { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) },
                    { infoItem: PsychometricEntity -> onItemInfo(infoItem) },
                    this,
                    validate!!
                )
            })
        }

    }


//    fun CustomAlert_Delete(psychometriclist: PsychometricEntity) {
//        val mDialogView =
//            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
//        val mBuilder = AlertDialog.Builder(this)
//            .setView(mDialogView)
//        val mAlertDialog = mBuilder.show()
//        mAlertDialog.setCanceledOnTouchOutside(false)
//
//        mDialogView.btn_yes.setOnClickListener {
//            psychometricViewModel.deletepsychometric(psychometriclist)
//            mAlertDialog.dismiss()
//
//            val intent = Intent(this, PsychometricListActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            this.startActivity(intent)
//        }
//        mDialogView.btn_no.setOnClickListener {
//
//            mAlertDialog.dismiss()
//        }
//    }



    fun CustomAlert_Delete(psychometriclist: PsychometricEntity) { // Create custom dialog object
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
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)

        val btnyes =
            dialog.findViewById<View>(R.id.btn_yes) as Button
        val btnno =
            dialog.findViewById<View>(R.id.btn_no) as Button

        btnyes.setOnClickListener {
            psychometricViewModel.deletepsychometric(psychometriclist)
            dialog.dismiss()
            val intent = Intent(this, PsychometricListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        btnno.setOnClickListener {


            dialog.dismiss()
        }

        // Display the dialog
        dialog.show()
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
        if (!list.isNullOrEmpty()) {
            data = list.let { psychometricViewModel.getMstDist(StateCode, it) }
        } else {

            data = CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
            )

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
            data = list.let { psychometricViewModel.getMstDist(StateCode, it) }
        } else {

            data = CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                StateCode
            )

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


    fun checkValidation(): Int {
        var value = 1
        if (spin_districtname.selectedItemPosition == 0) {
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.district)
            )
            value = 0
        } else if (spin_zone.selectedItemPosition == 0 && spin_zone.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.zone)
            )
            value = 0
        } else if (spin_bbmp.selectedItemPosition == 0 && spin_bbmp.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.bbmp_ward)
            )
            value = 0
        } else if (spin_panchayatname.selectedItemPosition == 0 && spin_panchayatname.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.panchayat)
            )
            value = 0
        }
        return value
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
        Log.i("MyTagTest44", "$pos")
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

    fun returnposPanchayat(id: Int?, distCode: Int): Int {
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