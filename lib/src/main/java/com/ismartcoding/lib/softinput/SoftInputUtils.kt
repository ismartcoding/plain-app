package com.ismartcoding.lib.softinput

import android.app.Activity
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

fun Activity.hideSoftInput() {
    currentFocus?.let {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } ?: let {
        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.ime())
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

fun EditText.hideSoftInput() {
    ViewCompat.getWindowInsetsController(this)?.hide(WindowInsetsCompat.Type.ime())
}

fun Activity.hasSoftInput(): Boolean {
    return window.hasSoftInput()
}

fun Window.hasSoftInput(): Boolean {
    return ViewCompat.getRootWindowInsets(decorView)?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
}

fun Fragment.hasSoftInput(): Boolean {
    return requireActivity().hasSoftInput()
}

fun Activity.getSoftInputHeight(): Int {
    val softInputHeight = ViewCompat.getRootWindowInsets(window.decorView)?.getInsets(WindowInsetsCompat.Type.ime())?.bottom
    return softInputHeight ?: 0
}

fun Fragment.getSoftInputHeight(): Int {
    return requireActivity().getSoftInputHeight()
}
