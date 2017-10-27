package com.swensun.swdesign.ui

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import com.swensun.swdesign.ui.ins.isAutoLike
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by macmini on 2017/10/26.
 */

private var isFirstCalled = true
private var page = 1
val eventSubject: PublishSubject<AccessibilityEvent> = PublishSubject.create()
var dis: Disposable? = null
var time = 0

class MyAccessibilityService: AccessibilityService() {



    companion object {
        val TAG = "MyAccessibilityService"
    }

//    com.instagram.android
//    com.instagram.android/.activity.MainTabActivity
//    com.instagram.android:id/row_feed_button_comment
//    com.instagram.android:id/row_feed_button_like


    override fun onServiceConnected() {
        super.onServiceConnected()
        Logger.d("connect to accessibilityService")
        initSubject()

    }

    private fun initSubject() {

    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.source == null) { return; }
        val eventType = event.eventType
        val rootInfo = rootInActiveWindow
        Log.d(TAG, eventType.toString())
        if (eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            if (!isAutoLike) {
                return
            }
            if (containListView(rootInfo)) {
                val listNodes = event.source.findAccessibilityNodeInfosByViewId("android:id/list")
                listNodes?.let {
                    Log.d(TAG, it.size.toString())
                    if (it.size == 1) {
                        val listNote = listNodes[0]
                        while (listNote.isScrollable) {
                            applyAutoAttention(listNote)
                            listNote.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                            Thread.sleep(1000)
                        }
                    }
                }
            }
        }
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val listNodes = event.source.findAccessibilityNodeInfosByText("停止关注")
            listNodes.forEach {
                if (it.text == "停止关注") {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Thread.sleep(100)
                }
            }
        }
    }

    private fun containListView(rootInfo: AccessibilityNodeInfo?): Boolean {
        if (rootInfo == null) return false
        val childCount = rootInfo.childCount
//        Log.d(TAG, rootInfo.className.toString())
        when(rootInfo.className.toString()) {
            "android.widget.ListView" -> {
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

    fun applyAutoAttention(listNode: AccessibilityNodeInfo?) {
        if (listNode == null) return
        val tvNodes = listNode.findAccessibilityNodeInfosByText("关注")
        tvNodes.forEach {
            if (it.text == "关注") {
                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "click" + time)
                Thread.sleep(500)
            } else if (it.text == "已关注") {
                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Thread.sleep(200)
                //取消点赞
                val stopNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("停止关注")
                stopNodes.forEach {
                    if (it.text == "停止关注") {
                        it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Thread.sleep(200)
                    }
                }
            }
        }
    }
}