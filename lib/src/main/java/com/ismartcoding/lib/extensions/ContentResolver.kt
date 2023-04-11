package com.ismartcoding.lib.extensions

import android.content.ContentResolver
import android.provider.Settings
import android.util.Log
import androidx.annotation.CheckResult
import com.ismartcoding.lib.logcat.LogCat

fun ContentResolver.getSystemScreenTimeout(): Int {
    return Settings.System.getInt(this, Settings.System.SCREEN_OFF_TIMEOUT)
}

@CheckResult
fun ContentResolver.setSystemScreenTimeout(timeout: Int): Boolean {
    return try {
        Settings.System.putInt(this, Settings.System.SCREEN_OFF_TIMEOUT, timeout)
        true
    } catch (e: SecurityException) {
        LogCat.e("Error writing screen timeout", e)
        false
    }
}

