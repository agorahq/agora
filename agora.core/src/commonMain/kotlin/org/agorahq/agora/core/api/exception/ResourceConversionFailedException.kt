package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.resource.Resource

class ResourceConversionFailedException(
        resource: Resource, cause: Exception
) : RuntimeException("Can't convert resource $resource to its corresponding view", cause)