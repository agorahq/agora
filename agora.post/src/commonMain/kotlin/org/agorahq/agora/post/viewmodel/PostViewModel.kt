package org.agorahq.agora.post.viewmodel

import org.agorahq.agora.core.api.view.ViewModel

data class PostViewModel(
        val id: String,
        val ownerId: String,
        val abstract: String,
        val excerpt: String,
        val title: String,
        val tags: String,
        val content: String,
        val publicationDate: String? = null,
        val isPublished: Boolean = false,
        val url: String? = null
) : ViewModel
