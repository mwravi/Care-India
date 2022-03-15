package com.careindia.lifeskills.views.prepostassessment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.AssessmentEntity
import com.careindia.lifeskills.entity.TrainingEntity
import com.careindia.lifeskills.utils.AppSP
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.AssessmentViewModel
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import com.careindia.lifeskills.viewmodel.MstTrainerViewModel
import com.careindia.lifeskills.viewmodel.TrainingViewModel
import kotlinx.android.synthetic.main.pre_post_assessment_list_item.view.*
import kotlinx.android.synthetic.main.pre_post_assessment_list_item.view.card_view
import kotlinx.android.synthetic.main.pre_post_assessment_list_item.view.imgDlt
import kotlinx.android.synthetic.main.pre_post_assessment_list_item.view.tvDate
import kotlinx.android.synthetic.main.pre_post_assessment_list_item.view.tvPersonName
import kotlinx.android.synthetic.main.pre_post_assessment_list_item.view.tv_status

class PrePostAssessmentAdapter(
    val items: List<TrainingEntity>,
    val context: Context,
    val validate: Validate,
    val trainingViewModel: TrainingViewModel,
    val mstLookupViewModel: MstLookupViewModel,
    val mstTrainerViewModel: MstTrainerViewModel
) : androidx.recyclerview.widget.RecyclerView.Adapter<PrePostAssessmentAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.pre_post_assessment_list_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(view: ViewHolder, pos: Int) {
        var trainerName = ""

        val value = mstLookupViewModel.getDescription(
            115,
            validate.RetriveSharepreferenceInt(AppSP.iLanguageID),
            items.get(pos).ModuleID!!
        )
        if ((items.get(pos).TrainerID)!=null){


            var traineridList = ArrayList<String>()

            var trainerid = items.get(pos).TrainerID.toString()

            val list = trainerid.split(",")
          var text = mstTrainerViewModel.getTrainerName(list).toString()

            trainerName = text.toString().replace("[", "").replace("]", "");



            }

        if (value.isNotEmpty()) {
            view.tvModuleName.setText(value)
        }
//        if(items.get(pos).IsEdited == 1) {
//            view.mainPrePost.setBackgroundResource(R.drawable.box_orange_white)
//        }
        if (trainerName!=null && trainerName.isNotEmpty()) {
            view.tv_PersonName.setText(trainerName)
        }
        view.tv_date.setText(validate.addDays(items.get(pos).DateoftrainingFrom!!.toInt())+" / "+validate.addDays(items.get(pos).DateoftrainingTo!!.toInt()))

        val status = items.get(pos).Status
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
//            validate.SaveSharepreferenceString(AppSP.PPAGUID, items.get(pos).AID)
            validate.SaveSharepreferenceInt(AppSP.TrainingID,items.get(pos).TrainingID)
            validate.SaveSharepreferenceInt(AppSP.TotalParticipant, items.get(pos).total_Participant!!)

            items.get(pos).BatchID?.let { it1 ->
                validate!!.SaveSharepreferenceInt(
                    AppSP.groupBatchID, it1
                )
            }

            val intent = Intent(context, PrePostAssessmentLifeSkillActivity::class.java)
            context.startActivity(intent)
        }

        view.imgDlt.setOnClickListener {
//            if (items.get(pos).IsEdited == 0 && items.get(pos).Status == 0) {
//                validate!!.CustomAlert(context, context.resources.getString(R.string.delete_record))
//            } else {
//                CustomAlert_Delete(items.get(pos).AID)
//            }

        }

    }

    class ViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val tvModuleName = view.tvModuleName
        val mainPrePost = view.prepost_main
        val tv_PersonName = view.tvPersonName
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
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)

        val btnyes =
            dialog.findViewById<View>(R.id.btn_yes) as Button
        val btnno =
            dialog.findViewById<View>(R.id.btn_no) as Button

        btnyes.setOnClickListener {
//            assessmentViewModel!!.deleteAssessmentdata(gUID)
            dialog.dismiss()

            val intent = Intent(context, PrePostAssessmentListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        btnno.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }
}