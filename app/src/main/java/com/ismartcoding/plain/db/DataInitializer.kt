package com.ismartcoding.plain.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ismartcoding.lib.extensions.cut
import com.ismartcoding.lib.helpers.StringHelper
import com.ismartcoding.plain.R
import com.ismartcoding.plain.data.enums.TagType
import kotlinx.datetime.Clock

class DataInitializer(val context: Context, val db: SupportSQLiteDatabase) {
    private data class TagItem(val nameKey: Int, val type: TagType)
    private data class MessageItem(val content: String, val isMe: Boolean)

    private val now = Clock.System.now().toString()

    private val tags = arrayOf(
        TagItem(R.string.favorites, TagType.AUDIO),
        TagItem(R.string.children_songs, TagType.AUDIO),
        TagItem(R.string.light_music, TagType.AUDIO),
        TagItem(R.string.movie, TagType.VIDEO),
        TagItem(R.string.family, TagType.IMAGE),
        TagItem(R.string.important, TagType.SMS),
        TagItem(R.string.todo, TagType.SMS),
        TagItem(R.string.family, TagType.CONTACT),
        TagItem(R.string.important, TagType.CONTACT),
        TagItem(R.string.inspirations, TagType.NOTE),
        TagItem(R.string.personal, TagType.NOTE),
        TagItem(R.string.work, TagType.NOTE),
    )

    fun insertTags() {
        tags.forEach { tag ->
            db.insert("tags", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
                put("id", StringHelper.shortUUID())
                put("name", context.resources.getString(tag.nameKey))
                put("type", tag.type.value)
                put("count", 0)
                put("created_at", now)
                put("updated_at", now)
            })
        }
    }

    fun insertNotes() {
        setOf(R.string.note_sample1).forEach {
            val sample = context.resources.getString(it)
            db.insert("notes", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
                put("id", StringHelper.shortUUID())
                put("title", sample.cut(100).replace("\n", ""))
                put("content", sample)
                put("created_at", now)
                put("updated_at", now)
            })
        }
    }

    fun insertWelcome() {
        setOf<MessageItem>(
            MessageItem("""{"type":"text","value":{"text":"${context.resources.getString(R.string.welcome_text)}"}}""", false),
        ).forEach {
            db.insert("chats", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
                put("id", StringHelper.shortUUID())
                put("is_me", it.isMe)
                put("content", it.content)
                put("created_at", now)
                put("updated_at", now)
            })
        }
    }
}