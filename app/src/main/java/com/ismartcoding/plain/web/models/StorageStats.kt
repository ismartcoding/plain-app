package com.ismartcoding.plain.web.models

import com.ismartcoding.plain.features.file.DStorageStats

data class StorageStats(val totalBytes: Long = 0L, val freeBytes: Long = 0L)

fun DStorageStats.toModel(): StorageStats {
    return StorageStats(totalBytes, freeBytes)
}