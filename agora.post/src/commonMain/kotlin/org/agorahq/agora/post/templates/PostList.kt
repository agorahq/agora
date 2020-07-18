package org.agorahq.agora.post.templates

import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.unsafe
import org.agorahq.agora.core.api.extensions.renderResourceCreatorLinksFor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.viewmodel.PostListViewModel

fun HTML.renderPostList(
        context: OperationContext<out Any>,
        model: PostListViewModel
) = withDefaultLayout(
        context = context,
        pageTitle = "${context.site.title} | Posts"
) {
    h1("mt-3 mb-3") {
        +"Posts"
    }
    val creatorLinks = context.renderResourceCreatorLinksFor(Post::class)
    if (creatorLinks.isNotBlank()) {
        div("card mb-3") {
            div("card-body") {
                unsafe {
                    +creatorLinks
                }
            }
        }
    }
    model.posts.forEach { renderPostCard(context, it) }
}
