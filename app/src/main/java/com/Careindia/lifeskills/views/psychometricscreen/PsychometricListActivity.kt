package com.careindia.lifeskills.views.psychometricscreen

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
import com.careindia.lifeskills.databinding.ActivityPsychometricListBinding
import com.careindia.lifeskills.entity.PsychometricEntity
import com.careindia.lifeskills.repository.PsychometricRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.PsychometricViewModel
import com.careindia.lifeskills.viewmodelfactory.PsychometricViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_psychometric_list.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PsychometricListActivity : AppCompatActivity() {
    private lateinit var listbinding: ActivityPsychometricListBinding
    var validate: Validate? = null
    lateinit var psychometricViewModel: PsychometricViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding = DataBindingUtil.setContentView(this, R.layout.activity_psychometric_list)
        validate = Validate(this)
        tv_title.text = "Psychometric List"

        val psychometricdao = CareIndiaApplication.database?.psychometricDao()
        val psychometricRepository = PsychometricRepository(psychometricdao!!)

        psychometricViewModel = ViewModelProvider(
            this,
            PsychometricViewModelFactory(psychometricRepository)
        )[PsychometricViewModel::class.java]

        listbinding.lifecycleOwner = this

        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.PATGUID, "")
            val intent = Intent(this, PsychometricFirstActivity::class.java)
            startActivity(intent)
            finish()
        }


        img_back.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        fillRecyclerView()

    }


    private fun fillRecyclerView() {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)
        psychometricViewModel.psychometricData.observe(this, Observer {
            listbinding.rvList.adapter = PsychometricAdapter(it,
                { selectedItem: PsychometricEntity -> onItemClicked(selectedItem) },
                { deletedItem: PsychometricEntity -> onItemDeleted(deletedItem) })
        })
    }

    private fun onItemClicked(psychometriclist: PsychometricEntity) {
        validate!!.SaveSharepreferenceString(AppSP.PATGUID, psychometriclist.PATGUID)
        val intent = Intent(this, PsychometricFirstActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(psychometriclist: PsychometricEntity) {
        CustomAlert_Delete(psychometriclist)
    }

    fun CustomAlert_Delete(psychometriclist: PsychometricEntity) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            psychometricViewModel.deletepsychometric(psychometriclist)
            mAlertDialog.dismiss()

            val intent = Intent(this, PsychometricListActivity::class.java)
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