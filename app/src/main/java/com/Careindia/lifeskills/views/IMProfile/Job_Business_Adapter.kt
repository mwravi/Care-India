package com.careindia.lifeskills.views.improfile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.utils.Validate
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import kotlinx.android.synthetic.main.job_business_item.view.*


class Job_Business_Adapter(
    var context: Context,
    var mstLookupEntity: List<MstLookupEntity>?,
    val sdata: String,
    var mstLookupViewModel:MstLookupViewModel,
    var iLanguageID:Int
) :
    RecyclerView.Adapter<Job_Business_Adapter.ViewHolder>() {

    var validate: Validate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.job_business_item, parent, false)
        validate = Validate(context)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mstLookupEntity!!.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_title.setText(mstLookupEntity?.get(position)?.Description)
        val arrdate = sdata.split(",")
       // binddata(context.resources.getString(R.string.select), holder.spin_priority, sdata)


        (context as IMProfileSixActivity).fillSpinner(context.resources.getString(R.string.select), holder.spin_priority,133,iLanguageID)

        if ((position+1) <= arrdate.size)
            holder.spin_priority.setSelection((context as IMProfileSixActivity).returnpos(validate!!.returnIntegerValue(arrdate[position]),133))


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_title = view.tv_title
        var spin_priority = view.spin_priority
    }


 /*   fun binddata(strValue: String, spin: Spinner, sdata: String) {
        val it = ArrayList<String>()

        if (mstLookupEntity != null) {
            for (i in 0 until mstLookupEntity!!.size) {

                it.add(i.toString())
            }
        }


        val iGen = it.size
        val name = arrayOfNulls<String>(iGen + 1)
        name[0] = strValue

        for (i in 0 until it.size) {
            name[i + 1] = it.get(i)
        }
        val adapter_category = ArrayAdapter<String>(
            context,
            R.layout.my_spinner_space_dashboard, name
        )
        adapter_category.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin.adapter = adapter_category




    }*/





}