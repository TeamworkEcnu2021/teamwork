package com.example.touralbum.ui.reminder.activity

import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.reminder.base.BaseActivity
import com.example.touralbum.ui.reminder.base.BaseHandler
import com.example.touralbum.ui.reminder.base.BaseHandler.HandlerResultCallBack
import com.example.touralbum.ui.reminder.base.EventRecyclerViewAdapter
import com.example.touralbum.ui.reminder.entity.Event
import com.example.touralbum.ui.reminder.exception.MemoException
import com.example.touralbum.ui.reminder.manager.ClockManager
import com.example.touralbum.ui.reminder.manager.EventManager
import com.example.touralbum.ui.reminder.service.ClockService
import com.example.touralbum.ui.reminder.util.AlertDialogUtil
import com.example.touralbum.ui.reminder.util.AppUtil
import com.example.touralbum.ui.reminder.util.StringUtil
import com.example.touralbum.ui.reminder.util.ToastUtil
import java.util.*

class ReminderActivity : BaseActivity(), HandlerResultCallBack {

    private var mRecyclerView: RecyclerView = findViewById(R.id.recycler_view)
    private var mSearchView: SearchView = findViewById(R.id.search_view)
    private var mAdapter: EventRecyclerViewAdapter? = null
    private val mEventManger: EventManager = EventManager.instance
    private val mClockManager: ClockManager = ClockManager.instance
    private val mBaseHandler = BaseHandler(this)

    override fun initView() {
        mAdapter = EventRecyclerViewAdapter(this)
        val params = mSearchView.layoutParams as LinearLayout.LayoutParams
        //将文字内容略微下移，SearchView  bug
        params.bottomMargin = -3
        mSearchView.layoutParams = params
        mSearchView.onActionViewExpanded()
        initSearchView()
    }

    private fun initSearchView() {
        //一处searchView进入屏幕时候的焦点
        mSearchView.clearFocus()
        val aClass: Class<out SearchView> = mSearchView.javaClass
        try {
            //去掉SearchView自带的下划线
            val mSearchPlate = aClass.getDeclaredField("mSearchPlate")
            mSearchPlate.isAccessible = true
            val o = mSearchPlate[mSearchView] as View
            o.setBackgroundColor(getColor(R.color.transparent))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //隐藏键盘
        AppUtil.hideSoftInput(this, mSearchView)
    }

    override fun initData() {
        //设置数据源，适配器等等
        mAdapter?.databases = mEventManger.findAll()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun setListener() {
        mAdapter!!.setOnItemClickListener(mOnItemClickListener)
        mSearchView.setOnQueryTextListener(mQueryListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity_memo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //判断是点击了那个按钮，删除，添加
        if (item.itemId == R.id.menu_add) {
            val intent = Intent()
            intent.setClass(this, EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.EXTRA_IS_ADD_EVENT, true)
            startActivity(intent)
        } else if (item.itemId == R.id.menu_delete) {
            if (mAdapter!!.isDeleteMode) {
                //删除数据
                if (mAdapter!!.selectedEventIds.isEmpty()) {
                    ToastUtil.showToastShort(R.string.no_event_selected_msg)
                } else {
                    val msg =
                        if (mAdapter!!.selectedEventIds.size == 1) R.string.delete_event_msg else R.string.delete_events_msg
                    AlertDialogUtil.showDialog(this, msg, mConfirmListener)
                }
            } else {
                mAdapter!!.isDeleteMode = true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override val contentView: Int
        get() = R.layout.activity_main
    private val mOnItemClickListener: EventRecyclerViewAdapter.OnItemClickListener =
        object : EventRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                if (!mAdapter!!.isDeleteMode) {
                    //跳屏，此时为查看详情，不是编辑状态
                    val intent = Intent()
                    intent.setClass(view!!.context, EventDetailActivity::class.java)
                    intent.putExtra(EventDetailActivity.EXTRA_IS_EDIT_EVENT, false)
                    intent.putExtra(
                        EventDetailActivity.EXTRA_EVENT_DATA,
                        mAdapter!!.databases?.get(position)
                    )
                    startActivity(intent)
                }
            }

            override fun onItemLongClick(view: View?, position: Int) {
                ToastUtil.showToastShort("Long clicked")
                mAdapter!!.isDeleteMode = true
            }
        }
    private val mConfirmListener = DialogInterface.OnClickListener { dialog, which ->
        mEventManger.deletedIds = mAdapter!!.selectedEventIds
        mEventManger.removeEvents(mBaseHandler, mEventManger.deletedIds)
    }

    /**
     * 从编辑屏幕回来时调用该方法，做数据更新
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mAdapter!!.databases = mEventManger.events
    }

    private val mQueryListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //做本地查询
                if (!StringUtil.isBlank(newText)) {
                    val events: MutableList<Event?> = ArrayList()
                    for (event in mAdapter!!.databases!!) {
                        if (event!!.getmTitle()!!.contains(newText)) {
                            events.add(event)
                        }
                    }
                    mAdapter!!.databases = events
                } else {
                    mAdapter!!.databases = mEventManger.events
                }
                return true
            }
        }

    /**
     * handler处理成功的回调函数
     * @link com.jsbintask@gmail.com.memo.base.BaseHandler
     */
    override fun handlerSuccess(msg: Message?) {
        ToastUtil.showToastShort(R.string.delete_successful)
        for (pendingIntent in buildIntent(mEventManger.deletedIds)) {
            mClockManager.cancelAlarm(pendingIntent)
        }
        mAdapter!!.databases = mEventManger.findAll()
    }

    /**
     * 处理失败的回调函数
     */
    override fun handlerFailed(e: MemoException?) {
        ToastUtil.showToastShort(R.string.delete_failed)
        mAdapter!!.isDeleteMode = false
    }

    private fun buildIntent(ids: List<Int?>?): List<PendingIntent> {
        val pendingIntents: MutableList<PendingIntent> = ArrayList()
        for (id in ids!!) {
            val intent = Intent()
            intent.putExtra(ClockService.EXTRA_EVENT_ID, id)
            intent.setClass(this, ClockService::class.java)
            pendingIntents.add(
                PendingIntent.getService(
                    this,
                    0x001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }
        return pendingIntents
    }
}