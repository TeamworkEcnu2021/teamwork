package com.example.touralbum.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
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

        //取数据、事件列表
        initEvent()
        val layoutManager = GridLayoutManager(requireContext(),1)
        val recyclerView : RecyclerView = requireActivity().findViewById(R.id.eventEntryList)
        recyclerView.layoutManager = layoutManager
        eventEntryAdapter = EventEntryAdapter(eventEntryList)
        recyclerView.adapter = eventEntryAdapter

        return root
    }

    private fun initEvent(){
        val prefs = activity?.getSharedPreferences("events",Context.MODE_PRIVATE)
        val count = prefs!!.getInt("eventCount",0)
        var i = 1
        while(i<=count){
            val name = prefs.getString("$i","")
            lateinit var event: Event
            event.getData(requireContext(),name!!)
            eventEntryList.add(event)
            i++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add("addEvent").setShowAsAction(SHOW_AS_ACTION_ALWAYS)
        menu.add("reminder").setShowAsAction(SHOW_AS_ACTION_ALWAYS)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_event -> {
                val intent = Intent(activity, CreateEvent::class.java)
                startActivityForResult(intent,0)
            }
            R.id.reminder -> {
                val intent = Intent(activity, ReminderActivity::class.java)
                startActivityForResult(intent,2)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}