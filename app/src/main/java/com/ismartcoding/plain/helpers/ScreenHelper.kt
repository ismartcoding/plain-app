package com.ismartcoding.plain.helpers

import android.annotation.SuppressLint
import com.ismartcoding.lib.extensions.getSystemScreenTimeout
import com.ismartcoding.lib.extensions.setSystemScreenTimeout
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.MainApp
import com.ismartcoding.plain.features.Permission

object ScreenHelper {
    @SuppressLint("CheckResult")
    fun keepScreenOn(enable: Boolean): Boolean {
        val contentResolver = MainApp.instance.contentResolver
        if (Permission.WRITE_SETTINGS.can()) {
            LocalStorage.keepScreenOn = enable
            if (enable) {
                LocalStorage.systemScreenTimeout = contentResolver.getSystemScreenTimeout()
                contentResolver.setSystemScreenTimeout(Int.MAX_VALUE)
            } else {
                contentResolver.setSystemScreenTimeout(if (LocalStorage.systemScreenTimeout > 0) LocalStorage.systemScreenTimeout else 5000 * 60) // default 5 minutes
            }
            return true
        } else {
            Permission.WRITE_SETTINGS.grant()
        }
        return false
    }
}