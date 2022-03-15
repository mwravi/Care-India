package com.careindia.lifeskills.views.collectiveTracker

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
import kotlinx.android.synthetic.main.coll_prog_tracker_seventh.*
import kotlinx.android.synthetic.main.coll_prog_tracker_seventh.btn_bottom
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.topnavigation_collective_tracker.*

class CollProgTrackerSeventh : BaseActivity(), View.OnClickListener {

    var validate: Validate? = null
    var iLanguageID = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coll_prog_tracker_seventh)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        mstLookupViewModel =
            ViewModelProvider(this).get(MstLookupViewModel::class.java)
        collectiveProgressTrackerViewModel =
            ViewModelProvider(this).get(CollectiveProgressTrackerViewModel::class.java)

        tv_title.text = resources.getString(R.string.collective_prog_tracker)
        btn_save.text = resources.getString(R.string.save_close)

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

                    CustomAlert(
                        this,
                        resources.getString(R.string.data_saved_successfully)
                    )
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollProgTrackerSixth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun hideView() {
        rg_acc_opened.setOnCheckedChangeListener { radioGroup, i ->

            val ID = validate!!.GetAnswerTypeRadioButtonIDNew(rg_acc_opened)
                    if (ID == 1) {
                        lay_no_of_banks.visibility = View.VISIBLE
                        lay_accounts_opened.visibility = View.VISIBLE
                    } else {
                        lay_no_of_banks.visibility = View.GONE
                        lay_accounts_opened.visibility = View.GONE
                        et_male.setText("")
                        et_female.setText("")
                    }
                }

    }

    fun fillSpinner() {
        validate!!.fillradio(this, rg_mobilized, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_acc_opened, -1, mstLookupViewModel, 3, iLanguageID)
        validate!!.fillradio(this, rg_access, -1, mstLookupViewModel, 3, iLanguageID)
    }

    fun CheckValidation(): Int {
        var iValue = 0
        if (rg_mobilized.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.as_a_collective_any_schemes_were_mobilized),
            )
        } else if (et_schemes_no.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_schemes_no,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.no_of_schemes_mobilized),
            )
        } else if (rg_acc_opened.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.any_bank_accounts_of_the_members_opened),
            )
        } else if (et_male.text.toString().length == 0 && lay_accounts_opened.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male),
            )
        } else if (et_female.text.toString().length == 0 && lay_accounts_opened.visibility == View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female),
            )
        } else if (rg_access.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.are_the_members_especially_women_able_to_access_collective_financial_resources_post_opening_of_bank_account),
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
        var pos = spin.getSelectedItemPosition()
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode!!
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
                        if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
                            btn_bottom.visibility = View.GONE
                        }else{
                            btn_bottom.visibility = View.VISIBLE
                        }
                        validate!!.SetAnswerTypeRadioButton(
                            rg_mobilized,
                            it[0].SchemesMobilized
                        )

                        setDefBlank(et_schemes_no, it.get(0).NoSchemesMobilized!!)
                        validate!!.SetAnswerTypeRadioButton(
                            rg_acc_opened,
                            it[0].BankAccountOpened
                        )
                        setDefBlank(et_male, it.get(0).NoBankAccountOpened_M!!)
                        setDefBlank(et_female, it.get(0).NoBankAccountOpened_F!!)

                        validate!!.SetAnswerTypeRadioButton(
                            rg_access,
                            it[0].AccessFinancialRes
                        )
                    }
                }
            )
        }
    }

    fun updateData() {
        collectiveProgressTrackerViewModel.updatecptseventh(
            validate!!.RetriveSharepreferenceString(AppSP.CPTGUID),
            validate!!.GetAnswerTypeRadioButtonID(rg_mobilized),
            validate!!.returnIntegerValue(et_schemes_no.text.toString()),
            validate!!.GetAnswerTypeRadioButtonID(rg_acc_opened),
            validate!!.returnIntegerValue(et_male.text.toString()),
            validate!!.returnIntegerValue(et_female.text.toString()),
            validate!!.GetAnswerTypeRadioButtonID(rg_access),
            1,
            validate!!.RetriveSharepreferenceInt(AppSP.iUserID),
            validate!!.getDaysfromdates(validate!!.currentdatetime, 2)
        )
    }

    fun CustomAlert(
        collProgTrackerSeventh: CollProgTrackerSeventh,
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
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            val intent = Intent(collProgTrackerSeventh, CollProgTrackerListActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }

    fun topLayClick() {
        autoSmoothScroll()
        lay_first_basic_info.setBackgroundColor(resources.getColor(R.color.back))
        lay_second_member_strength.setBackgroundColor(resources.getColor(R.color.back))
        lay_third_meeting_details.setBackgroundColor(resources.getColor(R.color.back))
        lay_fourth_effect1.setBackgroundColor(resources.getColor(R.color.back))
        lay_fifth_effect2.setBackgroundColor(resources.getColor(R.color.back))
        lay_sixth_sustainability.setBackgroundColor(resources.getColor(R.color.back))
        lay_seventh_access_scheme.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

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
            horizontalScroll.smoothScrollBy(2500, 0)
        }, 100)
    }
}