package com.swensun.swdesign.service

import android.provider.Settings

/**
 * Created by macmini on 2017/11/3.
 */
object DevelopAction {
    val DEVELOP_SETTINGS = "开发者选项" //com.android.settings:id/switch_bar
    val USB_DEBUG_ONE = "USB调试"
    val USB_DEBUG_TWO = "Android 调试"
    val STAY_AWAKE = "不锁定屏幕"
    val SHOW_TAPS = "显示点按操作反馈"
    val POINT_LOCATION = "指针位置"
    val SHOW_SURFACE_UPDATE = "更新时全部闪烁"
    val SHOW_LAYOUT_BOUNDS = "显示布局边界"
    val DEBUG_GPU_OVERDRAW = "调试 GPU 过度绘制"
    val STRICT_MODE_ENABLED = "启动严格模式"
    val SHOW_CPU_USAGE = "显示CPU信息"
    val PROFILE_GPU_RENDERING = "GPU 呈现模式分析"
}

enum class OpenMark {
    NOTSET, FALSE, TRUE
}

class DevelopData {
    var info = DevelopAction.DEVELOP_SETTINGS
    var detail = "开发者选项"
    var settingsKey = Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
    var openable = OpenMark.NOTSET
}

object DevelopSettings {
    var position = -1
    var itemSwitchOn = OpenMark.NOTSET
    var developData = DevelopData()
    var isAuto = false
}

