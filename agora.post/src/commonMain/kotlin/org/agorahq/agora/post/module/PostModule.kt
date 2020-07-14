package org.agorahq.agora.post.module

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.base.BaseModule
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.viewmodel.PostViewModel

@Suppress("EXPERIMENTAL_API_USAGE", "UNCHECKED_CAST")
class PostModule(
        operations: Iterable<Operation<out Resource, out Any, out Any>> = listOf()
) : BaseModule<Post, PostViewModel>(operations, Post::class, PostViewModel::class) {

    override val name: String = "Posts"

}
