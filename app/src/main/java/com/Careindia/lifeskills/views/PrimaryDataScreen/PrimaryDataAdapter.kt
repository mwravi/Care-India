package com.careindia.lifeskills.views.primarydatascreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.careindia.lifeskills.R

class PrimaryDataAdapter(var context: Context) :
    RecyclerView.Adapter<PrimaryDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(context).inflate(R.layout.primary_data_item_row, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}