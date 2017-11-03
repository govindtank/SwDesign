package com.swensun.swdesign.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import com.swensun.swdesign.ui.develop.DevelopHelpActivity

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
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Logger.d(DevelopSettings.isAuto)
            if (DevelopSettings.isAuto) {
                autoSettings()
            }
        }
    }

    private fun autoSettings() {
        val tempRecyclerNodes = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.android.settings:id/list")

        if (tempRecyclerNodes.size == 1) {
            tempRecyclerNodes[0].let { listNode ->


                when (DevelopSettings.developData.info) {
                    DevelopAction.DEVELOP_SETTINGS -> {
                        //开发者选项
                        startActivity(Intent(this, DevelopHelpActivity::class.java))
                    }
                    DevelopAction.USB_DEBUG_ONE, DevelopAction.USB_DEBUG_TWO -> {
                        //开发者选项
                        startActivity(Intent(this, DevelopHelpActivity::class.java))
                    }
                    DevelopAction.PROFILE_GPU_RENDERING, DevelopAction.DEBUG_GPU_OVERDRAW -> {
                        //二级菜单
                        startActivity(Intent(this, DevelopHelpActivity::class.java))
                    }
                    else -> {
                        clickNormalItem(listNode)
                    }
                }

            }
        }
    }

    private fun clickNormalItem(listNode: AccessibilityNodeInfo) {
        (0..9).forEach {
            val tempSwitchNodes = rootInActiveWindow.findAccessibilityNodeInfosByText(DevelopSettings.developData.info)
            if (tempSwitchNodes.size == 1) {
                val parentNode = tempSwitchNodes[0].parent
                val switchNode = tempSwitchNodes[0].parent.getChild(2)
                //save info
                switchNode?.let {
                    if (it.text == "OFF") {
                        DevelopSettings.itemSwitchOn = OpenMark.TRUE
                    } else {
                        DevelopSettings.itemSwitchOn = OpenMark.FALSE
                    }
                }

                parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                startActivity(Intent(this, DevelopHelpActivity::class.java))
                return
            } else {
                listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            }
        }
        startActivity(Intent(this, DevelopHelpActivity::class.java))
    }


    override fun onInterrupt() {

    }
}
