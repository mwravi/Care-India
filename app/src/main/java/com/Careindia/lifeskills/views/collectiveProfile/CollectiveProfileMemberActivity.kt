package com.careindia.lifeskills.views.collectiveProfile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectionMemberBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.utils.VerhoeffAlgorithm
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
    var coll_code_starting = ""
    var iShow = 0
    var total = 0
    var totalM = 0
    var totalF = 0
    var totalT = 0

    var hhGUID = ""
    var hhGUIDShow = ""
    var imGUIDShow = ""
    var stateCode = 0
    var zoneCode = 0
    var wardCode = 0
    var panchayatCode = 0
    var isUrban = 0
    var CodeHh = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection_member)
        validate = Validate(this)
        stateCode = validate!!.RetriveSharepreferenceInt(AppSP.StateCode)
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


        val profileData = CareIndiaApplication.database?.collectiveDao()
            ?.getColldatabyGuid(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)

        if (profileData!!.isNotEmpty()) {
            total = profileData[0].NoMembers!!
            totalM = profileData[0].NoMembers_M!!
            totalF = profileData[0].NoMembers_F!!
            totalT = profileData[0].NoMembers_T!!
        }

        val collectivemax = collectiveMemberViewModel.getCommunityCount() + 1
        et_last_code.setText(
            getCharacterNumber(
                collectivemax,
                "00000"
            )
        )
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
        et_last_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length != 0) et_member_id.setText(
                    coll_code_starting + getCharacterNumber(
                        validate!!.returnIntegerValue(et_last_code.text.toString()),
                        "00000"
                    )
                )

            }
        })

        collectiveMemberViewModel.Membersex.observe(this, Observer {

            val lookupcode =
                validate!!.returnID(spin_member_sex, mstLookupViewModel, 1, iLanguageID)
            var memCount: Int? = 0
            if (lookupcode == 1) {
                memCount = CareIndiaApplication.database?.collectiveMemDao()
                    ?.getAllMemberDataGender(
                        validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                        lookupcode
                    )
                if (memCount!! == totalM && iShow == 0) {
                    validate!!.CustomAlert(
                        this,
                        "Can not add Male members",
                    )
                    spin_member_sex.setSelection(0)
                }
            } else if (lookupcode == 2) {
                memCount = CareIndiaApplication.database?.collectiveMemDao()
                    ?.getAllMemberDataGender(
                        validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                        lookupcode
                    )
                if (memCount!! == totalF && iShow == 0) {
                    validate!!.CustomAlert(
                        this,
                        "Can not add Female members",
                    )
                    spin_member_sex.setSelection(0)
                }
            } else if (lookupcode == 3) {
                memCount = CareIndiaApplication.database?.collectiveMemDao()
                    ?.getAllMemberDataGender(
                        validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!,
                        lookupcode
                    )
                if (memCount!! == totalT && iShow == 0) {
                    validate!!.CustomAlert(
                        this,
                        "Can not add Transgender members",
                    )
                    spin_member_sex.setSelection(0)
                }
            }
        })

        isUrban = validate!!.RetriveSharepreferenceInt(AppSP.isUrban)
        zoneCode = validate!!.RetriveSharepreferenceInt(AppSP.zoneCode)
        wardCode = validate!!.RetriveSharepreferenceInt(AppSP.wardCode)
        panchayatCode = validate!!.RetriveSharepreferenceInt(AppSP.panchayatId)


        initializeController()
        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMemberGUID
            )!!.trim().isNotEmpty()
        ) {
            showLiveData()
        } else {
//            et_member_id.setText(getCollectiveUniqueID() + et_last_code.text.toString())
        }


    }

    override fun initializeController() {
        fillSpinner()
        applyClickOnView()
        hideShowView()
        rg_savings_account.setOnCheckedChangeListener { radioGroup, i ->
            validate!!.hideSoftKeyboard(this, radioGroup)
        }
    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    sendData()
                    collectiveMemberViewModel.savecollectivemember(
                        this,
                        iLanguageID,
                        isUrban,
                        zoneCode,
                        wardCode,
                        panchayatCode
                    )
                    CustomAlert(this, resources.getString(R.string.data_saved_successfully))

                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun hideShowView() {

        if (isUrban == 1) {
            bindHHIDTable("Select", spin_hh_id, zoneCode, wardCode, isUrban, 0)

        } else {
            bindHHIDTable("Select", spin_hh_id, 0, 0, isUrban, panchayatCode)

        }

        spin_hh_id.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    if (isUrban == 1) {
                        hhGUID = returnHH_GUID(position, isUrban, zoneCode, wardCode)

//                        CodeHh = returnHhPrfID(position, isUrban, zoneCode, wardCode, 0) as String
                    } else {
                        hhGUID = returnHH_GUID(position, isUrban, 0, panchayatCode)

//                        CodeHh = returnHhPrfID(position, isUrban, 0, 0, panchayatCode) as String

                    }
                    bindIMID(resources.getString(R.string.select), spin_im_id, hhGUID)
                    spin_im_id.setSelection(returnposIM_GUID(imGUIDShow, hhGUIDShow))

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spin_im_id.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) != null && validate!!.RetriveSharepreferenceString(
                            AppSP.CollectiveMemberGUID
                        )!!.trim().isNotEmpty()
                    ) {

                    } else {
                        val IndGUID = returnIM_GUID(position, hhGUID)
                        fillDataFromIM(IndGUID)
                    }

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }


    }


    fun sendData() {
        val imCode = spin_im_id.getItemAtPosition(spin_im_id.selectedItemPosition)
        val hhCode = spin_hh_id.getItemAtPosition(spin_hh_id.selectedItemPosition)
        collectiveMemberViewModel.collectiveMemberData(
            hhCode,
            imCode
        )


    }

    fun showLiveData() {
        val collectiveMemGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID)
        collectiveMemberViewModel.getCollectiveMemberdatabyGuid(
            validate!!.returnStringValue(
                collectiveMemGuid
            )
        ).observe(this, Observer {
            if (it != null && it.size > 0) {
                if (it.get(0).IsEdited == 0 && it.get(0).Status == 0) {
                    btn_bottom.visibility = View.GONE
                } else {
                    btn_bottom.visibility = View.VISIBLE
                }
                if (it.get(0).IsEdited == 0) {
                    if (it.get(0).Contact.toString().length == 0) {
                        et_contact_number.setText(validate!!.returnStringValue(it.get(0).Contact))
                    } else {
                        var lastthree =
                            it.get(0).Contact.toString()
                                .substring(it.get(0).Contact.toString().length - 3)
                        var getcontact = "XXXXXXX" + lastthree

                        et_contact_number.setText(getcontact)

                    }
                    if (it.get(0).Aadhaar != null) {
                        if (it.get(0).Aadhaar.toString().length == 0) {
                            et_aadhar_card.setText(validate!!.returnStringValue(it.get(0).Aadhaar))
                        } else {
                            var lastthree1 =
                                it.get(0).Aadhaar.toString()
                                    .substring(it.get(0).Aadhaar.toString().length - 4)
                            var getadhar = "XXXXXXXXX" + lastthree1

                            et_aadhar_card.setText(getadhar)

                        }
                    }

                } else {
                    et_contact_number.setText(validate!!.returnStringValue(it.get(0).Contact))
                    et_aadhar_card.setText(validate!!.returnStringValue(it.get(0).Aadhaar))
                }
                et_member_name.setText(validate!!.returnStringValue(it.get(0).Name))
//                et_member_id.setText(validate!!.returnStringValue(it.get(0).MemberID))
                spin_member_sex.setSelection(
                    returnpos(
                        validate!!.returnIntegerValue(it.get(0).Gender.toString()),
                        1, iLanguageID
                    )
                )

                if (it.get(0).HHGUID != null) {
//                    hhGUIDShow = returnHHcode(1, it.get(0).HHGUID!!)!!
                    hhGUIDShow = it.get(0).HHGUID!!
                }
                if (it.get(0).IndvGuid != null) {
//                    imGUIDShow = returnIMCode(1, it.get(0).IndvGuid!!)!!
                    imGUIDShow = it.get(0).IndvGuid!!
                }

//                spin_member_sex.isEnabled = false
                spin_wp_nwp.setSelection(returnpos(it[0].Category, 61, iLanguageID))
                setDefBlank(et_member_age, it.get(0).Age!!)
                et_role_of_member.setText(validate!!.returnStringValue(it.get(0).Position))
                validate!!.SetAnswerTypeRadioButton(rg_savings_account, it.get(0).Isbank)





                et_last_code.visibility = View.GONE

                if (isUrban == 1) {
                    spin_hh_id.setSelection(
                        returnposHHcode(
                            hhGUIDShow, isUrban, zoneCode, wardCode, 0
                        )
                    )
                } else {
                    spin_hh_id.setSelection(
                        returnposHHcode(
                            hhGUIDShow, isUrban, 0, 0, panchayatCode
                        )
                    )
                }

                spin_hh_id.isEnabled = false
                spin_im_id.isEnabled = false

                iShow = 1
            }
        })
    }

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun fillSpinner() {

        fillSpinner(resources.getString(R.string.select), spin_member_sex, 1, iLanguageID)
        fillSpinner(
            resources.getString(R.string.select),
            spin_wp_nwp,
            61,
            iLanguageID
        )

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

        var iValue = 0
        var iCount = collectiveMemberViewModel.getMemberID(
            et_member_id.text.toString(),
            validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!
        )
        if (et_member_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_member_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_the_member),
            )
