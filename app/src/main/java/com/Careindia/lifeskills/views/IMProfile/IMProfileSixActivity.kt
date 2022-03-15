package com.careindia.lifeskills.views.improfile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileSixBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_six.*
import kotlinx.android.synthetic.main.activity_improfile_six.btn_bottom
import kotlinx.android.synthetic.main.activity_improfile_six.btn_prev
import kotlinx.android.synthetic.main.activity_improfile_six.btn_save
import kotlinx.android.synthetic.main.bottomnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileSixActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImprofileSixBinding
    var validate: Validate? = null
    var iLanguageID: Int? = 0
    lateinit var imProfileViewModel: IndividualProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel

    lateinit var job_Business_Adapter: Job_Business_Adapter
    var interested_JobDetail=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improfile_six)
        validate = Validate(this)
        tv_title.text = resources.getString(R.string.im_profile)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = IndividualProfileRepository(improfiledao!!, mstDistrictDao)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]
        binding.individualProfileViewModel = imProfileViewModel
        binding.lifecycleOwner = this

        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        initializeController()

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
                    imProfileViewModel.updateProfileSixData(this)
                    CustomAlert(resources.getString(R.string.data_saved_successfully))
                }
            }
            R.id.btn_prev -> {
                val intent = Intent(this, IMProfileFifthActivity::class.java)
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


    override fun initializeController() {

        //apply click on view
        applyClickOnView()
        topLayClick()

        fillRadio()
        hideShowView()
        if (validate!!.RetriveSharepreferenceString(AppSP.IndividualProfileGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.IndividualProfileGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

//        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))

        validate!!.dynamicMultiCheckChange601(
            this,
            skills_jobs_picking,
            mstLookupViewModel,
            15,
            1,
            et_skills_jobs_picking,
            lay_et_specify
        )

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
                    et_skills_jobs_picking.setText(validate!!.returnStringValue(it.get(0).Skills_oth))
                    et_name_collective_part.setText(validate!!.returnStringValue(it.get(0).Collective_name))
                    et_specify_laboure.setText(validate!!.returnStringValue(it.get(0).Laboure_oth))
                    et_specify_business.setText(validate!!.returnStringValue(it.get(0).Business_oth))
                    et_specify_job.setText(validate!!.returnStringValue(it.get(0).Jobs))
                    /*     spin_alternative_get_opportunity.setSelection(
                             returnpos(
                                 validate!!.returnIntegerValue(
                                     it.get(0).Interested_JobDetail.toString()
                                 ), 16
                             )
                         )*/
                    interested_JobDetail= it.get(0).Interested_JobDetail.toString()


                    (it.get(0).Interested_Job?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_new_jobs_business,
                            it1
                        )
                    })
                    (it.get(0).Member_Collective?.let { it1 ->
                        validate!!.SetAnswerTypeRadioButton(
                            rg_member_cig_shg,
                            it1
                        )
                    })

                    (validate!!.SetAnswerTypeCheckBoxButton(
                        skills_jobs_picking,
                        it.get(0).Skills
                    ))


                }
            })

    }

    fun hideShowView() {

        rg_member_cig_shg.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_et_name_collective_part.visibility = View.VISIBLE
            } else {
                lay_et_name_collective_part.visibility = View.GONE
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }

        rg_new_jobs_business.setOnCheckedChangeListener { radioGroup, i ->
            if (i == 1) {
                lay_spin_alternative_get_opportunity.visibility = View.VISIBLE
                lay_et_specify_business.visibility = View.VISIBLE
                lay_et_specify_laboure.visibility = View.VISIBLE
//                lay_et_specify_job.visibility = View.VISIBLE
                fillRecyclerView(interested_JobDetail)

            } else {
                lay_spin_alternative_get_opportunity.visibility = View.GONE
                lay_et_specify_business.visibility = View.GONE
                lay_et_specify_laboure.visibility = View.GONE
//                lay_et_specify_job.visibility = View.GONE
                et_specify_business.setText("")
                et_specify_laboure.setText("")
                et_specify_job.setText("")
            }
            validate!!.hideSoftKeyboard(this, radioGroup)
        }
    }

    private fun fillRecyclerView(sdata:String) {
        val listData = iLanguageID?.let { mstLookupViewModel.getMstAllData(16, it) }

        job_Business_Adapter =
            Job_Business_Adapter(this, listData,sdata,mstLookupViewModel,iLanguageID!!)

        rv_Job_Business.layoutManager = LinearLayoutManager(this)

        rv_Job_Business.setAdapter(job_Business_Adapter)
    }

    fun fillRadio() {

        validate!!.fillradio(
            this,
            rg_new_jobs_business,
            -1,
            mstLookupViewModel,
            3,
            1
        )

        validate!!.fillradio(
            this,
            rg_member_cig_shg,
            -1,
            mstLookupViewModel,
            3,
            1
        )

    }

    fun fillSpinner() {
        // bindCommonTable("Select", spin_alternative_get_opportunity, 16, 1)
    }

    fun sendData() {
        imProfileViewModel.collectiveProfileSixData(
            validate!!.GetAnswerTypeCheckBoxButtonID(skills_jobs_picking),
            validate!!.GetAnswerTypeRadioButtonID(rg_new_jobs_business),
            validate!!.GetAnswerTypeRadioButtonID(rg_member_cig_shg)
        )
    }


    private fun checkValidation(): Int {
        var value = 1
        val listData = iLanguageID?.let { mstLookupViewModel.getMstAllData(16, it) }
        var data=recycledata()
        val arrdata = data.split(",")
        if (validate!!.GetAnswerTypeCheckBoxButtonID(skills_jobs_picking).isEmpty()) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.plz_select_mobiledata_lang)
            )
            value = 0
        } else if (et_skills_jobs_picking.text.toString().length == 0 && lay_et_specify.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_skills_jobs_picking,
                resources.getString(R.string.plz_skills_jobs_picking)
            )
            value = 0

        } else if (rg_new_jobs_business.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.new_jobs_business)
            )
            value = 0
        } /*else if (spin_alternative_get_opportunity.selectedItemPosition == 0  && lay_spin_alternative_get_opportunity.visibility == View.VISIBLE) {
            validate!!.CustomAlertSpinner(
                this,
                spin_alternative_get_opportunity,
                resources.getString(R.string.plz_select_alternative_jobbussi)
            )
            value = 0

        }*/ else if (rg_member_cig_shg.checkedRadioButtonId == -1) {
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.member_CIG_SHG)
            )
            value = 0
        } else if (et_name_collective_part.text.toString().length == 0 && lay_et_name_collective_part.visibility == View.VISIBLE) {
            validate!!.CustomAlertEdit(
                this,
                et_name_collective_part,
                resources.getString(R.string.plz_select_name_coltiv_part)
            )
            value = 0
        } else if (lay_spin_alternative_get_opportunity.visibility==View.VISIBLE && data.contains("0")) {

                validate!!.CustomAlert(
                    this,
                    resources.getString(R.string.alternative_get_opportunity)
                )
                value = 0

        }
        return value
    }

    fun CustomAlert(
        msg: String?
    ) { // Create custom dialog object
        val dialog = Dialog(this)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            val intent = Intent(this, IMProfileListActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }

    fun bindCommonTable(strValue: String, spin: Spinner, flag: Int, iLanguageID: Int) {
        mstLookupViewModel.getMstUser(flag, iLanguageID).observe(this, androidx.lifecycle.Observer {
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
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_forth.setBackgroundColor(resources.getColor(R.color.back))
        ll_schemess.setBackgroundColor(resources.getColor(R.color.back))
        lay_demographic.setBackgroundColor(resources.getColor(R.color.back))
        ll_other_detailss.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

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
            horizontalScroll.smoothScrollBy(2000, 0)
        }, 100)
    }


    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, IMProfileListActivity::class.java)
//        startActivity(intent)
//        finish()
    }

    fun recycledata():String {
        val iSize: Int = rv_Job_Business.getChildCount()
        var sdata = ""
        for (i in 0 until iSize) {

            val cardViewKnowledge = rv_Job_Business.getChildAt(i) as LinearLayout

            val tv = cardViewKnowledge.getChildAt(0)
            var spinner: Spinner? = null

            spinner = cardViewKnowledge.getChildAt(1) as Spinner?
           // val text: String = spinner?.selectedItemPosition.toString()
            val text: String = validate!!.returnID(spinner!!,mstLookupViewModel,133,iLanguageID!!).toString()
            if (i == 0) {
                sdata = text
            } else {
                sdata = sdata + "," + text
            }
        }

        return sdata
    }


    fun fillSpinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        val it= mstLookupViewModel.getLookup(flag, iLanguageID)
        if (it.isNotEmpty()) {
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


    }



}

