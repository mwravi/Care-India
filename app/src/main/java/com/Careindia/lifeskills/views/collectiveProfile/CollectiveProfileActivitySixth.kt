package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileSixthBinding
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_fifth.*
import kotlinx.android.synthetic.main.activity_collective_profile_sixth.*
import kotlinx.android.synthetic.main.activity_primary_data_first.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import androidx.databinding.BindingAdapter




class CollectiveProfileActivitySixth : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityCollectiveProfileSixthBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var collectiveViewModel: CollectiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_collective_profile_sixth)
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

        var s = GetAnswerTypeCheckBoxButtonIDChange(chk_options_below)
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
                senddata()
                collectiveViewModel.updatecollectiveprofileSix()
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
     validate!!.fillradio(
         rg_meeting_schedule,
         -1,
         mstCommonViewModel,
         24,
         this
     )

     validate!!.fillradio(
         rg_record_book,
         -1,
         mstCommonViewModel,
         25,
         this
     )

     validate!!.fillradio(
         rg_record_book_update,
         -1,
         mstCommonViewModel,
         26,
         this
     )

     validate!!.fillradio(
         rg_services_schemes,
         -1,
         mstCommonViewModel,
         28,
         this
     )

     validate!!.fillradio(
         rg_enterprise_business,
         -1,
         mstCommonViewModel,
         29,
         this
     )


        validate!!.dynamicMultiCheck(this, chk_options_below, mstCommonViewModel,27)




        validate!!.dynamicMultiCheck(this, chk_collective_plan, mstCommonViewModel,30)



    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid)).observe(this, Observer {
            if (it != null && it.size>0) {


                et_other_q501b.setText(validate!!.returnStringValue(it.get(0).Linkage_Services.toString()))
                et_details_service.setText(validate!!.returnStringValue(it.get(0).Services_availed.toString()))
                et_service_provider.setText(validate!!.returnStringValue(it.get(0).Services_dept.toString()))

                (it.get(0).Meeting_Schedule?.let { it1 ->
                    validate!!.SetAnswerTypeRadioButton(rg_meeting_schedule,
                        it1
                    )
                })

                validate!!.SetAnswerTypeRadioButton(rg_record_book,it.get(0).Register_maintained!!)
                validate!!.SetAnswerTypeRadioButton(rg_record_book_update,it.get(0).Register_regular!!)
                validate!!.SetAnswerTypeRadioButton(rg_services_schemes,it.get(0).IsService_availed!!)
                validate!!.SetAnswerTypeRadioButton(rg_enterprise_business,it.get(0).Collective_Schemes!!)

                validate!!.SetAnswerTypeCheckBoxButton(chk_options_below,it.get(0).Linkages)
                validate!!.SetAnswerTypeCheckBoxButton(chk_collective_plan,it.get(0).Collective_opp)

            }
        })
    }

    fun GetAnswerTypeCheckBoxButtonIDChange(linear: LinearLayout): String {
        var QusAns = ""
        for (i in 0 until linear.childCount) {

            val checkbox = linear.getChildAt(i) as CheckBox
            checkbox.setOnClickListener {
                if (checkbox.isChecked) {
                    if (QusAns.length == 0) {
                        QusAns = checkbox.id.toString()
                    } else {
                        QusAns = (QusAns + "," + checkbox.id.toString())

                     //   Log.i("MYTAGTWO____-----++", checkbox.id.toString

                        /*for (i in QusAns) {
                            if (i.equals("7")) {
                                break
                            }*/
                       // }
                    }
                }
            }
        }
        return QusAns
    }

    fun senddata() {
        collectiveViewModel.collectDataSix(
            validate!!.GetAnswerTypeRadioButtonID(rg_meeting_schedule),
            validate!!.GetAnswerTypeRadioButtonID(rg_record_book),
            validate!!.GetAnswerTypeRadioButtonID(rg_record_book_update),
            validate!!.GetAnswerTypeRadioButtonID(rg_services_schemes),
            validate!!.GetAnswerTypeRadioButtonID(rg_enterprise_business),
            validate!!.GetAnswerTypeCheckBoxButtonID(chk_options_below),
            validate!!.GetAnswerTypeCheckBoxButtonID(chk_collective_plan),
            )
    }

   /* fun CheckValidation():Int {

        var iValue = 0;
        if (spin_meeting_schedule.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_meeting_schedule,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q404_does_the_meetings_happen_as_per_the_schedule_check_meeting_register),
            )
        }  else if (spin_record_book.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_record_book,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q405_does_your_sangha_group_collective_have_a_record_book_check_for_the_record_book),
            )
        } else if (spin_record_book_update.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_record_book_update,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q406_is_the_record_book_register_updated_in_every_meeting),
            )
        }
        else if (et_other_q501a.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q501a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501a_please_specify_the_others),
            )
        } else if (et_other_q501b.text.toString().length == 0 ) {
           iValue = 1
           validate!!.CustomAlertEdit(
               this,
               et_other_q501b,
               resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501b_what_service_services_you_are_getting_through_this_linkage_more_than_one_response_upto_three_is_possible),
           )
       } else if (spin_services_schemes.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_services_schemes,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q502_is_your_sangha_collective_currently_availing_any_services_schemes),
            )
        }  else if (et_details_service.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_details_service,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502a_please_provide_details_of_the_service_scheme_that_you_are_availing_more_than_one_response_upto_three_is_possible),
            )
        } else if (et_service_provider.text.toString().length == 0 ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_service_provider,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502b_from_which_department_service_provider_you_are_availing_this_scheme_service),
            )
        } else if (spin_enterprise_business.selectedItemPosition == 0) {
           iValue = 1
           validate!!.CustomAlertSpinner(
               this,
               spin_enterprise_business,
               resources.getString(R.string.please_select) + " " + resources.getString(R.string.q601_is_your_sangha_collective_members_interested_in_taking_up_any_collective_enterprise_business),
           )
       } else if (et_others_q602a.text.toString().length == 0 ) {
           iValue = 1
           validate!!.CustomAlertEdit(
               this,
               et_others_q602a,
               resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q602a_please_specify_others),
           )
       }
        return iValue;
    }*/

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}