package com.careindia.lifeskills.views.improfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileTwoBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_household_profile_second.*
import kotlinx.android.synthetic.main.activity_improfile_two.*
import kotlinx.android.synthetic.main.activity_improfile_two.btn_bottom
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileTwoActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileTwoBinding
    var validate: Validate? = null
    var iLanguageID: Int = 0
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel

    var imProfileGUID = ""
    var readWrite =0
    var smartphone =0
    var accessmobile =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_two)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.im_profile)


        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)


        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = IndividualProfileRepository(improfiledao!!, mstDistrictDao)
        readWrite =  validate!!.RetriveSharepreferenceInt(AppSP.READWRITE)
        smartphone =  validate!!.RetriveSharepreferenceInt(AppSP.SMARTPHONE)
        accessmobile =  validate!!.RetriveSharepreferenceInt(AppSP.ACESSMOBILE)
        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this

        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        initializeController()
        hideShowView()


    }

    override fun initializeController() {
        //apply click on view
        applyClickOnView()
        topLayClick()
        fillMultiCheck()

        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }


    }

    fun fillMultiCheck() {
        validate!!.dynamicMultiCheckChange(
            this,
            multiCheck_lang_read,
            mstLookupViewModel,
            9,
            1,
            et_specifyread,
            lay_et_specifyread
        )
        validate!!.dynamicMultiCheckChange(
            this,
            lang_write,
            mstLookupViewModel,
            9,
            1,
            et_specifywrite,
            lay_et_specifywrite
        )
        validate!!.dynamicMultiCheckChange(
            this,
            prefer_comni_speaking,
            mstLookupViewModel,
            9,
            1,
            et_specifyprefer,
            lay_et_specifyprefer
        )
        validate!!.dynamicMultiCheckChange(
            this,
            spaekCheck,
            mstLookupViewModel,
            9,
            1,
            et_specify,
            lay_et_specify
        )

        validate!!.dynamicMultiCheckChange(
            this,
            lang_prefer_mobile_use,
            mstLookupViewModel,
            9,
            1,
            et_specify_perfer_mob,
            lay_et_specify_perfer_mob
        )

//        validate!!.dynamicMultiCheckNew(this, spaekCheck, mstLookupViewModel, 9, 1)

    }


    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_prev.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 1) {
                    sendData()
                    imProfileViewModel.updateProfileSecondData(this)
                    var intent = Intent(this, IMProfileThirdActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.btn_prev -> {
                var intent = Intent(this, IMProfileDemographicActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.img_back -> {
                val intent = Intent(this, IMProfileListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.img_setting -> {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }


    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)
        imProfileViewModel.getIdvProfiledatabyGuid(validate!!.returnStringValue(idvProfileGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                        btn_bottom.visibility = View.GONE
                    }else{
                        btn_bottom.visibility = View.VISIBLE
                    }
                    et_specify.setText(validate!!.returnStringValue(it.get(0).Speak_Other))
                    et_specifyread.setText(validate!!.returnStringValue(it.get(0).Read_Other))
                    et_specifywrite.setText(validate!!.returnStringValue(it.get(0).Write_Other))
                    et_specifyprefer.setText(validate!!.returnStringValue(it.get(0).Prefer_Commu_Other))
                    et_specify_perfer_mob.setText(validate!!.returnStringValue(it.get(0).Mobile_Other))

                    (validate!!.SetAnswerTypeCheckBoxButton(
                        lang_prefer_mobile_use,
                        it.get(0).PreferredLanguage_Mobile
                    ))


                    (validate!!.SetAnswerTypeCheckBoxButton(spaekCheck, it.get(0).Languages_Speak))
                    (validate!!.SetAnswerTypeCheckBoxButton(
                        multiCheck_lang_read,
                        it.get(0).Languages_Read
                    ))
                    (validate!!.SetAnswerTypeCheckBoxButton(lang_write, it.get(0).Languages_Write))
                    (validate!!.SetAnswerTypeCheckBoxButton(
                        prefer_comni_speaking,
                        it.get(0).PreferredLanguage_Communication
                    ))


                }
            })

    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun sendData() {
        imProfileViewModel.collectiveData(
            validate!!.GetAnswerTypeCheckBoxButtonID(spaekCheck),
            validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_lang_read),
            validate!!.GetAnswerTypeCheckBoxButtonID(lang_write),
            validate!!.GetAnswerTypeCheckBoxButtonID(prefer_comni_speaking),
            validate!!.GetAnswerTypeCheckBoxButtonID(lang_prefer_mobile_use),
        )
    }


    fun hideShowView() {
        if(readWrite == 1){
            lay_language_read.visibility = View.VISIBLE
            lay_language_write.visibility = View.VISIBLE

        }else{
            lay_language_read.visibility = View.GONE
            lay_language_write.visibility = View.GONE
        }


        if(smartphone == 1){
            if(accessmobile == 1){
                lay_language_mobile.visibility = View.VISIBLE
            }else{
                lay_language_mobile.visibility = View.GONE
            }

        }else{
            lay_language_mobile.visibility = View.GONE
        }

    }

    private fun checkValidation(): Int {
        var value = 1

        if (validate!!.GetAnswerTypeCheckBoxButtonID(spaekCheck).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_speack_lang)
            )
            value = 0
        } else if (et_specify.text.toString().length == 0 && lay_et_specify.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specify,
                resources.getString(R.string.plz_enter_speak)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(multiCheck_lang_read).isEmpty() && lay_language_read.visibility == View.VISIBLE) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_read_lang)
            )
            value = 0

        } else if (et_specifyread.text.toString().length == 0 && lay_et_specifyread.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specifyread,
                resources.getString(R.string.plz_enter_read)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(lang_write).isEmpty() && lay_language_write.visibility == View.VISIBLE) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_write_lang)
            )
            value = 0


        } else if (et_specifywrite.text.toString().length == 0 && lay_et_specifywrite.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specifywrite,
                resources.getString(R.string.plz_enter_write)
            )
            value = 0
        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(prefer_comni_speaking).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_communication_lang)
            )
            value = 0

        } else if (et_specifyprefer.text.toString().length == 0 && lay_et_specifyprefer.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specifyprefer,
                resources.getString(R.string.plz_enter_specifyprefer)
            )
            value = 0

        } else if (validate!!.GetAnswerTypeCheckBoxButtonID(lang_prefer_mobile_use).isEmpty() && lay_language_mobile.visibility == View.VISIBLE) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
        } else if (et_specify_perfer_mob.text.toString().length == 0 && lay_et_specify_perfer_mob.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_specify_perfer_mob,
                resources.getString(R.string.plz_enter_perfer_mob)
            )
            value = 0
        }
        return value
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


    fun topLayClick() {
        autoSmoothScroll()
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_schemess.setBackgroundColor(resources.getColor(R.color.back))
        lay_demographic.setBackgroundColor(resources.getColor(R.color.back))
        ll_other_detailss.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {

            val intent = Intent(this, IMProfileOneActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileTwoActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_third.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileThirdActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_forth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFourthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        lay_demographic.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileDemographicActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_schemess.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileFifthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        ll_other_detailss.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID)!!.length > 0) {
                val intent = Intent(this, IMProfileSixActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


    fun returnpos(id: Int, flag: Int): Int {
        val combobox = mstLookupViewModel.getMstLookupFlag(flag)
        var posi = 0
        for (i in 0 until combobox.size) {
            if (id == combobox[i].LookupCode) {
                posi = i + 1
            }
        }
        return posi
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
            if (pos > 0) id = data.get(pos - 1).LookupCode
        }
        return id
    }


    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(400, 0)
        }, 100)
    }

    //    Integer.parseInt(et_long_stay.text.toString()) <= 0 || Integer.parseInt(et_long_stay.text.toString()) >= 99 ||
    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, IMProfileListActivity::class.java)
//        startActivity(intent)
//        finish()
    }

}