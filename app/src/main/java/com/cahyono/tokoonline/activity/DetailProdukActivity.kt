package com.cahyono.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.cahyono.tokoonline.R

class DetailProdukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        getInfo()
    }

    fun getInfo(){
        val nama = intent.getStringExtra("nama")
        val harga = intent.getStringExtra("harga")
        val desc = intent.getStringExtra("desc")

        //Set Value
        var tvNama = findViewById<TextView>(R.id.tv_nama)
        var tvHarga = findViewById<TextView>(R.id.tv_harga)
        var tvDesc = findViewById<TextView>(R.id.tv_deskripsi)

        tvNama.text = nama
        tvHarga.text = harga
        tvDesc.text = desc
    }
}