package org.agorahq.agora.post.extensions

import org.agorahq.agora.core.api.module.context.PageContext
import org.agorahq.agora.core.api.module.context.ContentListingContext
import org.agorahq.agora.post.domain.Post

typealias PostRenderContext = PageContext<Post>

typealias PostListingContext = ContentListingContext<Post>