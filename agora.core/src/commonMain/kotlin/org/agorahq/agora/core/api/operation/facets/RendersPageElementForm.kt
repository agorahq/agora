package org.agorahq.agora.core.api.operation.facets

import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Attribute
import kotlin.reflect.KClass

data class RendersPageElementForm<PE : PageElement>(
        val pageElementClass: KClass<PE>
) : Attribute
