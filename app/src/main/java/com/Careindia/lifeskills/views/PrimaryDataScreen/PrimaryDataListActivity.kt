package com.careindia.lifeskills.views.primarydatascreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityPrimaryDataListBinding
import com.careindia.lifeskills.entity.PrimaryDataEntity
import com.careindia.lifeskills.repository.PrimaryDataRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.PrimaryDataViewModel
import com.careindia.lifeskills.viewmodelfactory.PrimaryDataViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PrimaryDataListActivity : AppCompatActivity() {
    private lateinit var listBinding: ActivityPrimaryDataListBinding
    lateinit var primaryDataViewModel: PrimaryDataViewModel
    var validate:Validate?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listBinding = DataBindingUtil.setContentView(this, R.layout.activity_primary_data_list)
        validate= Validate(this)
        tv_title.text = resources.getString(R.string.primary_data_list)
        val primaryDataDao = CareIndiaApplication.database?.primaryDataDao()!!
        val primaryDataRepository =
            PrimaryDataRepository(primaryDataDao)
        primaryDataViewModel = ViewModelProvider(
            this,
            PrimaryDataViewModelFactory(primaryDataRepository)
        )[PrimaryDataViewModel::class.java]

        listBinding.imgAddList.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.PDCGUID, "")
            val intent = Intent(this, PrimaryDataFirstActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()

    }


  /*  private fun fillRecyclerView() {
        listBinding.rvList.layoutManager = LinearLayoutManager(this)
        primaryDataViewModel.primaryData.observe(this, Observer {
            listBinding.rvList.adapter = PrimaryDataAdapter(it,
                { selectedItem: PrimaryDataEntity -> onItemClicked(selectedItem) },
                { deletedItem: PrimaryDataEntity -> onItemDeleted(deletedItem) })
        })
    }  */

    private fun fillRecyclerView() {
        listBinding.rvList.layoutManager = LinearLayoutManager(this)
        primaryDataViewModel.primaryData.observe(this, Observer {
            listBinding.rvList.adapter = PrimaryDataAdapter(it,
                { selectedItem: PrimaryDataEntity -> onItemClicked(selectedItem) },
                { deletedItem: PrimaryDataEntity -> onItemDeleted(deletedItem) })
        })
    }

    private fun onItemClicked(primaryDataEntity: PrimaryDataEntity) {

        validate!!.SaveSharepreferenceString(AppSP.PDCGUID, primaryDataEntity.PDCGUID)
        val intent = Intent(this, PrimaryDataFirstActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun onItemDeleted(primaryDataEntity: PrimaryDataEntity) {

        CustomAlert_Delete(primaryDataEntity.PDCGUID)

    }

    fun CustomAlert_Delete(HHGUID: String) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            primaryDataViewModel.delete_record(HHGUID)
            mAlertDialog.dismiss()

            val intent = Intent(this, PrimaryDataListActivity::class.java)
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