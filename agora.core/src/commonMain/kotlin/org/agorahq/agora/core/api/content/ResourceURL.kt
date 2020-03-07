package org.agorahq.agora.core.api.content

import org.agorahq.agora.core.api.resource.Resource

interface ResourceURL<R : Resource> {

    fun generate(): String

    fun matches(resource: R): Boolean

    companion object {

        fun <R : Resource> toString(resourceURL: ResourceURL<R>) = resourceURL.generate()
    }
}