package com.careindia.lifeskills.views.collectivemeeting

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_collectivemeetingsec.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.meetingtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingSecActivity : AppCompatActivity() {
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
            if (checkValidation()==0) {
                collectiveMeetingViewModel.UpdateWastePicker(this)
                val intent = Intent(this, CollectiveMeetingThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveMeetingActivity::class.java)
            startActivity(intent)
            finish()
        }
        bottomclick()
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMeetingGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        }
    }

    fun bottomclick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        lay_first.setOnClickListener {

            val intent = Intent(this, CollectiveMeetingActivity::class.java)
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
                val intent = Intent(this, CollectiveMeetingThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, CollectiveMeetingListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun checkValidation(): Int {

        var iValue = 0

        if (et_male_wp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_wp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malewp),
            )
        }  else  if (et_male_nwp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_nwp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.malenwp),
            )
        } else  if (et_female_wp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_wp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalewp),
            )
        }else  if (et_female_nwp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_nwp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.femalenwp),
            )
        }else  if (et_trans_wp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_wp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transwp),
            )
        }else  if (et_trans_nwp_attended.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trans_nwp_attended,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transnwp),
            )
        }
        return iValue
    }


    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveMeetingGUID)
        collectiveMeetingViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            ?.observe(this, Observer {
                if (it != null && it.size > 0) {
                    it.get(0).Attn_male_WP?.let { it1 -> setDefBlank(et_male_wp_attended, it1) }
                    it.get(0).Attn_male_NWP?.let { it1 -> setDefBlank(et_male_nwp_attended, it1) }
                    it.get(0).Attn_female_WP?.let { it1 -> setDefBlank(et_female_wp_attended, it1) }
                    it.get(0).Attn_female_NWP?.let { it1 -> setDefBlank(et_female_nwp_attended, it1) }
                    it.get(0).Attn_Transgender_WP?.let { it1 -> setDefBlank(et_trans_wp_attended, it1) }
                    it.get(0).Attn_Transgender_NWP?.let { it1 -> setDefBlank(et_trans_nwp_attended, it1) }

                }
            })
    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }
}