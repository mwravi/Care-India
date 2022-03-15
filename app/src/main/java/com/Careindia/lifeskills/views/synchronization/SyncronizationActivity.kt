package com.careindia.lifeskills.views.synchronization

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.*
import com.careindia.lifeskills.services.ApiCallback
import com.careindia.lifeskills.services.ApiClientConnection
import com.careindia.lifeskills.services.response.LoginResponse
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sync_dashboard.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncronizationActivity : AppCompatActivity() {

    private var tableName: Array<String>? = null
    var validate: Validate? = null
    lateinit var progressDialog: ProgressDialog
    var apiInterface: ApiCallback? = null
    var username = ""
    var password = ""
    var hhdata: List<HouseholdProfileEntity>? = null
    var individualdata: List<IndividualProfileEntity>? = null
    var primarydata: List<PrimaryDataEntity>? = null
    var psychometricdata: List<PsychometricEntity>? = null
    var assessmentDetaildata: List<AssessmentDetailEntity>? = null
    var assessmentdata: List<AssessmentEntity>? = null
    var beneficiarydata: List<BeneficiaryEntity>? = null
    var beneficiaryDetaildata: List<BeneficiaryDetailEntity>? = null

    var collectiveMeetingdata: List<CollectiveMeetingEntity>? = null
    var collProfile = 0
    var collMember = 0
    var assessmentDetail = 0
    var beneficiaryDetail = 0
    var assessment = 0
    var beneficiary = 0
    var cptdata: List<CollectiveProgressTrackerEntity>? = null

    var ihhupload = 0
    var iImupload = 0
    var iedpupload = 0
    var ipsyupload = 0
    var imeetgupload = 0
    var icprfupload = 0
    var icptupload = 0
    var icprepostupload = 0
    var icbeneficiaryupload = 0

    var ihhdownload = 0
    var iImdownload = 0
    var iedpdownload = 0
    var ipsydownload = 0
    var imeetgdownload = 0
    var icprfdownload = 0
    var icptdownload = 0
    var icprepostdownload = 0
    var icbeneficiarydownload = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_dashboard)
        validate = Validate(this)
        tv_title.text = getString(R.string.sync)

        apiInterface = ApiClientConnection.instance.createApiInterface()
        username = validate!!.RetriveSharepreferenceString(AppSP.sUserName)!!
        password = validate!!.RetriveSharepreferenceString(AppSP.sPassword)!!

        unsyncdata.setOnClickListener {
            dialog()
        }
        linear_upload.setOnClickListener {

            if (validate!!.isNetworkConnected(this)) {

                progressDialog = ProgressDialog.show(
                    this@SyncronizationActivity,
                    "",
                    resources.getString(R.string.data_uploading)
                )
                CoroutineScope(Dispatchers.IO).launch {
                    uploadData()
                }
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }
        }

        linear_download.setOnClickListener {
            if (validate!!.isNetworkConnected(this)) {

                val household = CareIndiaApplication.database!!.hhProfileDao().getHHdata()
                val individual =
                    CareIndiaApplication.database!!.imProfileDao().getIMProfileAlldata()
                val primary = CareIndiaApplication.database!!.primaryDataDao().getPrimaryAlldata()
                val psycho =
                    CareIndiaApplication.database!!.psychometricDao().getPsychometricAlldata()
                val meeting =
                    CareIndiaApplication.database!!.collectiveMeetingDao().getMemberAllData()
                val collective =
                    CareIndiaApplication.database!!.collectiveDao().getCollectiveAlldata()
                val collectiveMem = CareIndiaApplication.database!!.collectiveMemDao()
                    .getAllMemberDataNew()
                val collprogress = CareIndiaApplication.database!!.collectiveProgressTrackerDao()
                    .getallCollProgdata()
                val assessment =
                    CareIndiaApplication.database!!.assessmentDao().getAllAssessmentdata()
                val assessmentDetail = CareIndiaApplication.database!!.assessmentDetailDao()
                    .getAllAssessmentDetaildata()

                if (household.isNotEmpty() || individual.isNotEmpty() || primary.isNotEmpty() || psycho.isNotEmpty() ||
                    meeting.isNotEmpty() || collective.isNotEmpty() || collectiveMem.isNotEmpty() ||
                    collprogress.isNotEmpty() || assessment.isNotEmpty() || assessmentDetail.isNotEmpty()
                ) {
                    validate!!.CustomToast(this, getString(R.string.uploaddatafirst))
                } else {
                    progressDialog = ProgressDialog.show(
                        this@SyncronizationActivity,
                        "",
                        resources.getString(R.string.data_dwnloading)
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        dowloadData()
                    }
                }
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }
        }

        linear_download_status.setOnClickListener {
            if (validate!!.isNetworkConnected(this)) {
                downloadStatusData()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }

        linear_downloadmaster.setOnClickListener {
            if (validate!!.isNetworkConnected(this)) {
                importUserData()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }
        }


        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun dialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_unsync)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        val btn_ok = dialog.findViewById<View>(R.id.btn_ok) as Button
        val tvhh = dialog.findViewById<View>(R.id.tvhh) as TextView
        val tvhhtotal = dialog.findViewById<View>(R.id.tvhhtotal) as TextView
        val tvind = dialog.findViewById<View>(R.id.tvind) as TextView
        val tvindtotal = dialog.findViewById<View>(R.id.tvindtotal) as TextView
        val tvedp = dialog.findViewById<View>(R.id.tvedp) as TextView
        val tvedptotal = dialog.findViewById<View>(R.id.tvedptotal) as TextView
        val tvpsycho = dialog.findViewById<View>(R.id.tvpsycho) as TextView
        val tvpsychototal = dialog.findViewById<View>(R.id.tvpsychototal) as TextView
        val tvcp = dialog.findViewById<View>(R.id.tvcp) as TextView
        val tvcptotal = dialog.findViewById<View>(R.id.tvcptotal) as TextView
        val tvcpm = dialog.findViewById<View>(R.id.tvcpm) as TextView
        val tvcpmtotal = dialog.findViewById<View>(R.id.tvcpmtotal) as TextView
        val tvcm = dialog.findViewById<View>(R.id.tvcm) as TextView
        val tvcmtotal = dialog.findViewById<View>(R.id.tvcmtotal) as TextView
        val tvcpt = dialog.findViewById<View>(R.id.tvcpt) as TextView
        val tvcpttotal = dialog.findViewById<View>(R.id.tvcpttotal) as TextView
        val tvbpt = dialog.findViewById<View>(R.id.tvbpt) as TextView
        val tvbpttotal = dialog.findViewById<View>(R.id.tvbpttotal) as TextView
        val tvppa = dialog.findViewById<View>(R.id.tvppa) as TextView
        val tvppatotal = dialog.findViewById<View>(R.id.tvppatotal) as TextView

        tvhh.text = CareIndiaApplication.database!!.hhProfileDao().getHHunsyncCount()
        tvhhtotal.text = CareIndiaApplication.database!!.hhProfileDao().getHHtotalCount()
        tvind.text = CareIndiaApplication.database!!.imProfileDao().getIndunsyncCount()
        tvindtotal.text = CareIndiaApplication.database!!.imProfileDao().getIndtotalCount()
        tvedp.text = CareIndiaApplication.database!!.primaryDataDao().getEDPunsyncCount()
        tvedptotal.text = CareIndiaApplication.database!!.primaryDataDao().getEDPtotalCount()
        tvpsycho.text = CareIndiaApplication.database!!.psychometricDao().getPsychounsyncCount()
        tvpsychototal.text = CareIndiaApplication.database!!.psychometricDao().getPsychototalCount()
        tvcp.text = CareIndiaApplication.database!!.collectiveDao().getCollunsyncCount()
        tvcptotal.text = CareIndiaApplication.database!!.collectiveDao().getColltotalCount()
        tvcpm.text = CareIndiaApplication.database!!.collectiveMemDao().getCollMemunsyncCount()
        tvcpmtotal.text = CareIndiaApplication.database!!.collectiveMemDao().getCollMemtotalCount()
        tvcm.text = CareIndiaApplication.database!!.collectiveMeetingDao().getMeetunsyncCount()
        tvcmtotal.text = CareIndiaApplication.database!!.collectiveMeetingDao().getMeettotalCount()
        tvcpt.text = CareIndiaApplication.database!!.collectiveProgressTrackerDao()
            .getColltrackerunsyncCount()
        tvcpttotal.text = CareIndiaApplication.database!!.collectiveProgressTrackerDao()
            .getColltrackertotalCount()
        tvbpt.text = CareIndiaApplication.database!!.beneficiaryDao().getBenunsyncCount()
        tvbpttotal.text = CareIndiaApplication.database!!.beneficiaryDao().getBentotalCount()
        tvppa.text = CareIndiaApplication.database!!.assessmentDao().getAssunsyncCount()
        tvppatotal.text = CareIndiaApplication.database!!.assessmentDao().getAsstotalCount()

        btn_ok.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private suspend fun uploadData() {

        CoroutineScope(Dispatchers.IO).launch {
            val hhData = async { upload_hh_data() }
            val imData = async { upload_individual_data() }
            val primData = async { upload_primary_data() }
            val psychoData = async { upload_psychometric_data() }
            val meetingData = async { upload_collectiveMeeting_data() }
            val profileData = async { upload_collective_data() }
            val cptData = async { uploadCollProgTrackerData() }
            val asstDetailData = async { upload_PrePost_data() }
            val beneficiaryTrcakerData = async { upload_BeneficiaryTracker_data() }



            hhData.await()
            imData.await()
            primData.await()
            psychoData.await()
            meetingData.await()
            profileData.await()
            cptData.await()
            asstDetailData.await()
            beneficiaryTrcakerData.await()

            progressDialog.dismiss()

            if (hhdata!!.isEmpty() && individualdata!!.isEmpty() && primarydata!!.isEmpty() && psychometricdata!!.isEmpty()
                && collectiveMeetingdata!!.isEmpty() && collProfile == 0 && collMember == 0 && cptdata!!.isEmpty() && assessmentDetail == 0 && assessment == 0 && beneficiary == 0 && beneficiaryDetail == 0
            ) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SyncronizationActivity,
                        getString(R.string.nothingupload),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else if (ihhupload == 1 || iImupload == 1 || iedpupload == 1 || ipsyupload == 1 || imeetgupload == 1 || icprfupload == 1 || icptupload == 1 || icprepostupload == 1 || icbeneficiaryupload == 1) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SyncronizationActivity,
                        getString(R.string.uploadf),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SyncronizationActivity,
                        getString(R.string.uploads),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

        }

    }

    private suspend fun dowloadData() {

        CoroutineScope(Dispatchers.IO).launch {
            val hhData = async { importHHData() }
            val imData = async { importIMData() }
            val psychoData = async { importPsychoData() }
            val primaryData = async { importPrimaryData() }
            val meetingData = async { importMeetingData() }
            val memProfileData = async { importProfileData() }
            val cptData = async { importCPTData() }
            val assessmentData = async { importPrePostAssessmentData() }
            val beneficiaryData = async { importBeneficiaryTrackerData() }
            val prePostMasterData = async { importPrePostMasterData() }

            hhData.await()
            imData.await()
            psychoData.await()
            primaryData.await()
            meetingData.await()
            memProfileData.await()
            cptData.await()
            assessmentData.await()
            beneficiaryData.await()
            prePostMasterData.await()

            progressDialog.dismiss()

            if (ihhdownload == 1 || iImdownload == 1 || iedpdownload == 1 || ipsydownload == 1 || imeetgdownload == 1 || icprfdownload == 1 || icptdownload == 1 || icprepostdownload == 1 || icbeneficiarydownload == 1) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SyncronizationActivity,
                        getString(R.string.downf),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SyncronizationActivity,
                        getString(R.string.downs),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun upload_hh_data() {

        try {
            var json = ""
            hhdata = CareIndiaApplication.database!!.hhProfileDao().getHHdata()
            json = exportData(hhdata, "tblProfileHH").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_HHdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ihhupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.hhProfileDao()?.updateIsEdited()
                            ihhupload = 2
                        }

                        else -> {
                            ihhupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            ihhupload = 1
        }

    }

    private fun upload_individual_data() {

        try {
            var json = ""
            individualdata =
                CareIndiaApplication.database!!.imProfileDao().getIMProfileAlldata()
            json = exportData(individualdata, "tblProfileIndividual").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_individualdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    iImupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.imProfileDao()?.updateIsEdited()
                            iImupload = 2
                        }
                        else -> {
                            iImupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            iImupload = 1
        }

    }

    private fun upload_primary_data() {

        try {
            var json = ""
            primarydata = CareIndiaApplication.database!!.primaryDataDao().getPrimaryAlldata()
            json = exportData(primarydata, "tblPDC").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_primarydata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    iedpupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.primaryDataDao()?.updateIsEdited()
                            iedpupload = 2
                        }

                        else -> {
                            iedpupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            iedpupload = 1
        }

    }

    private fun upload_psychometric_data() {

        try {
            var json = ""
            psychometricdata =
                CareIndiaApplication.database!!.psychometricDao().getPsychometricAlldata()
            json = exportData(psychometricdata, "tblPsychometric").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_psychometricdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ipsyupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.psychometricDao()?.updateIsEdited()
                            ipsyupload = 2
                        }

                        else -> {
                            ipsyupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            ipsyupload = 1
        }

    }

    private fun upload_collectiveMeeting_data() {

        try {
            var json = ""
            collectiveMeetingdata =
                CareIndiaApplication.database!!.collectiveMeetingDao().getMemberAllData()
            json = exportData(collectiveMeetingdata, "tblCollectiveMeeting").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_meetdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    imeetgupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.collectiveMeetingDao()?.updateIsEdited()
                            imeetgupload = 2
                        }

                        else -> {
                            imeetgupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            imeetgupload = 1
        }

    }

    private fun upload_collective_data() {

        try {
            val collectivedata =
                CareIndiaApplication.database!!.collectiveDao().getCollectiveAlldata() as ArrayList
            val collectiveMemdata = CareIndiaApplication.database!!.collectiveMemDao()
                .getAllMemberDataNew() as ArrayList
            collProfile = collectivedata.size
            collMember = collectiveMemdata.size
            var json = exportTableTrackDetail(collectivedata, collectiveMemdata)
            val file = json!!
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_profiledata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    icprfupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.collectiveDao()?.updateIsEdited()
                            CareIndiaApplication.database?.collectiveMemDao()?.updateIsEdited()
                            icprfupload = 2
                        }
                        else -> {
                            icprfupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icprfupload = 1
        }

    }

    private fun uploadCollProgTrackerData() {
        try {
            var json = ""
            cptdata =
                CareIndiaApplication.database!!.collectiveProgressTrackerDao().getallCollProgdata()
            json = exportData(cptdata, "tblCollectiveProgressTracker").toString()

            val file = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_tblCPTrackerdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    icptupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.collectiveProgressTrackerDao()!!
                                .updateIsEdited()
                            icptupload = 2
                        }

                        else -> {
                            icptupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icptupload = 1
        }

    }


    private fun upload_PrePost_data() {

        try {
            val assessmentdata =
                CareIndiaApplication.database!!.assessmentDao().getAllAssessmentdata() as ArrayList
            val assessmentDetaildata = CareIndiaApplication.database!!.assessmentDetailDao()
                .getAllAssessmentDetaildata() as ArrayList
            assessment = assessmentdata.size
            assessmentDetail = assessmentDetaildata.size
            var json = exportTableTrackPrePostDetail(assessmentdata, assessmentDetaildata)
            val file = json!!
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_assessmentdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    icprepostupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.assessmentDao()?.updateIsEdited()
                            CareIndiaApplication.database?.assessmentDetailDao()?.updateIsEdited()
                            icprepostupload = 2
                        }
                        else -> {
                            icprepostupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icprepostupload = 1
        }

    }

    private fun upload_BeneficiaryTracker_data() {

        try {
            val beneficiarydata =
                CareIndiaApplication.database!!.beneficiaryDao()
                    .getAllBeneficiarydata() as ArrayList
            val beneficiaryDetaildata =
                CareIndiaApplication.database!!.beneficiaryTrackerDetailDao()
                    .getAllBeneficiaryDetaildata() as ArrayList
            beneficiary = beneficiarydata.size
            beneficiaryDetail = beneficiaryDetaildata.size
            var json = exportTableTrackBeneficiaryDetail(beneficiarydata, beneficiaryDetaildata)
            val file = json!!
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_beneficiaryTrackerdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    icbeneficiaryupload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.beneficiaryDao()?.updateIsEdited()
                            CareIndiaApplication.database?.beneficiaryTrackerDetailDao()
                                ?.updateIsEdited()
                            icbeneficiaryupload = 2
                        }
                        else -> {
                            icbeneficiaryupload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icbeneficiaryupload = 1
        }

    }


    fun exportData(
        data: Any?, tablename: String
    ): JSONObject? {

        var MyJson: JSONObject? = null
        try {
            val ArrayListName =
                arrayOf<List<*>>(data as List<*>)
            tableName = arrayOf(
                tablename
            )

            MyJson = ReturnJson(ArrayListName, tableName!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return MyJson
    }


    fun ReturnJson(abc: Array<List<*>>, TableName: Array<String>): JSONObject {

        val jsonObjectcombined = JSONObject()
        for (i in TableName.indices) {
            try {
                if (abc[i].size > 0) {
                    val json1 = Gson().toJson(abc[i])
                    val JSONCrosscheck = JSONArray(json1)
                    jsonObjectcombined.put(
                        TableName[i],
                        JSONCrosscheck
                    )
                } else {
                    val otherJsonArray = JSONArray()
                    jsonObjectcombined.put(TableName[i], otherJsonArray)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return jsonObjectcombined
    }

    fun exportTableTrackDetail(
        collectivedata: java.util.ArrayList<CollectiveEntity>,
        collectiveMemdata: java.util.ArrayList<CollectiveMemberEntity>
    ): String? {
        var MyJson: String? = null
        try {
            val jsonObjectcombined = JSONObject()
            val ArrayListName = arrayOf<ArrayList<*>>(
                collectivedata,
                collectiveMemdata
            )
            val TableName = arrayOf(
                "tblCollective",
                "tblCollectiveMember"
            )

            MyJson = ReturnJson(ArrayListName, TableName, jsonObjectcombined)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return MyJson
    }

    fun exportTableTrackPrePostDetail(
        assessmentdata: java.util.ArrayList<AssessmentEntity>,
        assessmentDetaildata: java.util.ArrayList<AssessmentDetailEntity>
    ): String? {
        var MyJson: String? = null
        try {
            val jsonObjectcombined = JSONObject()
            val ArrayListName = arrayOf<ArrayList<*>>(
                assessmentdata,
                assessmentDetaildata
            )
            val TableName = arrayOf(
                "tblAssessment",
                "tblAssessmentDetail"
            )

            MyJson = ReturnJson(ArrayListName, TableName, jsonObjectcombined)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return MyJson
    }

    fun exportTableTrackBeneficiaryDetail(
        beneficiarydata: java.util.ArrayList<BeneficiaryEntity>,
        beneficiaryDetaildata: java.util.ArrayList<BeneficiaryDetailEntity>
    ): String? {
        var MyJson: String? = null
        try {
            val jsonObjectcombined = JSONObject()
            val ArrayListName = arrayOf<ArrayList<*>>(
                beneficiarydata,
                beneficiaryDetaildata
            )
            val TableName = arrayOf(
                "tblBeneficiaryProgress",
                "tblBeneficiaryDetail"
            )

            MyJson = ReturnJson(ArrayListName, TableName, jsonObjectcombined)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return MyJson
    }


    fun ReturnJson(
        abc: Array<ArrayList<*>>,
        TableName: Array<String>,
        jsonObjectcombined: JSONObject
    ): String {

        for (i in TableName.indices) {
            try {
                if (abc[i].size > 0) {
                    val json1 = Gson().toJson(abc[i])
                    val JSONCrosscheck = JSONArray(json1)
                    jsonObjectcombined.put(
                        TableName[i],
                        JSONCrosscheck
                    )
                } else {
                    val otherJsonArray = JSONArray()
                    jsonObjectcombined.put(TableName[i], otherJsonArray)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return jsonObjectcombined.toString()
    }

    private fun importHHData() {

        try {

            val call = apiInterface!!.importHH(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    ihhdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.hhProfileDao()?.deleteProfiledata()
                            if (!response.body()!!.tblProfileHH.isNullOrEmpty()) {
                                CareIndiaApplication.database?.hhProfileDao()
                                    ?.insertAllData(response.body()!!.tblProfileHH)

                            }
                            ihhdownload = 2
                        }

                        else -> {
                            ihhdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            ihhdownload = 1
        }

    }

    private fun importIMData() {

        try {

            val call = apiInterface!!.importIM(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    iImdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            iImdownload = 2
                            CareIndiaApplication.database?.imProfileDao()?.deleteIMProfiledata()
                            if (!response.body()!!.tblProfileIndividual.isNullOrEmpty()) {
                                CareIndiaApplication.database?.imProfileDao()
                                    ?.insertAllData(response.body()!!.tblProfileIndividual)
                            }
                        }

                        else -> {
                            iImdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            iImdownload = 1
        }

    }

    private fun importPsychoData() {

        try {

            var call = apiInterface!!.importPsycho(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    ipsydownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            ipsydownload = 2
                            CareIndiaApplication.database?.psychometricDao()?.deletePsychometric()
                            if (!response.body()!!.tblPsychometric.isNullOrEmpty()) {
                                CareIndiaApplication.database?.psychometricDao()
                                    ?.insertAllPsychometricData(response.body()!!.tblPsychometric)
                            }
                        }

                        else -> {
                            ipsydownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            ipsydownload = 1
        }

    }

    private fun importPrimaryData() {
        try {
            var call = apiInterface!!.importprimary(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    iedpdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            iedpdownload = 2
                            CareIndiaApplication.database?.primaryDataDao()?.deletePrimarydata()
                            if (!response.body()!!.tblPDC.isNullOrEmpty()) {
                                CareIndiaApplication.database?.primaryDataDao()
                                    ?.insertAllData(response.body()!!.tblPDC)
                            }
                        }

                        else -> {
                            iedpdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            iedpdownload = 1
        }

    }

    private fun importMeetingData() {

        try {
            var call = apiInterface!!.importmeeting(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    imeetgdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            imeetgdownload = 2
                            CareIndiaApplication.database?.collectiveMeetingDao()?.deleteAllData()
                            if (!response.body()!!.tblCollectiveMeeting.isNullOrEmpty()) {
                                CareIndiaApplication.database?.collectiveMeetingDao()
                                    ?.insertMeetingData(response.body()!!.tblCollectiveMeeting)
                            }
                        }

                        else -> {
                            imeetgdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            imeetgdownload = 1
        }

    }

    private fun importProfileData() {

        try {
            var call = apiInterface!!.importprofiledata(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    icprfdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            icprfdownload = 2
                            CareIndiaApplication.database?.collectiveDao()?.deleteAllData()
                            CareIndiaApplication.database?.collectiveMemDao()?.deleteAllData()

                            if (!response.body()!!.tblCollectiveProfile.isNullOrEmpty()) {
                                CareIndiaApplication.database?.collectiveDao()
                                    ?.insertAllData(response.body()!!.tblCollectiveProfile)
                            }

                            if (!response.body()!!.tblCollectiveMember.isNullOrEmpty()) {
                                CareIndiaApplication.database?.collectiveMemDao()
                                    ?.insertAllMemberData(response.body()!!.tblCollectiveMember)
                            }

                        }

                        else -> {
                            icprfdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icprfdownload = 1
        }

    }

    private fun importPrePostAssessmentData() {

        try {
            var call = apiInterface!!.importAssessmentdata(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    icprepostdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            icprepostdownload = 2
                            CareIndiaApplication.database?.assessmentDao()?.deleteAll()
                            CareIndiaApplication.database?.assessmentDetailDao()?.deleteAll()

                            if (!response.body()!!.tblAssessment.isNullOrEmpty()) {
                                CareIndiaApplication.database?.assessmentDao()
                                    ?.insertAllData(response.body()!!.tblAssessment)
                            }

                            if (!response.body()!!.tblAssessmentDetail.isNullOrEmpty()) {
                                CareIndiaApplication.database?.assessmentDetailDao()
                                    ?.insertAllData(response.body()!!.tblAssessmentDetail)
                            }

                        }

                        else -> {
                            icprepostdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icprepostdownload = 1
        }

    }

    private fun importBeneficiaryTrackerData() {

        try {
            var call = apiInterface!!.importBeneficiaryTrackerdata(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    icbeneficiarydownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            icbeneficiarydownload = 2
                            CareIndiaApplication.database?.beneficiaryDao()?.deleteAll()
                            CareIndiaApplication.database?.beneficiaryTrackerDetailDao()
                                ?.deleteAll()

                            if (!response.body()!!.tblBeneficiaryProgress.isNullOrEmpty()) {
                                CareIndiaApplication.database?.beneficiaryDao()
                                    ?.insertAllData(response.body()!!.tblBeneficiaryProgress)
                            }

                            if (!response.body()!!.tblBeneficiaryDetail.isNullOrEmpty()) {
                                CareIndiaApplication.database?.beneficiaryTrackerDetailDao()
                                    ?.insertAllData(response.body()!!.tblBeneficiaryDetail)
                            }

                        }

                        else -> {
                            icbeneficiarydownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icbeneficiarydownload = 1
        }

    }

    private fun importCPTData() {

        try {

            val call = apiInterface!!.importtblCPTrackerdata(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    icptdownload = 1
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.collectiveProgressTrackerDao()!!
                                .deleteAllData()
                            if (!response.body()!!.tblCollectiveProgTracker.isNullOrEmpty()) {
                                CareIndiaApplication.database?.collectiveProgressTrackerDao()
                                    ?.insertAllData(response.body()!!.tblCollectiveProgTracker)
                            }
                            icptdownload = 2
                        }

                        else -> {
                            icptdownload = 1
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            icptdownload = 1
        }

    }


    private fun importUserData() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.master_dwnloading)
        )
        try {
            var call = apiInterface!!.login(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {

                            CareIndiaApplication.database?.mstStateDao()?.deleteAll()
                            CareIndiaApplication.database?.mstDistrictDao()?.deleteAll()
                            CareIndiaApplication.database?.mstZoneDao()?.deleteAll()
                            CareIndiaApplication.database?.mstPanchayatWardDao()?.deleteAll()
                            CareIndiaApplication.database?.mstLocalityDao()?.deleteAll()
                            CareIndiaApplication.database?.mstLookupDao()?.deleteAll()

                            if (!response.body()!!.mst_1State.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstStateDao()
                                    ?.insertWithCondition(response.body()!!.mst_1State)
                            }

                            if (!response.body()!!.mst_2District.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstDistrictDao()
                                    ?.insertWithCondition(response.body()!!.mst_2District)
                            }

                            if (!response.body()!!.Mst_3Zone.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstZoneDao()
                                    ?.insertWithCondition(response.body()!!.Mst_3Zone)
                            }

                            if (!response.body()!!.Mst_4Panchayat_Ward.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstPanchayatWardDao()
                                    ?.insertWithCondition(response.body()!!.Mst_4Panchayat_Ward)
                            }

                            if (!response.body()!!.mst_5Locality.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstLocalityDao()
                                    ?.insertWithCondition(response.body()!!.mst_5Locality)
                            }

                            if (!response.body()!!.mst_9Lookup.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstLookupDao()
                                    ?.insertWithCondition(response.body()!!.mst_9Lookup)
                            }
                            if (!response.body()!!.mstAssessment.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstAssessmentDao()
                                    ?.insertWithCondition(response.body()!!.mstAssessment)
                            }


                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                "Download Successful"
                            )
                            progressDialog.dismiss()

                        }

                        else -> {

                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }


    private fun importPrePostMasterData() {
        try {

            var call = apiInterface!!.prepostData(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            CareIndiaApplication.database?.trainingDao()?.deleteTraining()

                            if (!response.body()!!.tblTraining.isNullOrEmpty()) {
                                CareIndiaApplication.database?.trainingDao()
                                    ?.insertAllTrainingData(response.body()!!.tblTraining)
                            }
                            CareIndiaApplication.database?.participantAttendanceDetailDao()
                                ?.deleteParticipantAttendance()
                            if (!response.body()!!.tblParticipantAttendanceDetail.isNullOrEmpty()) {
                                CareIndiaApplication.database?.participantAttendanceDetailDao()
                                    ?.insertAllParticipantAttendanceData(response.body()!!.tblParticipantAttendanceDetail)
                            }

                            CareIndiaApplication.database?.mstTrainerDao()?.deleteTrainerData()
                            if (!response.body()!!.mstTrainer.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstTrainerDao()
                                    ?.insertAllTrainerData(response.body()!!.mstTrainer)
                            }

                            CareIndiaApplication.database?.trainingParticipantDetailDao()
                                ?.deleteTrainingParticipant()
                            if (!response.body()!!.tblParticipantAttendanceDetail.isNullOrEmpty()) {
                                CareIndiaApplication.database?.trainingParticipantDetailDao()
                                    ?.insertAllTrainingParticipantData(response.body()!!.tblTrainingParticipantDetail)
                            }


                        }

                        else -> {

                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()

        }

    }


    private fun downloadStatusData() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_dwnloading)
        )

        try {
            val call = apiInterface!!.getStatusData(username)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressDialog.dismiss()
//                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            if (!response.body()!!.tblProfileHH.isNullOrEmpty()) {
                                val listhh = response.body()!!.tblProfileHH
                                if (!listhh.isNullOrEmpty()) {
                                    for (i in 0 until listhh!!.size) {
                                        CareIndiaApplication.database?.hhProfileDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listhh[i].Remarks),
                                                validate!!.returnIntegerValue(listhh[i].Status!!.toString()),
                                                validate!!.returnStringValue(listhh[i].HHGUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblProfileIndividual.isNullOrEmpty()) {
                                val listIm = response.body()!!.tblProfileIndividual
                                if (!listIm.isNullOrEmpty()) {
                                    for (i in 0 until listIm!!.size) {
                                        CareIndiaApplication.database?.imProfileDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listIm[i].Remarks),
                                                validate!!.returnIntegerValue(listIm[i].Status!!.toString()),
                                                validate!!.returnStringValue(listIm[i].IndGUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblPDC.isNullOrEmpty()) {
                                val listedp = response.body()!!.tblPDC
                                if (!listedp.isNullOrEmpty()) {
                                    for (i in 0 until listedp!!.size) {
                                        CareIndiaApplication.database?.primaryDataDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listedp[i].Remarks),
                                                validate!!.returnIntegerValue(listedp[i].Status!!.toString()),
                                                validate!!.returnStringValue(listedp[i].PDCGUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblPsychometric.isNullOrEmpty()) {
                                val listpsycho = response.body()!!.tblPsychometric
                                if (!listpsycho.isNullOrEmpty()) {
                                    for (i in 0 until listpsycho!!.size) {
                                        CareIndiaApplication.database?.psychometricDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listpsycho[i].Remarks),
                                                validate!!.returnIntegerValue(listpsycho[i].Status!!.toString()),
                                                validate!!.returnStringValue(listpsycho[i].PATGUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblCollectiveProfile.isNullOrEmpty()) {
                                val listcol = response.body()!!.tblCollectiveProfile
                                if (!listcol.isNullOrEmpty()) {
                                    for (i in 0 until listcol!!.size) {
                                        CareIndiaApplication.database?.collectiveDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listcol[i].Remarks),
                                                validate!!.returnIntegerValue(listcol[i].Status!!.toString()),
                                                validate!!.returnStringValue(listcol[i].Col_GUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblCollectiveMeeting.isNullOrEmpty()) {
                                val listcolMeet = response.body()!!.tblCollectiveMeeting
                                if (!listcolMeet.isNullOrEmpty()) {
                                    for (i in 0 until listcolMeet!!.size) {
                                        CareIndiaApplication.database?.collectiveMeetingDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listcolMeet[i].Remarks),
                                                validate!!.returnIntegerValue(listcolMeet[i].Status!!.toString()),
                                                validate!!.returnStringValue(listcolMeet[i].CollMeetGUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblCollectiveMember.isNullOrEmpty()) {
                                val listcolmem = response.body()!!.tblCollectiveMember
                                if (!listcolmem.isNullOrEmpty()) {
                                    for (i in 0 until listcolmem!!.size) {
                                        CareIndiaApplication.database?.collectiveMemDao()
                                            ?.updateStatusData(
                                                validate!!.returnIntegerValue(listcolmem[i].Status!!.toString()),
                                                validate!!.returnStringValue(listcolmem[i].GUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblCollectiveProgTracker.isNullOrEmpty()) {
                                val listcpt = response.body()!!.tblCollectiveProgTracker
                                if (!listcpt.isNullOrEmpty()) {
                                    for (i in 0 until listcpt!!.size) {
                                        CareIndiaApplication.database?.collectiveProgressTrackerDao()
                                            ?.updateStatusData(
                                                validate!!.returnStringValue(listcpt[i].Remarks),
                                                validate!!.returnIntegerValue(listcpt[i].Status!!.toString()),
                                                validate!!.returnStringValue(listcpt[i].CPT_GUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblBeneficiaryProgress.isNullOrEmpty()) {
                                val listbene = response.body()!!.tblBeneficiaryProgress
                                if (!listbene.isNullOrEmpty()) {
                                    for (i in 0 until listbene!!.size) {
                                        CareIndiaApplication.database?.beneficiaryDao()
                                            ?.updateStatusData(
                                                validate!!.returnIntegerValue(listbene[i].Status!!.toString()),
                                                validate!!.returnStringValue(listbene[i].Bene_GUID)
                                            )
                                    }
                                }

                            }
                            if (!response.body()!!.tblAssessment.isNullOrEmpty()) {
                                val listprepost = response.body()!!.tblAssessment
                                if (!listprepost.isNullOrEmpty()) {
                                    for (i in 0 until listprepost!!.size) {
                                        CareIndiaApplication.database?.assessmentDao()
                                            ?.updateStatusData(
                                                validate!!.returnIntegerValue(listprepost[i].Status!!.toString()),
                                                validate!!.returnIntegerValue(listprepost[i].TrainingID!!.toString())
                                            )
                                    }
                                }

                            }

                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                "Download Status Successful"
                            )
                            progressDialog.dismiss()

                        }
                        else -> {
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }


    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}