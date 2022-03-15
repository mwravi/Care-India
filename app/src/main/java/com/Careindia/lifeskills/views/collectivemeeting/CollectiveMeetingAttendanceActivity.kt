package com.careindia.lifeskills.views.collectivemeeting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectivemeetingsecBinding
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collectivemeeting.*
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.btn_bottom
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.meetingtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingAttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectivemeetingsecBinding
    lateinit var collectiveMeetingViewModel: CollectiveMeetingViewModel
    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collectivemeetingsec)
        validate = Validate(this)
        val collectivedao = CareIndiaApplication.database?.collectiveMeetingDao()
        val collectiveRepository = CollectiveMeetingRepository(collectivedao!!)
        collectiveMeetingViewModel =
            ViewModelProvider(this, CollectiveMeetingViewModelFactory(collectiveRepository))[
                    CollectiveMeetingViewModel::class.java]
        binding.collectiveMeetingViewModel = collectiveMeetingViewModel
        binding.lifecycleOwner = this
        tv_title.text = getString(R.string.collmeeting)
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
                collectiveMeetingViewModel.UpdateWastePicker(this)
                val intent = Intent(this, CollectiveMeetingAgendaActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }
        bottomclick()
        // setNW_NWP_Data()
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMeetingGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
    }

