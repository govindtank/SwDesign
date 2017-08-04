package com.swensun.swutils.util

import android.content.Context

/**
 * Created by on 2017/5/19.
 */

 class Utils {

    companion object StaticFun{
        private var context: Context? = null

        @JvmStatic fun init(context: Context) {
            Utils.context = context.applicationContext
        }

        @JvmStatic fun getAppContext():Context {
            if (Utils.context != null) return Utils.context as Context
            throw NullPointerException("u should init first")
        }
    }
}