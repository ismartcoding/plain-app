package com.ismartcoding.plain.ui.preview

import com.ismartcoding.lib.extensions.isVideoFast

data class PreviewItem(
    val id: String,
    val uri: String,
) {
    fun itemType(): Int {
        return when {
            uri.isVideoFast() -> ItemType.VIDEO
            else -> ItemType.IMAGE
        }
    }
}

object ItemType {
    const val UNKNOWN = -1
    const val IMAGE = 2
    const val VIDEO = 3
}