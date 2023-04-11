package com.ismartcoding.plain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.ismartcoding.plain.MainApp

class PlugInControlReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_USB_STATE) {
            if (intent.extras?.getBoolean("connected") == true) {

            } else {
            }
        }
    }

    companion object {
        const val ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE"

        fun isUSBConnected(): Boolean {
            val intent = MainApp.instance.registerReceiver(null, IntentFilter(ACTION_USB_STATE))
            return intent?.extras?.getBoolean("connected") == true
        }
    }
}