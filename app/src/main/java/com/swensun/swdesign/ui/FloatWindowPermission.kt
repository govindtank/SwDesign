package com.yy.xcaller.ui.main.permission

import android.app.Activity
import android.app.AppOpsManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.swensun.swdesign.App.BaseApplication
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader



val TAG = "FloatWindowPermission"

/**
 * Created by macmini on 2017/8/28.
 */
fun checkFloatWindowPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val result = Settings.canDrawOverlays(BaseApplication.application)
        return result
    } else {
        //6.0 以下
        val result = checkRomFloatWindowPermission()
        return result
    }
    return true
}

fun checkRomFloatWindowPermission(): Boolean {

    val brand = Build.MANUFACTURER
    if (brand.contains("Xiaomi")) {
        //check miui permission
        return checkMeizuAndMIUIFloatWindowPermission()

    }
    if (Build.VERSION.SDK_INT <= 19) {
        return true
    }
    when (brand) {
        "Huawei", "HUAWEI" -> checkMeizuAndMIUIFloatWindowPermission()
        "Meizu" -> return checkMeizuAndMIUIFloatWindowPermission()
        "OPPO" -> return true
        "vivo" -> return true
        "Sony" -> return true
        "Letv" -> return true
        "LG" -> return true
    }
    return true
}

fun applyFloatWindowPermission(activity: Activity) {
    if (Build.VERSION.SDK_INT >= 23) {
        applySystemPermission(activity)
        return
    }
    val brand = Build.MANUFACTURER
    when (brand) {
        "Huawei", "HUAWEI" -> applyHuaweiPermission(activity)
        "Xiaomi" -> applyMIUIPermSetting(activity)
        "Meizu" -> applyMeizuPermission(activity)
//        "OPPO" -> openOppoPermSetting(activity)
//        "vivo" -> openVivoPermSetting(activity)
//        "Sony" -> openSonyPermSetting(activity)
//        "Letv" -> openLetvPermSetting(activity)
//        "LG" -> openLGPermSetting(activity)
        else -> applySystemPermission(activity)
    }
}


//-------------- Sysytem start ---------------------

fun  applySystemPermission(activity: Activity) {
    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
    intent.data = Uri.parse("package:" + activity.packageName)
    activity.startActivityForResult(intent, 1)

}

//-------------- Sysytem end ---------------------




//-------------- Meizu MIUI start---------------------

fun checkMeizuAndMIUIFloatWindowPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= 19) {
        return checkMeizuAndMIUIOp(24)
    }
    return true
}

fun checkMeizuAndMIUIOp(op: Int): Boolean {
    if (Build.VERSION.SDK_INT >= 19) {
        val opsManager = BaseApplication.application.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        try {
            val clazz = AppOpsManager::class.java
            val method = clazz.getDeclaredMethod("checkOp",
                    Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, String::class.java)
            return AppOpsManager.MODE_ALLOWED == method.invoke(opsManager, op, Binder.getCallingUid(), BaseApplication.application.packageName)
        } catch (e: Exception) {
            Log.d(TAG, "checkOp", e)
        }
    } else {
        Log.d(TAG, "Below API 19 cannot invoke!")
    }
    return false
}

//-------------- Meizu MIUI end ---------------------

//-------------- Meizu start---------------------

fun applyMeizuPermission(activity: Activity) {
    val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
    intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity")
    intent.putExtra("packageName", activity.packageName)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
}

//-------------- Meizu end ---------------------



//-------------- miui start---------------------

fun getMiuiVersion(activity: Activity): Int {
    val version = getSystemProperty("ro.miui.ui.version.name")
    if (version != null) {
        try {
            return Integer.parseInt(version.substring(1))
        } catch (e: Exception) {
            Log.d(TAG, "get miui version code error, version : " + version!!)
            Log.d(TAG, Log.getStackTraceString(e))
        }

    }
    return -1
}

