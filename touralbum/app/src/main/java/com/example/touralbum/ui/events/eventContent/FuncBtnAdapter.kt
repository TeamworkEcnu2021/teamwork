package com.example.touralbum.ui.events.eventContent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.touralbum.R

class FuncBtnAdapter(var context: Context,val FuncBtnList: List<FuncBtn>) : BaseAdapter() {

    inner class ViewHolder() {
        lateinit var FuncBtnImage: ImageView
        lateinit var FuncBtnName: TextView
        lateinit var cLayout: ConstraintLayout
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?=null
        var viewHolder: ViewHolder? = null

        if(convertView == null){
            viewHolder = ViewHolder()
            view = LayoutInflater.from(context).inflate(R.layout.item_func_btn,null)
            viewHolder.FuncBtnImage = view.findViewById(R.id.btn_image)
            viewHolder.FuncBtnName = view.findViewById(R.id.btn_name)
            viewHolder.cLayout = view.findViewById(R.id.func_btn_item)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.FuncBtnName.text = FuncBtnList[position].btn_name
        viewHolder.FuncBtnImage.setImageResource(FuncBtnList[position].btn_image)

        return view!!
    }

    override fun getItem(position: Int): Any {
        //获取指定位置(position)上的item对象，通常不需要修改
        return FuncBtnList.get(position)
    }

    override fun getItemId(position: Int): Long {
        // 获取指定位置(position)上的item的id，通常不需要修改
        return position.toLong()
    }

    override fun getCount(): Int {
        //返回一个整数,就是要在listview中现实的数据的条数
        return FuncBtnList.size
    }
}