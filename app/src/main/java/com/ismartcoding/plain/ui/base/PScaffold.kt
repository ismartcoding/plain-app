package com.ismartcoding.plain.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ismartcoding.plain.ui.extensions.*
import com.ismartcoding.plain.ui.theme.palette.onDark
import com.ismartcoding.plain.ui.theme.palette.onLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PScaffold(
    navController: NavHostController,
    containerColor: Color = MaterialTheme.colorScheme.surface onLight MaterialTheme.colorScheme.inverseOnSurface,
    topBarTonalElevation: Dp = 0.dp,
    containerTonalElevation: Dp = 0.dp,
    navigationIcon: (@Composable () -> Unit)? = {
        NavigationBackIcon(navController)
    },
    actions: (@Composable RowScope.() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(
                    topBarTonalElevation,
                    color = containerColor
                )
            ),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
            containerTonalElevation,
            color = containerColor
        ) onDark MaterialTheme.colorScheme.surface,
        topBar = {
            if (navigationIcon != null || actions != null) {
                TopAppBar(
                    title = {},
                    navigationIcon = { navigationIcon?.invoke() },
                    actions = { actions?.invoke(this) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    )
                )
            }
        },
        content = {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Spacer(modifier = Modifier.height(it.calculateTopPadding()))
                content()
            }
        },
        bottomBar = { bottomBar?.invoke() },
        floatingActionButton = { floatingActionButton?.invoke() },
    )
}