package org.agorahq.agora.post.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class PostListViewModel(
        val posts: Sequence<PostViewModel>,
        val context: OperationContext
) : ViewModel