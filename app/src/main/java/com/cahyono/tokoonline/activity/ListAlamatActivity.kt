package com.cahyono.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cahyono.tokoonline.R

class ListAlamatActivity : AppCompatActivity() {
    lateinit var btnTambah: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)
    }

    fun init(){
        btnTambah = findViewById(R.id.btn_tambahAlamat)
    }

    fun main(){
        btnTambah.setOnClickListener {
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }
}