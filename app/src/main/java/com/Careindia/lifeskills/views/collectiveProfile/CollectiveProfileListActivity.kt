package com.careindia.lifeskills.views.collectiveProfile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectionProfileListBinding
import com.careindia.lifeskills.entity.CollectiveEntity
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
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.adapter.CollectiveProfileAdapter
import com.careindia.lifeskills.views.collectiveTracker.CollProgTrackerListActivity
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingListActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_profile_list.*
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_bbmp
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_districtname
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_panchayatname
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_zone
import kotlinx.android.synthetic.main.activity_collection_profile_list.tv_NameofZone
import kotlinx.android.synthetic.main.activity_collection_profile_list.tv_bbmpName
import kotlinx.android.synthetic.main.activity_collection_profile_list.tv_panchayatName
import kotlinx.android.synthetic.main.activity_household_profile_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileListActivity : AppCompatActivity() {
    var validate: Validate? = null
    private lateinit var listbinding: ActivityCollectionProfileListBinding
    lateinit var collectiveViewModel: CollectiveViewModel
    lateinit var mstDistrictViewModel: MstDistrictViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var iDistrictCode = 0
    var iZoneCode = 0

    var DistCode = 0
    var ZoneCode = 0
    var WardCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding =
            DataBindingUtil.setContentView(this, R.layout.activity_collection_profile_list)
        tv_title.setText(resources.getString(R.string.colective_profile))
        validate = Validate(this)
        mstDistrictViewModel =
            ViewModelProviders.of(this).get(MstDistrictViewModel::class.java)
        mstZoneViewModel =
            ViewModelProviders.of(this).get(MstZoneViewModel::class.java)
        mstPanchayatWardViewModel =
            ViewModelProviders.of(this).get(MstPanchayatWardViewModel::class.java)
        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val collectiveRepository = CollectiveRepository(collectivedao!!, mstDistrictDao)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                    CollectiveViewModel::class.java]

        listbinding.lifecycleOwner = this

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
                    val isUrban = returnUrban_rural(
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



        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
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
                    fillRecyclerView(iDistrictCode, 0, 0, panchayatid)

                } else {
                    fillRecyclerView(iDistrictCode, 0, 0, 0)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        var profileData = CareIndiaApplication.database?.collectiveDao()
            ?.getColldatabyGuid(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)
        var memDataCount = CareIndiaApplication.database?.collectiveMemDao()
            ?.getAllData(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)

        img_add.setOnClickListener {
            if (CheckValidation() == 0) {
                if((profileData.isNullOrEmpty()) || (profileData[0].NoMembers!! > 0 && profileData[0].NoMembers == memDataCount)){
                    validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, "")
                    validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, "")
                    val intent = Intent(this, CollectiveProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    validate!!.CustomAlert(this,resources.getString(R.string.coll_notcomplete_prf))
//                    Toast.makeText(
//                        this,
//                        "You can not add new Profile,please complete the previous profile",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }

            }
        }



        fillRecyclerView(0, 0, 0, 0)

    }

    private fun fillRecyclerView(iDisCode: Int, iZoneCode: Int, WardID: Int, PanchayatID: Int) {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)

     if (iDisCode > 0 && PanchayatID > 0) {
        collectiveViewModel.getCommPData(iDisCode, PanchayatID).observe(this, Observer {

            listbinding.rvList.adapter = validate?.let { it1 ->
                CollectiveProfileAdapter(
                    it,
                    { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                    { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) },
                    { meetingClick: CollectiveEntity -> onmeetingClick(meetingClick) },
                    { infoItem: CollectiveEntity -> onItemInfo(infoItem) },
                    { cptItem: CollectiveEntity -> oncptClick(cptItem) },
                    it1
                )
            }
        })
    }else if (iDisCode > 0 && iZoneCode > 0 && WardID > 0) {
            collectiveViewModel.getCommWData(iDisCode, iZoneCode, WardID).observe(this, Observer {

                listbinding.rvList.adapter = validate?.let { it1 ->
                    CollectiveProfileAdapter(it,
                        { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                        { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) },
                        { meetingClick: CollectiveEntity -> onmeetingClick(meetingClick) },
                        { infoItem: CollectiveEntity -> onItemInfo(infoItem) },
                        { cptItem: CollectiveEntity -> oncptClick(cptItem) },
                        it1
                    )
                }
            })
        } else if (iDisCode > 0 && iZoneCode > 0) {
            collectiveViewModel.getCommWData(iDisCode, iZoneCode).observe(this, Observer {

                listbinding.rvList.adapter = validate?.let { it1 ->
                    CollectiveProfileAdapter(
                        it,
                        { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                        { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) },
                        { meetingClick: CollectiveEntity -> onmeetingClick(meetingClick) },
                        { infoItem: CollectiveEntity -> onItemInfo(infoItem) },
                        { cptItem: CollectiveEntity -> oncptClick(cptItem) },
                        it1
                    )
                }
            })
        } else if (iDisCode > 0) {
            collectiveViewModel.getCommWData(iDisCode).observe(this, Observer {

                listbinding.rvList.adapter = validate?.let { it1 ->
                    CollectiveProfileAdapter(
                        it,
                        { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                        { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) },
                        { meetingClick: CollectiveEntity -> onmeetingClick(meetingClick) },
                        { infoItem: CollectiveEntity -> onItemInfo(infoItem) },
                        { cptItem: CollectiveEntity -> oncptClick(cptItem) },
                        it1
                    )
                }
            })
        } else if (PanchayatID > 0) {
            collectiveViewModel.getCommPData(PanchayatID).observe(this, Observer {

                listbinding.rvList.adapter = validate?.let { it1 ->
                    CollectiveProfileAdapter(
                        it,
                        { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                        { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) },
                        { meetingClick: CollectiveEntity -> onmeetingClick(meetingClick) },
                        { infoItem: CollectiveEntity -> onItemInfo(infoItem) },
                        { cptItem: CollectiveEntity -> oncptClick(cptItem) },
                        it1
                    )
                }
            })
        } else {
            collectiveViewModel.collectiveData.observe(this, Observer {

                listbinding.rvList.adapter = validate?.let { it1 ->
                    CollectiveProfileAdapter(
                        it,
                        { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                        { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) },
                        { meetingClick: CollectiveEntity -> onmeetingClick(meetingClick) },
                        { infoItem: CollectiveEntity -> onItemInfo(infoItem) },
                        { cptItem: CollectiveEntity -> oncptClick(cptItem) },
                        it1
                    )
                }
            })
        }

    }

    private fun onItemClicked(collectivelist: CollectiveEntity) {

        validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, collectivelist.Col_GUID)
        val intent = Intent(this, CollectiveProfileActivity::class.java)
        startActivity(intent)

    }

    private fun onmeetingClick(collectivelist: CollectiveEntity) {

        validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, collectivelist.Col_GUID)
        val intent = Intent(this, CollectiveMeetingListActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(collectivelist: CollectiveEntity) {
//        if (collectivelist.IsEdited == 0 && collectivelist.Status == 0) {
//            validate!!.CustomAlert(this, resources.getString(R.string.delete_record))
//        } else {
            CustomAlert_Delete(collectivelist)
//        }

    }

    private fun onItemInfo(collectivelist: CollectiveEntity) {

        validate!!.CustomAlertRejected(this, collectivelist.Remarks)

    }

    private fun oncptClick(collectivelist: CollectiveEntity) {
        validate!!.SaveSharepreferenceInt(AppSP.DistrictFilter, 0)
        validate!!.SaveSharepreferenceInt(AppSP.ZoneFilter, 0)
        validate!!.SaveSharepreferenceInt(AppSP.WardFilter, 0)
        validate!!.SaveSharepreferenceInt(AppSP.PanchayatFilter, 0)
        validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, collectivelist.Col_GUID)
        validate!!.SaveSharepreferenceString(AppSP.CollectiveUniqueId, collectivelist.CollectiveID)
        val intent = Intent(this, CollProgTrackerListActivity::class.java)
        startActivity(intent)

    }


    fun CustomAlert_Delete(collectivelist: CollectiveEntity) { // Create custom dialog object
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
            collectiveViewModel.deletecollective(collectivelist)
            CareIndiaApplication.database?.collectiveMemDao()
                ?.deleteAllDataByCollProfile(collectivelist.Col_GUID)
            dialog.dismiss()
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        btnno.setOnClickListener {


            dialog.dismiss()
        }

        // Display the dialog
        dialog.show()
    }


//    fun CustomAlert_Delete(collectivelist: CollectiveEntity) {
//        val mDialogView =
//            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
//        val mBuilder = AlertDialog.Builder(this)
//            .setView(mDialogView)
//        val mAlertDialog = mBuilder.show()
//        mAlertDialog.setCanceledOnTouchOutside(false)
//
//        mDialogView.btn_yes.setOnClickListener {
//            collectiveViewModel.deletecollective(collectivelist)
//            CareIndiaApplication.database?.collectiveMemDao()
//                ?.deleteAllDataByCollProfile(collectivelist.Col_GUID)
//            mAlertDialog.dismiss()
//
//            val intent = Intent(this, CollectiveProfileListActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            this.startActivity(intent)
//        }
//        mDialogView.btn_no.setOnClickListener {
//
//            mAlertDialog.dismiss()
//        }
//    }


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