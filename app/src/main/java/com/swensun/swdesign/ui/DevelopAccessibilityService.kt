package com.swensun.swdesign.ui

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent


/**
 * Created by macmini on 2017/11/2.
 */

class DevelopAccessibilityService : AccessibilityService() {

//    fun onAccessibilityEvent(event: AccessibilityEvent) {
//        val eventType = event.eventType
//        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//            val nodes = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.android.settings:id/list")
////            val nodes = rootInActiveWindow.findAccessibilityNodeInfosByText("不锁定屏幕")
//            Logger.d(nodes.size)
//            nodes[0].let { node ->
//                val childCount = node.childCount
//                Logger.d(childCount)
//                //刚好9次能滑动完所有开发者选项， 找到即刻返回。
//                (0..10).forEach {
//                    val tempNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("显示布局边界")
//                    if (tempNodes.size == 1) {
//                        tempNodes[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        startActivity(Intent(this, DevelopHelpActivity::class.java))
//                    }
//                    node.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
//                    startActivity(Intent(this, DevelopHelpActivity::class.java))
//                }
//
//            }
////            nodes.forEach {
////                val node = it.parent
////                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
////            }
//        }
//    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val eventType = event.eventType
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//            when ()
        }
    }

    override fun onInterrupt() {

    }
}
