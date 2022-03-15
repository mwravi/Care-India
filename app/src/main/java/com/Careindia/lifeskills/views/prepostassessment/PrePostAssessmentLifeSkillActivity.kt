package com.careindia.lifeskills.views.prepostassessment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.model.PrePostModel
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.AssessmentViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.TrainingViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_improfile_one.*
import kotlinx.android.synthetic.main.activity_ppa_life_skill.*
import kotlinx.android.synthetic.main.activity_ppa_life_skill.et_crp_name
import kotlinx.android.synthetic.main.activity_ppa_life_skill.et_sfc_name
import kotlinx.android.synthetic.main.activity_ppa_life_skill.spin_group_batch_id
import kotlinx.android.synthetic.main.activity_ppa_life_skill.spin_pre_post
import kotlinx.android.synthetic.main.activity_ppa_life_skill_second.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_prev
import kotlinx.android.synthetic.main.buttons_save_cancel.btn_save
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PrePostAssessmentLifeSkillActivity : BaseActivity(), View.OnClickListener {
    var validate: Validate? = null
    var formattedDate = ""
    var iLanguageID = 0
    var dateoftrainingFrom = ""
    var batchid = 0

    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var assessmentViewModel: AssessmentViewModel
    lateinit var trainingViewModel: TrainingViewModel
    var listim: List<IndividualProfileEntity>? = null
    var listTraining: List<PrePostModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ppa_life_skill)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        mstLookupViewModel = ViewModelProvider(this).get(MstLookupViewModel::class.java)
        assessmentViewModel = ViewModelProvider(this).get(AssessmentViewModel::class.java)
        trainingViewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)

        tv_title.text = resources.getString(R.string.pre_post_assessment)
        fillSpinner()

        initializeController()

        trainingViewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)
        listim =
            trainingViewModel.getTrainingIndvData(validate!!.RetriveSharepreferenceInt(AppSP.TrainingID))

        if (!listim.isNullOrEmpty()) {
            var locfind = listim!!.get(0).Locality
            var genderfind = listim!!.get(0).Gender

            et_community_name.setText(locfind)
        }



