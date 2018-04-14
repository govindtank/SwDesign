package com.swensun.swdesign.base

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.swensun.swdesign.R
import com.swensun.swutils.util.dp2px
import com.swensun.swutils.util.showToast
import org.jetbrains.anko.find
import timber.log.Timber

fun TextView.countDown() {
    val frameLayout = FrameLayout(context)
    val rootView = LayoutInflater.from(context).inflate(R.layout.view_count_down, null)
    val mobileView = rootView.find<EditText>(R.id.vcd_et_mobile)
    val codeView = rootView.find<EditText>(R.id.vcd_et_code)
    val sendCode = rootView.find<TextView>(R.id.vcd_tv_send_code)
    sendCode.setOnClickListener {
        Timber.d("发送验证码")
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
            })
            .setPositiveButton("确认", { _, _ ->
                showToast("确认")
            })
            .setView(frameLayout)
            .show()
}