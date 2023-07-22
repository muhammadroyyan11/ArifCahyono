package com.cahyono.tokoonline.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.adapter.AdapterProduk
import com.cahyono.tokoonline.room.MyDatabase

/**
 * A simple [Fragment] subclass.
 */
class KeranjangFragment : Fragment() {

    lateinit var rvTerlaris: RecyclerView
    private lateinit var produkAdapter: AdapterProduk
    private lateinit var terlarisAdapter: AdapterProduk

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)

        mainButton()
        displayProduk()

        return view
    }

    private fun displayProduk(){
        val myDb = MyDatabase.getInstance(requireActivity())
        val listProduk = myDb!!.daoName().getAll() as ArrayList

        rvProduk.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvProduk.setHasFixedSize(true)

//        produkAdapter = ArrayList()

//        arrTerlarsis()

        produkAdapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.adapter = produkAdapter

    }

    private fun mainButton(){
        btnDelete.setOnClickListener {

        }

        btnBayar.setOnClickListener {

        }
    }

    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView

    private fun init(view: View){
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)

//        rv_produk = view.findViewById(R.id.rv_produk)
    }

    override fun onResume(){
        displayProduk()
        super.onResume()
    }

    override fun onPause(){
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}