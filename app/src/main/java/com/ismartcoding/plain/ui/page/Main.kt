package com.ismartcoding.plain.ui.page

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ismartcoding.plain.data.preference.DarkTheme
import com.ismartcoding.plain.data.preference.LocalDarkTheme
import com.ismartcoding.plain.ui.models.MainViewModel
import com.ismartcoding.plain.ui.page.settings.AboutPage
import com.ismartcoding.plain.ui.page.settings.BackupRestorePage
import com.ismartcoding.plain.ui.page.settings.ColorAndStylePage
import com.ismartcoding.plain.ui.page.settings.DarkThemePage
import com.ismartcoding.plain.ui.page.settings.LanguagePage
import com.ismartcoding.plain.ui.page.settings.LogsPage
import com.ismartcoding.plain.ui.page.settings.SettingsPage
import com.ismartcoding.plain.ui.page.web.WebConsolePage
import com.ismartcoding.plain.ui.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Main(
    viewModel: MainViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val useDarkTheme = DarkTheme.isDarkTheme(LocalDarkTheme.current)
    
    AppTheme(useDarkTheme = useDarkTheme) {
        rememberSystemUiController().run {
            setStatusBarColor(Color.Transparent, !useDarkTheme)
            setSystemBarsColor(Color.Transparent, !useDarkTheme)
            setNavigationBarColor(Color.Transparent, !useDarkTheme)
        }

        NavHost(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            navController = navController,
            startDestination = RouteName.HOME.name,
        ) {
            mapOf<RouteName, @Composable () -> Unit>(
                RouteName.HOME to { HomePage(navController) },
                RouteName.SETTINGS to { SettingsPage(navController) },
                RouteName.COLOR_AND_STYLE to { ColorAndStylePage(navController) },
                RouteName.DARK_THEME to { DarkThemePage(navController) },
                RouteName.LANGUAGE to { LanguagePage(navController) },
                RouteName.BACKUP_RESTORE to { BackupRestorePage(navController) },
                RouteName.ABOUT to { AboutPage(navController) },
                RouteName.LOGS to { LogsPage(navController) },
                RouteName.WEB_CONSOLE to { WebConsolePage(navController) },
            ).forEach { (routeName, content) ->
                composable(routeName.name) {
                    content()
                }
            }
        }
    }
}

