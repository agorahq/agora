package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.security.User

/**
 * Contains the *essentials* which are required for every user in the system.
 */
interface UserMetadata : Entity {

    val email: String
    val username: String

    fun toUser(): User

    companion object
}