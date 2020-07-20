package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.api.operation.OperationDescriptor

fun operations(vararg operations: AnyOperationDescriptor) = operations.toList()

inline fun <reified A : Attribute> OperationDescriptor<out Resource, out Any, out Any>.filterAttributes(): List<A> {
    return attributes.filterIsInstance<A>()
}
