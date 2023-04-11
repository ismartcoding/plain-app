package com.ismartcoding.plain.ui.scan

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.ismartcoding.lib.brv.utils.*
import com.ismartcoding.lib.extensions.cut
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.DialogScanHistoryBinding
import com.ismartcoding.plain.ui.BaseBottomSheetDialog
import com.ismartcoding.plain.ui.extensions.initMenu
import com.ismartcoding.plain.ui.extensions.onSearch
import com.ismartcoding.plain.ui.models.ListItemModel


class ScanHistoryDialog(val callback: () -> Unit) : BaseBottomSheetDialog<DialogScanHistoryBinding>() {
    data class ItemModel(val data: String) : ListItemModel()

    private var searchQ: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.run {
            initMenu(R.menu.commands)

            onSearch { q ->
                if (searchQ != q) {
                    searchQ = q
                    search()
                }
            }
        }

        binding.rv.linear().setup {
            addType<ItemModel>(R.layout.item_row)
            R.id.container.onClick {
                val m = getModel<ItemModel>()
                ScanResultDialog(m.data) {}.show()
            }
        }
        search()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback()
    }

    private fun search() {
        binding.rv.models = LocalStorage.scanResults.filter { searchQ.isEmpty() || it.contains(searchQ, true) }.map { d ->
            ItemModel(d).apply {
                keyTextMaxLines = 2
                keyText = d.cut(100)
                swipeEnable = true
                rightSwipeText = getString(R.string.remove)
                rightSwipeClick = {
                    val results = LocalStorage.scanResults.toMutableList()
                    results.remove(d)
                    LocalStorage.scanResults = results
                    search()
                }
            }
        }
    }
}