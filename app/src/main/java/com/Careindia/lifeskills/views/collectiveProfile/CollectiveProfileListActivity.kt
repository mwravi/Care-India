package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectionProfileListBinding
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstPanchayatWardViewModel
import com.careindia.lifeskills.viewmodel.MstZoneViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.adapter.CollectiveProfileAdapter
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_profile_list.*
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_bbmp
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_panchayatname
import kotlinx.android.synthetic.main.activity_collection_profile_list.spin_zone
import kotlinx.android.synthetic.main.activity_collection_profile_list.tv_NameofZone
import kotlinx.android.synthetic.main.activity_collection_profile_list.tv_bbmpName
import kotlinx.android.synthetic.main.activity_collection_profile_list.tv_panchayatName
import kotlinx.android.synthetic.main.activity_household_profile_list.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileListActivity : AppCompatActivity() {
    var validate: Validate? = null
    private lateinit var listbinding: ActivityCollectionProfileListBinding
    lateinit var collectiveViewModel: CollectiveViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var iDistrictCode = 0
    var iZoneCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding =
            DataBindingUtil.setContentView(this, R.layout.activity_collection_profile_list)
        tv_title.setText(resources.getString(R.string.colective_profile))
        validate = Validate(this)
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

        val Urban_rural = CareIndiaApplication.database?.mstDistrictDao()!!.getUrban_rural()
        iDistrictCode = CareIndiaApplication.database?.mstDistrictDao()!!.getDisCode()
        if (Urban_rural == 1) {
            tv_NameofZone.visibility = View.VISIBLE
            tv_bbmpName.visibility = View.VISIBLE
            spin_zone.visibility = View.VISIBLE
            spin_bbmp.visibility = View.VISIBLE
            tv_panchayatName.visibility = View.GONE
            spin_panchayatname.visibility = View.GONE
            spin_panchayatname.setSelection(0)
            bindMstZone(resources.getString(R.string.select), spin_zone, iDistrictCode)
        } else {
            tv_NameofZone.visibility = View.GONE
            tv_bbmpName.visibility = View.GONE
            spin_zone.visibility = View.GONE
            spin_zone.setSelection(0)
            spin_bbmp.visibility = View.GONE
            spin_bbmp.setSelection(0)
            tv_panchayatName.visibility = View.VISIBLE
            spin_panchayatname.visibility = View.VISIBLE
            bindPanchayat(resources.getString(R.string.select), spin_panchayatname, iDistrictCode)

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
        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, "")
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, "")
            val intent = Intent(this, CollectiveProfileActivity::class.java)
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
                        iZoneCode,
                        0, 0
                    )
                } else {
                    bindMstWard(resources.getString(R.string.select), spin_bbmp, 0)
                    fillRecyclerView(
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
                    fillRecyclerView(
                        iZoneCode,
                        returnWardID(position, iZoneCode), 0
                    )

                } else {
                    fillRecyclerView(
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
                    fillRecyclerView(0, 0, returnPanchayatID(position, iDistrictCode))

                } else {
                    fillRecyclerView(0, 0, 0)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        fillRecyclerView(0, 0, 0)
    }

    private fun fillRecyclerView(iZoneCode: Int, WardID: Int, PanchayatID: Int) {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)
        if (iZoneCode > 0 && WardID > 0) {
            collectiveViewModel.getCommWData(iZoneCode, WardID).observe(this, Observer {

                listbinding.rvList.adapter = CollectiveProfileAdapter(it,
                    { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                    { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) })
            })
        } else if (iZoneCode > 0) {
            collectiveViewModel.getCommZData(iZoneCode).observe(this, Observer {

                listbinding.rvList.adapter = CollectiveProfileAdapter(it,
                    { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                    { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) })
            })
        } else if (PanchayatID > 0) {
            collectiveViewModel.getCommPData(PanchayatID).observe(this, Observer {

                listbinding.rvList.adapter = CollectiveProfileAdapter(it,
                    { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                    { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) })
            })
        } else {
            collectiveViewModel.collectiveData.observe(this, Observer {

                listbinding.rvList.adapter = CollectiveProfileAdapter(it,
                    { selectedItem: CollectiveEntity -> onItemClicked(selectedItem) },
                    { deletedItem: CollectiveEntity -> onItemDeleted(deletedItem) })
            })
        }

    }

    private fun onItemClicked(collectivelist: CollectiveEntity) {

        validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID, collectivelist.Col_GUID!!)
        val intent = Intent(this, CollectiveProfileActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(collectivelist: CollectiveEntity) {

        CustomAlert_Delete(collectivelist)

    }

    fun CustomAlert_Delete(collectivelist: CollectiveEntity) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            collectiveViewModel.deletecollective(collectivelist)
            mAlertDialog.dismiss()

            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        mDialogView.btn_no.setOnClickListener {

            mAlertDialog.dismiss()
        }
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

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}