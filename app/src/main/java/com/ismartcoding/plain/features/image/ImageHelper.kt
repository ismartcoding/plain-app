package com.ismartcoding.plain.features.image

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.ismartcoding.lib.content.ContentWhere
import com.ismartcoding.lib.extensions.getLongValue
import com.ismartcoding.lib.extensions.getStringValue
import com.ismartcoding.lib.helpers.SearchHelper
import com.ismartcoding.plain.features.file.FileSortBy
import com.ismartcoding.plain.features.BaseContentHelper

object ImageHelper : BaseContentHelper() {
    override val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    override fun getProjection(): Array<String> {
        return arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.SIZE,
        )
    }

    override fun getWhere(query: String): ContentWhere {
        val where = ContentWhere()
        if (query.isNotEmpty()) {
            val queryGroups = SearchHelper.parse(query)
            queryGroups.forEach {
                if (it.name == "text") {
                    where.add("${MediaStore.Images.Media.TITLE} LIKE ?", "%${it.value}%")
                } else if (it.name == "ids") {
                    val ids = it.value.split(",")
                    if (ids.isNotEmpty()) {
                        where.addIn(MediaStore.Images.Media._ID, ids)
                    }
                }
            }
        }

        return where
    }

    fun search(context: Context, query: String, limit: Int, offset: Int, sortBy: FileSortBy): List<DImage> {
        val cursor = getSearchCursor(context, query, limit, offset, sortBy.toSortBy())
        val result = mutableListOf<DImage>()
        if (cursor?.moveToFirst() == true) {
            do {
                val id = cursor.getStringValue(MediaStore.Images.Media._ID)
                val title = cursor.getStringValue(MediaStore.Images.Media.TITLE)
                val size = cursor.getLongValue(MediaStore.Images.Media.SIZE)
                val path = cursor.getStringValue(MediaStore.Images.Media.DATA)
                result.add(DImage(id, title, path, size))
            } while (cursor.moveToNext())
        }
        return result
    }
}