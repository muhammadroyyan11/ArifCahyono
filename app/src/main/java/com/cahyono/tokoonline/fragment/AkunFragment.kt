package com.cahyono.tokoonline.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cahyono.tokoonline.MainActivity
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.activity.LoginActivity
import com.cahyono.tokoonline.helper.SharedPref

/**
 * A simple [Fragment] subclass.
 */
class AkunFragment : Fragment() {

    lateinit var s:SharedPref
    lateinit var btnLogout:TextView
    lateinit var tvNama:TextView
    lateinit var tvPhone:TextView
    lateinit var tvEmail:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)

        btnLogout = view.findViewById<TextView>(R.id.btn_logout)
        tvNama = view.findViewById<TextView>(R.id.tv_nama)
        tvEmail = view.findViewById<TextView>(R.id.tv_email)
        tvPhone = view.findViewById<TextView>(R.id.tv_phone)

        s = SharedPref(requireActivity())

        btnLogout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                s.setStatusLogin(false)

                activity?.let{
                    val intent = Intent (it, MainActivity::class.java)
                    it.startActivity(intent)
                }
            }
        })

        setData()
        return view
    }

    fun setData(){
        tvNama.text = s.getString(s.nama)
        tvEmail.text = s.getString(s.email)
        tvPhone.text = s.getString(s.phone)
    }

//    private fun init(view: View) {
//        btnLogout = view.findViewById<TextView>(R.id.btn_logout)
//        tvNama = view.findViewById<TextView>(R.id.tv_nama)
//        tvEmail = view.findViewById<TextView>(R.id.tv_email)
//        tvPhone = view.findViewById<TextView>(R.id.tv_phone)
//
//    }
}