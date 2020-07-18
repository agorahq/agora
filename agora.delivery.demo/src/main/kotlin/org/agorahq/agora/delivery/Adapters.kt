package org.agorahq.agora.delivery

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.shared.templates.renderDefaultHomepage
import org.agorahq.agora.delivery.adapter.impl.*
import org.agorahq.agora.delivery.extensions.toOperationContext

fun Routing.registerAdapters(site: SiteMetadata, authorization: Authorization) {

    get(site.baseUrl) {
        call.respondText(Templates.htmlTemplate {
            renderDefaultHomepage(call.toOperationContext(site, authorization))
        }, ContentType.Text.Html)
    }

    with(KtorRendererAdapter(Services.listPosts, site, authorization)) { register() }
    with(KtorParameterizedRendererAdapter(Services.showPost, site, authorization)) { register() }
    with(KtorResourceSaverAdapter(Services.createPost, site, authorization)) { register() }

    with(KtorParameterizedRendererAdapter(Services.showCommentEditor, site, authorization)) { register() }
    with(KtorResourceSaverAdapter(Services.createComment, site, authorization)) { register() }
    with(KtorResourceAltererAdapter(Services.deleteComment, site, authorization)) { register() }

    with(KtorRendererAdapter(Services.createAndEditNewPost, site, authorization)) { register() }
    with(KtorParameterizedRendererAdapter(Services.showPostEditor, site, authorization)) { register() }
    with(KtorResourceAltererAdapter(Services.deletePost, site, authorization)) { register() }
    with(KtorResourceAltererAdapter(Services.togglePostPublished, site, authorization)) { register() }

}
