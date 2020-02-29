package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import kotlin.reflect.KClass

interface Operation<R : Resource, C: OperationContext, T: Any> {

    val resourceClass: KClass<R>
    val route: String

    fun C.execute(): T
}
