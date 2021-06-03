package com.example.touralbum.ui.reminder.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseActivity : AppCompatActivity() {
    private var mUnbinder: Unbinder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        mUnbinder = ButterKnife.bind(this)
        initView()
        initData()
        setListener()
    }

    protected abstract fun setListener()
    protected abstract fun initView()
    protected abstract fun initData()
    abstract val contentView: Int
    override fun onDestroy() {
        super.onDestroy()
        mUnbinder!!.unbind()
    }
}