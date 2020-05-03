package org.agorahq.agora.core.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Message(
        val text: String,
        val type: MessageType
)

enum class MessageType {
    INFO,
    SUCCESS,
    WARNING,
    DANGER
}