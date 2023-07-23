package com.cahyono.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.helper.Helper

class PengirimanActivity : AppCompatActivity() {
    lateinit var btnTambahAlamat: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
//        Helper().setToolbar(this, toolbar, "Pengiriman")

        init()
        main()
    }

    fun init(){
        btnTambahAlamat = findViewById(R.id.btn_tambahAlamatPengiriman)
    }

    fun main(){
        btnTambahAlamat.setOnClickListener {
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
//        chekAlamat()
        super.onResume()
    }
}