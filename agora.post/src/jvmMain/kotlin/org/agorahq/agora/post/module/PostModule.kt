package org.agorahq.agora.post.module

import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.module.base.BaseModule
import org.agorahq.agora.post.domain.Post

@Suppress("EXPERIMENTAL_API_USAGE")
class PostModule(
        operations: Iterable<AnyContentOperation> = listOf()
) : BaseModule<Post>(operations, Post::class) {

    override val name: String = "Posts"

}