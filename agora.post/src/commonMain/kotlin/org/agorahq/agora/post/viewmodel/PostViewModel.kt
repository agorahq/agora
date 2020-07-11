package org.agorahq.agora.post.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class PostViewModel(
        val ownerId: String,
        val shortDescription: String,
        val excerpt: String,
        val publicationDate: String,
        val url: String,
        val title: String,
        val tags: Iterable<String>,
        val content: String,
        val context: OperationContext,
        val renderedPageElements: String
) : ViewModel