//        } else if (et_last_code.text.toString().length == 0 && et_last_code.visibility == VISIBLE) {
//            iValue = 1
//            validate!!.CustomAlertEdit(
//                this,
//                et_member_id,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.individual_member_id),
//            )
//        } else if (iCount > 0 && iShow == 0) {
//            iValue = 1
//            validate!!.CustomAlert(
//                this,
//                getString(R.string.indiexits),
//            )

        } else if (spin_hh_id.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_hh_id,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.hh_id),
            )
        } else if (spin_im_id.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_im_id,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.individual_member_id),
            )
        } else if (spin_member_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_member_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_the_member),
            )
        } else if (spin_wp_nwp.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_wp_nwp,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.category),
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
//        }else if(et_contact_number.text.toString().contains("X")){
//            iValue = 0
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
        } else if (rg_savings_account.checkedRadioButtonId == -1) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.do_you_have_savings_bank_account),
            )
//        } else if (et_aadhar_card.text.toString()
//                .isNotEmpty() && et_aadhar_card.text.toString().length != 12
//        ) {
//            iValue = 1
//            validate!!.CustomAlert(
//                this,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.validaadhar_no),
//            )
//        }
        } else if (et_aadhar_card.text.toString()
                .isNotEmpty() && et_aadhar_card.text.toString().length != 12
        ) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + getString(R.string.aadhar12digit),
            )
        } else if (!VerhoeffAlgorithm.validateVerhoeff(et_aadhar_card.text.toString())) {
            iValue = 1
            validate!!.CustomAlert(
                this,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.validaadhar_no),
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
        val community = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
            ?.let { collectiveMemberViewModel.getCommunity(it) }

        var sWardPanchayat = CareIndiaApplication.database?.collectiveDao()
            ?.getPWCode(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)
        var ward_or_panchayat_code = CareIndiaApplication.database?.collectiveDao()
            ?.getPanchayat_Ward(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)

        hh_code = cin + sWardPanchayat + getCharacterNumber(
            ward_or_panchayat_code!!,
            "000"
        ) + initials + community
        coll_code_starting = hh_code

        return hh_code
    }


    fun fillDataFromIM(IndGUID: String) {

        CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(IndGUID)
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_member_age.setText(it.get(0).Age.toString())
                    spin_member_sex.setSelection(
                        validate!!.returnpos(
                            it.get(0).Gender,
                            mstLookupViewModel,
                            1,
                            iLanguageID
                        )
                    )
                    et_contact_number.setText(it.get(0).Contact)

                }
            })


    }

    fun returnIM_GUID(pos: Int?, hhguid: String): String {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndGUID

            }
        }
        return id
    }


    fun returnHH_GUID(
        pos: Int?, isUrban: Int,
        ZoneCode: Int,
        WardCode: Int
    ): String {
        var data: List<HouseholdProfileEntity>? = null

        if (isUrban == 1) {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
        } else if (isUrban == 2) {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(WardCode)
        }

        var id = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHGUID

            }
        }
        return id
    }

    fun returnposIM_GUID(id: String?, hhguid: String): Int {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id != null && id.length > 0) {
                for (i in data.indices) {
                    if (id.equals(data.get(i).IndGUID))
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun bindIMID(strValue: String, spin: Spinner, hhguid: String) {

        var zonedata: List<IndividualProfileEntity>? = null

        if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveMemberGUID) != null && validate!!.RetriveSharepreferenceString(
                AppSP.CollectiveMemberGUID
            )!!.trim().isNotEmpty()
        ) {
            zonedata =
                CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
        } else {
            zonedata =
                CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhguid)
//            zonedata =
//                CareIndiaApplication.database!!.primaryDataDao().findIdvPrfdata(hhguid)
        }

        val iGen = zonedata.size
        val name = arrayOfNulls<String>(iGen + 1)
        name[0] = strValue

        for (i in 0 until zonedata.size) {
            name[i + 1] = zonedata.get(i).IndvCode
        }
        val adapter_category = ArrayAdapter<String>(
            this,
            R.layout.my_spinner_space_dashboard, name
        )
        adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin.adapter = adapter_category


    }

    fun bindHHIDTable(
        strValue: String,
        spin: Spinner,
        zoneCode: Int,
        ward: Int,
        isUrban: Int,
        Panchayat: Int

    ) {
        val adapter_category: ArrayAdapter<String?>
        var it: List<HouseholdProfileEntity>? = null
        if (isUrban == 1) {
            it = CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(zoneCode, ward)
        } else {
            it = CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(Panchayat)
        }

        if (it.isNotEmpty()) {
            val iGen = it.size
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = strValue

            for (i in 0 until it.size) {
                name[i + 1] = it.get(i).HHCode
            }
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category

        } else {
            val sValue = arrayOfNulls<String>(it.size + 1)
            sValue[0] = strValue
            adapter_category = ArrayAdapter(
                this,
                R.layout.my_spinner_space_dashboard, sValue
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }

    }

    fun getCharacterNumber(character_number: Int, pattern: String): String {
        val df = DecimalFormat(pattern)
        return df.format(character_number)

    }

    fun returnUrban_rural(pos: Int?, StateCode: Int): Int {
        var data: List<MstDistrictEntity>? = null
        val list: List<String>? = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
            ?.split(",")?.let {
                listOf(
                    *it
                        .toTypedArray()
                )
            }
        data = list?.let {
            CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                StateCode,
                it
            )
        }

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
        val list: List<String>? = validate?.RetriveSharepreferenceString(AppSP.DistrictIn)
            ?.split(",")?.let {
                listOf(
                    *it
                        .toTypedArray()
                )
            }
        data = list?.let {
            CareIndiaApplication.database?.mstDistrictDao()?.getMstDist(
                StateCode,
                it
            )
        }

        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).DistrictCode!!

            }
        }
        return id
    }


    fun returnHHGUID(pos: Int?, hhCode: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallGUIDBYHHIDDdata(hhCode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHGUID

            }
        }
        Log.i("MyIdhh", "$id")
        return id
    }

    fun returnIMGUID(pos: Int?, indvCode: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallGUIDIMProfileBYIMIDDdata(indvCode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndGUID

            }
        }
        Log.i("MyIIMM", "$id")
        return id
    }
  fun returnIMCode(pos: Int?, indvGuid: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuidNew(indvGuid)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndvCode

            }
        }
        Log.i("MyIIMM", "$id")
        return id
    }

    fun returnIMGUIDTest(pos: Int?, indvCode: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(indvCode)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).IndGUID

            }
        }
        Log.i("MyIIMM", "$id")
        return id
    }
 fun returnHHcode(pos: Int?, hhGuid: String): String? {
        var data: List<IndividualProfileEntity>? = null
        data =
            CareIndiaApplication.database!!.imProfileDao().getallIMProfileBYHHGUIDdata(hhGuid)

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHCode

            }
        }
        Log.i("MyIIMM", "$id")
        return id
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        /*   val intent = Intent(this, CollectiveProfileActivityThird::class.java)
           startActivity(intent)
           finish()*/
    }

    fun CustomAlert(
        collectiveProfileMemberActivity: CollectiveProfileMemberActivity,
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
            val intent =
                Intent(collectiveProfileMemberActivity, CollectiveProfileActivityThird::class.java)
            startActivity(intent)
            finish()
            btnok.setTextColor(resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }

    fun returnposHHcode(
        strValue: String,
        DistCode: Int,
        ZoneCode: Int,
        WardCode: Int,
        PanchayatCode: Int
    ): Int {
        var posi = 0
        var hhcode: List<HouseholdProfileEntity>

        if (DistCode == 1) {
            hhcode = collectiveMemberViewModel.gethhProfileDataWard(ZoneCode, WardCode)
        } else {
            hhcode = collectiveMemberViewModel.gethhProfileDataPanchayat(PanchayatCode)
        }

        /* if (isUrban == 1) {
             hhcode = CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
         } else {
             hhcode = CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(PanchayatCode)
         }

 */

        if (hhcode != null) {

            for (i in 0 until hhcode.size) {

                if (strValue == hhcode.get(i).HHGUID) {
                    posi = i + 1
                }
            }

        }

        return posi
    }

    fun returnHhPrfID(
        pos: Int?,
        isUrban: Int,
        ZoneCode: Int,
        WardCode: Int,
        PanchayatCode: Int
    ): String? {
        var data: List<HouseholdProfileEntity>? = null
//        data = psychometricViewModel.gethhProfileDataNew()


        if (isUrban == 1) {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataZone(ZoneCode, WardCode)
        } else {
            data =
                CareIndiaApplication.database!!.hhProfileDao().gethhDataPanchayat(PanchayatCode)
        }

        var id: String? = ""

        if (!data.isNullOrEmpty()) {
            if (pos != null) {
                if (pos > 0)
                    id = data.get(pos - 1).HHCode

            }
        }
        Log.i("MyGETHHIDATA", "$id")
        return id
    }


}