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
import org.agorahq.agora.delivery.adapter.impl.KtorParameterizedRendererAdapter
import org.agorahq.agora.delivery.adapter.impl.KtorRendererAdapter
import org.agorahq.agora.delivery.adapter.impl.KtorResourceAltererAdapter
import org.agorahq.agora.delivery.adapter.impl.KtorResourceSaverAdapter
import org.agorahq.agora.delivery.extensions.toOperationContext

fun Routing.registerAdapters(site: SiteMetadata, authorization: Authorization) {

    get(site.baseUrl) {
        call.respondText(Templates.htmlTemplate {
            renderDefaultHomepage(call.toOperationContext(site, authorization))
        }, ContentType.Text.Html)
    }

    with(KtorRendererAdapter(Operations.showPostListing, site, authorization)) { register() }
    with(KtorParameterizedRendererAdapter(Operations.showPost, site, authorization)) { register() }
    with(KtorResourceSaverAdapter(Operations.createPost, site, authorization)) { register() }

    with(KtorResourceSaverAdapter(Operations.createComment, site, authorization)) { register() }
    with(KtorResourceSaverAdapter(Operations.updateComment, site, authorization)) { register() }
    with(KtorResourceAltererAdapter(Operations.deleteComment, site, authorization)) { register() }

    with(KtorRendererAdapter(Operations.showPostCreator, site, authorization)) { register() }
    with(KtorParameterizedRendererAdapter(Operations.showPostEditor, site, authorization)) { register() }
    with(KtorResourceAltererAdapter(Operations.deletePost, site, authorization)) { register() }
    with(KtorResourceAltererAdapter(Operations.togglePostPublished, site, authorization)) { register() }

}
