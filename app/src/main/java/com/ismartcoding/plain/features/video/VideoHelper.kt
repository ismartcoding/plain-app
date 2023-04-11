package com.ismartcoding.plain.features.video

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.ismartcoding.lib.content.ContentWhere
import com.ismartcoding.lib.extensions.getLongValue
import com.ismartcoding.lib.extensions.getStringValue
import com.ismartcoding.lib.helpers.SearchHelper
import com.ismartcoding.plain.features.file.FileSortBy
import com.ismartcoding.plain.features.BaseContentHelper

object VideoHelper : BaseContentHelper() {
    override val uriExternal: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    override fun getProjection(): Array<String> {
        return arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION,
        )
    }

    override fun getWhere(query: String): ContentWhere {
        val where = ContentWhere()
        where.add("${MediaStore.Video.Media.DURATION}>0")
        if (query.isNotEmpty()) {
            val queryGroups = SearchHelper.parse(query)
            queryGroups.forEach {
                if (it.name == "text") {
                    where.add("${MediaStore.Video.Media.TITLE} LIKE ?", "%${it.value}%")
                } else if (it.name == "ids") {
                    val ids = it.value.split(",")
                    if (ids.isNotEmpty()) {
                        where.addIn(MediaStore.Video.Media._ID, ids)
                    }
                }
            }
        }

        return where
    }

    fun search(context: Context, query: String, limit: Int, offset: Int, sortBy: FileSortBy): List<DVideo> {
        val cursor = getSearchCursor(context, query, limit, offset, sortBy.toSortBy())
        val result = mutableListOf<DVideo>()
        if (cursor?.moveToFirst() == true) {
            do {
                val id = cursor.getStringValue(MediaStore.Video.Media._ID)
                val title = cursor.getStringValue(MediaStore.Video.Media.TITLE)
                val size = cursor.getLongValue(MediaStore.Video.Media.SIZE)
                val duration = cursor.getLongValue(MediaStore.Video.Media.DURATION) / 1000
                val path = cursor.getStringValue(MediaStore.Video.Media.DATA)
                result.add(DVideo(id, title, path, duration, size))
            } while (cursor.moveToNext())
        }
        return result
    }
}