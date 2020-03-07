package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.security.Group

interface GroupFilterPolicy : Policy {

    operator fun invoke(group: Group): Boolean

    companion object {

        fun create(fn: (group: Group) -> Boolean) = object : GroupFilterPolicy {
            override fun invoke(group: Group) = fn(group)
        }
    }
}