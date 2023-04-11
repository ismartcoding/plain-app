package com.ismartcoding.plain.ui.chat.views

import android.content.Context
import android.text.InputType
import com.ismartcoding.lib.brv.utils.linear
import com.ismartcoding.lib.brv.utils.models
import com.ismartcoding.lib.brv.utils.setup
import com.ismartcoding.lib.channel.receiveEventHandler
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.plain.R
import com.ismartcoding.plain.features.ChatItemRefreshEvent
import com.ismartcoding.plain.data.UIDataCache
import com.ismartcoding.plain.databinding.ChatItemExchangeBinding
import com.ismartcoding.plain.features.chat.ChatCommandType
import com.ismartcoding.plain.features.locale.LocaleHelper
import com.ismartcoding.plain.ui.helpers.DialogHelper
import com.ismartcoding.lib.helpers.FormatHelper
import com.ismartcoding.plain.features.UpdateMessageEvent
import com.ismartcoding.plain.databinding.ItemRowBinding
import com.ismartcoding.plain.db.DChat
import com.ismartcoding.plain.db.DMessageExchange
import com.ismartcoding.plain.features.FetchLatestExchangeRatesEvent
import com.ismartcoding.plain.features.LatestExchangeRatesResultEvent
import com.ismartcoding.plain.features.exchange.DExchangeRate
import com.ismartcoding.plain.ui.helpers.ResourceHelper
import com.ismartcoding.plain.ui.EditValueDialog
import com.ismartcoding.plain.ui.exchange.SelectCurrencyDialog
import com.ismartcoding.plain.ui.extensions.initTheme
import com.ismartcoding.plain.ui.extensions.setClick
import com.ismartcoding.plain.ui.extensions.setEndIcon
import com.ismartcoding.plain.ui.extensions.setKeyText
import com.ismartcoding.plain.ui.models.ListItemModel

data class RateModel(val chatItem: DChat, val rate: DExchangeRate, val value: Double) : ListItemModel()

fun ChatItemExchangeBinding.bindData(context: Context, chatItem: DChat) {
    val latestExchangeRates = UIDataCache.current().latestExchangeRates
    if (latestExchangeRates == null) {
        sendEvent(FetchLatestExchangeRatesEvent())
        state.showLoading()
    }
    updateUI(context, chatItem)
}

fun ChatItemExchangeBinding.initView() {
    rv.linear().setup {
        addType<RateModel>(R.layout.item_row)
        onBind {
            val b = getBinding<ItemRowBinding>()
            b.initTheme()
        }
        R.id.container.onClick {
            val m = getModel<RateModel>()
            val rate = m.rate
            EditValueDialog(
                rate.currency, LocaleHelper.getString(R.string.value),
                FormatHelper.formatDouble(m.value, isGroupingUsed = false),
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            ) {
                dismiss()
                val a = m.chatItem.content.value as DMessageExchange
                a.base = rate.currency
                a.value = binding.value.text.toDoubleOrNull() ?: 100.0
                sendEvent(UpdateMessageEvent(m.chatItem))
                updateUI(requireContext(), m.chatItem)
            }.show()
        }
    }
}

fun ChatItemExchangeBinding.updateUI(context: Context, chatItem: DChat) {
    UIDataCache.current().latestExchangeRates?.let { exchangeRates ->
        this.title
            .initTheme()
            .setKeyText(LocaleHelper.getString(R.string.date) + " " + exchangeRates.date)
            .setEndIcon(R.drawable.ic_add)
            .setClick {
                SelectCurrencyDialog { rate ->
                    val c = chatItem.content.value as DMessageExchange
                    if (!c.selected.contains(rate.currency)) {
                        c.selected.add(rate.currency)
                        sendEvent(UpdateMessageEvent(chatItem))
                    }
                }.show()
            }
        val config = chatItem.content.value as DMessageExchange
        val baseRate = exchangeRates.getBaseRate(config.base)
        val selected = config.selected
        rv.models = exchangeRates.rates.filter {
            selected.contains(it.currency)
        }.map {
            RateModel(chatItem, it, config.value * it.rate / baseRate).apply {
                startIconId = ResourceHelper.getCurrencyFlagResId(context, it.currency)
                keyText = it.currency
                swipeEnable = true
                rightSwipeText = LocaleHelper.getString(R.string.delete)
                rightSwipeClick = {
                    DialogHelper.confirmToAction(context, R.string.confirm_to_delete) {
                        val c = chatItem.content.value as DMessageExchange
                        c.selected.remove(rate.currency)
                        sendEvent(UpdateMessageEvent(chatItem))
                    }
                }
                valueText = FormatHelper.formatMoney(value, it.currency)
            }
        }
    }
}

fun ChatItemExchangeBinding.initEvents(context: Context, m: ChatListView.ChatItemModel) {
    m.events.add(receiveEventHandler<LatestExchangeRatesResultEvent> { event ->
        state.update(event.result) {
            sendEvent(FetchLatestExchangeRatesEvent())
        }
        bindData(context, m.data)
    })

    m.events.add(receiveEventHandler<ChatItemRefreshEvent> { event ->
        if (event.data.type == ChatCommandType.EXCHANGE.value) {
            state.showLoading()
            sendEvent(FetchLatestExchangeRatesEvent())
        }
    })
}