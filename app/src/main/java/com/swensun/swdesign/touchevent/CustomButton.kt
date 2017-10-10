package com.swensun.swdesign.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import com.orhanobut.logger.Logger

/**
 * Created by macmini on 2017/10/10.
 */

class CustomButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d(event?.action)
        return super.onTouchEvent(event)
    }
}
