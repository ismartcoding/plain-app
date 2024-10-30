package com.ismartcoding.plain.ui.page

import android.app.Activity
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.ismartcoding.lib.channel.receiveEventHandler
import com.ismartcoding.lib.extensions.isGestureInteractionMode
import com.ismartcoding.plain.enums.DarkTheme
import com.ismartcoding.plain.enums.DataType
import com.ismartcoding.plain.features.ConfirmDialogEvent
import com.ismartcoding.plain.features.LoadingDialogEvent
import com.ismartcoding.plain.preference.LocalDarkTheme
import com.ismartcoding.plain.ui.base.coil.newImageLoader
import com.ismartcoding.plain.ui.models.MainViewModel
import com.ismartcoding.plain.ui.models.NotesViewModel
import com.ismartcoding.plain.ui.models.TagsViewModel
import com.ismartcoding.plain.ui.nav.Routing
import com.ismartcoding.plain.ui.nav.sharedViewModel
import com.ismartcoding.plain.ui.page.apps.AppPage
import com.ismartcoding.plain.ui.page.apps.AppsPage
import com.ismartcoding.plain.ui.page.audio.AudioPage
import com.ismartcoding.plain.ui.page.chat.ChatEditTextPage
import com.ismartcoding.plain.ui.page.chat.ChatPage
import com.ismartcoding.plain.ui.page.chat.ChatTextPage
import com.ismartcoding.plain.ui.page.docs.DocsPage
import com.ismartcoding.plain.ui.page.feeds.FeedEntriesPage
import com.ismartcoding.plain.ui.page.feeds.FeedEntryPage
import com.ismartcoding.plain.ui.page.feeds.FeedSettingsPage
import com.ismartcoding.plain.ui.page.feeds.FeedsPage
import com.ismartcoding.plain.ui.page.images.ImagesPage
import com.ismartcoding.plain.ui.page.images.MediaFoldersPage
import com.ismartcoding.plain.ui.page.notes.NotePage
import com.ismartcoding.plain.ui.page.notes.NotesPage
import com.ismartcoding.plain.ui.page.scan.ScanHistoryPage
import com.ismartcoding.plain.ui.page.scan.ScanPage
import com.ismartcoding.plain.ui.page.settings.AboutPage
import com.ismartcoding.plain.ui.page.settings.BackupRestorePage
import com.ismartcoding.plain.ui.page.settings.ColorAndStylePage
import com.ismartcoding.plain.ui.page.settings.DarkThemePage
import com.ismartcoding.plain.ui.page.settings.LanguagePage
import com.ismartcoding.plain.ui.page.settings.SettingsPage
import com.ismartcoding.plain.ui.page.tags.TagsPage
import com.ismartcoding.plain.ui.page.tools.SoundMeterPage
import com.ismartcoding.plain.ui.page.videos.VideosPage
import com.ismartcoding.plain.ui.page.web.SessionsPage
import com.ismartcoding.plain.ui.page.web.WebDevPage
import com.ismartcoding.plain.ui.page.web.WebLearnMorePage
import com.ismartcoding.plain.ui.page.web.WebSecurityPage
import com.ismartcoding.plain.ui.page.web.WebSettingsPage
import com.ismartcoding.plain.ui.theme.AppTheme
import kotlinx.coroutines.Job

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class)
@Composable
fun Main(navControllerState: MutableState<NavHostController?>, onLaunched: () -> Unit, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val navController = rememberNavController()
    navControllerState.value = navController
    val useDarkTheme = DarkTheme.isDarkTheme(LocalDarkTheme.current)
    val view = LocalView.current
    val window = (view.context as Activity).window
    val insetsController = WindowCompat.getInsetsController(window, view)
    var confirmDialogEvent by remember {
        mutableStateOf<ConfirmDialogEvent?>(null)
    }
    var loadingDialogEvent by remember {
        mutableStateOf<LoadingDialogEvent?>(null)
    }
    val events = remember { mutableStateListOf<Job>() }

    LaunchedEffect(Unit) {
        events.add(
            receiveEventHandler<ConfirmDialogEvent> { event ->
                confirmDialogEvent = event
            }
        )
        events.add(
            receiveEventHandler<LoadingDialogEvent> { event ->
                loadingDialogEvent = if (event.show) event else null
            }
        )
        onLaunched()
    }

    AppTheme(useDarkTheme = useDarkTheme) {
        setSingletonImageLoaderFactory(::newImageLoader)

        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = if (context.isGestureInteractionMode()) Color.Transparent.toArgb() else MaterialTheme.colorScheme.background.toArgb()
        insetsController.isAppearanceLightStatusBars = !useDarkTheme
        insetsController.isAppearanceLightNavigationBars = !useDarkTheme

        NavHost(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            navController = navController,
            startDestination = Routing.Home,
        ) {
            composable<Routing.Home> { HomePage(navController, mainViewModel) }
            composable<Routing.Settings> { SettingsPage(navController) }
            composable<Routing.ColorAndStyle> { ColorAndStylePage(navController) }
            composable<Routing.DarkTheme> { DarkThemePage(navController) }
            composable<Routing.Language> { LanguagePage(navController) }
            composable<Routing.BackupRestore> { BackupRestorePage(navController) }
            composable<Routing.About> { AboutPage(navController) }
            composable<Routing.WebSettings> { WebSettingsPage(navController, mainViewModel) }
            composable<Routing.Sessions> { SessionsPage(navController) }
            composable<Routing.WebDev> { WebDevPage(navController) }
            composable<Routing.WebSecurity> { WebSecurityPage(navController) }
            composable<Routing.SoundMeter> { SoundMeterPage(navController) }
            composable<Routing.Chat> { ChatPage(navController) }
            composable<Routing.ScanHistory> { ScanHistoryPage(navController) }
            composable<Routing.Scan> { ScanPage(navController) }
            composable<Routing.Apps> { AppsPage(navController) }
            composable<Routing.Docs> { DocsPage(navController) }
            composable<Routing.Feeds> { FeedsPage(navController) }
            composable<Routing.FeedSettings> { FeedSettingsPage(navController) }
            composable<Routing.WebLearnMore> { WebLearnMorePage(navController) }
            composable<Routing.Audio> { AudioPage(navController) }
            composable<Routing.Notes> { backStackEntry ->
                val tagsViewModel = backStackEntry.sharedViewModel<TagsViewModel>(navController)
                val notesViewModel = backStackEntry.sharedViewModel<NotesViewModel>(navController)
                NotesPage(navController, viewModel = notesViewModel, tagsViewModel = tagsViewModel)
            }
            composable<Routing.AppDetails> { backStackEntry ->
                val r = backStackEntry.toRoute<Routing.AppDetails>()
                AppPage(navController, r.id)
            }

            composable<Routing.FeedEntries> { backStackEntry ->
                val r = backStackEntry.toRoute<Routing.FeedEntries>()
                val feedId = r.feedId
                val tagsViewModel = backStackEntry.sharedViewModel<TagsViewModel>(navController)
                FeedEntriesPage(navController, feedId, tagsViewModel = tagsViewModel)
            }

            composable<Routing.FeedEntry> { backStackEntry ->
                val tagsViewModel = backStackEntry.sharedViewModel<TagsViewModel>(navController)
                val r = backStackEntry.toRoute<Routing.FeedEntry>()
                FeedEntryPage(navController, r.id, tagsViewModel = tagsViewModel)
            }

            composable<Routing.NotesCreate> { backStackEntry ->
                val tagsViewModel = backStackEntry.sharedViewModel<TagsViewModel>(navController)
                val notesViewModel = backStackEntry.sharedViewModel<NotesViewModel>(navController)
                val r = backStackEntry.toRoute<Routing.NotesCreate>()
                NotePage(navController, "", r.tagId, notesViewModel = notesViewModel, tagsViewModel = tagsViewModel)
            }

            composable<Routing.NoteDetail> { backStackEntry ->
                val tagsViewModel = backStackEntry.sharedViewModel<TagsViewModel>(navController)
                val notesViewModel = backStackEntry.sharedViewModel<NotesViewModel>(navController)
                val r = backStackEntry.toRoute<Routing.NoteDetail>()
                NotePage(navController, r.id, "", notesViewModel = notesViewModel, tagsViewModel = tagsViewModel)
            }

            composable<Routing.Images> { backStackEntry ->
                val r = backStackEntry.toRoute<Routing.Images>()
                ImagesPage(navController, r.bucketId)
            }

            composable<Routing.Videos> { backStackEntry ->
                val r = backStackEntry.toRoute<Routing.Videos>()
                VideosPage(navController, r.bucketId)
            }

            composable<Routing.Text> {
                val title = navController.previousBackStackEntry?.savedStateHandle?.get("title") ?: ""
                val content = navController.previousBackStackEntry?.savedStateHandle?.get("content") ?: ""
                val language = navController.previousBackStackEntry?.savedStateHandle?.get("language") ?: ""
                TextPage(navController, title, content, language)
            }

            composable<Routing.TextFile> {
                val path = navController.previousBackStackEntry?.savedStateHandle?.get("path") ?: ""
                val title = navController.previousBackStackEntry?.savedStateHandle?.get("title") ?: ""
                val type = navController.previousBackStackEntry?.savedStateHandle?.get("type") ?: ""
                val mediaId = navController.previousBackStackEntry?.savedStateHandle?.get("mediaId") ?: ""
                TextFilePage(navController, path, title, mediaId, type)
            }

            composable<Routing.ChatText> {
                val content = navController.previousBackStackEntry?.savedStateHandle?.get("content") ?: ""
                ChatTextPage(navController, content)
            }

            composable<Routing.Tags> { backStackEntry ->
                val r = backStackEntry.toRoute<Routing.Tags>()
                TagsPage(navController, DataType.fromInt(r.dataType))
            }

            composable<Routing.MediaFolders> { backStackEntry ->
                val r = backStackEntry.toRoute<Routing.MediaFolders>()
                MediaFoldersPage(navController, DataType.fromInt(r.dataType))
            }

            composable<Routing.ChatEditText> { backStackEntry ->
                val content = navController.previousBackStackEntry?.savedStateHandle?.get("content") ?: ""
                val r = backStackEntry.toRoute<Routing.ChatEditText>()
                ChatEditTextPage(navController, r.id, content)
            }

            composable<Routing.OtherFile> {
                val path = navController.previousBackStackEntry?.savedStateHandle?.get("path") ?: ""
                OtherFilePage(navController, path)
            }

            composable<Routing.PdfViewer> {
                val uri = navController.previousBackStackEntry?.savedStateHandle?.get("uri") as? Uri
                if (uri != null) {
                    PdfPage(navController, uri)
                }
            }
        }

        if (confirmDialogEvent != null) {
            AlertDialog(onDismissRequest = {
                confirmDialogEvent = null
            }, title = if (confirmDialogEvent!!.title.isNotEmpty()) {
                {
                    Text(
                        confirmDialogEvent!!.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            } else null, text = {
                Text(confirmDialogEvent!!.message)
            }, confirmButton = {
                Button(onClick = {
                    confirmDialogEvent!!.confirmButton.second()
                    confirmDialogEvent = null
                }) {
                    Text(
                        confirmDialogEvent!!.confirmButton.first,
                    )
                }
            }, dismissButton = {
                confirmDialogEvent?.dismissButton?.let {
                    TextButton(onClick = {
                        it.second()
                        confirmDialogEvent = null
                    }) {
                        Text(it.first)
                    }
                }
            })
        }

        if (loadingDialogEvent != null) {
            Dialog(
                onDismissRequest = { loadingDialogEvent = null },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(96.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

