package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileFourthBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collective_profile_fourth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivityFourth : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectiveProfileFourthBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_fourth)
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
        collectiveViewModel.Groupsaving.observe(this, Observer {
            val lookupCode =
                validate!!.returnLookupCode(spin_group_savings, mstLookupViewModel, 20, iLanguageID)
            if (lookupCode == 99) {
                lay_other_inr.visibility = VISIBLE
            } else {
                lay_other_inr.visibility = GONE
                et_other_inr.setText("")
            }
        })

        collectiveViewModel.Frequencygroupsaving.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_frequency_group_savings,
                mstLookupViewModel,
                21,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_other_frequency.visibility = VISIBLE
            } else {
                lay_other_frequency.visibility = GONE
                et_other_frequency.setText("")
            }
        })


        rg_office_bearer.setOnCheckedChangeListener { radioGroup, i ->

            val lookupcode = validate!!.GetAnswerTypeRadioButtonIDNew(rg_office_bearer)
            if (lookupcode == 1) {
                lay_bearer_happens.visibility = VISIBLE
            } else {
                lay_bearer_happens.visibility = GONE
                et_bearer_happens.setText("")
            }
        }

        rg_bank_account.setOnCheckedChangeListener { radioGroup, i ->
            val lookupcode = validate!!.GetAnswerTypeRadioButtonIDNew(rg_bank_account)
            if (lookupcode == 0) {
                lay_cbank_account.visibility = VISIBLE
                lay_group_savings.visibility = GONE
                et_other_inr.setText("")
                et_other_frequency.setText("")
                lay_other_inr.visibility = GONE
                lay_frequency_group_savings.visibility = GONE
                lay_other_frequency.visibility = GONE
                spin_group_savings.setSelection(0)
                spin_frequency_group_savings.setSelection(0)
            } else if (lookupcode == 1) {
                lay_group_savings.visibility = VISIBLE
                lay_frequency_group_savings.visibility = VISIBLE
                lay_cbank_account.visibility = GONE
                et_cbank_account.setText("")
            } else {
                lay_cbank_account.visibility = GONE
                lay_group_savings.visibility = GONE
                lay_other_inr.visibility = GONE
                et_other_inr.setText("")
                lay_frequency_group_savings.visibility = GONE
                lay_other_frequency.visibility = GONE
                et_cbank_account.setText("")
                et_other_frequency.setText("")
                spin_group_savings.setSelection(0)
                spin_frequency_group_savings.setSelection(0)
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {

                if (checkValidation() == 0) {
                    collectiveViewModel.updatecollectiveprofilefour(this)
                    val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                    startActivity(intent)
                    finish()
                }
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
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    setDefBlank(et_tenure, it.get(0).Tenure_President!!)
                    setDefBlank(et_bearer_happens, it.get(0).Election_Freq!!)
                    setDefBlank(et_other_inr, it.get(0).Savings_Oth!!)
                    validate!!.SetAnswerTypeRadioButton(rg_rotation_of_roles, it[0].Rolerotation)
                    validate!!.SetAnswerTypeRadioButton(rg_office_bearer, it[0].Elections)
                    validate!!.SetAnswerTypeRadioButton(rg_bank_account, it[0].IsBank)
                    et_cbank_account.setText(validate!!.returnStringValue(it.get(0).Bank_Challenges.toString()))
                    spin_group_savings.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(it.get(0).Savings.toString()),
                            20, iLanguageID
                        )
                    )
                    spin_frequency_group_savings.setSelection(
                        returnpos(
                            validate!!.returnIntegerValue(
                                it.get(0).Savings_Freq.toString()
                            ), 21, iLanguageID
                        )
                    )
                    et_other_frequency.setText(validate!!.returnStringValue(it.get(0).Savings_FreqOth.toString()))
                }
            })
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

        validate!!.fillradio(this, rg_rotation_of_roles, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_office_bearer, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_bank_account, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_group_savings, 20, iLanguageID)
        fillSpinner(
            resources.getString(R.string.select),
            spin_frequency_group_savings,
            21,
            iLanguageID
        )

    }

    fun checkValidation(): Int {

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
        } else if (rg_rotation_of_roles.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,

                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q302_has_there_been_any_rotation_of_the_roles_of_the_member_in_last_one_year),
            )
        } else if (rg_office_bearer.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q303_has_there_been_any_election_for_the_office_bearer),
            )
        } else if (et_bearer_happens.text.toString().length == 0 && lay_bearer_happens.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q304_in_what_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        } else if ((validate!!.returnIntegerValue(et_bearer_happens.text.toString()) < 1 || validate!!.returnIntegerValue(
                et_bearer_happens.text.toString()
            ) > 10) && lay_bearer_happens.visibility == VISIBLE
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_bearer_happens,
                resources.getString(R.string.valid_frequency_the_election_for_office_bearer_happens_in_complete_years),
            )
        } else if (rg_bank_account.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q305_does_your_sangha_have_a_common_group_bank_account),
            )
        } else if (et_cbank_account.text.toString().length == 0 && lay_cbank_account.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_cbank_account,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q305a_what_are_the_challenges_in_opening_bank_account),
            )
        } else if (spin_group_savings.selectedItemPosition == 0 && lay_group_savings.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_group_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q306_how_much_does_each_member_contribute_to_group_savings),
            )
        } else if (et_other_inr.text.toString().length == 0 && lay_other_inr.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_inr,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q306a_please_specify_the_others_in_inr),
            )
        } else if (spin_frequency_group_savings.selectedItemPosition == 0 && lay_frequency_group_savings.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_frequency_group_savings,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q307_in_what_frequency_the_members_contribute_to_the_group_savings),
            )
        } else if (et_other_frequency.text.toString().length == 0 && lay_other_frequency.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_frequency,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q307a_please_specify_others),
            )
        }
        return iValue
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

    fun bottomCLick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_six.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {


            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()

        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {

                val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
        /* ll_fourth.setOnClickListener {
             val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
             startActivity(intent)
             finish()
         }*/
        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_six.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}