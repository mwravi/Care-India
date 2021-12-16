package com.careindia.lifeskills.views.householdscreen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileThirdBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataListActivity
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.hhnavigationtab.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.activity_household_profile_third.*


class HouseholdProfileThirdActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHouseholdProfileThirdBinding
    var validate: Validate? = null

    lateinit var householdProfileViewModel: HouseholdProfileViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    val dwelling = MutableLiveData<Int>()
    val ration_card = MutableLiveData<Int>()
    var iLanguageID = 0
    var quetionValue201=0
    var quetionValue201a=0
    var quetionValue201b=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_third)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        val hhdao = CareIndiaApplication.database?.hhProfileDao()!!
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val hhRepository = HouseholdProfileRepository(hhdao, mstDistrictDao)
        householdProfileViewModel =
            ViewModelProvider(
                this,
                HouseholdProfileViewModelFactory(hhRepository)
            )[HouseholdProfileViewModel::class.java]
        binding.householdProfileViewModel = householdProfileViewModel
        binding.lifecycleOwner = this
        tv_title.text = "Household Profile"
        btn_save.text = resources.getString(R.string.save_close)

        initializeController()
        binddata()
        bottomclick()
        hideview()
        if (validate!!.RetriveSharepreferenceString(AppSP.HHGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.HHGUID
            )!!.trim().length > 0
        ) {
            showLiveData()
        }

    }
    fun bottomclick()
    {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.color_darkgrey))

        lay_first.setOnClickListener {

            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {

            val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
            startActivity(intent)
            finish()
        }

        ll_third.setOnClickListener {

            val intent = Intent(this, HouseholdProfileThirdActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_back.setOnClickListener {
            val intent = Intent(this, HouseholdProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun initializeController() {
        applyClickOnView()


    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (CheckValidation() == 0) {
                    householdProfileViewModel.updatehh_third(this)

                    CustomAlert(this@HouseholdProfileThirdActivity,resources.getString(R.string.data_saved_successfully))

                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, HouseholdProfileSecondActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun binddata() {

        validate!!.fillradio(
            this,
            rg_dwelling_place_registered,
            -1,
            mstLookupViewModel,
            3,
            iLanguageID


        )


        fillSpinner(
            resources.getString(R.string.select),
            spin_dwelling,
            2,
            iLanguageID
        )

        fillSpinner(
            resources.getString(R.string.select),
            spin_hh_ration_card,
            4,
            iLanguageID
        )


    }


    fun CheckValidation(): Int {
        var iValue = 0;
        if (et_totalEarningMember.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalEarningMember,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_earning_member),
            )
        } else if (validate!!.returnIntegerValue(et_totalEarningMember.text.toString()) >quetionValue201) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleearningmember,
                resources.getString(R.string.q204_input_value_cannot_be_more_than_value_in_q201),
            )
        } else if (et_maleearningmember.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleearningmember,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male_earning_member),
            )
        } else if (validate!!.returnIntegerValue(et_maleearningmember.text.toString()) >quetionValue201a) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleearningmember,
                resources.getString(R.string.q204a_input_value_cannot_be_more_than_value_in_q201a),
            )
        } else if (validate!!.returnIntegerValue(et_maleearningmember.text.toString())  > validate!!.returnIntegerValue(
                et_totalEarningMember.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleearningmember,
                resources.getString(R.string.q204a_input_value_between_0_q204)
            )
        } else if (et_femaleearningmember.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_femaleearningmember,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female_earning_member),
            )
        } else if (validate!!.returnIntegerValue(et_femaleearningmember.text.toString()) >quetionValue201b) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_maleearningmember,
                resources.getString(R.string.q204b_input_value_cannot_be_more_than_value_in_q201b),
            )
        }  else if (validate!!.returnIntegerValue(et_femaleearningmember.text.toString())  > validate!!.returnIntegerValue(
                et_totalEarningMember.text.toString()
            )) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_femaleearningmember,
                resources.getString(R.string.q204b_input_value_between_0_q204)
            )
        } else if (validate!!.returnIntegerValue(et_maleearningmember.text.toString()) +
            validate!!.returnIntegerValue(et_femaleearningmember.text.toString()) > validate!!.returnIntegerValue(
                et_totalEarningMember.text.toString()
            )
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_totalEarningMember,
                resources.getString(R.string.the_sum_of_male_and_female_earning_members_must_be_equal_to_or_less_than_the_total_number_of_earning_members)
            )
        } else if (spin_dwelling.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_dwelling,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.type_of_dwelling),
            )
        } else if (et_othertypeofdwelling.text.toString().length == 0 && lay_othertypeofdwelling.visibility==View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_othertypeofdwelling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.if_other_type_of_dwelling),
            )
        }else if(rg_dwelling_place_registered.checkedRadioButtonId==-1){
              iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.is_your_dwelling_place_registered),
            )
        }

        else if (spin_hh_ration_card.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_hh_ration_card,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_ration_card),
            )
        } else if (et_other_ration.text.toString().length == 0 && lay_other_ration.visibility==View.VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_other_ration,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.if_other_ration),
            )
        }
        return iValue;
    }
    fun showLiveData() {
        val idvProfileGuid = validate!!.RetriveSharepreferenceString(AppSP.HHGUID)
        if (idvProfileGuid != null) {
            householdProfileViewModel.gethhdatabyGuid(idvProfileGuid).observe(this, Observer {
                if (it != null && it.size > 0) {
                    quetionValue201=it.get(0).No_adults!!
                    quetionValue201a=it.get(0).No_adults_M!!
                    quetionValue201b=it.get(0).No_adults_F!!
                    setDefBlank(et_totalEarningMember, it.get(0).No_Earningmembers!!)
                    setDefBlank(et_maleearningmember, it.get(0).No_Earningmembers_M!!)
                    setDefBlank(et_femaleearningmember, it.get(0).No_Earningmembers_F!!)
                    spin_dwelling.setSelection(
                        validate!!.returnpos(
                            it.get(0).Dwelling_type,
                            mstLookupViewModel,
                            2,
                            iLanguageID

                        )
                    )
                    et_othertypeofdwelling.setText(it.get(0).Dwelling_Oth!!)
                    spin_hh_ration_card.setSelection(
                        validate!!.returnpos(
                            it.get(0).Type_Ration,
                            mstLookupViewModel,
                            4,
                            iLanguageID
                        )
                    )

                    et_other_ration.setText(it.get(0).Other_Ration!!)
                    validate!!.SetAnswerTypeRadioButton(
                        rg_dwelling_place_registered,
                        it.get(0).Dwelling_Registered!!
                    )


                }
            })
        }

    }

    fun fillSpinner(
        strValue: String, spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        mstLookupViewModel!!.getMstLookup(flag, iLanguageID)
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


    fun hideview() {
        householdProfileViewModel.dwelling.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            var pos = it
            if (pos > 0) {
                var id = returnID(pos, 2, iLanguageID)
                if (id == 99) {
                    lay_othertypeofdwelling.visibility = View.VISIBLE
                } else {
                    lay_othertypeofdwelling.visibility = View.GONE
                    et_othertypeofdwelling.setText("")

                }

            }


        })

        householdProfileViewModel.ration_card.observe(this, Observer {
            Log.i("MYTAGTWO", it.toString())
            val pos = it
            if (pos > 0) {
                val id = returnID(pos, 4, iLanguageID)
                if (id == 99) {
                    lay_other_ration.visibility = View.VISIBLE
                } else {
                    lay_other_ration.visibility = View.GONE
                    et_other_ration.setText("")

                }

            }


        })


    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    override fun onBackPressed() {
        val intent = Intent(this, HouseholdProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun CustomAlert(
        primaryDataThirdActivity: HouseholdProfileThirdActivity,
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
            val intent = Intent(primaryDataThirdActivity, HouseholdProfileListActivity::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }

}