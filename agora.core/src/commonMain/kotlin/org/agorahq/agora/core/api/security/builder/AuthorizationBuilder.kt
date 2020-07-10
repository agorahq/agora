package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.internal.security.DefaultAuthorization

class AuthorizationBuilder {

    private val roles = mutableListOf<Role>()

    fun roles(fn: RolesBuilder.() -> Unit) {
        val builder = RolesBuilder()
        fn(builder)
        roles.addAll(builder.build())
    }

    // TODO: check for conflicting permissions!
    fun build(): Authorization = DefaultAuthorization(
            roles = roles.toList()
    )

}
