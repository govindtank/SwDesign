package com.swensun.swdesign.ui

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger

/**
 * Created by macmini on 2017/10/26.
 */
class MyAccessibilityService: AccessibilityService() {

    companion object {
        val TAG = "MyAccessibilityService"
    }
    var listNote: AccessibilityNodeInfo? =null
    var isLiking = false

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
        Log.d(TAG, eventType.toString())
        val rootInfo = rootInActiveWindow
//        Log.d(TAG,  containListView(rootInfo).toString())
        if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
//            if (isLiking) {
//                return
//            }
//            if (containListView(rootInfo)) {
//                isLiking = true
//                listNote?.let { note ->
//                    Observable.interval(1, TimeUnit.SECONDS)
//                            .take(20)
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe {
//                                applyAutoLike(note)
//                                if (note.isScrollable) {
//                                    note.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
//                                }
//                            }
//                }
//            }
//            applyAutoLike(event)
            if (containListView(rootInfo)) {
                applyAutoLike(listNote!!)
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

    private fun applyAutoLike(noteInfo: AccessibilityNodeInfo) {
//        val noteInfo = event?.source!!
        val childCount = noteInfo.childCount
        Log.d(TAG, noteInfo.className.toString())
        if (noteInfo.className.toString() == "android.widget.ImageView") {
            Log.d(TAG, "111111111")
            Log.d(TAG, noteInfo.contentDescription.toString())
            return
        }
        (0 until childCount).forEach {
            val childInfo = noteInfo.getChild(it)
            applyAutoLike(childInfo)
        }
//        val infos = noteInfo.findAccessibilityNodeInfosByViewId("com.instagram.android:id/row_feed_button_like")
//        infos.forEach {
//            if (it.contentDescription == "赞了") {
//                Log.d(TAG, "赞了")
//            } else {
//                Log.d(TAG, "没赞")
////                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//            }
//            Log.d(TAG, it.toString())
//        }
//        noteInfo.findAccessibilityNodeInfosByViewId("com.instagram.android:id/row_feed_button_like")
    }
}