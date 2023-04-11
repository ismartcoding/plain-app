package com.ismartcoding.plain.ui.chat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.lib.softinput.setWindowSoftInput
import com.ismartcoding.plain.db.DChat
import com.ismartcoding.plain.features.UpdateMessageEvent
import com.ismartcoding.plain.databinding.DialogEditChatTextBinding
import com.ismartcoding.plain.db.DMessageContent
import com.ismartcoding.plain.db.DMessageText
import com.ismartcoding.plain.db.DMessageType
import com.ismartcoding.plain.ui.BaseBottomSheetDialog
import com.ismartcoding.plain.ui.extensions.setSafeClick

class EditChatTextDialog(val chatItem: DChat) : BaseBottomSheetDialog<DialogEditChatTextBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val c = chatItem.content.value as? DMessageText
        binding.input.setText(c?.text ?: "")
        binding.send.setSafeClick {
            val content = binding.input.text.toString()
            if (content.isEmpty()) {
                return@setSafeClick
            }
            chatItem.content = DMessageContent(DMessageType.TEXT.value, DMessageText(content))
            sendEvent(UpdateMessageEvent(chatItem))
            dismiss()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // fix the glitch bug
            setWindowSoftInput(binding.container)
        }, 200)
    }
}