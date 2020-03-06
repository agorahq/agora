package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.resource.Resource

interface ResourceFilterPolicy<R : Resource> : Policy {

    operator fun invoke(resource: R): Boolean

    companion object {

        fun <R : Resource> create(fn: (resource: R) -> Boolean) = object : ResourceFilterPolicy<R> {
            override fun invoke(resource: R) = fn(resource)
        }
    }
}