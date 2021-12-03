package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectiveProfileFirstBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
     var distric  = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_first)
        validate = Validate(this)
        mstCommonViewModel = ViewModelProviders.of(this).get(MstCommonViewModel::class.java)


        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val collectiveRepository = CollectiveRepository(collectivedao!!,commondao!!)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                CollectiveViewModel::class.java]

        binding.collectiveViewModel = collectiveViewModel
        binding.lifecycleOwner = this

        tv_title.text = "Collective Profile"

        if(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.trim().length>0) {
            showLiveData()
        }

        initializeController()
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
        spin_name_of_crp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                var abc = spin_name_of_crp.selectedItemPosition
                if (abc == 2) {
                    lay_sfcName.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                    collectiveViewModel.saveandUpdateCollectiveProfile()
                    val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                    startActivity(intent)
                    finish()

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
            collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid)).observe(this, Observer {
                if (it != null && it.size>0) {
                    et_date_of_filling.setText(validate!!.returnStringValue(it.get(0).DateForm))

                   // distric=validate!!.returnIntegerValue(it.get(0).DistrictCode)

                    spin_district_name.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).DistrictCode),3))

                    spin_zone_name.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).ZoneCode),4))

                    spin_ward_name.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Panchayat_Ward.toString()),5))

                    spin_panchayat_name.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).PWCode),6))

                    et_locality_name.setText(validate!!.returnStringValue(it.get(0).Localitycode))
                    et_collective_id.setText(validate!!.returnStringValue(it.get(0).CollectiveID))
                    et_group_collective_name.setText(validate!!.returnStringValue(it.get(0).CollectiveName))
                }
            })
    }

    fun hide(){
        var abc = spin_name_of_crp.selectedItemPosition
        if (abc == 2) {
            lay_sfcName.visibility = View.VISIBLE
        }
    }



    fun CheckValidation():Int {
        var iValue = 0;
        if (et_date_of_filling.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_filling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form_profile),
            )
        } else if (spin_name_of_crp.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_name_of_crp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_crp_filling),
            )
        } else if (spin_name_of_supervising.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_name_of_supervising,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_supervising_care_india),
            )
        } else if (spin_district_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_district_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_district),
            )
        }else if (spin_zone_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_zone_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.Name_of_zone),
            )
        } else if (spin_ward_name.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_ward_name,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_bbmp_ward),
            )
        } else if (spin_panchayat_name.selectedItemPosition == 0) {
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
        }else if (et_group_collective_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_group_collective_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_group_collective_sangha),
            )
        }
        return iValue;
    }


    fun fillSpinner(){
        bindCommonTable("Select",spin_name_of_crp,1)
        bindCommonTable("Select",spin_name_of_supervising,2)
        bindCommonTable("Select",spin_district_name,3)
        bindCommonTable("Select",spin_zone_name,4)
        bindCommonTable("Select",spin_ward_name,5)
        bindCommonTable("Select",spin_panchayat_name,6)
    }

    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int) {
        mstCommonViewModel.getMstCommondata(flag).observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    val iGen = it.size
                    val name = arrayOfNulls<String>(iGen + 1)
                    name[0] = strValue

                    for (i in 0 until it.size) {
                        name[i + 1] = it.get(i).value
                    }
                    val adapter_category = ArrayAdapter<String>(
                        this,
                        R.layout.my_spinner_space_dashboard, name
                    )
                    adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                    spin.adapter = adapter_category
                }
            })
        /*if (distric>0) {
            spin_district_name.setSelection(returnpos(distric, 3))
        }*/
    }

    fun returnpos(id: Int,flag: Int): Int {
        val combobox = mstCommonViewModel.getMstCommon(flag)
        var posi = 0
        for (i in 0 until combobox.size) {
            if (id == combobox[i].id) {
                posi = i + 1
            }
        }
        return posi
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}