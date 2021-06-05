package com.example.touralbum.ui.reminder.base

import android.content.*
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.touralbum.R
import com.example.touralbum.ui.reminder.Constants.EventFlag
import com.example.touralbum.ui.reminder.Constants.MemoIconTag
import com.example.touralbum.ui.reminder.activity.EventDetailActivity
import com.example.touralbum.ui.reminder.base.EventRecyclerViewAdapter.EventViewHolder
import com.example.touralbum.ui.reminder.entity.Event
import java.util.*

class EventRecyclerViewAdapter(private val mContext: Context) :
    RecyclerView.Adapter<EventViewHolder>() {
    private var mDatabases: List<Event?>? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mIsDeleteMode = false
    private val mSelectedEventIds: MutableList<Int?> = ArrayList()
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    var isDeleteMode: Boolean
        get() = mIsDeleteMode
        set(mIsDeleteMenu) {
            mSelectedEventIds.clear()
            mIsDeleteMode = mIsDeleteMenu
            notifyDataSetChanged()
        }
    val selectedEventIds: List<Int?>
        get() = mSelectedEventIds

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.memo_item_memo_layout, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = mDatabases!![position]
        if (!mIsDeleteMode) {
            if (event!!.getmIsImportant() == EventFlag.Companion.IMPORTANT) {
                holder.ivMemoIcon!!.setImageResource(R.drawable.collection_memo)
                holder.tvTitle!!.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            } else {
                holder.ivMemoIcon!!.setImageResource(R.drawable.normal_memo)
                holder.tvTitle!!.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            }
        } else {
            holder.ivMemoIcon!!.setImageResource(R.drawable.ic_circle_memo)
            if (event!!.getmIsImportant() == EventFlag.Companion.IMPORTANT) {
                holder.tvTitle!!.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            } else {
                holder.tvTitle!!.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            }
        }
        holder.tvTitle!!.text = event.getmTitle()
        holder.itemView.setOnClickListener { v ->
            if (mIsDeleteMode) {
                if (MemoIconTag.Companion.FIRST == holder.ivMemoIcon!!.tag as Int) {
                    holder.ivMemoIcon!!.tag = MemoIconTag.Companion.OTHER
                    holder.ivMemoIcon!!.setImageResource(R.drawable.ic_selected_memo)
                    mSelectedEventIds.add(event.getmId())
                } else {
                    mSelectedEventIds.remove(event.getmId())
                    holder.ivMemoIcon!!.tag = MemoIconTag.Companion.FIRST
                    holder.ivMemoIcon!!.setImageResource(R.drawable.ic_circle_memo)
                }
            } else if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(v, holder.layoutPosition)
            }
        }
        holder.itemView.setOnLongClickListener { v ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemLongClick(v, holder.layoutPosition)
            }
            false
        }
        holder.ivEdit!!.setOnClickListener {
            val intent = Intent()
            intent.setClass(mContext, EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.Companion.EXTRA_IS_EDIT_EVENT, true)
            intent.putExtra(EventDetailActivity.Companion.EXTRA_EVENT_DATA, event)
            mContext.startActivity(intent)
        }
    }

    var databases: List<Event?>?
        get() = mDatabases
        set(events) {
            mDatabases = events
            mIsDeleteMode = false
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return if (mDatabases == null) 0 else mDatabases!!.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }

    inner class EventViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {
        @kotlin.jvm.JvmField
        @BindView(R.id.iv_memo)
        var ivMemoIcon: ImageView? = null

        @kotlin.jvm.JvmField
        @BindView(R.id.tv_title)
        var tvTitle: TextView? = null

        @kotlin.jvm.JvmField
        @BindView(R.id.iv_edit)
        var ivEdit: ImageView? = null
        val itemView: View
            get() = this.itemView

        init {
            ButterKnife.bind(this, itemView!!)
            ivMemoIcon!!.tag = MemoIconTag.Companion.FIRST
        }
    }
}