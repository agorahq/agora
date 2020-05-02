package org.agorahq.agora.delivery.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacebookUser(
        override val id: String,
        override val email: String,
        @SerialName("first_name")
        override val firstName: String,
        @SerialName("last_name")
        override val lastName: String
) : OauthUser
