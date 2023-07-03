package com.ismartcoding.plain.web

import com.ismartcoding.plain.db.AppDatabase
import com.ismartcoding.plain.db.DSession
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

object SessionList {
    fun getItems(): List<DSession> {
        return AppDatabase.instance.sessionDao().getAll()
    }

    fun addOrUpdateAsync(clientId: String, updateItem: (DSession) -> Unit) {
        var item = AppDatabase.instance.sessionDao().getByClientId(clientId)
        var isInsert = false
        if (item == null) {
            item = DSession()
            item.clientId = clientId
            isInsert = true
        } else {
            item.updatedAt = Clock.System.now()
        }

        updateItem(item)

        if (isInsert) {
            AppDatabase.instance.sessionDao().insert(item)
        } else {
            AppDatabase.instance.sessionDao().update(item)
        }
    }

    fun deleteAsync(item: DSession) {
        AppDatabase.instance.sessionDao().delete(item)
    }
}