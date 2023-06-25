package com.ismartcoding.plain.data.preference

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object CustomPrimaryColorPreference {

    const val default = ""

    fun put(context: Context, scope: CoroutineScope, value: String) {
        scope.launch {
            context.dataStore.put(DataStoreKeys.CustomPrimaryColor, value)
        }
    }
}
