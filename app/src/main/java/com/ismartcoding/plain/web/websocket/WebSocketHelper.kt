package com.ismartcoding.plain.web.websocket

import android.util.Base64
import com.ismartcoding.lib.helpers.CryptoHelper
import com.ismartcoding.lib.helpers.JsonHelper
import com.ismartcoding.plain.web.HttpServerManager
import io.ktor.websocket.*

object WebSocketHelper {
    suspend fun sendEventAsync(event: WebSocketEvent) {
        val json = JsonHelper.jsonEncode(event)
        HttpServerManager.wsSessions.forEach {
            val token = HttpServerManager.tokenCache[it.clientId]
            if (token != null) {
                it.session.send(encrypt(token, json))
            }
        }
    }

    suspend fun encrypt(token: ByteArray, json: String): String {
        val r = CryptoHelper.aesEncrypt(token, json)
        return Base64.encodeToString(r, Base64.NO_WRAP)
    }
}