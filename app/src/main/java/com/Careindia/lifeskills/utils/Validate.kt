package com.Careindia.lifeskills.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.view.ViewGroup
import android.widget.*
import com.Careindia.lifeskills.R
import java.text.SimpleDateFormat
import java.util.*

class Validate(context: Context) {
    lateinit var context: Context

init {
    this.context = context
}

    fun datePickerwithmindate(sec: Long, date1: EditText) {
        val myCalendar = Calendar.getInstance()
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val myFormat = "dd-MM-yyyy" // your format
                val sdf =
                    SimpleDateFormat(myFormat, Locale.getDefault())
                date1.setText(sdf.format(myCalendar.time))
            }

        if(date1.text.toString().length==0){
            val dpDialog = DatePickerDialog(
                context,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            dpDialog.show()
            dpDialog.datePicker.minDate = sec * 1000
            dpDialog.datePicker.maxDate = myCalendar.timeInMillis
        }else {
            var datelist = date1.text.toString().split("-")
            if (datelist.size == 3) {
                val dpDialog = DatePickerDialog(
                    context,
                    date,
                    datelist[2].toInt(),
                    datelist[1].toInt() - 1,
                    datelist[0].toInt()
                )
                dpDialog.show()
                dpDialog.datePicker.minDate = sec * 1000
                dpDialog.datePicker.maxDate = myCalendar.timeInMillis
            } else {
                val dpDialog = DatePickerDialog(
                    context,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )
                dpDialog.show()
                dpDialog.datePicker.minDate = sec * 1000
                dpDialog.datePicker.maxDate = myCalendar.timeInMillis
            }

        }
    }



    fun Daybetweentime(date2: String?): Long {
        var Value: String? = ""
        if (date2 != null && !date2.equals("null", ignoreCase = true) && date2.length > 0) {

            if(date2.length<16){
                Value = date2+" 00:00:00"
            }else{
                Value = date2
            }
        }
        val date = Value
        val olddate = "01-01-1970 00:00:00"
        var Seconds: Long = 0

        return if (olddate != null && olddate.length > 0 && date != null && date.length > 0) {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
            var Date1: Date? = null
            var Date2: Date? = null
            try {
                Date1 = sdf.parse(olddate)
                Date2 = sdf.parse(date)
                Seconds = (Date2.getTime() - Date1.getTime()) / 1000
                // Seconds = (int) Math. round (value);
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Seconds
        } else {
            Seconds
        }
    }


    @SuppressLint("ResourceType")
    fun dynamicRadio(context: Context, radioGroup: RadioGroup, values: Array<String?>) {

        for (i in values) {
            val radioButton1 = RadioButton(context)
            radioButton1.layoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            radioButton1.setText(i)
            radioButton1.id = values.indexOf(i)
            val params = RadioGroup.LayoutParams(context, null)
            params.setMargins(0, 0, 100, 0)
            radioButton1.setLayoutParams(params)
            if (radioGroup != null) {
                radioGroup.addView(radioButton1)
                radioGroup.setOnCheckedChangeListener { group, checkedId ->

                }
            }
        }

    }

    fun fillCheckBoxes(context: Context, linear: LinearLayout, data: Array<String?>) {
        val iGen = data.size
        val name = arrayOfNulls<String>(iGen + 1)
        for (i in data) {
            val multicheck1 = CheckBox(context)
            multicheck1.layoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            multicheck1.setText(i)
            multicheck1.id = data.indexOf(i)

            if (linear != null) {
                linear.addView(multicheck1)
            }

        }

    }

    fun GetAnswerTypeCheckBoxButtonID(linear: LinearLayout): String {
        var QusAns = ""
        for (i in 0 until linear.childCount) {

            val checkbox = linear.getChildAt(i) as CheckBox
            if (checkbox.isChecked) {
                if (QusAns.length == 0) {
                    QusAns = checkbox.id.toString()
                } else {
                    QusAns = (QusAns
                            + ","
                            + checkbox.id.toString())
                }
            }
        }
        return QusAns
    }

    fun SetAnswerTypeCheckBoxButton(layout: LinearLayout, value: String?) {

        for (i in 0 until layout.childCount) {

            val chkbox = layout.getChildAt(i) as CheckBox
            chkbox.setChecked(check(chkbox.id, value))
        }
    }

    fun check(ID: Int, Value: String?): Boolean {
        var iValue = false
        if (Value != null && !Value.equals("null", ignoreCase = true) && Value.length > 0) {
            val visit = Value.split("\\,".toRegex()).dropLastWhile {
                it.isEmpty()
            }.toTypedArray()
            for (i in visit.indices) {
                if (Integer.valueOf(visit[i]) == ID) {
                    iValue = true
                    break
                }
            }
        }
        return iValue
    }

    fun fillSpinnerLanguage(activity: Activity, spin: Spinner, Header: String?, Data: Array<String?>) {

        val adapter: ArrayAdapter<String?>
        val sValue = arrayOfNulls<String>(Data!!.size + 1)
        if (Header != null && Header.length > 0) {
            sValue[0] = Header
        } else {
            sValue[0] = activity.resources.getString(R.string.select)
        }
        for (i in Data.indices) {
            sValue[i + 1] = Data[i]
        }
        adapter = ArrayAdapter(
            activity,
            R.layout.my_spinner_space_dashboard, sValue
        )
        adapter.setDropDownViewResource(R.layout.my_spinner_dashboard)
        spin.setAdapter(adapter)

//        spin.adapter = adapter
    }
}