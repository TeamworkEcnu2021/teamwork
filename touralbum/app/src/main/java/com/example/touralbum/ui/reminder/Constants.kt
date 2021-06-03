package com.example.touralbum.ui.reminder

object Constants {
    const val EMPTY = ""
    const val DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val DEFAULT_CHINESE_TIME_FORMAT = "yyyy年MM月dd日 HH:mm"
    const val CLOCK_RECEIVER_ACTION = "cn.jsbintask.memo.action.CLOCK_RECEIVER"
    const val HANDLER_SUCCESS = 0x0001
    const val HANDLER_FAILED = 0x0000

    interface EventFlag {
        companion object {
            const val IMPORTANT = 1
            const val NORMAL = 0
        }
    }

    interface MemoIconTag {
        companion object {
            const val FIRST = 1
            const val OTHER = 2
        }
    }

    interface EventClockFlag {
        companion object {
            const val NONE = 0
            const val CLOCKED = 10
        }
    }
}