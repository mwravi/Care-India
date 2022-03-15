package com.careindia.lifeskills.views.beneficiaryProgressTracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import androidx.lifecycle.Observer
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.databinding.RowBeneficiaryProgressLayoutBinding
import com.careindia.lifeskills.entity.BeneficiaryEntity
import com.careindia.lifeskills.utils.Validate

class BeneficiaryProgressAdapter(
    private val beneficiaryList: List<BeneficiaryEntity>,
    private val clickListener: (BeneficiaryEntity) -> Unit,
    private val ItemDeleted: (BeneficiaryEntity) -> Unit,
    private val ItemInfo: (BeneficiaryEntity) -> Unit,
    val validate: Validate,
    val benedataList: BeneficiaryProgressListActivity
) :
    RecyclerView.Adapter<BeneficiaryProgressAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowBeneficiaryProgressLayoutBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.row_beneficiary_progress_layout,
                parent,
                false
            )


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return beneficiaryList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            beneficiaryList[position],
            clickListener,
            ItemDeleted,
            ItemInfo,
            validate,
            benedataList
        )
    }

    class ViewHolder(val binding: RowBeneficiaryProgressLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            beneficiaryList: BeneficiaryEntity,
            clickListener: (BeneficiaryEntity) -> Unit,
            ItemDeleted: (BeneficiaryEntity) -> Unit,
            ItemInfo: (BeneficiaryEntity) -> Unit,
            validate: Validate,
            benedataList: BeneficiaryProgressListActivity

        ) {
            if (beneficiaryList.IsEdited == 1) {
                binding.benMainBack.setBackgroundResource(R.drawable.box_orange_white)
            }
            if (beneficiaryList.IsEdited == 0) {
                binding.ivDelete.visibility = View.GONE
            } else {
                binding.ivDelete.visibility = View.VISIBLE
            }

//            binding.tvcptid.text = beneficiaryList.Collective_Code.toString()
            binding.tvCollName.text = beneficiaryList.Name_Collective.toString()
            binding.tvdate.text = validate.addDays(beneficiaryList.DateForm!!.toInt())


//            CareIndiaApplication.database!!.imProfileDao().getIdvProfiledatabyGuid(beneficiaryList.IMGUID!!)
//                .observe(benedataList, Observer {
//                    if (it != null && it.size > 0) {
//                        binding.tvcptid.text = it.get(0).IndvCode
//
//                    }
//                })

            var imCode = ""
            var colName = ""
            var indvCode = CareIndiaApplication.database!!.imProfileDao()
                .getIdvPrfbyGuid(beneficiaryList.IMGUID!!)
            if (!indvCode.isNullOrEmpty()) {
                imCode = indvCode
            }
            var colname = CareIndiaApplication.database!!.collectiveMemDao()
                .getColPrfDatabyGuid(beneficiaryList.Col_GUID!!)
            if (!colname.isNullOrEmpty()) {
                colName = colname
            }
            binding.tvcptid.text = imCode + " (" + colName + ")"

            val status = beneficiaryList.Status
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
                clickListener(beneficiaryList)
            }
            binding.ivDelete.setOnClickListener {
                ItemDeleted(beneficiaryList)
            }

            binding.ivInfo.setOnClickListener {
                ItemInfo(beneficiaryList)
            }
        }
    }
}