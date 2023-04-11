package com.ismartcoding.plain.features.feed

import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.data.ISelectOption
import com.ismartcoding.plain.helpers.FormatHelper

class FeedAutoRefreshInterval(val value: Int) : ISelectOption {
    override fun isSelected(): Boolean {
        return value == LocalStorage.feedAutoRefreshInterval
    }

    override fun getText(): String {
        return FormatHelper.formatSeconds(value)
    }

}