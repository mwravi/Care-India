package com.Careindia.lifeskills.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.Careindia.lifeskills.R
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        btn_login.setOnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }


    }
}