package org.agorahq.agora.post.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class PostListViewModel(
        override val context: OperationContext,
        val posts: Sequence<PostViewModel>
) : ViewModel
