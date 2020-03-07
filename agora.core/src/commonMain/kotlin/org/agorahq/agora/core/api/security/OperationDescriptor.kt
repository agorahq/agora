package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.internal.security.DefaultOperationDescriptor
import kotlin.reflect.KClass

interface OperationDescriptor<R : Resource, C : OperationContext, T : Any> {

    val name: String
    val resourceClass: KClass<R>
    val type: OperationType<R, C, T>
    val urlClass: KClass<out ResourceURL<R>>
    val route: String

    companion object {

        fun <R : Resource, C : OperationContext, T : Any> create(
                name: String,
                resourceClass: KClass<R>,
                type: OperationType<R, C, T>,
                route: String,
                urlClass: KClass<ResourceURL<R>>
        ): OperationDescriptor<R, C, T> = DefaultOperationDescriptor(
                name = name,
                resourceClass = resourceClass,
                type = type,
                route = route,
                urlClass = urlClass)

        fun <R : Resource, C : OperationContext, T : Any> toString(descriptor: OperationDescriptor<R, C, T>): String {
            with(descriptor) {
                return "OperationDescriptor(name=$name, " +
                        "resourceClass=${resourceClass.simpleName}, " +
                        "type=$type, " +
                        "route=$route, " +
                        "urlClass=$urlClass)"
            }
        }

    }


}