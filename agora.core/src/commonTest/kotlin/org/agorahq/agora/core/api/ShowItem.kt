package org.agorahq.agora.core.api

import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.PageRenderer
import org.agorahq.agora.core.api.operation.context.PageURLContext

class ShowItem : Operation<Item, PageURLContext<Item>, String>, OperationDescriptor<Item, PageURLContext<Item>, String> by Companion {

    override fun PageURLContext<Item>.createCommand() = {
        "foo"
    }.toCommand()

    companion object : OperationDescriptor<Item, PageURLContext<Item>, String> {
        override val name = "Show Item"
        override val resourceClass = Item::class
        override val type = PageRenderer(Item::class)
        override val urlClass = ItemURL::class
        override val route = ItemURL.route
    }
}
