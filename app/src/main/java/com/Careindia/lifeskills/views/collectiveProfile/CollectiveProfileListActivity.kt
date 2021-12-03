package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectionProfileListBinding
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.repository.CollectiveRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveViewModelFactory
import com.careindia.lifeskills.views.adapter.CollectiveProfileAdapter
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collection_profile_list.*
import kotlinx.android.synthetic.main.activity_collective_profile_first.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileListActivity : AppCompatActivity() {
    var validate: Validate? = null
    private lateinit var listbinding: ActivityCollectionProfileListBinding
    lateinit var collectiveViewModel: CollectiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding= DataBindingUtil.setContentView(this,R.layout.activity_collection_profile_list)
        tv_title.setText(resources.getString(R.string.colective_profile))
        validate = Validate(this)
        val collectivedao = CareIndiaApplication.database?.collectiveDao()
        val commondao = CareIndiaApplication.database?.mstCommonDao()
        val collectiveRepository = CollectiveRepository(collectivedao!!,commondao!!)
        collectiveViewModel =
            ViewModelProvider(this, CollectiveViewModelFactory(collectiveRepository))[
                    CollectiveViewModel::class.java]

        listbinding.lifecycleOwner = this

        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID,"")
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID,"")
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        fillRecyclerView()
    }

    private fun fillRecyclerView() {
    listbinding.rvList.layoutManager = LinearLayoutManager(this)
        collectiveViewModel.collectiveData.observe(this, Observer {

            listbinding.rvList.adapter = CollectiveProfileAdapter(it,{selectedItem:CollectiveEntity->onItemClicked(selectedItem)},{deletedItem:CollectiveEntity->onItemDeleted(deletedItem)})
        })
    }

    private fun onItemClicked(collectivelist: CollectiveEntity){

        validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID,collectivelist.Col_GUID!!)
        val intent = Intent(this, CollectiveProfileActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(collectivelist: CollectiveEntity){

        CustomAlert_Delete(collectivelist)

    }

    fun CustomAlert_Delete(collectivelist:CollectiveEntity) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null,false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            collectiveViewModel.deletecollective(collectivelist)
            mAlertDialog.dismiss()

            val intent = Intent(this, CollectiveProfileListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        mDialogView.btn_no.setOnClickListener {

            mAlertDialog.dismiss()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}