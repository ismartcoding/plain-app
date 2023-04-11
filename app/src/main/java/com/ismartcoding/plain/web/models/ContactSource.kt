package com.ismartcoding.plain.web.models

import com.ismartcoding.plain.features.contact.DContactSource

data class ContactSource(var name: String, var type: String)

fun DContactSource.toModel(): ContactSource {
    return ContactSource(name, type)
}
