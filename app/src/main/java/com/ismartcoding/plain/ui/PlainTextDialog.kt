package com.ismartcoding.plain.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.ismartcoding.lib.extensions.isGestureNavigationBar
import com.ismartcoding.lib.extensions.navigationBarHeight
import com.ismartcoding.plain.databinding.DialogPlainTextBinding
import com.ismartcoding.plain.ui.extensions.onBack

class PlainTextDialog(val title: String, val content: String) : BaseDialog<DialogPlainTextBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTransparentBar(view)

        if (!requireContext().isGestureNavigationBar()) {
            binding.content.updateLayoutParams<FrameLayout.LayoutParams> {
                bottomMargin = navigationBarHeight
            }
        }

        binding.topAppBar.toolbar.title = title
        binding.topAppBar.toolbar.onBack {
            dismiss()
        }
        binding.content.text = content
    }
}