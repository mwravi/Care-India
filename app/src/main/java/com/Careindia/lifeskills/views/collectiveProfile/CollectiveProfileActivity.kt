package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.entity.MstCommonEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.IOException
import java.io.InputStream
import com.google.gson.Gson
import kotlinx.android.synthetic.main.buttons_save_cancel.*


class CollectiveProfileActivity : AppCompatActivity() {

    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    var name_of_crp_filling: List<MstCommonEntity>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile_first)
        validate = Validate(this)
        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)
        tv_title.text = "Collective Profile"

        initCall()
    }

    fun initCall() {

        btn_save.setOnClickListener {
           val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
           finish()
        }

        et_date_of_filling.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_date_of_filling
            )

        }

        fun fillSpinner() {
            validate!!.fillSpinner(
                this,
                spin_name_of_crp,
                resources.getString(R.string.name_of_crp_filling),
                name_of_crp_filling
            )
            validate!!.fillSpinnerLanguage(
                this,
                spin_name_of_supervising,
                resources.getString(R.string.select),
                resources.getStringArray(R.array.yes_no)
            )
            validate!!.fillSpinnerLanguage(
                this,
                spin_district_name,
                resources.getString(R.string.select),
                resources.getStringArray(R.array.yes_no)
            )
            validate!!.fillSpinnerLanguage(
                this,
                spin_zone_name,
                resources.getString(R.string.select),
                resources.getStringArray(R.array.yes_no)
            )
            validate!!.fillSpinnerLanguage(
                this,
                spin_ward_name,
                resources.getString(R.string.select),
                resources.getStringArray(R.array.yes_no)
            )
            validate!!.fillSpinnerLanguage(
                this,
                spin_panchayat_name,
                resources.getString(R.string.select),
                resources.getStringArray(R.array.yes_no)
            )
            validate!!.fillSpinnerLanguage(
                this,
                spin_group_registered,
                resources.getString(R.string.select),
                resources.getStringArray(R.array.yes_no)
            )
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

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }
}