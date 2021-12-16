package com.careindia.lifeskills.views.collectiveProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.CollectiveProfilememItemRowBinding
import com.careindia.lifeskills.entity.CollectiveMemberEntity

class CollectiveMemberAdapter(
    private val collectiveList: List<CollectiveMemberEntity>,
    private val clickListener: (CollectiveMemberEntity) -> Unit,
    private val ItemDeleted: (CollectiveMemberEntity) -> Unit
) :
    RecyclerView.Adapter<CollectiveMemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: CollectiveProfilememItemRowBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.collective_profilemem_item_row,
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


    class ViewHolder(val binding: CollectiveProfilememItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            collectiveList: CollectiveMemberEntity,
            clickListener: (CollectiveMemberEntity) -> Unit,
            ItemDeleted: (CollectiveMemberEntity) -> Unit
        ) {
            binding.tvCrpName.text = collectiveList.Name
            binding.tvuniqueid.text=collectiveList.MemberID
            /*if (collectiveList.Gender == 1) {
                binding.tvGender.text = "Male"
            } else if (collectiveList.Gender == 2) {
                binding.tvGender.text = "Female"
            } else {
                binding.tvGender.text = "Transgender"
            }*/

            binding.tvAge.text = collectiveList.Age.toString()
            binding.cardView.setOnClickListener {
                clickListener(collectiveList)
            }
            binding.imgDlt.setOnClickListener {
                ItemDeleted(collectiveList)
            }
        }
    }
}