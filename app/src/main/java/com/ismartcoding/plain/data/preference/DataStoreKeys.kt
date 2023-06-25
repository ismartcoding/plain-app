package com.ismartcoding.plain.data.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object DataStoreKeys {
    val ThemeIndex = intPreferencesKey("themeIndex")
    val CustomPrimaryColor = stringPreferencesKey("customPrimaryColor")
    val DarkTheme = intPreferencesKey("darkTheme")
    val AmoledDarkTheme = booleanPreferencesKey("amoledDarkTheme")
    val Language = intPreferencesKey("language")
    val WebConsole = booleanPreferencesKey("webConsole")
}