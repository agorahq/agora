package org.agorahq.agora.delivery.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleUser(
        override val id: String,
        override val email: String,
        @SerialName("given_name")
        override val firstName: String,
        @SerialName("family_name")
        override val lastName: String
) : OauthUser