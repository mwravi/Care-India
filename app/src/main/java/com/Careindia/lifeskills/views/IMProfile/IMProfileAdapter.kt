package com.careindia.lifeskills.views.improfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.databinding.RowInvprofileItemBinding
import com.careindia.lifeskills.entity.IndividualProfileEntity
import com.careindia.lifeskills.utils.Validate


class IMProfileAdapter(
    private val imProfileList: List<IndividualProfileEntity>,
    private val clickListener: (IndividualProfileEntity) -> Unit,
    private val ItemDeleted: (IndividualProfileEntity) -> Unit,
    private val ItemInfo: (IndividualProfileEntity) -> Unit,
    private val ItemBenePrgT: (IndividualProfileEntity) -> Unit,
    val validate: Validate
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
        holder.bind(imProfileList[position], clickListener, ItemDeleted,ItemInfo,ItemBenePrgT, validate)
    }

    class ViewHolder(val binding: RowInvprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imProfileList: IndividualProfileEntity,
            clickListener: (IndividualProfileEntity) -> Unit,
            ItemDeleted: (IndividualProfileEntity) -> Unit,
            ItemInfo: (IndividualProfileEntity) -> Unit,
            ItemBenePrgT: (IndividualProfileEntity) -> Unit,
            validate: Validate
        ) {
            binding.tvName.text = imProfileList.Name
            binding.tvHhid.text = imProfileList.IndvCode
            if(imProfileList.IsEdited == 1){
                binding.imMainBack.setBackgroundResource(R.drawable.box_orange_white)
            }
//            else{
//                binding.imMainBack.setBackgroundResource(R.drawable.border_white)
//            }

            if(imProfileList.IsEdited == 0){
                binding.ivDelete.visibility = View.GONE
            }else{
                binding.ivDelete.visibility = View.VISIBLE
            }

//            binding.tvDate.text = validate!!.addDays(imProfileList.DateForm!!.toInt())
            binding.tvDate.text = imProfileList.DateForm?.let { validate.addDays(it.toInt()) }
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
            binding.ivbenept.setOnClickListener {
                ItemBenePrgT(imProfileList)
            }
        }
    }
}