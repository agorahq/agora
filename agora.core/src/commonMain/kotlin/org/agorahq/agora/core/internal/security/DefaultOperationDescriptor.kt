package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType
import kotlin.reflect.KClass

data class DefaultOperationDescriptor<R : Resource, T : OperationType>(
        override val name: String,
        override val klass: KClass<out Operation<out R, out OperationContext, out Any>>,
        override val type: OperationType
) : OperationDescriptor<R, T>