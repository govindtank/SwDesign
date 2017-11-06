package com.swensun.swutils.util

import android.content.Context

/**
 * Created by macmini on 2017/11/6.
 */
object SwUtils {

    var context: Context? = null
    fun init(context: Context) {
        SwUtils.context = context
    }
}