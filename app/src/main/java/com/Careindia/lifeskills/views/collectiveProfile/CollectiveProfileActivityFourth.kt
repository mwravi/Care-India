package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileFourthBinding
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.activity_collective_profile_third.btn_prev
import kotlinx.android.synthetic.main.activity_collective_profile_third.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFourth : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectiveProfileFourthBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_collective_profile_fourth)
        validate = Validate(this)
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
        applyClickOnView()
        fillspinner()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                collectiveViewModel.updatecollectiveprofilefour()
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid)).observe(this, Observer {
            if (it != null && it.size>0) {

                et_tenure.setText(validate!!.returnStringValue(it.get(0).Tenure_President.toString()))

                spin_office_bearer.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Elections.toString()),13))

                et_bearer_happens.setText(validate!!.returnStringValue(it.get(0).IsBank.toString()))

                spin_bank_account.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).IsBank.toString()),14))

                et_cbank_account.setText(validate!!.returnStringValue(it.get(0).Bank_Challenges.toString()))

                spin_group_savings.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Savings.toString()),15))

                et_other_inr.setText(validate!!.returnStringValue(it.get(0).Savings_Oth.toString()))

                spin_frequency_group_savings.setSelection(returnpos(validate!!.returnIntegerValue(it.get(0).Savings_Freq.toString()),16))

                et_other_frequency.setText(validate!!.returnStringValue(it.get(0).Savings_FreqOth.toString()))
            }
        })
    }

    fun fillspinner() {
   bindCommonTable(resources.getString(R.string.select),spin_rotation_of_roles,12)
   bindCommonTable(resources.getString(R.string.select),spin_office_bearer,13)
   bindCommonTable(resources.getString(R.string.select),spin_bank_account,14)
   bindCommonTable(resources.getString(R.string.select),spin_group_savings,15)
   bindCommonTable(resources.getString(R.string.select),spin_frequency_group_savings,16)

    }

    fun CheckValidation():Int {

        var iValue = 0;
        if (et_tenure.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_tenure,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q301_what_is_the_tenure_of_the_president_office_bearer_in_complete_years),
            )
        } else if (validate!!.returnIntegerValue(et_tenure.text.toString()) < 1 || validate!!.returnIntegerValue(
                et_tenure.text.toString()
            ) > 5
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_tenure,
                resources.getString(R.string.valid_tenure_of_the_president_office_bearer_in_complete_years),
            )
        } else if (spin_rotation_of_roles.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_rotation_of_roles,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q302_has_there_been_any_rotation_of_the_roles_of_the_member_in_last_one_year),
            )
        } else if (spin_office_bearer.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_office_bearer,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q303_has_there_been_any_election_for_the_office_bearer),
            )
        } else if (et_bearer_happens.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q304_in_what_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        } else if (validate!!.returnIntegerValue(et_bearer_happens.text.toString()) < 1 || validate!!.returnIntegerValue(
                et_bearer_happens.text.toString()
            ) > 10
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.valid_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        }
         else if (spin_bank_account.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_bank_account,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q305_does_your_sangha_have_a_common_group_bank_account),
            )
        } else if (et_cbank_account.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_cbank_account,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q305a_what_are_the_challenges_in_opening_bank_account),
            )
        } else if (spin_group_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_group_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q306_how_much_does_each_member_contribute_to_group_savings),
            )
        } else if (et_other_inr.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_inr,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q306a_please_specify_the_others_in_inr),
            )
        } else if (spin_frequency_group_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_frequency_group_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q307_in_what_frequency_the_members_contribute_to_the_group_savings),
            )
        } else if (et_other_frequency.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_frequency,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q307a_please_specify_others),
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