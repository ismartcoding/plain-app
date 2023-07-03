package com.ismartcoding.plain.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.CursorWindow
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ismartcoding.lib.channel.receiveEvent
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.lib.extensions.*
import com.ismartcoding.lib.helpers.CoroutinesHelper.withIO
import com.ismartcoding.lib.helpers.CryptoHelper
import com.ismartcoding.lib.helpers.JsonHelper
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.MainApp
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.*
import com.ismartcoding.plain.data.enums.ExportFileType
import com.ismartcoding.plain.data.enums.PickFileTag
import com.ismartcoding.plain.data.enums.PickFileType
import com.ismartcoding.plain.data.enums.Language
import com.ismartcoding.plain.data.preference.AuthTwoFactorPreference
import com.ismartcoding.plain.data.preference.KeepScreenOnPreference
import com.ismartcoding.plain.data.preference.LanguagePreference
import com.ismartcoding.plain.data.preference.SettingsProvider
import com.ismartcoding.plain.data.preference.SystemScreenTimeoutPreference
import com.ismartcoding.plain.db.*
import com.ismartcoding.plain.features.*
import com.ismartcoding.plain.features.bluetooth.BluetoothPermission
import com.ismartcoding.plain.features.locale.LocaleHelper.getStringF
import com.ismartcoding.plain.helpers.ScreenHelper
import com.ismartcoding.plain.mediaProjectionManager
import com.ismartcoding.plain.services.ScreenMirrorService
import com.ismartcoding.plain.ui.extensions.*
import com.ismartcoding.plain.ui.helpers.FilePickHelper
import com.ismartcoding.plain.ui.models.MainViewModel
import com.ismartcoding.plain.ui.models.ShowMessageEvent
import com.ismartcoding.plain.ui.page.Main
import com.ismartcoding.plain.web.*
import io.ktor.server.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private var pickFileType = PickFileType.IMAGE
    private var pickFileTag = PickFileTag.SEND_MESSAGE
    private var exportFileType = ExportFileType.OPML
    private var requestToConnectDialog: AlertDialog? = null
    private val viewModel: MainViewModel by viewModels()
    private val screenCapture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            if (ScreenMirrorService.instance == null) {
                val metrics = resources.displayMetrics
                val service = Intent(this, ScreenMirrorService::class.java)
                service.putExtra("code", result.resultCode)
                service.putExtra("data", result.data!!)
                service.putExtra("width", metrics.widthPixels)
                service.putExtra("height", metrics.heightPixels)
                service.putExtra("density", metrics.densityDpi)
                startService(service)
            }
        }
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            sendEvent(PickFileResultEvent(pickFileTag, pickFileType, setOf(uri)))
        }
    }

    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
        if (uris.isNotEmpty()) {
            sendEvent(PickFileResultEvent(pickFileTag, pickFileType, uris.toSet()))
        }
    }

    private val pickFileActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            sendEvent(PickFileResultEvent(pickFileTag, pickFileType, FilePickHelper.getUris(result.data!!)))
        }
    }

    private val exportFileActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data?.data != null) {
                sendEvent(ExportFileResultEvent(exportFileType, data.data!!))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "DiscouragedPrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val language = LanguagePreference.get(this)
        Language.values().find { it.value == language }?.let {
            if (it == Language.UseDeviceLanguage) return@let
            it.setLocale(this)
        }

        instance = WeakReference(this)

        // https://stackoverflow.com/questions/51959944/sqliteblobtoobigexception-row-too-big-to-fit-into-cursorwindow-requiredpos-0-t
        try {
            val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }

        BluetoothPermission.init(this)
        Permissions.init(this)
        initEvents()


        setContent {
            SettingsProvider {
                Main(viewModel)
            }
        }

        Permissions.checkNotification(this@MainActivity, R.string.foreground_service_notification_prompt) {
            sendEvent(StartHttpServerEvent())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BluetoothPermission.release()
        Permissions.release()
    }

    @SuppressLint("CheckResult")
    private fun initEvents() {
        receiveEvent<ShowMessageEvent> { event ->
            Toast.makeText(instance.get()!!, event.message, event.duration).show()
        }

        receiveEvent<PermissionResultEvent> { event ->
            if (event.permission == Permission.WRITE_SETTINGS && Permission.WRITE_SETTINGS.can(this@MainActivity)) {
                val enable = !KeepScreenOnPreference.get(this@MainActivity)
                ScreenHelper.saveOn(enable)
                if (enable) {
                    ScreenHelper.saveTimeout(contentResolver.getSystemScreenTimeout())
                    contentResolver.setSystemScreenTimeout(Int.MAX_VALUE)
                } else {
                    val systemScreenTimeout = SystemScreenTimeoutPreference.get(this@MainActivity)
                    contentResolver.setSystemScreenTimeout(if (systemScreenTimeout > 0) systemScreenTimeout else 5000 * 60) // default 5 minutes
                }
            }
        }

        receiveEvent<StartScreenMirrorEvent> {
            screenCapture.launch(mediaProjectionManager.createScreenCaptureIntent())
        }

        receiveEvent<UpdateHomeItemEvent> { event ->
//            binding.home.update(event.type)
        }

        receiveEvent<RestartAppEvent> {
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        receiveEvent<PickFileEvent> {
            pickFileType = it.type
            pickFileTag = it.tag
            var type: ActivityResultContracts.PickVisualMedia.VisualMediaType? = null
            when (it.type) {
                PickFileType.IMAGE_VIDEO -> {
                    type = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                }

                PickFileType.IMAGE -> {
                    type = ActivityResultContracts.PickVisualMedia.ImageOnly
                }

                else -> {}
            }
            if (type != null) {
                if (it.multiple) {
                    pickMultipleMedia.launch(PickVisualMediaRequest(type))
                } else {
                    pickMedia.launch(PickVisualMediaRequest(type))
                }
            } else {
                doPickFile(it)
            }
        }

        receiveEvent<ExportFileEvent> { event ->
            exportFileType = event.type
            exportFileActivityLauncher.launch(Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                type = "text/*"
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_TITLE, event.fileName)
            })
        }

        receiveEvent<ConfirmToAcceptLoginEvent> { event ->
            val clientIp = HttpServerManager.clientIpCache[event.clientId] ?: ""
            if (!AuthTwoFactorPreference.get(this@MainActivity)) {
                launch {
                    withIO {
                        respondTokenAsync(event, clientIp)
                    }
                }
                return@receiveEvent
            }

            if (requestToConnectDialog?.isShowing == true) {
                requestToConnectDialog?.dismiss()
                requestToConnectDialog = null
            }
            requestToConnectDialog = MaterialAlertDialogBuilder(instance.get()!!).setTitle(getStringF(R.string.request_to_connect, "ip", clientIp)).setMessage(
                getStringF(
                    R.string.client_ua, "os_name", event.osName.capitalize(), "os_version", event.osVersion, "browser_name", event.browserName.capitalize(), "browser_version", event.browserVersion
                )
            ).setPositiveButton(getString(R.string.accept)) { _, _ ->
                launch {
                    withIO { respondTokenAsync(event, clientIp) }
                }
            }.setNegativeButton(getString(R.string.reject)) { _, _ ->
                launch {
                    withIO {
                        event.session.close(
                            CloseReason(
                                CloseReason.Codes.TRY_AGAIN_LATER, "rejected"
                            )
                        )
                    }
                }
            }.create()
            requestToConnectDialog?.show()
        }
    }

    private suspend fun respondTokenAsync(event: ConfirmToAcceptLoginEvent, clientIp: String) {
        val token = CryptoHelper.generateAESKey()
        SessionList.addOrUpdateAsync(event.clientId) {
            it.clientIP = clientIp
            it.osName = event.osName
            it.osVersion = event.osVersion
            it.browserName = event.browserName
            it.browserVersion = event.browserVersion
            it.token = token
        }
        HttpServerManager.loadTokenCache()
        event.session.send(
            CryptoHelper.aesEncrypt(
                HttpServerManager.passwordToToken(), JsonHelper.jsonEncode(
                    AuthResponse(
                        AuthStatus.COMPLETED, token
                    )
                )
            )
        )
    }

    private fun doPickFile(event: PickFileEvent) {
        pickFileActivityLauncher.launch(FilePickHelper.getPickFileIntent(event.multiple))
    }

    companion object {
        lateinit var instance: WeakReference<MainActivity>
    }
}