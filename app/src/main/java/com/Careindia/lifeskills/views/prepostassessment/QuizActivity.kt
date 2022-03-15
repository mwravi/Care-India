package com.careindia.lifeskills.views.prepostassessment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.AssessmentDetailEntity
import com.careindia.lifeskills.entity.MstAssessmentEntity
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.model.GroupBatchModel
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.AssessmentDetailViewModel
import com.careindia.lifeskills.viewmodel.AssessmentViewModel
import com.careindia.lifeskills.viewmodel.MstAssessmentViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class QuizActivity : BaseActivity() {
    var validate: Validate? = null
    var iLanguageID = 0
    var size = 0
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var mstAssessmentViewModel: MstAssessmentViewModel
    lateinit var assessmentDetailViewModel: AssessmentDetailViewModel
    lateinit var assessmentViewModel: AssessmentViewModel
    val list = mutableListOf<GroupBatchModel>()
    var question: List<MstAssessmentEntity>? = null
    var questionPageNo: List<MstAssessmentEntity>? = null
    var linear: LinearLayout? = null
    var text1: TextView? =
        null
    var text2: TextView? = null
    var tvIsMandatory: TextView? = null
    var tvQuestionTypeID: TextView? = null
    var currentpage = 0
    var alertMsg=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)

        tv_title.text = resources.getString(R.string.pre_post_assessment)

        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)

        mstAssessmentViewModel =
            ViewModelProviders.of(this).get(MstAssessmentViewModel::class.java)
        assessmentViewModel = ViewModelProvider(this).get(AssessmentViewModel::class.java)
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



            val checkValidation: Int = CheckValidation()
            if (checkValidation == 100 || checkValidation == 101) {
                validate!!.CustomAlert(this@QuizActivity, "  $alertMsg ")
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


            if (currentpage>0) {
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
            var data: List<MstLookupEntity>? = null
            data =
                mstLookupViewModel.getLookup(flag, iLanguage)
            var id = -1

            if (!data.isNullOrEmpty()) {
                id = data.get(pos).LookupCode
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
            var pos = -1
            if (!data.isNullOrEmpty()) {
                if (id!! > -1) {
                    for (i in data.indices) {
                        if (id == data.get(i).LookupCode)
                            pos = i
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


            questionPageNo = mstAssessmentViewModel.getDistinctPage(validate!!.RetriveSharepreferenceInt(AppSP.ModuleID))


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
                            linear!!.setLayoutParams(params1)
                            linear!!.setOrientation(LinearLayout.VERTICAL)
                            linear!!.setId(Integer.valueOf(question!![j].QID))
                            linearQues.addView(linear)
                            val ppaGUID = validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!
                            val groupBatchID = validate!!.RetriveSharepreferenceInt(AppSP.groupBatchID)
                            val data = assessmentDetailViewModel.getData(ppaGUID, Integer.valueOf(question!![j].QID), groupBatchID)

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
                            text1!!.setGravity(Gravity.CENTER)
                            text1!!.text = "*"
                            text1!!.setLayoutParams(
                                TableRow.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                            )
                            tblrow1.addView(text1)


                            text2 = TextView(this)
                            text2!!.setTextAppearance(this, R.style.TextViewStyle)
                            text2!!.setGravity(Gravity.START)
                            text2!!.setPadding(5, 0, 0, 5)
                            text2!!.text = question!![j].Question
                            text2!!.setLayoutParams(
                                TableRow.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                            )
                            tblrow1.addView(text2)

                            tvIsMandatory = TextView(this)
                            tvIsMandatory!!.setText("1")
                            tvIsMandatory!!.setVisibility(View.GONE)
                            linear!!.addView(tvIsMandatory)

                            tvQuestionTypeID = TextView(this)
                            tvQuestionTypeID!!.setText(java.lang.String.valueOf(question!![j].QuestionType))
                            tvQuestionTypeID!!.setVisibility(View.GONE)
                            linear!!.addView(tvQuestionTypeID)

                            if (question!![j].QuestionType == 2) {

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
                                spinner.id= question!![j].LookupID!!
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


                            }

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
                        val QuestionTypeID: Int =
                            validate!!.returnIntegerValue(tvQuestionTypeID!!.text.toString())

                        if (QuestionTypeID == 2) {
                            val tblrow = ll.getChildAt(3) as TableRow
                            for (i in 0 until tblrow.childCount) {
                                val spinner = tblrow.getChildAt(0) as Spinner
                                QusAns = returnID(spinner.selectedItemPosition,spinner.id,iLanguageID).toString()
                            }
                        }


                        if (IsMandatory == 1 && validate!!.returnIntegerValue(QusAns) == -1) {
                            iCheck = 100
                            alertMsg = "Please select "+Question
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
                    val QuestionTypeID: Int =
                        validate!!.returnIntegerValue(tvQuestionTypeID!!.text.toString())
                  if (QuestionTypeID == 2) {
                        val tblrow = ll.getChildAt(3) as TableRow
                        for (i in 0 until tblrow.childCount) {
                            val spinner = tblrow.getChildAt(0) as Spinner
                            QusAns = returnID(spinner.selectedItemPosition,spinner.id,iLanguageID).toString()
                        }
                    }


                    val groupBatchID = validate!!.RetriveSharepreferenceInt(AppSP.groupBatchID)
                    var AggScore = 0

                    val count = assessmentDetailViewModel.getAllCount(
                        validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!,
                        QusID,
                        groupBatchID
                    )
                    if (count > 0) {
                        if (validate!!.RetriveSharepreferenceInt(AppSP.PrePostID) == 1) {

                            var score = assessmentDetailViewModel.getPostScore(
                                validate!!.RetriveSharepreferenceInt(
                                    AppSP.TrainingID
                                )!!, QusID, groupBatchID
                            )
                            if(score == -1){
                                score = 0
                            }
                            AggScore = validate!!.returnIntegerValue(QusAns) + score

                            assessmentDetailViewModel.updatePre(
                                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!,
                                QusID,
                                validate!!.returnIntegerValue(QusAns),
                                AggScore,
                                groupBatchID
                            )
                        } else {

                            var score = assessmentDetailViewModel.getPreScore(
                                validate!!.RetriveSharepreferenceInt(
                                    AppSP.TrainingID
                                )!!, QusID, groupBatchID
                            )
                            if(score == -1){
                                score = 0
                            }
                            AggScore = validate!!.returnIntegerValue(QusAns) + score

                            assessmentDetailViewModel.updatePost(
                                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!,
                                QusID,
                                validate!!.returnIntegerValue(QusAns),
                                AggScore,
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
                            AScore = PreScore + 0
                        } else {

                            PostScore = validate!!.returnIntegerValue(QusAns)
                            PreScore = -1
                            AScore = PostScore + 0
                        }

                        assessmentDetailViewModel.insert(
                            AssessmentDetailEntity(
                                validate!!.RetriveSharepreferenceInt(AppSP.TrainingID)!!,
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

            assessmentViewModel.updateIsCompleted(validate!!.RetriveSharepreferenceInt(AppSP.TrainingID),validate!!.RetriveSharepreferenceInt(AppSP.ModuleID),1)
            CustomAlert(this,resources.getString(R.string.data_saved_successfully))

        }
    }

}