package org.agorahq.agora.post.module

import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.module.base.BaseModule
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.viewmodel.PostViewModel

@Suppress("EXPERIMENTAL_API_USAGE")
class PostModule(
        operations: Iterable<AnyContentOperation> = listOf()
) : BaseModule<Post, PostViewModel>(operations, Post::class, PostViewModel::class) {

    override val name: String = "Posts"

}