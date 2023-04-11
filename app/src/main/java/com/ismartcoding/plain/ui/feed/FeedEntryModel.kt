package com.ismartcoding.plain.ui.feed

import androidx.databinding.BaseObservable
import com.ismartcoding.lib.brv.item.ItemCheckable
import com.ismartcoding.plain.db.DFeed
import com.ismartcoding.plain.db.DFeedEntry
import com.ismartcoding.plain.ui.models.IDataModel

class FeedEntryModel(override val data: DFeedEntry, val feed: DFeed?) : IDataModel, ItemCheckable, BaseObservable() {
    override var toggleMode = false
    override var isChecked = false
    var title: String = ""
    var image: String = ""
    var subtitle: String = ""
}
