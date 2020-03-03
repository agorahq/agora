package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.hr
import kotlinx.html.html
import kotlinx.html.span
import org.agorahq.agora.core.api.extensions.AnyChildResourceListRenderer
import org.agorahq.agora.core.api.extensions.forEachModuleWithOperation
import org.agorahq.agora.core.api.extensions.htmlContent
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.module.context.ViewContext
import org.agorahq.agora.core.api.module.renderer.FormRenderer
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.api.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.post.viewmodel.PostViewModel

val POST_DETAILS = template<ViewContext<PostViewModel>> { ctx ->
    val (site, _, model) = ctx
    html {
        include(DEFAULT_HEAD, model.title)
        body {
            include(DEFAULT_NAVIGATION, ctx)
            div("container") {
                h1 {
                    +model.title
                }
                div {
                    htmlContent(model.content)
                }
                div {
                    model.tags.forEach { tag ->
                        span("badge badge-secondary mr-1") { +tag }
                    }
                }
                hr { }
                div {
                    site.forEachModuleWithOperation<AnyChildResourceListRenderer> { (_, operation) ->
                        htmlContent(with(operation) { ctx.reify().execute().get() })
                    }
                    site.forEachModuleWithOperation<FormRenderer<Resource, ViewModel>> { (_, operation) ->
                        htmlContent(with(operation) {
                            ctx.reify().execute().get()
                        })
                    }
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}