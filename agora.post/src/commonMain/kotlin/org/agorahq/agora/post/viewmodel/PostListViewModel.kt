package org.agorahq.agora.post.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel

data class PostListViewModel(
        val posts: Sequence<PostViewModel>,
        val context: OperationContext,
        override val owner: User = User.ANONYMOUS
) : ViewModel