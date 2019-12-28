package org.agorahq.agora.core.module.base

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.module.Facet
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.ServerContext
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<D : DomainObject, S : ServerContext>(
        vararg facets: Facet
) : Module<D, S> {

    private val facets = facets.map {
        it::class to it
    }.toMap().toMutableMap()

    final override fun hasFacet(facetType: KClass<out Facet>): Boolean {
        return facets.containsKey(facetType)
    }

    final override fun <T : Facet> findFacet(facetType: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(facets[facetType] as T?)
    }

    override fun registerEndpoints(serverContext: S) {
    }
}