package org.agorahq.agora.delivery.data

import kotlinx.serialization.Serializable
import org.agorahq.agora.core.api.data.Message

@Serializable
sealed class SessionState {

    abstract val message: Message?

    abstract fun withMessage(message: Message?): SessionState
}

@Serializable
data class AnonState(
        override val message: Message? = null
) : SessionState() {

    override fun withMessage(message: Message?) = copy(message = message)
}

@Serializable
data class AuthenticatedUserState(
        override val message: Message? = null,
        val principal: UserPrincipal
) : SessionState() {

    override fun withMessage(message: Message?) = copy(message = message)
}

@Serializable
data class RegisteringState(
        override val message: Message? = null,
        val oauthUser: OauthUser
) : SessionState() {

    override fun withMessage(message: Message?) = copy(message = message)
}