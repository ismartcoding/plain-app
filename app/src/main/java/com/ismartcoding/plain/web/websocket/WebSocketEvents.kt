package com.ismartcoding.plain.web.websocket

import kotlinx.serialization.Serializable

@Serializable
class WebSocketEvent(val type: EventType, val data: String = "") // Event will be sent to web client

enum class EventType {
    MESSAGE_CREATED,
    MESSAGE_DELETED,
    MESSAGE_UPDATED,
    FEEDS_FETCHED,
    AI_CHAT_REPLIED,
}