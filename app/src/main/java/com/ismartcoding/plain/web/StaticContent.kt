package com.ismartcoding.plain.web

import com.ismartcoding.plain.LocalStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

private const val pathParameterName = "static-content-path-parameter"

private fun String?.combinePackage(resourcePackage: String?) = when {
    this == null -> resourcePackage
    resourcePackage == null -> this
    else -> "$this.$resourcePackage"
}

public fun Route.resources(resourcePackage: String? = null) {
    val packageName = staticBasePackage.combinePackage(resourcePackage)
    get("{$pathParameterName...}") {
        if (!LocalStorage.webConsoleEnabled) {
            call.response.status(HttpStatusCode.NotFound)
            return@get
        }
        val relativePath = call.parameters.getAll(pathParameterName)?.joinToString(File.separator) ?: return@get
        val content = call.resolveResource(relativePath, packageName)
        if (content != null) {
            call.respond(content)
        }
    }
}

public fun Route.defaultResource(resource: String, resourcePackage: String? = null) {
    val packageName = staticBasePackage.combinePackage(resourcePackage)
    get {
        if (!LocalStorage.webConsoleEnabled) {
            call.response.status(HttpStatusCode.NotFound)
            return@get
        }
        val content = call.resolveResource(resource, packageName)
        if (content != null) {
            call.respond(content)
        }
    }
}
