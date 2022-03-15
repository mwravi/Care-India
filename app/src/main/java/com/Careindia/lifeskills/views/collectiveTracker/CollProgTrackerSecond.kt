package com.careindia.lifeskills.views.collectiveTracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveProgressTrackerViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.coll_prog_tracker_second.*
import kotlinx.android.synthetic.main.coll_prog_tracker_second.btn_bottom
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*

class CollProgTrackerSecond : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel
    var stateCode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validate = Validate(this)
        setContentView(R.layout.coll_prog_tracker_second)

        validate = Validate(this)
        stateCode = validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
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

        mstLookupViewModel =
            ViewModelProvider(this).get(MstLookupViewModel::class.java)


        collectiveProgressTrackerViewModel =
            ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)

        if (validate!!.RetriveSharepreferenceString(AppSP.CPTGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CPTGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
        initializeController()
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
                    updateCPTSecond()
                    val intent = Intent(this, CollProgTrackerThird::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.btn_prev -> {
                 val intent = Intent(this, CollProgTrackerFirst::class.java)
                 startActivity(intent)
                 finish()
            }
        }
    }

    fun checkValidation(): Int {
        var iValue = 0

        if (et_male.text.toString().length == 0) {
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
        } else if (et_male_joined.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_joined,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male),
            )
        } else if (et_female_joined.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_joined,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female),
            )
        } else if (et_male_left.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_left,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male),
            )
        } else if (et_female_left.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_left,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female),
            )
        } else if (et_meetings_held.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_meetings_held,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_meetings_held_as_per_scheduled),
            )
        } else if (et_male_participate.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_participate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male),
            )
        } else if (et_female_participate.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_participate,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female),
            )
        }
        return iValue
    }

    fun showLiveData() {
        val cptGuid = validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)
        collectiveProgressTrackerViewModel.getLiveCollProgTrackerdatabyGuid(validate!!.returnStringValue(cptGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    setDefBlank(et_male,it[0].NoCollectiveMember_M!!)
                    setDefBlank(et_female,it.get(0).NoCollectiveMember_F!!)
                    setDefBlank(et_male_joined,it.get(0).NoNewMembers_M!!)
                    setDefBlank(et_female_joined,it.get(0).NoNewMembers_F!!)
                    setDefBlank(et_male_left,it.get(0).NoMembersLeft_M!!)
                    setDefBlank(et_female_left,it.get(0).NoMembersLeft_F!!)
                    setDefBlank(et_meetings_held,it.get(0).NoMeetingHeld!!)
                    setDefBlank(et_male_participate,it.get(0).NoMembersMeetingOut_M!!)
                    setDefBlank(et_female_participate,it.get(0).NoMembersMeetingOut_F!!)
                }
            })
    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun updateCPTSecond() {
        collectiveProgressTrackerViewModel.updatecptSecond(
            validate!!.RetriveSharepreferenceString(AppSP.CPTGUID)!!,
            et_male.text.toString(),
            et_female.text.toString(),
            et_male_joined.text.toString(),
            et_female_joined.text.toString(),
            et_male_left.text.toString(),
            et_female_left.text.toString(),
            et_meetings_held.text.toString(),
            et_male_participate.text.toString(),
            et_female_participate.text.toString(),
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
            1
        )
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first_basic_info.setBackgroundColor(resources.getColor(R.color.back))
        lay_second_member_strength.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_third_meeting_details.setBackgroundColor(resources.getColor(R.color.back))
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
            horizontalScroll.smoothScrollBy(400, 0)
        }, 100)
    }

}