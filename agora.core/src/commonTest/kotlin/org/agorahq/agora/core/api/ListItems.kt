package org.agorahq.agora.core.api

import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.PageListRenderer
import org.agorahq.agora.core.api.operation.context.OperationContext

class ListItems(
        private val items: Iterable<Item>
) : Operation<Item, OperationContext, String>, OperationDescriptor<Item, OperationContext, String> by Companion {

    private val operation = this

    override fun OperationContext.createCommand() = {
        authorization.tryAuthorize(user, operation)
        items.filter { item -> authorization.hasResourceAccess(user, item) }.joinToString { it.id.toString() }
    }.toCommand()

    override fun toString() = name

    companion object : OperationDescriptor<Item, OperationContext, String> {
        override val name = "List Items"
        override val resourceClass = Item::class
        override val type = PageListRenderer(Item::class)
        override val urlClass = ItemURL::class
        override val route = ItemURL.root
    }
}
