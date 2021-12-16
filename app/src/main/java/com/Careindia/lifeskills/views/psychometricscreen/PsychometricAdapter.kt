package com.careindia.lifeskills.views.psychometricscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.RowPsychometricItemBinding
import com.careindia.lifeskills.entity.PsychometricEntity


class PsychometricAdapter(
    private val psychometricList: List<PsychometricEntity>,
    private val clickListener: (PsychometricEntity) -> Unit,
    private val ItemDeleted: (PsychometricEntity) -> Unit
) :
    RecyclerView.Adapter<PsychometricAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowPsychometricItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_psychometric_item, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return psychometricList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(psychometricList[position], clickListener, ItemDeleted)
    }

    class ViewHolder(val binding: RowPsychometricItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            psychometricList: PsychometricEntity,
            clickListener: (PsychometricEntity) -> Unit,
            ItemDeleted: (PsychometricEntity) -> Unit

        ) {
            binding.tvName.text = psychometricList.Name_participant
            binding.tvHhid.text = psychometricList.IMID
            binding.tvDate.text = psychometricList.Date
            binding.cardView.setOnClickListener {
                clickListener(psychometricList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(psychometricList)
            }
        }
    }
}