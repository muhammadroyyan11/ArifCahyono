package com.cahyono.tokoonline.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.activity.MasukActivity
import com.cahyono.tokoonline.activity.PengirimanActivity
import com.cahyono.tokoonline.adapter.AdapterKeranjang
import com.cahyono.tokoonline.adapter.AdapterProduk
import com.cahyono.tokoonline.room.MyDatabase
import com.cahyono.tokoonline.helper.Helper
import com.cahyono.tokoonline.helper.SharedPref
import com.cahyono.tokoonline.model.Produk
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class KeranjangFragment : Fragment() {

    lateinit var rvTerlaris: RecyclerView
    private lateinit var produkAdapter: AdapterKeranjang
    lateinit var s: SharedPref

    lateinit var myDb: MyDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDb = MyDatabase.getInstance(requireActivity())!!
        s = SharedPref(requireActivity())
        mainButton()
        displayProduk()

        return view
    }

    var totalHarga = 0
//    lateinit var cbAll: CheckBox
    fun hitungTotal() {
        val listProduk = myDb.daoName().getAll() as ArrayList
        totalHarga = 0
//        var isSelectedAll = true
        for (produk in listProduk) {
            if (produk.selected) {
                val harga = Integer.valueOf(produk.harga)
                totalHarga += (harga * produk.jumlah)
            }
        }

//        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun displayProduk(){
        val myDb = MyDatabase.getInstance(requireActivity())
        val listProduk = myDb!!.daoName().getAll() as ArrayList

        rvProduk.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvProduk.setHasFixedSize(true)

//        produkAdapter = ArrayList()

//        arrTerlarsis()

        produkAdapter = AdapterKeranjang(requireActivity(), listProduk, object : AdapterKeranjang.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                produkAdapter.notifyDataSetChanged()
                hitungTotal()
            }
        })
        rvProduk.adapter = produkAdapter

    }


    var listProduk = ArrayList<Produk>()
    lateinit var adapter: AdapterKeranjang
    private fun mainButton(){

        btnBayar.setOnClickListener {
            startActivity(Intent(requireActivity(), PengirimanActivity::class.java))
            if (s.getStatusLogin()) {
                var isThereProduk = false
                for (p in listProduk) {
                    if (p.selected){
                        val intent = Intent(requireActivity(), PengirimanActivity::class.java)
                        intent.putExtra("extra", "" + totalHarga)
                        startActivity(intent)
                    }
                }

//                if (isThereProduk) {
//                    val intent = Intent(requireActivity(), PengirimanActivity::class.java)
//                    intent.putExtra("extra", "" + totalHarga)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(requireContext(), "Tidak ada produk yg terpilih", Toast.LENGTH_SHORT).show()
//                }

            } else {
                requireActivity().startActivity(Intent(requireActivity(), MasukActivity::class.java))
            }
//            val listProduk = myDb.daoName().getAll() // get All data
//            for(produk :Produk in listProduk){
//                println("-----------------------")
//                println(produk.name)
//                println(produk.selected)
//            }
        }

//        cbAll.setOnClickListener {
//            for (i in listProduk.indices) {
//                val produk = listProduk[i]
//                produk.selected = cbAll.isChecked
//                listProduk[i] = produk
//            }
//            adapter.notifyDataSetChanged()
//        }
    }

    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView

    private fun init(view: View){
//        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_keranjang)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
//        cbAll = view.findViewById(R.id.cb_all)

//        rv_produk = view.findViewById(R.id.rv_produk)
    }

    override fun onResume(){
        displayProduk()
        hitungTotal()
        super.onResume()
    }

    override fun onPause(){
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}