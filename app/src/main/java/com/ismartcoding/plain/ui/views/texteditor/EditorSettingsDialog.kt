package com.ismartcoding.plain.ui.views.texteditor

import android.os.Bundle
import android.view.View
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.DialogEditorSettingsBinding
import com.ismartcoding.plain.ui.BaseBottomSheetDialog
import com.ismartcoding.plain.ui.extensions.setKeyText
import com.ismartcoding.plain.ui.extensions.setSwitch
import com.ismartcoding.plain.ui.views.texteditor.EditorSettingsChangedEvent
import com.ismartcoding.plain.ui.views.texteditor.EditorSettingsType

class EditorSettingsDialog : BaseBottomSheetDialog<DialogEditorSettingsBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lineNumbers.setKeyText(R.string.show_line_numbers)
            .setSwitch(LocalStorage.editorShowLineNumbers, onChanged = { _, isEnabled ->
                LocalStorage.editorShowLineNumbers = isEnabled
                sendEvent(EditorSettingsChangedEvent(EditorSettingsType.LINE_NUMBERS))
            })
        binding.wrapContent.setKeyText(R.string.wrap_content)
            .setSwitch(LocalStorage.editorWrapContent, onChanged = { _, isEnabled ->
                LocalStorage.editorWrapContent = isEnabled
                sendEvent(EditorSettingsChangedEvent(EditorSettingsType.WRAP_CONTENT))
            })

        binding.syntaxHighlight.setKeyText(R.string.syntax_highlight)
            .setSwitch(LocalStorage.editorSyntaxHighlight, onChanged = { _, isEnabled ->
                LocalStorage.editorSyntaxHighlight = isEnabled
                sendEvent(EditorSettingsChangedEvent(EditorSettingsType.SYNTAX_HIGHLIGHT))
            })
    }
}