package org.agorahq.agora.core.api.document

import org.agorahq.agora.core.api.resource.Resource

interface ResourceURL<R : Resource> {

    fun generate(): String

    fun matches(resource: R): Boolean

    companion object
}