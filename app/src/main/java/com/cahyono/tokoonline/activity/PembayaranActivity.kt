package com.cahyono.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.adapter.AdapterBank
import com.cahyono.tokoonline.model.Bank

class PembayaranActivity : AppCompatActivity() {
    lateinit var rv_data: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        init()
        displayBank()
        getInfo()
    }

    fun displayBank() {
        val arrBank = ArrayList<Bank>()
        arrBank.add(Bank("BCA", "092093871237", "Arif Cahyono", R.drawable.logo_bca))
        arrBank.add(Bank("BRI", "86721349128", "Arif Cahyono", R.drawable.logo_bri))
        arrBank.add(Bank("Mandiri", "02394870329", "Arif Cahyono", R.drawable.logo_madiri))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_data.layoutManager = layoutManager
        rv_data.adapter = AdapterBank(arrBank, object : AdapterBank.Listeners {
            override fun onClicked(data: Bank, index: Int) {
//                bayar(data)
                println("halo")
            }
        })
    }

    fun getInfo(){
        var tlbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        // Set toolbarsindar@mail.com
        setSupportActionBar(tlbar)
        supportActionBar!!.title = "Metode Pembayaran"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun init(){
        rv_data = findViewById(R.id.rv_data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}