/*    fun setNW_NWP_Data() {

        val nw_nwp_data = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
            ?.let { collectiveMeetingViewModel.getNW_NWP_data(it) }
        if (nw_nwp_data != null)
            if (nw_nwp_data.get(0).NW_NWP == 1) {
                et_male_wp.setText(nw_nwp_data.get(0).NoMembers_M.toString())
                et_female_wp.setText(nw_nwp_data.get(0).NoMembers_F.toString())
                et_trans_wp.setText(nw_nwp_data.get(0).NoMembers_T.toString())
            } else {
                et_male_nwp.setText(nw_nwp_data.get(0).NoMembers_M.toString())
                et_female_nwp.setText(nw_nwp_data.get(0).NoMembers_F.toString())
                et_trans_nwp.setText(nw_nwp_data.get(0).NoMembers_T.toString())
            }

    }*/

    fun bottomclick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        lay_first.setOnClickListener {

            val intent = Intent(this, CollectiveMeetingDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }
        /*    lay_secnd.setOnClickListener {
                if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)!!.length > 0) {
                    val intent = Intent(this, CollectiveMeetingSecActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }*/
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
        /*    val intent = Intent(this, CollectiveMeetingListActivity::class.java)
            startActivity(intent)
            finish()*/
    }

    fun checkValidation(): Int {

        var iValue = 0

        if (et_male_wp.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_wp,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malewp)
            )
        } else if (et_female_wp.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_wp,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalewp)
            )
        } else if (et_trans_wp.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_wp,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transwp)
            )
        } else if (et_male_nwp.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_nwp,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malenwp),
            )
        } else if (et_female_nwp.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_nwp,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalenwp),
            )
        }else if (et_trans_nwp.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_nwp,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transnwp),
            )

        }else if (et_male_hhm.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_hhm,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malehhm)
            )
        } else if (et_female_hhm.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_hhm,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalehhm)
            )
        } else if (et_trans_hhm.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_hhm,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transhhm)
            )
        } else if (et_male_wp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_wp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malewp),
            )
        } else if (validate!!.returnIntegerValue(et_male_wp_attended.text.toString())>validate!!.returnIntegerValue(et_male_wp.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_wp_attended,
                resources.getString(R.string.attended_male_wp_should_be_less_than_total_male_wp)
            )
        } else if (et_male_nwp.text.toString().length==0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_nwp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malenwp),
            )
        }else if (validate!!.returnIntegerValue(et_male_nwp_attended.text.toString())> validate!!.returnIntegerValue(et_male_nwp.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_nwp_attended,
                resources.getString(R.string.attended_male_nwp_should_be_less_than_total_male_nwp)
            )
        } else if (et_female_wp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_wp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalewp),
            )
        } else if (validate!!.returnIntegerValue(et_female_wp_attended.text.toString())>validate!!.returnIntegerValue(et_female_wp.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_wp_attended,
                resources.getString(R.string.attended_female_wp_should_be_less_than_total_female_wp)
            )
        } else if (et_female_nwp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_nwp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalenwp),
            )
        } else if (validate!!.returnIntegerValue(et_female_nwp_attended.text.toString())>validate!!.returnIntegerValue(et_female_nwp.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_nwp_attended,
                resources.getString(R.string.attended_female_nwp_should_be_less_than_total_female_nwp)
            )
        } else if (et_trans_wp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_wp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transwp),
            )
        }  else if (validate!!.returnIntegerValue(et_trans_wp_attended.text.toString())>validate!!.returnIntegerValue(et_trans_wp.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_wp_attended,
                resources.getString(R.string.attended_transgender_wp_should_be_less_than_total_transgender_wp)
            )
        } else if (et_trans_nwp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_nwp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transnwp),
            )
        } else if (validate!!.returnIntegerValue(et_trans_nwp_attended.text.toString())>validate!!.returnIntegerValue(et_trans_nwp.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_nwp_attended,
                resources.getString(R.string.attended_transgender_nwp_should_be_less_than_total_transgender_nwp)
            )
        } else if (et_male_hhm_attended.text.toString().length==0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_hhm_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malehhm),
            )
        }else if (validate!!.returnIntegerValue(et_male_hhm_attended.text.toString())> validate!!.returnIntegerValue(et_male_hhm.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_hhm_attended,
                resources.getString(R.string.attended_male_hhm_should_be_less_than_total_male_hhm)
            )
        } else if (et_female_hhm_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_hhm_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalehhm),
            )
        } else if (validate!!.returnIntegerValue(et_female_hhm_attended.text.toString())>validate!!.returnIntegerValue(et_female_hhm.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_hhm_attended,
                resources.getString(R.string.attended_female_hhm_should_be_less_than_total_female_hhm)
            )

        } else if (et_trans_hhm_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_hhm_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transhhm),
            )
        } else if (validate!!.returnIntegerValue(et_trans_hhm_attended.text.toString())>validate!!.returnIntegerValue(et_trans_hhm.text.toString())) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_hhm_attended,
                resources.getString(R.string.attended_transgender_hhm_should_be_less_than_total_transgender_hhm)
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
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }

                    it.get(0).Attn_male_HHM?.let { it1 -> setDefBlank(et_male_hhm_attended, it1) }
                    it.get(0).Attn_male_WP?.let { it1 -> setDefBlank(et_male_wp_attended, it1) }
                    it.get(0).Attn_male_NWP?.let { it1 -> setDefBlank(et_male_nwp_attended, it1) }
                    it.get(0).Attn_female_WP?.let { it1 -> setDefBlank(et_female_wp_attended, it1) }
                    it.get(0).Attn_female_HHM?.let { it1 -> setDefBlank(et_female_hhm_attended, it1) }

                    it.get(0).Attn_female_NWP?.let { it1 ->
                        setDefBlank(
                            et_female_nwp_attended,
                            it1
                        )
                    }
                    it.get(0).Attn_Transgender_WP?.let { it1 ->
                        setDefBlank(
                            et_trans_wp_attended,
                            it1
                        )
                    }
                    it.get(0).Attn_Transgender_HHM?.let { it1 ->
                        setDefBlank(
                            et_trans_hhm_attended,
                            it1
                        )
                    }
                    it.get(0).Attn_Transgender_NWP?.let { it1 ->
                        setDefBlank(
                            et_trans_nwp_attended,
                            it1
                        )
                    }


                    val MaleWP = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getMaleWP(it)
                        }

                    val FeMaleWP = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getFeMaleWP(it)
                        }

                    val TransWP = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getTransWP(it)
                        }

                    val MaleNWP = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getMaleNWP(it)
                        }

                    val FeMaleNWP = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getFeMaleNWP(it)
                        }

                    val TransNWP = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getTransNWP(it)
                        }

                    val MaleHHM = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getMaleHHM(it)
                        }

                    val FeMaleHHM = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getFeMaleHHM(it)
                        }

                    val TransHHM = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
                        ?.let {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.getTransHHM(it)
                        }

                    if (it.get(0).Member_male_WP!! > 0) {
                        it.get(0).Member_male_WP?.let { it1 ->
                            setDefBlank(
                                et_male_wp,
                                it1
                            )
                        }
                    } else {
                        et_male_wp.setText(MaleWP.toString())
                    }

                    if (it.get(0).Member_female_WP!! > 0) {
                        it.get(0).Member_female_WP?.let { it1 ->
                            setDefBlank(
                                et_female_wp,
                                it1
                            )
                        }
                    } else {
                        et_female_wp.setText(FeMaleWP.toString())
                    }

                    if (it.get(0).Member_Transgender_WP!! > 0) {
                        it.get(0).Member_Transgender_WP?.let { it1 ->
                            setDefBlank(
                                et_trans_wp,
                                it1
                            )
                        }
                    } else {
                        et_trans_wp.setText(TransWP.toString())
                    }


                    if (it.get(0).Member_male_HHM!! > 0) {
                        it.get(0).Member_male_HHM?.let { it1 ->
                            setDefBlank(
                                et_male_hhm,
                                it1
                            )
                        }
                    } else {
                        et_male_hhm.setText(MaleHHM.toString())
                    }

                    if (it.get(0).Member_female_HHM!! > 0) {
                        it.get(0).Member_female_HHM?.let { it1 ->
                            setDefBlank(
                                et_female_hhm,
                                it1
                            )
                        }
                    } else {
                        et_female_hhm.setText(FeMaleHHM.toString())
                    }

                    if (it.get(0).Member_Transgender_HHM!! > 0) {
                        it.get(0).Member_Transgender_HHM?.let { it1 ->
                            setDefBlank(
                                et_trans_hhm,
                                it1
                            )
                        }
                    } else {
                        et_trans_hhm.setText(TransHHM.toString())
                    }



                    if (it.get(0).Member_male_NWP!! > 0) {
                        it.get(0).Member_male_NWP?.let { it1 ->
                            setDefBlank(
                                et_male_nwp,
                                it1
                            )
                        }
                    } else {
                        et_male_nwp.setText(MaleNWP.toString())
                    }
                    if (it.get(0).Member_female_NWP!! > 0) {
                        it.get(0).Member_female_NWP?.let { it1 ->
                            setDefBlank(
                                et_female_nwp,
                                it1
                            )
                        }
                    } else {
                        et_female_nwp.setText(FeMaleNWP.toString())
                    }
                    if (it.get(0).Member_Transgender_NWP!! > 0) {
                        it.get(0).Member_Transgender_NWP?.let { it1 ->
                            setDefBlank(
                                et_trans_nwp,
                                it1
                            )
                        }
                    } else {
                        et_trans_nwp.setText(TransNWP.toString())
                    }


                }
            })
    }
    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }
    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(400, 0)
        }, 100)
    }
}