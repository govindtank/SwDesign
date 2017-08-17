package com.swensun.swdesign.App

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.swensun.swdesign.BuildConfig

/**
 * Created by on 2017/6/6.
 */

class SwApp: Application() {

    val TAG =  "SwApp"

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        BaseApplication.application = this
    }
}