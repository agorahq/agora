package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Attribute

inline fun <reified R : Resource, reified I : Any, reified O : Any> SiteMetadata.findMatchingOperations(
        vararg attributes: Attribute
) = operationRegistry.findMatchingOperations<R, I, O>(*attributes)

inline fun <reified A : Attribute> SiteMetadata.findOperationsWithAttribute() =
        operationRegistry.findOperationsWithAttribute<A>()
