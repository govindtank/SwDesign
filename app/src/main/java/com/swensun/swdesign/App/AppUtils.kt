package com.swensun.swdesign.App

import android.content.pm.PackageInfo

/**
 * Created by macmini on 2017/8/15.
 */

val packageInfo: PackageInfo
    get() = BaseApplication.application.packageManager.getPackageInfo(BaseApplication.application.packageName, 0)