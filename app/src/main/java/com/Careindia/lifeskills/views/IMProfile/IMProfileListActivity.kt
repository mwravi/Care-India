package com.careindia.lifeskills.views.improfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
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
import com.careindia.lifeskills.viewmodelfactory.IndividualViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_profile_list.*
import kotlinx.android.synthetic.main.activity_primary_data_list.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class IMProfileListActivity : AppCompatActivity() {
    private lateinit var listbinding:ActivityImprofileListBinding
    var validate: Validate? = null

    lateinit var imProfileViewModel: IndividualProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding = DataBindingUtil.setContentView(this,R.layout.activity_improfile_list)
        validate = Validate(this)
        tv_title.text = "IM Profile List"


        val improfiledao = CareIndiaApplication.database?.imProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val improfileRepository = IndividualProfileRepository(improfiledao!!,mstDistrictDao)

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
            listbinding.rvList.adapter = IMProfileAdapter(it,{ selectedItem:IndividualProfileEntity->onItemClicked(selectedItem)},{deletedItem:IndividualProfileEntity->onItemDeleted(deletedItem)})
        })
    }
    private fun onItemClicked(imProfilelist: IndividualProfileEntity){

        validate!!.SaveSharepreferenceString(AppSP.IndividualProfileGUID,imProfilelist.IndGUID!!)
        val intent = Intent(this, IMProfileOneActivity::class.java)
        startActivity(intent)


    }

    private fun onItemDeleted(imProfileList: IndividualProfileEntity){

        CustomAlert_Delete(imProfileList)

    }

    fun CustomAlert_Delete(imProfileList:IndividualProfileEntity) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null,false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            imProfileViewModel.deleteImProfile(imProfileList)
            mAlertDialog.dismiss()

            val intent = Intent(this, IMProfileListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        mDialogView.btn_no.setOnClickListener {

            mAlertDialog.dismiss()
        }
    }



    override fun onBackPressed() {
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}