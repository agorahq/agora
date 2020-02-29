package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.resource.Resource
import kotlin.reflect.KClass

class ResourceNotFoundException(
        type: KClass<out Resource>,
        key: String
) : RuntimeException("The resource ${type::class.simpleName} with the given $key was not found.")