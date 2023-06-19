package com.ismartcoding.plain.ui.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ismartcoding.lib.channel.receiveEvent
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.lib.helpers.CoroutinesHelper
import com.ismartcoding.plain.R
import com.ismartcoding.plain.databinding.DialogChatBinding
import com.ismartcoding.plain.db.AppDatabase
import com.ismartcoding.plain.db.ChatItemDataUpdate
import com.ismartcoding.plain.features.DeleteChatItemViewEvent
import com.ismartcoding.plain.features.SendMessageEvent
import com.ismartcoding.plain.features.UpdateMessageEvent
import com.ismartcoding.plain.features.chat.ChatHelper
import com.ismartcoding.plain.ui.BaseDialog
import com.ismartcoding.plain.ui.MainActivity
import com.ismartcoding.plain.ui.extensions.initMenu
import com.ismartcoding.plain.ui.extensions.onBack
import com.ismartcoding.plain.ui.extensions.setSafeClick
import com.ismartcoding.plain.ui.helpers.DialogHelper
import com.ismartcoding.plain.ui.models.ShowMessageEvent
import com.ismartcoding.plain.web.HttpServerEvents
import com.ismartcoding.plain.web.websocket.EventType
import com.ismartcoding.plain.web.websocket.WebSocketEvent
import kotlinx.coroutines.launch

class ChatDialog() : BaseDialog<DialogChatBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.toolbar.run {
            title = getString(R.string.my_phone)
            onBack {
                onBackPressed()
            }
        }

        initEvents()
        binding.fab.setSafeClick {
            SendMessageDialog().show()
        }
        binding.page.pageName = javaClass.simpleName
        binding.page.run {
//                onRefresh {
//                    finishRefresh()
//                }
            setEnableRefresh(false)
        }
        binding.chatList.initView(lifecycle)
        lifecycleScope.launch {
            binding.chatList.refreshAsync()
        }
    }

    private fun initEvents() {
        receiveEvent<ShowMessageEvent> { event ->
            Toast.makeText(MainActivity.instance.get()!!, event.message, event.duration).show()
        }

        receiveEvent<DeleteChatItemViewEvent> { event ->
            binding.chatList.remove(event.id)
            if (!binding.chatList.isScrollable()) {
                binding.fab.isVisible = true
            }
        }

        receiveEvent<SendMessageEvent> { event ->
            lifecycleScope.launch {
                val items = CoroutinesHelper.withIO { ChatHelper.createChatItemsAsync(event.content) }
                sendEvent(WebSocketEvent(EventType.MESSAGE_CREATED))
                binding.chatList.addAll(items)
                DialogHelper.hideLoading()
            }
        }

        receiveEvent<HttpServerEvents.MessageCreatedEvent> { event ->
            binding.chatList.addAll(event.items)
        }

        receiveEvent<UpdateMessageEvent> { event ->
            lifecycleScope.launch {
                val update = ChatItemDataUpdate(event.chatItem.id, event.chatItem.content)
                CoroutinesHelper.withIO {
                    AppDatabase.instance.chatDao().updateData(update)
                }
                binding.chatList.update(event.chatItem)
            }
        }
    }
}