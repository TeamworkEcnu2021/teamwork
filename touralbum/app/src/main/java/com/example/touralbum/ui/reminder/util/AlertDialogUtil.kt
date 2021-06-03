package com.example.touralbum.ui.reminder.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import com.example.touralbum.R

/**
 * @author jsbintask@gmail.com
 * @date 2018/4/25 20:25
 */
object AlertDialogUtil {
    fun showDialog(
        context: Context?,
        msg: Int,
        positiveListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
        builder.setCancelable(false)
            .setPositiveButton(R.string.confirm_text, positiveListener)
            .setNegativeButton(R.string.cancel_text, null)
        builder.create().show()
    }

    fun showDialog(
        context: Context?,
        msg: String?,
        positiveListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
        builder.setCancelable(false)
            .setPositiveButton(R.string.confirm_text, positiveListener)
            .setNegativeButton(R.string.cancel_text, null)
        builder.create().show()
    }

    fun showDialog(context: Context?, view: View?): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(false)
        return builder.create()
    }
}