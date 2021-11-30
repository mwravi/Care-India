package com.careindia.lifeskills.views.improfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityImprofileListBinding
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.repository.IndividualProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.IndividualProfileViewModel
import com.careindia.lifeskills.viewmodel.MstCommonViewModel
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_profile_list.*
import kotlinx.android.synthetic.main.activity_primary_data_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileListActivity : AppCompatActivity() {
    private lateinit var listbinding:ActivityImprofileListBinding
    var validate: Validate? = null
    lateinit var mstCommonViewModel: MstCommonViewModel
    lateinit var imProfileViewModel: IndividualProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding = DataBindingUtil.setContentView(this,R.layout.activity_improfile_list)
        validate = Validate(this)
        tv_title.text = "IM Profile List"

        mstCommonViewModel =
            ViewModelProviders.of(this).get(MstCommonViewModel::class.java)

        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val improfileRepository = IndividualProfileRepository(improfiledao!!, commondao!!)

        imProfileViewModel = ViewModelProvider(
            this,
            IndividualViewModelFactory(improfileRepository)
        )[IndividualProfileViewModel::class.java]

        listbinding.lifecycleOwner = this


        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID, "")
            val intent = Intent(this, IMProfileOneActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()
    }


    private fun fillRecyclerView() {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)
        imProfileViewModel.imProfileData.observe(this, Observer {
            listbinding.rvList.adapter = IMProfileAdapter(it,{ selectedItem:IndividualProfileEntity->onItemClicked(selectedItem)})
        })
    }
    private fun onItemClicked(imProfilelist: IndividualProfileEntity){

        validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID,imProfilelist.IndGUID!!)
        val intent = Intent(this, IMProfileOneActivity::class.java)
        startActivity(intent)


    }
    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}