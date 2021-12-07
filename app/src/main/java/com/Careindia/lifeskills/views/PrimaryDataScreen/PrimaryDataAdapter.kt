package com.careindia.lifeskills.views.primarydatascreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.RowInvprofileItemBinding
import com.careindia.lifeskills.entity.PrimaryDataEntity


class PrimaryDataAdapter(
    private val primaryList: List<PrimaryDataEntity>,
    private val clickListener: (PrimaryDataEntity) -> Unit,
    private val ItemDeleted:(PrimaryDataEntity)->Unit
) :
    RecyclerView.Adapter<PrimaryDataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowInvprofileItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_invprofile_item, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return primaryList.size
    }




    class ViewHolder(val binding: RowInvprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imProfileList: PrimaryDataEntity,
            clickListener: (PrimaryDataEntity) -> Unit,
            ItemDeleted: (PrimaryDataEntity) -> Unit

        ) {
            binding.tvName.text = imProfileList.Name
            binding.tvHhid.text = imProfileList.PDCGUID
            binding.tvDate.text = imProfileList.Locality
            binding.cardView.setOnClickListener {
                clickListener(imProfileList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(imProfileList)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(primaryList[position], clickListener,ItemDeleted)
    }

}