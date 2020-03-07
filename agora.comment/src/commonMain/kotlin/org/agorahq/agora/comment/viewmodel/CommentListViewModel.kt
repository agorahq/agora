package org.agorahq.agora.comment.viewmodel

import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel

data class CommentListViewModel(
        val comments: Sequence<CommentViewModel>,
        override val owner: User
) : ViewModel