package com.careindia.lifeskills.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.CollectiveProfileItemRowBinding
import com.careindia.lifeskills.entity.CollectiveEntity

class CollectiveProfileAdapter(
    private val collectiveList: List<CollectiveEntity>,
    private val clickListener: (CollectiveEntity) -> Unit,
    private val ItemDeleted: (CollectiveEntity) -> Unit
) :
    RecyclerView.Adapter<CollectiveProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: CollectiveProfileItemRowBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.collective_profile_item_row,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collectiveList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collectiveList[position], clickListener, ItemDeleted)
    }


    class ViewHolder(val binding: CollectiveProfileItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            collectiveList: CollectiveEntity,
            clickListener: (CollectiveEntity) -> Unit,
            ItemDeleted: (CollectiveEntity) -> Unit
        ) {
            binding.tvCrpName.text = collectiveList.CollectiveName
            binding.tvFfDate.text = collectiveList.DateForm
            binding.tvcrpid.text = collectiveList.CollectiveID
            binding.cardView.setOnClickListener {
                clickListener(collectiveList)
            }
            binding.imgDlt.setOnClickListener {
                ItemDeleted(collectiveList)
            }
        }
    }
}