//        if (validate!!.RetriveSharepreferenceString(AppSP.PPAGUID) != null && validate!!.RetriveSharepreferenceString(
//                AppSP.PPAGUID
//            )!!.trim().length > 0
//        ) {
//            showLiveData()
//        }


        if (validate!!.RetriveSharepreferenceInt(AppSP.TrainingID) != null) {
            showLiveData()
        }

        listTraining =
            trainingViewModel.getTrainingParticipantData(validate!!.RetriveSharepreferenceInt(AppSP.TrainingID))
        if (!listTraining.isNullOrEmpty()) {
            var malefind = listTraining!!.get(0).Male

            et_male_participate.setText(listTraining!!.get(0).Male.toString())
            et_female_participate.setText(listTraining!!.get(0).Female.toString())
            et_transgender_participate.setText(listTraining!!.get(0).Transgender.toString())

        }

    }

    override fun initializeController() {
        applyClickOnView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }
    }

    fun fillSpinner() {

        et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
        et_sfc_name.setText(validate!!.RetriveSharepreferenceString(AppSP.FCID_Name))

//        et_date_of_filling.setOnClickListener {
//            validate!!.datePickerwithmindate(
//                validate!!.DaybetweentimeBefore(formattedDate),
//                et_date_of_filling
//            )
//        }
        fillSpinnerlookup(
            resources.getString(R.string.select),
            spin_pre_post,
            114,
            iLanguageID
        )

        fillSpinnerlookup(
            resources.getString(R.string.select),
            spin_NameofTrainingmodule,
            115,
            iLanguageID
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(5)
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)

    }


    private fun applyClickOnView() {
        btn_save.setOnClickListener(this)
        btn_prev.setOnClickListener(this)
        img_back.setOnClickListener(this)
        img_setting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> {
                if (CheckValidation() == 0) {
                    saveData()
                    saveDataUpdate()
                    val intent = Intent(this, PrePostQuizActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btn_prev -> {
                val intent = Intent(this, PrePostAssessmentListActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.img_back -> {
                val intent = Intent(this, PrePostAssessmentListActivity::class.java)
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

    private fun CheckValidation(): Int {
        var iValue = 0

        if (et_crp_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_crp_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_person_filling_the_form)
            )
        } else if (et_sfc_name.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_sfc_name,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.Supervising_fc)
            )
        } else if (spin_NameofTrainingmodule.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_NameofTrainingmodule,
                resources.getString(R.string.please_select) + " " + resources.getString(R.string.name_of_training_module)
            )
        } else if (et_trainingLocation.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_trainingLocation,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.training_location)
            )
        } else if (et_date_of_filling.text.toString().length == 0) {
            iValue = 1
            validate!!.CustomAlertEdit(
                this,
                et_date_of_filling,
                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.date_of_form_filling)
            )
//        } else if (et_community_name.text.toString().length == 0) {
//            iValue = 1
//            validate!!.CustomAlertEdit(
//                this,
//                et_community_name,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.name_of_community)
//            )
//        } else if (et_group_batch_id.text.toString().length == 0) {
//            iValue = 1
//            validate!!.CustomAlertEdit(
//                this,
//                et_group_batch_id,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.group_batch_id)
//            )
//        } else if (et_male_participate.text.toString().length == 0) {
//            iValue = 1
//            validate!!.CustomAlertEdit(
//                this,
//                et_male_participate,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.male)
//            )
//        } else if (et_female_participate.text.toString().length == 0) {
//            iValue = 1
//            validate!!.CustomAlertEdit(
//                this,
//                et_female_participate,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.female)
//            )
//        } else if (et_transgender_participate.text.toString().length == 0) {
//            iValue = 1
//            validate!!.CustomAlertEdit(
//                this,
//                et_transgender_participate,
//                resources.getString(R.string.please_enter) + " " + resources.getString(R.string.transgender)
//            )
        } else if (spin_pre_post.selectedItemPosition == 0) {
            iValue = 1
            validate!!.CustomAlertSpinner(
                this,
                spin_pre_post,
                resources.getString(R.string.please_select_pre_test_post_test)
            )

//        } else if (spin_group_batch_id.selectedItemPosition == 0) {
//            iValue = 1
//            validate!!.CustomAlertSpinner(
//                this,
//                spin_group_batch_id,
//                resources.getString(R.string.please_select_group_batch_id)
//            )
        }

        return iValue
    }

    fun fillSpinnerlookup(
        strValue: String,
        spin: Spinner,
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


    fun saveDataUpdate() {

        validate!!.SaveSharepreferenceInt(
            AppSP.ModuleID,
            returnID(spin_NameofTrainingmodule.selectedItemPosition, 115, iLanguageID)
        )

        validate!!.SaveSharepreferenceInt(
            AppSP.PrePostID,
            returnID(spin_pre_post.selectedItemPosition, 114, iLanguageID)
        )

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
            if (pos > 0)
                id = data.get(pos - 1).LookupCode
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

    fun setDefBlank(edi: EditText, data: Int) {
        if (data < 0) edi.setText("")
        else edi.setText(data.toString())

    }

    fun showLiveData() {
        val trainingId = validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)
        if (trainingId != null) {
            trainingViewModel.getTrainingDatabyTrainingID(trainingId).observe(
                this, Observer {
                    if (it != null && it.size > 0) {
                        et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
                        et_sfc_name.setText(validate!!.RetriveSharepreferenceString(AppSP.FCID_Name))
                        spin_NameofTrainingmodule.setSelection(
                            returnpos(
                                it.get(0).ModuleID,
                                115,
                                iLanguageID
                            )
                        )
                        batchid = it.get(0).BatchID!!
                        val them = arrayOf<String>(it.get(0).BatchID!!.toString())
                        var adaptertheme =
                            ArrayAdapter(this, R.layout.my_spinner_space_dashboard, them)
                        adaptertheme.setDropDownViewResource(R.layout.my_spinner_dashboard)
                        spin_group_batch_id?.adapter = adaptertheme
                        spin_group_batch_id.isEnabled = false
                        et_trainingLocation.setText(it.get(0).VenuOfTraning!!)
                        dateoftrainingFrom= validate!!.addDays(it.get(0).DateoftrainingFrom!!.toInt())
                        var  DateoftrainingTo=    " / "+  validate!!.addDays(it.get(0).DateoftrainingTo!!.toInt())
                        et_date_of_filling.setText(
                            dateoftrainingFrom+DateoftrainingTo
                            )

//                        spin_group_batch_id.setSelection(
//                                it.get(0).BatchID!!.toInt(),
//                        )

//                        setDefBlank(et_group_batch_id, it.get(0).NoBaches!!)

                        //  setDefBlank(et_male_participate, it.get(0).ParticipantsM!!)
                        //  setDefBlank(et_female_participate, it.get(0).ParticipantsF!!)
                    }
                }
            )
        }
    }

//    fun showLiveData() {
//        val ppaGuid = validate!!.RetriveSharepreferenceString(AppSP.PPAGUID)
//        if (ppaGuid != null) {
//            assessmentViewModel.getLiveAssessmentbyGuid(ppaGuid).observe(
//                this, Observer {
//                    if (it != null && it.size > 0) {
//                        if(it.get(0).IsEdited == 0 && it.get(0).Status == 0){
//                            btn_bottom.visibility = View.GONE
//                        }else{
//                            btn_bottom.visibility = View.VISIBLE
//                        }
//                        et_crp_name.setText(validate!!.RetriveSharepreferenceString(AppSP.CRPID_Name))
//                        et_sfc_name.setText(validate!!.RetriveSharepreferenceString(AppSP.FCID_Name))
//                        spin_NameofTrainingmodule.setSelection(
//                            returnpos(
//                                it.get(0).ModuleID,
//                                115,
//                                iLanguageID
//                            )
//                        )
//                        et_trainingLocation.setText(it.get(0).TrainingLocation!!)
//                        et_date_of_filling.setText(validate!!.addDays(it.get(0).DateForm!!.toInt()))
//                        et_community_name.setText(it.get(0).Community!!)
//
////                        setDefBlank(et_group_batch_id, it.get(0).NoBaches!!)
//
//                        setDefBlank(et_male_participate, it.get(0).ParticipantsM!!)
//                        setDefBlank(et_female_participate, it.get(0).ParticipantsF!!)
//                    }
//                }
//            )
//        }
//    }

    fun saveData() {
        validate!!.SaveSharepreferenceInt(
            AppSP.ModuleID,
            returnID(spin_NameofTrainingmodule.selectedItemPosition, 115, iLanguageID)
        )

       var iCount= assessmentViewModel.getAssessmentCount(validate!!.RetriveSharepreferenceInt(AppSP.TrainingID))

        if (iCount==0) {
            assessmentViewModel.insert(
                AssessmentEntity(
                    validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!,
                    returnID(spin_NameofTrainingmodule.selectedItemPosition, 115, iLanguageID),
                    validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                    validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                    et_trainingLocation.text.toString(),
                    validate!!.getDaysfromdates(dateoftrainingFrom, 1),
                    et_community_name.text.toString(),
                    batchid,
                    validate!!.returnIntegerValue(et_male_participate.text.toString()),
                    validate!!.returnIntegerValue(et_female_participate.text.toString()),
                    validate!!.returnIntegerValue(et_transgender_participate.text.toString()),
                    validate!!.RetriveSharepreferenceInt(AppSP.userId),
                    validate!!.getDaysfromdates(validate!!.currentdatetime, 2),
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    1
                )
            )
        } else {
            assessmentViewModel.updateAssessment(
                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!,
                returnID(spin_NameofTrainingmodule.selectedItemPosition, 115, iLanguageID),
                validate!!.RetriveSharepreferenceInt(AppSP.CRPID),
                validate!!.RetriveSharepreferenceInt(AppSP.FCID),
                et_trainingLocation.text.toString(),
                validate!!.getDaysfromdates(dateoftrainingFrom, 1),
                et_community_name.text.toString(),
                batchid,
                validate!!.returnIntegerValue(et_male_participate.text.toString()),
                validate!!.returnIntegerValue(et_female_participate.text.toString()),
                validate!!.returnIntegerValue(et_transgender_participate.text.toString()),
                validate!!.RetriveSharepreferenceInt(AppSP.userId),
                validate!!.getDaysfromdates(validate!!.currentdatetime, 2)
            )
        }
    }
}