package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.Entity
import org.agorahq.agora.core.api.security.User
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KProperty1

interface QueryService<E : Entity> {


    fun findAll(): Sequence<E>

    fun <T: Any> findBy(property: KProperty1<E, T>, value: T): Sequence<E>

    fun <T: Any> findOneBy(property: KProperty1<E, T>, value: T): Maybe<E>

    fun findById(id: UUID): Maybe<E>

    companion object {
        val x: KProperty1<User, UUID> = User::id
    }
}