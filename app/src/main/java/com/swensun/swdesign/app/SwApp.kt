package com.swensun.swdesign.app

import android.app.Application
import android.support.annotation.NonNull
import android.util.Log
import com.swensun.swdesign.BuildConfig
import com.swensun.swdesign.database.DataBaseManager
import com.swensun.swutils.util.SwUtils
import org.jetbrains.anko.doAsync
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by on 2017/6/6.
 */

class SwApp : Application() {

    val TAG = "SwApp"

    override fun onCreate() {
        super.onCreate()
        SwUtils.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        BaseApplication.application = this
        doAsync {
            DataBaseManager.saveDoubanMovieEntnties()
        }
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, @NonNull message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            FakeCrashLibrary.log(priority, tag ?: "", message)
            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }


}

object FakeCrashLibrary {
    fun log(priority: Int, tag: String, message: String) {
        // TODO add log entry to circular buffer.
    }

    fun logWarning(t: Throwable) {
        // TODO report non-fatal warning.
    }

    fun logError(t: Throwable) {
        // TODO report non-fatal error.
    }
}