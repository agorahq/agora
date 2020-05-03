package org.agorahq.agora.delivery.extensions

import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.delivery.data.AgoraSession
import org.agorahq.agora.delivery.data.SessionState
import org.agorahq.agora.delivery.security.JWTService

val AgoraSession.state: SessionState
    get() = JWTService.extractStateFrom(this.token)

fun AgoraSession.withMessage(message: Message?) = AgoraSession.fromState(state.withMessage(message))