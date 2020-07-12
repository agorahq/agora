package org.agorahq.agora.post.templates

import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h1
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.viewmodel.PostListViewModel

fun HTML.renderPostList(model: PostListViewModel) = withDefaultLayout(
        ctx = model.context,
        pageTitle = "${model.context.site.title} | Posts"
) {
    h1("mt-3 mb-3") {
        +"Posts"
    }
    model.posts.forEach(::renderPostCard)
}
