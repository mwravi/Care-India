package com.careindia.lifeskills.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.careindia.lifeskills.R
import kotlinx.android.synthetic.main.toolbar_layout.*

class CollectiveProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collective_profile)

        tv_title.text = "Collective Profile"


    }
}