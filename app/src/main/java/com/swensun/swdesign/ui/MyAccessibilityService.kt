package com.swensun.swdesign.ui

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import com.swensun.swdesign.ui.ins.isAutoLike

/**
 * Created by macmini on 2017/10/26.
 */


class MyAccessibilityService: AccessibilityService() {

    companion object {
        val TAG = "MyAccessibilityService"
    }
    var listNote: AccessibilityNodeInfo? =null
    private var isFirstCalled = true
    private var page = 1

//    com.instagram.android
//    com.instagram.android/.activity.MainTabActivity
//    com.instagram.android:id/row_feed_button_comment
//    com.instagram.android:id/row_feed_button_like


    override fun onServiceConnected() {
        super.onServiceConnected()
        Logger.d("connect to accessibilityService")

    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.source == null) { return; }
        val eventType = event.eventType
        val rootInfo = rootInActiveWindow
//        Log.d(TAG,  containListView(rootInfo).toString())
//        if (eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
//
//        }
        Log.d(TAG, eventType.toString())
        if (!isAutoLike) {
            return
        }
        if (!isFirstCalled) {
            return
        }
        if (containListView(rootInfo)) {
            isFirstCalled = false
            listNote?.let { note ->
//                Observable.interval(3, TimeUnit.SECONDS)
//                        .take(20)
//                        .doOnComplete {
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe {
//                            applyAutoLike(note)
//                            if (note.isScrollable) {
//                                note.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
//                            }
//                        }
                while (note.isScrollable) {
                    if (page >= 10) {
                        isFirstCalled = true
                        return
                    }
                    note.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                    Thread.sleep(1000)
                    page += 1
                }
            }
        }

    }

    private fun containListView(rootInfo: AccessibilityNodeInfo?): Boolean {
        if (rootInfo == null) return false
        val childCount = rootInfo.childCount
        Log.d(TAG, rootInfo.className.toString())
        when(rootInfo.className.toString()) {
            "android.widget.ListView" -> {
                listNote = rootInfo
                return true
            }
        }
        (0 until childCount).forEach {
            val childInfo = rootInfo.getChild(it)
            if (containListView(childInfo)) {
                return true
            }
        }

        return false
    }

    private fun applyAutoLike(noteInfo: AccessibilityNodeInfo?) {
        if (noteInfo == null) return
//        val noteInfo = event?.source!!
        val childCount = noteInfo.childCount
        Log.d(TAG, noteInfo.toString())
        if (noteInfo.className.toString() == "android.widget.ImageView") {
            if (noteInfo.contentDescription == "赞") {
                noteInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }

        }
        (0 until childCount).forEach {
            val childInfo = noteInfo.getChild(it)
            applyAutoLike(childInfo)
        }
    }

    fun applyAutoAttention() {

    }
}