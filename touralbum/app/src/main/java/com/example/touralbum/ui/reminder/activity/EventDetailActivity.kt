package com.example.touralbum.ui.reminder.activity

import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import com.example.touralbum.R
import com.example.touralbum.ui.reminder.Constants.EventFlag
import com.example.touralbum.ui.reminder.base.BaseActivity
import com.example.touralbum.ui.reminder.entity.Event
import com.example.touralbum.ui.reminder.manager.ClockManager
import com.example.touralbum.ui.reminder.manager.EventManager
import com.example.touralbum.ui.reminder.receiver.ClockReceiver
import com.example.touralbum.ui.reminder.service.ClockService
import com.example.touralbum.ui.reminder.util.AlertDialogUtil
import com.example.touralbum.ui.reminder.util.DateTimeUtil
import com.example.touralbum.ui.reminder.util.StringUtil
import com.example.touralbum.ui.reminder.util.ToastUtil
import java.util.*

class EventDetailActivity : BaseActivity() {
    private var isEditEvent = false
    private var isAddEvent = false
    private val mEventManager: EventManager = EventManager.instance
    private val mClockManager: ClockManager = ClockManager.instance

    @kotlin.jvm.JvmField
    @BindView(R.id.ll_update_time)
    var llUpdateTime: LinearLayout? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.ed_title)
    var edTitle: EditText? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.tv_remind_time_picker)
    var tvRemindTime: EditText? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.ed_content)
    var edContent: EditText? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.tv_last_edit_time)
    var tvUpdateTime: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.iv_back)
    var ivBack: ImageView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.tv_confirm)
    var tvConfirm: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.iv_delete)
    var ivDelete: ImageView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.iv_edit)
    var ivEdit: ImageView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.chb_is_important)
    var chbIsImportant: CheckBox? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.scroll_view)
    var scrollView: ScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setListener() {}
    override fun initView() {
        isEditEvent = intent.getBooleanExtra(EXTRA_IS_EDIT_EVENT, false)
        isAddEvent = intent.getBooleanExtra(EXTRA_IS_ADD_EVENT, false)
        judgeOperate()
    }

    private fun judgeOperate() {
        //是否显示上方上次编辑时间
        llUpdateTime!!.visibility = if (isAddEvent) View.GONE else View.VISIBLE
        //是否能够编辑标题
        setEditTextReadOnly(edTitle, !isEditEvent && !isAddEvent)
        //是否能够编辑内容
        setEditTextReadOnly(edContent, !isEditEvent && !isAddEvent)
        //设置提醒时间不能手动输入
        setEditTextReadOnly(tvRemindTime, true)
        //设置提醒时间是否能够点击：弹出时间选择器
        tvRemindTime!!.isClickable = isEditEvent || isAddEvent
        //设置右上角确定按钮是否可见
        tvConfirm!!.visibility = if (isEditEvent || isAddEvent) View.VISIBLE else View.GONE
        //设置右下角编辑按钮是否可见
        ivEdit!!.visibility = if (!isEditEvent && !isAddEvent) View.VISIBLE else View.GONE
        //设置左下角删除按钮是否可见
        ivDelete!!.visibility = if (!isAddEvent) View.VISIBLE else View.GONE
        //设置checkbox能不能点击
        chbIsImportant!!.isClickable = isEditEvent || isAddEvent
    }

    override fun initData() {
        if (!isAddEvent) {
            val event: Event = intent.getParcelableExtra(EXTRA_EVENT_DATA)!!
            //填充值
            tvUpdateTime!!.text = event.getmUpdatedTime()
            edTitle!!.setText(event.getmTitle())
            edContent!!.setText(event.getmContent())
            tvRemindTime!!.setText(event.getmRemindTime())
            chbIsImportant!!.isChecked = event.getmIsImportant() == EventFlag.Companion.IMPORTANT
        }
    }

    @OnClick(R.id.iv_back)
    fun backImageClick(view: View?) {
        finish()
    }

    @OnClick(R.id.iv_delete)
    fun deleteImageClick(view: View?) {
        if (!isAddEvent) {
            AlertDialogUtil.showDialog(this, R.string.delete_event_msg) { dialog, which ->
                val event: Event = intent.getParcelableExtra(EXTRA_EVENT_DATA)!!
                if (mEventManager.removeEvent(event.getmId())) {
                    ToastUtil.showToastShort(R.string.delete_successful)
                    mClockManager.cancelAlarm(buildIntent(event.getmId()!!))
                    mEventManager.flushData()
                    postToMainActivity()
                } else {
                    ToastUtil.showToastShort(R.string.delete_failed)
                }
            }
        }
    }

    private fun postToMainActivity() {
        val intent = Intent()
        intent.setClass(this@EventDetailActivity, ReminderActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override val contentView: Int
        get() = R.layout.activity_event_detail_memo

    /**
     * 弹出时间选择器，选择闹钟执行时间
     * @param view
     */
    @OnClick(R.id.tv_remind_time_picker)
    fun datePickClick(view: View?) {
        if (isEditEvent || isAddEvent) {
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(this, { view, year, month, dayOfMonth ->
                val timePickerDialog =
                    TimePickerDialog(this@EventDetailActivity, { view, hourOfDay, minute ->
                        val time =
                            year.toString() + "-" + StringUtil.getLocalMonth(month) + "-" + StringUtil.getMultiNumber(
                                dayOfMonth
                            ) + " " + StringUtil.getMultiNumber(hourOfDay) + ":" + StringUtil.getMultiNumber(
                                minute
                            )
                        tvRemindTime!!.setText(time)
                    }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false)
                timePickerDialog.show()
            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            dialog.datePicker.minDate = calendar.timeInMillis
            dialog.show()
        }
    }

    @OnClick(R.id.iv_edit)
    fun editImageClick(view: View?) {
        if (!isEditEvent) {
            ToastUtil.showToastShort(R.string.enter_edit_mode)
            ivEdit!!.visibility = View.GONE
            isEditEvent = true
            judgeOperate()
        }
    }

    @OnClick(R.id.tv_confirm)
    fun confirmClick(view: View?) {
        //更新
        if (isEditEvent || isAddEvent) {
            val event = buildEvent()
            //检查属性并且提醒
            if (!mEventManager.checkEventField(event)) {
                return
            }
            if (mEventManager.saveOrUpdate(event)) {
                if (isEditEvent) {
                    ToastUtil.showToastShort(R.string.update_successful)
                } else if (isAddEvent) {
                    ToastUtil.showToastShort(R.string.create_successful)
                    event.setmId(mEventManager.latestEventId)
                }
                //添加闹钟
                mClockManager.addAlarm(
                    buildIntent(event.getmId()!!),
                    DateTimeUtil.str2Date(event.getmRemindTime())
                )
                mEventManager.flushData()
                postToMainActivity()
            } else {
                if (isEditEvent) {
                    ToastUtil.showToastShort(R.string.update_failed)
                } else if (isAddEvent) {
                    ToastUtil.showToastShort(R.string.create_failed)
                }
            }
        }
    }

    private fun buildIntent(id: Int): PendingIntent {
        val intent = Intent()
        intent.putExtra(ClockReceiver.Companion.EXTRA_EVENT_ID, id)
        intent.setClass(this, ClockService::class.java)
        return PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @OnClick(R.id.scroll_view)
    fun scrollViewClick(view: View?) {
        if (isAddEvent || isEditEvent) {
            //打开软键盘
            setEditTextReadOnly(edContent, false)
        }
    }

    private fun buildEvent(): Event {
        val event = Event()
        if (isEditEvent) {
            event.setmId((intent.getParcelableExtra<Parcelable>(EXTRA_EVENT_DATA) as Event?)!!.getmId())
            event.setmCreatedTime((intent.getParcelableExtra<Parcelable>(EXTRA_EVENT_DATA) as Event?)!!.getmCreatedTime())
        }
        event.setmRemindTime(tvRemindTime!!.text.toString())
        event.setmTitle(edTitle!!.text.toString())
        event.setmIsImportant(if (chbIsImportant!!.isChecked) EventFlag.Companion.IMPORTANT else EventFlag.Companion.NORMAL)
        event.setmContent(edContent!!.text.toString())
        event.setmUpdatedTime(DateTimeUtil.dateToStr(Date()))
        return event
    }

    private fun setEditTextReadOnly(editText: EditText?, readOnly: Boolean) {
        editText!!.isFocusable = !readOnly
        editText.isFocusableInTouchMode = !readOnly
        editText.isCursorVisible = !readOnly
        editText.setTextColor(getColor(if (readOnly) R.color.gray3 else R.color.black))
    }

    companion object {
        const val EXTRA_IS_EDIT_EVENT = "extra.is.edit.event"
        const val EXTRA_EVENT_DATA = "extra.event.data"
        const val EXTRA_IS_ADD_EVENT = "extra.is.create.event"
    }
}