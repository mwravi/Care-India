package com.careindia.lifeskills.utils

import android.widget.CompoundButton
import androidx.databinding.BindingAdapter

interface OnUserCheckedChangeListener {
    fun onUserCheckChange(view: CompoundButton, isChecked:Boolean)
}
@BindingAdapter("onUserCheckedChange")
fun setUserCheckedChangeListener(view:CompoundButton, listener: OnUserCheckedChangeListener?){
    if(listener == null){
        view.setOnClickListener(null)
    }else{
        view.setOnClickListener {
            listener.onUserCheckChange(view, view.isChecked)
        }
    }
}