package com.ismartcoding.plain.ui.views.richtext

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import java.util.regex.Pattern

data class Suggestion(val value: String)

data class SuggestionView(
    var pattern: Pattern, var isEnabled: Boolean,
    var colors: ColorStateList,
    @ColorInt var color: Int,
    var items: List<String>,
    var itemClick: (() -> Unit)? = null,
    var textChanged: (() -> Unit)? = null,
)
