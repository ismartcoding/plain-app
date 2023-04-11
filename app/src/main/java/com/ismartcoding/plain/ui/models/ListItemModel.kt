package com.ismartcoding.plain.ui.models

import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.IData

open class ListItemModel : BaseItemModel() {
    var startIconId: Int = 0
    var endIconId: Int = 0
    var keyTextMaxLines: Int = 1
    var keyText: String = ""
    var subtitle: CharSequence = ""
    var valueText: String = ""
    var showSwitch: Boolean = false

    fun showSelected(show: Boolean) {
        endIconId = if (show) R.drawable.ic_done else 0
    }

    fun isSelected(): Boolean {
        return endIconId == R.drawable.ic_done
    }

    fun showMore(show: Boolean) {
        endIconId = if (show) R.drawable.ic_chevron_right else 0
    }
}

interface IDataModel {
    val data: IData
}

data class DataModel(override val data: IData) : ListItemModel(), IDataModel
