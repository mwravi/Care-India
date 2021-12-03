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
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileThirdBinding
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMemberViewModel
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMemberViewModelFactory
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityThird : BaseActivity(), View.OnClickListener {
   private lateinit var binding: ActivityCollectiveProfileThirdBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveMemberViewModel: CollectiveMemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_collective_profile_third)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        val collectiveMemberDao = CareIndiaApplication.database?.collectiveMemDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val collectiveMemberRepository = CollectiveMemberRepository(collectiveMemberDao!!,commondao!!)
        collectiveMemberViewModel =
            ViewModelProvider(this, CollectiveMemberViewModelFactory(collectiveMemberRepository))[
                    CollectiveMemberViewModel::class.java]

        binding.collectiveMemberViewModel = collectiveMemberViewModel
        binding.lifecycleOwner = this
        tv_title.text = "Collective Profile"

        if(validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) !=null && validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)!!.trim().length>0) {
            showLiveData()
        }

        initializeController()
    }

    override fun initializeController() {
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
                collectiveMemberViewModel.savecollectivemember()
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun showLiveData() {
        val collectiveMemGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)
        collectiveMemberViewModel.getCollectiveMemberdatabyGuid(validate!!.returnStringValue(collectiveMemGuid)).observe(this, Observer {
            if (it != null && it.size>0) {

                et_member_name.setText(validate!!.returnStringValue(it.get(0).Name))

                et_member_id.setText(validate!!.returnStringValue(it.get(0).MemberID))

                spin_member_sex.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Gender.toString()),10))

                et_member_age.setText(validate!!.returnStringValue(it.get(0).Age.toString()))

                et_contact_number.setText(validate!!.returnStringValue(it.get(0).Contact))

                spin_savings_account.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Isbank.toString()),11))

                et_aadhar_card.setText(validate!!.returnStringValue(it.get(0).Aadhaar))

            }
        })
    }

    fun fillSpinner(){
       bindCommonTable("Select",spin_member_sex,10)
        bindCommonTable("Select",spin_savings_account,11)

    }

    fun CheckValidation(): Int {

        var iValue = 0;

        if (et_member_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_member),
            )
        } else if (et_member_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.individual_member_id),
            )
        } else if (spin_member_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_member_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_the_member),
            )
        } else if (et_member_age.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.age_of_the_member),
            )
        } else if (validate!!.returnIntegerValue(et_member_age.text.toString()) < 18 || validate!!.returnIntegerValue(
                et_member_age.text.toString()
            ) > 65
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.valid_age_of_the_member),
            )
        } else if (et_contact_number.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_contact_number,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.contact_number),
            )
        } else if (validate!!.checkmobileno(et_contact_number.text.toString()) == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_contact_number,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.valid_mobile_no),
            )
        } else if (et_role_of_member.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_role_of_member,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.role_of_member_in_group),
            )
        } else if (spin_savings_account.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_savings_account,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_savings_bank_account),
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