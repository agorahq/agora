package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.internal.security.DefaultOperationDescriptor
import kotlin.reflect.KClass

interface OperationDescriptor<R : Resource, T : OperationType> {

    val name: String
    val klass: KClass<out Operation<out R, out OperationContext, out Any>>
    val type: OperationType

    companion object {

        fun <R : Resource, T : OperationType> create(
                name: String,
                klass: KClass<out Operation<out R, out OperationContext, out Any>>,
                type: T
        ): OperationDescriptor<R, T> {
            return DefaultOperationDescriptor(name, klass, type)
        }
    }
}