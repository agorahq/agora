package org.agorahq.agora.delivery.data

import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.delivery.security.JWTService

data class AgoraSession(
        val token: String
) {

    fun withState(state: SessionState) = AgoraSession(JWTService.createJWSFrom(state))

    companion object {

        fun anon() = AgoraSession(JWTService.createJWSFrom(AnonState()))

        fun fromState(state: SessionState) = AgoraSession(JWTService.createJWSFrom(state))

        fun fromUser(user: User) = fromState(
                AuthenticatedUserState(
                        principal = UserPrincipal.fromUser(user))
        )


        fun fromUserPrincipal(userPrincipal: UserPrincipal) = fromState(
                AuthenticatedUserState(
                        principal = userPrincipal)
        )

        fun fromOauthUser(oauthUser: OauthUser) = fromState(
                RegisteringState(
                        oauthUser = oauthUser)
        )
    }
}