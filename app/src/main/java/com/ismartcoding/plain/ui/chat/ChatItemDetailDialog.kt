package com.ismartcoding.plain.ui.chat

import android.os.Bundle
import android.view.View
import com.ismartcoding.plain.db.DChat
import com.ismartcoding.plain.databinding.DialogChatItemDetailBinding
import com.ismartcoding.plain.db.DMessageText
import com.ismartcoding.plain.ui.BaseDialog
import com.ismartcoding.plain.ui.extensions.onBack

class ChatItemDetailDialog(private val chatItem: DChat) : BaseDialog<DialogChatItemDetailBinding>(){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.onBack {
            dismiss()
        }
        binding.content.run {
            val c = chatItem.content.value as? DMessageText
            text = c?.text ?: ""
            //helper.hashtag.isEnabled = true
        }
    }
}