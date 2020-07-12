@file:Suppress("UNUSED_PARAMETER")

package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.HTML
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.title
import org.agorahq.agora.core.api.data.SiteMetadata

fun HTML.renderDefaultHead(
        meta: SiteMetadata,
        pageTitle: String
) {
    head {
        title(pageTitle)
        link(
                rel = "stylesheet",
                href = "https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        )
    }
}

