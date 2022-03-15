package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
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
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.coll_prog_tracker_sixth.*
import kotlinx.android.synthetic.main.coll_prog_tracker_sixth.btn_bottom
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.img_back
import kotlinx.android.synthetic.main.toolbar_layout.img_setting
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*

class CollProgTrackerSixth : BaseActivity(), View.OnClickListener {

    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_sixth)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        mstLookupViewModel =
            ViewModelProvider(this).get(MstLookupViewModel::class.java)
        collectiveProgressTrackerViewModel =
            ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)

        tv_title.text = resources.getString(R.string.collective_prog_tracker)



        et_purpose1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_purpose2.isEnabled = true
                }else{
                    et_purpose2.isEnabled = false
                    et_purpose2.setText("")
                }
            }
        })

        et_purpose2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length > 0) {
                    et_purpose3.isEnabled = true
                }else{
                    et_purpose3.isEnabled = false
                    et_purpose3.setText("")
                }
            }
        })



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
                    val intent = Intent(this, CollProgTrackerSeventh::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollProgTrackerFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun hideView() {

    }

    fun fillSpinner() {
        fillSpinner(resources.getString(R.string.select), spin_status, 78, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_repayment, 75, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_internalloan, 76, iLanguageID)
        validate!!.fillradio(this, rg_affairs, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_actors, -1, mstLookupViewModel, 3, iLanguageID)
    }

    fun CheckValidation(): Int {
        var iValue = 0
        if (spin_status.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_status,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.group_s_corpus_fund_status),
            )
        } else if (spin_repayment.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_repayment,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.fequency_of_repayment),
            )
        } else if (spin_internalloan.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_internalloan,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.management_of_internal_loan),
            )
        } else if (rg_affairs.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.members_are_self_sustaining_in_managing_the_group_affairs),
            )
        } else if (rg_actors.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.any_exposure_cross_visit_planned_to_other_collectives_enterprises_ecosystem_actors),
            )
        } else if (et_visits_no.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_visits_no,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_visits_planned_executed),
            )
        } else if (et_org_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_org_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_organization_collective_enterprise_to_which_exposure_visit_was_planned),
            )
        } else if (et_male.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male),
            )
        } else if (et_female.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female),
            )
        } else if (et_purpose1.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_purpose1,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.purpose_of_the_visit),
            )
        } else if (et_women_no.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_women_no,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_women_members_able_to_articulated_their_needs_with_ecosystem_actors_post_visit),
            )
        } else if (et_ecosystem_no.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_ecosystem_no,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_ecosystem_actors_enterprises_organizations_provided_platforms_to_women_to_communicate_their_needs_effectively),
            )
        }

        return iValue
    }

    fun fillSpinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
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

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun showLiveData() {
        val cptGuid = validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)
        if (cptGuid != null) {
            collectiveProgressTrackerViewModel.getLiveCollProgTrackerdatabyGuid(cptGuid).observe(
                this, Observer {
                    if (it != null && it.size > 0) {
                        if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                            btn_bottom.visibility = View.GONE
                        }else{
                            btn_bottom.visibility = View.VISIBLE
                        }
                        spin_status.setSelection(
                            returnpos(
                                it.get(0).CorpusFundStatus,
                                mstLookupViewModel,
                                78,
                                iLanguageID
                            )
                        )
                        spin_repayment.setSelection(
                            returnpos(
                                it.get(0).FrequencyRepayment,
                                mstLookupViewModel,
                                75,
                                iLanguageID
                            )
                        )
                        spin_internalloan.setSelection(
                            returnpos(
                                it.get(0).InternalLoanManage,
                                mstLookupViewModel,
                                76,
                                iLanguageID
                            )
                        )
                        validate!!.SetAnswerTypeRadioButton(
                            rg_affairs,
                            it[0].SelfSustaining
                        )
                        validate!!.SetAnswerTypeRadioButton(
                            rg_actors,
                            it[0].ExposureVisit
                        )
                        setDefBlank(et_visits_no, it.get(0).NoVisits!!)
                        et_org_name.setText(it.get(0).OrgName!!)
                        setDefBlank(et_male, it.get(0).NoMembersParticipated_M!!)
                        setDefBlank(et_female, it.get(0).NoMembersParticipated_F!!)
                        validate!!.setAgenda(et_purpose1,et_purpose2,et_purpose3,it.get(0).Purpose.toString())
                        setDefBlank(et_women_no, it.get(0).NoWomenArticulated!!)
                        setDefBlank(et_ecosystem_no, it.get(0).NoEcoSystem!!)

                    }
                }
            )
        }
    }

    fun updateData() {
        collectiveProgressTrackerViewModel.updatecptsixth(
            validate!!.RetriveSharepreferenceString(AppSP.CPTGUID),
            returnID(
                spin_status,
                mstLookupViewModel,
                78,
                iLanguageID
            ),
            returnID(
                spin_repayment,
                mstLookupViewModel,
                75,
                iLanguageID
            ),
            returnID(
                spin_internalloan,
                mstLookupViewModel,
                76,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(rg_affairs),

            validate!!.GetAnswerTypeRadioButtonID(rg_actors),

            validate!!.returnIntegerValue(et_visits_no.text.toString()),
            et_org_name.text.toString(),
            validate!!.returnIntegerValue(et_male.text.toString()),
            validate!!.returnIntegerValue(et_female.text.toString()),
            validate!!.getAgenda(et_purpose1,et_purpose2,et_purpose3),
            validate!!.returnIntegerValue(et_women_no.text.toString()),
            validate!!.returnIntegerValue(et_ecosystem_no.text.toString()),
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
        lay_fourth_effect1.setBackgroundColor(resources.getColor(R.color.back))
        lay_fifth_effect2.setBackgroundColor(resources.getColor(R.color.back))
        lay_sixth_sustainability.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
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
            horizontalScroll.smoothScrollBy(2000, 0)
        }, 100)
    }

}