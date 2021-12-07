package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collective_profile_sixth.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import androidx.databinding.BindingAdapter
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import kotlinx.android.synthetic.main.collectivetab.*


class CollectiveProfileActivitySixth : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityCollectiveProfileSixthBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_sixth)
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
        tv_title.text = "Collective Profile"

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
        collectiveViewModel.RecordBook.observe(this, Observer {
            val lookupCode = validate!!.GetAnswerTypeRadioButtonID(rg_record_book)
            if(lookupCode==1){
                lay_record_book_update.visibility= VISIBLE
            }else{
                lay_record_book_update.visibility= GONE
                rg_record_book_update.clearCheck()
            }
        })
        collectiveViewModel.ServiceScheme.observe(this, Observer {
            val lookupCode = validate!!.GetAnswerTypeRadioButtonID(rg_services_schemes)
            if(lookupCode==1){
                lay_details_service.visibility= VISIBLE
                lay_service_provider.visibility= VISIBLE
            }else{
                lay_details_service.visibility= GONE
                lay_service_provider.visibility= GONE
                et_details_service.setText("")
                et_service_provider.setText("")
            }
        })
        collectiveViewModel.Enterprise.observe(this, Observer {
            val lookupCode = validate!!.GetAnswerTypeRadioButtonID(rg_enterprise_business)
            if(lookupCode==1){
                lay_others_q602.visibility= VISIBLE
            }else{
                lay_others_q602.visibility= GONE
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    senddata()
                    collectiveViewModel.updatecollectiveprofileSix()
                    val intent = Intent(this, CollectiveProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun fillspinner() {


        validate!!.fillradioNew(
            this,
            rg_record_book,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradioNew(
            this,
            rg_record_book_update,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradioNew(
            this,
            rg_services_schemes,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.fillradioNew(
            this,
            rg_enterprise_business,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID
        )

        validate!!.dynamicMultiCheckChange501(
            this,
            chk_options_below,
            mstLookupViewModel,
            26,
            iLanguageID,
            et_other_q501a,
            lay_other_q501a,et_other_q501b,lay_other_q501b
        )

        validate!!.dynamicMultiCheckChange(
            this,
            chk_collective_plan,
            mstLookupViewModel,
            27,
            iLanguageID,
            et_others_q602a,
            lay_others_q602a
        )


    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {

                    validate!!.SetAnswerTypeRadioButton(
                        rg_record_book,
                        it.get(0).Register_maintained!!
                    )
                    validate!!.SetAnswerTypeRadioButton(
                        rg_record_book_update,
                        it.get(0).Register_regular!!
                    )


                    et_other_q501a.setText(it.get(0).Linkages_oth)
                    et_other_q501b.setText(validate!!.returnStringValue(it.get(0).Linkage_Services.toString()))
                    et_details_service.setText(validate!!.returnStringValue(it.get(0).Services_availed.toString()))
                    et_service_provider.setText(validate!!.returnStringValue(it.get(0).Services_dept.toString()))
                    et_others_q602a.setText(it.get(0).Collective_opp_Other)


                    validate!!.SetAnswerTypeRadioButton(
                        rg_services_schemes,
                        it.get(0).IsService_availed!!
                    )
                    validate!!.SetAnswerTypeRadioButton(
                        rg_enterprise_business,
                        it.get(0).Collective_Schemes!!
                    )

                    validate!!.SetAnswerTypeCheckBoxButton(chk_options_below, it.get(0).Linkages)
                    validate!!.SetAnswerTypeCheckBoxButton(
                        chk_collective_plan,
                        it.get(0).Collective_opp
                    )

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
            validate!!.GetAnswerTypeRadioButtonID(rg_record_book_update),
            validate!!.GetAnswerTypeCheckBoxButtonID(chk_options_below),
            validate!!.GetAnswerTypeCheckBoxButtonID(chk_collective_plan),
        )
    }

    fun checkValidation(): Int {

        var iValue = 0;
        if (validate!!.GetAnswerTypeRadioButtonID(rg_record_book) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q405_does_your_sangha_group_collective_have_a_record_book_check_for_the_record_book),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_record_book_update) == -1 && lay_record_book_update.visibility== VISIBLE) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q406_is_the_record_book_register_updated_in_every_meeting),
            )
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(chk_options_below).equals("")) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q501_has_the_sangha_group_collective_established_any_linkage_with_options_below),
            )
        } else if (et_other_q501a.text.toString().length == 0 && lay_other_q501a.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q501a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501a_please_specify_the_others),
            )
        } else if (et_other_q501b.text.toString().length == 0 && lay_other_q501b.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_q501b,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q501b_what_service_services_you_are_getting_through_this_linkage_more_than_one_response_upto_three_is_possible),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_services_schemes) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502_is_your_sangha_collective_currently_availing_any_services_schemes),
            )
        } else if (et_details_service.text.toString().length == 0 && lay_details_service.visibility== VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_details_service,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502a_please_provide_details_of_the_service_scheme_that_you_are_availing_more_than_one_response_upto_three_is_possible),
            )
        } else if (et_service_provider.text.toString().length == 0 && lay_service_provider.visibility== VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_service_provider,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q502b_from_which_department_service_provider_you_are_availing_this_scheme_service),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_enterprise_business) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q601_is_your_sangha_collective_members_interested_in_taking_up_any_collective_enterprise_business),
            )
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(chk_collective_plan).equals("") && lay_others_q602.visibility== VISIBLE) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q602_what_business_would_the_collective_plan_to_do_if_given_an_opportunity),
            )
        } else if (et_others_q602a.text.toString().length == 0 && lay_others_q602a.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_others_q602a,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.q602a_please_specify_others),
            )
        }
        return iValue;
    }

    fun bottomCLick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_six.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

        lay_first.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
            finish()
        }
        ll_third.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityThird::class.java)
            startActivity(intent)
            finish()
        }
        ll_fourth.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
            startActivity(intent)
            finish()
        }
        ll_fifth.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
            startActivity(intent)
            finish()
        }
        /*   ll_six.setOnClickListener {
               val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
               startActivity(intent)
               finish()
           }*/
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}