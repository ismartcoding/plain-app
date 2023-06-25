package com.ismartcoding.plain.ui.page.web

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ismartcoding.lib.helpers.NetworkHelper
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.preference.LocalWebConsole
import com.ismartcoding.plain.ui.base.BlockRadioButton
import com.ismartcoding.plain.ui.base.BlockRadioGroupButtonItem
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PIconButton
import com.ismartcoding.plain.ui.base.PListItem
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.base.PSwitch
import com.ismartcoding.plain.ui.base.Tips
import com.ismartcoding.plain.ui.models.WebConsoleViewModel
import com.ismartcoding.plain.ui.theme.palette.onDark
import com.ismartcoding.plain.ui.theme.palette.onLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebConsolePage(
    navController: NavHostController,
    viewModel: WebConsoleViewModel = viewModel(),
) {
    val context = LocalContext.current
    val webConsole = LocalWebConsole.current
    val scope = rememberCoroutineScope()
    var isHttps by remember { mutableStateOf(true) }

    PScaffold(
        navController,
        content = {
            LazyColumn {
                item {
                    DisplayText(text = stringResource(R.string.web_console), desc = "")
                }
                item {
                    PListItem(
                        title = stringResource(R.string.enable),
                    ) {
                        PSwitch(
                            activated = webConsole
                        ) {
                            viewModel.enableWebConsole(context, scope, !webConsole)
                        }
                    }
                    Tips(text = stringResource(id = R.string.must_in_the_same_lan))
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    BlockRadioButton(
                        selected = if (isHttps) 0 else 1,
                        onSelected = { isHttps = it == 0 },
                        itemRadioGroups = listOf(
                            BlockRadioGroupButtonItem(
                                text = stringResource(R.string.recommended_https),
                                onClick = {},
                            ) {
                            },
                            BlockRadioGroupButtonItem(
                                text = "HTTP",
                                onClick = {},
                            ) {
                            },
                        ),
                    )
                    BrowserPreview(isHttps)
                    if (isHttps) {
                        Tips(text = stringResource(id = R.string.browser_https_error_tips))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserPreview(isHttps: Boolean) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .padding(horizontal = 24.dp)
            .background(
                color = MaterialTheme.colorScheme.surface onDark MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            PIconButton(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
            PIconButton(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = stringResource(R.string.refresh),
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Box(
                Modifier
                    .padding(top = 4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface onLight MaterialTheme.colorScheme.inverseOnSurface,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                SelectionContainer {
                    Text(
                        text = if (isHttps) {
                            "https://${NetworkHelper.getDeviceIP4().ifEmpty { "127.0.0.1" }}:${LocalStorage.httpsPort}"
                        } else {
                            "http://${NetworkHelper.getDeviceIP4().ifEmpty { "127.0.0.1" }}:${LocalStorage.httpPort}"
                        },
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = stringResource(id = R.string.enter_this_address_tips),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
