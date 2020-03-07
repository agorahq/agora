package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.api.security.User

val UserMetadata.isAnonymous: Boolean
    get() = this === User.ANONYMOUS

val UserMetadata.isAuthenticated: Boolean
    get() = isAnonymous.not()