package com.swensun.swdesign.touchevent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.orhanobut.logger.Logger
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.activity_touch_event.*
import kotlinx.android.synthetic.main.content_touch_event.*

class TouchEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
        setSupportActionBar(toolbar)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d(event?.action)
        custom_card_view.dispatchTouchEvent(event)
        return super.onTouchEvent(event)
    }
}
