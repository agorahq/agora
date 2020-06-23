package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.security.User

val User.isAnonymous: Boolean
    get() = this === User.ANONYMOUS

val User.isAuthenticated: Boolean
    get() = isAnonymous.not()
