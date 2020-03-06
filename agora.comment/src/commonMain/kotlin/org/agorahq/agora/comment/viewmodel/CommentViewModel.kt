package org.agorahq.agora.comment.viewmodel

import org.agorahq.agora.core.api.view.ViewModel

data class CommentViewModel(
        val id: String? = null,
        val parentId: String,
        val content: String,
        val userId: String = "",
        val username: String = ""
) : ViewModel