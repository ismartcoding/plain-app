package com.ismartcoding.plain.data

interface ISelectOption {
    fun isSelected(): Boolean
    fun getText(): String
}

interface IFormItem {
    fun beforeSubmit()
    fun blurAndHideSoftInput()
    val hasError: Boolean
}
