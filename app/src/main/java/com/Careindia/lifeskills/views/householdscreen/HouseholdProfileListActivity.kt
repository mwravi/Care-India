package com.careindia.lifeskills.views.householdscreen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
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
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileListBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodel.MstDistrictViewModel
import com.careindia.lifeskills.viewmodel.MstPanchayatWardViewModel
import com.careindia.lifeskills.viewmodel.MstZoneViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.improfile.IMProfileListActivity
import kotlinx.android.synthetic.main.activity_household_profile_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileListActivity : AppCompatActivity() {
    private lateinit var hhbinding: ActivityHouseholdProfileListBinding
    var validate: Validate? = null
    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var iDistrictCode = 0
    var iZoneCode = 0
    var DistCode = 0
    var ZoneCode = 0
    var WardCode = 0
    var isUrban = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hhbinding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_list)
        validate = Validate(this)
        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)
        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)

        val hhProfileDao = CareIndiaApplication.database?.hhProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val householdProfileRepository =
            HouseholdProfileRepository(hhProfileDao!!, mstDistrictDao)

        householdProfileViewModel = ViewModelProvider(
            this,
            HouseholdProfileViewModelFactory(householdProfileRepository)
        )[HouseholdProfileViewModel::class.java]

        hhbinding.lifecycleOwner = this
        bindDistrict(resources.getString(R.string.select), spin_districtname)

        setLocation()


        spin_districtname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    isUrban = returnUrban_rural(
                        position,
                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
                    )
                    iDistrictCode = returnDistrictID(
                        position,
                        validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
                    )
                    fillRecyclerView(iDistrictCode, 0, 0, 0)
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
                    fillRecyclerView(0, 0, 0, 0)
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }




        img_Add.setOnClickListener {

            if (CheckValidation() == 0) {
                validate!!.SaveSharepreferenceString(AppSP.HHGUID, "")
                val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        img_back.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
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
                        0, 0
                    )
                    spin_bbmp.setSelection(returnposWard(WardCode, ZoneCode))
                } else {
//                    bindMstWard(resources.getString(R.string.select), spin_bbmp, 0)
                    fillRecyclerView(
                        iDistrictCode,
                        iZoneCode,
                        0, 0
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
                        wardCode, 0
                    )


                } else {
                    fillRecyclerView(
                        iDistrictCode,
                        iZoneCode,
                        0, 0
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


                    fillRecyclerView(iDistrictCode, 0, 0, returnPanchayatID(position, iDistrictCode))

                } else {
                    fillRecyclerView(iDistrictCode, 0, 0, 0)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })


        tv_title.setText(resources.getString(R.string.household_profile))
        fillRecyclerView(0, 0, 0, 0)
    }


    private fun fillRecyclerView(iDisCode: Int, iZoneCode: Int, WardID: Int, PanchayatID: Int) {
        hhbinding.rvList.layoutManager = LinearLayoutManager(this)

    if (iDisCode > 0 && PanchayatID > 0) {
        householdProfileViewModel.getHHPData(iDisCode, PanchayatID).observe(this, Observer {
            hhbinding.rvList.adapter = HouseholdProfileAdapter(
                it,
                { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) },
                { infoItem: HouseholdProfileEntity -> onItemInfo(infoItem) },
                { itemAddIM: HouseholdProfileEntity -> onitemAddIM(itemAddIM) },
                validate!!
            )
        })

    }else if (iDisCode > 0 && iZoneCode > 0 && WardID > 0) {
            householdProfileViewModel.getHHWData(iDisCode, iZoneCode, WardID)
                .observe(this, Observer {
                    hhbinding.rvList.adapter = HouseholdProfileAdapter(
                        it,
                        { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                        { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) },
                        { infoItem: HouseholdProfileEntity -> onItemInfo(infoItem) },
                        { itemAddIM: HouseholdProfileEntity -> onitemAddIM(itemAddIM) },
                        validate!!
                    )
                })
        } else if (iDisCode > 0 && iZoneCode > 0) {
            householdProfileViewModel.getHHWData(iDisCode, iZoneCode).observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(
                    it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) },
                    { infoItem: HouseholdProfileEntity -> onItemInfo(infoItem) },
                    { itemAddIM: HouseholdProfileEntity -> onitemAddIM(itemAddIM) },
                    validate!!
                )
            })
        } else if (iDisCode > 0) {
            householdProfileViewModel.getHHWData(iDisCode).observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(
                    it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) },
                    { infoItem: HouseholdProfileEntity -> onItemInfo(infoItem) },
                    { itemAddIM: HouseholdProfileEntity -> onitemAddIM(itemAddIM) },
                    validate!!
                )
            })

