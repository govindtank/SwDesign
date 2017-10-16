package com.swensun.swdesign.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.text.TextPaint
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.swensun.swdesign.app.BaseApplication


/**
 * Created by macmini on 2017/8/17.
 */
fun getDrawable(@DrawableRes resId: Int): Drawable? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BaseApplication.application.getDrawable(resId)
        } else {
            @Suppress("DEPRECATION")
            BaseApplication.application.resources.getDrawable(resId)
        }

fun getString(@StringRes resId: Int) = BaseApplication.application.getString(resId) ?: ""

fun getDimen(@DimenRes resId: Int) = BaseApplication.application.resources.getDimensionPixelOffset(resId)

fun getColor(@ColorRes resId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BaseApplication.application.getColor(resId)
        } else {
            @Suppress("DEPRECATION")
            BaseApplication.application.resources.getColor(resId)
        }

fun getColor(color: String) = Color.parseColor(color)


fun dp2px(value: Float): Int {
    val f = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            value, getDisplayMetrics(BaseApplication.application.applicationContext))
    val res = (f + 0.5f).toInt()
    if (res != 0) return res
    if (value == 0f) return 0
    if (value > 0) return 1
    return -1
}

fun sp2px(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        value, getDisplayMetrics(BaseApplication.application.applicationContext))

fun px2dp(pxValue: Float): Int {
    val scale = getDisplayMetrics(BaseApplication.application.applicationContext).density
    return (pxValue / scale + 0.5f).toInt()
}

fun getDisplayMetrics(context: Context): DisplayMetrics {
    return context.resources.displayMetrics
}

fun hideKeyboard(act: Activity) {
    val view = act.currentFocus
    if (view != null) {
        val imm = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun showKeyboard(act: Activity) {
    try {
        val imm = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(act.currentFocus!!, InputMethodManager.SHOW_FORCED)
    } catch (exception: NullPointerException) {
        Logger.d("keyboard ,InputMethodManager can't find focus")
    }
}

fun isNetworkAvailable(): Boolean {
    val connectivityManager = BaseApplication.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = connectivityManager.activeNetworkInfo ?: return false
    return ni.isConnected || ni.isAvailable && ni.isConnectedOrConnecting
}


private fun getSize(): Point {
    val wm = BaseApplication.application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    var size = Point()
    display.getSize(size)
    return size
}

fun getWinHeight(): Int {
    return getSize().y
}

fun getWinWidth(): Int {
    return getSize().x
}

fun getNavigationBarHeight(): Int {
    BaseApplication.application.resources.let {
        val id = it.getIdentifier("navigation_bar_height", "dimen", "android")
        return it.getDimensionPixelSize(id)
    }
}
fun getStatusBarBarHeight(): Int {
    BaseApplication.application.resources.let {
        val id = it.getIdentifier("status_bar_height", "dimen", "android")
        return it.getDimensionPixelSize(id)
    }
}

fun TextView.adjustTextSize(maxWidth: Int, text: String) {
    val avaiWidth = maxWidth - this.paddingLeft - this.paddingRight - 10

    if (avaiWidth <= 0) {
        return
    }

    val textPaintClone = TextPaint(this.paint)
    // note that Paint text size works in px not sp
    var trySize = textPaintClone.textSize

    while (textPaintClone.measureText(text) > avaiWidth) {
        trySize--
        textPaintClone.textSize = trySize
    }

    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize)
    this.text = text
}

fun checkDeviceNavigation(activity: Activity): Boolean {
    val windowManager = activity.windowManager
    val d = windowManager.defaultDisplay

    val realDisplayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        d.getRealMetrics(realDisplayMetrics)
    }

    val realHeight = realDisplayMetrics.heightPixels
    val realWidth = realDisplayMetrics.widthPixels

    val displayMetrics = DisplayMetrics()
    d.getMetrics(displayMetrics)

    val displayHeight = displayMetrics.heightPixels
    val displayWidth = displayMetrics.widthPixels

    return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
}