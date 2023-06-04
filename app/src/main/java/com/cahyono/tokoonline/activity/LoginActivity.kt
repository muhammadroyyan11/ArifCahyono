package com.cahyono.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.helper.SharedPref

class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        val button = findViewById<Button>(R.id.btn_prosesLogin)
        button.setOnClickListener {
            //cdoe that executes when user taps on the button
            s.setStatusLogin(true)
        }
    }
}