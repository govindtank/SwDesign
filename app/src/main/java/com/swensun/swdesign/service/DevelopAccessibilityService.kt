package com.swensun.swdesign.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.swensun.swdesign.ui.develop.DevelopHelpActivity
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Created by macmini on 2017/11/2.
 */

class DevelopAccessibilityService : AccessibilityService() {

//    fun onAccessibilityEvent(event: AccessibilityEvent) {
//        val eventType = event.eventType
//        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//            val nodes = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.android.settings:id/list")
////            val nodes = rootInActiveWindow.findAccessibilityNodeInfosByText("不锁定屏幕")
//            Timber.d(nodes.size)
//            nodes[0].let { node ->
//                val childCount = node.childCount
//                Timber.d(childCount)
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
            Timber.d(DevelopSettings.isAuto.toString())
            if (DevelopSettings.isAuto) {
                if (DevelopSettings.developData.info == DevelopAction.DEVELOP_SETTINGS) {
                    //开发者选项
                    clickDevelopItem()
                } else {
                    autoSettings()
                }


            }
        }
    }

    private fun autoSettings() {
        val tempRecyclerNodes = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.android.settings:id/list")

        if (tempRecyclerNodes.size == 1) {
            tempRecyclerNodes[0].let { listNode ->
                when (DevelopSettings.developData.info) {
                    DevelopAction.USB_DEBUG_ONE, DevelopAction.USB_DEBUG_TWO -> {
                        //usb 调试
                        clickUSBItem(listNode)
                    }
                    DevelopAction.DEBUG_GPU_OVERDRAW -> {
                        //二级菜单
                        clickGPUIOverdrawItem(listNode)
                    }
                    else -> {
                        clickNormalItem(listNode)
                    }
                }

            }
        }
    }

    private fun clickGPUIOverdrawItem(listNode: AccessibilityNodeInfo) {
        (0..10).forEach {
            val tempSwitchNodes = rootInActiveWindow.findAccessibilityNodeInfosByText(DevelopSettings.developData.info)
            if (tempSwitchNodes.size == 1) {
                val parentNode = tempSwitchNodes[0].parent
                parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Thread.sleep(100)
                //弹出对话框
                val checkNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("关闭")
                checkNodes?.let {
                    if (it[0].isChecked) {
                        val overdrawNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("显示过度绘制区域")
                        overdrawNodes?.let {
                            it[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            toast("已打开 ${DevelopSettings.developData.info}")
                        }
                    } else {
                        it[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        toast("已关闭 ${DevelopSettings.developData.info}")
                    }
                    goToDevelopHelpActivity()
                    return
                }
            } else {
                listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            }
        }
        goToDevelopHelpActivity()
    }

    private fun clickDevelopItem() {
        val switchNodes = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.android.settings:id/switch_bar")
        if (switchNodes.size == 1) {
            switchNodes[0].let {
                if (it.text == "开启") {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    toast("已关闭 开发者选项")
                    goToDevelopHelpActivity()
                } else {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Thread.sleep(100)
                    val confirmNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("确定")
                    if (confirmNodes.size == 1) {
                        confirmNodes[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        toast("已打开 开发者选项")
                        goToDevelopHelpActivity()
                    }
                }
            }
        }
    }

    private fun clickUSBItem(listNode: AccessibilityNodeInfo) {
        val usbActions = arrayListOf(DevelopAction.USB_DEBUG_ONE, DevelopAction.USB_DEBUG_TWO)
        usbActions.forEach one@{ action ->
            (0..10).forEach {
                val tempSwitchNodes = rootInActiveWindow.findAccessibilityNodeInfosByText(action)
                if (tempSwitchNodes.size == 1) {
                    val parentNode = tempSwitchNodes[0].parent
                    val parentChild = tempSwitchNodes[0].parent.childCount
                    if (parentChild != 3) return@one
                    val switchNode = parentNode.getChild(2)
                    if (switchNode.text == "ON") {
                        parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        toast("已关闭 USB调试")
                        goToDevelopHelpActivity()
                        return
                    } else {
                        parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Thread.sleep(100)
                        val confirmNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("确定")
                        if (confirmNodes.size == 1) {
                            confirmNodes[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            toast("已打开 USB调试")
                            goToDevelopHelpActivity()
                            return
                        }
                    }
                } else {
                    listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                }
            }
            goToDevelopHelpActivity()
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
                        toast("已打开 ${DevelopSettings.developData.info}")
                    } else {
                        toast("已关闭 ${DevelopSettings.developData.info}")
                    }
                }

                parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                goToDevelopHelpActivity()
                return
            } else {
                listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            }
        }
        goToDevelopHelpActivity()

    }

    fun goToDevelopHelpActivity() {
        startActivity(Intent(this, DevelopHelpActivity::class.java))
        DevelopSettings.isAuto = false
        return
    }


    override fun onInterrupt() {

    }
}
