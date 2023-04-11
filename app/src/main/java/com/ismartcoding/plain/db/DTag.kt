package com.ismartcoding.plain.db

import androidx.room.*
import com.ismartcoding.lib.helpers.StringHelper
import com.ismartcoding.plain.data.IData
import kotlinx.datetime.*

@Entity(tableName = "tags")
data class DTag(
    @PrimaryKey override var id: String = StringHelper.shortUUID()
) : IData, DEntityBase() {
    var name: String = ""
    var type: Int = 0
    var count: Int = 0
}

@Dao
interface TagDao {
    @Query("SELECT * FROM tags WHERE `type`=:type")
    fun getAll(type: Int): List<DTag>

    @Query("SELECT * FROM tags WHERE id=:id")
    fun getById(id: String): DTag?

    @Insert
    fun insert(vararg item: DTag)

    @Update
    fun update(vararg item: DTag)

    @Query("DELETE FROM tags WHERE id=:id")
    fun delete(id: String)
}

