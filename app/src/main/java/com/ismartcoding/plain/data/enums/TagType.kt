package com.ismartcoding.plain.data.enums

enum class TagType(val value: Int) {
    DEFAULT(0),
    AUDIO(1),
    VIDEO(2),
    IMAGE(3),
    SMS(4),
    CONTACT(5),
    NOTE(6),
    FEED_ENTRY(7),
    CALL(8),
    BOOK(9),
    AI_CHAT(10);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}