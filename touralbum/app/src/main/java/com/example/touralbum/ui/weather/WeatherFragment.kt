package com.example.touralbum.ui.weather

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
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

    private var tv_zhunagtai: TextView? = null
    var cityName = "上海"
    private var recyclerView: RecyclerView? = null
    private var recyclerView2: RecyclerView? = null
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var tv3: TextView? = null
    private var tv4: TextView? = null
    private var tv5: TextView? = null
    private var tv6: TextView? = null
    private var tv11: TextView? = null
    private var tv22: TextView? = null
    private var tv33: TextView? = null
    private var tv44: TextView? = null
    private var tv55: TextView? = null
    private var tv66: TextView? = null
    private var sheshidu: TextView? = null
    private var fengxiang: TextView? = null
    private var img_zhungkuang: ImageView? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private var weatherBean: WeatherBean? = null
    private var tv_cityName: TextView? = null
    private var editText: EditText? = null
    private var btn_chaxun: Button? = null
    private var qiehuan: TextView? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_weather, container, false)

        //import fonts
        val test1 = requireActivity().findViewById<View>(R.id.foot_tv1) as TextView
        val typeface1 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test1.setTypeface(typeface1)
        val test2 = requireActivity().findViewById<View>(R.id.foot_tv2) as TextView
        val typeface2 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test2.setTypeface(typeface2)
        val test3 = requireActivity().findViewById<View>(R.id.foot_tv3) as TextView
        val typeface3 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test3.setTypeface(typeface3)
        val test4 = requireActivity().findViewById<View>(R.id.foot_tv4) as TextView
        val typeface4 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test4.setTypeface(typeface4)
        val test5 = requireActivity().findViewById<View>(R.id.foot_tv5) as TextView
        val typeface5 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test5.setTypeface(typeface5)
        val test6 = requireActivity().findViewById<View>(R.id.foot_tv6) as TextView
        val typeface6 = Typeface.createFromAsset(requireActivity().assets, "wawati.TTF")
        test6.setTypeface(typeface6)
        tv_zhunagtai = requireActivity().findViewById(R.id.tv_zhuangtai)
        sheshidu = requireActivity().findViewById(R.id.tv_sheshidu)
        fengxiang = requireActivity().findViewById(R.id.tv_fengxiang)
        img_zhungkuang = requireActivity().findViewById(R.id.img_now_zhuangkuang)
        recyclerView = requireActivity().findViewById(R.id.ry_recycleView)
        recyclerView2 = requireActivity().findViewById(R.id.ry_recycleView2)
        refreshLayout = requireActivity().findViewById(R.id.srl_swipe)
        tv_cityName = requireActivity().findViewById(R.id.tv_cityName)
        qiehuan = requireActivity().findViewById(R.id.tv_qiehuan)
        tv_cityName!!.setOnClickListener(View.OnClickListener {
            val myDialog = MyDialog(requireActivity())
            myDialog.show()
        })

        qiehuan!!.setOnClickListener(View.OnClickListener {
            val myDialog = MyDialog(requireActivity())
            myDialog.show()
        })

        //下拉刷新
        refreshLayout!!.setOnRefreshListener(OnRefreshListener {
            Handler().postDelayed(
                { selectWeather(tv_cityName!!.getText().toString()) }, 3000
            )
        })
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView!!.setLayoutManager(linearLayoutManager)
        val linearLayoutManager1 = LinearLayoutManager(requireActivity())
        linearLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView2!!.setLayoutManager(linearLayoutManager1)
        tv1 = requireActivity().findViewById(R.id.foot_tv1)
        tv2 = requireActivity().findViewById(R.id.foot_tv2)
        tv3 = requireActivity().findViewById(R.id.foot_tv3)
        tv4 = requireActivity().findViewById(R.id.foot_tv4)
        tv5 = requireActivity().findViewById(R.id.foot_tv5)
        tv6 = requireActivity().findViewById(R.id.foot_tv6)
        tv11 = requireActivity().findViewById(R.id.foot_tv11)
        tv22 = requireActivity().findViewById(R.id.foot_tv22)
        tv33 = requireActivity().findViewById(R.id.foot_tv33)
        tv44 = requireActivity().findViewById(R.id.foot_tv44)
        tv55 = requireActivity().findViewById(R.id.foot_tv55)
        tv66 = requireActivity().findViewById(R.id.foot_tv66)

        //得到sp存储的值
        sharedPreferences = requireActivity().getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
        val s_chengshi = sharedPreferences!!.getString("chengshi", "")
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
    private fun selectWeather(cityName: String?) {
        val jsonObject = JSONObject()
        val url =
            "https://way.jd.com/he/freeweather?city=$cityName&appkey=1fea054bfd7426b48e50b2863fdc2bd2"
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, url, jsonObject, { jsonObject ->
                val gson = Gson()
                weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean::class.java)
                if (weatherBean!!.result!!.heWeather5?.get(0)!!.status == "unknown location") {
                    Toast.makeText(requireActivity(), "输入城市有误", Toast.LENGTH_SHORT).show()
                } else {
                    tv_zhunagtai!!.text = weatherBean!!.result!!.heWeather5!!.get(0)!!.now!!.cond!!.txt
                    sheshidu!!.text = weatherBean!!.result!!.heWeather5!!.get(0).now!!.tmp + "℃"
                    fengxiang!!.text = weatherBean!!.result!!.heWeather5!!.get(0).now!!.wind!!.dir
                    //根据天气状况 来判断图片
                    if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "多云" || weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "阴") {
                        img_zhungkuang!!.setImageResource(R.mipmap.yin)
                    } else if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "晴") {
                        img_zhungkuang!!.setImageResource(R.mipmap.qinglang)
                    } else if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "雨夹雪" || weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "小雪") {
                        img_zhungkuang!!.setImageResource(R.mipmap.xiaoxue)
                    } else if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "小雨") {
                        img_zhungkuang!!.setImageResource(R.mipmap.xiaoyu)
                    } else if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "中雨" || weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "大雨") {
                        img_zhungkuang!!.setImageResource(R.mipmap.dayu)
                    } else if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "雷阵雨") {
                        img_zhungkuang!!.setImageResource(R.mipmap.leizhenyu)
                    } else if (weatherBean!!.result!!.heWeather5?.get(0)!!.now!!.cond!!.txt == "雾") {
                        img_zhungkuang!!.setImageResource(R.mipmap.wu)
                    }
                    //3小时 recyclerView滑动适配
                    val reAdapter = weatherBean!!.result!!.heWeather5?.get(0)!!.hourly_forecast?.let {
                        ReAdapter(
                            it
                        )
                    }
                    recyclerView!!.adapter = reAdapter
                    //未来七天  recyclerView滑动适配
                    val reAdapter2 =
                        weatherBean!!.result!!.heWeather5?.get(0)?.let { it.daily_forecast?.let { it1 ->
                            ReAdapter2(
                                it1
                            )
                        } }
                    recyclerView2!!.adapter = reAdapter2
                    tv_cityName!!.text = weatherBean!!.result!!.heWeather5?.get(0)!!.basic!!.city
                    //生活指数部分
                    tv1!!.text =
                        "舒适度指数：" + weatherBean!!.result?.heWeather5?.get(0)!!.suggestion!!.comf!!.brf
                    tv2!!.text = "洗车指数：" + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.cw!!.brf
                    tv3!!.text = "穿衣指数：" + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.drsg!!.brf
                    tv4!!.text = "感冒指数：" + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.flu!!.brf
                    tv5!!.text =
                        "运动指数：" + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.sport!!.brf
                    tv6!!.text = "旅游指数：" + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.trav!!.brf
                    tv11!!.text =
                        "      " + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.comf!!.txt
                    tv22!!.text = "      " + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.cw!!.txt
                    tv33!!.text =
                        "      " + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.drsg!!.txt
                    tv44!!.text =
                        "      " + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.flu!!.txt
                    tv55!!.text =
                        "      " + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.sport!!.txt
                    tv66!!.text =
                        "      " + weatherBean!!.result!!.heWeather5?.get(0)!!.suggestion!!.trav!!.txt
                    refreshLayout!!.isRefreshing = false
                    Toast.makeText(
                        requireActivity(),
                        "更新时间" + weatherBean!!.result!!.heWeather5?.get(0)!!.basic!!.update!!.loc,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Toast.makeText(requireActivity(), "网络有误", Toast.LENGTH_SHORT).show()
                refreshLayout!!.isRefreshing = false
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
            btn_chaxun!!.setOnClickListener(View.OnClickListener {
                Log.d("dddddddd", "dfgagfagdfg")
                if (editText!!.getText().toString() == "") {
                    Toast.makeText(requireActivity(), "输入为空,重新输入", Toast.LENGTH_SHORT).show()
                } else {
                    selectWeather(editText!!.getText().toString())
                    //sp存储
                    sharedPreferences = requireActivity().getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
                    val editor = sharedPreferences!!.edit()
                    editor.putString("chengshi", editText!!.getText().toString().trim { it <= ' ' })
                    editor.apply()
                    dismiss()
                }
            })
        }
    }
}