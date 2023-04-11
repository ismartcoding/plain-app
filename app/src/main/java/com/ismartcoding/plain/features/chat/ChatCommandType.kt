package com.ismartcoding.plain.features.chat

import com.ismartcoding.plain.features.locale.LocaleHelper

enum class ChatCommandType(val value: String) {
    APP("app"),
    STORAGE("storage"),
    EDUCATION("education"),
    WORK("work"),
    SOCIAL("social"),
    NETWORK("network"),
    EXCHANGE("exchange");

    fun getTitle(): String {
        return LocaleHelper.getString("command_title_$value")
    }

    fun getDescription(): String {
        return LocaleHelper.getString("command_description_$value")
    }

    fun canRefresh(): Boolean{
        return setOf(EXCHANGE, NETWORK).contains(this)
    }

    companion object {
        fun parse(input: String): ChatCommandType? {
            return values().find { it.value == input }
        }
    }
}