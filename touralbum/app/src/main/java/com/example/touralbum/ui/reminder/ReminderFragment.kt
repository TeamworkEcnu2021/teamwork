package com.example.touralbum.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.touralbum.R

class ReminderFragment : Fragment() {

    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reminderViewModel =
            ViewModelProvider(this).get(ReminderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reminder, container, false)
        val textView: TextView = root.findViewById(R.id.text_account)
        reminderViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}