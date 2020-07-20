package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsPageElements
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.hexworks.cobalt.core.api.UUID

class ListComments : ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        TODO()
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = Command.of {
        "foo"
    }

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "List comments"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = CommentURL.root,
                urlClass = CommentURL::class,
                additionalAttributes = listOf(ShowsPageElements)
        )
    }
}

