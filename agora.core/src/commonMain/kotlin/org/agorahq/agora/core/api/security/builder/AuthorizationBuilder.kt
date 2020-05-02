package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.internal.security.DefaultAuthorization

class AuthorizationBuilder {

    private val roles = mutableListOf<Role>()
    private val groups = mutableListOf<Group>()

    fun roles(fn: RolesBuilder.() -> Unit) {
        val builder = RolesBuilder()
        fn(builder)
        roles.addAll(builder.build())
    }

    fun groups(fn: GroupsBuilder.() -> Unit) {
        val builder = GroupsBuilder()
        fn(builder)
        groups.addAll(builder.build())
    }

    fun build(): Authorization = DefaultAuthorization(
            roles = roles.toList(),
            groups = groups.toList())


}