package com.Careindia.lifeskills.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Careindia.lifeskills.R
import com.Careindia.lifeskills.utils.Validate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {
    var validate: Validate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        validate = Validate(this)

        validate!!.dynamicRadio(this, checkRadio, resources.getStringArray(R.array.yes_no))
//        validate!!.fillCheckBoxes(this, multiCheck, resources.getStringArray(R.array.yes_no))

        tv_title.text = "Main"

        /*et_formfilngDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngDate
            )

        }*/
        et_formfilngjgDate.setOnClickListener {
            validate!!.datePickerwithmindate(
                validate!!.Daybetweentime("01-01-1990"),
                et_formfilngjgDate
            )

        }
        validate!!.fillSpinnerLanguage(
            this,
            spin_language,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.yes_no)
        )
        validate!!.fillSpinnerLanguage(
            this,
            spin_SupervisingFC,
            resources.getString(R.string.select),
            resources.getStringArray(R.array.spin_SupervisingFC)
        )

    }

}