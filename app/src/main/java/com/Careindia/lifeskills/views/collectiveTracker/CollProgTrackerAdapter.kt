package com.careindia.lifeskills.views.collectiveTracker

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.CollectiveProgressTrackerEntity
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.CollectiveProgressTrackerViewModel
import kotlinx.android.synthetic.main.coll_prog_tracker_list_item.view.*


class CollProgTrackerAdapter(
    val items: List<CollectiveProgressTrackerEntity>,
    val context: Context,
    val validate: Validate,
    val collectiveProgressTrackerViewModel: CollectiveProgressTrackerViewModel
) : androidx.recyclerview.widget.RecyclerView.Adapter<CollProgTrackerAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.coll_prog_tracker_list_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(view: ViewHolder, pos: Int) {

        view.tv_CuCode.text = items.get(pos).CollectiveID
        view.tv_PersonName.text = items.get(pos).FPersonName
        view.tv_date.text = validate.addDays(items.get(pos).Date!!.toInt())

        if (items.get(0).IsEdited == 1) {
            view.mainColTrakr.setBackgroundResource(R.drawable.box_orange_white)
        }
        if (items.get(0).IsEdited == 0) {
            view.imgDlt.visibility = View.GONE
        }else{
            view.imgDlt.visibility = View.VISIBLE
        }

        val status = items.get(0).Status
        when (status) {
            0 -> {
                view.ivinfo.visibility = View.GONE
                view.tvStatus.setBackgroundResource(R.color.darkgrey)
            }
            2 -> {
                view.ivinfo.visibility = View.GONE
                view.tvStatus.setBackgroundResource(R.color.button_bgcolor)
            }
            3 -> {
                view.ivinfo.visibility = View.VISIBLE
                view.tvStatus.setBackgroundResource(R.color.red_color)
            }
            4 -> {
                view.ivinfo.visibility = View.GONE
                view.tvStatus.setBackgroundResource(R.color.blue_dark)
            }
            5 -> {
                view.ivinfo.visibility = View.GONE
                view.tvStatus.setBackgroundResource(R.color.green)
            }
        }

        view.cardView.setOnClickListener {
            validate.SaveSharepreferenceString(AppSP.CPTGUID, items.get(pos).CPT_GUID)
            val intent = Intent(context, CollProgTrackerFirst::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        view.ivinfo.setOnClickListener {
            validate.CustomAlertRejected(context, items.get(0).Remarks)
        }

        view.imgDlt.setOnClickListener {
//            if (items.get(pos).IsEdited == 0 && items.get(pos).Status == 0) {
//                validate!!.CustomAlert(context, context.resources.getString(R.string.delete_record))
//            } else {
            CustomAlert_Delete(items.get(pos).CPT_GUID)
//            }

        }

    }

    class ViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val tv_CuCode = view.tvCuCode
        val tv_PersonName = view.tvPersonName
        val mainColTrakr = view.coltracker_main
        val tv_date = view.tvDate
        val cardView = view.card_view
        val imgDlt = view.imgDlt
        val ivinfo = view.iv_info
        val tvStatus = view.tv_status

    }

    fun CustomAlert_Delete(gUID: String) {
        val dialog = Dialog(context)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.delete_dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams

        val btnyes =
            dialog.findViewById<View>(R.id.btn_yes) as Button
        val btnno =
            dialog.findViewById<View>(R.id.btn_no) as Button

        btnyes.setOnClickListener {
            collectiveProgressTrackerViewModel.deleteCollProgTrackerdata(gUID)
            dialog.dismiss()

            val intent = Intent(context, CollProgTrackerListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        btnno.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }
}