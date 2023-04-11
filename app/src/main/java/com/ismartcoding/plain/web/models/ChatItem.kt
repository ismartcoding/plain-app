package com.ismartcoding.plain.web.models

import com.ismartcoding.plain.db.*
import com.ismartcoding.plain.helpers.FileHelper
import kotlinx.datetime.Instant

data class ChatItem(
    val id: ID,
    val isMe: Boolean,
    val content: String,
    val _content: DMessageContent,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun getData(): ChatItemContent? {
        return when (_content.value) {
            is DMessageImages -> {
                ChatItemContent.MessageImages((_content.value as DMessageImages).items.map { FileHelper.getFileId(it.uri) })
            }
            is DMessageFiles -> {
                ChatItemContent.MessageFiles((_content.value as DMessageFiles).items.map { FileHelper.getFileId(it.uri) })
            }
            else -> {
                null
            }
        }
    }
}

sealed class ChatItemContent() {
    data class MessageImages(val ids: List<String>) : ChatItemContent()
    data class MessageFiles(val ids: List<String>) : ChatItemContent()
}

fun DChat.toModel(): ChatItem {
    return ChatItem(ID(id), isMe, content.toJSONString(), content, createdAt, updatedAt)
}