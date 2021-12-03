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
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileFifthBinding
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFifth : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityCollectiveProfileFifthBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveViewModel: CollectiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_collective_profile_fifth)
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

        if(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID) !=null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveGUID)!!.trim().length>0) {
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
                collectiveViewModel.updatecollectiveprofilefive()
                val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                startActivity(intent)
                finish()

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

 fun fillspinner(){
bindCommonTable( resources.getString(R.string.select),spin_regular_savings,17)
bindCommonTable( resources.getString(R.string.select),spin_avial_loan,19)
bindCommonTable( resources.getString(R.string.select),spin_easily_avial_loan,20)
bindCommonTable( resources.getString(R.string.select),spin_meeting_conducted,21)
bindCommonTable( resources.getString(R.string.select),spin_frequency_of_meetings,22)
bindCommonTable( resources.getString(R.string.select),spin_attending_meeting,23)
    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid)).observe(this, Observer {
            if (it != null && it.size>0) {

                spin_regular_savings.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(it.get(0).Savings_Regularity.toString()),
                        17
                    )
                )

                spin_avial_loan.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(it.get(0).Loan_Availed.toString()),
                        19
                    )
                )

                et_challenges.setText(validate!!.returnStringValue(it.get(0).Loan_Challenges.toString()))

                spin_meeting_conducted.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(it.get(0).Meeting_Held.toString()),
                        21
                    )
                )

                spin_frequency_of_meetings.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(
                            it.get(
                                0
                            ).Meeting_Freq.toString()
                        ), 22
                    )
                )

                spin_attending_meeting.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(it.get(0).Meeting_Regularity.toString()),
                        23
                    )
                )
            }
            })
    }

    fun CheckValidation():Int {

        var iValue = 0;
         if (spin_regular_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_regular_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q308_are_the_members_regularly_saving_as_per_the_group_decision),
            )
        } else if (et_other_frequency1.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_frequency1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q308a_please_specify_others),
            )
        } else if (spin_easily_avial_loan.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_easily_avial_loan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q309_are_members_avail_the_loan),
            )
        } else if (spin_avial_loan.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_avial_loan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q309a_from_where_the_members_avail_the_loan),
            )
        } else if (et_other_specify_q309b.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_specify_q309b,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q309b_please_specify_others),
            )
        }
        else if (et_challenges.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_challenges,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q310_what_are_the_challenges_in_availing_loan_minimum_1_and_maximum_upto_3_responses_possible),
            )
        } else if (spin_meeting_conducted.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_meeting_conducted,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q401_are_meetings_conducted_in_your_sangha_group_collective),
            )
        } else if (spin_frequency_of_meetings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_frequency_of_meetings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q402_what_is_the_frequency_of_meetings),
            )
        }  else if (et_other_q402a.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q402a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q402a_please_specify_others),
            )
        }  else if (spin_attending_meeting.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_attending_meeting,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q403_regularity_of_members_attending_meeting),
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