package com.ismartcoding.plain.features.locale

import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.ISelectOption
import com.ismartcoding.plain.features.locale.LocaleHelper.getString
import java.util.*

data class AppLocale(val value: String) : ISelectOption {
    override fun isSelected(): Boolean {
        return LocalStorage.appLocale == value
    }

    override fun getText(): String {
        if (value.isEmpty()) {
            return getString(R.string.system)
        }
        val split = value.split("-")
        val loc = Locale(split[0], if (split.size == 2) split[1] else "")
        return loc.getDisplayLanguage(loc)
    }
}