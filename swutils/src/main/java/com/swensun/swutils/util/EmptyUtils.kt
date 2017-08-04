package com.swensun.swutils.util

import java.lang.reflect.Array

/**
 * Created by on 2017/5/19.
 */
class EmptyUtils {

    companion object {

        @JvmStatic
        fun isEmpty(obj: Any): Boolean{
            if (obj == null) {
                return true
            }
            if (obj is String && obj.toString().length == 0) {
                return true
            }
            if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
                return true
            }
            if (obj is Collection<*> && obj.isEmpty()) {
                return true
            }
            if (obj is Map<*, *> && obj.isEmpty()) {
                return true
            }
            return false
        }

        @JvmStatic
        fun isNotEmpty(obj: Any): Boolean {
            return !isEmpty(obj)
        }
    }
}