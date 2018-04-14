package com.swensun.swdesign.base

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.swensun.swdesign.R
import com.swensun.swutils.util.dp2px
import com.swensun.swutils.util.showToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.find
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun TextView.countDown() {
    val frameLayout = FrameLayout(context)
    val rootView = LayoutInflater.from(context).inflate(R.layout.view_count_down, null)
    val mobileView = rootView.find<EditText>(R.id.vcd_et_mobile)
    val codeView = rootView.find<EditText>(R.id.vcd_et_code)
    val sendCode = rootView.find<TextView>(R.id.vcd_tv_send_code)

    var disposable: Disposable? = null
    val countTimesObservable = Observable.interval(1, TimeUnit.SECONDS)
            .take(10)
            .doOnDispose {
                Timber.d("取消订阅")
            }
            .doOnSubscribe {
                Timber.d( "开始计时")
                disposable = it
            }.doOnComplete {
                Timber.d( "结束计时")
                sendCode.text = "发送验证码"
                disposable?.dispose()
            }
            .observeOn(AndroidSchedulers.mainThread())

    sendCode.setOnClickListener {
        countTimesObservable.subscribe {
            val second = 9 - it
            sendCode.text = "已发送($second)"
            Timber.d(second.toString())
        }
    }

    val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
    lp.topMargin = dp2px(20f)
    lp.bottomMargin = dp2px(20f)
    lp.leftMargin = dp2px(24f)
    lp.rightMargin = dp2px(24f)
    rootView.layoutParams = lp
    frameLayout.addView(rootView)

    AlertDialog.Builder(context).setTitle("手机号码")
            .setNegativeButton("取消", { _, _ ->
                showToast("取消")
                disposable?.dispose()
            })
            .setPositiveButton("确认", { _, _ ->
                showToast("确认")
                disposable?.dispose()
            })
            .setView(frameLayout)
            .show()
}

fun add1(a: Int, b: Int): Int {
    return a + b
}

fun add2(a: Int, b: Int) = a + b

val add3 = {a: Int, b: Int -> a + b}

fun add4(): (Int, Int) -> Int {
    return ::add2
}

fun add5() = {
    add3
}

val add5_1 = {
    val temp = 3 + 4
    temp + 10
}

val add5_2 = 10

fun add5_1() = {
    val temp = 4 + 5
    10 + temp
}

fun add5_2(): Int {
    return 10
}

val add5_3_1 = {
    val temp = 10
    {a: Int -> a + temp}
}


fun add5_3_2() = {
    val temp = 10
    {a: Int -> a + temp}
}


val add6 = add3


fun add7(): (Int, Int) -> Int {
    return fun(a: Int, b: Int): Int {
        return a + b
    }
}

fun add8(): (Int, Int) -> Int {
    return {a: Int, b: Int -> a + b}
}

val add9 = {a: Int, b: Int -> a + b}


fun calculate(a: Int, b: Int, f: (Int, Int) -> Int) : Int {
    return f(a, b)
}

