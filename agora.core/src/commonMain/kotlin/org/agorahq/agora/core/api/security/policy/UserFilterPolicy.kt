package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.security.User

interface UserFilterPolicy : Policy {

    operator fun invoke(currentUser: User, owner: User): Boolean

    companion object {

        fun create(fn: (currentUser: User, owner: User) -> Boolean) = object : UserFilterPolicy {
            override fun invoke(currentUser: User, owner: User) = fn(currentUser, owner)
        }
    }
}