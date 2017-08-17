package com.swensun.swdesign.App

import android.app.Application
import com.swensun.swdesign.repository.Repositories

/**
 * Created by macmini on 2017/8/15.
 */
object BaseApplication {
    lateinit var application: Application
    val repository = Repositories()
}