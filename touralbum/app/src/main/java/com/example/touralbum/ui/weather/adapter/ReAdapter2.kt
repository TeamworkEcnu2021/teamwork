package com.example.touralbum.ui.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.weather.Bean.WeatherBean.ResultBean.HeWeather5Bean.DailyForecastBean
import com.example.touralbum.ui.weather.adapter.ReAdapter2.ViewHoloder

class ReAdapter2(private val dayList: List<DailyForecastBean>) :
    RecyclerView.Adapter<ViewHoloder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoloder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list2, parent, false)
        return ViewHoloder(view)
    }

    override fun onBindViewHolder(holder: ViewHoloder, position: Int) {
        val dailyForecastBean = dayList[position]
        holder.re2_tv1.text = dailyForecastBean.date!!.substring(5) + ""
        holder.re2_tv2.text = dailyForecastBean.cond!!.txt_d
        holder.re2_tv3.text = dailyForecastBean.tmp!!.max + "℃"
        holder.re2_tv4.text = dailyForecastBean.tmp!!.min + "℃"
        if (dailyForecastBean.cond!!.txt_d == "多云" || dailyForecastBean.cond!!.txt_d == "阴") {
            holder.re2_img.setImageResource(R.mipmap.yin)
        } else if (dailyForecastBean.cond!!.txt_d == "晴") {
            holder.re2_img.setImageResource(R.mipmap.qinglang)
        } else if (dailyForecastBean.cond!!.txt_d == "雨夹雪" || dailyForecastBean.cond!!.txt_d == "小雪") {
            holder.re2_img.setImageResource(R.mipmap.xiaoxue)
        } else if (dailyForecastBean.cond!!.txt_d == "小雨") {
            holder.re2_img.setImageResource(R.mipmap.xiaoyu)
        } else if (dailyForecastBean.cond!!.txt_d == "中雨" || dailyForecastBean.cond!!.txt_d == "大雨") {
            holder.re2_img.setImageResource(R.mipmap.dayu)
        } else if (dailyForecastBean.cond!!.txt_d == "雷阵雨") {
            holder.re2_img.setImageResource(R.mipmap.leizhenyu)
        } else if (dailyForecastBean.cond!!.txt_d == "雾") {
            holder.re2_img.setImageResource(R.mipmap.wu)
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    inner class ViewHoloder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var re2_tv1: TextView
        var re2_tv2: TextView
        var re2_tv3: TextView
        var re2_tv4: TextView
        var re2_img: ImageView

        init {
            re2_tv1 = itemView.findViewById(R.id.re2_tv1)
            re2_tv2 = itemView.findViewById(R.id.re2_tv2)
            re2_tv3 = itemView.findViewById(R.id.re2_tv3)
            re2_tv4 = itemView.findViewById(R.id.re2_tv4)
            re2_img = itemView.findViewById(R.id.re2_img_zhuangtai)
        }
    }
}