package com.example.touralbum.ui.reminder.entity

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.provider.BaseColumns

class Event : BaseColumns, Parcelable {
    private var mId: Int? = null
    private var mTitle: String? = null
    private var mContent: String? = null
    private var mCreatedTime: String? = null
    fun getmIsClocked(): Int? {
        return mIsClocked
    }

    fun setmIsClocked(mIsClocked: Int?) {
        this.mIsClocked = mIsClocked
    }

    private var mUpdatedTime: String? = null
    private var mIsClocked: Int? = 0

    constructor() {}
    protected constructor(`in`: Parcel) {
        mId = if (`in`.readByte().toInt() == 0) {
            null
        } else {
            `in`.readInt()
        }
        mTitle = `in`.readString()
        mContent = `in`.readString()
        mCreatedTime = `in`.readString()
        mUpdatedTime = `in`.readString()
        mIsClocked = if (`in`.readByte().toInt() == 0) {
            null
        } else {
            `in`.readInt()
        }
        mIsImportant = if (`in`.readByte().toInt() == 0) {
            null
        } else {
            `in`.readInt()
        }
        mRemindTime = `in`.readString()
    }

    fun getmIsImportant(): Int? {
        return mIsImportant
    }

    fun setmIsImportant(mIsImportant: Int?) {
        this.mIsImportant = mIsImportant
    }

    private var mIsImportant: Int? = null
    fun getmId(): Int? {
        return mId
    }

    fun setmId(mId: Int?) {
        this.mId = mId
    }

    fun getmTitle(): String? {
        return mTitle
    }

    fun setmTitle(mTitle: String?) {
        this.mTitle = mTitle
    }

    fun getmContent(): String? {
        return mContent
    }

    fun setmContent(mContent: String?) {
        this.mContent = mContent
    }

    fun getmCreatedTime(): String? {
        return mCreatedTime
    }

    fun setmCreatedTime(mCreatedTime: String?) {
        this.mCreatedTime = mCreatedTime
    }

    fun getmUpdatedTime(): String? {
        return mUpdatedTime
    }

    fun setmUpdatedTime(mUpdatedTime: String?) {
        this.mUpdatedTime = mUpdatedTime
    }

    fun getmRemindTime(): String? {
        return mRemindTime
    }

    fun setmRemindTime(mRemindTime: String?) {
        this.mRemindTime = mRemindTime
    }

    private var mRemindTime: String? = null
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        if (mId == null) {
            dest.writeByte(0.toByte())
        } else {
            dest.writeByte(1.toByte())
            dest.writeInt(mId!!)
        }
        dest.writeString(mTitle)
        dest.writeString(mContent)
        dest.writeString(mCreatedTime)
        dest.writeString(mUpdatedTime)
        if (mIsClocked == null) {
            dest.writeByte(0.toByte())
        } else {
            dest.writeByte(1.toByte())
            dest.writeInt(mIsClocked!!)
        }
        if (mIsImportant == null) {
            dest.writeByte(0.toByte())
        } else {
            dest.writeByte(1.toByte())
            dest.writeInt(mIsImportant!!)
        }
        dest.writeString(mRemindTime)
    }

    companion object {
        @JvmField val CREATOR: Creator<Event?> = object : Creator<Event?> {
            override fun createFromParcel(`in`: Parcel): Event? {
                return Event(`in`)
            }

            override fun newArray(size: Int): Array<Event?> {
                return arrayOfNulls(size)
            }
        }
    }
}