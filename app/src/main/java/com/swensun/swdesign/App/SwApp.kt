package com.swensun.swdesign.App

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by on 2017/6/6.
 */

class SwApp: Application() {

    val TAG =  "SwApp"

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}