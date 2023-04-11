package com.ismartcoding.plain.ui.chat.views

import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.ChatItemWorkBinding
import com.ismartcoding.plain.ui.book.BooksDialog
import com.ismartcoding.plain.ui.extensions.initTheme
import com.ismartcoding.plain.ui.extensions.setClick
import com.ismartcoding.plain.ui.extensions.setKeyText
import com.ismartcoding.plain.ui.extensions.showMore
import com.ismartcoding.plain.ui.feed.FeedEntriesDialog
import com.ismartcoding.plain.ui.note.NotesDialog

fun ChatItemWorkBinding.initView() {
    notes
        .initTheme()
        .setKeyText(R.string.notes)
        .showMore()
        .setClick {
            NotesDialog().show()
        }

    books.initTheme()
        .setKeyText(R.string.books)
        .showMore()
        .setClick {
            BooksDialog().show()
        }

    feeds.initTheme()
        .setKeyText(R.string.feeds)
        .showMore()
        .setClick {
            FeedEntriesDialog().show()
        }
}