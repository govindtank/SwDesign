package com.swensun.swdesign.base

import android.R
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.EditText
import com.swensun.swdesign.app.BaseApplication
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull


/**
 * Created by on 2017/8/1.
 */

fun <T> io2MainObservable(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
}


