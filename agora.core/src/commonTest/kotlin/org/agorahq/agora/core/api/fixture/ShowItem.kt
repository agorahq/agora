package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor

class ShowItem : ParameterizedRenderer<Item, ItemURL>, ParameterizedRendererDescriptor<Item, ItemURL> by Companion {

    override fun fetchResource(context: OperationContext<out ItemURL>): ElementSource<Item> {
        TODO()
    }

    override fun createCommand(
            context: OperationContext<out ItemURL>,
            data: ElementSource<Item>
    ) = Command.of {
        "foo"
    }

    companion object : ParameterizedRendererDescriptor<Item, ItemURL> {
        override val name = "Show item"
        override val attributes = Attributes.create<Item, ItemURL, String>(
                urlClass = ItemURL::class,
                route = ItemURL.route,
                additionalAttributes = listOf())
    }
}
