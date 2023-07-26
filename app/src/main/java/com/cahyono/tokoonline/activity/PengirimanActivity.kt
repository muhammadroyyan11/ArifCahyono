
package com.cahyono.tokoonline.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.adapter.AdapterKurir
import com.cahyono.tokoonline.app.ApiConfigAlamat
import com.cahyono.tokoonline.helper.Helper
import com.cahyono.tokoonline.model.rajaongkir.Costs
import com.cahyono.tokoonline.model.rajaongkir.ResponOngkir
import com.cahyono.tokoonline.room.MyConnection
import com.cahyono.tokoonline.room.MyDatabase
import com.cahyono.tokoonline.util.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanActivity : AppCompatActivity() {

    lateinit var myDb: MyConnection
    lateinit var myDbTwo: MyDatabase
    lateinit var btnTambahAlamat: Button
    lateinit var spn_kurir: Spinner
    lateinit var rv_metode: RecyclerView
    lateinit var tv_ongkir: TextView
    lateinit var tv_total: TextView
    lateinit var div_alamat: CardView
    lateinit var div_kosong: TextView
    lateinit var div_metodePengiriman: LinearLayout
    lateinit var tv_nama: TextView
    lateinit var tv_phone: TextView
    lateinit var tv_alamat: TextView
    lateinit var tv_totalBelanja: TextView
    lateinit var tv_total_belanja: TextView

    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
//        Helper().setToolbar(this, toolbar, "Pengiriman")
        myDb = MyConnection.getInstance(this)!!
        myDbTwo = MyDatabase.getInstance(this)!!
        init()


        val listProduk = myDbTwo.daoName().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
//        val produks = ArrayList<Chekout.Item>()
        for (p in listProduk) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))

                tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
//                val produk = Chekout.Item()
//                produk.id = "" + p.id
//                produk.total_item = "" + p.jumlah
//                produk.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
//                produk.catatan = "catatan baru"
//                produks.add(produk)
            }
        }
        main()
        setSepiner()
    }

    fun setSepiner() {
        val arryString = ArrayList<String>()
        arryString.add("JNE")
        arryString.add("POS")
        arryString.add("TIKI")

        val adapter = ArrayAdapter<Any>(this, R.layout.item_spinner, arryString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_kurir.adapter = adapter
        spn_kurir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    getOngkir(spn_kurir.selectedItem.toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun chekAlamat() {

        if (myDb.daoAlamat().getByStatus(true) != null) {
            div_alamat.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE
            div_metodePengiriman.visibility = View.VISIBLE

            val a = myDb.daoAlamat().getByStatus(true)!!
            tv_nama.text = a.name
            tv_phone.text = a.phone
            tv_alamat.text = a.alamat + ", " + a.kota + ", " + a.kodepos + ", (" + a.type + ")"
            btnTambahAlamat.text = "Ubah Alamat"

            getOngkir("JNE")

//            tv_total_belanja = (totalHarga + Integer.valueOf(ongkir)
        } else {
            div_alamat.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btnTambahAlamat.text = "Tambah Alamat"
        }
    }

    private fun getOngkir(kurir: String) {

        val alamat = myDb.daoAlamat().getByStatus(true)

        val origin = "255"
        val destination = "" + alamat!!.id_kota.toString()
        val berat = 1000

        ApiConfigAlamat.instanceRetrofit.ongkir(ApiKey.key, origin, destination, berat, kurir.toLowerCase()).enqueue(object :
            Callback<ResponOngkir> {
            override fun onResponse(call: Call<ResponOngkir>, response: Response<ResponOngkir>) {
                if (response.isSuccessful) {

                    Log.d("Success", "berhasil memuat data")
                    val result = response.body()!!.rajaongkir.results
                    if (result.isNotEmpty()) {
                        displayOngkir(result[0].code.toUpperCase(), result[0].costs)
                    }


                } else {
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }

            override fun onFailure(call: Call<ResponOngkir>, t: Throwable) {
                Log.d("Error", "gagal memuat data:" + t.message)
            }

        })
    }

    var ongkir = "0"
    var kurir = ""
    var jasaKirim = ""
    private fun displayOngkir(_kurir: String, arrayList: ArrayList<Costs>) {

        var arrayOngkir = ArrayList<Costs>()
        for (i in arrayList.indices) {
            val ongkir = arrayList[i]
            if (i == 0) {
                ongkir.isActive = true
            }
            arrayOngkir.add(ongkir)
        }
        setTotal(arrayOngkir[0].cost[0].value)
        ongkir = arrayOngkir[0].cost[0].value
        kurir = _kurir
        jasaKirim = arrayOngkir[0].service

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        var adapter: AdapterKurir? = null
        adapter = AdapterKurir(arrayOngkir, _kurir, object : AdapterKurir.Listeners {
            override fun onClicked(data: Costs, index: Int) {
                val newArrayOngkir = ArrayList<Costs>()
                for (ongkir in arrayOngkir) {
                    ongkir.isActive = data.description == ongkir.description
                    newArrayOngkir.add(ongkir)
                }
                arrayOngkir = newArrayOngkir
                adapter!!.notifyDataSetChanged()
                setTotal(data.cost[0].value)

                ongkir = data.cost[0].value
                kurir = _kurir
                jasaKirim = data.service
            }
        })
        rv_metode.adapter = adapter
        rv_metode.layoutManager = layoutManager
    }

    fun setTotal(ongkir: String) {
        val listProduk = myDbTwo.daoName().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
//        val produks = ArrayList<Chekout.Item>()
        for (p in listProduk) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))

                tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
//                val produk = Chekout.Item()
//                produk.id = "" + p.id
//                produk.total_item = "" + p.jumlah
//                produk.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
//                produk.catatan = "catatan baru"
//                produks.add(produk)
                tv_ongkir.text = Helper().gantiRupiah(ongkir)
                tv_total.text = Helper().gantiRupiah(Integer.valueOf(ongkir) + totalHarga)
            }
        }
    }

    fun init(){
        btnTambahAlamat = findViewById(R.id.btn_tambahAlamatPengiriman)
        spn_kurir = findViewById(R.id.spn_kurir)
        rv_metode = findViewById(R.id.rv_metode)
        tv_ongkir = findViewById(R.id.tv_ongkir)
        tv_total = findViewById(R.id.tv_total_belanja)

        div_alamat = findViewById(R.id.div_alamat)
        div_kosong = findViewById(R.id.div_kosong)
        div_metodePengiriman = findViewById(R.id.div_metodePengiriman)

        tv_nama = findViewById(R.id.tv_nama_alamat)
        tv_phone = findViewById(R.id.tv_phone_alamat)
        tv_alamat = findViewById(R.id.tv_alamat_alamat)
        tv_totalBelanja = findViewById(R.id.tv_totalBelanja)
        tv_total_belanja = findViewById(R.id.tv_total_belanja)

//        btn_tambahAlamat = findViewById(R.id.btn_tambahAlamatPengiriman)
    }

    fun main(){
        btnTambahAlamat.setOnClickListener {
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        chekAlamat()
        super.onResume()
    }
}