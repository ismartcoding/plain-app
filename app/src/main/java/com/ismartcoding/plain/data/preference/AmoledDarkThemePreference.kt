package com.ismartcoding.plain.data.preference


import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object AmoledDarkThemePreference {
    const val default = false

    fun put(context: Context, scope: CoroutineScope, value: Boolean) {
        scope.launch {
            context.dataStore.put(
                DataStoreKeys.AmoledDarkTheme,
                value
            )
        }
    }
}

