package com.ismartcoding.plain.helpers

import com.ismartcoding.lib.extensions.getFilenameExtension
import com.ismartcoding.lib.helpers.NetworkHelper

object UrlHelper {
    private val mediaPathMap = mutableMapOf<String, String>()  // format: <short_path>:<raw_path>

    fun getMediaHttpUrl(path: String): String {
        // cast screen only only supports http in local network and some TV OS only supports simple file name with extension
        val id = System.currentTimeMillis().toString()
        mediaPathMap[id] = path
        val extension = path.getFilenameExtension()
        return "http://${NetworkHelper.getDeviceIP4()}:8080/media/$id.$extension"
    }

    fun getCastCallbackUrl(): String {
        return "http://${NetworkHelper.getDeviceIP4()}:8080/callback/cast"
    }

    fun getMediaPath(id: String): String {
        return mediaPathMap[id] ?: ""
    }
}