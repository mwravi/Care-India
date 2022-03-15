package com.careindia.lifeskills.views.collectivemeeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.CollectiveMeetingItemBinding
import com.careindia.lifeskills.entity.CollectiveMeetingEntity
import com.careindia.lifeskills.utils.Validate


class CollectiveMeetingAdapter(
    private val collmeetingList: List<CollectiveMeetingEntity>,
    private val clickListener: (CollectiveMeetingEntity) -> Unit,
    private val ItemDeleted: (CollectiveMeetingEntity) -> Unit,
    private val ItemInfo: (CollectiveMeetingEntity) -> Unit,
    val validate: Validate?
) :
    RecyclerView.Adapter<CollectiveMeetingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CollectiveMeetingItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.collective_meeting_item, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collmeetingList.size
    }




    class ViewHolder(val binding: CollectiveMeetingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            collmeetingList: CollectiveMeetingEntity,
            clickListener: (CollectiveMeetingEntity) -> Unit,
            ItemDeleted: (CollectiveMeetingEntity) -> Unit,
            ItemInfo: (CollectiveMeetingEntity) -> Unit,
            validate: Validate?

        ) {
            if (validate != null) {
                binding.tvDate.text = collmeetingList.Meeting_date?.let { validate.addDays(it.toInt()) }
            }

            if(collmeetingList.IsEdited == 1) {
                binding.colmeetMain.setBackgroundResource(R.drawable.box_orange_white)
            }
            if(collmeetingList.IsEdited == 0) {
                binding.imgDlt.visibility = View.GONE
            }else{
                binding.imgDlt.visibility = View.VISIBLE
            }


            val status = collmeetingList.Status
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
           // binding.tvFfDate.text = collmeetingList.Dateform
          //  binding.tvFfDate.text = collmeetingList.Dateform
           // binding.tvFfDate.text = collmeetingList.Dateform

            binding.cardView.setOnClickListener {
                clickListener(collmeetingList)
            }
            binding.imgDlt.setOnClickListener {
                ItemDeleted(collmeetingList)
            }

            binding.ivInfo.setOnClickListener {
                ItemInfo(collmeetingList)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collmeetingList[position], clickListener,ItemDeleted,ItemInfo,validate)
    }

}