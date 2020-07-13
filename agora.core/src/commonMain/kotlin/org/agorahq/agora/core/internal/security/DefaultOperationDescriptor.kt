package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType
import org.agorahq.agora.core.api.operation.context.OperationContext
import kotlin.reflect.KClass

data class DefaultOperationDescriptor<R : Resource, C : OperationContext, T : Any>(
        override val name: String,
        override val resourceClass: KClass<R>,
        override val type: OperationType<R, C, T>,
        override val route: String,
        override val urlClass: KClass<out ResourceURL<R>>,
        override val facets: Iterable<Facet>
) : OperationDescriptor<R, C, T>
