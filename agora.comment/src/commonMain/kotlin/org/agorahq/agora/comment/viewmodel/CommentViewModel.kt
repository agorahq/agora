package org.agorahq.agora.comment.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class CommentViewModel(
        override val context: OperationContext,
        val id: String? = null,
        val parentId: String,
        val content: String,
        val userId: String = "",
        val username: String = ""
) : ViewModel
