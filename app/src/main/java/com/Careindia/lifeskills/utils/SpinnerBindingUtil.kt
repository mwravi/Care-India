package com.careindia.lifeskills.utils

import android.view.View
import androidx.appcompat.widget.AppCompatSpinner

import androidx.databinding.InverseBindingAdapter

import android.widget.ArrayAdapter

import android.widget.AdapterView

import androidx.databinding.InverseBindingListener

import androidx.databinding.BindingAdapter




class SpinnerBindingUtil {
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun bindSpinnerData(
        pAppCompatSpinner: AppCompatSpinner,
        newSelectedValue: String?,
        newTextAttrChanged: InverseBindingListener
    ) {
        pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                newTextAttrChanged.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        if (newSelectedValue != null) {
            val pos =
                (pAppCompatSpinner.adapter as ArrayAdapter<String?>).getPosition(newSelectedValue)
            pAppCompatSpinner.setSelection(pos, true)
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedValue(pAppCompatSpinner: AppCompatSpinner): String? {
        return pAppCompatSpinner.selectedItem as String
    }
}