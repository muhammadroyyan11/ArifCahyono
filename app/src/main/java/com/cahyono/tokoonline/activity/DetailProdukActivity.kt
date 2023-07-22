package com.cahyono.tokoonline.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.room.Room
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.data.MyConnection
import com.cahyono.tokoonline.data.Name
import com.cahyono.tokoonline.helper.Helper
import com.cahyono.tokoonline.model.Produk
import com.cahyono.tokoonline.room.MyDatabase
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailProdukActivity : AppCompatActivity() {

    lateinit var myDb : MyDatabase
    lateinit var produk: Produk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        myDb = MyDatabase.getInstance(this)!!

        val btn_keranjang = findViewById<RelativeLayout>(R.id.btn_keranjang)
        val btn_favorit = findViewById<RelativeLayout>(R.id.btn_favorit)

//        initDatabase()
        getInfo()
        checkKeranjang()


        btn_favorit.setOnClickListener {
//            loadData()// call database
            val listProduk = myDb.daoName().getAll() // get All data
            for(produk :Produk in listProduk){
                println("-----------------------")
                println(produk.name)
                println(produk.harga)
            }

        }

        btn_keranjang.setOnClickListener {
            saveName()
//            Log.d("Response","Berhasil di input")
        }
    }

//    private fun loadData(){
//        GlobalScope.launch {
//            val names = connect.daoName().getAll()
//
//            runOnUiThread{
//                for(name :Produk in names){
//                    println("-----------------------")
//                    println(name.name)
//                }
//            }
//        }
//    }

    private fun saveName() {
//        val name = Name(Name, Belakang)
//
//        name.name = "First Note"
//        name.belakang = "9000"
//        val data = Name(nameFirst, belakang)


//        GlobalScope.launch {
//            connect.NameDao().insertAll(Name(nameFirst, lastName))

        val note = Produk() //create new note
        note.name = produk.name
        note.harga = produk.harga

        CompositeDisposable().add(Observable.fromCallable { myDb.daoName().insertAll(note) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
            })
    }

    private fun checkKeranjang(){
        val dataKeranjang = myDb.daoName().getAll()
        var div_angka = findViewById<RelativeLayout>(R.id.div_angka)
        var tvAngka = findViewById<TextView>(R.id.tv_angka)


        if(dataKeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tvAngka.text = dataKeranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }


    fun getInfo(){
        val data = intent.getStringExtra("extra")

        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        //Set Value
        var tvNama = findViewById<TextView>(R.id.tv_nama)
        var tvHarga = findViewById<TextView>(R.id.tv_harga)
        var tvDesc = findViewById<TextView>(R.id.tv_deskripsi)
        var image = findViewById<ImageView>(R.id.image)

        var tlbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        tvNama.text = produk.name
        tvHarga.text = Helper().gantiRupiah(produk.harga)
        tvDesc.text = produk.deskripsi

        val img = "http://api.readytowork.site/uploads/produk/"+produk.image

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