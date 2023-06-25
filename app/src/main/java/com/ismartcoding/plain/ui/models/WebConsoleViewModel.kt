package com.ismartcoding.plain.ui.models

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.lifecycle.ViewModel
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.plain.BuildConfig
import com.ismartcoding.plain.data.preference.WebConsolePreference
import com.ismartcoding.plain.features.HttpServerEnabledEvent
import com.ismartcoding.plain.features.StartHttpServerEvent
import kotlinx.coroutines.CoroutineScope

class WebConsoleViewModel : ViewModel() {
    fun enableWebConsole(context: Context, scope: CoroutineScope, enable: Boolean) {
        WebConsolePreference.put(context, scope, enable)
        sendEvent(HttpServerEnabledEvent(enable))
        if (enable) {
            requestIgnoreBatteryOptimization(context)
            sendEvent(StartHttpServerEvent())
        }
    }

    private fun requestIgnoreBatteryOptimization(context: Context) {
        try {
            val packageName = BuildConfig.APPLICATION_ID
            val pm = context.getSystemService(Application.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                val intent = Intent()
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                context.startActivity(intent)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}