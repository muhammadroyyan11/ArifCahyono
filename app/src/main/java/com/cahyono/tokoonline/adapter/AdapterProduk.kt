package com.cahyono.tokoonline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.model.Produk
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterProduk(var data: ArrayList<Produk>):RecyclerView.Adapter<AdapterProduk.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
//        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
//        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)

        val tvNama: TextView = itemView.findViewById(R.id.tv_nama)
        val tvHarga: TextView = itemView.findViewById(R.id.tv_harga)
        val imgProduk: ImageView = itemView.findViewById(R.id.img_produk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = data[position]

        holder.tvNama.text = produk.name
        holder.tvHarga.text = NumberFormat.getCurrencyInstance(Locale("in","ID")).format(Integer.valueOf(produk.harga))
//        holder.imgProduk.setImageResource(produk.image)

        val image = "http://192.168.252.112/TokoOnlineWebMaster/storage/app/public/produk/"+data[position].image

        Picasso.get()
            .load(image)
            .placeholder(R.drawable.loading)
            .error(R.drawable.loading)
            .into(holder.imgProduk)
    }
}