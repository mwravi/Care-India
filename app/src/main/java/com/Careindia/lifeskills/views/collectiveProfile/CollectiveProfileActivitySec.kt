package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileSecondBinding
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.activity_collective_profile_second.btn_prev
import kotlinx.android.synthetic.main.activity_collective_profile_second.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivitySec : BaseActivity(), View.OnClickListener {
     private lateinit var binding: ActivityCollectiveProfileSecondBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveViewModel: CollectiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_second)
        validate= Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
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
        et_date_of_group_formation.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_group_formation
            )
        }

        fillSpinner()
        applyClickOnView()

    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                collectiveViewModel.updatecollectiveprofilesecond()
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid)).observe(this, Observer {
            if (it != null && it.size>0) {
                et_date_of_group_formation.setText(validate!!.returnStringValue(it.get(0).Date_formation))

                et_head_group_name.setText(validate!!.returnStringValue(it.get(0).Head_name))

                spin_head_group_sex.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Head_gender.toString()),9))

                et_total_no_of_members.setText(validate!!.returnStringValue(it.get(0).NoMembers.toString()))

                et_male_members.setText(validate!!.returnStringValue(it.get(0).NoMembers_M.toString()))

                et_female_members.setText(validate!!.returnStringValue(it.get(0).NoMembers_F.toString()))

                et_transgender_members.setText(validate!!.returnStringValue(it.get(0).NoMembers_T.toString()))
            }
        })
    }

   fun fillSpinner(){
        validate!!.fillSpinner(
            this,
            spin_collective_group,
            resources.getString(R.string.select),
            mstCommonViewModel,
            7
        )

        validate!!.fillSpinner(
            this,
            spin_group_registered,
            resources.getString(R.string.select),
            mstCommonViewModel,
            8
        )
       bindCommonTable("Select",spin_head_group_sex,9)


    }

    fun CheckValidation():Int {
        var sumofMember = validate!!.returnIntegerValue(et_male_members.text.toString())+validate!!.returnIntegerValue(et_female_members.text.toString())+
        validate!!.returnIntegerValue(et_transgender_members.text.toString())

        var iValue = 0;
         if (et_date_of_group_formation.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_group_formation,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_formation_of_group),
            )
        } else if (spin_collective_group.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collective_group,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q112_what_type_of_collective_group_it_is),
            )
        } else if (et_specify_others_group.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_others_group,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others),
            )
        }else if (spin_group_registered.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_group_registered,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_your_griup_registered),
            )
        } else if (et_specify_others_group_registered.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_others_group_registered,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others_registered),
            )
        } else if (et_head_group_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_head_group_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_head_group),
            )
        } else if (spin_head_group_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_head_group_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_head_of_group),
            )
        }  else if (et_total_no_of_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_no_of_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_members_in_group),
            )
        } else if (et_male_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_male_members_in_group),
            )
        }else if (et_female_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_female_members_in_group),
            )
        } else if (et_transgender_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_transgender_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_transgender_members_in_group),
            )
        }   else if (validate!!.returnIntegerValue(et_total_no_of_members.text.toString())!=sumofMember) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_no_of_members,
                resources.getString(R.string.sum_of_collective_member),
            )
        }
        return iValue;
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