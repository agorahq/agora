package org.agorahq.agora.delivery.data

import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.delivery.security.JWTService

data class Session(
        val token: String
) {

    fun toUser(): User = JWTService.createUserFrom(token).toUser()

    companion object {

        fun fromUser(user: User) = Session(JWTService.createJWSFrom(user))

        fun fromOauthUser(oauthUser: OauthUser) = Session(JWTService.createJWSFrom(oauthUser.toUser()))
    }
}