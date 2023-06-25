package com.ismartcoding.plain.data.preference

import androidx.datastore.preferences.core.Preferences

fun Preferences.toSettings(): Settings {
    return Settings(
        // Theme
        themeIndex = this[DataStoreKeys.ThemeIndex] ?: ThemeIndexPreference.default,
        customPrimaryColor = this[DataStoreKeys.CustomPrimaryColor] ?: CustomPrimaryColorPreference.default,
        darkTheme = this[DataStoreKeys.DarkTheme] ?: DarkThemePreference.default,
        amoledDarkTheme = this[DataStoreKeys.AmoledDarkTheme] ?: AmoledDarkThemePreference.default,

        language = this[DataStoreKeys.Language] ?: LanguagePreference.default,
        webConsole = this[DataStoreKeys.WebConsole] ?: WebConsolePreference.default,
    )
}
