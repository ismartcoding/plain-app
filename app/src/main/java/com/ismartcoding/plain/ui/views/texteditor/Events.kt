package com.ismartcoding.plain.ui.views.texteditor

enum class EditorSettingsType {
    WRAP_CONTENT,
    LINE_NUMBERS,
    SYNTAX_HIGHLIGHT,
}

class EditorSettingsChangedEvent(val type: EditorSettingsType)

class EditorInsertImageEvent(val url: String, val description: String = "", val width: String = "")
