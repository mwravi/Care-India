package com.careindia.lifeskills.views.prepostassessment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.AssessmentDetailEntity
import com.careindia.lifeskills.entity.BeneficiaryDetailEntity
import com.careindia.lifeskills.entity.MstAssessmentEntity
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.model.GroupBatchModel
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.*
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.mw.survey.views.activities.DataProvider
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class PrePostQuizActivity : BaseActivity() {
    var validate: Validate? = null
    var iLanguageID = 0
    var size = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var mstAssessmentViewModel: MstAssessmentViewModel
    lateinit var beneficiaryDetailViewModel: BeneficiaryDetailViewModel
    lateinit var beneficiaryViewModel: BeneficiaryViewModel
    lateinit var assessmentDetailViewModel: AssessmentDetailViewModel
    val list = mutableListOf<GroupBatchModel>()
    var question: List<MstAssessmentEntity>? = null
    var questionPageNo: List<MstAssessmentEntity>? = null
    var linear: LinearLayout? = null
    var text1: TextView? =
        null
    var textNo: TextView? =
        null
    var text2: TextView? = null
    var tvIsMandatory: TextView? = null
    var tvQuestionTypeID: TextView? = null
    var tvDependentQuestion: TextView? = null
    var tvDependentOption: TextView? = null
    var currentpage = 0
    var alertMsg = ""
    var dataProvider = DataProvider()
    var which_button = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ben_quiz)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        tv_title.text = resources.getString(R.string.pre_post_assessment)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        mstAssessmentViewModel =
            ViewModelProviders.of(this).get(MstAssessmentViewModel::class.java)
        beneficiaryViewModel = ViewModelProvider(this).get(BeneficiaryViewModel::class.java)
        beneficiaryDetailViewModel =
            ViewModelProvider(this).get(BeneficiaryDetailViewModel::class.java)
        assessmentDetailViewModel =
            ViewModelProvider(this).get(AssessmentDetailViewModel::class.java)

        fillQuestion()
        initializeController()


        img_back.setOnClickListener {
            val intent = Intent(this, PrePostAssessmentLifeSkillActivity::class.java)
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


        btn_save.setOnClickListener {

            which_button = 1
            val checkValidation: Int = CheckValidation()
            if (checkValidation == 100 || checkValidation == 101) {
                validate!!.CustomAlert(this@PrePostQuizActivity, "  $alertMsg ")
            } else {
                SaveRecord()

                if (currentpage <= questionPageNo!!.size) {
                    currentpage += 1
                    lldynamic.visibility = View.VISIBLE
                    fillQuestion()
                }
                if (currentpage == question!!.size - 1) {
                    // button_Next.setText(myLang.getValue("save", R.string.save))
                }
            }

        }

        btn_prev.setOnClickListener {

            which_button = 2
            if (currentpage > 0) {
                currentpage -= 1
                fillQuestion()
            } else {
                val intent = Intent(this, PrePostAssessmentLifeSkillActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }


    fun fillSpinner(
        spin: Spinner,
        flag: Int,
        iLanguageID: Int
    ) {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguageID)

        val iGen = data.size

        if (flag == 124) {
            val name = arrayOfNulls<String>(iGen)
            for (i in 0 until data.size) {
                name[i] = data.get(i).Description
            }

            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        } else {
            val name = arrayOfNulls<String>(iGen + 1)
            name[0] = resources.getString(R.string.select)
            for (i in 0 until data.size) {
                name[i + 1] = data.get(i).Description
            }

            val adapter_category = ArrayAdapter<String>(
                this,
                R.layout.my_spinner_space_dashboard, name
            )
            adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
            spin.adapter = adapter_category
        }


    }


    override fun onBackPressed() {
        //super.onBackPressed()
//        val intent = Intent(this, PrePostAssessmentLifeSkillActivitySecond::class.java)
//        startActivity(intent)
//        finish()

    }


    fun returnID(
        pos: Int,
        flag: Int, iLanguage: Int
    ): Int {
        var id = 0
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)

        if (flag == 124) {
            id = -1
            if (!data.isNullOrEmpty()) {
                id = data.get(pos).LookupCode
            }
        } else {
            if (!data.isNullOrEmpty()) {
                if (pos > 0) id = data.get(pos - 1).LookupCode
            }
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

            if (flag == 124) {
                pos = -1
                if (id!! > -1) {
                    for (i in data.indices) {
                        if (id == data.get(i).LookupCode)
                            pos = i
                    }
                }
            } else {
                if (id!! > 0) {
                    for (i in data.indices) {
                        if (id == data.get(i).LookupCode)
                            pos = i + 1
                    }
                }
            }
        }
        return pos
    }

    fun CustomAlert(
        context: Context,
        msg: String?
    ) { // Create custom dialog object
        val dialog = Dialog(context)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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

            btnok.setTextColor(context.resources.getColor(R.color.white))
            val intent = Intent(this, PrePostAssessmentListActivity::class.java)
            startActivity(intent)
            finish()

            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }


    fun fillQuestion() {


        questionPageNo =
            mstAssessmentViewModel.getDistinctPage(validate!!.RetriveSharepreferenceInt(AppSP.ModuleID))


        if (questionPageNo != null) {
            for (i in currentpage until questionPageNo!!.size) {
                //  question.clear();
                linearQues.removeAllViews()
                idformName.text = validate!!.returnStringValue(questionPageNo!![i].PageHeader)
                question = mstAssessmentViewModel
                    .getAllQuestion(
                        validate!!.RetriveSharepreferenceInt(AppSP.ModuleID),
                        questionPageNo!![i].PageID!!
                    )


                if (question != null && question!!.size > 0) {


                    for (j in question!!.indices) {

                        val params1 = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        linear = LinearLayout(this)
                        params1.setMargins(10, 10, 0, 0)
                        linear!!.layoutParams = params1
                        linear!!.orientation = LinearLayout.VERTICAL
                        linear!!.id = Integer.valueOf(question!![j].QID)
                        linearQues.addView(linear)
                        val ppaGUID = validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)
                        val groupBatchID = validate!!.RetriveSharepreferenceInt(AppSP.groupBatchID)
                        val data = assessmentDetailViewModel.getData(
                            ppaGUID,
                            Integer.valueOf(question!![j].QID),
                            groupBatchID
                        )

                        val tblrow1 = TableRow(this)
                        val params = TableRow.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(
                            0,
                            resources.getDimension(R.dimen._5sdp).toInt(), 10, 0
                        )
                        tblrow1.layoutParams = params
                        linear!!.addView(tblrow1)


                        text1 = TextView(this)
                        text1!!.setTextAppearance(this, R.style.TextViewStylestrik)
                        text1!!.gravity = Gravity.CENTER
                        text1!!.text = "*"
                        text1!!.layoutParams = TableRow.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        tblrow1.addView(text1)

                        textNo = TextView(this)
                        textNo!!.setTextAppearance(this, R.style.TextViewStyleNo)
                        textNo!!.gravity = Gravity.CENTER
                        textNo!!.text = question!![j].QNo.toString()
                        textNo!!.layoutParams = TableRow.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        textNo!!.visibility = View.GONE
                        tblrow1.addView(textNo)


                        text2 = TextView(this)
                        text2!!.setTextAppearance(this, R.style.TextViewStyle)
                        text2!!.gravity = Gravity.START
                        text2!!.setPadding(5, 0, 0, 5)

                        text2!!.text = " " + question!![j].Question
                        text2!!.layoutParams = TableRow.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )

                        tblrow1.addView(text2)

                        tvIsMandatory = TextView(this)
                        tvIsMandatory!!.text = "1"
                        tvIsMandatory!!.visibility = View.GONE
                        linear!!.addView(tvIsMandatory)

                        tvQuestionTypeID = TextView(this)
                        tvQuestionTypeID!!.text = java.lang.String.valueOf(question!![j].QuestionType)
                        tvQuestionTypeID!!.visibility = View.GONE
                        linear!!.addView(tvQuestionTypeID)



                        tvDependentQuestion = TextView(this)
                        tvDependentQuestion!!.text = java.lang.String.valueOf(question!![j].DependentQuestion)
                        tvDependentQuestion!!.visibility = View.GONE
                        linear!!.addView(tvDependentQuestion)

                        tvDependentOption = TextView(this)
                        tvDependentOption!!.text = java.lang.String.valueOf(question!![j].DependentOption)
                        tvDependentOption!!.visibility = View.GONE
                        linear!!.addView(tvDependentOption)
                        if (question!![j].QuestionType == 1) {

                            val tbl_row_edit = TableRow(this)
                            val params_row_edit = TableRow.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            params_row_edit.setMargins(
                                0,
                                resources.getDimension(R.dimen._5sdp).toInt(), 10, 0
                            )
                            tbl_row_edit.layoutParams = params_row_edit
                            linear!!.addView(tbl_row_edit)


                            val edit_text_type =
                                layoutInflater.inflate(R.layout.edit_text_type, null) as EditText


                            if (data.size > 0) {


                            }
                            var ID = 0
                            if (data.size > 0) {

                                if (validate!!.RetriveSharepreferenceInt(AppSP.PrePostID) == 1) {
                                    ID = data.get(0).PreScore!!
                                } else {
                                    ID = data.get(0).PostScore!!
                                }

                            }




                            tbl_row_edit.addView(edit_text_type)


                        } else if (question!![j].QuestionType == 2) {

                            val tblrowspinner = TableRow(this)
                            val paramsspinner = TableRow.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            paramsspinner.setMargins(
                                0,
                                resources.getDimension(R.dimen._5sdp).toInt(), 10, 0
                            )
                            tblrowspinner.layoutParams = paramsspinner
                            linear!!.addView(tblrowspinner)


                            val spinner =
                                layoutInflater.inflate(R.layout.spinnner_type, null) as Spinner
                            fillSpinner(spinner, question!![j].LookupID!!, iLanguageID)




                            spinner.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }

                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {


                                        Setvisibility(
                                            question!![j].QID,
                                            returnID(
                                                position,
                                                question!![j].LookupID!!,
                                                iLanguageID
                                            )
                                        )


                                    }

                                }

                            spinner.id = question!![j].LookupID!!
                            var ID = 0
                            if (data.size > 0) {

                                if (validate!!.RetriveSharepreferenceInt(AppSP.PrePostID) == 1) {
                                    ID = data.get(0).PreScore!!
                                } else {
                                    ID = data.get(0).PostScore!!
                                }
                                spinner.setSelection(
                                    returnpos(
                                        ID,
                                        mstLookupViewModel,
                                        question!![j].LookupID!!,
                                        iLanguageID
                                    )
                                )
                            }
                            tblrowspinner.addView(spinner)


                        } else if (question!![j].QuestionType == 3) {
                            val tbl_row_multi = TableRow(this)
                            val params_row_multi = TableRow.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            params_row_multi.setMargins(
                                0,
                                resources.getDimension(R.dimen._5sdp).toInt(), 10, 0
                            )
                            tbl_row_multi.layoutParams = params_row_multi
                            linear!!.addView(tbl_row_multi)


                            val check_box_type =
                                layoutInflater.inflate(
                                    R.layout.check_box_type,
                                    null
                                ) as LinearLayout
                            dynamicMultiCheck(
                                this@PrePostQuizActivity,
                                check_box_type,
                                mstLookupViewModel,
                                question!![j].LookupID!!,
                                iLanguageID,
                                question!![j].QID
                            )

                            /*  if (data.size > 0) {
                                  validate!!.SetAnswerTypeCheckBoxButton(
                                      check_box_type,
                                      data.get(0).QuestionValue!!
                                  )

                              }*/
                            tbl_row_multi.addView(check_box_type)
                        } else if (question!![j].QuestionType == 4) {

                            val tbl_row_edit = TableRow(this)
                            val params_row_edit = TableRow.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            params_row_edit.setMargins(
                                0,
                                resources.getDimension(R.dimen._5sdp).toInt(), 10, 0
                            )
                            tbl_row_edit.layoutParams = params_row_edit
                            linear!!.addView(tbl_row_edit)


                            val edit_text_type =
                                layoutInflater.inflate(R.layout.edit_text_type, null) as EditText

                            edit_text_type.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

                            if (data.size > 0) {


                            }

                            var ID = 0
                            if (data.size > 0) {

                                if (validate!!.RetriveSharepreferenceInt(AppSP.PrePostID) == 1) {
                                    ID = data.get(0).PreScore!!
                                } else {
                                    ID = data.get(0).PostScore!!
                                }
                                edit_text_type.setText(ID.toString())
                            }

                            tbl_row_edit.addView(edit_text_type)


                        }

                    }

                } else {

                    if (which_button == 1) {
                        currentpage += 1
                        fillQuestion()
                    } else {
                        currentpage -= 1
                        fillQuestion()
                    }


                }
                break
            }
        }


    }


    fun CheckValidation(): Int {
        var iCheck = 1
        val iSize = linearQues.childCount
        var QusID = 0
        for (j in 0 until iSize) {
            var QusAns = ""
            var Question = ""
            var QuestionNo = ""
            var IsMandatory = 0
            var isVisibility: Boolean? = false
            val ll = linearQues.getChildAt(j) as LinearLayout
            QusID = ll.id
            if (ll.visibility == View.VISIBLE) {
                if (ll.childCount > 0) {
                    val tblrow1 = ll.getChildAt(0) as TableRow
                    if (tblrow1.childCount > 0) {
                        textNo = tblrow1.getChildAt(1) as TextView
                        QuestionNo = validate!!.returnStringValue(textNo!!.text.toString())

                        text2 = tblrow1.getChildAt(2) as TextView
                        Question = validate!!.returnStringValue(text2!!.text.toString())
                    }
                    tvIsMandatory = ll.getChildAt(1) as TextView
                    IsMandatory = validate!!.returnIntegerValue(tvIsMandatory!!.text.toString())
                    tvQuestionTypeID = ll.getChildAt(2) as TextView
                    tvDependentQuestion = ll.getChildAt(3) as TextView
                    tvDependentOption = ll.getChildAt(4) as TextView

                    val QuestionTypeID: Int =
                        validate!!.returnIntegerValue(tvQuestionTypeID!!.text.toString())

                    if (QuestionTypeID == 1) {
                        val tblrow = ll.getChildAt(5) as TableRow
                        if (tblrow.visibility == View.VISIBLE)
                            isVisibility = true
                        val edt_text = tblrow.getChildAt(0) as EditText
                        QusAns = edt_text.text.toString()

                    } else if (QuestionTypeID == 2) {
                        val tblrow = ll.getChildAt(5) as TableRow
                        if (tblrow.visibility == View.VISIBLE)
                            isVisibility = true
                        val spinner = tblrow.getChildAt(0) as Spinner
                        val spin_lookup_id = spinner.id
                        QusAns = returnID(
                            spinner.selectedItemPosition,
                            spinner.id,
                            iLanguageID
                        ).toString()
                        if (spin_lookup_id == 124 && validate!!.returnIntegerValue(QusAns) == -1) {
                            QusAns = ""
                        } else if (spin_lookup_id != 124 && validate!!.returnIntegerValue(QusAns) == 0) {
                            QusAns = ""
                        }


                    } else if (QuestionTypeID == 3) {
                        val tblrow = ll.getChildAt(5) as TableRow
                        if (tblrow.visibility == View.VISIBLE)
                            isVisibility = true
                        val checkBox = tblrow.getChildAt(0) as LinearLayout
                        QusAns = validate!!.GetAnswerTypeCheckBoxButtonID(checkBox)
                    } else if (QuestionTypeID == 4) {
                        val tblrow = ll.getChildAt(5) as TableRow
                        if (tblrow.visibility == View.VISIBLE)
                            isVisibility = true
                        val edt_no_type = tblrow.getChildAt(0) as EditText
                        QusAns = edt_no_type.text.toString()

                        if (validate!!.returnIntegerValue(QusAns) > validate!!.RetriveSharepreferenceInt(
                                AppSP.TotalParticipant
                            ) && isVisibility == true
                        ) {

                            iCheck = 100
                            alertMsg = "Please enter valid values"
                            break
                        }

                    }


                    if (IsMandatory == 1 && QusAns.length == 0 && isVisibility == true) {
                        iCheck = 100
                        alertMsg = "Please select " + QuestionNo + " " + Question
                        break
                    }
                }
            }
        }
        return iCheck
    }


    fun SaveRecord() {
        val iSize = linearQues.childCount
        for (j in 0 until iSize) {
            var QusAns = ""
            var Question: String? = ""
            var IsMandatory = 0
            var QusID = 0
            val ll = linearQues.getChildAt(j) as LinearLayout
            QusID = ll.id
            if (ll.visibility == View.VISIBLE) {
                if (ll.childCount > 0) {
                    val tblrow1 = ll.getChildAt(0) as TableRow
                    if (tblrow1.childCount > 0) {
                        text2 = tblrow1.getChildAt(1) as TextView
                        Question = validate!!.returnStringValue(text2!!.text.toString())
                    }
                    tvIsMandatory = ll.getChildAt(1) as TextView
                    IsMandatory = validate!!.returnIntegerValue(tvIsMandatory!!.text.toString())
                    tvQuestionTypeID = ll.getChildAt(2) as TextView
                    tvDependentQuestion = ll.getChildAt(3) as TextView
                    tvDependentOption = ll.getChildAt(4) as TextView
                    val QuestionTypeID: Int =
                        validate!!.returnIntegerValue(tvQuestionTypeID!!.text.toString())
                    if (QuestionTypeID == 1) {
                        val tblrow = ll.getChildAt(5) as TableRow

                        val edt_text = tblrow.getChildAt(0) as EditText
                        QusAns = edt_text.text.toString()

                    } else if (QuestionTypeID == 2) {
                        val tblrow = ll.getChildAt(5) as TableRow

                        val spinner = tblrow.getChildAt(0) as Spinner
                        QusAns = returnID(
                            spinner.selectedItemPosition,
                            spinner.id,
                            iLanguageID
                        ).toString()

                    } else if (QuestionTypeID == 3) {
                        val tblrow = ll.getChildAt(5) as TableRow

                        val checkBox = tblrow.getChildAt(0) as LinearLayout
                        QusAns = validate!!.GetAnswerTypeCheckBoxButtonID(checkBox)
                    } else if (QuestionTypeID == 4) {
                        val tblrow = ll.getChildAt(5) as TableRow

                        val edt_no_type = tblrow.getChildAt(0) as EditText
                        QusAns = edt_no_type.text.toString()

                    }


                    val groupBatchID = validate!!.RetriveSharepreferenceInt(AppSP.groupBatchID)

                    val count = assessmentDetailViewModel.getAllCount(
                        validate!!.RetriveSharepreferenceInt(AppSP.TrainingID),
                        QusID,
                        groupBatchID
                    )


                    if (count > 0) {
                        if (validate!!.RetriveSharepreferenceInt(AppSP.PrePostID) == 1) {

                            assessmentDetailViewModel.updatePre(
                                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID),
                                QusID,
                                validate!!.returnIntegerValue(QusAns),
                                0,
                                groupBatchID
                            )
                        } else {


                            assessmentDetailViewModel.updatePost(
                                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID),
                                QusID,
                                validate!!.returnIntegerValue(QusAns),
                                0,
                                groupBatchID
                            )
                        }
                    } else {
                        var PreScore = 0
                        var PostScore = 0
                        var AScore = 0
                        if (validate!!.RetriveSharepreferenceInt(AppSP.PrePostID) == 1) {

                            PreScore = validate!!.returnIntegerValue(QusAns)
                            PostScore = -1
                        } else {

                            PostScore = validate!!.returnIntegerValue(QusAns)
                            PreScore = -1

                        }

                        assessmentDetailViewModel.insert(
                            AssessmentDetailEntity(
                                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID),
                                QusID,
                                PreScore,
                                PostScore,
                                AScore,
                                groupBatchID,
                                1
                            )
                        )
                    }


                }
            }
        }

        if (currentpage == questionPageNo!!.size - 1) {

            //beneficiaryViewModel.updateIsCompleted(validate!!.RetriveSharepreferenceString(AppSP.Ben_GUID),4,1)
            CustomAlert(this, resources.getString(R.string.data_saved_successfully))

        }
    }


    fun Setvisibility(qid: Int, returnID: Int) {
        var iCheck = 1
        val iSize = linearQues.childCount
        var QusID = 0
        var DependentQuestion = 0
        var DependentOption = 0
        for (j in 0 until iSize) {
            var QusAns = ""
            var Question = ""
            var IsMandatory = 0
            val ll = linearQues.getChildAt(j) as LinearLayout
            QusID = ll.id
            if (ll.visibility == View.VISIBLE) {
                if (ll.childCount > 0) {
                    val tblrow1 = ll.getChildAt(0) as TableRow
                    if (tblrow1.childCount > 0) {
                        text2 = tblrow1.getChildAt(1) as TextView
                        Question = validate!!.returnStringValue(text2!!.text.toString())
                    }
                    tvIsMandatory = ll.getChildAt(1) as TextView
                    IsMandatory = validate!!.returnIntegerValue(tvIsMandatory!!.text.toString())
                    tvQuestionTypeID = ll.getChildAt(2) as TextView
                    tvDependentQuestion = ll.getChildAt(3) as TextView
                    DependentQuestion =
                        validate!!.returnIntegerValue(tvDependentQuestion!!.text.toString())
                    tvDependentOption = ll.getChildAt(4) as TextView
                    DependentOption =
                        validate!!.returnIntegerValue(tvDependentOption!!.text.toString())
                    val QuestionTypeID: Int =
                        validate!!.returnIntegerValue(tvQuestionTypeID!!.text.toString())

                    if (qid == DependentQuestion && returnID == DependentOption) {
                        val tblrow2 = ll.getChildAt(5) as TableRow
                        tblrow1.visibility = View.VISIBLE
                        tblrow2.visibility = View.VISIBLE


                    } else if (qid == DependentQuestion) {
                        val tblrow2 = ll.getChildAt(5) as TableRow
                        tblrow1.visibility = View.GONE
                        tblrow2.visibility = View.GONE

                        if (QuestionTypeID == 1 || QuestionTypeID == 4) {
                            val edt_other = tblrow2.getChildAt(0) as EditText
                            edt_other.setText("")
                        } else if (QuestionTypeID == 2) {
                            val spinner = tblrow2.getChildAt(0) as Spinner
                            spinner.setSelection(0)
                        }

                    }


                }
            }
        }

    }

    fun SetvisibilityMulti(qid: Int, returnID: Int, flag: Int) {
        var iCheck = 1
        val iSize = linearQues.childCount
        var QusID = 0
        var DependentQuestion = 0
        var DependentOption = 0
        for (j in 0 until iSize) {
            var QusAns = ""
            var Question = ""
            var IsMandatory = 0
            val ll = linearQues.getChildAt(j) as LinearLayout
            QusID = ll.id
            if (ll.visibility == View.VISIBLE) {
                if (ll.childCount > 0) {
                    val tblrow1 = ll.getChildAt(0) as TableRow
                    val tblrow2 = ll.getChildAt(5) as TableRow
                    if (tblrow1.childCount > 0) {
                        text2 = tblrow1.getChildAt(1) as TextView
                        Question = validate!!.returnStringValue(text2!!.text.toString())
                    }
                    tvIsMandatory = ll.getChildAt(1) as TextView
                    IsMandatory = validate!!.returnIntegerValue(tvIsMandatory!!.text.toString())
                    tvQuestionTypeID = ll.getChildAt(2) as TextView
                    tvDependentQuestion = ll.getChildAt(3) as TextView
                    DependentQuestion =
                        validate!!.returnIntegerValue(tvDependentQuestion!!.text.toString())
                    tvDependentOption = ll.getChildAt(4) as TextView
                    DependentOption =
                        validate!!.returnIntegerValue(tvDependentOption!!.text.toString())
                    val QuestionTypeID: Int =
                        validate!!.returnIntegerValue(tvQuestionTypeID!!.text.toString())
                    /*    tblrow1.visibility = View.GONE
                        tblrow2.visibility = View.GONE*/
                    if (qid == DependentQuestion && returnID == DependentOption && flag == 0) {
                        tblrow1.visibility = View.VISIBLE
                        tblrow2.visibility = View.VISIBLE


                    } else if (qid == DependentQuestion) {

                        tblrow1.visibility = View.GONE
                        tblrow2.visibility = View.GONE

                        for (i in 0 until tblrow2.childCount) {
                            val edt_other = tblrow2.getChildAt(0) as EditText
                            edt_other.setText("")
                        }
                    }


                }
            }
        }

    }


    fun dynamicMultiCheck(
        context: Context, liear: LinearLayout, mstCommonViewModel: MstLookupViewModel?,
        flag: Int, iLanguageID: Int, qid: Int
    ) {

        var data: List<MstLookupEntity>? = null
        data =
            mstCommonViewModel!!.getMstAllData(flag, iLanguageID)
        val iGen = data.size
        for (i in 0 until data.size) {
            val multicheck1 = CheckBox(context)
            multicheck1.layoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            multicheck1.text = data.get(i).Description?.trim()
            multicheck1.id = data.get(i).LookupCode
            liear.addView(multicheck1)



            multicheck1.setOnCheckedChangeListener { compoundButton, b ->
                if (multicheck1.isChecked) {
                    if (multicheck1.id == 99) {
                        SetvisibilityMulti(
                            qid,
                            multicheck1.id,
                            0
                        )

                        validate!!.CustomToast(
                            this@PrePostQuizActivity,
                            "Check"
                        )

                    }
                } else {
                    if (multicheck1.id == 99) {
                        validate!!.CustomToast(
                            this@PrePostQuizActivity,
                            "UnCheck"
                        )

                        SetvisibilityMulti(
                            qid,
                            multicheck1.id,
                            1
                        )
                    }
                }
            }

        }

    }


}