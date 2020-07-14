package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Facets
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext

data class DefaultOperationDescriptor<R : Resource, I: Any, O : Any>(
        override val name: String,
        override val facets: Facets<R, I, O>
) : OperationDescriptor<R, I, O> {

    override fun toString() = OperationDescriptor.toString(this)

}
