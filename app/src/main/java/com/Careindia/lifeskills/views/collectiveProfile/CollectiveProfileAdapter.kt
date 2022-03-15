package com.careindia.lifeskills.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.CollectiveProfileItemRowBinding
import com.careindia.lifeskills.entity.CollectiveEntity
import com.careindia.lifeskills.utils.Validate

class CollectiveProfileAdapter(
    private val collectiveList: List<CollectiveEntity>,
    private val clickListener: (CollectiveEntity) -> Unit,
    private val ItemDeleted: (CollectiveEntity) -> Unit,
    private val meetingClick: (CollectiveEntity) -> Unit,
    private val ItemInfo: (CollectiveEntity) -> Unit,
    private val cptItem: (CollectiveEntity) -> Unit,
    val validate: Validate
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
        holder.bind(
            collectiveList[position],
            clickListener,
            ItemDeleted,
            meetingClick,
            ItemInfo,
            cptItem,
            validate
        )
    }


    class ViewHolder(val binding: CollectiveProfileItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            collectiveList: CollectiveEntity,
            clickListener: (CollectiveEntity) -> Unit,
            ItemDeleted: (CollectiveEntity) -> Unit,
            meetingClick: (CollectiveEntity) -> Unit,
            ItemInfo: (CollectiveEntity) -> Unit,
            cptItem: (CollectiveEntity) -> Unit,
            validate: Validate
        ) {
            binding.tvCrpName.text = collectiveList.CollectiveName

//            val mtgdate=validate.RetriveSharepreferenceString(AppSP.CollectiveGUID)
//                ?.let { CareIndiaApplication.database?.collectiveMeetingDao()?.getMaxmtgdate(it) }
            val mtgdate = collectiveList.Col_GUID
                .let { CareIndiaApplication.database?.collectiveMeetingDao()?.getMaxmtgdate(it) }

            binding.tvLastMtgDate.text = mtgdate?.let { validate.addDays(it) }

            binding.tvcrpid.text = collectiveList.CollectiveID

            if (collectiveList.IsEdited == 1) {
                binding.colLayout.setBackgroundResource(R.drawable.box_orange_white)
            }
            if (collectiveList.IsEdited == 0) {
                binding.imgDlt.visibility = View.GONE
            } else {
                binding.imgDlt.visibility = View.VISIBLE
            }

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

            binding.imgMeeting.setOnClickListener {
//                validate!!.SaveSharepreferenceString(AppSP.CollectiveGUID,collectiveList.Col_GUID)
                meetingClick(collectiveList)
            }

            binding.ivInfo.setOnClickListener {
                ItemInfo(collectiveList)
            }

            binding.imgCpt.setOnClickListener {
                cptItem(collectiveList)
            }
        }
    }
}

