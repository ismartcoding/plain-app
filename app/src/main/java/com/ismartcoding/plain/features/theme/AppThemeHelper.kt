package com.ismartcoding.plain.features.theme

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.MainApp

object AppThemeHelper {
    fun init() {
        setDarkMode(LocalStorage.appTheme)
    }

    fun setDarkMode(theme: AppTheme) {
        LocalStorage.appTheme = theme
        when (theme) {
            AppTheme.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            AppTheme.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    fun isDarkMode(): Boolean {
        val theme = LocalStorage.appTheme
        return if (theme != AppTheme.SYSTEM) {
            theme == AppTheme.DARK
        } else {
            isAppInDarkMode()
        }
    }

    private fun isAppInDarkMode(): Boolean {
        return MainApp.instance.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}