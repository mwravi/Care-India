package com.careindia.lifeskills.views.prepostassessment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.AssessmentViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.MstTrainerViewModel
import com.careindia.lifeskills.viewmodel.TrainingViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_pre_post_assessment_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PrePostAssessmentListActivity : BaseActivity(), View.OnClickListener {
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var assessmentViewModel: AssessmentViewModel
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var validate: Validate
    var formattedDate: String = ""
    lateinit var mstTrainerViewModel: MstTrainerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_post_assessment_list)
        validate = Validate(this)
        assessmentViewModel = ViewModelProvider(this).get(AssessmentViewModel::class.java)
        mstLookupViewModel = ViewModelProvider(this).get(MstLookupViewModel::class.java)
        trainingViewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)
        mstTrainerViewModel = ViewModelProvider(this).get(MstTrainerViewModel::class.java)

        tv_title.text = resources.getString(R.string.pre_post_assessment)


        img_back.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetdateBeforeFiveDays()
        }

        et_dateform.setOnClickListener {
            validate.datePickerwithmindate(
                validate.DaybetweentimeBefore(validate.currentdatetimeNew),
                et_dateform
            )

        }

        et_dateform.setText(convertDateFormate(formattedDate))

        et_todate.setOnClickListener {
            validate.datePickerwithmindate(
                validate.DaybetweentimeBefore(validate.currentdatetimeNew),
                et_todate
            )
        }

        et_todate.setText(validate.currentdatetimeNew)



        et_dateform.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                fillRecyclerView(
                    validate.getDaysfromdates(et_dateform.text.toString(), 1),
                    validate.getDaysfromdates(et_todate.text.toString(), 1)
                )
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {



            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })


        et_todate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                fillRecyclerView(
                    validate.getDaysfromdates(et_dateform.text.toString(), 1),
                    validate.getDaysfromdates(et_todate.text.toString(), 1)
                )

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })




        initializeController()
    }

    override fun initializeController() {
        applyClickOnView()
        fillRecyclerView(
            validate.getDaysfromdates(et_dateform.text.toString(), 1),
            validate.getDaysfromdates(et_todate.text.toString(), 1)
        )

    }

    private fun applyClickOnView() {
        btn_add.setOnClickListener(this)
    }

    fun fillRecyclerView(dateto: Long, datefrom: Long) {
        trainingViewModel.getTrainingFilterData(dateto, datefrom).observe(this, Observer {
            rv_list.layoutManager = LinearLayoutManager(this)
            if (it != null && it.size > 0) {
                rv_list.adapter = PrePostAssessmentAdapter(
                    it,
                    this,
                    validate,
                    trainingViewModel,
                    mstLookupViewModel,
                    mstTrainerViewModel
                )
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_add -> {
                validate.SaveSharepreferenceString(AppSP.PPAGUID, "")
                validate.SaveSharepreferenceString(AppSP.PrePostID, "")
                validate.SaveSharepreferenceString(AppSP.groupBatchID, "")

                val intent = Intent(this, PrePostAssessmentLifeSkillActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun convertDateFormate(startDateString: String): String {
        var date = ""
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val sdf2 = SimpleDateFormat("dd-MM-yyyy")
            date = sdf2.format(sdf.parse(startDateString))
//            System.out.println(sdf2.format(sdf.parse(startDateString)))
        } catch (e: ParseException) {
            date = ""
            e.printStackTrace()
        }
        return date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetdateBeforeFiveDays() {
        val date: LocalDate = LocalDate.now()
        val dateMinus7Days: LocalDate = date.minusDays(7)
        //Format and display date
        formattedDate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        Log.i("MyTagDate","$formattedDate")

    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}