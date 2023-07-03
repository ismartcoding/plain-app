package com.ismartcoding.plain.ui.page.settings

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ismartcoding.lib.helpers.FormatHelper
import com.ismartcoding.lib.logcat.DiskLogFormatStrategy
import com.ismartcoding.plain.R
import com.ismartcoding.plain.features.locale.LocaleHelper.getString
import com.ismartcoding.plain.ui.base.BlockOutlineButton
import com.ismartcoding.plain.ui.base.BottomSpacer
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PListItem
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.helpers.DialogHelper
import com.ismartcoding.plain.ui.models.LogsViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsPage(
    navController: NavHostController,
    viewModel: LogsViewModel = viewModel(),
) {
    val context = LocalContext.current
    var fileSize by remember { mutableLongStateOf(getFileSize(context)) }

    PScaffold(
        navController,
        content = {
            LazyColumn {
                item {
                    DisplayText(text = stringResource(R.string.logs))
                    PListItem(
                        title = stringResource(R.string.file_size),
                        desc = FormatHelper.formatBytes(fileSize)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    BlockOutlineButton(
                        text = stringResource(R.string.share_logs),
                        onClick = {
                            if (fileSize == 0L) {
                                DialogHelper.showMessage(getString(R.string.no_logs_error))
                                return@BlockOutlineButton
                            }
                            viewModel.export(context)
                        }
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    BlockOutlineButton(
                        text = stringResource(R.string.clear_logs),
                        onClick = {
                            DialogHelper.confirmToAction(context, R.string.confirm_to_clear_logs) {
                                val dir = File(DiskLogFormatStrategy.getLogFolder(context))
                                if (dir.exists()) {
                                    dir.deleteRecursively()
                                }
                                fileSize = 0
                            }
                        }
                    )
                    BottomSpacer()
                }
            }
        }
    )
}

private fun getFileSize(context: Context): Long {
    val dir = File(DiskLogFormatStrategy.getLogFolder(context))
    if (!dir.exists()) {
        return 0
    }

    var totalSize: Long = 0
    val files = dir.listFiles() ?: arrayOf()
    for (file in files) {
        totalSize += file.length()
    }
    return totalSize
}
