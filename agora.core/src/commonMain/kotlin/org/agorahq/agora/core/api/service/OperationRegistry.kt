package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation

interface OperationRegistry {

    val operations: Iterable<Operation<out Resource, out Any, out Any>>

    fun register(operation: Operation<out Resource, out Any, out Any>)

}
