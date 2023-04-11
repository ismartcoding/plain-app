package com.ismartcoding.plain.ui.chat.views

import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ChatItemTextBinding
import com.ismartcoding.plain.db.*
import com.ismartcoding.lib.extensions.*
import com.ismartcoding.plain.ui.WebDialog
import com.ismartcoding.plain.ui.extensions.initTheme

fun ChatItemTextBinding.initView(chatItem: DChat) {
    text.run {
        // https://stackoverflow.com/questions/15836306/can-a-textview-be-selectable-and-contain-links
        setTextColor(this.context.getColor(R.color.primary))
        setLinkTextColor(this.context.getColor(R.color.purple_light))
        setTextWithLinkSupport((chatItem.content.value as DMessageText).text) {
            WebDialog(it).show()
        }
        // hashtagColor = context.getColor(R.color.color_blue)
        // isHashtagEnabled = true
    }
}
