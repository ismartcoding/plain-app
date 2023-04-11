package com.ismartcoding.plain.ui.chat.views

import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ChatItemNameBinding
import com.ismartcoding.plain.db.DChat
import com.ismartcoding.plain.extensions.formatTime


fun ChatItemNameBinding.initView(chatItem: DChat) {
    name.setTextColor(name.context.getColor(R.color.primary))
    name.text = chatItem.name
    time.setTextColor(time.context.getColor(R.color.secondary))
    time.text = chatItem.createdAt.formatTime()
}