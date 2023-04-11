package com.ismartcoding.plain.ui.scan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.ismartcoding.lib.channel.receiveEvent
import com.ismartcoding.lib.channel.sendEvent
import com.ismartcoding.lib.extensions.*
import com.ismartcoding.lib.helpers.BitmapHelper
import com.ismartcoding.lib.helpers.CoroutinesHelper.coIO
import com.ismartcoding.lib.logcat.LogCat
import com.ismartcoding.plain.LocalStorage
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.enums.PickFileTag
import com.ismartcoding.plain.data.enums.PickFileType
import com.ismartcoding.plain.databinding.DialogScanBinding
import com.ismartcoding.plain.features.*
import com.ismartcoding.plain.ui.BaseDialog
import com.ismartcoding.plain.ui.extensions.onBack
import com.ismartcoding.plain.ui.extensions.setSafeClick
import com.ismartcoding.plain.ui.helpers.DialogHelper
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ScanDialog() : BaseDialog<DialogScanBinding>() {
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private val screenAspectRatio by lazy {
        resources.displayMetrics.getAspectRatio()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireActivity(), R.style.Theme_FullScreen).apply {
            window?.fullScreen()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.onBack {
            dismiss()
        }

        receiveEvent<PermissionResultEvent> { event ->
            if (Permission.CAMERA.can()) {
                startCamera()
            } else {
                DialogHelper.showMessage(getString(R.string.scan_needs_camera_warning))
            }
        }

        receiveEvent<PickFileResultEvent> { event ->
            if (event.tag != PickFileTag.SCAN) {
                return@receiveEvent
            }
            coIO {
                try {
                    DialogHelper.showLoading()
                    val barcodeScanner = BarcodeScanning.getClient()
                    val bitmap = BitmapHelper.getBitmapFromContentUri(requireContext(), event.uris.first())
                    processImage(barcodeScanner, InputImage.fromBitmap(bitmap!!, 0))
                        .addOnCompleteListener {
                            DialogHelper.hideLoading()
                        }
                } catch (ex: Exception) {
                    // the picked file could be deleted
                    ex.printStackTrace()
                }
            }
        }

        binding.history.setSafeClick {
            cameraProvider?.unbindAll()
            camera = null
            ScanHistoryDialog() {
                startCamera()
            }.show()
        }

        binding.photos.setSafeClick {
            sendEvent(PickFileEvent(PickFileTag.SCAN, PickFileType.IMAGE, multiple = false))
        }

        if (Permission.CAMERA.can()) {
            startCamera()
        } else {
            Permission.CAMERA.grant()
        }
    }

    private fun startCamera() {
        if (isActive && camera == null) { // if dialog is dismissed, should not start camera
            val context = requireContext()
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            }, ContextCompat.getMainExecutor(context))
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindPreview(camProvider: ProcessCameraProvider) {
        cameraProvider = camProvider
        val rotation = resources.configuration.orientation
        val previewUseCase = Preview.Builder()
            .setTargetRotation(rotation)
            .setTargetAspectRatio(screenAspectRatio)
            .build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }
        val barcodeScanner = BarcodeScanning.getClient()
        val analysisUseCase = ImageAnalysis.Builder()
            .setTargetRotation(rotation)
            .setTargetAspectRatio(screenAspectRatio)
            .build().also {
                it.setAnalyzer(
                    ContextCompat.getMainExecutor(requireContext())
                ) { imageProxy ->
                    processImageProxy(barcodeScanner, imageProxy)
                }
            }
        val useCaseGroup = UseCaseGroup.Builder().addUseCase(previewUseCase).addUseCase(
            analysisUseCase
        ).build()

        camera = cameraProvider?.bindToLifecycle(
            this,
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build(),
            useCaseGroup
        )
    }

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    private fun processImageProxy(barcodeScanner: BarcodeScanner, imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            processImage(barcodeScanner, inputImage)
                .addOnFailureListener {
                    image.close()
                    imageProxy.close()
                    LogCat.e("processImageProxy: $it")
                }.addOnCompleteListener {
                    image.close()
                    imageProxy.close()
                }
        }
    }

    private fun processImage(barcodeScanner: BarcodeScanner, inputImage: InputImage): Task<List<Barcode>> {
        return barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodeList ->
                if (!barcodeList.isNullOrEmpty()) {
                    if (!barcodeList[0].rawValue.isNullOrEmpty()) {
                        cameraProvider?.unbindAll()
                        camera = null
                        val r = barcodeList[0].rawValue!!
                        addScanResult(r)
                        ScanResultDialog(r) {
                            startCamera()
                        }.show()
                    }
                }
            }
    }

    private fun addScanResult(value: String) {
        val results = LocalStorage.scanResults.toMutableList()
        results.remove(value)
        results.add(0, value)
        LocalStorage.scanResults = results
    }

    private fun DisplayMetrics.getAspectRatio(): Int {
        val ratio43Value = 4.0 / 3.0
        val ratio169Value = 16.0 / 9.0

        val previewRatio = max(this.widthPixels, this.heightPixels).toDouble() / min(
            this.widthPixels,
            this.heightPixels
        )
        if (abs(previewRatio - ratio43Value) <= abs(previewRatio - ratio169Value)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }
}