package org.agorahq.agora.core.api.extensions

fun String.toSlug() = toLowerCase()
        .replace("\r\n", " ")
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString("-")
        .replace("-+".toRegex(), "-")