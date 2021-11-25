package com.careindia.lifeskills.utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstCommonEntity

/**
 * fill the Spinner with all available projects.
 * Set the Spinner selection to selectedProject.
 * If the selection changes, call the InverseBindingAdapter
 */
@BindingAdapter(value = ["users", "selectedUser", "selectedUserAttrChanged"], requireAll = false)
fun setUsers(spinner: Spinner, users: List<MstCommonEntity>?, selectedUser: MstCommonEntity?, listener: InverseBindingListener) {
    if (users == null) return
    spinner.adapter = NameAdapter(spinner.context, R.layout.my_spinner_dashboard, users)
    setCurrentSelection(spinner, selectedUser)
    setSpinnerListener(spinner, listener)
}


@InverseBindingAdapter(attribute = "selectedUser")
fun getSelectedUser(spinner: Spinner): MstCommonEntity? {
//    Toast.makeText(
//        spinner.context,
//        (spinner.selectedItem as MstCommonEntity?)?.value,
//        Toast.LENGTH_LONG
//    ).show()
    return spinner.selectedItem as MstCommonEntity?
}

private fun setSpinnerListener(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  = listener.onChange()
        override fun onNothingSelected(adapterView: AdapterView<*>) = listener.onChange()
    }
}

private fun setCurrentSelection(spinner: Spinner, selectedItem: MstCommonEntity?): Boolean {
    for (index in 0 until spinner.adapter.count) {
        if (spinner.getItemAtPosition(index) == selectedItem?.value) {
            spinner.setSelection(index)
            return true
        }
    }
    return false
}