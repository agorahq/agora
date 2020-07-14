package org.agorahq.agora.post.templates

import kotlinx.html.HTML
import kotlinx.html.h1
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.viewmodel.PostListViewModel

fun HTML.renderPostList(
        context: OperationContext<out Any>,
        model: PostListViewModel
) = withDefaultLayout(
        ctx = context,
        pageTitle = "${context.site.title} | Posts"
) {
    h1("mt-3 mb-3") {
        +"Posts"
    }
    model.posts.forEach { renderPostCard(context, it) }
}
