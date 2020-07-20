package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor

class SaveComment : Procedure<Comment, String>, ProcedureDescriptor<Comment, String> by Companion {

    override fun fetchResource(context: OperationContext<out String>): ElementSource<Comment> {
        TODO()
    }

    override fun createCommand(context: OperationContext<out String>, data: ElementSource<Comment>): Command<Unit> {
        TODO()
    }

    companion object : ProcedureDescriptor<Comment, String> {
        override val name = "List comments"
        override val attributes = Attributes.create<Comment, String, Unit>(
                route = CommentURL.root,
                urlClass = CommentURL::class
        )
    }
}

