package com.ismartcoding.plain.web.models

import com.ismartcoding.plain.features.Permission
import com.ismartcoding.plain.features.audio.MediaPlayMode

data class App(
    val usbConnected: Boolean,
    val fileIdToken: String,
    val externalFilesDir: String,
    val deviceName: String,
    val battery: Int,
    val version: String,
    val permissions: List<Permission>,
    val audios: List<PlaylistAudio>,
    val audioMode: MediaPlayMode,
    val audioCurrent: String,
    val allowSensitivePermissions: Boolean,
    val sdcardPath: String,
    val internalStoragePath: String,
    val downloadsDir: String
)