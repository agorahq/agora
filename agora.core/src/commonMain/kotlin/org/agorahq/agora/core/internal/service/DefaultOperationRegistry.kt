package org.agorahq.agora.core.internal.service

import kotlinx.collections.immutable.persistentListOf
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.service.OperationRegistry

@Suppress("UNCHECKED_CAST")
class DefaultOperationRegistry : OperationRegistry {

    private var operationList = persistentListOf<Operation<out Resource, out Any, out Any>>()

    override val operations: Iterable<Operation<out Resource, out Any, out Any>>
        get() = operationList

    override fun register(operation: Operation<out Resource, out Any, out Any>) {
        operationList = operationList.add(operation)
    }
}
