package com.ismartcoding.plain.ui.models

import androidx.databinding.BaseObservable

open class MenuItemModel(val data: Any? = null) : BaseObservable() {
    var title: String = ""
    var iconId: Int = 0
    var count: Int = 0
    var isChecked: Boolean = false
}
