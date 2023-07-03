package com.ismartcoding.plain.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Computer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ismartcoding.lib.extensions.allowSensitivePermissions
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.preference.LocalKeepScreenOn
import com.ismartcoding.plain.data.preference.LocalWeb
import com.ismartcoding.plain.helpers.ScreenHelper
import com.ismartcoding.plain.ui.base.ActionButtonMore
import com.ismartcoding.plain.ui.base.ActionButtonSettings
import com.ismartcoding.plain.ui.base.BottomSpacer
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PIconButton
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.chat.ChatDialog
import com.ismartcoding.plain.ui.extensions.navigate
import com.ismartcoding.plain.ui.home.views.HomeItemSocial
import com.ismartcoding.plain.ui.home.views.HomeItemStorage
import com.ismartcoding.plain.ui.home.views.HomeItemWork
import com.ismartcoding.plain.ui.scan.ScanDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    var isMenuOpen by remember { mutableStateOf(false) }
    val webConsole = LocalWeb.current
    val keepScreenOn = LocalKeepScreenOn.current

    PScaffold(navController,
        navigationIcon = {
            ActionButtonSettings {
                navController.navigate(RouteName.SETTINGS)
            }
        },
        actions = {
            PIconButton(
                imageVector = Icons.Outlined.Computer,
                contentDescription = stringResource(R.string.web_console),
                tint = MaterialTheme.colorScheme.onSurface,
                showBadge = webConsole
            ) {
                navController.navigate(RouteName.WEB_CONSOLE)
            }
            ActionButtonMore {
                isMenuOpen = !isMenuOpen
            }
            DropdownMenu(expanded = isMenuOpen, onDismissRequest = { isMenuOpen = false }, content = {
                DropdownMenuItem(onClick = {
                    isMenuOpen = false
                    ScreenHelper.keepScreenOn(context, !keepScreenOn)
                }, text = {
                    Row {
                        Text(
                            text = stringResource(R.string.keep_screen_on), modifier = Modifier.padding(top = 14.dp)
                        )
                        Checkbox(checked = keepScreenOn, onCheckedChange = {
                            isMenuOpen = false
                            ScreenHelper.keepScreenOn(context, it)
                        })
                    }
                })
                DropdownMenuItem(onClick = {
                    isMenuOpen = false
                    ScanDialog().show()
                }, text = {
                    Text(text = stringResource(R.string.scan_qrcode))
                })
            })
        },
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.navigationBarsPadding(), onClick = {
                ChatDialog().show()
            }) {
                Icon(
                    Icons.Outlined.Chat, stringResource(R.string.my_phone)
                )
            }
        }) {
        LazyColumn {
            item {
                DisplayText(
                    text = stringResource(R.string.app_name)
                )
                HomeItemStorage()
                Spacer(modifier = Modifier.height(16.dp))
                HomeItemWork()
                Spacer(modifier = Modifier.height(16.dp))
                if (context.allowSensitivePermissions()) {
                    HomeItemSocial()
                    Spacer(modifier = Modifier.height(16.dp))
                }
                BottomSpacer()
            }
        }
    }
}
