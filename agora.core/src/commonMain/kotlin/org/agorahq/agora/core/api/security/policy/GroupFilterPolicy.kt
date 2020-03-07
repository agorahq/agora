package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.security.User

interface GroupFilterPolicy : Policy {

    operator fun invoke(user: User): Boolean

    companion object {

        fun create(fn: (user: User) -> Boolean) = object : GroupFilterPolicy {
            override fun invoke(user: User) = fn(user)
        }
    }
}