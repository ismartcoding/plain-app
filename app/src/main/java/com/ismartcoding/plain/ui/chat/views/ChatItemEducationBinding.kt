package com.ismartcoding.plain.ui.chat.views

import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ChatItemEducationBinding
import com.ismartcoding.plain.ui.endict.VocabulariesDialog
import com.ismartcoding.plain.ui.extensions.initTheme
import com.ismartcoding.plain.ui.extensions.setClick
import com.ismartcoding.plain.ui.extensions.setKeyText
import com.ismartcoding.plain.ui.extensions.showMore

fun ChatItemEducationBinding.initView() {
    memorizeWords
        .initTheme()
        .setKeyText(R.string.memorize_words)
        .showMore()
        .setClick {
            VocabulariesDialog().show()
        }
}