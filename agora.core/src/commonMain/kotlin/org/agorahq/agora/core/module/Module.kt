package org.agorahq.agora.core.module

import org.agorahq.agora.core.domain.DomainObject
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface Module<D : DomainObject, S : ServerContext> {

    val name: String

    fun hasFacet(facetType: KClass<out Facet>): Boolean

    fun <T : Facet> findFacet(facetType: KClass<T>): Maybe<T>

    fun registerEndpoints(serverContext: S)

}