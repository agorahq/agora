package org.agorahq.agora.post.extensions

import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.module.context.ResourceListingContext
import org.agorahq.agora.post.domain.Post

typealias PostRenderContext = ResourceContext<Post>

typealias PostListingContext = ResourceListingContext<Post>