package com.careindia.lifeskills.views.collectivemeeting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectivemeetingBinding
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.meetingtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CollectiveMeetingDetailsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCollectivemeetingBinding
    lateinit var collectiveMeetingViewModel: CollectiveMeetingViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    var validate: Validate? = null
    var iLanguageID = 0
    var formattedDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collectivemeeting)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val collectivedao = CareIndiaApplication.database?.collectiveMeetingDao()
        val collectiveRepository = CollectiveMeetingRepository(collectivedao!!)
        collectiveMeetingViewModel =
            ViewModelProvider(this, CollectiveMeetingViewModelFactory(collectiveRepository))[
                    CollectiveMeetingViewModel::class.java]
        binding.collectiveMeetingViewModel = collectiveMeetingViewModel
        binding.lifecycleOwner = this

        tv_title.text = getString(R.string.collmeeting)
        et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
        et_sfc_name.setText(validate!!.RetriveSharepreferenceString(AppSP.FCID_Name))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }
        et_date_of_filling.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.DaybetweentimeBefore(formattedDate),
                et_date_of_filling
            )
        }
        et_date_of_filling.setText(validate!!.currentdatetimeNew)

//        et_date_meeting.setOnClickListener {
//            validate!!.datePickerwithmindate(
//                validate!!.Daybetweentime("01-01-1900"),
//                et_date_meeting
//            )
//        }
        et_date_meeting.setOnClickListener {
            validate!!.datePickerwithmaxdate(
                validate!!.Daybetweentime("01-01-2300"),
                et_date_meeting
            )
        }


        et_date_meeting.setText(validate!!.currentdatetimeNew)

        et_starttime.setOnClickListener {
            validate!!.getTimePicker(
                this,
                et_starttime
            )
        }

        et_endtime.setOnClickListener {
            validate!!.getTimePicker(
                this,
                et_endtime
            )
        }
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingListActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_save.setOnClickListener {

            if (checkValidation() == 0) {
                collectiveMeetingViewModel.saveandUpdateCollectiveProfile(this)
                val intent = Intent(this, CollectiveMeetingAttendanceActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingListActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillspinnner()

        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMeetingGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
        bottomclick()
    }

    override fun initializeController() {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(0)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTag","$formattedDate")

    }

    fun fillspinnner() {

        bindHHIDTable(resources.getString(R.string.select), spin_collective_group)
        spin_collective_group.setSelection(
            returnposHH_GUID(
                validate?.RetriveSharepreferenceString(
                    AppSP.CollectiveGUID
                )
            )
        )
        validate!!.dynamicMultiCheckChange(
            this,
            multiCheck_purpose,
            mstLookupViewModel,
            109,
            iLanguageID,
            et_others_purpose,
            lay_otherspurpose
        )
    }

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        /*  lay_first.setOnClickListener {

              val intent = Intent(this, CollectiveMeetingActivity::class.java)
              startActivity(intent)
              finish()
          }*/
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingAttendanceActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingAgendaActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_fourth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveMeetingCollectionActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


    override fun onBackPressed() {
//        super.onBackPressed()
/*        val intent = Intent(this, CollectiveMeetingListActivity::class.java)
        startActivity(intent)
        finish()*/
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    fun checkValidation(): Int {

        var iValue = 0

        if (et_date_of_filling.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_filling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_filling_the_form_profile),
            )
        } else if (spin_collective_group.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collective_group,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.meetinggroup),
            )
        } else if (et_date_meeting.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_meeting,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.dateofmeeting),
            )
        } else if (et_place.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_place,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.placeofmeeting),
            )
        } else if (et_starttime.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_starttime,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.meetingstarttime),
            )
        } else if (et_endtime.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_endtime,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.meetingendtime),
            )
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_purpose)
                .isEmpty() && lay_purpose_meeting.visibility == View.VISIBLE
        ) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.purposeofmeeting)
            )
            iValue = 1

        } else if (lay_otherspurpose.visibility == View.VISIBLE && et_others_purpose.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_others_purpose,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_otherspurpose),
            )
        }
        return iValue
    }


    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)
        collectiveMeetingViewModel.getCollectivedatabyGuid(
            validate!!.returnStringValue(
                collectiveGuid
            )
        )
            ?.observe(this, Observer {
                if (it != null && it.size > 0) {
                    if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                        btn_bottom.visibility = View.GONE
                    } else {
                        btn_bottom.visibility = View.VISIBLE
                    }

                    et_date_of_filling.setText(it.get(0).Dateform?.let { it1 ->
                        validate!!.addDays(
                            it1.toInt()
                        )
                    })
                    et_date_meeting.setText(it.get(0).Meeting_date?.let { it1 ->
                        validate!!.addDays(
                            it1.toInt()
                        )
                    })
                    spin_collective_group.setSelection(returnposHH_GUID(it.get(0).Col_GUID))
                    et_place.setText(validate!!.returnStringValue(it.get(0).Meeting_place))
                    et_starttime.setText(validate!!.returnStringValue(it.get(0).Meet_start_time))
                    et_endtime.setText(validate!!.returnStringValue(it.get(0).Meet_end_time))
                    validate!!.SetAnswerTypeCheckBoxButton(
                        multiCheck_purpose,
                        it.get(0).Meet_purpose
                    )
                    et_others_purpose.setText(it.get(0).Meet_purpose_oth)


                }
            })
    }

    fun bindHHIDTable(strValue: String, spin: Spinner) {

        //var sWardPanchayat = CareIndiaApplication.database?.hhProfileDao()?.getallHHdata()

        val it = validate?.RetriveSharepreferenceString(AppSP.CollectiveGUID)
            ?.let { CareIndiaApplication.database!!.collectiveDao().getColldatabyGuid(it) }
        if (it != null) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i).CollectiveName
            }
            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }

    fun returnHH_GUID(pos: Int?): String {
        var data: List<CollectiveEntity>? = null
        data =
            CareIndiaApplication.database!!.collectiveDao()
                .getCollectivedata(validate?.RetriveSharepreferenceString(AppSP.CollectiveGUID))

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).Col_GUID

            }
        }
        return id
    }

    fun returnposHH_GUID(
        id: String?
    ): Int {
        var data: List<CollectiveEntity>? = null
        data =
            CareIndiaApplication.database!!.collectiveDao()
                .getCollectivedata(validate?.RetriveSharepreferenceString(AppSP.CollectiveGUID))
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id != null && id.length > 0) {
                for (i in data.indices) {
                    if (id.equals(data.get(i).Col_GUID))
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(0, 500)
        }, 100)
    }
}