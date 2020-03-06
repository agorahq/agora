package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.data.Entity
import org.agorahq.agora.core.api.service.QueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KProperty1

abstract class BaseQueryService<E : Entity>(
        private val objects: MutableMap<UUID, E>
) : QueryService<E> {

    override fun findAll(): Sequence<E> = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

    override fun <T : Any> findBy(property: KProperty1<E, T>, value: T): Sequence<E> {
        return objects.values.filter { property.get(it) == value }.asSequence()
    }

    override fun <T : Any> findOneBy(property: KProperty1<E, T>, value: T): Maybe<E> {
        return Maybe.ofNullable(objects.values.firstOrNull { property.get(it) == value })
    }
}