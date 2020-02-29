package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.footer
import kotlinx.html.script
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.template.template

val DEFAULT_FOOTER = template<SiteMetadata> { site ->
    footer {
        text("Copyright ${site.title}")
    }
    script(src = "https://code.jquery.com/jquery-3.4.1.slim.min.js") {}
    script(src = "https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js") {}
    script(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js") {}
}