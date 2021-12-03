package com.careindia.lifeskills.views.improfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.RowInvprofileItemBinding
import com.careindia.lifeskills.entity.HouseholdProfileEntity


class HouseholdProfileAdapter(
    private val imProfileList: List<HouseholdProfileEntity>,
    private val clickListener: (HouseholdProfileEntity) -> Unit,
    private val ItemDeleted:(HouseholdProfileEntity)->Unit
) :
    RecyclerView.Adapter<HouseholdProfileAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowInvprofileItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_invprofile_item, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imProfileList.size
    }




    class ViewHolder(val binding: RowInvprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imProfileList: HouseholdProfileEntity,
            clickListener: (HouseholdProfileEntity) -> Unit,
            ItemDeleted: (HouseholdProfileEntity) -> Unit

        ) {
            binding.tvName.text = imProfileList.Name
            binding.tvHhid.text = imProfileList.HHCode
            binding.cardView.setOnClickListener {
                clickListener(imProfileList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(imProfileList)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imProfileList[position], clickListener,ItemDeleted)
    }

}