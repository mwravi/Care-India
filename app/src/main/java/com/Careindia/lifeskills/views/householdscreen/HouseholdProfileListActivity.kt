package com.careindia.lifeskills.views.householdscreen

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
import com.careindia.lifeskills.databinding.ActivityHouseholdProfileListBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.repository.HouseholdProfileRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.HouseholdProfileViewModel
import com.careindia.lifeskills.viewmodelfactory.HouseholdProfileViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_household_profile_list.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class HouseholdProfileListActivity : AppCompatActivity() {
    private lateinit var hhbinding: ActivityHouseholdProfileListBinding
    var validate: Validate? = null
    lateinit var householdProfileViewModel: HouseholdProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hhbinding = DataBindingUtil.setContentView(this, R.layout.activity_household_profile_list)
        validate = Validate(this)


        val hhProfileDao = CareIndiaApplication.database?.hhProfileDao()
        val mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()!!
        val householdProfileRepository =
            HouseholdProfileRepository(hhProfileDao!!, mstDistrictDao)

        householdProfileViewModel = ViewModelProvider(
            this,
            HouseholdProfileViewModelFactory(householdProfileRepository)
        )[HouseholdProfileViewModel::class.java]

        hhbinding.lifecycleOwner = this


        img_Add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.HHGUID, "")
            val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
            startActivity(intent)
            finish()
        }
        tv_title.setText(resources.getString(R.string.household_profile))
        fillRecyclerView()
    }


    private fun fillRecyclerView() {
        hhbinding.rvList.layoutManager = LinearLayoutManager(this)
        householdProfileViewModel.hhData.observe(this, Observer {
            hhbinding.rvList.adapter = HouseholdProfileAdapter(it,
                { selectedItem: HouseholdProfileEntity -> onItemClicked(selectedItem) },
                { deletedItem: HouseholdProfileEntity -> onItemDeleted(deletedItem) })
        })
    }

    private fun onItemClicked(imProfilelist: HouseholdProfileEntity) {

        validate!!.SaveSharepreferenceString(AppSP.HHGUID, imProfilelist.HHGUID)
        val intent = Intent(this, HouseholdProfileFirstActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun onItemDeleted(imProfileList: HouseholdProfileEntity) {

        CustomAlert_Delete(imProfileList.HHGUID)

    }

    fun CustomAlert_Delete(HHGUID: String) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            householdProfileViewModel.deletehh_record(HHGUID)
            mAlertDialog.dismiss()

            val intent = Intent(this, HouseholdProfileListActivity::class.java)
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