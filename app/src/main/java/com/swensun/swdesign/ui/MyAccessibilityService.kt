package com.swensun.swdesign.ui

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import com.swensun.swdesign.ui.ins.InsAction
import com.swensun.swdesign.ui.ins.InsSetting
import com.swensun.swdesign.ui.ins.InstagramProActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by macmini on 2017/10/26.
 */

var listNode: AccessibilityNodeInfo? = null
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
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (!InsSetting.isAutoLike) {
                return
            }
            if (observer == null) {
                if (containListView(rootInfo)) {
                    listNode?.let { note ->
                        observer = Observable.interval(InsSetting.interval.toLong(), TimeUnit.SECONDS).take(InsSetting.page.toLong())
                                .doOnSubscribe {
                                    Log.d(MyAccessibilityService.TAG, "begin")
                                }
                                .doOnComplete {
                                    observer = null
                                    startActivity(Intent(this, InstagramProActivity::class.java))
                                }
                        if (InsSetting.action == InsAction.LIKE) {
                            //点赞
                            observer!!.subscribe {
                                Log.d(TAG, it.toString())
                                if (it != (InsSetting.page - 1).toLong()) {
                                    applyAutoLike(note)
                                    note.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                                }
                            }
                        } else {
                            //关注和取关
                            observer!!.subscribe {
                                if (it != (InsSetting.page - 1).toLong()) {
                                    applyAutoAttention(note)
                                    note.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                                }
                            }
                        }
                    }

//                    val listNodes =rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/list")
//                    Log.d(TAG, listNodes.size.toString())
//                    listNodes?.let {
//                        if (it.size == 1) {
//                            val listNote = listNodes[0]
//                            observer = Observable.interval(InsSetting.interval.toLong(), TimeUnit.SECONDS).take(InsSetting.page.toLong())
//                                    .doOnSubscribe {
//                                        Log.d(MyAccessibilityService.TAG, "begin")
//                                    }
//                                    .doOnComplete {
//                                        observer = null
//                                        startActivity(Intent(this, InstagramProActivity::class.java))
//                                    }
//                            if (InsSetting.action == InsAction.LIKE) {
//                                //点赞
//                                observer!!.subscribe {
//                                    Log.d(TAG, it.toString())
//                                    if (it != (InsSetting.page - 1).toLong()) {
//                                        applyAutoLike(listNote)
//                                        listNote.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
//                                    }
//                                }
//
//                            } else {
//                                //关注和取关
//                                observer!!.subscribe {
//                                    if (it != (InsSetting.page - 1).toLong()) {
//                                        applyAutoAttention(listNote)
//                                        listNote.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }

    private fun containListView(rootInfo: AccessibilityNodeInfo?): Boolean {
        if (rootInfo == null) return false
        val childCount = rootInfo.childCount
        when(rootInfo.className.toString()) {
            "android.widget.ListView" -> {
                listNode = rootInfo
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

    private fun applyAutoLike(listNode: AccessibilityNodeInfo?) {
        if (listNode == null) return
        val imageNodes = listNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/row_feed_button_like")
        imageNodes.forEach {
            if (it.contentDescription == "赞") {
                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "click like")
                Thread.sleep(200)
            }
        }
        val attentionNodes = listNode.findAccessibilityNodeInfosByText("关注")
//        attentionNodes.forEach {
//            if (it.text == "关注") {
//                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                Log.d(TAG, "click attention")
//                Thread.sleep(200)
//            }
//        }
    }

    fun applyAutoAttention(listNode: AccessibilityNodeInfo?) {
        if (listNode == null) return
        val tvNodes = listNode.findAccessibilityNodeInfosByText("关注")
        tvNodes.forEach {
            if (it.text == "关注") {
                if (InsSetting.action == InsAction.ATTENTION) {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "click")
                    Thread.sleep(800)
                }
            } else if (it.text == "已关注") {
                if (InsSetting.action == InsAction.NOTATTENTION) {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "click")
                    Thread.sleep(100)
                    val listNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("停止关注")
                    listNodes.forEach {
                        if (it.text == "停止关注") {
                            it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Thread.sleep(500)
                        }
                    }
                }
            }
        }
    }
}