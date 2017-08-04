package com.swensun.swutils.util

import android.content.Intent

/**
 * Created by on 2017/5/19.
 */

 class ActivityUtils {

    companion object {

        fun <JvmStatic> isActivityExists(packageName: String, className: String): Boolean {
            val intent = Intent()
            intent.setClassName(packageName, className)
            return !(Utils.getAppContext().packageManager.resolveActivity(intent, 0) == null ||
                    intent.resolveActivity(Utils.getAppContext().packageManager) == null ||
                    Utils.getAppContext().packageManager.queryIntentActivities(intent, 0).size == 0)
        }

        fun <JvmStatic> getLauncherActivity(packageName: String): String {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            var pm = Utils.getAppContext().packageManager
            var infos = pm.queryIntentActivities(intent, 0)
            for(info in infos) {
                if (info.activityInfo.packageName.equals(packageName)) return info.activityInfo.name
            }
            return "no" + packageName
        }
    }
}