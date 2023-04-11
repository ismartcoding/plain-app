package com.ismartcoding.plain.ui.chat.views

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ismartcoding.lib.brv.BindingAdapter
import com.ismartcoding.lib.brv.utils.addModels
import com.ismartcoding.lib.brv.utils.linear
import com.ismartcoding.lib.brv.utils.models
import com.ismartcoding.lib.brv.utils.mutable
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.lib.extensions.setSelectableItemBackground
import com.ismartcoding.lib.helpers.CoroutinesHelper.withIO
import com.ismartcoding.lib.roundview.RoundLinearLayout
import com.ismartcoding.lib.roundview.setStrokeColor
import com.ismartcoding.plain.R
import com.ismartcoding.plain.clipboardManager
import com.ismartcoding.plain.features.ChatItemRefreshEvent
import com.ismartcoding.plain.databinding.*
import com.ismartcoding.plain.db.AppDatabase
import com.ismartcoding.plain.db.DChat
import com.ismartcoding.plain.db.DMessageText
import com.ismartcoding.plain.db.DMessageType
import com.ismartcoding.plain.extensions.formatDate
import com.ismartcoding.plain.features.chat.ChatCommandType
import com.ismartcoding.plain.features.chat.ChatHelper
import com.ismartcoding.plain.features.locale.LocaleHelper
import com.ismartcoding.plain.ui.chat.ChatItemDetailDialog
import com.ismartcoding.plain.ui.chat.EditChatTextDialog
import com.ismartcoding.plain.ui.helpers.DialogHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatListView(context: Context, attrs: AttributeSet? = null) : RecyclerView(context, attrs), LifecycleObserver {
    private val pool = RecycledViewPool()
    private val events = mutableListOf<Job>()

    private fun registerLifecycleOwner(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        events.forEach {
            it.cancel()
        }
    }

    data class ChatItemModel(val data: DChat, val events: MutableList<Job> = mutableListOf())

    fun isScrollable(): Boolean {
        return canScrollHorizontally(1)
    }

    fun initView(lifecycle: Lifecycle) {
        registerLifecycleOwner(lifecycle)

        linear().adapter = object : BindingAdapter() {
            override fun onViewRecycled(holder: BindingViewHolder) {
                val m = holder._data as ChatItemModel
                m.events.forEach {
                    it.cancel()
                }
                m.events.clear()
            }
        }.apply {
            addType<ChatItemModel> {
                when (data.content.type) {
                    DMessageType.TEXT.value -> {
                        R.layout.chat_item_text
                    }
                    ChatCommandType.APP.value -> {
                        R.layout.chat_item_app
                    }
                    ChatCommandType.STORAGE.value -> {
                        R.layout.chat_item_storage
                    }
                    ChatCommandType.EXCHANGE.value -> {
                        R.layout.chat_item_exchange
                    }
                    ChatCommandType.NETWORK.value -> {
                        R.layout.chat_item_network
                    }
                    ChatCommandType.EDUCATION.value -> {
                        R.layout.chat_item_education
                    }
                    ChatCommandType.WORK.value -> {
                        R.layout.chat_item_work
                    }
                    DMessageType.IMAGES.value -> {
                        R.layout.chat_item_images
                    }
                    DMessageType.FILES.value -> {
                        R.layout.chat_item_files
                    }
                    ChatCommandType.SOCIAL.value -> {
                        R.layout.chat_item_social
                    }
                    else -> {
                        R.layout.chat_item_text
                    }
                }
            }
            onCreate {
                when (it) {
                    R.layout.chat_item_exchange -> {
                        val b = getBinding<ChatItemExchangeBinding>()
                        b.rv.setRecycledViewPool(pool)
                        b.initView()
                    }
                    R.layout.chat_item_images -> {
                        val b = getBinding<ChatItemImagesBinding>()
                        b.rv.setRecycledViewPool(pool)
                        b.initView()
                    }
                    R.layout.chat_item_files -> {
                        val b = getBinding<ChatItemFilesBinding>()
                        b.rv.setRecycledViewPool(pool)
                        b.initView()
                    }
                }
            }
            onBind {
                val m = getModel<ChatItemModel>()
                val b: ViewBinding
                when (m.data.content.type) {
                    "text" -> {
                        b = getBinding<ChatItemTextBinding>()
                        b.initView(m.data)
                    }
                    ChatCommandType.APP.value -> {
                        b = getBinding<ChatItemAppBinding>()
                        b.initView()
                    }
                    ChatCommandType.STORAGE.value -> {
                        b = getBinding<ChatItemStorageBinding>()
                        b.initView()
                    }
                    ChatCommandType.NETWORK.value -> {
                        b = getBinding<ChatItemNetworkBinding>()
                        b.initEvents(m)
                        b.initView()
                    }
                    ChatCommandType.EXCHANGE.value -> {
                        b = getBinding<ChatItemExchangeBinding>()
                        b.initEvents(context, m)
                        b.bindData(context, m.data)
                    }
                    ChatCommandType.EDUCATION.value -> {
                        b = getBinding<ChatItemEducationBinding>()
                        b.initView()
                    }
                    ChatCommandType.WORK.value -> {
                        b = getBinding<ChatItemWorkBinding>()
                        b.initView()
                    }
                    DMessageType.IMAGES.value -> {
                        b = getBinding<ChatItemImagesBinding>()
                        b.bindData(m.data)
                    }
                    DMessageType.FILES.value -> {
                        b = getBinding<ChatItemFilesBinding>()
                        b.bindData(m.data)
                    }
                    ChatCommandType.SOCIAL.value -> {
                        b = getBinding<ChatItemSocialBinding>()
                        b.initView()
                    }
                    else -> {
                        b = getBinding<ChatItemTextBinding>()
                    }
                }

                ChatItemNameBinding.bind(b.root).initView(m.data)

                var dateVisible = false
                if (modelPosition == 0) {
                    dateVisible = true
                } else {
                    val prev = adapter._data?.get(modelPosition - 1) as? ChatItemModel
                    if (prev != null && prev.data.createdAt.formatDate() != m.data.createdAt.formatDate()) {
                        dateVisible = true
                    }
                }

                itemView.findViewById<TextView>(R.id.date).run {
                    isVisible = dateVisible
                    if (dateVisible) {
                        setTextColor(context.getColor(R.color.secondary))
                        text = m.data.createdAt.formatDate()
                    }
                }
                itemView.findViewById<RoundLinearLayout>(R.id.section)?.setStrokeColor(context.getColor(R.color.primary))

                itemView.findViewById<View>(R.id.container).run {
                    setSelectableItemBackground()
                    setOnLongClickListener {
                        val popup = PopupMenu(context, itemView)
                        val popupMenu = popup.menu
                        val chatItem = m.data
                        val c = chatItem.content
                        if (ChatCommandType.parse(c.type)?.canRefresh() == true) {
                            addMenuItem(popupMenu, PopupMenuItemType.REFRESH, R.string.refresh)
                        }
                        if (c.value is DMessageText) {
                            addMenuItem(popupMenu, PopupMenuItemType.VIEW_DETAIL, R.string.view_detail)
                            addMenuItem(popupMenu, PopupMenuItemType.COPY_TEXT, R.string.copy_text)
                            addMenuItem(popupMenu, PopupMenuItemType.EDIT_TEXT, R.string.edit_text)
                        }
                        addMenuItem(popupMenu, PopupMenuItemType.DELETE, R.string.delete)
                        popup.setOnMenuItemClickListener {
                            when (it.itemId) {
                                PopupMenuItemType.REFRESH.ordinal -> {
                                    sendEvent(ChatItemRefreshEvent(c))
                                }
                                PopupMenuItemType.VIEW_DETAIL.ordinal -> {
                                    ChatItemDetailDialog(chatItem).show()
                                }
                                PopupMenuItemType.COPY_TEXT.ordinal -> {
                                    val clip = ClipData.newPlainText(LocaleHelper.getString(R.string.message), (c.value as DMessageText).text)
                                    clipboardManager.setPrimaryClip(clip)
                                    DialogHelper.showMessage(R.string.copied)
                                }
                                PopupMenuItemType.EDIT_TEXT.ordinal -> {
                                    EditChatTextDialog(chatItem).show()
                                }
                                PopupMenuItemType.DELETE.ordinal -> {
                                    DialogHelper.confirmToAction(context, R.string.confirm_to_delete) {
                                        lifecycle.coroutineScope.launch {
                                            ChatHelper.deleteAsync(chatItem)
                                        }
                                    }
                                }
                            }
                            true
                        }
                        popup.show()
                        true
                    }
                }
            }
        }
    }

    fun remove(id: String) {
        val index = models?.indexOfFirst { (it as ChatItemModel).data.id == id } ?: -1
        if (index != -1) {
            mutable.removeAt(index)
            adapter?.notifyItemRemoved(index)
            // force update next item to make sure `date` text view is updated.
            if (index <= (models?.size ?: 0) - 1) {
                adapter?.notifyItemChanged(index)
            }
        }
    }

    fun addAll(items: List<DChat>) {
        addModels(items.map { ChatItemModel(it) })
        postDelayed({
            scrollToPosition((models?.size ?: 0) - 1)
        }, 200)
    }

    fun update(item: DChat) {
        val index = models?.indexOfFirst { (it as ChatItemModel).data.id == item.id } ?: -1
        if (index >= 0) {
            mutable[index] = ChatItemModel(item)
            adapter?.notifyItemChanged(index)
        }
    }

    suspend fun refreshAsync() {
        val items = withIO { AppDatabase.instance.chatDao().getAll() }
        models = items.map { ChatItemModel(it) }
        if (items.isNotEmpty()) {
            scrollToPosition((models?.size ?: 0) - 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshUI() {
        adapter?.notifyDataSetChanged()
    }

    private enum class PopupMenuItemType {
        REFRESH,
        VIEW_DETAIL,
        COPY_TEXT,
        EDIT_TEXT,
        DELETE
    }

    private fun addMenuItem(menu: Menu, type: PopupMenuItemType, titleRes: Int) {
        menu.add(0, type.ordinal, type.ordinal, titleRes)
    }
}