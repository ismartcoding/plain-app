package com.ismartcoding.plain.web.models

import com.ismartcoding.plain.db.*
import kotlinx.datetime.Instant

data class AIChat(
    val id: ID,
    val parentId: String,
    val isMe: Boolean,
    val content: String,
    val type: Int,
    val createdAt: Instant,
    val updatedAt: Instant,
)

fun DAIChat.toModel(): AIChat {
    return AIChat(ID(id), parentId, isMe, content, type, createdAt, updatedAt)
}

data class AIChatConfig(
    val chatGPTApiKey: String,
)
