package com.ismartcoding.plain.ui.feed

import android.os.Bundle
import android.view.View
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.DialogFeedSettingsBinding
import com.ismartcoding.plain.features.feed.FeedAutoRefreshInterval
import com.ismartcoding.plain.features.locale.LocaleHelper
import com.ismartcoding.plain.helpers.FormatHelper
import com.ismartcoding.plain.ui.BaseBottomSheetDialog
import com.ismartcoding.plain.ui.SelectOptionsDialog
import com.ismartcoding.plain.ui.extensions.*
import com.ismartcoding.plain.workers.FeedFetchWorker

class FeedSettingsDialog : BaseBottomSheetDialog<DialogFeedSettingsBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.autoRefresh.setKeyText(R.string.auto_refresh_feeds)
            .setSwitch(LocalStorage.feedAutoRefresh, onChanged = { _, isEnabled ->
                LocalStorage.feedAutoRefresh = isEnabled
                if (isEnabled) {
                    FeedFetchWorker.startRepeatWorker()
                } else {
                    FeedFetchWorker.cancelRepeatWorker()
                }
            })
        binding.autoRefreshInterval.setKeyText(R.string.auto_refresh_interval)
            .setValueText(FormatHelper.formatSeconds(LocalStorage.feedAutoRefreshInterval))
            .showMore()
            .setClick {
                val options = setOf(900, 1800, 3600, 7200, 21600, 43200, 86400).map { FeedAutoRefreshInterval(it) }
                SelectOptionsDialog(LocaleHelper.getString(R.string.auto_refresh_interval), options) {
                    LocalStorage.feedAutoRefreshInterval = it.value
                    binding.autoRefreshInterval.setValueText(FormatHelper.formatSeconds(LocalStorage.feedAutoRefreshInterval))
                }.show()
            }

        binding.autoRefreshOnlyWifi.setKeyText(R.string.auto_refresh_only_over_wifi)
            .setSwitch(LocalStorage.feedAutoRefreshOnlyWifi, onChanged = { _, isEnabled ->
                LocalStorage.feedAutoRefreshOnlyWifi = isEnabled
            })
    }
}