package org.agorahq.agora.core.api.extensions

import org.hexworks.cobalt.core.api.UUID

fun String.toSlug() = toLowerCase()
        .replace("\r\n", " ")
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString("-")
        .replace("-+".toRegex(), "-")

fun String.toUUID() = UUID.fromString(this)