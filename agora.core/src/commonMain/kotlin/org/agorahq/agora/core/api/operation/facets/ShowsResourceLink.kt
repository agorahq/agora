package org.agorahq.agora.core.api.operation.facets

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Attribute
import kotlin.reflect.KClass

data class ShowsResourceLink<R : Resource>(
        val resourceClass: KClass<R>
) : Attribute
