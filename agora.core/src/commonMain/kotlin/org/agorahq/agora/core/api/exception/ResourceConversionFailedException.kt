package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.data.Resource

class ResourceConversionFailedException(
        resource: Resource,
        cause: Exception,
        private val msg: String = "Can't convert resource $resource to its corresponding view"
) : RuntimeException(msg, cause) {
    override fun toString() = msg
}
