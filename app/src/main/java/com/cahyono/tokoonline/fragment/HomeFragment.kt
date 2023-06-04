package com.cahyono.tokoonline.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.adapter.AdapterProduk
import com.cahyono.tokoonline.adapter.AdapterSlider
import com.cahyono.tokoonline.model.Produk

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvTerlaris: RecyclerView

    private lateinit var produkAdapter: AdapterProduk
    private lateinit var produkList: ArrayList<Produk>
    private lateinit var terlarisAdapter: AdapterProduk
    private lateinit var terlarisList: ArrayList<Produk>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvTerlaris = view.findViewById(R.id.rv_terlaris)

        var arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider


        rvProduk.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        rvProduk.setHasFixedSize(true)

        rvTerlaris.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        rvTerlaris.setHasFixedSize(true)

        produkList = ArrayList()
        terlarisList = ArrayList()

        arrProduk()
        arrTerlarsis()

        produkAdapter = AdapterProduk(produkList)
        rvProduk.adapter = produkAdapter

        terlarisAdapter = AdapterProduk(terlarisList)
        rvTerlaris.adapter = terlarisAdapter

        return view
    }

    private fun arrProduk(){
        produkList.add(Produk("Tanaman Murah", "Rp.100.000", R.drawable.produk1))
        produkList.add(Produk("Tanaman Menengah", "Rp.500.000", R.drawable.produk2))
        produkList.add(Produk("Tanaman Ter Mahal", "Rp.1.000.000", R.drawable.produk3))
    }

    private fun arrTerlarsis(){
        terlarisList.add(Produk("Tanaman Murah", "Rp.100.000", R.drawable.produk1))
        terlarisList.add(Produk("Tanaman Menengah", "Rp.500.000", R.drawable.produk2))
        terlarisList.add(Produk("Tanaman Mahal", "Rp.1.000.000", R.drawable.produk4))
    }


}