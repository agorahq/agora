package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.FlowContent
import kotlinx.html.footer
import kotlinx.html.script
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.shared.date.Dates.renderCurrentDate

fun FlowContent.renderDefaultFooter(site: SiteMetadata) {
    footer {
        text("Copyright ${site.title} ${renderCurrentDate()}")
    }
    script(src = "https://code.jquery.com/jquery-3.4.1.slim.min.js") {}
    script(src = "https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js") {}
    script(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js") {}
}
