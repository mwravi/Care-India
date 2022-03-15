package com.careindia.lifeskills.views.householdscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.RowHhprofileItemBinding


import com.careindia.lifeskills.entity.HouseholdProfileEntity
import com.careindia.lifeskills.utils.Validate


class HouseholdProfileAdapter(
    private val imProfileList: List<HouseholdProfileEntity>,
    private val clickListener: (HouseholdProfileEntity) -> Unit,
    private val ItemDeleted:(HouseholdProfileEntity)->Unit,
    private val ItemInfo:(HouseholdProfileEntity)->Unit,
    private val ItemAddIM:(HouseholdProfileEntity)->Unit,
    val validate: Validate
) :
    RecyclerView.Adapter<HouseholdProfileAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowHhprofileItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_hhprofile_item, parent, false)



        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imProfileList.size
    }

    class ViewHolder(val binding: RowHhprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imProfileList: HouseholdProfileEntity,
            clickListener: (HouseholdProfileEntity) -> Unit,
            ItemDeleted: (HouseholdProfileEntity) -> Unit,
            ItemInfo: (HouseholdProfileEntity) -> Unit,
            ItemAddIM: (HouseholdProfileEntity) -> Unit,
            validate: Validate

        ) {
            binding.tvName.text = imProfileList.Name
            binding.tvHhid.text = imProfileList.HHCode

            if(imProfileList.IsEdited == 1){
                binding.hhMainLay.setBackgroundResource(R.drawable.box_orange_white)
            }
            if(imProfileList.IsEdited == 0){
                binding.ivDelete.visibility = View.GONE
            }else{
                binding.ivDelete.visibility = View.VISIBLE
            }

            binding.tvDate.text = imProfileList.Dateform?.let { validate.addDays(it.toInt()) }
            val status = imProfileList.Status
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
                clickListener(imProfileList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(imProfileList)
            }

            binding.ivInfo.setOnClickListener {
                ItemInfo(imProfileList)
            }

            binding.ivAddIndividual.setOnClickListener {
                ItemAddIM(imProfileList)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imProfileList[position], clickListener,ItemDeleted,ItemInfo,ItemAddIM,validate)
    }

}