package org.agorahq.agora.comment.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class CommentListViewModel(
        override val context: OperationContext,
        val comments: Sequence<CommentViewModel>
) : ViewModel
