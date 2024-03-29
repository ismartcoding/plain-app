package com.ismartcoding.plain.ui.page

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ismartcoding.lib.helpers.ShareHelper
import com.ismartcoding.plain.R
import com.ismartcoding.plain.ui.base.PIconButton
import com.ismartcoding.plain.ui.base.PScaffold
import com.ismartcoding.plain.ui.models.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextPage(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    val context = LocalContext.current
    PScaffold(
        navController,
        topBarTitle = sharedViewModel.textTitle.value,
        actions = {
            PIconButton(
                imageVector = Icons.Outlined.Share,
                contentDescription = stringResource(R.string.share),
                tint = MaterialTheme.colorScheme.onSurface,
            ) {
                ShareHelper.shareText(context, sharedViewModel.textContent.value)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                SelectionContainer {
                    Text(text = sharedViewModel.textContent.value)
                }
            }
        },
    )
}
