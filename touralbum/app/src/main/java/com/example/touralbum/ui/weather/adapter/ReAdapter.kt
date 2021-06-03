package com.example.touralbum.ui.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.weather.Bean.WeatherBean.ResultBean.HeWeather5Bean.HourlyForecastBean

class ReAdapter(private val mlist: List<HourlyForecastBean>) :
    RecyclerView.Adapter<ReAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyForecastBean = mlist[position]
        holder.re_tv1.text = hourlyForecastBean.date!!.substring(11) + ""
        holder.re_tv2.text = hourlyForecastBean.cond!!.txt + ""
        holder.re_tv3.text = hourlyForecastBean.tmp + "℃"
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var re_tv1: TextView
        var re_tv2: TextView
        var re_tv3: TextView

        init {
            re_tv1 = itemView.findViewById(R.id.re_tv1)
            re_tv2 = itemView.findViewById(R.id.re_tv2)
            re_tv3 = itemView.findViewById(R.id.re_tv3)
        }
    }
}