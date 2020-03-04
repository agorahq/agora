package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.internal.security.DefaultOperationDescriptor
import kotlin.reflect.KClass

interface OperationDescriptor<R : Resource, C : OperationContext, U : Any> {

    val name: String
    val resourceClass: KClass<R>
    val type: OperationType<R, C, U>
    val urlClass: KClass<out ResourceURL<R>>
    val route: String

    companion object {

        fun <R : Resource, C : OperationContext, U : Any> create(
                name: String,
                resourceClass: KClass<R>,
                type: OperationType<R, C, U>,
                route: String,
                urlClass: KClass<ResourceURL<R>>
        ): OperationDescriptor<R, C, U> = DefaultOperationDescriptor(
                name = name,
                resourceClass = resourceClass,
                type = type,
                route = route,
                urlClass = urlClass)

    }


}