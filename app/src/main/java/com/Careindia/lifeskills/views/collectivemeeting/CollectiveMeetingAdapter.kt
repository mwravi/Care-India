package com.careindia.lifeskills.views.collectivemeeting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.CollectiveMeetingItemBinding
import com.careindia.lifeskills.entity.CollectiveMeetingEntity


class CollectiveMeetingAdapter(
    private val collmeetingList: List<CollectiveMeetingEntity>,
    private val clickListener: (CollectiveMeetingEntity) -> Unit,
    private val ItemDeleted:(CollectiveMeetingEntity)->Unit
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
            ItemDeleted: (CollectiveMeetingEntity) -> Unit

        ) {
            binding.tvFfDate.text = collmeetingList.Dateform
           // binding.tvFfDate.text = collmeetingList.Dateform
          //  binding.tvFfDate.text = collmeetingList.Dateform
           // binding.tvFfDate.text = collmeetingList.Dateform

            binding.cardView.setOnClickListener {
                clickListener(collmeetingList)
            }
            binding.imgDlt.setOnClickListener {
                ItemDeleted(collmeetingList)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collmeetingList[position], clickListener,ItemDeleted)
    }

}