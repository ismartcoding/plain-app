package com.ismartcoding.plain.ui.app

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ismartcoding.lib.brv.utils.linear
import com.ismartcoding.lib.brv.utils.setup
import com.ismartcoding.lib.extensions.capitalize
import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.DialogSelectItemBinding
import com.ismartcoding.plain.databinding.ViewListItemBinding
import com.ismartcoding.plain.db.DSession
import com.ismartcoding.plain.extensions.formatDateTime
import com.ismartcoding.plain.ui.BaseBottomSheetDialog
import com.ismartcoding.plain.web.HttpServerManager
import com.ismartcoding.plain.web.SessionList
import com.ismartcoding.lib.helpers.CoroutinesHelper.withIO
import com.ismartcoding.plain.features.locale.LocaleHelper
import com.ismartcoding.plain.ui.helpers.DialogHelper
import com.ismartcoding.plain.ui.extensions.*
import kotlinx.coroutines.launch

class SessionsDialog : BaseBottomSheetDialog<DialogSelectItemBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setTitle(R.string.sessions)
        binding.list.rv.linear().setup {
            addType<DSession>(R.layout.view_list_item)
            onBind {
                val itemBinding = getBinding<ViewListItemBinding>()
                val m = getModel<DSession>()
                itemBinding.setKeyText(m.clientIP)
                itemBinding.clearTextRows()
                itemBinding.addTextRow("${getString(R.string.client_id)} " + m.clientId)
                itemBinding.addTextRow("${getString(R.string.created_at)} " + m.createdAt.formatDateTime())
                itemBinding.addTextRow("${getString(R.string.last_visit_at)} " + m.updatedAt.formatDateTime())
                itemBinding.addTextRow(
                    LocaleHelper.getStringF(
                        R.string.client_ua, "os_name", m.osName.capitalize(), "os_version",
                        m.osVersion, "browser_name", m.browserName.capitalize(), "browser_version", m.browserVersion
                    )
                )
                itemBinding.enableSwipeMenu(true)
                itemBinding.setRightSwipeButton(getString(R.string.delete)) {
                    DialogHelper.confirmToAction(requireContext(), R.string.confirm_to_delete) {
                        lifecycleScope.launch {
                            withIO {
                                SessionList.deleteAsync(m)
                                HttpServerManager.loadTokenCache()
                            }
                            binding.list.page.refresh()
                        }
                    }
                }
            }
        }

        binding.list.page.run {
            setEnableRefresh(false)
            setEnableNestedScroll(false)
            onRefresh {
                lifecycleScope.launch {
                    val items = withIO { SessionList.getItemsAsync() }
                    binding.list.page.addData(items)
                }
            }.showLoading()
        }
    }
}