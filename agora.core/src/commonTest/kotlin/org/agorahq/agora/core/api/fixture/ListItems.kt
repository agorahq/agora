package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Renderer
import org.agorahq.agora.core.api.operation.types.RendererDescriptor

class ListItems(
        private val items: Iterable<Item>
) : Renderer<Item>, RendererDescriptor<Item> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>): ElementSource<Item> = ElementSource.ofIterable(items)

    override fun createCommand(context: OperationContext<out Unit>, data: ElementSource<Item>) = Command.of {
        data.asSequence().joinToString { it.id.toString() }
    }

    override fun toString() = name

    companion object : RendererDescriptor<Item> {
        override val name = "List items"
        override val attributes = Attributes.create<Item, Unit, String>(
                urlClass = ItemURL::class,
                route = ItemURL.root,
                additionalAttributes = listOf())
    }
}
