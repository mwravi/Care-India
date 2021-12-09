package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileSecondBinding
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collective_profile_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivitySec : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCollectiveProfileSecondBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveViewModel: CollectiveViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_second)
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
        tv_title.text = getString(R.string.collprofile)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }
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
        et_date_of_group_formation.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_group_formation
            )
        }

        fillSpinner()
        applyClickOnView()

    }

    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
        collectiveViewModel.Grouptype.observe(this, Observer {

            val lookupCode = validate!!.returnLookupCode(
                spin_collective_group,
                mstLookupViewModel,
                18,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_specify_others_group.visibility = VISIBLE
            } else {
                lay_specify_others_group.visibility = GONE
                et_specify_others_group.setText("")
            }

        })

        collectiveViewModel.Groupregistered.observe(this, Observer {
            val lookupCode = validate!!.returnLookupCode(
                spin_group_registered,
                mstLookupViewModel,
                19,
                iLanguageID
            )
            if (lookupCode == 99) {
                lay_specify_others_group_registered.visibility = VISIBLE
            } else {
                lay_specify_others_group_registered.visibility = GONE
                et_specify_others_group_registered.setText("")
            }
        })

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (checkValidation() == 0) {
                    collectiveViewModel.updatecollectiveprofilesecond(this)
                    val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, CollectiveProfileActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    fun showLiveData() {
        val collectiveGuid = validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
        collectiveViewModel.getCollectivedatabyGuid(validate!!.returnStringValue(collectiveGuid))
            .observe(this, Observer {
                if (it != null && it.size > 0) {
                    et_date_of_group_formation.setText(validate!!.returnStringValue(it.get(0).Date_formation))
                    spin_collective_group.setSelection(returnpos(it[0].Type, 18, iLanguageID))
                    et_specify_others_group.setText(it[0].TypeOther)
                    spin_group_registered.setSelection(
                        returnpos(
                            it[0].Registration,
                            19,
                            iLanguageID
                        )
                    )
                    spin_head_group_sex.setSelection(returnpos(it[0].Head_gender, 1, iLanguageID))
                    et_specify_others_group_registered.setText(it[0].RegistrationOther)
                    et_objective.setText(it.get(0).Objective)
                    et_head_group_name.setText(validate!!.returnStringValue(it.get(0).Head_name))
                    et_total_no_of_members.setText(validate!!.returnStringValue(it.get(0).NoMembers.toString()))
                    et_male_members.setText(validate!!.returnStringValue(it.get(0).NoMembers_M.toString()))
                    et_female_members.setText(validate!!.returnStringValue(it.get(0).NoMembers_F.toString()))
                    et_transgender_members.setText(validate!!.returnStringValue(it.get(0).NoMembers_T.toString()))
                }
            })
    }

    fun fillSpinner() {
        fillSpinner(
            resources.getString(R.string.select),
            spin_collective_group,
            18,
            iLanguageID
        )
        fillSpinner(
            resources.getString(R.string.select),
            spin_group_registered,
            19,
            iLanguageID
        )
        fillSpinner(
            resources.getString(R.string.select),
            spin_head_group_sex,
            1,
            iLanguageID
        )


    }

    fun checkValidation(): Int {
        var sumofMember =
            validate!!.returnIntegerValue(et_male_members.text.toString()) + validate!!.returnIntegerValue(
                et_female_members.text.toString()
            ) +
                    validate!!.returnIntegerValue(et_transgender_members.text.toString())

        var iValue = 0;
        if (et_date_of_group_formation.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_group_formation,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_formation_of_group),
            )
        } else if (spin_collective_group.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_collective_group,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.q112_what_type_of_collective_group_it_is),
            )
        } else if (et_specify_others_group.text.toString().length == 0 && lay_specify_others_group.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_others_group,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others),
            )
        } else if (spin_group_registered.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_group_registered,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.is_your_griup_registered),
            )
        } else if (et_specify_others_group_registered.text.toString().length == 0 && lay_specify_others_group_registered.visibility == VISIBLE) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_specify_others_group_registered,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.please_specify_others_registered),
            )
        } else if (et_objective.text.toString().isEmpty()) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_objective,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.what_is_the_objective_of_group),
            )
        } else if (et_head_group_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_head_group_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_head_group),
            )
        } else if (spin_head_group_sex.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_head_group_sex,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.sex_of_head_of_group),
            )
        } else if (et_total_no_of_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_no_of_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_members_in_group),
            )
        } else if (et_male_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_male_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_male_members_in_group),
            )
        } else if (et_female_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_female_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_female_members_in_group),
            )
        } else if (et_transgender_members.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_transgender_members,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.total_no_of_transgender_members_in_group),
            )
        } else if (validate!!.returnIntegerValue(et_total_no_of_members.text.toString()) != sumofMember) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_total_no_of_members,
                resources.getString(R.string.sum_of_collective_member),
            )
        }
        return iValue;
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

    fun bottomCLick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_third.setBackgroundColor(resources.getColor(R.color.back))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_six.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilesecond(this)
                val intent = Intent(this, CollectiveProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        /* lay_secnd.setOnClickListener {
             val intent = Intent(this, CollectiveProfileActivitySec::class.java)
             startActivity(intent)
             finish()
         }*/
        ll_third.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilesecond(this)
                val intent = Intent(this, CollectiveProfileActivityThird::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fourth.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilesecond(this)
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fifth.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilesecond(this)
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_six.setOnClickListener {
            if (checkValidation() == 0) {
                collectiveViewModel.updatecollectiveprofilesecond(this)
                val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                startActivity(intent)
                finish()
            }
        }
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


    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }


}