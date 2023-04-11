package com.ismartcoding.lib.extensions

import android.view.Gravity
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu

fun PopupMenu.onItemClick(block: MenuItem.() -> Unit) {
    setOnMenuItemClickListener {
        block(it)
        true
    }
}