fun applyMIUIPermSetting(activity: Activity) {
//    val version = getMiuiVersion(activity)
//    Log.d(TAG, "applyMIUIPermSetting" + version)
//    if (version == 6) {
//        goToMiuiPermissionActivity_V6(activity)
//    }else if (version == 8) {
//        goToMiuiPermissionActivity_V8(activity)
//    }
//    openMIUIPermSetting(activity)

}

fun goToMiuiPermissionActivity_V8(activity: Activity) {
    val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
    intent.putExtra("extra_pkgname", activity.getPackageName())
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    if (isIntentAvailable(intent, activity)) {
        activity.startActivity(intent)
    } else {
        Log.d(TAG, "Intent is not available!")
    }
}

fun goToMiuiPermissionActivity_V6(activity: Activity) {
    val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
    intent.putExtra("extra_pkgname", activity.packageName)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (isIntentAvailable(intent, activity)) {
        activity.startActivity(intent)
    } else {
        Log.d(TAG, "Intent is not available!")
    }
}

private fun isIntentAvailable(intent: Intent?, context: Context): Boolean {
    if (intent == null) {
        return false
    }
    return context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size > 0
}


//-------------- miui end---------------------



//-------------- miui start---------------------

fun applyHuaweiPermission(context: Context) {
    try {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //   ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        //   ComponentName comp = new ComponentName("com.huawei.systemmanager",
        //      "com.huawei.permissionmanager.ui.SingleAppActivity");//华为权限管理，跳转到指定app的权限管理位置需要华为接口权限，未解决
        var comp = ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity")//悬浮窗管理页面
        intent.component = comp
        if (getEmuiVersion() == 3.1) {
            //emui 3.1 的适配
            context.startActivity(intent)
        } else {
            //emui 3.0 的适配
            comp = ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity")//悬浮窗管理页面
            intent.component = comp
            context.startActivity(intent)
        }
    } catch (e: SecurityException) {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //   ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        val comp = ComponentName("com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity")//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
        //      ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
        intent.component = comp
        context.startActivity(intent)
        Log.e(TAG, Log.getStackTraceString(e))
    } catch (e: ActivityNotFoundException) {
        /**
         * 手机管家版本较低 HUAWEI SC-UL10
         */
        //   Toast.makeText(MainActivity.this, "act找不到", Toast.LENGTH_LONG).show();
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val comp = ComponentName("com.Android.settings", "com.android.settings.permission.TabItem")//权限管理页面 android4.4
        //   ComponentName comp = new ComponentName("com.android.settings","com.android.settings.permission.single_app_activity");//此处可跳转到指定app对应的权限管理页面，但是需要相关权限，未解决
        intent.component = comp
        context.startActivity(intent)
        e.printStackTrace()
        Log.e(TAG, Log.getStackTraceString(e))
    } catch (e: Exception) {
        //抛出异常时提示信息
        Toast.makeText(context, "进入设置页面失败，请手动设置", Toast.LENGTH_LONG).show()
        Log.e(TAG, Log.getStackTraceString(e))
    }

}

fun getEmuiVersion(): Double {
    try {
        val emuiVersion = getSystemProperty("ro.build.version.emui") ?: return 4.0
        val version = emuiVersion.substring(emuiVersion.indexOf("_") + 1)
        return java.lang.Double.parseDouble(version)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return 4.0
}

//-------------- miui end---------------------

fun getSystemProperty(propName: String): String? {
    val line: String
    var input: BufferedReader? = null
    try {
        val p = Runtime.getRuntime().exec("getprop " + propName)
        input = BufferedReader(InputStreamReader(p.inputStream), 1024)
        line = input!!.readLine()
        input!!.close()
    } catch (ex: IOException) {
        Log.d(TAG, "Unable to read sysprop " + propName, ex)
        return null
    } finally {
        if (input != null) {
            try {
                input!!.close()
            } catch (e: IOException) {
                Log.d(TAG, "Exception while closing InputStream", e)
            }

        }
    }
    return line
}