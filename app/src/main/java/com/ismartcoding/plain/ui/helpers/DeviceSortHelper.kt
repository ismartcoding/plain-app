package com.ismartcoding.plain.ui.helpers

import android.view.Menu
import android.view.MenuItem
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.features.device.DeviceSortBy

object DeviceSortHelper {
    fun getSelectedSortItem(menu: Menu): MenuItem {
        return when (LocalStorage.deviceSortBy) {
            DeviceSortBy.NAME_ASC -> {
                menu.findItem(R.id.sort_name_asc)
            }
            DeviceSortBy.NAME_DESC -> {
                menu.findItem(R.id.sort_name_desc)
            }
            DeviceSortBy.IP_ADDRESS -> {
                menu.findItem(R.id.sort_ip_address)
            }
            DeviceSortBy.LAST_ACTIVE -> {
                menu.findItem(R.id.sort_last_active_desc)
            }
        }
    }
}