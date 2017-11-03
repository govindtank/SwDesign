package com.swensun.swdesign.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.orhanobut.logger.Logger


/**
 * Created by macmini on 2017/9/1.
 */

class StartupService : Service() {


    companion object {
        val TAG = "StartupService"
    }
    override fun onCreate() {
        super.onCreate()
        Logger.d("onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Logger.d("onStartCommand")
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onBind(intent: Intent): IBinder? {
        Logger.d("onBind")
        return null
    }

    override fun onDestroy() {
        Logger.e("onDestroy")
        super.onDestroy()
    }
}