//        } else if (PanchayatID > 0) {
//            householdProfileViewModel.getHHPData(PanchayatID).observe(this, Observer {
//                hhbinding.rvList.adapter = HouseholdProfileAdapter(
//                    it,
//                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
//                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) },
//                    { infoItem: HouseholdProfileEntity -> onItemInfo(infoItem) },
//                    validate!!
//                )
//            })
        } else {
            householdProfileViewModel.hhData.observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(
                    it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) },
                    { infoItem: HouseholdProfileEntity -> onItemInfo(infoItem) },
                    { itemAddIM: HouseholdProfileEntity -> onitemAddIM(itemAddIM) },
                    validate!!
                )
            })
        }


    }

    private fun onItemClicked(imProfilelist: HouseholdProfileEntity) {

        validate!!.SaveSharepreferenceString(AppSP.HHGUID, imProfilelist.HHGUID)
        validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, imProfilelist.DistrictCode!!)
        validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, imProfilelist.ZoneCode!!)
        validate!!.SaveSharepreferenceInt(AppSP.WardFilter, imProfilelist.Panchayat_Ward!!)
        validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, imProfilelist.Panchayat_Ward)
        val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun onItemDeleted(imProfileList: HouseholdProfileEntity) {
//        if (imProfileList.IsEdited == 0 && imProfileList.Status == 0) {
//            validate!!.CustomAlert(this, resources.getString(R.string.delete_record))
//        } else {
            CustomAlert_Delete(imProfileList.HHGUID)
//        }
    }

    private fun onItemInfo(imProfilelist: HouseholdProfileEntity) {

        validate!!.CustomAlertRejected(this, imProfilelist.Remarks)
    }

    private fun onitemAddIM(imProfilelist: HouseholdProfileEntity) {

        validate!!.SaveSharepreferenceString(AppSP.HHGUID, imProfilelist.HHGUID)
        validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, imProfilelist.DistrictCode!!)
        validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, imProfilelist.ZoneCode!!)
        validate!!.SaveSharepreferenceInt(AppSP.WardFilter, imProfilelist.Panchayat_Ward!!)
        validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, imProfilelist.Panchayat_Ward)
        validate!!.SaveSharepreferenceString(AppSP.IMClick, "HH")
        val intent = Intent(this, IMProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }



//    fun CustomAlert_Delete(HHGUID: String) {
//        val mDialogView =
//            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
//        val mBuilder = AlertDialog.Builder(this)
//            .setView(mDialogView)
//        val mAlertDialog = mBuilder.show()
//        mAlertDialog.setCanceledOnTouchOutside(false)
//
//        mDialogView.btn_yes.setOnClickListener {
//            householdProfileViewModel.deletehh_record(HHGUID)
//            mAlertDialog.dismiss()
//
//            val intent = Intent(this, HouseholdProfileListActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            this.startActivity(intent)
//        }
//        mDialogView.btn_no.setOnClickListener {
//
//            mAlertDialog.dismiss()
//        }
//    }
//


    fun CustomAlert_Delete(HHGUID: String) { // Create custom dialog object
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
            householdProfileViewModel.deletehh_record(HHGUID)
            dialog.dismiss()
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        btnno.setOnClickListener {


            dialog.dismiss()
        }

        // Display the dialog
        dialog.show()
    }



    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
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
            zonedata = list.let { mstZoneViewModel.getMstZone(districtCode, it) }
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
            if(mstWard.size == 0){
                WardCode = 0
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
                    id = data.get(pos - 1).pwcode

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
            data = list.let { householdProfileViewModel.getMstDist(StateCode, it) }
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
            data = list.let { householdProfileViewModel.getMstDist(StateCode, it) }
        } else {

            data = CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                StateCode
            )

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

    fun CheckValidation(): Int {
        var iValue = 0

        if (spin_districtname.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_districtname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.district)
            )
        } else if (spin_zone.selectedItemPosition == 0 && spin_zone.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.zone)
            )
        } else if (spin_bbmp.selectedItemPosition == 0 && spin_bbmp.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bbmp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.bbmp_ward)
            )
        } else if (spin_panchayatname.selectedItemPosition == 0 && spin_panchayatname.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_panchayatname,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.panchayat)
            )
        }


        return iValue
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
            data = list.let {
                CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                    validate!!.RetriveSharepreferenceInt(AppSP.StateCode),
                    it
                )
            }
        } else {
            data = CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
            )
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

}