package com.careindia.lifeskills.views.synchronization

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.google.gson.Gson
import com.mamta.sabal.callback.ApiCallback
import com.mamta.sabal.service.ApiClientConnection
import kotlinx.android.synthetic.main.activity_sync_dashboard.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_dashboard)
        validate = Validate(this)

        apiInterface = ApiClientConnection.instance.createApiInterface()
        linear_household.setOnClickListener {

            if (validate!!.isNetworkConnected()) {
                upload_hh_data()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }
        linear_individual.setOnClickListener {

            if (validate!!.isNetworkConnected()) {
                upload_individual_data()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }
        ll_primary_data.setOnClickListener {

            if (validate!!.isNetworkConnected()) {
                upload_primary_data()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }
        ll_upload_psychometric.setOnClickListener {

            if (validate!!.isNetworkConnected()) {
                upload_psychometric_data()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }
        ll_collective.setOnClickListener {

            if (validate!!.isNetworkConnected()) {
                upload_collective_data()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }
        ll_collective_meeting.setOnClickListener {
            if (validate!!.isNetworkConnected()) {
                upload_collectiveMeeting_data()
            } else {
                validate!!.CustomToast(this, getString(R.string.no_internet_connection))
            }

        }

        tv_title.text = "Sync"
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

    private fun upload_hh_data() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_uploading)
        )
        try {
            var json = ""
            val hhdata = CareIndiaApplication.database!!.hhProfileDao().getHHdata()
            json = exportData(hhdata, "tblProfileHH").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_data(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {

                            /*    validate!!.CustomToast(
                                    this@SyncronizationActivity,
                                    getString(R.string.userregsuccess)
                                )*/
                            progressDialog.dismiss()
                            //nextActivity()
                        }

                        else -> {
                            /*validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.usernamealready)
                            )*/
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressDialog.dismiss()
        }

    }

    private fun upload_individual_data() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_uploading)
        )
        try {
            var json = ""
            val individualdata =
                CareIndiaApplication.database!!.imProfileDao().getIMProfileAlldata()
            json = exportData(individualdata, "tblProfileIndividual").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_individualdata(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {

                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.upload_succes)
                            )
                            progressDialog.dismiss()
                            //nextActivity()
                        }
                        else -> {
                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.upload_failed)
                            )
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressDialog.dismiss()
        }

    }

    //upload Primary data....

    private fun upload_primary_data() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_uploading)
        )
        try {
            var json = ""
            val primarydata = CareIndiaApplication.database!!.primaryDataDao().getPrimaryAlldata()
            json = exportData(primarydata, "tblPDC").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_data(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {

                            /*    validate!!.CustomToast(
                                    this@SyncronizationActivity,
                                    getString(R.string.userregsuccess)
                                )*/
                            progressDialog.dismiss()
                            //nextActivity()
                        }

                        else -> {
                            /*validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.usernamealready)
                            )*/
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressDialog.dismiss()
        }

    }

    //upload Psychometric data..
    private fun upload_psychometric_data() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_uploading)
        )
        try {
            var json = ""
            val psychometricdata =
                CareIndiaApplication.database!!.psychometricDao().getPsychometricAlldata()
            json = exportData(psychometricdata, "tblPsychometric").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_data(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {

                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.upload_succes)
                            )
                            progressDialog.dismiss()
                            //nextActivity()
                        }

                        else -> {
                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.upload_failed)
                            )
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressDialog.dismiss()
        }

    }//upload collectiveMeeting data..

    private fun upload_collectiveMeeting_data() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_uploading)
        )
        try {
            var json = ""
            val collectiveMeetingdata =
                CareIndiaApplication.database!!.collectiveMeetingDao().getMemberAllData()
            json = exportData(collectiveMeetingdata, "tblCollectiveMeeting").toString()
            val file = json
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_data(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {

                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.upload_succes)
                            )
                            progressDialog.dismiss()
                            //nextActivity()
                        }

                        else -> {
                            validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.upload_failed)
                            )
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressDialog.dismiss()
        }

    }

    //upload Collective data..
    private fun upload_collective_data() {
        progressDialog = ProgressDialog.show(
            this@SyncronizationActivity,
            "",
            resources.getString(R.string.data_uploading)
        )
        try {
            val collectivedata =
                CareIndiaApplication.database!!.collectiveDao().getCollectiveAlldata() as ArrayList
            val collectiveMemdata = CareIndiaApplication.database!!.collectiveMemDao()
                .getAllMemberDataNew() as ArrayList
            var json = exportTableTrackDetail(collectivedata, collectiveMemdata)
            val file = json!!
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = apiInterface!!.upload_data(file)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@SyncronizationActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        200 -> {

                            /*    validate!!.CustomToast(
                                    this@SyncronizationActivity,
                                    getString(R.string.userregsuccess)
                                )*/
                            progressDialog.dismiss()
                            //nextActivity()
                        }

                        else -> {
                            /*validate!!.CustomToast(
                                this@SyncronizationActivity,
                                getString(R.string.usernamealready)
                            )*/
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressDialog.dismiss()
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

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
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


}