package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectionMemberBinding
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMemberViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMemberViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_member.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.DecimalFormat

class CollectiveProfileMemberActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectionMemberBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveMemberViewModel: CollectiveMemberViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection_member)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val collectiveMemberDao = CareIndiaApplication.database?.collectiveMemDao()

        val collectiveMemberRepository =
            CollectiveMemberRepository(collectiveMemberDao!!)
        collectiveMemberViewModel =
            ViewModelProvider(this, CollectiveMemberViewModelFactory(collectiveMemberRepository))[
                    CollectiveMemberViewModel::class.java]

        binding.collectiveMemberViewModel = collectiveMemberViewModel
        binding.lifecycleOwner = this
        tv_title.text = getString(R.string.collmember)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityThird::class.java)
            startActivity(intent)
            finish()
        }
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMemberGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        } else {
            et_member_id.setText(getCollectiveUniqueID())
        }

        initializeController()
    }

    override fun initializeController() {
        fillSpinner()
        applyClickOnView()
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    collectiveMemberViewModel.savecollectivemember(this)
                    val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun showLiveData() {
        val collectiveMemGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)
        collectiveMemberViewModel.getCollectiveMemberdatabyGuid(
            validate!!.returnStringValue(
                collectiveMemGuid
            )
        ).observe(this, Observer {
            if (it != null && it.size > 0) {

                et_member_name.setText(validate!!.returnStringValue(it.get(0).Name))
                et_member_id.setText(validate!!.returnStringValue(it.get(0).MemberID))
                spin_member_sex.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(it.get(0).Gender.toString()),
                        1, iLanguageID
                    )
                )
                et_member_age.setText(validate!!.returnStringValue(it.get(0).Age.toString()))
                et_contact_number.setText(validate!!.returnStringValue(it.get(0).Contact))
                et_role_of_member.setText(validate!!.returnStringValue(it.get(0).Position))
                validate!!.SetAnswerTypeRadioButton(rg_savings_account, it.get(0).Isbank)
                et_aadhar_card.setText(validate!!.returnStringValue(it.get(0).Aadhaar))

            }
        })
    }

    fun fillSpinner() {

        fillSpinner(resources.getString(R.string.select), spin_member_sex, 1, iLanguageID)
        validate!!.fillradio(this, rg_savings_account, -1, mstLookupViewModel, 3, iLanguageID)

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

    fun checkValidation(): Int {

        var iValue = 0;

        if (et_member_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_member),
            )
        } else if (et_member_id.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_id,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.individual_member_id),
            )
        } else if (spin_member_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_member_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_the_member),
            )
        } else if (et_member_age.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.age_of_the_member),
            )
        } else if (validate!!.returnIntegerValue(et_member_age.text.toString()) < 18 || validate!!.returnIntegerValue(
                et_member_age.text.toString()
            ) > 65
        ) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_age,
                resources.getString(R.string.valid_age_of_the_member),
            )
        } else if (et_contact_number.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_contact_number,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.contact_number),
            )
        } else if (validate!!.checkmobileno(et_contact_number.text.toString()) == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_contact_number,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.valid_mobile_no),
            )
        } else if (et_role_of_member.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_role_of_member,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.role_of_member_in_group),
            )
        } else if (validate!!.GetAnswerTypeRadioButtonID(rg_savings_account) == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_savings_bank_account),
            )
        }
        return iValue;
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

    fun getCollectiveUniqueID(): String {
        var hh_code = ""
        val cin = "CIN"
        val initials = "IM"
        var character_number = collectiveMemberViewModel.getCommunityCount() + 1


        var sWardPanchayat = CareIndiaApplication.database?.collectiveDao()
            ?.getPWCode(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)
        var ward_or_panchayat_code = CareIndiaApplication.database?.collectiveDao()
            ?.getPanchayat_Ward(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)

        hh_code = cin + sWardPanchayat + getCharacterNumber(
            ward_or_panchayat_code!!,
            "000"
        ) + initials + getCharacterNumber(
            character_number, "00000"
        )

        return hh_code
    }

    fun getCharacterNumber(character_number: Int, pattern: String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }

    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(StateCode)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).Urban_rural!!

            }
        }
        return id
    }

    fun returnDistrictID(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        data =
            CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(StateCode)

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode!!

            }
        }
        return id
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileActivityThird::class.java)
        startActivity(intent)
        finish()
    }
}