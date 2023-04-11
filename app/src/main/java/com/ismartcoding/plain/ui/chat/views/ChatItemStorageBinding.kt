package com.ismartcoding.plain.ui.chat.views

import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ChatItemStorageBinding
import com.ismartcoding.plain.ui.audio.AudiosDialog
import com.ismartcoding.plain.ui.extensions.*
import com.ismartcoding.plain.ui.file.FilesDialog
import com.ismartcoding.plain.ui.image.ImagesDialog
import com.ismartcoding.plain.ui.video.VideosDialog

fun ChatItemStorageBinding.initView() {
    images.initTheme()
        .setKeyText(R.string.images)
        .showMore()
        .setClick {
            ImagesDialog().show()
        }

    audios.initTheme()
        .setKeyText(R.string.audios)
        .showMore()
        .setClick {
            AudiosDialog().show()
        }

    videos.initTheme()
        .setKeyText(R.string.videos)
        .showMore()
        .setClick {
            VideosDialog().show()
        }

    files.initTheme()
        .setKeyText(R.string.files)
        .showMore()
        .setClick {
            FilesDialog().show()
        }
}

