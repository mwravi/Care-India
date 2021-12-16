package com.careindia.lifeskills.views.householdscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
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
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodel.MstPanchayatWardViewModel
import com.careindia.lifeskills.viewmodel.MstZoneViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_household_profile_list.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileListActivity : AppCompatActivity() {
    private lateinit var hhbinding: ActivityHouseholdProfileListBinding
    var validate: Validate? = null
    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    lateinit var mstZoneViewModel: MstZoneViewModel
    lateinit var mstPanchayatWardViewModel: MstPanchayatWardViewModel
    var iDistrictCode = 0
    var iZoneCode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hhbinding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_list)
        validate = Validate(this)

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

        img_Add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.HHGUID, "")
            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
            finish()
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


        tv_title.setText(resources.getString(R.string.household_profile))
        fillRecyclerView(0, 0, 0)
    }


    private fun fillRecyclerView(iZoneCode: Int, WardID: Int, PanchayatID: Int) {
        hhbinding.rvList.layoutManager = LinearLayoutManager(this)
        if (iZoneCode > 0 && WardID > 0) {
            householdProfileViewModel.getHHWData(iZoneCode, WardID).observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) })
            })
        } else if (iZoneCode > 0) {
            householdProfileViewModel.getHHZData(iZoneCode).observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) })
            })
        } else if (PanchayatID > 0) {
            householdProfileViewModel.getHHPData(PanchayatID).observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) })
            })
        } else {
            householdProfileViewModel.hhData.observe(this, Observer {
                hhbinding.rvList.adapter = HouseholdProfileAdapter(it,
                    { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                    { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) })
            })
        }


    }

    private fun onItemClicked(imProfilelist: HouseholdProfileEntity) {

        validate!!.SaveSharepreferenceString(AppSP.HHGUID, imProfilelist.HHGUID)
        val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun onItemDeleted(imProfileList: HouseholdProfileEntity) {

        CustomAlert_Delete(imProfileList.HHGUID)

    }

    fun CustomAlert_Delete(HHGUID: String) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            householdProfileViewModel.deletehh_record(HHGUID)
            mAlertDialog.dismiss()

            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        mDialogView.btn_no.setOnClickListener {

            mAlertDialog.dismiss()
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
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

}