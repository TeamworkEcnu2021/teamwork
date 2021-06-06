package com.example.touralbum.ui.weather

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.touralbum.R
import com.example.touralbum.ui.weather.Bean.WeatherBean
import com.example.touralbum.ui.weather.adapter.ReAdapter
import com.example.touralbum.ui.weather.adapter.ReAdapter2
import com.google.gson.Gson
import org.json.JSONObject


class WeatherFragment : Fragment() {

    private lateinit var tv_zhunagtai: TextView
    var cityName = "上海"
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var tv1: TextView
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView
    private lateinit var tv4: TextView
    private lateinit var tv5: TextView
    private lateinit var tv6: TextView
    private lateinit var tv11: TextView
    private lateinit var tv22: TextView
    private lateinit var tv33: TextView
    private lateinit var tv44: TextView
    private lateinit var tv55: TextView
    private lateinit var tv66: TextView
    private lateinit var sheshidu: TextView
    private lateinit var fengxiang: TextView
    private lateinit var img_zhungkuang: ImageView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var weatherBean: WeatherBean
    private lateinit var tv_cityName: TextView
    private lateinit var editText: EditText
    private lateinit var btn_chaxun: Button
    private lateinit var qiehuan: TextView
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_weather, container, false)

        //import fonts
        val test1 = root.findViewById<View>(R.id.foot_tv1) as TextView
        val typeface1 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test1.typeface = typeface1
        val test2 = root.findViewById<View>(R.id.foot_tv2) as TextView
        val typeface2 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test2.typeface = typeface2
        val test3 = root.findViewById<View>(R.id.foot_tv3) as TextView
        val typeface3 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test3.typeface = typeface3
        val test4 = root.findViewById<View>(R.id.foot_tv4) as TextView
        val typeface4 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test4.typeface = typeface4
        val test5 = root.findViewById<View>(R.id.foot_tv5) as TextView
        val typeface5 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test5.typeface = typeface5
        val test6 = root.findViewById<View>(R.id.foot_tv6) as TextView
        val typeface6 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test6.typeface = typeface6
        tv_zhunagtai = root.findViewById(R.id.tv_zhuangtai)
        sheshidu = root.findViewById(R.id.tv_sheshidu)
        fengxiang = root.findViewById(R.id.tv_fengxiang)
        img_zhungkuang = root.findViewById(R.id.img_now_zhuangkuang)
        recyclerView = root.findViewById(R.id.ry_recycleView)
        recyclerView2 = root.findViewById(R.id.ry_recycleView2)
        refreshLayout = root.findViewById(R.id.srl_swipe)
        tv_cityName = root.findViewById(R.id.tv_cityName)
        qiehuan = root.findViewById(R.id.tv_qiehuan)
        tv_cityName.setOnClickListener {
            val myDialog = MyDialog(requireActivity())
            myDialog.show()
        }

        qiehuan.setOnClickListener {
            val myDialog = MyDialog(requireActivity())
            myDialog.show()
        }

        //下拉刷新
        refreshLayout.setOnRefreshListener {
            Handler().postDelayed(
                { selectWeather(tv_cityName.text.toString()) }, 3000
            )
        }
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager
        val linearLayoutManager1 = LinearLayoutManager(requireActivity())
        linearLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView2.layoutManager = linearLayoutManager1
        tv1 = root.findViewById(R.id.foot_tv1)
        tv2 = root.findViewById(R.id.foot_tv2)
        tv3 = root.findViewById(R.id.foot_tv3)
        tv4 = root.findViewById(R.id.foot_tv4)
        tv5 = root.findViewById(R.id.foot_tv5)
        tv6 = root.findViewById(R.id.foot_tv6)
        tv11 = root.findViewById(R.id.foot_tv11)
        tv22 = root.findViewById(R.id.foot_tv22)
        tv33 = root.findViewById(R.id.foot_tv33)
        tv44 = root.findViewById(R.id.foot_tv44)
        tv55 = root.findViewById(R.id.foot_tv55)
        tv66 = root.findViewById(R.id.foot_tv66)

        //得到sp存储的值
        sharedPreferences = requireActivity().getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
        val s_chengshi = sharedPreferences.getString("chengshi", "")
        //判断sp的值时候为空 为空就执行默认城市
        if (s_chengshi == "") {
            selectWeather(cityName)
        } else { //否则查询sp存储的值
            selectWeather(s_chengshi)
        }


        return root
    }

    /**
     * 请求
     * 注意请将秘钥替换为：自己到京东万象申请秘钥
     * @param cityName 城市名称
     */
    @SuppressLint("SetTextI18n")
    private fun selectWeather(cityName: String?) {
        Log.d("d","城市：$cityName")
        val jsonObject1 = JSONObject()
        val url =
            "https://way.jd.com/he/freeweather?city=$cityName&appkey=39eb6073d1a0250877579a294eed96e5"
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, url, jsonObject1, { jsonObject ->
                val gson = Gson()
                weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean::class.java)
                Log.d("d","$weatherBean")
                Log.d("d","${weatherBean.result}")
                Log.d("d","${weatherBean.result?.heWeather5?.isEmpty()}")
                //Log.d("d","${weatherBean.result?.heWeather5?.get(0)}")
                //Log.d("d","${weatherBean.result?.heWeather5?.get(0)?.status}")
                if (weatherBean.result!!.heWeather5[0].status == "unknown location") {
                    Toast.makeText(requireActivity(), "输入城市有误", Toast.LENGTH_SHORT).show()
                } else {
                    tv_zhunagtai.text = weatherBean.result!!.heWeather5[0].now!!.cond!!.txt
                    sheshidu.text = weatherBean.result!!.heWeather5[0].now!!.tmp + "℃"
                    fengxiang.text = weatherBean.result!!.heWeather5[0].now!!.wind!!.dir
                    //根据天气状况 来判断图片
                    if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "多云" || weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "阴") {
                        img_zhungkuang.setImageResource(R.mipmap.yin)
                    } else if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "晴") {
                        img_zhungkuang.setImageResource(R.mipmap.qinglang)
                    } else if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "雨夹雪" || weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "小雪") {
                        img_zhungkuang.setImageResource(R.mipmap.xiaoxue)
                    } else if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "小雨") {
                        img_zhungkuang.setImageResource(R.mipmap.xiaoyu)
                    } else if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "中雨" || weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "大雨") {
                        img_zhungkuang.setImageResource(R.mipmap.dayu)
                    } else if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "雷阵雨") {
                        img_zhungkuang.setImageResource(R.mipmap.leizhenyu)
                    } else if (weatherBean.result!!.heWeather5[0].now!!.cond!!.txt == "雾") {
                        img_zhungkuang.setImageResource(R.mipmap.wu)
                    }
                    //3小时 recyclerView滑动适配
                    val reAdapter = ReAdapter(weatherBean.result!!.heWeather5[0].hourly_forecast)
                    recyclerView.adapter = reAdapter
                    //未来七天  recyclerView滑动适配
                    val reAdapter2 = ReAdapter2(weatherBean.result!!.heWeather5[0].daily_forecast)
                    recyclerView2.adapter = reAdapter2
                    tv_cityName.text = weatherBean.result!!.heWeather5[0].basic!!.city
                    //生活指数部分
                    tv1.text = "舒适度指数：" + weatherBean.result!!.heWeather5[0].suggestion!!.comf!!.brf
                    tv2.text = "洗车指数：" + weatherBean.result!!.heWeather5[0].suggestion!!.cw!!.brf
                    tv3.text = "穿衣指数：" + weatherBean.result!!.heWeather5[0].suggestion!!.drsg!!.brf
                    tv4.text = "感冒指数：" + weatherBean.result!!.heWeather5[0].suggestion!!.flu!!.brf
                    tv5.text = "运动指数：" + weatherBean.result!!.heWeather5[0].suggestion!!.sport!!.brf
                    tv6.text = "旅游指数：" + weatherBean.result!!.heWeather5[0].suggestion!!.trav!!.brf
                    tv11.text =
                        "      " + weatherBean.result!!.heWeather5[0].suggestion!!.comf!!.txt
                    tv22.text = "      " + weatherBean.result!!.heWeather5[0].suggestion!!.cw!!.txt
                    tv33.text =
                        "      " + weatherBean.result!!.heWeather5[0].suggestion!!.drsg!!.txt
                    tv44.text =
                        "      " + weatherBean.result!!.heWeather5[0].suggestion!!.flu!!.txt
                    tv55.text =
                        "      " + weatherBean.result!!.heWeather5[0].suggestion!!.sport!!.txt
                    tv66.text =
                        "      " + weatherBean.result!!.heWeather5[0].suggestion!!.trav!!.txt
                    refreshLayout.isRefreshing = false
                    Toast.makeText(
                        requireActivity(),
                        "更新时间" + weatherBean.result!!.heWeather5[0].basic!!.update!!.loc,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Toast.makeText(requireActivity(), "网络有误", Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }
        requestQueue.add(jsonObjectRequest)
    }

    /**
     * Dialog对话框
     */
    internal inner class MyDialog(context: Context?) : Dialog(
        context!!
    ) {
        override fun onCreate(savedInstanceState: Bundle) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog)
            editText = findViewById(R.id.edit_chengshi)
            btn_chaxun = findViewById(R.id.btn_quding)
            btn_chaxun.setOnClickListener {
                Log.d("dddddddd", "dfgagfagdfg")
                if (editText.text.toString() == "") {
                    Toast.makeText(requireActivity(), "输入为空,重新输入", Toast.LENGTH_SHORT).show()
                } else {
                    selectWeather(editText.text.toString())
                    //sp存储
                    sharedPreferences = requireActivity().getSharedPreferences(
                        "data",
                        AppCompatActivity.MODE_PRIVATE
                    )
                    val editor = sharedPreferences.edit()
                    editor.putString("chengshi", editText.text.toString().trim { it <= ' ' })
                    editor.apply()
                    dismiss()
                }
            }
        }
    }
}