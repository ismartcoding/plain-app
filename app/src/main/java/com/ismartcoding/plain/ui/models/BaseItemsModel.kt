package com.ismartcoding.plain.ui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseItemsModel : ViewModel() {
    val dismissed = MutableLiveData(false)
    val toggleMode = MutableLiveData(false)
    var offset = 0
    var limit: Int = 1000
    var searchQ: String = ""
    var total: Int = 0
}
