package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.data.Entity
import kotlin.reflect.KClass

class EntityNotFoundException(
        type: KClass<out Entity>,
        key: String,
        private val msg: String = "The entity ${type.simpleName} with the given key $key was not found."
) : RuntimeException(msg) {
    override fun toString() = msg
}
