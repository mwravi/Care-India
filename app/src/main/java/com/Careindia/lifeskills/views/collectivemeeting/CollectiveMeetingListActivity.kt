package com.careindia.lifeskills.views.collectivemeeting

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityMeetinglistBinding
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.repository.CollectiveMeetingRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMeetingViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMeetingViewModelFactory
import com.careindia.lifeskills.views.collectiveProfile.CollectiveProfileListActivity
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

        val GroupName=validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
            ?.let { collectiveMeetingViewModel.getGroupName(it) }

       val CollectiveID= validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)
            ?.let { collectiveMeetingViewModel.getCollectiveID(it) }

        tv_title.text = getString(R.string.collmeetinglist)
        tv_set_group_name.setText(GroupName)
        tv_set_unique_id.setText(CollectiveID)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, "")
            val intent = Intent(this, CollectiveMeetingDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, "")
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()

    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun fillRecyclerView() {
        listBinding.rvList.layoutManager = LinearLayoutManager(this)
        collectiveMeetingViewModel.collectiveData.observe(this, Observer {
            listBinding.rvList.adapter = CollectiveMeetingAdapter(it,
                { selectedItem: CollectiveMeetingEntity -> onItemClicked(selectedItem) },
                { deletedItem: CollectiveMeetingEntity -> onItemDeleted(deletedItem) },
                { infoItem: CollectiveMeetingEntity -> onItemInfo(infoItem) },
                validate
            )
        })
    }

    private fun onItemClicked(collectiveMeetingEntity: CollectiveMeetingEntity) {

        validate!!.SaveSharepreferenceString(AppSP.CollectiveMeetingGUID, collectiveMeetingEntity.CollMeetGUID)
        val intent = Intent(this, CollectiveMeetingDetailsActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun onItemDeleted(collectivemeetingEntity: CollectiveMeetingEntity) {
//        if (collectivemeetingEntity.IsEdited == 0 && collectivemeetingEntity.Status == 0) {
//            validate!!.CustomAlert(this, resources.getString(R.string.delete_record))
//        } else {
            CustomAlert_Delete(collectivemeetingEntity.CollMeetGUID)
//        }

    }
 private fun onItemInfo(collectivemeetingEntity: CollectiveMeetingEntity) {

       validate!!.CustomAlertRejected(this,collectivemeetingEntity.Remarks)

    }


    fun CustomAlert_Delete(HHGUID: String) { // Create custom dialog object
        val dialog = Dialog(this)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.delete_dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)

        val btnyes =
            dialog.findViewById<View>(R.id.btn_yes) as Button
        val btnno =
            dialog.findViewById<View>(R.id.btn_no) as Button

        btnyes.setOnClickListener {
            collectiveMeetingViewModel.delete_record(HHGUID)
            dialog.dismiss()
            val intent = Intent(this, PrimaryDataListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        btnno.setOnClickListener {


            dialog.dismiss()
        }

        // Display the dialog
        dialog.show()
    }


//    fun CustomAlert_Delete(HHGUID: String) {
//        val mDialogView =
//            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
//        val mBuilder = AlertDialog.Builder(this)
//            .setView(mDialogView)
//        val mAlertDialog = mBuilder.show()
//        mAlertDialog.setCanceledOnTouchOutside(false)
//
//        mDialogView.btn_yes.setOnClickListener {
//            collectiveMeetingViewModel.delete_record(HHGUID)
//            mAlertDialog.dismiss()
//
//            val intent = Intent(this, PrimaryDataListActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            this.startActivity(intent)
//        }
//        mDialogView.btn_no.setOnClickListener {
//
//            mAlertDialog.dismiss()
//        }
//    }
}