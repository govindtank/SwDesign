package com.swensun.swdesign.base

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.EditText
import com.swensun.swdesign.app.BaseApplication
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by on 2017/8/1.
 */

fun <T> io2MainObservable(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
}


fun AppCompatActivity.displaybackBtn() {
    this.supportActionBar?.let {
        it.setDisplayShowHomeEnabled(true)
        it.setDisplayHomeAsUpEnabled(true)
    }
}

//click and hide keyboard
fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
    if (v != null && v is EditText) {
        val leftTop = intArrayOf(0, 0)
        v.getLocationInWindow(leftTop)
        val left = leftTop[0]
        val top = leftTop[1]
        val bottom = top + v.height
        val right = left + v.width
        return event.x < left || event.x > right || event.y < top || event.y > bottom
    }
    return false
}

fun isAccessibilityServiceEnable(): Boolean {
    val services = "ui.MyAccessibilityService"
    val accessibilityManager = BaseApplication.application.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    val accessibilityServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
    return accessibilityServices.any { it.id.contains(services) }
}

fun openAccessibilitySetting(context: Context) {
    val accessibleIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    context.startActivity(accessibleIntent)
}


