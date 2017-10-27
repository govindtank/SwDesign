package com.swensun.swdesign.ui.touchevent

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by macmini on 2017/10/10.
 */

class CustomCardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Logger.d(event?.action)
        return super.onTouchEvent(event)
    }
}
