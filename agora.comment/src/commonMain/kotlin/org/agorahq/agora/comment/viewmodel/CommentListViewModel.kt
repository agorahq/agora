package org.agorahq.agora.comment.viewmodel

import org.agorahq.agora.core.api.view.ViewModel

data class CommentListViewModel(
        val comments: Sequence<CommentViewModel>
) : ViewModel
