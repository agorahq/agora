package org.agorahq.agora.core.api

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext

class ShowItem : Operation<Item, ResourceURL<Item>, String>, OperationDescriptor<Item, ResourceURL<Item>, String> by Companion {

    override fun fetchResource(context: OperationContext<out ResourceURL<Item>>): ElementSource<Item> {
        TODO()
    }

    override fun createCommand(context: OperationContext<out ResourceURL<Item>>, data: ElementSource<Item>) = {
        "foo"
    }.toCommand()

    companion object : OperationDescriptor<Item, ResourceURL<Item>, String> by OperationDescriptor.create(
            name = "Show Item",
            urlClass = ItemURL::class,
            route = ItemURL.route,
            facets = listOf()
    )
}
