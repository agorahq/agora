package org.agorahq.agora.delivery.data

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class OauthUser {

    abstract val email: String
    abstract val firstName: String
    abstract val lastName: String

}

@Serializable
data class FacebookUser(
        override val email: String,
        @SerialName("first_name")
        override val firstName: String,
        @SerialName("last_name")
        override val lastName: String
) : OauthUser()

@Serializable
data class GoogleUser(
        override val email: String,
        @SerialName("given_name")
        override val firstName: String,
        @SerialName("family_name")
        override val lastName: String
) : OauthUser()