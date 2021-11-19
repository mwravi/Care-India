package com.careindia.lifeskills.views.loginscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.enums.Status
import com.careindia.lifeskills.views.base.BaseActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import showShortToast
import java.io.IOException
import java.io.InputStream

class LoginActivity : BaseActivity(), View.OnClickListener {
    private val loginViewModel by viewModel<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeController()


    }


    override fun initializeController() {
        applyClickOnView()

        readJson()

    }

    /**
     * Click on view
     */
    private fun applyClickOnView() {
        btn_login.setOnClickListener(this)

    }



    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_login->{
//                if (loginViewModel.isValid()) {
//                }
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
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

    fun readJson() {
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
    }

    override fun onBackPressed() {
        // super.onBackPressed()

        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}

