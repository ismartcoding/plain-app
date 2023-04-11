package com.ismartcoding.plain.features.theme

import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.ISelectOption
import com.ismartcoding.plain.features.locale.LocaleHelper.getString

enum class AppTheme : ISelectOption {
    SYSTEM, LIGHT, DARK;

    override fun getText(): String {
        return when (this) {
            LIGHT -> getString(R.string.light)
            DARK -> getString(R.string.dark)
            else -> getString(R.string.system)
        }
    }

    override fun isSelected(): Boolean {
        return LocalStorage.appTheme == this
    }
}