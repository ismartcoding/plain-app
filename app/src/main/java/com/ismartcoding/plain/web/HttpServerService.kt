package com.ismartcoding.plain.web

import android.app.Notification
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.ismartcoding.lib.logcat.LogCat
import com.ismartcoding.plain.Constants
import com.ismartcoding.plain.MainApp
import com.ismartcoding.plain.R
import com.ismartcoding.plain.helpers.NotificationHelper
import io.ktor.server.netty.*
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class HttpServerService : LifecycleService() {
    private lateinit var _httpServer: NettyApplicationEngine

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.ensureDefaultChannel()
        _httpServer = HttpServerManager.createHttpServer(MainApp.instance)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForeground(1, createNotification())
        thread {
            try {
                _httpServer.start(wait = false)
            } catch (ex: Exception) {
                ex.printStackTrace()
                LogCat.e(ex.toString())
            }
        }

        return START_STICKY
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(getString(R.string.app_name))
            setContentText(getString(R.string.api_service_is_running))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setOnlyAlertOnce(true)
            setSilent(true)
            setWhen(System.currentTimeMillis())
            setContentIntent(NotificationHelper.createContentIntent(this@HttpServerService))
            setStyle(NotificationCompat.DecoratedCustomViewStyle())
        }.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        exitProcess(0) // should also kill the app self
    }
}