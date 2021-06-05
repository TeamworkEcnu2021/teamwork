package com.example.touralbum.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.reminder.activity.ReminderActivity


class EventsFragment : Fragment() {

    private lateinit var eventEntryAdapter: EventEntryAdapter
    private val eventEntryList = ArrayList<Event>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_event_list, container, false)
        Log.d("d","出行事件fragment")

        val toolbar = root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_event_list)
        toolbar.inflateMenu(R.menu.event_list_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.add_event -> {
                    val intent = Intent(activity, CreateEvent::class.java)
                    startActivityForResult(intent,0)
                }
                R.id.reminder -> {
                    val intent = Intent(activity, ReminderActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        Log.d("d","设置toolbar中的添加事件和行程提醒按钮")

        //取数据、事件列表
        initEvent()
        val layoutManager = GridLayoutManager(requireContext(),1)
        val recyclerView : RecyclerView = root.findViewById(R.id.eventEntryList)
        recyclerView.layoutManager = layoutManager
        eventEntryAdapter = EventEntryAdapter(eventEntryList)
        recyclerView.adapter = eventEntryAdapter
        Log.d("d","设置数据适配器，加载事件列表")

        return root
    }

    override fun onResume() {
        eventEntryList.clear()
        initEvent()
        eventEntryAdapter.notifyDataSetChanged()
        super.onResume()
    }

    private fun initEvent(){
        val prefs = activity?.getSharedPreferences("events",Context.MODE_PRIVATE)
        val count = prefs!!.getInt("eventCount",0)
        var i = 1
        while(i<=count){
            val name = prefs.getString("$i","")
            val event = Event("","","","")
            event.getData(requireContext(),name!!)
            eventEntryList.add(event)
            i++
        }
        Log.d("d","打开名为events的shared preference读取事件总数，循环读取事件名并取数据，组装list")
    }
}