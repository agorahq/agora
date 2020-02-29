package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.title
import org.agorahq.agora.core.api.template.template

val DEFAULT_HEAD = template<String> { pageTitle ->
    head {
        title(pageTitle)
        link(rel = "stylesheet",
                href = "https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css")
    }
}