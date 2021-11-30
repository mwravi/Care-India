package com.careindia.lifeskills.views.improfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.RowInvprofileItemBinding
import com.careindia.lifeskills.entity.IndividualProfileEntity


class IMProfileAdapter(
    private val imProfileList: List<IndividualProfileEntity>,
    private val clickListener: (IndividualProfileEntity) -> Unit
) :
    RecyclerView.Adapter<IMProfileAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowInvprofileItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_invprofile_item, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imProfileList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imProfileList[position], clickListener)
    }

    class ViewHolder(val binding: RowInvprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imProfileList: IndividualProfileEntity,
            clickListener: (IndividualProfileEntity) -> Unit
        ) {
            binding.tvCrpName.text = imProfileList.Name
            binding.tvFfDate.text = imProfileList.DateForm
            binding.cardView.setOnClickListener {
                clickListener(imProfileList)
            }
        }
    }
}