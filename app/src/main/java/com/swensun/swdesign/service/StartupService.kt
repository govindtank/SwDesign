package com.swensun.swdesign.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import timber.log.Timber


/**
 * Created by macmini on 2017/9/1.
 */

class StartupService : Service() {


    companion object {
        val TAG = "StartupService"
    }
    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onBind(intent: Intent): IBinder? {
        Timber.d("onBind")
        return null
    }

    override fun onDestroy() {
        Timber.e("onDestroy")
        super.onDestroy()
    }
}