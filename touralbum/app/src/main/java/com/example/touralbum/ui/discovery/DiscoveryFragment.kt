package com.example.touralbum.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.touralbum.R

class DiscoveryFragment : Fragment() {

    private lateinit var discoveryModel: DiscoveryModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        discoveryModel =
                ViewModelProvider(this).get(DiscoveryModel::class.java)
        val root = inflater.inflate(R.layout.fragment_discovery, container, false)
        val textView: TextView = root.findViewById(R.id.text_discovery)
        discoveryModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}