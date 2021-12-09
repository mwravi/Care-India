package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFifth : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityCollectiveProfileFifthBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_fifth)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val collectiveRepository = CollectiveRepository(collectivedao!!, mstDistrictDao)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                    CollectiveViewModel::class.java]

        binding.collectiveViewModel = collectiveViewModel
        binding.lifecycleOwner = this
        tv_title.text = getString(R.string.collprofile)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
        initializeController()
        bottomCLick()
    }


    override fun initializeController() {
        applyClickOnView()
        fillspinner()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)

        collectiveViewModel.MemberSaving.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_regular_savings,
                mstLookupViewModel,
                22,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_other_frequency1.visibility = VISIBLE
            } else {
                lay_other_frequency1.visibility = GONE
                et_other_frequency1.setText("")
            }
        })
        collectiveViewModel.Fromwhereloan.observe(this, Observer {
            val lookupCode =
                validate!!.returnLookupCode(spin_avial_loan, mstLookupViewModel, 23, iLanguageID)
            if (lookupCode == 99) {
                lay_other_specify_q309b.visibility = VISIBLE
            } else {
                lay_other_specify_q309b.visibility = GONE
                et_other_specify_q309b.setText("")
            }
        })
        collectiveViewModel.FrequencyMeeting.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_frequency_of_meetings,
                mstLookupViewModel,
                24,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_other_q402a.visibility = VISIBLE
            } else {
                lay_other_q402a.visibility = GONE
                et_other_q402a.setText("")
            }
        })
        collectiveViewModel.Availloan.observe(this, Observer {
            val lookupCode = validate!!.GetAnswerTypeRadioButtonID(rg_easily_avial_loan)
            if (lookupCode == 1) {
                lay_avial_loan.visibility = VISIBLE
                lay_challenges.visibility = GONE
                et_challenges.setText("")
            } else if (lookupCode == 0) {
                lay_avial_loan.visibility = GONE
                lay_other_specify_q309b.visibility = GONE
                lay_challenges.visibility = VISIBLE
                spin_avial_loan.setSelection(0)
                et_other_specify_q309b.setText("")
            } else {
                lay_avial_loan.visibility = GONE
                lay_other_specify_q309b.visibility = GONE
                spin_avial_loan.setSelection(0)
                lay_challenges.visibility = GONE
                et_challenges.setText("")
                et_other_specify_q309b.setText("")
            }
        })

        collectiveViewModel.Meetingconducted.observe(this, Observer {
            val lookupCode = validate!!.GetAnswerTypeRadioButtonID(rg_meeting_conducted)
            if (lookupCode == 1) {
                lay_frequency_of_meetings.visibility = VISIBLE
                lay_attending_meeting.visibility = VISIBLE
                lay_meeting_schedule.visibility = VISIBLE
            } else if (lookupCode == 0) {
                lay_frequency_of_meetings.visibility = GONE
                lay_other_q402a.visibility = GONE
                lay_attending_meeting.visibility = GONE
                lay_meeting_schedule.visibility = GONE
                spin_frequency_of_meetings.setSelection(0)
                spin_attending_meeting.setSelection(0)
                rg_meeting_schedule.clearCheck()
                et_other_q402a.setText("")
            } else {
                lay_frequency_of_meetings.visibility = GONE
                lay_other_q402a.visibility = GONE
                lay_attending_meeting.visibility = GONE
                lay_meeting_schedule.visibility = GONE
                spin_frequency_of_meetings.setSelection(0)
                spin_attending_meeting.setSelection(0)
                rg_meeting_schedule.clearCheck()
                et_other_q402a.setText("")
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    collectiveViewModel.updatecollectiveprofilefive(this)
                    val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun returnID(
        pos: Int,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode
        }
        return id
    }


    fun returnpos(
        id: Int?,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).LookupCode)
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun fillspinner() {

        fillSpinner(resources.getString(R.string.select), spin_regular_savings, 22, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_avial_loan, 23, iLanguageID)
        validate!!.fillradio(this, rg_easily_avial_loan, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_meeting_conducted, -1, mstLookupViewModel, 3, iLanguageID)
        spin_attending_meeting.getItemAtPosition(spin_attending_meeting.selectedItemPosition)
        fillSpinner(
            resources.getString(R.string.select),
            spin_frequency_of_meetings,
            24,
            iLanguageID
        )
        fillSpinner(resources.getString(R.string.select), spin_attending_meeting, 11, iLanguageID)
        validate!!.fillradioNew(
            this,
            rg_meeting_schedule,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )
    }

    fun fillSpinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        mstLookupViewModel!!.getMstLookup(flag, iLanguageID)
            .observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    val iGen = it.size
                    val name = arrayOfNulls<String>(iGen + 1)
                    name[0] = strValue

                    for (i in 0 until it.size) {
                        name[i + 1] = it.get(i).Description
                    }
                    val adapter_category = ArrayAdapter<String>(
                        this,
                        R.layout.my_spinner_space_dashboard, name
                    )
                    adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
                    spin.adapter = adapter_category
                }
            })

    }


    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {

                    spin_regular_savings.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Savings_Regularity.toString()),
                            22, iLanguageID
                        )
                    )
                    et_other_frequency1.setText(it.get(0).Savings_RegularityOther)
                    validate!!.SetAnswerTypeRadioButton(rg_easily_avial_loan, it[0].Loan_Availed)
                    validate!!.SetAnswerTypeRadioButton(rg_meeting_schedule, it[0].Meeting_Schedule)
                    spin_avial_loan.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Loan_Availed_Where.toString()),
                            23, iLanguageID
                        )
                    )
                    et_other_specify_q309b.setText(it.get(0).Loan_Availed_Others)
                    et_challenges.setText(it.get(0).Loan_Challenges)
                    validate!!.SetAnswerTypeRadioButton(
                        rg_meeting_conducted,
                        it.get(0).Meeting_Held
                    )
                    spin_frequency_of_meetings.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Meeting_Freq.toString()),
                            24, iLanguageID
                        )
                    )
                    et_other_q402a.setText(it[0].Meeting_FreqOth)
                    spin_attending_meeting.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Meeting_Regularity.toString()),
                            11, iLanguageID
                        )
                    )
                }
            })
    }

    fun checkValidation(): Int {

        var iValue = 0;
        if (spin_regular_savings.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_regular_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q308_are_the_members_regularly_saving_as_per_the_group_decision),
            )
        } else if (et_other_frequency1.text.toString().length == 0 && lay_other_frequency1.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_frequency1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q308a_please_specify_others),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_easily_avial_loan) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q309_are_members_avail_the_loan),
            )
        } else if (spin_avial_loan.selectedItemPosition == 0 && lay_avial_loan.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_avial_loan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q309a_from_where_the_members_avail_the_loan),
            )
        } else if (et_other_specify_q309b.text.toString().length == 0 && lay_other_specify_q309b.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_specify_q309b,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q309b_please_specify_others),
            )
        } else if (et_challenges.text.toString().length == 0 && lay_challenges.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_challenges,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q310_what_are_the_challenges_in_availing_loan_minimum_1_and_maximum_upto_3_responses_possible),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_meeting_conducted) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q401_are_meetings_conducted_in_your_sangha_group_collective),
            )
        } else if (spin_frequency_of_meetings.selectedItemPosition == 0 && lay_frequency_of_meetings.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_frequency_of_meetings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q402_what_is_the_frequency_of_meetings),
            )
        } else if (et_other_q402a.text.toString().length == 0 && lay_other_q402a.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q402a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q402a_please_specify_others),
            )
        } else if (spin_attending_meeting.selectedItemPosition == 0 && lay_attending_meeting.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_attending_meeting,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q403_regularity_of_members_attending_meeting),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_meeting_schedule) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q404_does_the_meetings_happen_as_per_the_schedule_check_meeting_register),
            )
        }
        return iValue;
    }

    fun bottomCLick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_six.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilefive(this)
                val intent = Intent(this, CollectiveProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        lay_secnd.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilefive(this)
                val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_third.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilefive(this)
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fourth.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilefive(this)
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
        /*  ll_fifth.setOnClickListener {
              val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
              startActivity(intent)
              finish()
          }*/
        ll_six.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilefive(this)
                val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}