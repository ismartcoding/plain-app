package com.ismartcoding.plain.ui.page.settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ismartcoding.plain.BuildConfig
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.MainApp
import com.ismartcoding.plain.R
import com.ismartcoding.plain.TempData
import com.ismartcoding.plain.ui.base.BottomSpacer
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PListItem
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.base.PSwitch
import com.ismartcoding.plain.ui.extensions.navigate
import com.ismartcoding.plain.ui.helpers.WebHelper
import com.ismartcoding.plain.ui.models.BackupRestoreViewModel
import com.ismartcoding.plain.ui.page.RouteName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPage(
    navController: NavHostController,
    viewModel: BackupRestoreViewModel = viewModel(),
) {
    val context = LocalContext.current
    var demoMode by remember { mutableStateOf(TempData.demoMode) }

    PScaffold(
        navController,
        content = {
            LazyColumn {
                item {
                    DisplayText(text = stringResource(R.string.about))
                    PListItem(
                        title = stringResource(R.string.client_id),
                        value = LocalStorage.clientId
                    )
                    PListItem(
                        title = stringResource(R.string.app_version),
                        value = MainApp.getAppVersion(),
                    )
                    PListItem(
                        title = stringResource(R.string.android_version),
                        value = MainApp.getAndroidVersion(),
                    )
                    PListItem(
                        title = stringResource(R.string.donation),
                        showMore = true,
                        onClick = {
                            WebHelper.open(context, "https://ko-fi.com/ismartcoding")
                        }
                    )
                    PListItem(
                        title = stringResource(R.string.logs),
                        showMore = true,
                        onClick = {
                            navController.navigate(RouteName.LOGS)
                        }
                    )
                    if (BuildConfig.DEBUG) {
                        PListItem(
                            title = stringResource(R.string.demo_mode),
                        ) {
                            PSwitch(
                                activated = demoMode
                            ) {
                                demoMode = it
                                TempData.demoMode = it
                            }
                        }
                    }
                    BottomSpacer()
                }
            }
        }
    )
}
