package com.ismartcoding.plain.ui.chat.views

import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ChatItemSocialBinding
import com.ismartcoding.plain.ui.call.CallsDialog
import com.ismartcoding.plain.ui.contact.ContactsDialog
import com.ismartcoding.plain.ui.extensions.*
import com.ismartcoding.plain.ui.sms.SmsDialog

fun ChatItemSocialBinding.initView() {
    messages
        .initTheme()
        .setKeyText(R.string.messages)
        .showMore()
        .setClick {
            SmsDialog().show()
        }

    contacts
        .initTheme()
        .setKeyText(R.string.contacts)
        .showMore()
        .setClick {
            ContactsDialog().show()
        }

    calls
        .initTheme()
        .setKeyText(R.string.calls)
        .showMore()
        .setClick {
            CallsDialog().show()
        }
}

