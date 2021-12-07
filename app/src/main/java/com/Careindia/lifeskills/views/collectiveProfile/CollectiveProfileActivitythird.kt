package com.careindia.lifeskills.views.collectiveProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.ActivityCollectiveProfileThirdBinding
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMemberViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMemberViewModelFactory
import kotlinx.android.synthetic.main.activity_collective_profile_third.img_add
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*

class CollectiveProfileActivityThird : AppCompatActivity() {
    private lateinit var listbinding: ActivityCollectiveProfileThirdBinding
    var validate: Validate? = null
    lateinit var mstLookupViewModel: MstLookupViewModel
    lateinit var collectiveMemberViewModel: CollectiveMemberViewModel
    var iLanguageID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listbinding =
            DataBindingUtil.setContentView(this, R.layout.activity_collective_profile_third)
        validate = Validate(this)
        iLanguageID = validate!!.RetriveSharepreferenceInt(AppSP.iLanguageID)
        mstLookupViewModel =
            ViewModelProviders.of(this).get(MstLookupViewModel::class.java)
        val collectiveMemberDao = CareIndiaApplication.database?.collectiveMemDao()

        val collectiveMemberRepository =
            CollectiveMemberRepository(collectiveMemberDao!!)
        collectiveMemberViewModel =
            ViewModelProvider(this, CollectiveMemberViewModelFactory(collectiveMemberRepository))[
                    CollectiveMemberViewModel::class.java]

        listbinding.lifecycleOwner = this
        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, "")
            val intent = Intent(this, CollectiveProfileMemberActivity::class.java)
            startActivity(intent)
            finish()
        }
        fillRecyclerView()
        btn_save.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
            startActivity(intent)
            finish()
        }
        btn_prev.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
            finish()
        }
        bottomCLick()
    }

    fun bottomCLick() {
        lay_first.setBackgroundColor(resources.getColor(R.color.back))
        lay_secnd.setBackgroundColor(resources.getColor(R.color.back))
        ll_third.setBackgroundColor(resources.getColor(R.color.color_darkgrey))
        ll_fourth.setBackgroundColor(resources.getColor(R.color.back))
        ll_fifth.setBackgroundColor(resources.getColor(R.color.back))
        ll_six.setBackgroundColor(resources.getColor(R.color.back))

        lay_first.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        lay_secnd.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySec::class.java)
            startActivity(intent)
            finish()
        }
        /* ll_third.setOnClickListener {
             val intent = Intent(this, CollectiveProfileActivityThird::class.java)
             startActivity(intent)
             finish()
         }*/
        ll_fourth.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
            startActivity(intent)
            finish()
        }
        ll_fifth.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
            startActivity(intent)
            finish()
        }
        ll_six.setOnClickListener {
            val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fillRecyclerView() {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)
        collectiveMemberViewModel.memcollectiveData.observe(this, Observer {

            listbinding.rvList.adapter = CollectiveMemberAdapter(it,
                { selectedItem: CollectiveMemberEntity -> onItemClicked(selectedItem) },
                { deletedItem: CollectiveMemberEntity -> onItemDeleted(deletedItem) })
        })
    }

    private fun onItemClicked(collectivelist: CollectiveMemberEntity) {

        validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, collectivelist.GUID!!)
        val intent = Intent(this, CollectiveProfileMemberActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(collectivelist: CollectiveMemberEntity) {

        CustomAlert_Delete(collectivelist)

    }

    fun CustomAlert_Delete(collectivelist: CollectiveMemberEntity) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)

        mDialogView.btn_yes.setOnClickListener {
            collectiveMemberViewModel.deletemember(collectivelist)
            mAlertDialog.dismiss()

            val intent = Intent(this, CollectiveProfileActivityThird::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        mDialogView.btn_no.setOnClickListener {

            mAlertDialog.dismiss()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, CollectiveProfileListActivity::class.java)
        startActivity(intent)
        finish()
    }

}