package com.ismartcoding.plain.features.tag

import com.ismartcoding.plain.data.IData
import com.ismartcoding.plain.data.enums.TagType
import com.ismartcoding.plain.db.DTagRelation
import com.ismartcoding.plain.features.audio.DAudio
import com.ismartcoding.plain.features.image.DImage
import com.ismartcoding.plain.features.video.DVideo

data class TagRelationStub(
    var key: String = "",
    var title: String = "",
    var size: Long = 0,
) {
    fun toTagRelation(tagId: String, tagType: TagType): DTagRelation {
        val stub = this
        return DTagRelation(tagId, stub.key, tagType.value).apply {
            title = stub.title
            size = stub.size
        }
    }

    companion object {
        fun create(data: IData): TagRelationStub {
            return when (data) {
                is DImage -> {
                    TagRelationStub(data.id, data.title, data.size)
                }
                is DVideo -> {
                    TagRelationStub(data.id, data.title, data.size)
                }
                is DAudio -> {
                    TagRelationStub(data.id, data.title, data.size)
                }
                else -> {
                    TagRelationStub(data.id)
                }
            }
        }
    }
}