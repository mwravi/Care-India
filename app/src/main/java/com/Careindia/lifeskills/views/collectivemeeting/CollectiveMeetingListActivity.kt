package com.careindia.lifeskills.views.collectivemeeting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityMeetinglistBinding
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.careindia.lifeskills.views.primarydatascreen.PrimaryDataListActivity
import kotlinx.android.synthetic.main.activity_meetinglist.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveMeetingListActivity : AppCompatActivity() {
    private lateinit var listBinding: ActivityMeetinglistBinding
    lateinit var collectiveMeetingViewModel: CollectiveMeetingViewModel
    var validate: Validate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listBinding = DataBindingUtil.setContentView(this, R.layout.activity_meetinglist)
        validate = Validate(this)

        val colldao = CareIndiaApplication.database?.collectiveMeetingDao()!!
        val collectiveMeetingRepository =
            CollectiveMeetingRepository(colldao)
        collectiveMeetingViewModel = ViewModelProvider(
            this,
            CollectiveMeetingViewModelFactory(collectiveMeetingRepository)
        )[CollectiveMeetingViewModel::class.java]

        tv_title.text = getString(R.string.collmeetinglist)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, "")
            val intent = Intent(this, CollectiveMeetingActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, "")
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()

    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun fillRecyclerView() {
        listBinding.rvList.layoutManager = LinearLayoutManager(this)
        collectiveMeetingViewModel.collectiveData.observe(this, Observer {
            listBinding.rvList.adapter = CollectiveMeetingAdapter(it,
                { selectedItem: CollectiveMeetingEntity -> onItemClicked(selectedItem) },
                { deletedItem: CollectiveMeetingEntity -> onItemDeleted(deletedItem) })
        })
    }

    private fun onItemClicked(collectiveMeetingEntity: CollectiveMeetingEntity) {

        validate!!.SaveSharepreferenceString(AppSP.PDCGUID, collectiveMeetingEntity.CollMeetGUID)
        val intent = Intent(this, CollectiveMeetingActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun onItemDeleted(primaryDataEntity: CollectiveMeetingEntity) {

        CustomAlert_Delete(primaryDataEntity.CollMeetGUID)

    }

    fun CustomAlert_Delete(HHGUID: String) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            collectiveMeetingViewModel.delete_record(HHGUID)
            mAlertDialog.dismiss()

            val intent = Intent(this, PrimaryDataListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        mDialogView.btn_no.setOnClickListener {

            mAlertDialog.dismiss()
        }
    }
}