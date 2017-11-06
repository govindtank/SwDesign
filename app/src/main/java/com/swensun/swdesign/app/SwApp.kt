package com.swensun.swdesign.app

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.swensun.swdesign.BuildConfig
import com.swensun.swdesign.database.DataBaseManager
import com.swensun.swutils.util.SwUtils
import org.jetbrains.anko.doAsync

/**
 * Created by on 2017/6/6.
 */

class SwApp: Application() {

    val TAG =  "SwApp"

    override fun onCreate() {
        super.onCreate()
        SwUtils.init(this)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        BaseApplication.application = this
        doAsync {
            DataBaseManager.saveDoubanMovieEntnties()
        }
    }
}