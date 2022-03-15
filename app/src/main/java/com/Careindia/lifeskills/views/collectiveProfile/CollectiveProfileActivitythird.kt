package com.careindia.lifeskills.views.collectiveProfile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Button
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
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.entity.CollectiveMemberEntity
import com.careindia.lifeskills.repository.CollectiveMemberRepository
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveMemberViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodelfactory.CollectiveMemberViewModelFactory
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_collective_profile_third.*
import kotlinx.android.synthetic.main.buttons_save_cancel.*
import kotlinx.android.synthetic.main.collectivetab.*
import kotlinx.android.synthetic.main.delete_dialog_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

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
        tv_title.text = getString(R.string.collmember)
        img_setting.setOnClickListener {
            val intent = Intent(this, HomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        val profileData = CareIndiaApplication.database?.collectiveDao()
            ?.getColldatabyGuid(validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!)
        val memDataCount = CareIndiaApplication.database?.collectiveMemDao()
            ?.getAllData(
                validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!
            )

        if (profileData!!.isNotEmpty() && profileData[0].NoMembers!! > 0 && memDataCount == profileData[0].NoMembers) {
            tbladd.visibility = INVISIBLE
        }

        if (profileData[0].NoMembers!! == 0 || profileData[0].NoMembers!! == -1) {
            tbladd.visibility = INVISIBLE
        }
        img_add.setOnClickListener {
            validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, "")
            val intent = Intent(this, CollectiveProfileMemberActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_back.setOnClickListener {
            val intent = Intent(this, CollectiveProfileListActivity::class.java)
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
        autoSmoothScroll()
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
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivitySec::class.java)
                startActivity(intent)
                finish()
            }
        }
        /* ll_third.setOnClickListener {
             val intent = Intent(this, CollectiveProfileActivityThird::class.java)
             startActivity(intent)
             finish()
         }*/
        ll_fourth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivityFourth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_fifth.setOnClickListener {
            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivityFifth::class.java)
                startActivity(intent)
                finish()
            }
        }
        ll_six.setOnClickListener {

            if (validate!!.RetriveSharepreferenceString(AppSP.CollectiveGUID)!!.length > 0) {
                val intent = Intent(this, CollectiveProfileActivitySixth::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun fillRecyclerView() {
        listbinding.rvList.layoutManager = LinearLayoutManager(this)
        collectiveMemberViewModel.memcollectiveData.observe(this, Observer {

            listbinding.rvList.adapter = CollectiveMemberAdapter(it,
                { selectedItem: CollectiveMemberEntity -> onItemClicked(selectedItem) },
                { deletedItem: CollectiveMemberEntity -> onItemDeleted(deletedItem) },
                { infoItem: CollectiveMemberEntity -> onItemInfo(infoItem) },
                this)

        })
    }

    private fun onItemClicked(collectivelist: CollectiveMemberEntity) {

        validate!!.SaveSharepreferenceString(AppSP.CollectiveMemberGUID, collectivelist.GUID!!)
        val intent = Intent(this, CollectiveProfileMemberActivity::class.java)
        startActivity(intent)

    }

    private fun onItemDeleted(collectivelist: CollectiveMemberEntity) {
//        if (collectivelist.IsEdited == 0 && collectivelist.Status == 0) {
//            validate!!.CustomAlert(this, resources.getString(R.string.delete_record))
//        } else {
            CustomAlert_Delete(collectivelist)
//        }
    }


    private fun onItemInfo(collectivelist: CollectiveMemberEntity) {

//       validate!!.CustomAlertRejected(this,collectivelist.Remarks)

    }


    fun CustomAlert_Delete(collectivelist: CollectiveMemberEntity) { // Create custom dialog object
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
            collectiveMemberViewModel.deletemember(collectivelist)
            dialog.dismiss()
            val intent = Intent(this, CollectiveProfileActivityThird::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        btnno.setOnClickListener {


            dialog.dismiss()
        }

        // Display the dialog
        dialog.show()
    }


//    fun CustomAlert_Delete(collectivelist: CollectiveMemberEntity) {
//        val mDialogView =
//            LayoutInflater.from(this).inflate(R.layout.delete_dialog_layout, null, false)
//        val mBuilder = AlertDialog.Builder(this)
//            .setView(mDialogView)
//        val mAlertDialog = mBuilder.show()
//        mAlertDialog.setCanceledOnTouchOutside(false)
//
//        mDialogView.btn_yes.setOnClickListener {
//            collectiveMemberViewModel.deletemember(collectivelist)
//            mAlertDialog.dismiss()
//
//            val intent = Intent(this, CollectiveProfileActivityThird::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            this.startActivity(intent)
//        }
//        mDialogView.btn_no.setOnClickListener {
//
//            mAlertDialog.dismiss()
//        }
//    }
    fun autoSmoothScroll() {
//        val hsv = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
        horizontalScroll.postDelayed({ //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            horizontalScroll.smoothScrollBy(500, 0)
        }, 100)
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        /*  val intent = Intent(this, CollectiveProfileListActivity::class.java)
          startActivity(intent)
          finish()*/
    }

}