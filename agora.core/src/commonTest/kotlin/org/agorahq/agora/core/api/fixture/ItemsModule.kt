package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.base.BaseModule
import org.agorahq.agora.core.api.operation.Operation

class ItemsModule(
        initialOperations: Iterable<Operation<out Resource, out Any, out Any>> = listOf()
) : BaseModule<Comment>(
        initialOperations = initialOperations,
        resourceClass = Comment::class
) {

    override val name = "Items"
}
