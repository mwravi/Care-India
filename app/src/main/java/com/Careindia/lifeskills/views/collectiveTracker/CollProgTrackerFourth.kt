package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveProgressTrackerViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.coll_prog_tracker_fourth.*
import kotlinx.android.synthetic.main.coll_prog_tracker_fourth.btn_bottom
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*

class CollProgTrackerFourth : BaseActivity(), View.OnClickListener {

    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_fourth)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)


        mstLookupViewModel =
            ViewModelProvider(this).get(MstLookupViewModel::class.java)
        collectiveProgressTrackerViewModel =
            ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)

        tv_title.text = resources.getString(R.string.collective_prog_tracker)

        img_back.setOnClickListener {
            val intent = Intent(this, CollProgTrackerListActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        initializeController()
        fillSpinner()
        hideView()
        if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CPTGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }
    }

    override fun initializeController() {
        applyClickOnView()
        topLayClick()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (CheckValidation() == 0) {
                    updateData()
                    val intent = Intent(this, CollProgTrackerFifth::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollProgTrackerThird::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun hideView() {
        rg_support.setOnCheckedChangeListener { radioGroup, i ->

            val ID = validate!!.GetAnswerTypeRadioButtonIDNew(rg_support)
                    if (ID == 1) {
                        lay_support.visibility = View.VISIBLE
                    } else {
                        lay_support.visibility = View.GONE
                        et_supportkind.setText("")
                    }
                }

        spin_support_where.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val ID = returnID(
                        spin_support_where,
                        mstLookupViewModel,
                        73,
                        iLanguageID
                    )
                    if (ID == 99) {
                        lay_et_specify_others.visibility = View.VISIBLE
                    } else {
                        lay_et_specify_others.visibility = View.GONE
                        et_specify_sell_waste_collect.setText("")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }
    }

    fun fillSpinner() {
        validate!!.fillradio(this, rg_lending, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_loans, 66, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_repayment, 72, iLanguageID)
        validate!!.fillradio(this, rg_issues, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_skill, 66, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_skillplus, 66, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_edp, 66, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_collectivization, 66, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_business, 66, iLanguageID)
        validate!!.fillradio(this, rg_support, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_support_where, 73, iLanguageID)
    }

    fun CheckValidation(): Int {
        var iValue = 0

        if (rg_lending.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.does_internal_money_lending_happen_in_the_group),
            )
        } else if (spin_loans.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_loans,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.percentage_of_members_receiving_loans_for_income_generation_activitie),
            )
        } else if (spin_repayment.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_repayment,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.fequency_of_repayment),
            )
        } else if (rg_issues.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.does_the_group_attempt_to_address_the_issues_concerned_to_their_locality),
            )
        } else if (spin_skill.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_skill,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.life_skill),
            )
        } else if (spin_skillplus.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_skillplus,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.life_skill_plus),
            )
        } else if (spin_edp.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_edp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.edp),
            )
        } else if (spin_collectivization.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collectivization,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.collectivization),
            )
        } else if (spin_business.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_business,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.group_memebers_interested_in_starting_an_individual_collective_business),
            )
        } else if (rg_support.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.any_support_provided_or_initiative_taken_by_the_collective_for_its_members_to_start_the_collective_business),
            )
        } else if (spin_support_where.selectedItemPosition == 0 && lay_support.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_support_where,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.from_where_whom_the_support_was_received),
            )
        } else if (et_specify_sell_waste_collect.text.toString().length == 0 && lay_et_specify_others.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_sell_waste_collect,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others_new),
            )
        } else if (et_supportkind.text.toString().length == 0 && lay_support.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_supportkind,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_kind_of_support_initiative_was_taken),
            )
        }

        return iValue
    }

    fun fillSpinner(
        strValue: String, spin: Spinner, flag: Int, iLanguageID: Int
    ) {
        mstLookupViewModel.getMstLookup(flag, iLanguageID)
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

    fun returnID(
        spin: Spinner, mstLookupViewModel: MstLookupViewModel?,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel!!.getLookup(flag, iLanguage)
        var pos = spin.getSelectedItemPosition()
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode!!
        }
        return id
    }


    fun returnpos(
        id: Int?, mstLookupViewModel: MstLookupViewModel,
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

    fun showLiveData() {
        val cptGuid = validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)
        if (cptGuid != null) {
            collectiveProgressTrackerViewModel.getLiveCollProgTrackerdatabyGuid(cptGuid)
                .observe(this,
                    Observer {
                        if (it != null && it.size > 0) {
                            if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                                btn_bottom.visibility = View.GONE
                            }else{
                                btn_bottom.visibility = View.VISIBLE
                            }
                            validate!!.SetAnswerTypeRadioButton(
                                rg_lending,
                                it[0].MoneyLanding
                            )
                            spin_loans.setSelection(
                                returnpos(
                                    it.get(0).ReceivingLoanPercentage,
                                    mstLookupViewModel,
                                    66,
                                    iLanguageID
                                )
                            )
                            spin_repayment.setSelection(
                                returnpos(
                                    it.get(0).RepaymentFreq,
                                    mstLookupViewModel,
                                    72,
                                    iLanguageID
                                )
                            )
                            validate!!.SetAnswerTypeRadioButton(
                                rg_issues,
                                it[0].AddressIssue
                            )
                            spin_skill.setSelection(
                                returnpos(
                                    it.get(0).LifeSkill,
                                    mstLookupViewModel,
                                    66,
                                    iLanguageID
                                )
                            )
                            spin_skillplus.setSelection(
                                returnpos(
                                    it.get(0).LifeSkillPlus,
                                    mstLookupViewModel,
                                    66,
                                    iLanguageID
                                )
                            )
                            spin_edp.setSelection(
                                returnpos(
                                    it.get(0).EDP,
                                    mstLookupViewModel,
                                    66,
                                    iLanguageID
                                )
                            )
                            spin_collectivization.setSelection(
                                returnpos(
                                    it.get(0).Collectivization,
                                    mstLookupViewModel,
                                    66,
                                    iLanguageID
                                )
                            )
                            spin_business.setSelection(
                                returnpos(
                                    it.get(0).StartingBusiness,
                                    mstLookupViewModel,
                                    66,
                                    iLanguageID
                                )
                            )
                            validate!!.SetAnswerTypeRadioButton(
                                rg_support,
                                it[0].BusinessInitiativeTaken
                            )
                            spin_support_where.setSelection(
                                returnpos(
                                    it.get(0).BusinessInitiativeReceived,
                                    mstLookupViewModel,
                                    73,
                                    iLanguageID
                                )
                            )
                            et_specify_sell_waste_collect.setText(it.get(0).BusinessInitiativeOthers!!)
                            et_supportkind.setText(it.get(0).BusinessInitiativeKind!!)
                        }
                    })
        }

    }

    fun updateData() {
        collectiveProgressTrackerViewModel.updatecptfourth(
            validate!!.RetriveSharepreferenceString(AppSP.CPTGUID),
            validate!!.GetAnswerTypeRadioButtonID(rg_lending),
            returnID(
                spin_loans,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            returnID(
                spin_repayment,
                mstLookupViewModel,
                72,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(rg_issues),
            returnID(
                spin_skill,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            returnID(
                spin_skillplus,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            returnID(
                spin_edp,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            returnID(
                spin_collectivization,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            returnID(
                spin_business,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(rg_support),
            returnID(
                spin_support_where,
                mstLookupViewModel,
                73,
                iLanguageID
            ),
            et_specify_sell_waste_collect.text.toString(),
            et_supportkind.text.toString(),
            1,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2)


        )
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first_basic_info.setBackgroundColor(resources.getColor(R.color.back))
        lay_second_member_strength.setBackgroundColor(resources.getColor(R.color.back))
        lay_third_meeting_details.setBackgroundColor(resources.getColor(R.color.back))
        lay_fourth_effect1.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_fifth_effect2.setBackgroundColor(resources.getColor(R.color.back))
        lay_sixth_sustainability.setBackgroundColor(resources.getColor(R.color.back))
        lay_seventh_access_scheme.setBackgroundColor(resources.getColor(R.color.back))

        lay_first_basic_info.setOnClickListener {
            val intent = Intent(this, CollProgTrackerFirst::class.java)
            startActivity(intent)
            finish()
        }
        lay_second_member_strength.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerSecond::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_third_meeting_details.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerThird::class.java)
                startActivity(intent)
                finish()
            }
        }
        lay_fourth_effect1.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerFourth::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_fifth_effect2.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerFifth::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_sixth_sustainability.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerSixth::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_seventh_access_scheme.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!.length > 0) {
                val intent = Intent(this, CollProgTrackerSeventh::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(1000, 0)
        }, 100)
    }
}