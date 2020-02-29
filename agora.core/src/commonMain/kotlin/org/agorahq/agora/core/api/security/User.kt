package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.UserMetadata

interface User : UserMetadata {

    val firstName: String
    val lastName: String
    val roles: Set<Role>

    companion object
}