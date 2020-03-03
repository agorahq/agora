package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.data.Entity
import kotlin.reflect.KClass

class EntityNotFoundException(
        type: KClass<out Entity>,
        key: String
) : RuntimeException("The entity ${type::class.simpleName} with the given $key was not found.")