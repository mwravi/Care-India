package com.careindia.lifeskills.views.loginscreen

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.enums.Status
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstUserViewModel
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.mamta.sabal.callback.ApiCallback
import com.mamta.sabal.service.ApiClientConnection
import com.mamta.sabal.service.response.LoginResponse
import kotlinx.android.synthetic.main.activity_home_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import showShortToast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : BaseActivity(), View.OnClickListener {
    private val loginViewModel by viewModel<LoginViewModel>()
    var shake: Animation? = null
    var mstUserViewModel: MstUserViewModel? = null
    var validate: Validate? = null
    lateinit var progressDialog: ProgressDialog
    var apiInterface: ApiCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        shake = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.shake)
        mstUserViewModel =
            ViewModelProviders.of(this).get(MstUserViewModel::class.java)
        apiInterface = ApiClientConnection.instance.createApiInterface()
        validate = Validate(this)
        initializeController()

//        keyhash()
    }


    override fun initializeController() {
        applyClickOnView()
        //readJson()

    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_login.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
//                if (loginViewModel.isValid()) {
//                }

                if (checkValidation() == 1) {
                    var iCount = mstUserViewModel!!.getusersCount()
                    if (iCount == 0) {
                        if (validate!!.isNetworkConnected()) {
                            importUserData()
                        } else {
                            validate!!.CustomToast(this, getString(R.string.no_internet_connection))
                        }
                    } else {
                        nextActivity(1)
                    }
                }

                //  nextActivity(1)


            }
        }

    }

    private fun sendDataForLogin() {
        loginViewModel.loginApiData().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
//                    progressBar.visible()
//                    isClickedMultipleTime = false
                }
                Status.SUCCESS -> {
//                    progressBar.gone()
//                    isClickedMultipleTime = true
//                    this@LoginActivity.startNewActivity(MainActivity::class.java, true)
                }
                Status.ERROR -> {
                    //ProgressBarUtils.stopProcessingDialog()
//                    isClickedMultipleTime = true
//                    progressBar.gone()
                    it.msg?.showShortToast(this)
                }
            }
        })
    }

/*    fun readJson() {
        var json: String? = null
        try {
            val inputStream: InputStream = resources.assets.open("common.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val objectList = gson.fromJson(json, Array<MstCommonEntity>::class.java).asList()

            if (!objectList.isNullOrEmpty()) {
                CareIndiaApplication.database?.mstCommonDao()
                    ?.insertWithCondition(objectList)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }*/

    override fun onBackPressed() {
        // super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkValidation(): Int {
        var value = 1
        if (et_user_name.text.toString().isEmpty()) {
            et_user_name.error = resources.getString(R.string.Usernameblank)
            et_user_name.startAnimation(shake)
            et_user_name.requestFocus()
            et_user_name.performClick()
            value = 0
        } else if (et_password.text.toString().isEmpty()) {
            et_password.error = resources.getString(R.string.Passwordblank)
            et_password.startAnimation(shake)
            et_password.requestFocus()
            et_password.performClick()
            value = 0
        }

        return value

    }


    private fun importUserData() {
        progressDialog = ProgressDialog.show(
            this@LoginActivity,
            "",
            resources.getString(R.string.authenticating)
        )
        try {
            var username = et_user_name.text.toString()
            var password = et_password.text.toString()
            var call = apiInterface!!.login(username, password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    validate!!.CustomToast(this@LoginActivity, t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {

                            validate!!.SaveSharepreferenceString(AppSP.sUserName, username)
                            validate!!.SaveSharepreferenceString(AppSP.sPassword, password)

                            CareIndiaApplication.database?.mstUserDao()?.deleteAllUsers()
                            CareIndiaApplication.database?.mstStateDao()?.deleteAll()
                            CareIndiaApplication.database?.mstDistrictDao()?.deleteAll()
                            CareIndiaApplication.database?.mstZoneDao()?.deleteAll()
                            CareIndiaApplication.database?.mstPanchayatWardDao()?.deleteAll()
                            CareIndiaApplication.database?.mstLocalityDao()?.deleteAll()
                            CareIndiaApplication.database?.mstLookupDao()?.deleteAll()

                            if (!response.body()!!.users.isNullOrEmpty()) {
                                CareIndiaApplication.database?.mstUserDao()
                                    ?.insertWithCondition(response.body()!!.users)


                                validate!!.SaveSharepreferenceInt(
                                    AppSP.iUserID,
                                    response.body()!!.users!!.get(0).UserID
                                )

                                validate!!.SaveSharepreferenceInt(
                                    AppSP.CRPID,
                                    response.body()!!.users!!.get(0).CRPID
                                )

                                validate!!.SaveSharepreferenceString(
                                    AppSP.CRPID_Name,
                                    response.body()!!.users!!.get(0).CRPID_Name
                                )

                                validate!!.SaveSharepreferenceInt(
                                    AppSP.FCID,
                                    response.body()!!.users!!.get(0).FCID
                                )

                                validate!!.SaveSharepreferenceString(
                                    AppSP.FCID_Name,
                                    response.body()!!.users!!.get(0).FCID_Name
                                )
                                validate!!.SaveSharepreferenceInt(
                                    AppSP.StateCode,
                                    response.body()!!.users!!.get(0).Statecode!!
                                )

                            }
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

                            nextActivity(1)


                        }
                        201 -> {
                            validate!!.CustomToast(
                                this@LoginActivity,
                                getString(R.string.user_name_incorrect)
                            )
                            progressDialog.dismiss()
                        }
                        202 -> {

                            validate!!.CustomToast(
                                this@LoginActivity,
                                getString(R.string.user_name_not_exist)
                            )
                            progressDialog.dismiss()
                        }

                        else -> {
                            validate!!.CustomToast(
                                this@LoginActivity,
                                getString(R.string.wrongusernamepwd)
                            )
                            progressDialog.dismiss()
                        }
                    }
                }

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }


    private fun nextActivity(flag: Int) {
        if (flag == 1) {
            val i = Intent(this, HomeDashboardActivity::class.java)
            startActivity(i)
            finish()
        }
    }


    fun keyhash() {
        try {
            val info = packageManager.getPackageInfo(
                "com.careindia.lifeskills",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "KeyHash", "KeyHash:" + Base64.encodeToString(
                        md.digest(),
                        Base64.DEFAULT
                    )
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

}

