package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Renderer
import org.agorahq.agora.core.api.operation.types.RendererDescriptor

class ShowItemListing : Renderer<Item>, RendererDescriptor<Item> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>): ElementSource<Item> {
        TODO("not implemented")
    }

    override fun createCommand(context: OperationContext<out Unit>, data: ElementSource<Item>): Command<String> {
        TODO("not implemented")
    }

    companion object : RendererDescriptor<Item> {
        override val name = "Show item listing"
        override val attributes = Attributes.create<Item, Unit, String>(
                urlClass = ItemURL::class,
                route = ItemURL.route,
                additionalAttributes = listOf(HasNavigationLink("/items", "Items")))
    }
}
