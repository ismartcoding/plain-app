package com.ismartcoding.plain.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ismartcoding.plain.ui.base.DisplayText
import com.ismartcoding.plain.ui.base.PScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextPage(
    navController: NavHostController,
    title: String,
    content: String,
) {
    PScaffold(
        navController,
        content = {
            LazyColumn {
                item {
                    DisplayText(text = title)
                }
                item {
                    Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                        SelectionContainer {
                            Text(text = content)
                        }
                    }
                }
            }
        }
    )
}

