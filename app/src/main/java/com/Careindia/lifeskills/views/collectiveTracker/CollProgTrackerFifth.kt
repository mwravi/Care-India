package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.coll_prog_tracker_fifth.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*


class CollProgTrackerFifth : BaseActivity(), View.OnClickListener {

    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_fifth)
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
                    val intent = Intent(this, CollProgTrackerSixth::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollProgTrackerFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun hideView() {
        rg_support_job.setOnCheckedChangeListener { radioGroup, i ->

            val ID = validate!!.GetAnswerTypeRadioButtonIDNew(rg_support_job)
            if (ID == 1) {
                lay_from_where_aj.visibility = View.VISIBLE
                lay_kind_of_support.visibility = View.VISIBLE
            } else {
                lay_from_where_aj.visibility = View.GONE
                lay_kind_of_support.visibility = View.GONE
                et_support_taken.setText("")
            }
        }
    }

    fun fillSpinner() {
        fillSpinner(resources.getString(R.string.select), spin_job, 66, iLanguageID)
        validate!!.fillradio(this, rg_support_job, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_support_rec, 73, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_linakage, 77, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_interest, 66, iLanguageID)
        fillSpinner(resources.getString(R.string.select), spin_trained, 66, iLanguageID)
        validate!!.fillradio(this, rg_solveissue, -1, mstLookupViewModel, 3, iLanguageID)
    }

    fun CheckValidation(): Int {
        var iValue = 0
        if (et_federation_no.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_federation_no,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_federations_women_waste_picker_association_bodies_groups_entrepreneuers_formed_for_alternate_income_and_livelihoods),
            )
        } else if (spin_job.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_job,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.members_interested_in_doing_alternative_job_especially_women),
            )
        } else if (rg_support_job.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.any_support_provided_or_initiative_taken_by_collective_taken_for_its_members_to_enter_into_an_alternative_job),
            )
        } else if (spin_support_rec.selectedItemPosition == 0 && lay_from_where_aj.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_support_rec,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.from_where_whom_the_support_was_received),
            )
        } else if (et_support_taken.text.toString().length == 0 && lay_kind_of_support.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_support_taken,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_kind_of_support_initiative_was_taken),
            )
        } else if (spin_linakage.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_linakage,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.any_linkage_established_with_service_providers_like_financial_institutions_employers_government_officials_private_officials_training_organizations_ngo),
            )
        } else if (spin_interest.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_interest,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.post_training_does_the_women_members_shown_interest_in_taking_up_the_leadership_role),
            )
        } else if (et_capacity_no.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_capacity_no,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_capacity_building_trainings_held_for_women_managers),
            )
        } else if (spin_trained.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_trained,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.women_trained_to_manage_the_books_in_collectives_and_bank_transactions),
            )
        } else if (rg_solveissue.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_the_members_of_the_group_gender_sensitive_and_are_able_to_solve_issues_pertaining_it),
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
        var pos = spin.selectedItemPosition
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode
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
                        if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                            btn_bottom.visibility = View.GONE
                        } else {
                            btn_bottom.visibility = View.VISIBLE
                        }
                        setDefBlank(et_federation_no, it.get(0).NoFederation!!)
                        spin_job.setSelection(
                            returnpos(
                                it.get(0).AlternativeJobs,
                                mstLookupViewModel,
                                66,
                                iLanguageID
                            )
                        )
                        validate!!.SetAnswerTypeRadioButton(
                            rg_support_job,
                            it[0].AJInitiative
                        )
                        spin_support_rec.setSelection(
                            returnpos(
                                it.get(0).AJInitiativeReceived,
                                mstLookupViewModel,
                                73,
                                iLanguageID
                            )
                        )
                        et_support_taken.setText(it.get(0).AJInitiativeReceivedKind!!)
                        spin_linakage.setSelection(
                            returnpos(
                                it.get(0).LinkageEstablished,
                                mstLookupViewModel,
                                77,
                                iLanguageID
                            )
                        )
                        spin_interest.setSelection(
                            returnpos(
                                it.get(0).PostTraining,
                                mstLookupViewModel,
                                66,
                                iLanguageID
                            )
                        )
                        setDefBlank(et_capacity_no, it.get(0).NoCapacityBuilding!!)
                        spin_trained.setSelection(
                            returnpos(
                                it.get(0).WomanTrainedBooks,
                                mstLookupViewModel,
                                66,
                                iLanguageID
                            )
                        )
                        validate!!.SetAnswerTypeRadioButton(
                            rg_solveissue,
                            it[0].GenderSensitive
                        )
                    }
                }
            )
        }
    }

    fun updateData() {
        collectiveProgressTrackerViewModel.updatecptfifth(
            validate!!.RetriveSharepreferenceString(AppSP.CPTGUID),
            validate!!.returnIntegerValue(et_federation_no.text.toString()),
            returnID(
                spin_job,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(rg_support_job),
            returnID(
                spin_support_rec,
                mstLookupViewModel,
                73,
                iLanguageID
            ),
            et_support_taken.text.toString(),
            returnID(
                spin_linakage,
                mstLookupViewModel,
                77,
                iLanguageID
            ),
            returnID(
                spin_interest,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            validate!!.returnIntegerValue(et_capacity_no.text.toString()),
            returnID(
                spin_trained,
                mstLookupViewModel,
                66,
                iLanguageID
            ),
            validate!!.GetAnswerTypeRadioButtonID(rg_solveissue),
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
        lay_fifth_effect2.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
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
            horizontalScroll.smoothScrollBy(1500, 0)
        }, 100)
    }

}