package org.agorahq.agora.core.api.module.base

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.platform.isSubclassOf
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<R : Resource>(
        initialOperations: Iterable<Operation<out Resource, out Any, out Any>>,
        override val resourceClass: KClass<R>
) : Module<R> {

    override val operations = initialOperations as Iterable<AnyOperation>

    private val operationLookup = (initialOperations as Iterable<AnyOperation>).map {
        it::class to it
    }.toMap().toMutableMap()

    override fun supportsResource(resource: Resource) = isSubclassOf(resource::class, resourceClass)

}
