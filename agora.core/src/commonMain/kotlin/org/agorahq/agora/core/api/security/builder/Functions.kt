package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.security.DefaultAuthorization

fun authorization(fn: AuthorizationBuilder.() -> Unit): Authorization {
    return DefaultAuthorization()
}

typealias UserFilter = (currentUser: User, owner: User) -> Boolean

typealias GroupFilter = () -> Group

object saving

val allGroups = object : Group {
    override val name = "all"
}

val ownOnly = { currentUser: User, owner: User ->
    currentUser == owner
}

val all = { _: User, _: User ->
    true
}