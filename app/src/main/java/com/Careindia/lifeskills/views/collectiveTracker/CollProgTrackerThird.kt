package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.coll_prog_tracker_third.*
import kotlinx.android.synthetic.main.coll_prog_tracker_third.btn_bottom
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*

class CollProgTrackerThird : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_third)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

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

        mstLookupViewModel = ViewModelProvider(this).get(MstLookupViewModel::class.java)

        collectiveProgressTrackerViewModel =
            ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)

        fillSpinner()
        initializeController()

        if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CPTGUID
            )!!.trim().isNotEmpty()
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
                if (checkValidation() == 0) {
                    updateCPTThird()
                    val intent = Intent(this, CollProgTrackerFourth::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollProgTrackerSecond::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun fillSpinner() {
        validate!!.fillradio(this, rg_Action, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_representation, -1, mstLookupViewModel, 3, iLanguageID)
        fillSpinnerlookup("Select", spin_voice, 66, iLanguageID)
        fillSpinnerlookup("Select", spin_elected, 67, iLanguageID)
        fillSpinnerlookup("Select", spin_rotation, 68, iLanguageID)
        fillSpinnerlookup("Select", spin_active, 66, iLanguageID)
        fillSpinnerlookup("Select", spin_book, 69, iLanguageID)
        fillSpinnerlookup("Select", spin_financial, 70, iLanguageID)
        fillSpinnerlookup("Select", spin_manage, 71, iLanguageID)
    }

    fun fillSpinnerlookup(
        strValue: String,
        spin: Spinner,
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

    fun checkValidation(): Int {

        var iValue = 0

        if (rg_Action.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.action_taken_as_decided_in_previous_meeting),
            )
        } else if (rg_representation.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_there_an_equal_representaion_of_men_and_women_in_the_elected_board_ask_if_applicable_to_the_collective),
            )
        } else if (spin_voice.selectedItemPosition == 0) {

            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_voice,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.what_percentage_of_members_able_to_voice_their_opinion_interests),
            )
        } else if (spin_elected.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_elected,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.leaders_are_elected_through),
            )
        } else if (spin_rotation.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_rotation,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.roataion_of_leadership),
            )
        } else if (spin_active.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_active,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.active_participation_of_members_in_group_activities),
            )
        } else if (spin_book.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_book,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.book_keeping_record_maintenance_by_group_members),
            )
        } else if (spin_financial.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_financial,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.financial_literacy),
            )
        } else if (spin_manage.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_manage,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.management_of_group_savings),
            )
        }
        return iValue
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
            if (pos > 0)
                id = data.get(pos - 1).LookupCode
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

    fun showLiveData() {
        val cptGuid = validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)
        collectiveProgressTrackerViewModel.getLiveCollProgTrackerdatabyGuid(
            validate!!.returnStringValue(
                cptGuid
            )
        )
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    validate!!.SetAnswerTypeRadioButton(
                        rg_Action,
                        it[0].ActionTaken
                    )
                    validate!!.SetAnswerTypeRadioButton(
                        rg_representation,
                        it[0].IsEqualRepresentation
                    )
                    spin_voice.setSelection(returnpos(it[0].VoicePercentage, 66, iLanguageID))
                    spin_elected.setSelection(returnpos(it[0].VoicePercentage, 66, iLanguageID))
                    spin_rotation.setSelection(returnpos(it[0].LeadershipRotation, 66, iLanguageID))
                    spin_active.setSelection(returnpos(it[0].ActiveParticipation, 66, iLanguageID))
                    spin_book.setSelection(returnpos(it[0].BookKeeping, 66, iLanguageID))
                    spin_financial.setSelection(returnpos(it[0].FinancialLiteracy, 66, iLanguageID))
                    spin_manage.setSelection(returnpos(it[0].GroupSavingManage, 66, iLanguageID))
                }
            })
    }

    fun updateCPTThird() {
        collectiveProgressTrackerViewModel.updatecptThird(
            validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!,
            validate!!.GetAnswerTypeRadioButtonID(rg_Action),
            validate!!.GetAnswerTypeRadioButtonID(rg_representation),
            returnID(spin_voice.selectedItemPosition, 66, iLanguageID),
            returnID(spin_elected.selectedItemPosition, 67, iLanguageID),
            returnID(spin_rotation.selectedItemPosition, 68, iLanguageID),
            returnID(spin_active.selectedItemPosition, 66, iLanguageID),
            returnID(spin_book.selectedItemPosition, 69, iLanguageID),
            returnID(spin_financial.selectedItemPosition, 70, iLanguageID),
            returnID(spin_manage.selectedItemPosition, 71, iLanguageID),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first_basic_info.setBackgroundColor(resources.getColor(R.color.back))
        lay_second_member_strength.setBackgroundColor(resources.getColor(R.color.back))
        lay_third_meeting_details.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_fourth_effect1.setBackgroundColor(resources.getColor(R.color.back))
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
            horizontalScroll.smoothScrollBy(600, 0)
        }, 100)
    }

}