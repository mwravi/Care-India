package com.careindia.lifeskills.views.psychometricscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.RowPsychometricItemBinding
import com.careindia.lifeskills.entity.PsychometricEntity
import com.careindia.lifeskills.utils.Validate


class PsychometricAdapter(
    private val psychometricList: List<PsychometricEntity>,
    private val clickListener: (PsychometricEntity) -> Unit,
    private val ItemDeleted: (PsychometricEntity) -> Unit,
    private val ItemInfo: (PsychometricEntity) -> Unit,
    val psychoDataList: PsychometricListActivity,
    val validate: Validate
) :
    RecyclerView.Adapter<PsychometricAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowPsychometricItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_psychometric_item, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return psychometricList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            psychometricList[position],
            clickListener,
            ItemDeleted,
            ItemInfo,
            psychoDataList,
            validate
        )
    }

    class ViewHolder(val binding: RowPsychometricItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            psychometricList: PsychometricEntity,
            clickListener: (PsychometricEntity) -> Unit,
            ItemDeleted: (PsychometricEntity) -> Unit,
            ItemInfo: (PsychometricEntity) -> Unit,
            psychoDataList: PsychometricListActivity,
            validate: Validate

        ) {

            if (psychometricList.IsEdited == 1) {
                binding.psychoMain.setBackgroundResource(R.drawable.box_orange_white)
            }
            if (psychometricList.IsEdited == 0) {
                binding.ivDelete.visibility = View.GONE
            } else {
                binding.ivDelete.visibility = View.VISIBLE
            }

            binding.tvName.text = psychometricList.Name_participant
//            binding.tvHhid.text = psychometricList.IMID
            binding.tvDate.text = validate.addDays(psychometricList.Date!!.toInt())

            CareIndiaApplication.database!!.imProfileDao()
                .getIdvProfiledatabyGuid(psychometricList.IMID!!)
                .observe(psychoDataList, Observer {
                    if (it != null && it.size > 0) {
                        binding.tvHhid.text = it.get(0).IndvCode

                    }
                })
            val status = psychometricList.Status
            when (status) {
                0 -> {
                    binding.ivInfo.visibility = View.GONE
                    binding.tvStatus.setBackgroundResource(R.color.darkgrey)
                }
                2 -> {
                    binding.ivInfo.visibility = View.GONE
                    binding.tvStatus.setBackgroundResource(R.color.button_bgcolor)
                }
                3 -> {
                    binding.ivInfo.visibility = View.VISIBLE
                    binding.tvStatus.setBackgroundResource(R.color.red_color)
                }
                4 -> {
                    binding.ivInfo.visibility = View.GONE
                    binding.tvStatus.setBackgroundResource(R.color.blue_dark)
                }
                5 -> {
                    binding.ivInfo.visibility = View.GONE
                    binding.tvStatus.setBackgroundResource(R.color.green)
                }
            }
            binding.cardView.setOnClickListener {
                clickListener(psychometricList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(psychometricList)
            }

            binding.ivInfo.setOnClickListener {
                ItemInfo(psychometricList)
            }
        }
    }
}