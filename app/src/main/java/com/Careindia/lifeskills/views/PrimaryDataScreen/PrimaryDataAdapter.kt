package com.careindia.lifeskills.views.primarydatascreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.RowEdpPrfItemsBinding
import com.careindia.lifeskills.entity.PrimaryDataEntity
import com.careindia.lifeskills.utils.Validate


class PrimaryDataAdapter(
    private val primaryList: List<PrimaryDataEntity>,
    private val clickListener: (PrimaryDataEntity) -> Unit,
    private val ItemDeleted: (PrimaryDataEntity) -> Unit,
    private val ItemInfo: (PrimaryDataEntity) -> Unit,
    val validate: Validate,
    val primaryDataList: PrimaryDataListActivity
) :
    RecyclerView.Adapter<PrimaryDataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowEdpPrfItemsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_edp_prf_items, parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return primaryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(primaryList[position], clickListener, ItemDeleted,ItemInfo, validate, primaryDataList)
    }


    class ViewHolder(val binding: RowEdpPrfItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imProfileList: PrimaryDataEntity,
            clickListener: (PrimaryDataEntity) -> Unit,
            ItemDeleted: (PrimaryDataEntity) -> Unit,
            ItemInfo: (PrimaryDataEntity) -> Unit,
            validate: Validate,
            primaryDataList: PrimaryDataListActivity

        ) {
            if(imProfileList.IsEdited == 1) {
                binding.edpMain.setBackgroundResource(R.drawable.box_orange_white)
            }
            if(imProfileList.IsEdited == 0) {
                binding.ivDelete.visibility = View.GONE
            }else{
                binding.ivDelete.visibility = View.VISIBLE
            }

            binding.tvName.text = imProfileList.Name
            binding.tvDate.text = validate.addDays(imProfileList.CollectionDate!!.toInt())
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
            CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(imProfileList.IMGUID!!)
                .observe(primaryDataList, Observer {
                    if (it != null && it.size > 0) {
                        binding.tvImid.text = it.get(0).IndvCode

                    }
                })
            binding.cardView.setOnClickListener {
                clickListener(imProfileList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(imProfileList)
            }
            binding.ivInfo.setOnClickListener {
                ItemInfo(imProfileList)
            }
        }
    }


    fun fillDataFromIM() {


    }

}