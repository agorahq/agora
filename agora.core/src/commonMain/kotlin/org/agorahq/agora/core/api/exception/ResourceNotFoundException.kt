package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.data.Resource
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

class ResourceNotFoundException(
        type: KClass<out Resource>,
        key: String
) : RuntimeException("The resource ${type::class.simpleName} with the given $key was not found.")