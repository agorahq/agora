package org.agorahq.agora.core.module.base

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.module.Facet
import org.agorahq.agora.core.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<D : DomainObject>(
        facets: Iterable<Facet>
) : Module<D> {

    private val facets = facets.map {
        it::class to it
    }.toMap().toMutableMap()

    final override fun containsFacet(facet: Facet): Boolean {
        return facets.any { it.value === facet }
    }

    final override fun hasFacet(facetType: KClass<out Facet>): Boolean {
        return facets.filterKeys {
            facetType.isSuperclassOf(it)
        }.isNotEmpty()
    }

    final override fun <T : Facet> findFacet(facetType: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(filterFacets(facetType).firstOrNull())
    }

    final override fun <T : Facet> filterFacets(facetType: KClass<T>): Iterable<T> {
        return facets.filterKeys { facetType.isSuperclassOf(it) }.map { it.value as T }
    }

}