package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.internal.security.DefaultAuthorization

fun authorization(fn: AuthorizationBuilder.() -> Unit): Authorization {
    val builder = AuthorizationBuilder()
    fn(builder)
    return builder.build()
}
