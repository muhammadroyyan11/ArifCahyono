package com.cahyono.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.helper.Helper
import com.cahyono.tokoonline.model.Produk
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetailProdukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        getInfo()
    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")

        val produk = Gson().fromJson<Produk>(data, Produk::class.java)

        //Set Value
        var tvNama = findViewById<TextView>(R.id.tv_nama)
        var tvHarga = findViewById<TextView>(R.id.tv_harga)
        var tvDesc = findViewById<TextView>(R.id.tv_deskripsi)
        var image = findViewById<ImageView>(R.id.image)

        var tlbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        tvNama.text = produk.name
        tvHarga.text = Helper().gantiRupiah(produk.harga)
        tvDesc.text = produk.deskripsi

        val img = "http://192.168.100.82/webApi/uploads/produk/"+produk.image

        Picasso.get()
            .load(img)
            .placeholder(R.drawable.loading)
            .error(R.drawable.loading)
            .into(image)

        // Set toolbarsindar@mail.com
        setSupportActionBar(tlbar)
        supportActionBar!!.title = produk.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}