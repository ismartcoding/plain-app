package com.ismartcoding.plain.ui.audio

import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.features.audio.AudioPlayer
import com.ismartcoding.plain.ui.models.BaseItemModel

open class BaseAudioModel : BaseItemModel() {
    var title: String = ""
    var subtitle: String = ""
    var isPlaying = false

    fun checkIsPlaying(path: String) {
        isPlaying = LocalStorage.audioPlaying?.path == path && AudioPlayer.instance.isPlaying()
    }
}
