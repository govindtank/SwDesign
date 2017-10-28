package com.swensun.swdesign.ui

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import com.swensun.swdesign.ui.ins.InstagramProActivity
import com.swensun.swdesign.ui.ins.isAutoLike
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by macmini on 2017/10/26.
 */


var time = 0
var observer: Observable<Long>? = null


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
//        Log.d(TAG, eventType.toString())
        if (eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            if (!isAutoLike) {
                return
            }

            if (observer == null) {
                if (containListView(rootInfo)) {
                    val listNodes = event.source.findAccessibilityNodeInfosByViewId("android:id/list")
                    listNodes?.let {
                        if (it.size == 1) {
                            val listNote = listNodes[0]
                            observer = Observable.interval(10, TimeUnit.SECONDS).take(3)
                            observer!!.doOnSubscribe {
                                Log.d(MyAccessibilityService.TAG, "begin")
                            }.doOnComplete {
                                observer = null
                                startActivity(Intent(this, InstagramProActivity::class.java))
                            }.subscribe {
                                Log.d(TAG, it.toString())
                                if (it != 2L) {
                                    applyAutoAttention(listNote)
                                    listNote.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                                }
                            }
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
                Thread.sleep(800)
            } else if (it.text == "已关注") {
                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Thread.sleep(400)
                //取消点赞
                val stopNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("停止关注")
                stopNodes.forEach {
                    if (it.text == "停止关注") {
                        it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Thread.sleep(400)
                    }
                }
            }
        }
    }
}