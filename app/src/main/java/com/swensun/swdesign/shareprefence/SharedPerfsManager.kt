package com.swensun.swdesign.shareprefence

import android.content.Context
import com.swensun.swdesign.app.BaseApplication
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by macmini on 2017/9/1.
 */
@Singleton
class SharedPerfsManager @Inject constructor() {




    val mSharedPreferences = BaseApplication.application.getSharedPreferences("parrot", Context.MODE_PRIVATE)

    fun put(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    fun put(key: String, value: Float) {
        mSharedPreferences.edit().putFloat(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    operator fun get(key: String, defaultValue: String = ""): String {
        return mSharedPreferences.getString(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Int = -1): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Float = -1f): Float {
        return mSharedPreferences.getFloat(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    fun deleteSavedData(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }
}