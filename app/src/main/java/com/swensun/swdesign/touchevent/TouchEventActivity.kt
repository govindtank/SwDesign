package com.swensun.swdesign.touchevent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.activity_touch_event.*
import kotlinx.android.synthetic.main.content_touch_event.*

class TouchEventActivity : AppCompatActivity() {

    //  修改clickable即可屏蔽点击事件
    var clickable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        custom_btn.setOnClickListener {
            Logger.d("click custom btn")
        }
        custom_btn.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.performClick()
            }
            true
        }
        custom_card_view.setOnClickListener {
            Logger.d("click custom card view")
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d("onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (clickable) {
            super.dispatchTouchEvent(ev)
        } else {
            true
        }
    }
}
