package org.agorahq.agora.delivery.extensions

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

private val json = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true))

fun <T : Any> String.deserializeTo(serializer: KSerializer<T>): T {
    return json.parse(serializer, this)
}