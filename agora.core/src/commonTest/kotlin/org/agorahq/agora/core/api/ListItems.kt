package org.agorahq.agora.core.api

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext

class ListItems(
        private val items: Iterable<Item>
) : Operation<Item, Unit, String>, OperationDescriptor<Item, Unit, String> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>): ElementSource<Item> = ElementSource.ofIterable(items)

    override fun createCommand(context: OperationContext<out Unit>, data: ElementSource<Item>) = {
        data.asSequence().joinToString { it.id.toString() }
    }.toCommand()

    override fun toString() = name

    companion object : OperationDescriptor<Item, Unit, String> by OperationDescriptor.create(
            name = "List Items",
            urlClass = ItemURL::class,
            route = ItemURL.root,
            facets = listOf()
    )
}
