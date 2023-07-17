package com.cahyono.tokoonline.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.cahyono.tokoonline.R
import com.cahyono.tokoonline.adapter.AdapterSlider

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var tvContent: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_about, container, false)

        init(view)

        var arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.sampul)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        tvContent.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")

        return view
    }

    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider_about)
        tvContent = view.findViewById(R.id.content_about)
    }
}