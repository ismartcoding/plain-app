package com.ismartcoding.plain.ui.page.settings

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ismartcoding.plain.R
import com.ismartcoding.plain.extensions.formatName
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PListItem
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.helpers.FilePickHelper
import com.ismartcoding.plain.ui.models.BackupRestoreViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupRestorePage(
    navController: NavHostController,
    viewModel: BackupRestoreViewModel = viewModel(),
) {
    val context = LocalContext.current
    val exportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data?.data != null) {
                viewModel.backup(context, data.data!!)
            }
        }
    }

     val restoreLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            FilePickHelper.getUris(result.data!!).firstOrNull()?.let {
                viewModel.restore(context, it)
            }
        }
    }

    PScaffold(
        navController,
        content = {
            LazyColumn {
                item {
                    DisplayText(text = stringResource(R.string.backup_restore), desc = "")
                }
                item {
                    PListItem(
                        title = stringResource(R.string.backup),
                        onClick = {
                            exportLauncher.launch(Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                type = "text/*"
                                addCategory(Intent.CATEGORY_OPENABLE)
                                putExtra(Intent.EXTRA_TITLE, "backup_" + Date().formatName() + ".zip")
                            })
                        },
                    )
                }
                item {
                    PListItem(
                        title = stringResource(R.string.restore),
                        onClick = {
                            restoreLauncher.launch(FilePickHelper.getPickFileIntent(false))
                        },
                    )
                }
            }
        }
    )
}
