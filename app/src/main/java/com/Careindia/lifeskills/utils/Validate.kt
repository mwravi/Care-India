package com.careindia.lifeskills.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.util.Patterns
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.careindia.lifeskills.R
import com.careindia.lifeskills.entity.MstLookupEntity
import com.careindia.lifeskills.viewmodel.MstLookupViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import android.view.WindowManager


class Validate(context: Context) {
    val MyPREFERENCES = "CARESP"
    lateinit var sharedpreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context

    init {
        this.context = context
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        editor = sharedpreferences.edit()
    }

    fun SaveSharepreferenceString(key: String, Value: String) {
        editor.putString(key, Value)
        editor.commit()
    }

    fun RetriveSharepreferenceString(key: String): String? {
        return sharedpreferences.getString(key, "")
    }

    fun SaveSharepreferenceInt(key: String, iValue: Int) {
        editor.putInt(key, iValue)
        editor.commit()
    }

    fun RetriveSharepreferenceInt(key: String): Int {
        return sharedpreferences.getInt(key, 0)
    }

    fun ClearSharedPrefrence() {
        editor.clear()
        editor.commit()
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

        if (date1.text.toString().length == 0) {
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
        } else {
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

            if (date2.length < 16) {
                Value = date2 + " 00:00:00"
            } else {
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

    fun fillSpinnerLanguage(
        activity: Activity,
        spin: Spinner,
        Header: String?,
        Data: Array<String?>
    ) {

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


    fun returnLookupCode(
        spin: Spinner, mstLookupViewModel: MstLookupViewModel?,
        flag: Int, languageID: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel!!.getLookup(flag, languageID)
        var pos = spin.getSelectedItemPosition()
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode!!
        }
        return id
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

    fun fillradio(
        activity: Activity,
        Radio: RadioGroup,
        value: Int,
        mstLookupViewModel: MstLookupViewModel?,
        flag: Int,
        iLanguageID: Int
    ) {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel!!.getLookup(flag, iLanguageID)
        Radio.removeAllViews()
        Radio.clearCheck()
        if (!data.isNullOrEmpty()) {
            val rb = arrayOfNulls<RadioButton>(data.size)
            for (i in data.indices) {
                rb[i] = RadioButton(activity)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
                rb[i]!!.setLayoutParams(params)
                Radio.addView(rb[i])
                //                rb[i].setButtonDrawable(R.drawable.radio_check);
                rb[i]!!.setText(data.get(i).Description)
                rb[i]!!.setId(data.get(i).LookupCode.toInt())
                rb[i]!!.setTextColor(activity.resources.getColor(R.color.black))
                rb[i]!!.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
                rb[i]!!.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    activity.resources.getDimension(R.dimen.radio)
                )
                Radio.setPadding(20, 5, 20, 5)
                //                }
                if (value == data.get(i).LookupCode) {
                    rb[i]!!.setChecked(true)
                }
            }
        }
    }

    fun returnID(
        spin: Spinner, mstLookupViewModel: MstLookupViewModel?,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel!!.getLookup(flag, iLanguage)
        var pos = spin.getSelectedItemPosition()
        var id = 0

        if (!data.isNullOrEmpty()) {
            if (pos > 0) id = data.get(pos - 1).LookupCode!!
        }
        return id
    }


    fun returnpos(
        id: Int?, mstLookupViewModel: MstLookupViewModel,
        flag: Int, iLanguage: Int
    ): Int {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel.getLookup(flag, iLanguage)
        var pos = 0
        if (!data.isNullOrEmpty()) {
            if (id!! > 0) {
                for (i in data.indices) {
                    if (id == data.get(i).LookupCode)
                        pos = i + 1
                }
            }
        }
        return pos
    }

    fun DaybetweentimeBefore(date2: String?): Long {
        var Value: String? = ""
        if (date2 != null && !date2.equals("null", ignoreCase = true) && date2.length > 0) {

            if (date2.length < 16) {
                Value = date2 + " 00:00:00"
            } else {
                Value = date2
            }
        }
        val date = Value
        val olddate = "1970-01-01 00:00:00"
        var Seconds: Long = 0

        return if (olddate != null && olddate.length > 0 && date != null && date.length > 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
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


    fun fillradioNew(
        activity: Activity,
        Radio: RadioGroup,
        value: Int,
        mstLookupViewModel: MstLookupViewModel?,
        flag: Int,
        iLanguageID: Int
    ) {
        var data: List<MstLookupEntity>? = null
        data =
            mstLookupViewModel!!.getMstAllData(flag, iLanguageID)
        Radio.removeAllViews()
        Radio.clearCheck()
        if (!data.isNullOrEmpty()) {
            val rb = arrayOfNulls<RadioButton>(data.size)
            for (i in data.indices) {
                rb[i] = RadioButton(activity)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
                rb[i]!!.setLayoutParams(params)
                Radio.addView(rb[i])
                //                rb[i].setButtonDrawable(R.drawable.radio_check);
                rb[i]!!.setText(data.get(i).Description)
                rb[i]!!.setId(data.get(i).LookupCode!!.toInt())
                rb[i]!!.setTextColor(activity.resources.getColor(R.color.black))
                rb[i]!!.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
                rb[i]!!.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    activity.resources.getDimension(R.dimen.radio)
                )
                Radio.setPadding(20, 5, 20, 5)
                //                }
                if (value == data.get(i).LookupCode) {
                    rb[i]!!.setChecked(true)
                }
            }
        }
    }

    fun SetAnswerTypeRadioButton(radioGroup: RadioGroup, data: Int?) {

        for (i in 0 until radioGroup.childCount) {

            val radioButton1 = radioGroup.getChildAt(i) as RadioButton
            if (radioButton1.id == data) {
                radioButton1.isChecked = true
            }
        }
    }


    fun GetAnswerTypeRadioButtonID(radioGroup: RadioGroup): Int {
        var ID = 0

        for (i in 0 until radioGroup.childCount) {

            val radioButton1 = radioGroup.getChildAt(i) as RadioButton
            if (radioButton1.isChecked) {
                ID = radioButton1.id

            }
        }
        return ID
    }

    fun CustomAlertEdit(
        activity: Activity,
        et: EditText,
        msg: String?
    ) { // Create custom dialog object
        val dialog = Dialog(activity)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            // Dismiss the dialog
            dialog.dismiss()
            et.performClick()
            et.requestFocus()
            et.setText("")
        }
        // Display the dialog
        dialog.show()
    }

    @SuppressLint("ServiceCast")
    fun CustomAlertSpinner(
        activity: Activity,
        spin: Spinner,
        msg: String?
    ) { // Create custom dialog object
        val dialog = Dialog(activity)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater = activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            btnok.setTextColor(activity.resources.getColor(R.color.white))
            // Dismiss the dialog
            dialog.dismiss()
            spin.performClick()
            spin.requestFocus()
        }
        // Display the dialog
        dialog.show()
    }

    fun CustomAlert(
        context: Context,
        msg: String?
    ) { // Create custom dialog object
        val dialog = Dialog(context)
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflate the layout dialog_layout.xml and set it as contentView
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog_layout, null, false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.getWindow()?.setAttributes(layoutParams)
        val txtTitle = dialog
            .findViewById<View>(R.id.txt_alert_message) as TextView
        txtTitle.text = msg
        val btnok =
            dialog.findViewById<View>(R.id.btn_ok) as Button
        btnok.setOnClickListener {
            btnok.setTextColor(context.resources.getColor(R.color.white))
            dialog.dismiss()
        }
        // Display the dialog
        dialog.show()
    }

    fun checkmobileno(mobileno: String): Int {

        if (mobileno != null && !mobileno.equals("null") && mobileno.length > 0) {
            var pattern = Pattern.compile("[6-9]{1}[0-9]{9}")
            var matcher = pattern.matcher(mobileno)
            // Check if pattern matches
            if (matcher.matches()) {
                return 1
            } else {
                return 0
            }
        }
        return 0
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


    fun dynamicMultiCheckChange(
        context: Context, liear: LinearLayout, mstCommonViewModel: MstLookupViewModel?,
        flag: Int, iLanguageID: Int, edt: EditText, tbl: LinearLayout
    ) {
        liear as LinearLayout
        var data: List<MstLookupEntity>? = null
        data =
            mstCommonViewModel!!.getMstAllData(flag, iLanguageID)
        if (data != null) {
            val iGen = data.size
            val value = arrayOfNulls<String>(iGen + 1)
            for (i in 0 until data.size) {
                val multicheck1 = CheckBox(context)
                multicheck1.layoutParams =
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                multicheck1.setText(data.get(i).Description?.trim())
                multicheck1.id = data.get(i).LookupCode!!

                if (liear != null) {

                    liear.addView(multicheck1)
                }

                multicheck1.setOnCheckedChangeListener { compoundButton, b ->
                    if (multicheck1.isChecked) {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.VISIBLE
                        }
                    } else {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.GONE
                            edt.setText("")
                        }
                    }
                }

            }

        }

    }

    fun dynamicMultiCheckChange501(
        context: Context,
        liear: LinearLayout,
        mstCommonViewModel: MstLookupViewModel?,
        flag: Int,
        iLanguageID: Int,
        edt: EditText,
        tbl: LinearLayout,
        et_other_q501b: EditText,
        lay_other_q501b: LinearLayout
    ) {
        liear as LinearLayout
        var data: List<MstLookupEntity>? = null
        data =
            mstCommonViewModel!!.getMstAllData(flag, iLanguageID)
        if (data != null) {
            val iGen = data.size
            val value = arrayOfNulls<String>(iGen + 1)
            for (i in 0 until data.size) {
                val multicheck1 = CheckBox(context)
                multicheck1.layoutParams =
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                multicheck1.setText(data.get(i).Description?.trim())
                multicheck1.id = data.get(i).LookupCode!!

                if (liear != null) {

                    liear.addView(multicheck1)
                }

                multicheck1.setOnCheckedChangeListener { compoundButton, b ->
                    if (multicheck1.isChecked) {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.VISIBLE
                        }
                        lay_other_q501b.visibility = View.VISIBLE
                    } else {
                        if (multicheck1.id == 99) {
                            tbl.visibility = View.GONE
                            edt.setText("")
                        }
                    }
                }

            }

        }

    }

    fun returnIntegerValue(myString: String?): Int {
        var iValue = 0
        if (myString != null && !myString.equals(
                "null",
                ignoreCase = true
            ) && myString.length > 0
        ) {
            //   if (CheckNumbers(myString)==true) {
            iValue = Integer.valueOf(myString)

        }
        return iValue

    }

    fun returnStringValue(myString: String?): String {
        var iValue = ""
        if (myString != null && !myString.equals(
                "null",
                ignoreCase = true
            ) && myString.length > 0
        ) {
            iValue = myString
        }
        return iValue

    }

    val currentdatetime: String
        get() {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)

            return sdf.format(Date())
        }

    fun random(): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()
        val sb = StringBuilder()
        val random = Random()
        for (i in 0..29) {
            val c = chars[random.nextInt(chars.size)]
            sb.append(c)
        }


        val dateFormat = SimpleDateFormat("yyyyMMddHHmmssSS")
        val date = Date()
        val SDateString = dateFormat.format(date)
        return sb.toString() + SDateString
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }


    fun CustomToast(activity: Activity?, sMsg: String?) {
        Toast.makeText(activity, sMsg, Toast.LENGTH_LONG).show()
    }
}