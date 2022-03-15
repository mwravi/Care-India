package com.careindia.lifeskills.views.collectiveProfile

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.CollectiveProfilememItemRowBinding
import com.careindia.lifeskills.entity.CollectiveMemberEntity

class CollectiveMemberAdapter(
    private val collectiveList: List<CollectiveMemberEntity>,
    private val clickListener: (CollectiveMemberEntity) -> Unit,
    private val ItemDeleted: (CollectiveMemberEntity) -> Unit,
    private val ItemInfo: (CollectiveMemberEntity) -> Unit,
    val collectiveDataList: CollectiveProfileActivityThird
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
        holder.bind(
            collectiveList[position],
            clickListener,
            ItemDeleted,
            ItemInfo,
            collectiveDataList
        )
    }


    class ViewHolder(val binding: CollectiveProfilememItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            collectiveList: CollectiveMemberEntity,
            clickListener: (CollectiveMemberEntity) -> Unit,
            ItemDeleted: (CollectiveMemberEntity) -> Unit,
            ItemInfo: (CollectiveMemberEntity) -> Unit,
            collectiveDataList: CollectiveProfileActivityThird
        ) {
            binding.tvCrpName.text = collectiveList.Name

            CareIndiaApplication.database!!.imProfileDao()
                .getIdvProfiledatabyGuid(collectiveList.IndvGuid!!)
                .observe(collectiveDataList, Observer {
                    if (it != null && it.size > 0) {
                        binding.tvuniqueid.text = it.get(0).IndvCode

                    }
                })

//            binding.tvuniqueid.text=collectiveList.IndvGuid
            /*if (collectiveList.Gender == 1) {
                binding.tvGender.text = "Male"
            } else if (collectiveList.Gender == 2) {
                binding.tvGender.text = "Female"
            } else {
                binding.tvGender.text = "Transgender"
            }*/

            if (collectiveList.IsEdited == 1) {
                binding.colMemberMain.setBackgroundResource(R.drawable.box_orange_white)
            }
            if (collectiveList.IsEdited == 0) {
                binding.imgDlt.visibility = View.GONE
            } else {
                binding.imgDlt.visibility = View.VISIBLE
            }


            binding.tvAge.text = collectiveList.Age.toString()
            val status = collectiveList.Status
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
                clickListener(collectiveList)
            }
            binding.imgDlt.setOnClickListener {
                ItemDeleted(collectiveList)
            }

            binding.ivInfo.setOnClickListener {
                ItemInfo(collectiveList)
            }
        }
    }
}