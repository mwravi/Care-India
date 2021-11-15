package com.Careindia.lifeskills.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Careindia.lifeskills.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class SignupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        tv_title.text = "SignUp"

        btn_signup.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
}