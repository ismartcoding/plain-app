package com.ismartcoding.plain.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.preference.LocalWebConsole
import com.ismartcoding.plain.helpers.ScreenHelper
import com.ismartcoding.plain.ui.app.HttpServerDialog
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PIconButton
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.chat.ChatDialog
import com.ismartcoding.plain.ui.home.views.HomeItemStorage
import com.ismartcoding.plain.ui.home.views.HomeItemWork
import com.ismartcoding.plain.ui.scan.ScanDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavHostController,
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    val webConsole = LocalWebConsole.current

    PScaffold(navController,
        navigationIcon = {
            PIconButton(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.settings),
                tint = MaterialTheme.colorScheme.onSurface,
            ) {
                navController.navigate(RouteName.SETTINGS.name)
            }
        }, actions = {
            PIconButton(
                imageVector = Icons.Outlined.Computer,
                contentDescription = stringResource(R.string.web_console),
                tint = MaterialTheme.colorScheme.onSurface,
                showBadge = webConsole
            ) {
                navController.navigate(RouteName.WEB_CONSOLE.name)
            }
            PIconButton(
                imageVector = Icons.Outlined.MoreVert, contentDescription = stringResource(R.string.more), tint = MaterialTheme.colorScheme.onSurface
            ) {
                isMenuOpen = !isMenuOpen
            }
            DropdownMenu(expanded = isMenuOpen, onDismissRequest = { isMenuOpen = false }, content = {
                DropdownMenuItem(onClick = {
                    isMenuOpen = false
                    if (ScreenHelper.keepScreenOn(!LocalStorage.keepScreenOn)) {
                    }
                }, text = {
                    Row {
                        Text(
                            text = stringResource(R.string.keep_screen_on), modifier = Modifier.padding(top = 14.dp)
                        )
                        Checkbox(checked = LocalStorage.keepScreenOn, onCheckedChange = {
                            isMenuOpen = false
                            if (ScreenHelper.keepScreenOn(it)) {
                            }
                        })
                    }
                })
                DropdownMenuItem(onClick = {
                    isMenuOpen = false
                    ScanDialog().show()
                }, text = {
                    Text(text = stringResource(R.string.scan))
                })
            })
        }, content = {
            HomeList()
        }, floatingActionButton = {
            FloatingActionButton(modifier = Modifier.navigationBarsPadding(), onClick = {
                ChatDialog().show()
            }) {
                Icon(
                    Icons.Outlined.Chat, stringResource(R.string.my_phone)
                )
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeList() {
    LazyColumn {
        item {
            DisplayText(
                text = stringResource(R.string.app_name), desc = ""
            )
        }
        item {
            HomeItemStorage()
        }
        item {
            HomeItemWork()
        }
    }
}