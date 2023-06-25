package com.ismartcoding.plain.data.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ismartcoding.plain.ui.extensions.collectAsStateValue
import kotlinx.coroutines.flow.map

data class Settings(
    // Theme
    val themeIndex: Int = ThemeIndexPreference.default,
    val customPrimaryColor: String = CustomPrimaryColorPreference.default,
    val darkTheme: Int = DarkThemePreference.default,
    val amoledDarkTheme: Boolean = AmoledDarkThemePreference.default,

    val language: Int = LanguagePreference.default,
    val webConsole: Boolean = WebConsolePreference.default,
)

// Theme
val LocalThemeIndex = compositionLocalOf { ThemeIndexPreference.default }
val LocalCustomPrimaryColor = compositionLocalOf { CustomPrimaryColorPreference.default }
val LocalDarkTheme = compositionLocalOf { DarkThemePreference.default }
val LocalAmoledDarkTheme = compositionLocalOf { AmoledDarkThemePreference.default }

val LocalLanguage = compositionLocalOf { LanguagePreference.default }
val LocalWebConsole = compositionLocalOf { WebConsolePreference.default }

@Composable
fun SettingsProvider(
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val settings = remember {
        context.dataStore.data.map {
            it.toSettings()
        }
    }.collectAsStateValue(initial = Settings())

    CompositionLocalProvider(
        // Theme
        LocalThemeIndex provides settings.themeIndex,
        LocalCustomPrimaryColor provides settings.customPrimaryColor,
        LocalDarkTheme provides settings.darkTheme,
        LocalAmoledDarkTheme provides settings.amoledDarkTheme,

        LocalLanguage provides settings.language,
        LocalWebConsole provides settings.webConsole,
    ) {
        content()
    }
}

